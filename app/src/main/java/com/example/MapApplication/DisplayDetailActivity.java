package com.example.MapApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.ListView;
import android.widget.ArrayAdapter;

public class DisplayDetailActivity extends ActionBarActivity {

    private AddCommentTask mAddCommentTask;
    private SearchCommentTask mSearchCommentTask;
    private PlaceInfo mPlaceInfo;

	private Button bt;
	private int count1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_detail);

		TextView txtView_placenm = (TextView)findViewById(R.id.TextView_placename_value);
		TextView txtView_placenm_en = (TextView)findViewById(R.id.TextView_placename_en_value);
		TextView txtView_address = (TextView)findViewById(R.id.TextView_address_value);
		TextView txtView_phone = (TextView)findViewById(R.id.TextView_Telephone_value);
		TextView txtView_remark = (TextView)findViewById(R.id.TextView_Remark_value);

		Intent intent = getIntent();
		PlaceInfo pInfo = (PlaceInfo)intent.getSerializableExtra("placeInfo");
		txtView_placenm.setText(pInfo.mPlacename);
		txtView_placenm_en.setText(pInfo.mPlacename_en);
		txtView_address.setText(pInfo.mAddress);
		txtView_phone.setText(pInfo.mPhonenumber);
		txtView_remark.setText(pInfo.mRemark);
		mPlaceInfo = pInfo;

		bt = (Button)findViewById(R.id.btniine);  //ボタン
		bt.setWidth(400);//ボタンの高さと横幅a
		bt.setHeight(20);//a
        bt.setText("like");
	;




		// Get Comment List
		String strId = Integer.toString(pInfo.mPlaceId);
		mSearchCommentTask = new SearchCommentTask(this);
        mSearchCommentTask.execute(strId, getString(R.string.URL_SearchComment));

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}

	public void onClickBtnAddComment(View v)
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String[] strArray = new String[4];
		strArray[0] = Integer.toString(mPlaceInfo.mPlaceId);
		strArray[1] = "1"; //TODO:ユーザID（端末保持予定）
		strArray[2] = sdf.format(date);
		strArray[3] = ((EditText)findViewById(R.id.EditText_Comment)).getText().toString();
        mAddCommentTask = new AddCommentTask(DisplayDetailActivity.this, getString(R.string.URL_AddComment));
        mAddCommentTask.execute(strArray);
	}

	public void onClickBtniine(View v)
	{

		if(v == bt){
			count1++;
			bt.setText("like：" + count1);
		}


		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String[] strArray = new String[4];
		strArray[0] = Integer.toString(mPlaceInfo.mPlaceId);
		strArray[1] = "1"; //TODO:ユーザID（端末保持予定）
		strArray[2] = sdf.format(date);
		strArray[3] = "like";
		mAddCommentTask = new AddCommentTask(DisplayDetailActivity.this, getString(R.string.URL_AddComment));
		mAddCommentTask.execute(strArray);

		bt.setEnabled(false); //ボタンを無効
	}

}
