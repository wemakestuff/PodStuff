package com.wemakestuff.podstuff.rss.event;

import android.net.Uri;

public class RequestRssFeedEvent {
	public Uri uri;

	public RequestRssFeedEvent(Uri uri) {
		this.uri = uri;
	}
}
