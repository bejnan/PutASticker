package com.putasticker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.putasticker.providers.Sticker;

public class PushActivity extends Activity {

	private long stickerId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_push);
		Sticker sticker = Sticker.getInstanceFromIntent(getIntent(), getContentResolver());
		stickerId = sticker.getId();
		setView(sticker);
	}
	
	private void setView(Sticker sticker)
	{
		TextView tv = (TextView) findViewById(R.id.push_message);
		String text = tv.getText().toString();
		tv.setText(String.format(text, sticker.getSubject()));
	}
	
	public void decideButton(View view)
	{
		if (stickerId > 0)
		{
			changeViewToSavedSticker(stickerId);
		}
		finish();
	}
	
	private void changeViewToSavedSticker(long stickerId)
	{
		Intent intent = new Intent(this, SavedStickActivity.class);
		intent.putExtra(Sticker.ID, Long.toString(stickerId));
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.push, menu);
		return true;
	}

}
