package com.putasticker.sheduler;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
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

		long id = (intent.getStringExtra(Sticker.ID) != null) ? Long
				.parseLong(intent.getStringExtra(Sticker.ID)) : 0;
		Log.i(Sticker.CONTENT_TYPE, Long.toString(id));
		if (id > 0) {
			sendNotification(id);
			AlarmReceiver.completeWakefulIntent(intent);
		}
	}

	private void sendNotification(long id) {
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Sticker sticker = new Sticker(id, getContentResolver());
		
		if (sticker.getId() > 0 && !sticker.isEmpty()) {
			PendingIntent clickIntent = createStickerIntent(id);
			NotificationCompat.Builder mBuilder = createNotification(sticker, clickIntent);
			mNotificationManager.notify((int) sticker.getId(), mBuilder.build());
		}
	}
	
	private PendingIntent createStickerIntent(long id)
	{
		Intent newIntent = new Intent(this, SavedStickActivity.class);
		newIntent.putExtra(Sticker.ID, Long.toString(id));
		PendingIntent contentIntent = PendingIntent.getActivity(this, (int)id,
				newIntent, PendingIntent.FLAG_ONE_SHOT);
		return contentIntent;
	}
	
	private NotificationCompat.Builder createNotification(Sticker sticker, PendingIntent newIntent)
	{
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(sticker.getSubject())
				.setContentText(sticker.getText())
				.setContentIntent(newIntent)
				.setSound(alarmSound);
		return mBuilder;
	}
}
