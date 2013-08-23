package com.wemakestuff.podstuff.ui;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.InjectView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import com.wemakestuff.podstuff.R;
import com.wemakestuff.podstuff.media.event.ProvideMediaProgressEvent;
import com.wemakestuff.podstuff.media.event.ProvideMediaServiceStateEvent;
import com.wemakestuff.podstuff.media.event.ToggleEvent;
import com.wemakestuff.podstuff.provider.ContentManager;
import com.wemakestuff.podstuff.rss.event.ProvideRssFeedEvent;
import com.wemakestuff.podstuff.rss.model.RssFeed;
import com.wemakestuff.podstuff.rss.model.RssItem;
import com.wemakestuff.podstuff.service.MediaService;
import com.wemakestuff.podstuff.service.RssFeedService;
import com.wemakestuff.podstuff.util.ConversionUtils;
import com.wemakestuff.podstuff.util.Ln;

import javax.inject.Inject;

public class FeedItemListActivity extends BootstrapListActivity {
	public static final String TAG = FeedItemListActivity.class.getSimpleName();
	@Inject
	Bus            bus;
	@InjectView(R.id.rl_currently_playing)
	RelativeLayout currentlyPlayingSection;
	@InjectView(R.id.ib_currently_playing_podcast_icon)
	ImageButton    currentlyPlayingPodcastIcon;
	@InjectView(R.id.tv_currently_playing_episode_title)
	TextView       currentlyPlayingEpisodeTitle;
	@InjectView(R.id.tv_currently_playing_length)
	TextView       currentlyPlayingLength;
	@InjectView(R.id.ib_currently_playing_play_pause)
	ImageButton    currentlyPlayingPlayPause;
	RssFeed parsedFeed;
	RssItem mCurrentlyPlayingItem;
	MediaService.State mMediaServiceState = MediaService.State.Stopped;
	Integer mCurrentlyPlayingProgress;
	Integer mCurrentlyPlayingLength;

    @Inject
    ContentManager manager;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity);
		RssFeedService.getRssFeed(getApplicationContext(), Uri.parse("http://feeds.feedburner.com/StartupsForTheRestOfUs"));
		getListView().setTextFilterEnabled(true);
		getListView().setFastScrollEnabled(true);
		getListView().setClickable(true);

        Log.i(TAG, "manager = " + (manager == null ? "null" : "not null"));
        updateCurrentlyPlayingView();
	}

	private void updateCurrentlyPlayingView() {
		if (mMediaServiceState != null && mMediaServiceState != MediaService.State.Stopped) {
			Ln.d("%s - Current State is %s", TAG, mMediaServiceState.toString());
			switch (mMediaServiceState) {
				case Paused:
					Picasso.with(this).load(R.drawable.ic_media_play).into(currentlyPlayingPlayPause);
					break;
				case Playing:
					Picasso.with(this).load(R.drawable.ic_media_pause).into(currentlyPlayingPlayPause);
					break;
				case Preparing:
					Picasso.with(this).load(R.drawable.ic_media_play).into(currentlyPlayingPlayPause);
					break;
			}

			Picasso.with(this)
			       .load("http://www.startupsfortherestofus.com/wp-content/uploads/sftrou_300x300.jpg")
			       .into(currentlyPlayingPodcastIcon);

			if (mCurrentlyPlayingItem != null) {
				Ln.d("%s - Updating Current Episode Title", TAG);
				currentlyPlayingEpisodeTitle.setText(mCurrentlyPlayingItem.getTitle());
			}

			String currentProgressLength = "";

			//When the Media Player is preparing it sends back a large number for the maximum length, want to suppress that.
			if (mCurrentlyPlayingProgress != null && mCurrentlyPlayingLength != null && mMediaServiceState != MediaService.State.Preparing) {
				Ln.d("%s - Updating Current Episode Progress", TAG);
				currentProgressLength = ConversionUtils.formatMilliseconds(mCurrentlyPlayingProgress) + "/" + ConversionUtils.formatMilliseconds(mCurrentlyPlayingLength);
			} else {
				currentProgressLength += "0:00:00/0:42:42";
			}
			currentlyPlayingLength.setText(currentProgressLength);

			currentlyPlayingPlayPause.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(final View v) {
					produceToggleEvent();
				}
			});

			Ln.d("%s - Displaying Currently Playing Section", TAG);
			currentlyPlayingSection.setVisibility(View.VISIBLE);
		} else {
			Ln.d("%s - Current State is Stopped, Hiding Currently Playing Section", TAG);
			currentlyPlayingSection.setVisibility(View.GONE);
		}

	}

	@Override
	protected void onDestroy() {
		bus.unregister(this);
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		bus.register(this);
	}

	@Subscribe
	public void onProvideRssFeedEvent(ProvideRssFeedEvent provideRssFeedEvent) {
		parsedFeed = provideRssFeedEvent.rssFeed;
		FeedItemListAdapter adapter = new FeedItemListAdapter(getLayoutInflater(), parsedFeed);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(final ListView l, final View v, final int position, final long id) {
		super.onListItemClick(l, v, position, id);
	}

	/**
	 * Handles a {@link com.wemakestuff.podstuff.media.event.ProvideMediaServiceStateEvent} message to the {@link Bus}
	 */
	@Subscribe
	public void onProvideMediaServiceStateEvent(ProvideMediaServiceStateEvent mediaServiceStateEvent) {
		mMediaServiceState = mediaServiceStateEvent.state;
		mCurrentlyPlayingItem = mediaServiceStateEvent.rssItem;
		if (mMediaServiceState != null && mCurrentlyPlayingItem != null) {
			Ln.d("%s - Got Media Service State of %s for %s", TAG, mMediaServiceState.toString(), mCurrentlyPlayingItem.getTitle());
		}
		updateCurrentlyPlayingView();
	}

	/**
	 * Handles a {@link com.wemakestuff.podstuff.media.event.ProvideMediaProgressEvent} message to the {@link Bus}
	 */
	@Subscribe
	public void onProvideMediaProgressEvent(ProvideMediaProgressEvent mediaProgressEvent) {
		mCurrentlyPlayingProgress = mediaProgressEvent.progress;
		mCurrentlyPlayingLength = mediaProgressEvent.max;
		if (mCurrentlyPlayingProgress != null && mCurrentlyPlayingLength != null) {
			Ln.d("%s - Got Media Progress %s listened of %s", TAG, ConversionUtils.formatMilliseconds(mCurrentlyPlayingProgress), ConversionUtils.formatMilliseconds(mCurrentlyPlayingLength));
		}
		updateCurrentlyPlayingView();
	}

	/**
	 * Posts a {@link com.wemakestuff.podstuff.media.event.ToggleEvent} message to the {@link Bus}
	 */
	private void produceToggleEvent() {
		Ln.d("%s - Sent Toggle Event", TAG);
		bus.post(new ToggleEvent());
	}
}
