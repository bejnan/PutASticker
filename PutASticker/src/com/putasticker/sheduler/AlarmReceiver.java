package com.putasticker.sheduler;

import java.util.Calendar;

import com.putasticker.providers.Sticker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * When the alarm fires, this WakefulBroadcastReceiver receives the broadcast
 * Intent and then starts the IntentService {@code SampleSchedulingService} to
 * do some work.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {

	// The app's AlarmManager, which provides access to the system alarm
	// services.
	private AlarmManager alarmMgr;
	// The pending intent that is triggered when the alarm fires.
	private PendingIntent alarmIntent;

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent service = new Intent(context, StickerSchedulingService.class);
		String id = intent.getStringExtra(Sticker.ID);
		if (id != null) {
			service.putExtra(Sticker.ID, id);
			startWakefulService(context, service);
		}
	}

	public void setAlarm(Context context, long id) {
		alarmMgr = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.putExtra(Sticker.ID, Long.toString(id));
		alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.MINUTE, 1);

		alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
				alarmIntent);

		ComponentName receiver = new ComponentName(context, BootReceiver.class);
		PackageManager pm = context.getPackageManager();

		pm.setComponentEnabledSetting(receiver,
				PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
				PackageManager.DONT_KILL_APP);
	}

	/**
	 * Cancels the alarm.
	 * 
	 * @param context
	 */
	public void cancelAlarm(Context context) {
		// If the alarm has been set, cancel it.
		if (alarmMgr != null) {
			alarmMgr.cancel(alarmIntent);
		}

		// Disable {@code SampleBootReceiver} so that it doesn't automatically
		// restart the
		// alarm when the device is rebooted.
		ComponentName receiver = new ComponentName(context, BootReceiver.class);
		PackageManager pm = context.getPackageManager();

		pm.setComponentEnabledSetting(receiver,
				PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
				PackageManager.DONT_KILL_APP);
	}
}
