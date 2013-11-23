package com.putasticker.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class StickerContentProvider extends ContentProvider {

	public static final String Authority = "com.putasticker.providers.stickercontentprovider";

	private static final String TAG = "StickerContentProvider";

	private static final String DB_NAME = "stricker.db";

	private static final int DB_VERSION = 1;

	private static final String StickerTableName = "sticker";

	private static final UriMatcher stickerUriMatcher;

	private static final int STICKERS = 1;
	private static final int STICKERS_ID = 2;

	private static class DBHelper extends SQLiteOpenHelper {
		private static final String CreateQuery = "CREATE TABLE " + StickerTableName
				+ "(" + Sticker.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ Sticker.SUBJECT + " VARCHAR(255)," + Sticker.TEXT
				+ " LONGTEXT" + " );";

		public DBHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CreateQuery);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ". All data will be destroyed.");
			db.execSQL("DROP TABLE IF EXISTS " + StickerTableName);
			onCreate(db);
		}

	}

	private DBHelper dbHelper;

	static {
		stickerUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		stickerUriMatcher.addURI(Authority, StickerTableName, STICKERS);
		stickerUriMatcher.addURI(Authority, StickerTableName + "/#", STICKERS_ID);
	}
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		switch (stickerUriMatcher.match(uri)) {

		case STICKERS:
			break;
		case STICKERS_ID:
			selection = selection + Sticker.ID + " = "
					+ uri.getLastPathSegment();
			break;
		default:
			throw new IllegalArgumentException("Uknown URI: " + uri);
		}

		int count = db.delete(StickerTableName, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		if (stickerUriMatcher.match(uri) == STICKERS) {
			return Sticker.CONTENT_TYPE;
		}
		throw new IllegalArgumentException("Uknown URI: " + uri);
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if (stickerUriMatcher.match(uri) != STICKERS) {
			throw new IllegalArgumentException("Uknown URI: " + uri);
		}

		ContentValues newValues;
		if (values != null)
			newValues = values;
		else
			newValues = new ContentValues();

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowId = db.insert(StickerTableName, Sticker.TEXT, newValues);

		if (rowId == 0) {
			throw new SQLException("Failed to insert row into " + DB_NAME);
		}

		Uri insertedUri = ContentUris
				.withAppendedId(Sticker.CONTENT_URI, rowId);
		getContext().getContentResolver().notifyChange(insertedUri, null);
		return insertedUri;
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DBHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(StickerTableName);

		switch (stickerUriMatcher.match(uri)) {
		case STICKERS:
			break;
		case STICKERS_ID:
			selection = selection + Sticker.ID + " = "
					+ uri.getLastPathSegment();
			break;
		default:
			throw new IllegalArgumentException("Uknown URI: " + uri);
		}

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, sortOrder);

		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		if (stickerUriMatcher.match(uri) != STICKERS) {
			throw new IllegalArgumentException("Uknown URI: " + uri);
		}

		int count = db.update(StickerTableName, values, selection,
				selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
