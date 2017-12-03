package com.frutacloud.socketsample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * socket客户端简单示例
 */

public class SocketClientAc extends AppCompatActivity implements View.OnClickListener, SocketConcat.ISocketView {
    /**
     * 接收服务器消息 变量
     */
    // 输入流对象
    InputStream is;
    // 输入流读取器对象
    InputStreamReader isr;
    BufferedReader br;
    // 接收服务器发送过来的消息
    String response;
    /**
     * 发送消息到服务器 变量
     */
    // 输出流对象
    OutputStream outputStream;
    ProgressDialog pd;
    // 主线程Handler
    // 用于将从服务器获取的消息显示出来
    private Handler mMainHandler;
    // Socket变量
    private Socket mSocket;
    // 线程池
    // 为了方便展示,此处直接采用线程池进行线程管理,而没有一个个开线程
    private ExecutorService mThreadPool;
    /**
     * 按钮 变量
     */

    // 连接 断开连接 发送数据到服务器 的按钮变量
    private Button btnConnect, btnDisconnect, btnSend;
    // 显示接收服务器消息 按钮
    private TextView receive_message;
    // 输入需要发送的消息 输入框
    private EditText mEdit;
    private SocketPersent mSocketPersent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socketclient);

        initView();
    }

    private void initView() {

        btnConnect = (Button) findViewById(R.id.btn_connect);
        btnDisconnect = (Button) findViewById(R.id.btn_disconnect);
        btnSend = (Button) findViewById(R.id.btn_send);
        receive_message = (TextView) findViewById(R.id.receive_message);
        mEdit = (EditText) findViewById(R.id.edit);

        btnSend.setOnClickListener(this);
        btnDisconnect.setOnClickListener(this);
        btnConnect.setOnClickListener(this);

        mSocketPersent = new SocketPersent(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_connect:
                mSocketPersent.connect();
                break;
            case R.id.btn_disconnect:
                mSocketPersent.disconnect();
                break;
            case R.id.btn_send:
                mSocketPersent.send();
                break;
        }
    }

    @Override
    public void showData(String str) {
        receive_message.setText(str);
    }

    @Override
    public void showLoading() {
        pd = new ProgressDialog(this);
        pd.setMessage("loading...");
        pd.show();
    }

    @Override
    public void dismissLoading() {
        if (pd != null & pd.isShowing()) {
            pd.dismiss();
        }
    }

    @Override
    public String getMessage() {
        return mEdit.getText().toString().trim();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
