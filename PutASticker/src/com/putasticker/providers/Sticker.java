package com.putasticker.providers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class Sticker implements BaseColumns {

	private long id;
	private String subject;
	private String text;
	private boolean isChanged;
	private ContentResolver resolver;
	
	public Sticker(long id, ContentResolver cr) {
		super();
		resolver = cr;
		if (id > 0) {
			Cursor c = resolver.query(CONTENT_URI, projection, ID + " = " + id, null,
					ID + " ASC");
			if (c.moveToFirst()) {
				subject = c.getString(c.getColumnIndex(SUBJECT));
				text = c.getString(c.getColumnIndex(TEXT));
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
	
	public void setText(String t)
	{
		text = t;
		isChanged = true;
	}

	public String getText() {
		return text;
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
	public static final Uri CONTENT_URI = Uri.parse("content://"
			+ StickerContentProvider.Authority + "/sticker");
	public static final String CONTENT_TYPE = "com.putasticker.providers.sticker";
	public static final String ID = "_id";
	public static final String SUBJECT = "subject";
	public static final String TEXT = "text";
	public static final String[] projection = { ID, SUBJECT, TEXT };

}
