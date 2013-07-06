package com.wemakestuff.podstuff.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.RemoteControlClient;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.wemakestuff.podstuff.BootstrapApplication;
import com.wemakestuff.podstuff.R;
import com.wemakestuff.podstuff.core.Constants;
import com.wemakestuff.podstuff.util.ImageUtils;
import com.wemakestuff.podstuff.media.MediaButtonHelper;
import com.wemakestuff.podstuff.media.RemoteControlClientCompat;
import com.wemakestuff.podstuff.media.RemoteControlHelper;
import com.wemakestuff.podstuff.media.event.*;
import com.wemakestuff.podstuff.rss.model.RssItem;
import com.wemakestuff.podstuff.rss.model.iTunesImage;
import com.wemakestuff.podstuff.ui.PlayerActivity;
import com.wemakestuff.podstuff.util.Ln;

import javax.inject.Inject;
import java.io.IOException;

import static com.wemakestuff.podstuff.core.Constants.Notification.PLAYBACK_NOTIFICATION_ID;
import static com.wemakestuff.podstuff.core.Constants.System.WIFI_LOCK_TAG;

public class MediaService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener,
                                                     MediaPlayer.OnErrorListener, AudioManager.OnAudioFocusChangeListener, Runnable {

	public static final String TAG         = "MediaService";
	// The volume we set the media player to when we lose audio focus, but are allowed to reduce
	// the volume instead of stopping playback.
	public static final float  DUCK_VOLUME = 0.1f;
	@Inject
	protected Bus                 BUS;
	@Inject
	protected NotificationManager mNotificationManager;
	@Inject
	protected AudioManager        mAudioManager;
	private MediaPlayer mPlayer      = null;
	private State       mState       = State.Stopped;
	private boolean     mIsStreaming = false;
	//Why did we pause? Only relevant when State == Paused.
	private PauseReason mPauseReason = PauseReason.UserRequest;
	// Wifi lock that we hold when streaming files from the internet, in order to prevent the
	// device from shutting off the Wifi radio
	private WifiManager.WifiLock      mWifiLock;
	// our RemoteControlClient object, which will use remote control APIs available in
	// SDK level >= 14, if they're available.
	private RemoteControlClientCompat mRemoteControlClientCompat;
	// The component name of MusicIntentReceiver, for use with media button and remote control
	// APIs
	private ComponentName             mMediaButtonReceiverComponent;
	private Notification mNotification  = null;
	//Do we have audio focus?
	private AudioFocus   mAudioFocus    = AudioFocus.NoFocusNoDuck;
	private RssItem      playingRssItem = null;

	@Override
	public void onCreate() {
		super.onCreate();
		Ln.d("%s - Service Started.", TAG);
		BootstrapApplication.getInstance().inject(this);
		// Register the bus so we can send notifications.
		BUS.register(this);

		// Create the Wifi lock (this does not acquire the lock, this just creates it)
		mWifiLock = ((WifiManager) getSystemService(Context.WIFI_SERVICE))
				            .createWifiLock(WifiManager.WIFI_MODE_FULL, WIFI_LOCK_TAG);
		mMediaButtonReceiverComponent = new ComponentName(this, MusicIntentReceiver.class);
	}

	@Override
	public void onDestroy() {
		Ln.d("%s - Service Destroyed.", TAG);
		// Unregister bus, since its not longer needed as the service is shutting down
		BUS.unregister(this);
		mNotificationManager.cancel(Constants.Notification.PLAYBACK_NOTIFICATION_ID);
		super.onDestroy();
	}

	/**
	 * Starts playing the next song.
	 */
	private void playMedia(RssItem mediaItem) {
		updatePlaybackState(State.Stopped);
		releaseResources(false); // release everything except MediaPlayer

		String itemName = "NULL";
		if (mediaItem != null) {
			itemName = mediaItem.getTitle();
			Ln.d("%s - Attempting to Play: %s", TAG, itemName);
		}

		try {
			if (mediaItem == null) {
				//TODO: Need to grab the next Item in the queue and assign it as mediaItem.
				Ln.d("%s - No Item Passed to Play", TAG);
			}

			if (mediaItem != null) {
				playingRssItem = mediaItem;
				tryToGetAudioFocus();
				createMediaPlayerIfNeeded();
				mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				//TODO: Add logic for playing local files.
				mPlayer.setDataSource(mediaItem.getEnclosure().getUrl());

				mIsStreaming = true;
				updatePlaybackState(State.Preparing);
				Ln.d("%s - Becoming Foreground Service", TAG);
				setUpAsForeground(mediaItem);

				// Use the media button APIs (if available) to register ourselves for media button
				// events
				Ln.d("%s - Setting Up Media Button Receiver", TAG);
				MediaButtonHelper.registerMediaButtonEventReceiverCompat(mAudioManager, mMediaButtonReceiverComponent);

				// Use the remote control APIs (if available) to set the playback state
				if (mRemoteControlClientCompat == null) {
					Ln.d("%s - Setting up Remote Control APIs", TAG);
					Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
					intent.setComponent(mMediaButtonReceiverComponent);
					mRemoteControlClientCompat = new RemoteControlClientCompat(PendingIntent.getBroadcast(this /*context*/, 0 /*requestCode, ignored*/, intent /*intent*/, 0 /*flags*/));
					RemoteControlHelper.registerRemoteControlClient(mAudioManager, mRemoteControlClientCompat);
				}

				Ln.d("%s - Updating Status on Remote Control", TAG);
				mRemoteControlClientCompat.setPlaybackState(RemoteControlClient.PLAYSTATE_PLAYING);
				mRemoteControlClientCompat.setTransportControlFlags(RemoteControlClient.FLAG_KEY_MEDIA_PLAY | RemoteControlClient.FLAG_KEY_MEDIA_PAUSE | RemoteControlClient.FLAG_KEY_MEDIA_NEXT | RemoteControlClient.FLAG_KEY_MEDIA_STOP);

				String largeIconPath = "";
				iTunesImage itunesImage = mediaItem.getiTunesImage();
				if (itunesImage != null) {
					largeIconPath = itunesImage.getHref();
				}

				mRemoteControlClientCompat.editMetadata(true).putString(MediaMetadataRetriever.METADATA_KEY_ARTIST, "Startups For the Rest of Us")
				                          .putString(MediaMetadataRetriever.METADATA_KEY_TITLE, mediaItem.getTitle())
				                          .putLong(MediaMetadataRetriever.METADATA_KEY_DURATION, mediaItem.getEnclosure().getLength())
				                          .putBitmap(RemoteControlClientCompat.MetadataEditorCompat.METADATA_KEY_ARTWORK, ImageUtils.getBitmap(largeIconPath))
				                          .apply();

				// starts preparing the media player in the background. When it's done, it will call
				// our OnPreparedListener (that is, the onPrepared() method on this class, since we set
				// the listener to 'this').
				// Until the media player is prepared, we *cannot* call start() on it!
				Ln.d("%s - Preparing to Play", TAG);
				mPlayer.prepareAsync();

				// If we are streaming from the internet, we want to hold a Wifi lock, which prevents
				// the Wifi radio from going to sleep while the song is playing. If, on the other hand,
				// we are *not* streaming, we want to release the lock if we were holding it before.
				if (mIsStreaming) {
					Ln.d("%s - Retrieving Wifi Lock", TAG);
					mWifiLock.acquire();
				} else if (mWifiLock.isHeld()) {
					Ln.d("%s - Releasing Wifi Lock", TAG);
					mWifiLock.release();
				}
			} else {
				//We have advanced to the next item due to completion or user input and the queue is empty. Release all the things!
				Ln.d("%s - Queue is Empty, and Playback is done. Stopping all playback.", TAG);
				processStopRequest(true);
			}
		} catch (IOException e) {
			Ln.e(e, TAG + "Failed to Play Media Item %s:%s - IO Error", "Startups for the Rest of Us", mediaItem.getTitle());
		} catch (IllegalArgumentException e) {
			Ln.e(e, TAG + "Failed to Play Media Item %s:%s - Bad Arguments", "Startups for the Rest of Us", mediaItem.getTitle());
		} catch (SecurityException e) {
			Ln.e(e, TAG + "Failed to Play Media Item %s:%s - Security Error", "Startups for the Rest of Us", mediaItem.getTitle());
		} catch (IllegalStateException e) {
			Ln.e(e, TAG + "Failed to Play Media Item %s:%s - Bad State", "Startups for the Rest of Us", mediaItem.getTitle());
		}
	}

	private void playPreviousMedia() {
		//TODO: Get the previous item in the queue and play it.
		playMedia(playingRssItem);
	}

	private void processTogglePlaybackRequest() {
		if (mState == State.Paused || mState == State.Stopped) {
			processPlayRequest();
		} else {
			processPauseRequest();
		}
	}

	private void processPlayRequest() {
		// actually play the song
		if (mState == State.Stopped) {
			// If we're stopped, just go ahead and start the last played song (if available), if not go to the next in the queue.
			playMedia(playingRssItem);
		} else if (mState == State.Paused) {
			// If we're paused, just continue playback and restore the 'foreground service' state.
			updatePlaybackState(State.Playing);
			configAndStartMediaPlayer();
		}

		// Tell any remote controls that our playback state is 'playing'.
		if (mRemoteControlClientCompat != null) {
			mRemoteControlClientCompat
					.setPlaybackState(RemoteControlClient.PLAYSTATE_PLAYING);
		}

		updateNotification(playingRssItem);
	}

	private void processPauseRequest() {
		if (mState == State.Playing) {
			// Pause media player and cancel the 'foreground service' state.
			updatePlaybackState(State.Paused);
			mPlayer.pause();
			releaseResources(false); // while paused, we always retain the MediaPlayer
			// do not give up audio focus
		}

		// Tell any remote controls that our playback state is 'paused'.
		if (mRemoteControlClientCompat != null) {
			mRemoteControlClientCompat
					.setPlaybackState(RemoteControlClient.PLAYSTATE_PAUSED);
		}

		updateNotification(playingRssItem);
	}

	private void processPreviousRequest() {
		if (mState == State.Playing || mState == State.Paused) {
			tryToGetAudioFocus();
			playPreviousMedia();
		}
	}

	private void processNextRequest() {
		playMedia(null);
	}

	private void processStopRequest() {
		processStopRequest(false);
	}

	private void processRewindRequest() {
		processSeekRequest(mPlayer.getCurrentPosition() - 15000);
	}

	private void processSeekRequest(int seekTo) {
		if (mState == State.Playing || mState == State.Paused) {
			mPlayer.seekTo(seekTo < 0 ? 0 : seekTo);
			produceProvideMediaProgressEvent();
		}
	}

	private void processFastForwardRequest() {
		processSeekRequest(mPlayer.getCurrentPosition() + 15000);
	}

	private void processStopRequest(boolean forceStop) {
		if (mState == State.Playing || mState == State.Paused || forceStop) {
			updatePlaybackState(State.Stopped);

			// let go of all resources...
			releaseResources(true);
			produceProvideMediaProgressEvent();
			giveUpAudioFocus();
			playingRssItem = null;

			cancelNotification();

			// Tell any remote controls that our playback state is 'stopped'.
			if (mRemoteControlClientCompat != null) {
				mRemoteControlClientCompat
						.setPlaybackState(RemoteControlClient.PLAYSTATE_STOPPED);
			}

			// service is no longer necessary. Will be started again if needed.
			stopSelf();
		}
	}

	/**
	 * Makes sure the media player exists and has been reset. This will create the media player if needed, or reset the
	 * existing media player if one already exists.
	 */
	private void createMediaPlayerIfNeeded() {
		if (mPlayer == null) {
			Ln.d("%s - Creating Media Player", TAG);
			mPlayer = new MediaPlayer();

			// Make sure the media player will acquire a wake-lock while playing. If we don't do
			// that, the CPU might go to sleep while the song is playing, causing playback to stop.
			mPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);

			// we want the media player to notify us when it's ready preparing, and when it's done
			// playing:
			mPlayer.setOnPreparedListener(this);
			mPlayer.setOnCompletionListener(this);
			mPlayer.setOnErrorListener(this);
		} else {
			Ln.d("%s - Resetting Media Player", TAG);
			mPlayer.reset();
		}
	}

	/**
	 * Reconfigures MediaPlayer according to audio focus settings and starts/restarts it. This method starts/restarts the
	 * MediaPlayer respecting the current audio focus state. So if we have focus, it will play normally; if we don't have
	 * focus, it will either leave the MediaPlayer paused or set it to a low volume, depending on what is allowed by the
	 * current focus settings. This method assumes mPlayer != null, so if you are calling it, you have to do so from a
	 * context where you are sure this is the case.
	 */
	private void configAndStartMediaPlayer() {
		if (mAudioFocus == AudioFocus.NoFocusNoDuck) {
			// If we don't have audio focus and can't duck, we have to pause, even if mState
			// is State.Playing. But we stay in the Playing state so that we know we have to resume
			// playback once we get the focus back.
			if (mPlayer.isPlaying()) {
				Ln.d("%s - Pausing Audio Playback", TAG);
				mPlayer.pause();
			}
			return;
		} else if (mAudioFocus == AudioFocus.NoFocusCanDuck) {
			Ln.d("%s - Ducking Audio Playback", TAG);
			mPlayer.setVolume(DUCK_VOLUME, DUCK_VOLUME);  // we'll be relatively quiet
		} else {
			Ln.d("%s - Setting Volume to 11", TAG);
			mPlayer.setVolume(1.0f, 1.0f); // we can be loud
		}

		if (!mPlayer.isPlaying()) {
			Ln.d("%s - Beginning Audio Playback", TAG);
			mPlayer.start();
			new Thread(this).start();
		}
	}

	/**
	 * Releases resources used by the service for playback. This includes the "foreground service" status and notification,
	 * the wake locks and possibly the MediaPlayer.
	 *
	 * @param releaseMediaPlayer
	 * 		Indicates whether the Media Player should also be released or not
	 */
	private void releaseResources(boolean releaseMediaPlayer) {

		// stop and release the Media Player, if it's available
		if (releaseMediaPlayer && mPlayer != null) {
			// stop being a foreground service
			stopForeground(true);

			//Release the player.
			mPlayer.reset();
			mPlayer.release();
			mPlayer = null;
		}

		// we can also release the Wifi lock, if we're holding it
		if (mWifiLock.isHeld()) {
			mWifiLock.release();
		}
	}

	private void tryToGetAudioFocus() {
		Ln.d("%s - Trying to get audio focus", TAG);
		if (mAudioFocus != AudioFocus.Focused && requestFocus()) {
			Ln.d("%s - Got audio focus!", TAG);
			mAudioFocus = AudioFocus.Focused;
		}
	}

	private void giveUpAudioFocus() {
		Ln.d("%s - Giving up audio focus", TAG);
		if (mAudioFocus == AudioFocus.Focused && abandonFocus()) {
			Ln.d("%s - Gave up audio focus!", TAG);
			mAudioFocus = AudioFocus.NoFocusNoDuck;
		}
	}

	/**
	 * Requests audio focus. Returns whether request was successful or not.
	 */
	private boolean requestFocus() {
		return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
		       mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
	}

	/**
	 * Abandons audio focus. Returns whether request was successful or not.
	 */
	private boolean abandonFocus() {
		return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == mAudioManager.abandonAudioFocus(this);
	}

	/**
	 * Configures service as a foreground service. A foreground service is a service that's doing something the user is
	 * actively aware of (such as playing music), and must appear to the user as a notification. That's why we create the
	 * notification here.
	 */
	private void setUpAsForeground(RssItem mediaItem) {
		// Run as foreground service: http://stackoverflow.com/a/3856940/5210
		// Another example: https://github.com/commonsguy/cw-android/blob/master/Notifications/FakePlayer/src/com/commonsware/android/fakeplayerfg/PlayerService.java
		startForeground(PLAYBACK_NOTIFICATION_ID, getNotification(mediaItem));
	}

	@Subscribe
	public void onHeadsetPluggedInEvent(HeadsetPluggedInEvent headsetPluggedInEvent) {
		//TODO: Add logic to check settings and stuff.
		processPlayRequest();
	}

	@Subscribe
	public void onRequestMediaServiceStateEvent(RequestMediaServiceStateEvent headsetUnpluggedEvent) {
		produceProvideMediaServiceStateEvent();
	}

	@Subscribe
	public void onHeadsetUnpluggedEvent(HeadsetUnpluggedEvent headsetUnpluggedEvent) {
		processPauseRequest();
	}

	@Subscribe
	public void onNextEvent(NextPlaybackEvent nextPlaybackEvent) {
		processNextRequest();
	}

	@Subscribe
	public void onPauseEvent(PausePlaybackEvent pausePlaybackEvent) {
		processPauseRequest();
	}

	@Subscribe
	public void onPlayEvent(PlayPlaybackEvent playPlaybackEvent) {
		processPlayRequest();
	}

	@Subscribe
	public void onPlayItemEvent(PlayItemPlaybackEvent playItemPlaybackEvent) {
		playMedia(playItemPlaybackEvent.mediaItem);
	}

	@Subscribe
	public void onPreviousEvent(PreviousPlaybackEvent previousPlaybackEvent) {
		processPreviousRequest();
	}

	@Subscribe
	public void onRewindEvent(RewindPlaybackEvent rewindPlaybackEvent) {
		processRewindRequest();
	}

	@Subscribe
	public void onFastForwardEvent(FastForwardPlaybackEvent fastForwardPlaybackEvent) {
		processFastForwardRequest();
	}

	@Subscribe
	public void onStopEvent(StopPlaybackEvent stopPlaybackEvent) {
		processStopRequest();
	}

	@Subscribe
	public void onToggleEvent(TogglePlaybackEvent togglePlaybackEvent) {
		processTogglePlaybackRequest();
	}

	@Subscribe
	public void onSeekPlaybackEvent(SeekPlaybackEvent seekPlaybackEvent) {
		processSeekRequest(seekPlaybackEvent.seekTo);
	}

	@Override
	public void onCompletion(final MediaPlayer mp) {
		//Media playback has completed, play the next song in the queue if there is one.
		playMedia(null);
	}

	@Override
	public boolean onError(final MediaPlayer mp, final int what, final int extra) {
		Ln.e(TAG + "Error: what=%s extra=%s", String.valueOf(what), String.valueOf(extra));

		updatePlaybackState(State.Stopped);
		releaseResources(true);
		giveUpAudioFocus();
		return true; // true indicates we handled the error
	}

	@Override
	public void onPrepared(final MediaPlayer mp) {
		// The media player is done preparing. That means we can start playing!
		Ln.d("%s - Media Player is Prepared, Starting Playback.", TAG);
		updatePlaybackState(State.Playing);
		updateNotification(playingRssItem);
		configAndStartMediaPlayer();
	}

	@Override
	public IBinder onBind(final Intent intent) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onAudioFocusChange(final int focusChange) {
		switch (focusChange) {
			case AudioManager.AUDIOFOCUS_GAIN:
				mAudioFocus = AudioFocus.Focused;

				// restart media player with new focus settings
				if (mState == State.Playing) {
					configAndStartMediaPlayer();
				}
				break;
			case AudioManager.AUDIOFOCUS_LOSS:
				//Audio focus has been lost. Audio playback should be stopped.
				mAudioFocus = AudioFocus.NoFocusNoDuck;
				mPauseReason = PauseReason.FocusLoss;
				processStopRequest(true);
				break;
			case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
				//Audio focus has been lost for a short period of time. Audio playback should be paused.
				mAudioFocus = AudioFocus.NoFocusNoDuck;

				// start/restart/pause media player with new focus settings
				if (mPlayer != null && mPlayer.isPlaying()) {
					configAndStartMediaPlayer();
				}
				break;
			case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
				//Audio focus has been lost for a short period of time, DUCK!
				mAudioFocus = AudioFocus.NoFocusCanDuck;

				// start/restart/pause media player with new focus settings
				if (mPlayer != null && mPlayer.isPlaying()) {
					configAndStartMediaPlayer();
				}
				break;
		}
	}

	private void updateNotification(RssItem mediaItem) {
		mNotificationManager.notify(PLAYBACK_NOTIFICATION_ID, getNotification(mediaItem));
	}

	private void cancelNotification() {
		mNotificationManager.cancel(PLAYBACK_NOTIFICATION_ID);
	}

	private void updatePlaybackState(State state) {
		mState = state;
		produceProvideMediaServiceStateEvent();
	}

	private void produceProvideMediaServiceStateEvent() {
		BUS.post(new ProvideMediaServiceStateEvent(mState));
	}

	private void produceProvideMediaProgressEvent() {
		if (mPlayer != null && mState != State.Stopped) {
			BUS.post(new ProvideMediaProgressEvent(mPlayer.getCurrentPosition(), mPlayer.getDuration()));
		} else {
			BUS.post(new ProvideMediaProgressEvent(0, 42000));
		}
	}

	/**
	 * Creates a notification to show in the notification bar
	 *
	 * @param mediaItem
	 * 		The item that the notification is being created for. This is used to fill out the Custom Notification.
	 *
	 * @return a new {@link android.app.Notification}
	 */
	private Notification getNotification(RssItem mediaItem) {
		final Intent intent = new Intent(this, PlayerActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

		int smallIcon = R.drawable.ic_media_pause;
		String contentInfo = getString(R.string.paused);

		switch (mState) {
			case Preparing:
				smallIcon = R.drawable.stat_sys_download;
				contentInfo = getString(R.string.loading);
				break;
			case Paused:
				smallIcon = R.drawable.ic_media_pause;
				contentInfo = getString(R.string.paused);
				break;
			case Playing:
				smallIcon = R.drawable.ic_media_play;
				contentInfo = getString(R.string.playing);
				break;
			case Stopped:
				smallIcon = R.drawable.ic_media_stop;
				contentInfo = getString(R.string.stopped);
				break;
			default:
				smallIcon = R.drawable.ic_media_stop;
				contentInfo = getString(R.string.stopped);
		}

		String largeIconPath = "";
		iTunesImage itunesImage = mediaItem.getiTunesImage();
		if (itunesImage != null) {
			largeIconPath = itunesImage.getHref();
		}

		return new NotificationCompat.Builder(this)
				       .setContentTitle("Startups For the Rest of Us")
				       .setSmallIcon(smallIcon)
				       .setLargeIcon(ImageUtils.getBitmap(largeIconPath))
				       .setContentText(mediaItem.getTitle())
				       .setContentInfo(contentInfo)
				       .setAutoCancel(false)
				       .setOnlyAlertOnce(true)
				       .setOngoing(true)
				       .setContentIntent(pendingIntent)
				       .getNotification();
	}

	@Override
	public void run() {
		while (mPlayer != null && mState == State.Playing) {
			try {
				Thread.sleep(1000);
				produceProvideMediaProgressEvent();
			} catch (InterruptedException e) {
				return;
			}
		}
	}

	// indicates the state our service:
	public enum State {
		Stopped,    // media player is stopped and not prepared to play
		Preparing,  // media player is preparing...
		Playing,    // playback active (media player ready!). (but the media player may actually be
		// paused in this state if we don't have audio focus. But we stay in this state
		// so that we know we have to resume playback once we get focus back)
		Paused      // playback paused (media player ready!)
	}

	enum PauseReason {
		UserRequest,  // paused by user request
		FocusLoss,    // paused because of audio focus loss
		HeadsetUnplugged //paused because of headset being unplugged
	}

	// do we have audio focus?
	enum AudioFocus {
		NoFocusNoDuck,    // we don't have audio focus, and can't duck
		NoFocusCanDuck,   // we don't have focus, but can play at a low volume ("ducking")
		Focused           // we have full audio focus
	}
}
