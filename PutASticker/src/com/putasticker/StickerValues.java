package com.putasticker;

import android.app.Activity;
import android.app.AlarmManager;

public final class StickerValues {

	public static final int STICKER_DELETED = Activity.RESULT_FIRST_USER;
	
	public static final long REPEAT_INTERVAL = AlarmManager.INTERVAL_DAY * 2;
	
	public static final int NOTIFY_TIME = 1000 * 60 * 60 * 24 * 3;
	
	public static boolean DEBUG = false;
	
}
