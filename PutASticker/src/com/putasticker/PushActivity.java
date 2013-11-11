package com.putasticker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.putasticker.providers.Sticker;

public class PushActivity extends Activity {

	private Sticker sticker;
	private TextView subject;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_push);
		Intent intent = getIntent();
		int id = Integer.parseInt(intent.getStringExtra(Sticker.ID));
		sticker = new Sticker(id, getContentResolver());
		subject = (TextView) findViewById(R.id.push_text);
		subject.setText(sticker.getSubject());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.push, menu);
		return true;
	}

}
