package com.putasticker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.putasticker.providers.Sticker;

public class NewStickerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stick);
		setTitle("New sticker");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stick, menu);
		return true;
	}

	public void saveSticker(View view) {
		
		EditText editSubject = (EditText) findViewById(R.id.editSubject);
		EditText editText = (EditText) findViewById(R.id.editText);
		String subject = editSubject.getText().toString();
		String text = editText.getText().toString();
		
		long stickerId = Sticker.createStricker(subject, text, getContentResolver(), this);
		changeViewToSavedSticker(stickerId);
		finish();
	}
	
	private void changeViewToSavedSticker(long stickerId)
	{
		Intent intent = new Intent(this, SavedStickActivity.class);
		intent.putExtra(Sticker.ID, Long.toString(stickerId));
		startActivity(intent);
	}

	public void closeSticker(View view) {
		if (!StickerListActivity.isRunning)
		{
			Intent intent = new Intent(this, StickerListActivity.class);
			startActivity(intent);
		}
		finish();
	}
}
