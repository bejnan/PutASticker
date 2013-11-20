package com.putasticker;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.putasticker.providers.Sticker;

public class CongratulationActivity extends Activity {

	private int resultCode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_congratulation);
		setTitle("Congratulations!!");
		
		String subject = getIntent().getStringExtra(Sticker.SUBJECT);
		String result= getIntent().getStringExtra(StickerDecideActivity.RESULT);
		resultCode = Integer.parseInt(result);
		TextView tv = (TextView) findViewById(R.id.stickerSubject);
		Button button = (Button) findViewById(R.id.backButton);
		if (resultCode > 0)
			button.setText("Go back to the stickers");
		tv.setText(subject);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.congratulation, menu);
		return true;
	}
	
	public void closeMessage(View view)
	{
		finish();
	}

}
