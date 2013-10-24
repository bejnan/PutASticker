package com.putasticker;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.putasticker.providers.Sticker;

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

	public void saveSticker(View view) {
		Uri newUri;
		ContentValues newValues = new ContentValues();

		EditText editSubject = (EditText) findViewById(R.id.editSubject);
		newValues.put(Sticker.SUBJECT, editSubject.getText().toString());

		EditText editText = (EditText) findViewById(R.id.editText);
		newValues.put(Sticker.TEXT, editText.getText().toString());

		newUri = getContentResolver().insert(Sticker.CONTENT_URI, newValues);

		Intent intent = new Intent(this, SavedStickActivity.class);
		intent.putExtra(Sticker.ID, newUri.getLastPathSegment());
		startActivity(intent);
	}

	public void closeSticker(View view) {
		if (StickerListActivity.isRunning)
			finish();
		else {
			Intent intent = new Intent(this, StickerListActivity.class);
			startActivity(intent);
		}
	}

}
