package com.example.MapApplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;



//test masuda

import java.io.FileNotFoundException;
import java.io.InputStream;
//comment mori
// test comment by masaki-n 001
//test madoka


public class MainActivity extends AppCompatActivity
// test comment by masaki-n 002
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); //nakamura comment

		// Move to UserRegistActivity when first run
		InputStream in = null;

		try
		{
			in = openFileInput(getString(R.string.filename));

		}
		catch(FileNotFoundException e)
		{
			Intent intent = new Intent(this,UserRegistActivity.class);
			startActivityForResult(intent, 111);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_version) {
			VersionDialogFragment diag = new VersionDialogFragment();
			diag.show(getSupportFragmentManager(), "Version");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent){
        switch (requestCode)
        {
        case 111:
        	if(resultCode == RESULT_OK)
        	{
        		// Nop
        	}
        	else if(resultCode == RESULT_CANCELED)
        	{
        		// Finish application when the user registration Cancelled
        		finish();
        	}
        	break;
        default:
        	break;
        }
	}

	// 「情報表示(地図)」ボタン押下時イベント
	public void onClickBtnMap(View v)
	{
        Intent intent = new Intent(this, MapTestActivity.class);
        startActivity(intent);
	}

	// 「各種ランキング」ボタン押下時イベント
	public void onClickBtnRanking(View v)
	{
        Intent intent = new Intent(this, SelectRankingActivity.class);
        startActivity(intent);
	}

	public void onClickBtnVersion(View v)
	{
		VersionDialogFragment diag = new VersionDialogFragment();
		diag.show(getSupportFragmentManager(), "Version");
	}

}
