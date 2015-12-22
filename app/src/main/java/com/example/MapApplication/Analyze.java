package com.example.MapApplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.content.Context;



/**
 * Created by MADOKA on 2015/12/08.
 */
public class Analyze extends AppCompatActivity {
    private Button bt;
    private float xc = 0.0f;
    private float yc =0.0f;
    private int like;
    private int dislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);

        Intent intent = getIntent();
        like = intent.getIntExtra("LIKECOUNT", 0);
        dislike = intent.getIntExtra("DISLIKECOUNT", 0);


        TestView testView = new TestView(this);
        setContentView(testView);


    }

    class TestView extends View{
        Paint paint;

        public TestView(Context context){
            super(context);
            paint = new Paint();
        }

        protected void onDraw(Canvas canvas){


            // Canvas 中心点
            xc = canvas.getWidth()/2;
            yc = canvas.getHeight()/2;


            int a = like + dislike;

            if(a == 0)  return;
            double b = (double)like/a;



            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(xc - 500, yc - 200, xc + 500, yc + 200, paint);

            paint.setColor(Color.RED);
            canvas.drawRect(xc - 500, yc - 200, (xc - 500) + 1000 * (float) b, yc + 200, paint);
            paint.setColor(Color.RED);
            paint.setTextSize(62);
            canvas.drawText("" + (int) (b * 100) + "%", xc - 480, yc + 250, paint);

            paint.setColor(Color.BLUE);
            paint.setTextSize(62);
            canvas.drawText("" + (int) (100-b * 100) + "%", xc + 400, yc+250, paint);

        }

    }

}