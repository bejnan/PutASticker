package com.putasticker.providers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class Sticker implements BaseColumns {

	private int id;
	private String subject;
	private String text;

	public Sticker(int id, ContentResolver cr) {
		super();
		if (id > 0) {
			Cursor c = cr.query(CONTENT_URI, projection, ID + " = " + id, null,
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

	public int getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public String getText() {
		return text;
	}
	
	public String toString()
	{
		return subject;
	}

	public static final Uri CONTENT_URI = Uri.parse("content://"
			+ StickerContentProvider.Authority + "/sticker");
	public static final String CONTENT_TYPE = "com.putasticker.providers.sticker";
	public static final String ID = "_id";
	public static final String SUBJECT = "subject";
	public static final String TEXT = "text";
	public static final String[] projection = { ID, SUBJECT, TEXT };
}
