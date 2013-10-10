package com.putasticker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class StickActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stick, menu);
		return true;
	}
	
	public void saveSticker(View view)
	{
		Intent intent = new Intent(this,SavedStickActivity.class);
		startActivity(intent);
	}
	
	public void closeSticker(View view)
	{
		Intent intent = new Intent(this,StickerListActivity.class);
		startActivity(intent);
	}

}
