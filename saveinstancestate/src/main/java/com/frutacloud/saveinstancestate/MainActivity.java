package com.frutacloud.saveinstancestate;

import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private EditText et_username;
    private EditText et_password;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* if (savedInstanceState != null) {
            Log.i(TAG, "GET_savedInstanceState");

            et_username.setText(savedInstanceState.getString("name"));
           // et_password.setText(savedInstanceState.getString("pass"));
        }*/
        initView();
    }

    private String username;
    private String password;

    private void initView() {
        btn_login = (Button) findViewById(R.id.btn_login);
        et_password = (EditText) findViewById(R.id.et_password);
        et_username = (EditText) findViewById(R.id.et_username);

        btn_login.setOnClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle persistableBundle) {
        super.onSaveInstanceState(outState, persistableBundle);
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        outState.putString("name", username);
       // outState.putString("pass", password);
        Log.i(TAG, "onSaveInstanceState...");
        //outState.putBundle("data",bundle);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause....");
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        ed.putString("name", username);
        ed.putString("pass", password);
        ed.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        String name = sp.getString("name", null);
        String pass = sp.getString("pass",null);
        Log.i(TAG, "onResume...."+name);

        if ( name!=null){
            et_username.setText(name);
        }
        if ( pass!=null){
            et_password.setText(pass);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:

                break;
        }
    }
}
