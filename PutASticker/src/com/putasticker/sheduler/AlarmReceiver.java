package com.putasticker.sheduler;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.putasticker.providers.Sticker;

/**
 * When the alarm fires, this WakefulBroadcastReceiver receives the broadcast
 * Intent and then starts the IntentService {@code SampleSchedulingService} to
 * do some work.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {

	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;

	public static final long REPEAT_INTERVAL = AlarmManager.INTERVAL_DAY * 2;

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		String id = extras.getString(Sticker.ID);
		Log.i(Sticker.CONTENT_TYPE, id);

		if (id != null) {
			Intent service = new Intent(context, StickerSchedulingService.class);
			service.putExtra(Sticker.ID, id);
			startWakefulService(context, service);
		}
	}

	public void setAlarm(Context context, long id) {
		alarmMgr = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.putExtra(Sticker.ID, Long.toString(id));
		alarmIntent = PendingIntent.getBroadcast(context, (int) id, intent,
				PendingIntent.FLAG_ONE_SHOT);

		alarmMgr.setInexactRepeating(
				// To be punctual change to setReapeting
				AlarmManager.RTC_WAKEUP, getAlarmTime(),
				AlarmReceiver.REPEAT_INTERVAL, alarmIntent);

		ComponentName receiver = new ComponentName(context, BootReceiver.class);
		PackageManager pm = context.getPackageManager();

		pm.setComponentEnabledSetting(receiver,
				PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
				PackageManager.DONT_KILL_APP);
	}

	private long getAlarmTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.DAY_OF_YEAR, 3);
		if (calendar.before(Calendar.getInstance())) {
			calendar.add(Calendar.YEAR, 1);
		}
		// calendar.add(Calendar.MINUTE, 1);
		return calendar.getTimeInMillis();
	}

	/**
	 * Cancels the alarm.
	 * 
	 * @param context
	 */
	public void cancelAlarm(Context context) {
		if (alarmMgr != null) {
			alarmMgr.cancel(alarmIntent);
		}
		ComponentName receiver = new ComponentName(context, BootReceiver.class);
		PackageManager pm = context.getPackageManager();

		pm.setComponentEnabledSetting(receiver,
				PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
				PackageManager.DONT_KILL_APP);
	}
}
