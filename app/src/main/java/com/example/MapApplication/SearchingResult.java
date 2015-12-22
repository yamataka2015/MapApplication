package com.example.MapApplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

public class SearchingResult extends AppCompatActivity {

    public int ClickCondition;
    public int ClickCondition2;

    public Double Lat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_result);

        Intent intent = getIntent();
        //Double Lat = intent.getDoubleExtra("m",0);
        //Double Lng = intent.getDoubleExtra("m2",0);
        String res = intent.getStringExtra("MPLACEINFOLIST");
        //Double res2 = intent.getDoubleExtra("test",100);
        int res2 = intent.getIntExtra("CLICKCONDITION",0);
        TextView txtView_SearchingResult = (TextView)findViewById(R.id.SearchingResult);
        txtView_SearchingResult.setText(res);//ここにmPlaceInfoListを表示させる
        TextView txtView_Test = (TextView)findViewById(R.id.test);
        txtView_Test.setText(String.valueOf(res2));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_searching_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

        public void onClickTxt(View v) {
        Intent intent = new Intent(this, MapTestActivity.class);
            ClickCondition2 = 1;

            Double Lat = getIntent().getDoubleExtra("m",0);
            Double Lng = getIntent().getDoubleExtra("m2",0);

            intent.putExtra("CLICKCONDITION2",ClickCondition2);
            intent.putExtra("mtest",Lat);
            intent.putExtra("m2test",Lng);
        startActivity(intent);
    }
}
