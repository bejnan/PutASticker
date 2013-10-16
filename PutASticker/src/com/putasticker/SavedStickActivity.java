package com.putasticker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.putasticker.providers.Sticker;

public class SavedStickActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		int id = intent.getIntExtra(Sticker.ID, 0);
		setContentView(R.layout.activity_saved_stick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.save_stick, menu);
		return true;
	}
	
	public void closeSticker(View view)
	{
		Intent intent = new Intent(this,StickerListActivity.class);
		startActivity(intent);
	}

}
