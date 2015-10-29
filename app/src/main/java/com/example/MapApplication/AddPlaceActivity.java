package com.example.MapApplication;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

public class AddPlaceActivity extends ActionBarActivity {

    private LatLng mLatLng;
    private AddPlaceTask mAddPlaceTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_place);

		Intent intent = getIntent();
		mLatLng = new LatLng(intent.getDoubleExtra("latitude",0), intent.getDoubleExtra("longitude", 0));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_place, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_version) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClickBtnAddPlace(View v){
		// INSERT into DB and set a marker on the map.
    	PlaceInfo pInfo = new PlaceInfo();
    	EditText eText_pName = (EditText)(findViewById(R.id.editText_placename));
    	EditText eText_pNameEn = (EditText)(findViewById(R.id.EditText_placename_en));
    	EditText eText_Address = (EditText)(findViewById(R.id.EditText_address));
    	EditText eText_Phone = (EditText)(findViewById(R.id.EditText_Telephone));
    	EditText eText_Note = (EditText)(findViewById(R.id.EditText_Note));

    	pInfo.mPlacename = eText_pName.getText().toString();
    	pInfo.mPlacename_en = eText_pNameEn.getText().toString();
    	pInfo.mAddress = eText_Address.getText().toString();
    	pInfo.mLatitude = mLatLng.latitude;
    	pInfo.mLongitude = mLatLng.longitude;
    	pInfo.mPhonenumber = eText_Phone.getText().toString();
    	pInfo.mRemark = eText_Note.getText().toString();
    	pInfo.mProjectCode = getString(R.string.ProjectCode);

    	//TODO:必須項目空欄時例外処理

        mAddPlaceTask = new AddPlaceTask(AddPlaceActivity.this, getString(R.string.URL_AddPlace));
        mAddPlaceTask.execute(pInfo);

        try
        {
        	JSONObject jsonObj= mAddPlaceTask.get();
        	pInfo.mPlaceId = jsonObj.getInt("placeid");
            Intent intent = new Intent();
            intent.putExtra("placeInfo", pInfo);
            setResult(RESULT_OK, intent);
        }
        catch(JSONException e)
        {
        	e.printStackTrace();
        }
        catch(InterruptedException e)
        {
        	e.printStackTrace();
        }
        catch(ExecutionException e)
        {
        	e.printStackTrace();
        }
        finish();
	}

}
