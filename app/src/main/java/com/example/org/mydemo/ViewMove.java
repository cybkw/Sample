package com.example.org.mydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class ViewMove extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_move);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("View触摸移动");
        setSupportActionBar(toolbar);

        final Button button = (Button) findViewById(R.id.button);
        final Button fab = (Button) findViewById(R.id.fab);

        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                int lastX = 0;
                int lastY = 0;
                Log.d("cy","-----X="+x+",Y="+y);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //记录触摸点坐标
                        lastX = x;
                        lastY = y;
                        Log.d("cy","DOWN----lastX="+lastX+",lastY="+lastY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //计算偏移量
                        int offsetX = x - lastX;
                        int offsetY = y - lastY;
                        // fab.layout(fab.getLeft()+offsetX,fab.getTop()+offsetY,fab.getRight()+offsetX,fab.getBottom()+offsetY);
                        fab.offsetLeftAndRight(offsetX);  //此方法, 与layout()方法相同效果
                        fab.offsetTopAndBottom(offsetY);
                        fab.invalidate();
                        Log.d("cy","ACTION_MOVE----offsetX="+offsetX+",offsetY="+offsetY);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;

                }
                return false;
            }
        });


        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x= (int) event.getX();
                int y= (int) event.getY();
                int lastX=0;
                int lastY=0;
                Log.d("cy","-----X="+x+",Y="+y);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX=x;
                        lastY=y;
                        Log.d("cy","DOWN----lastX="+lastX+",lastY="+lastY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int offsetX=x-lastX;
                        int offsetY=y-lastY;
                        Log.d("cy","ACTION_MOVE----offsetX="+offsetX+",offsetY="+offsetY);

                        break;
                    case MotionEvent.ACTION_UP:
                        lastX=x;
                        lastY=y;
                        Log.d("cy","ACTION_UP----offsetX="+lastX+",offsetY="+lastY);
                        break;
                }
                return false;
            }
        });
    }

}
