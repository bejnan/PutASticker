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
import android.widget.CursorAdapter;
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
		isRunning= true;
		setView();			
	}
	
	private void setView()
	{
		setTitle("Stickers list");
		setListView();
	}
	
	private void setListView()
	{
		lview = (ListView) findViewById(R.id.stickerListView);
		
		CursorAdapter adapter = createCursorAdapter();
		lview.setAdapter(adapter);
		lview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getApplicationContext(),SavedStickActivity.class);
				intent.putExtra(Sticker.ID,Long.toString(arg3));
				startActivity(intent);
			}
			
		});

	}
	
	private CursorAdapter createCursorAdapter()
	{
		cursor = Sticker.getStickerCursor(getContentResolver()); 
		String[] fromColumns = {Sticker.SUBJECT};
		int[] toIds = {R.id.stickerSubject};
		return  new SimpleCursorAdapter(this, R.layout.listview_layout, cursor, fromColumns, toIds);
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
		Intent intent = new Intent(this, NewStickerActivity.class);
		startActivity(intent);
	}

}
