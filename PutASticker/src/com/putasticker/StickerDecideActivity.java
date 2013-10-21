package com.putasticker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class StickerDecideActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sticker_decide_activity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sticker_decide_activiti, menu);
		return true;
	}
	
	public void closeSticker(View view)
	{
		Intent intent = new Intent(this,StickerListActivity.class);
		startActivity(intent);
	}
	
	public void facebookShare(View view)
	{
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, "URLyouWantToShare");
		startActivity(Intent.createChooser(shareIntent, "Share..."));
	}

}
