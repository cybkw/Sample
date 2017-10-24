package com.frutacloud.handler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.StaticLayout;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private static HandlerThread handlerThread = null;

    private static Handler handler = null;

    private static Handler workHandler=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.mainPost).setOnClickListener(this);
        findViewById(R.id.threadPost).setOnClickListener(this);


        handlerThread = new HandlerThread("running");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.i(TAG, "msg=" + msg.what + "  线程： " + Thread.currentThread().getName());
            }
        };




        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                workHandler=  new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Log.i(TAG, "msg=" + msg.what + "  线程： " + Thread.currentThread().getName());
                    }
                };
                Looper.loop();
            }
        }).start();
        //new Thread(new MyRunnable()).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mainPost:
                //在主线程给handler发送消息
                //handler.sendEmptyMessage(1);
                //在主线程给子线程发送消息
                workHandler.sendEmptyMessage(1);
                break;
            case R.id.threadPost:
                new Thread(new MyRunnable()).start();
                break;
        }
    }

    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
            //在子线程给handler发送数据
            handler.sendEmptyMessage(2);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }
}
