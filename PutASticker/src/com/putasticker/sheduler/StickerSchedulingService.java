package com.putasticker.sheduler;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.putasticker.R;
import com.putasticker.SavedStickActivity;
import com.putasticker.providers.Sticker;

public class StickerSchedulingService extends IntentService {
	public StickerSchedulingService() {
		super("SchedulingService");
	}

	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;

	@Override
	protected void onHandleIntent(Intent intent) {

		Bundle extras = intent.getExtras();
		long id = (extras.getString(Sticker.ID) != null) ? Long
				.parseLong(intent.getStringExtra(Sticker.ID)) : 0; 
		Log.i(Sticker.CONTENT_TYPE, Long.toString(id));
		sendNotification(id);
		AlarmReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(long id) {
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Intent newIntent = new Intent(this, SavedStickActivity.class);
		newIntent.putExtra(Sticker.ID, Long.toString(id));
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				newIntent, 0);

		Sticker stick = new Sticker(id, getContentResolver());

		if (stick.getId() > 0) {
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
					this)
					.setSmallIcon(R.drawable.ic_launcher)
					.setContentTitle(stick.getSubject())
					.setContentText(stick.getText());

			mBuilder.setContentIntent(contentIntent);
			mNotificationManager.notify((int) stick.getId(), mBuilder.build());
		}
	}
}
