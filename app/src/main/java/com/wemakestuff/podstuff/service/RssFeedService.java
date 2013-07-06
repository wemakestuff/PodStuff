package com.wemakestuff.podstuff.service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.squareup.otto.Bus;
import com.wemakestuff.podstuff.rss.RssFeed;
import com.wemakestuff.podstuff.rss.RssFeedParser;

import javax.inject.Inject;

public class RssFeedService extends HttpService {

	private static final String TAG = RssFeedService.class.getSimpleName();
	@Inject
	Bus bus;

	@Override
	public void onRequestComplete(final Intent result) {
		super.onRequestComplete(result);

		Bundle args = result.getBundleExtra(EXTRA_BUNDLE);
		RequestCode mRequestCode = (RequestCode) args.getSerializable(EXTRA_REQUEST_CODE);
		int mHttpStatusCode = args.getInt(EXTRA_STATUS_CODE);
		String mJson = args.getString(REST_RESULT);

		Log.i(TAG, "Request complete!");
		if (mRequestCode != null)
			Log.i(TAG, "RequestCode = " + mRequestCode.name());

		Log.i(TAG, "HTTP Status Code = " + mHttpStatusCode);
		Log.i(TAG, "JSON/XML = " + mJson);

		RssFeedParser parser = new RssFeedParser(mJson);
		RssFeed feed = parser.parse();

		bus.post(feed);
	}

	public static void getRssFeed(Context context, Uri uri) {
		Intent intent = new Intent(context, RssFeedService.class);
		intent.putExtra(EXTRA_USER_AGENT, USER_AGENT);
		intent.setData(uri);

		context.startService(intent);
	}
}