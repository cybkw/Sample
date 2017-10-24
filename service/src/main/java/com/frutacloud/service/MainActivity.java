package com.frutacloud.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private Button start;
    private Button stop;
    private Button post;
    private Button cancle;
    private Button bind;
    private Button unbind;
    private TextView tv_text;

    private MessageService.MessageBinder messageBinder = null;


    public ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messageBinder = (MessageService.MessageBinder) service;
            MessageService service1 = messageBinder.getService();

            service1.setOnDataCallback(new MessageService.OnDataCallback() {
                @Override
                public void onCallback(String data) {
                    stringBuffer.append(data);
                    Message msg = Message.obtain();
                    msg.obj = stringBuffer;
                    handler.sendMessage(msg);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            String name = savedInstanceState.getString("name");
            Log.i(TAG, "name=" + name);
        }

        findViewById(R.id.button_start).setOnClickListener(this);
        findViewById(R.id.button_stop).setOnClickListener(this);
        findViewById(R.id.btn_bind).setOnClickListener(this);
        findViewById(R.id.btn_cancle).setOnClickListener(this);
        findViewById(R.id.btn_post).setOnClickListener(this);
        findViewById(R.id.btn_unbind).setOnClickListener(this);
        findViewById(R.id.btn_show).setOnClickListener(this);
        findViewById(R.id.btn_jump).setOnClickListener(this);
        tv_text = (TextView) findViewById(R.id.tv_text);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        String name = "bkw";
        outState.putString("name", name);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private StringBuffer stringBuffer = new StringBuffer();


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_post:

                intent = new Intent(this, MessageService.class);
                startService(intent);
                break;
            case R.id.btn_cancle:
                intent = new Intent(this, MessageService.class);
                stopService(intent);
                break;
            case R.id.btn_bind:
                intent = new Intent(this, MessageService.class);
                bindService(intent, serviceConnection, BIND_AUTO_CREATE);


                break;
            case R.id.btn_show:

                break;
            case R.id.btn_unbind:
                unbindService(serviceConnection);
                break;
            case R.id.button_start:
                Intent i = new Intent(this, ServiceSample.class);
                startService(i);
                break;
            case R.id.button_stop:
                Intent in = new Intent(this, ServiceSample.class);
                stopService(in);
            case R.id.btn_jump:
                Intent intent2 = new Intent(this, JumpActivity.class);
                startActivity(intent2);
                break;
        }

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_text.setText(msg.obj.toString());
        }
    };
}
