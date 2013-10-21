package com.putasticker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.putasticker.providers.Sticker;

public class SavedStickActivity extends Activity {
	
	private Sticker sticker;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_stick);
		
		Intent intent = getIntent();
		int id = Integer.parseInt(intent.getStringExtra(Sticker.ID));
		sticker = new Sticker(id, getContentResolver());
		EditText editSubject = (EditText) findViewById(R.id.editSubject);
		editSubject.setText(sticker.getSubject());
		EditText editText = (EditText) findViewById(R.id.editText);
		editText.setText(sticker.getText());
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
	
	public void decide(View view)
	{
		Intent intent = new Intent(this,StickerDecideActivity.class);
		intent.putExtra(Sticker.ID,Long.toString(sticker.getId()));
		startActivity(intent);
	}

}
