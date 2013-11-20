package com.putasticker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.putasticker.providers.Sticker;

public class SavedStickActivity extends Activity {

	private final int DECIDE = 1;

	private Sticker sticker;
	private EditText subject;
	private EditText text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_stick);

		sticker = Sticker.getInstanceFromIntent(getIntent(),
				getContentResolver());
		subject = (EditText) findViewById(R.id.editSubject);
		subject.setText(sticker.getSubject());
		text = (EditText) findViewById(R.id.editText);
		text.setText(sticker.getText());
		
		setTitle("Sticker " + sticker.getId());
	}

	@Override
	protected void onPause() {
		if (subject.getText().toString() != sticker.getSubject()) {
			sticker.setSubject(subject.getText().toString());
		}
		if (subject.getText().toString() != sticker.getText()) {
			sticker.setText(text.getText().toString());
		}
		if (sticker.hasChanged()) {
			sticker.save();
		}
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.save_stick, menu);
		return true;
	}

	public void closeSticker(View view) {
		if (!StickerListActivity.isRunning) {
			Intent intent = new Intent(this, StickerListActivity.class);
			startActivity(intent);
		}
		finish();
	}

	public void decide(View view) {
		Intent intent = new Intent(this, StickerDecideActivity.class);
		intent.putExtra(Sticker.ID, Long.toString(sticker.getId()));
		startActivityForResult(intent, DECIDE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		congratulate(resultCode);
		if (resultCode == RESULT_FIRST_USER) {
			finish();
		}
	}

	private void congratulate(int resultCode) {
		Intent intent = new Intent(this, CongratulationActivity.class);
		intent.putExtra(Sticker.SUBJECT, sticker.getSubject());
		intent.putExtra(StickerDecideActivity.RESULT,
				Integer.toString(resultCode));
		startActivity(intent);
	}
}
