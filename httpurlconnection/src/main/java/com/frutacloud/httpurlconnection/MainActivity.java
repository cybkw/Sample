package com.frutacloud.httpurlconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_get).setOnClickListener(this);
        findViewById(R.id.btn_post).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:

                new Thread(new MyRunnable()).start();
                break;
            case R.id.btn_post:
                new Thread(new MyRunnable2()).start();
                break;
        }
    }

    private static class MyRunnable implements  Runnable{
        @Override
        public void run() {
            doget();
        }
    }

    private static class MyRunnable2 implements  Runnable{
        @Override
        public void run() {
            dopost();
        }
    }

    private static String path = "http://etdemo.cloudinward.com/ws/packagings/";

    private static void doget() {

        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();
            String msg = conn.getResponseMessage();
            String content = inputStreamToString(conn.getInputStream(), "UTF-8");
            Log.i(TAG, "code:" + code + ",message=" + msg + ",content=" + content);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String post = "http://etdemo.cloudinward.com/ws/token/login";

    private static void dopost() {

        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");


            // 设置编码格式
            //conn.setRequestProperty("Charset", "UTF-8");
            // 传递自定义参数
            //conn.setRequestProperty("MyProperty", "this is me!");

            String body="username:www";
            //设置conn可以写请求的内容
            conn.setDoOutput(true);
            conn.getOutputStream().write(body.getBytes());
            int code = conn.getResponseCode();
            String msg = conn.getResponseMessage();
            String content = inputStreamToString(conn.getInputStream(), "UTF-8");

            Log.i(TAG, "code:" + code + ",message=" + msg + ",content=" + content);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String inputStreamToString(InputStream in, String charset) throws IOException {
        StringBuffer out = new StringBuffer();
        BufferedReader input = new BufferedReader(new InputStreamReader(in, charset));
        String s;
        while ((s = input.readLine()) != null) {
            out.append(s);
        }
        return out.toString();
    }
}
