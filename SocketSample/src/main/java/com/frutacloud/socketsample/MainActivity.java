package com.frutacloud.socketsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    String ip = "192.168.43.73";
    int port = 8080;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        socketConnection();
    }

    private void socketConnection() {
        try {
            Socket socket = new Socket(ip, port);

            InputStream is = socket.getInputStream(); //接收服务器的数据流
            // 步骤2：创建输入流读取器对象 并传入输入流对象
            // 该对象作用：获取服务器返回的数据
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            // 步骤3：通过输入流读取器对象 接收服务器发送过来的数据
            br.readLine();
            String respons = br.readLine();

            //操作2：发送数据 到 服务器 -->

            // 步骤1：从Socket 获得输出流对象OutputStream
            // 该对象作用：发送数据
            OutputStream outputStream = socket.getOutputStream();

            // 步骤2：写入需要发送的数据到输出流对象中
            outputStream.write(("Carson_Ho" + "\n").getBytes("utf-8"));
            // 特别注意：数据的结尾加上换行符才可让服务器端的readline()停止阻塞

            // 步骤3：发送数据到服务端
            outputStream.flush();


            //  <-- 步骤3：断开客户端 & 服务器 连接-->

            outputStream.close();
            // 断开 客户端发送到服务器 的连接，即关闭输出流对象OutputStream

            br.close();
            // 断开 服务器发送到客户端 的连接，即关闭输入流读取器对象BufferedReader

            socket.close();
            // 最终关闭整个Socket连接

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
