package com.putasticker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class CongratulationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_congratulation);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.congratulation, menu);
		TextView tv = (TextView) findViewById(R.id.stickerSubject);
		tv.setText("Tytuł stickera");
		return true;
	}
	
	public void closeMessage(View view)
	{
		setResult(RESULT_OK);
		finish();
	}

}
