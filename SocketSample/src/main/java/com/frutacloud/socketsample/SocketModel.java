package com.frutacloud.socketsample;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/12/1.
 */

public class SocketModel implements SocketConcat.ISocketModel {

    private static final String TAG = "SocketModel";
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
    // Socket变量
    private Socket mSocket;
    // 线程池
    // 为了方便展示,此处直接采用线程池进行线程管理,而没有一个个开线程
    private ExecutorService mThreadPool;
    private ClientCallBack<String> callback;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.arg1) {
                case 0:
                    callback.onSuccesful(msg.obj.toString());
                    break;
                case -1:
                    callback.onSuccesful(msg.obj.toString());
                    break;
            }
        }
    };

    @Override
    public void connect(final String ip, final int port, final ClientCallBack<String> callBack) {
        this.callback = callBack;
        mThreadPool = Executors.newCachedThreadPool();
        System.out.print("connect()");

        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = new Socket(ip, port);
                    System.out.print(mSocket.isConnected());
                    if (mSocket.isConnected()) {
                        //连接成功接收服务器数据
                        is = mSocket.getInputStream();
                        isr = new InputStreamReader(is, "UTF-8");

                        br = new BufferedReader(isr);

                        StringBuffer stb = new StringBuffer();
                        String result;
                        while ((result = br.readLine()) != null) {
                            stb.append(result);
                        }
                        response = stb.toString();


                        Message msg = new Message();
                        msg.arg1 = 0;
                        msg.obj = response;
                        mHandler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.arg1 = -1;
                        msg.obj = "连接失败";
                        mHandler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void disconnect() {
        if (mSocket.isConnected()) {
            try {
                outputStream.close();

                br.close();

                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void send(final String str) {
        Log.i(TAG, str);
        mThreadPool = Executors.newCachedThreadPool();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    outputStream = mSocket.getOutputStream();

                    outputStream.write(str.getBytes("utf-8"));  //写入数据

                    outputStream.flush(); //发送数据
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
