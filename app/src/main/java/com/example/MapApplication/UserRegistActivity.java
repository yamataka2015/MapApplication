package com.example.MapApplication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
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
import android.widget.Toast;

public class UserRegistActivity extends ActionBarActivity {

	private UserRegistTask mUserRegistTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_regist);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_regist, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}

	public void onClickBtnRegistration(View v)
	{
		// INSERT into DB and set a marker on the map.
    	EditText eText_mailaddress = (EditText)(findViewById(R.id.editText_mailaddress));
    	EditText eText_password = (EditText)(findViewById(R.id.EditText_password));

    	int userid;

    	//TODO:必須項目空欄時例外処理

    	String[] strData = new String[2];
    	strData[0] = eText_mailaddress.getText().toString();
    	strData[1] = eText_password.getText().toString();
        mUserRegistTask = new UserRegistTask(UserRegistActivity.this, getString(R.string.URL_UserRegist));
        mUserRegistTask.execute(strData);

        try
        {
        	JSONObject jsonObj= mUserRegistTask.get();
        	userid = jsonObj.getInt("userid");
			if(userid == -1)
			{
				// Show error message
				String strErr = jsonObj.getString("message");
				Toast toast = Toast.makeText(this, strErr, Toast.LENGTH_LONG);
				toast.show();
				return;
			}
        	// Save the userid in file
        	byte[] byteUserid = (String.valueOf(userid)).getBytes();
        	OutputStream out = null;
        	out = this.openFileOutput(getString(R.string.filename), MODE_PRIVATE);
        	out.write(byteUserid, 0, byteUserid.length);

            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
    		//TODO:string.xml化
    		Toast toast = Toast.makeText(this, "ユーザ登録が完了しました", Toast.LENGTH_LONG);
    		toast.show();
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
        catch(FileNotFoundException e)
        {
        	e.printStackTrace();
        }
        catch(IOException e)
        {
        	e.printStackTrace();
        }
        finish();
	}

}
