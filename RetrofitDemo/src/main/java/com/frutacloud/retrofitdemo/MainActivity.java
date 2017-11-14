package com.frutacloud.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private EditText et_edit;

    private TextView text;

    private Button btn_fy;

    private Button btn_post;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {


        text = (TextView) findViewById(R.id.text);
        btn_fy = (Button) findViewById(R.id.buttonFy);
        et_edit = (EditText) findViewById(R.id.edit_query);
        btn_post = (Button) findViewById(R.id.buttonPost);

        btn_fy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post();
            }
        });
    }

    private void post() {
        //步骤4:创建Retrofit对象
        retrofit = new Retrofit.Builder()
                .baseUrl(ICabcAPI.HOST)
                //.addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        //创建网络请求接口实例
        ICabcAPI api = retrofit.create(ICabcAPI.class);

        Call<ResponseBody> call = api.postCall("bkw", "123456");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String string = new String(response.body().bytes()); //获得返回的原始json字符串
                    Log.i(TAG, string);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "请求失败");
            }
        });
    }


    private void request() {

        String word = et_edit.getText().toString();

        if (word.equals("") || word == null) {
            et_edit.setHint("请输入要翻译的词");
            return;
        }

        //步骤4:创建Retrofit对象
        retrofit = new Retrofit.Builder()
                .baseUrl(ICabcAPI.URL)
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        // 步骤5:创建 网络请求接口 的实例
        ICabcAPI api = retrofit.create(ICabcAPI.class);

        //对 发送请求 进行封装
        Call<WordBean> call = api.getCall("fy", "auto", "zh", word);

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<WordBean>() {
            @Override
            public void onResponse(Call<WordBean> call, Response<WordBean> response) {

                String str = response.body().getContent().toString();
                text.setText(response.body().getContent().toString());
                Log.i(TAG, str);
            }

            @Override
            public void onFailure(Call<WordBean> call, Throwable t) {
                Log.i(TAG, "请求失败");
            }
        });
    }
}
