package com.putasticker;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.putasticker.providers.Sticker;

public class StickerListActivity extends Activity {
	public static boolean isRunning = false;
	private ListView lview;
	private Cursor cursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sticker_list);
		setTitle("Stickers list");
		lview = (ListView) findViewById(R.id.stickerListView);
		
		Uri uri = Sticker.CONTENT_URI;
		ContentResolver cr = getContentResolver();
		
		cursor = cr.query(uri, Sticker.projection, null, null, "_id ASC");
		
		String[] fromColumns = {Sticker.SUBJECT};
		int[] toIds = {R.id.stickerSubject};
		SimpleCursorAdapter sadapter = new SimpleCursorAdapter(this, R.layout.listview_layout, cursor, fromColumns, toIds);
		
		lview.setAdapter(sadapter);
		lview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getApplicationContext(),SavedStickActivity.class);
				intent.putExtra(Sticker.ID,Long.toString(arg3));
				startActivity(intent);
			}
			
		});
		isRunning= true;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		isRunning = false;
		cursor.close();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sticker_list, menu);
		return true;
	}

	public void activateSticker(View view) {
		Intent intent = new Intent(this, StickActivity.class);
		startActivity(intent);
	}

}
