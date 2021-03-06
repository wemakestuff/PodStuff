

package com.wemakestuff.teracast;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

import com.github.kevinsawicki.http.HttpRequest;
import com.wemakestuff.teracast.module.RootModule;
import com.wemakestuff.teracast.service.MediaService;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.FROYO;

/**
 * Android Bootstrap application
 */
public class BaseApplication extends Application {

	private static BaseApplication instance;

	/**
	 * Create main application
	 */
	public BaseApplication() {

		// Disable http.keepAlive on Froyo and below
		if (SDK_INT <= FROYO) {
			HttpRequest.keepAlive(false);
		}
	}

	/**
	 * Create main application
	 *
	 * @param context
	 */
	public BaseApplication(final Context context) {
		this();
		attachBaseContext(context);

	}

	/**
	 * Create main application
	 *
	 * @param instrumentation
	 */
	public BaseApplication(final Instrumentation instrumentation) {
		this();
		attachBaseContext(instrumentation.getTargetContext());
	}

	public static BaseApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		instance = this;

		// Perform injection
		Injector.init(getRootModule(), this);
        Intent intent = new Intent(this, MediaService.class);
        startService(intent);
	}

	private Object getRootModule() {
		return new RootModule();
	}
}