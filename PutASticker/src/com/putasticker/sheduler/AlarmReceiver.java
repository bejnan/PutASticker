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

import com.putasticker.PushActivity;
import com.putasticker.StickerValues;
import com.putasticker.providers.Sticker;

/**
 * When the alarm fires, this WakefulBroadcastReceiver receives the broadcast
 * Intent and then starts the IntentService {@code SampleSchedulingService} to
 * do some work.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {

	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		String id = extras.getString(Sticker.ID);
		Log.i(Sticker.CONTENT_TYPE, id);

		if (id != null) {
			Intent service = new Intent(context, StickerSchedulingService.class);
			service.putExtra(Sticker.ID, id);
			startWakefulService(context, service);
			startPushActivity(context, id);
		}
	}

	private void startPushActivity(Context context, String stickerId) {
		Intent pushIntent = new Intent(context, PushActivity.class);
		pushIntent.putExtra(Sticker.ID, stickerId);
		pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		context.startActivity(pushIntent);
	}

	public void setAlarm(Context context, long id) {
		alarmMgr = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.putExtra(Sticker.ID, Long.toString(id));
		alarmIntent = PendingIntent.getBroadcast(context, (int) id, intent,
				PendingIntent.FLAG_ONE_SHOT);

		if (StickerValues.DEBUG) {
			alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, getAlarmTime(),
					StickerValues.REPEAT_INTERVAL, alarmIntent);

		} else {
			alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,
					getAlarmTime(), StickerValues.REPEAT_INTERVAL, alarmIntent);
		}
		ComponentName receiver = new ComponentName(context, BootReceiver.class);
		PackageManager pm = context.getPackageManager();

		pm.setComponentEnabledSetting(receiver,
				PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
				PackageManager.DONT_KILL_APP);
	}

	private long getAlarmTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.MILLISECOND, StickerValues.NOTIFY_TIME);
		return calendar.getTimeInMillis();
	}

	public void cancelAlarm(Context context, long id) {
		PendingIntent reCreatedIntent = cancelPendingIntent(context, id);
		if (reCreatedIntent != null)
			alarmMgr.cancel(reCreatedIntent);
		cancelAlarmRestoreOnBoot(context);
	}

	private PendingIntent cancelPendingIntent(Context context, long id) {
		Intent intent = new Intent(context, AlarmReceiver.class);
		PendingIntent recreatedIntend = PendingIntent.getBroadcast(context,
				(int) id, intent, PendingIntent.FLAG_ONE_SHOT);
		if (recreatedIntend != null)
			recreatedIntend.cancel();
		return recreatedIntend;
	}

	private void cancelAlarmRestoreOnBoot(Context context) {
		ComponentName receiver = new ComponentName(context, BootReceiver.class);
		PackageManager pm = context.getPackageManager();

		pm.setComponentEnabledSetting(receiver,
				PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
				PackageManager.DONT_KILL_APP);
	}
}
