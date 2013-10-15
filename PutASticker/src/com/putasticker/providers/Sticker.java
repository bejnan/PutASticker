package com.putasticker.providers;

import android.net.Uri;
import android.provider.BaseColumns;

public class Sticker implements BaseColumns{
	private int id;
	private String subject;
	private String text;
	
	public static final Uri CONTENT_URI = Uri.parse("content://"
            + StickerContentProvider.Authority + "/sticker");
	public static final String CONTENT_TYPE = "com.putasticker.providers.sticker";
	public static final String ID = "_id";
	public static final String SUBJECT = "subject";
	public static final String TEXT = "text";
}
