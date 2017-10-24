package com.frutacloud.sqlite;

import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.MessageQueue;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity" ;
    TextView tv_text;
    MyHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_text= (TextView) findViewById(R.id.tv_text);

        handler=new MyHandler(this);
        // post
        /*handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tv_text.setText("我承认世界对我有点诱惑啊");
            }
        });*/

        //sendMessage;
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i(TAG,"runnable....");
                Message msg=new Message();
                msg.arg1=1;
                msg.obj="失去资格怎么留得下";
                handler.sendMessage(msg);
            }
        }).start();*/

        //runOnUiThread()
       /* runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i(TAG,"runnable....");
                tv_text.setText("不该像叛逆期的孩子拼命耍");
            }
        });*/

        //View.post()
        tv_text.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                tv_text.setText("我接受了惩罚");

            }
        });

    }

    private class MyHandler extends Handler{
        private WeakReference<MainActivity> ref;

        public MyHandler(MainActivity activity){
            ref=new WeakReference<MainActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String text=msg.obj.toString();
            switch (msg.arg1){
                case 1:
                    tv_text.setText(text);
                    break;
            }
        }
    }
}
