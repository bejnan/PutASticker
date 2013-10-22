package com.putasticker;

import java.util.List;

import com.putasticker.providers.Sticker;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class StickerDecideActivity extends Activity {

	final int DELETE_REQUEST = 1;
	private TextView subject;
	private Sticker sticker;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sticker_decide_activity);
		
		Intent intent = getIntent();
		long id = Long.parseLong(intent.getStringExtra(Sticker.ID));
		sticker = new Sticker(id, getContentResolver());
		subject = (TextView) findViewById(R.id.stickerDecideSubject);
		subject.setText(sticker.getSubject());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sticker_decide_activiti, menu);
		return true;
	}
	
	public void closeSticker(View view)
	{
		finish();
	}
	
	public void facebookShare(View view)
	{
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, "URLyouWantToShare");
		PackageManager pm = view.getContext().getPackageManager();
		List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
		for (final ResolveInfo app : activityList) {
		    if ((app.activityInfo.name).contains("facebook")) {
		        final ActivityInfo activity = app.activityInfo;
		        final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
		        shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		        shareIntent.setComponent(name);
		        view.getContext().startActivity(shareIntent);
		        break;
		   }
		}
	}
	
	public void messageShare(View view)
	{
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, "TestText");
		startActivity(shareIntent);
	}
	
	public void deleteMessage(View view)
	{
		getContentResolver().delete(Sticker.CONTENT_URI, "_id=" + sticker.getId(), null);
		Intent congratulations = new Intent(this,CongratulationActivity.class);
		startActivityForResult(congratulations, DELETE_REQUEST);
		//TODO how for result?
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		
	}

}
