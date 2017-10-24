package com.frutacloud.subthread_updateui;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity" ;
    private TextView tv_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_text= (TextView) findViewById(R.id.tv_text);

        new Thread(new Runnable() {
            @Override
            public void run() {
                tv_text.setText("ui Thread");
                Log.i(TAG,"Thread....");
            }
        }).start();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume....");
    }
}
