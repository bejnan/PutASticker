package com.putasticker.providers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.putasticker.sheduler.AlarmReceiver;

public class Sticker implements BaseColumns {

	public static final Uri CONTENT_URI = Uri.parse("content://"
			+ StickerContentProvider.Authority + "/sticker");
	public static final String CONTENT_TYPE = "com.putasticker.providers.sticker";
	public static final String ID = "_id";
	public static final String SUBJECT = "subject";
	public static final String TEXT = "text";
	public static final String[] projection = { ID, SUBJECT, TEXT };
	public static final long NotificationTime = 1000*60*60;

	private long id;
	private String subject;
	private String text;
	private int notification_id;
	private boolean isChanged;
	private ContentResolver resolver;
	
	private static AlarmReceiver alarm = new AlarmReceiver();
	
	public static long createStricker(String subject, String text, ContentResolver cr, Context context)
	{
		Uri newUri;
		ContentValues newValues = new ContentValues();
		newValues.put(Sticker.SUBJECT, subject);
		newValues.put(Sticker.TEXT, text);
	
		newUri = cr.insert(Sticker.CONTENT_URI, newValues);
		long id = Long.parseLong(newUri.getLastPathSegment());
		
		alarm.setAlarm(context,id);
		
		return id;
	}
	
	public static Sticker getInstanceFromIntent(Intent intent, ContentResolver cr)
	{
		String textId = intent.getStringExtra(Sticker.ID);
		long id = (textId == null) ? 0 : Long.parseLong(textId);
		return new Sticker(id,cr);
	}

	public static Cursor getStickerCursor(ContentResolver cr)
	{
		return getStickerCursor(cr, null);
	}
	
	public static Cursor getStickerCursor(ContentResolver cr, String where)
	{
		return cr.query(Sticker.CONTENT_URI, Sticker.projection, where, null, "_id ASC");
	}
	
	public Sticker(long id, ContentResolver cr) {
		super();
		resolver = cr;
		if (id > 0) {
			Cursor c = Sticker.getStickerCursor(resolver, Sticker.ID + " = " + id);
			if (c.moveToFirst()) {
				subject = c.getString(c.getColumnIndex(SUBJECT));
				text = c.getString(c.getColumnIndex(TEXT));
			c.close();
			}
		}
		this.id = id;
	}
	
	public Sticker(Cursor c)
	{
		if (!c.isBeforeFirst() && !c.isAfterLast())
		{
			subject = c.getString(c.getColumnIndex(SUBJECT));
			text = c.getString(c.getColumnIndex(TEXT));
		}
	}

	public long getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String sub) {
		subject = sub;
		isChanged =true;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String t)
	{
		text = t;
		isChanged = true;
	}
	
	public int getNotificationId()
	{
		return notification_id;
	}

	public boolean hasChanged()
	{
		return isChanged;
	}
	
	public String toString()
	{
		return subject;
	}

	public void save()
	{
		ContentValues cv = new ContentValues();
		cv.put(SUBJECT,subject);
		cv.put(TEXT, text);
		resolver.update(CONTENT_URI, cv, ID + " = " + id, null);
	}
}
