package com.example.org.mydemo;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.org.mydemo.dialog.ErrorDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ErrorDialog.ErrorClickListener {

    private LinearLayout activity_main;

    private Button btn_showError;

    private ErrorDialog mErrorDialog = null;

    private static final String TAG = "MainActivity";

    private String str;

    @Override
    public String toString() {
        return "MainActivity{" +
                "str='" + str + '\'' +
                '}';
    }

    public void setStr(String str) {
        this.str = str;
    }

    private Button btn_move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mErrorDialog = new ErrorDialog(MainActivity.this);
        initView();
    }

    private void initView() {
        activity_main = (LinearLayout) findViewById(R.id.activity_main);
        btn_showError = (Button) findViewById(R.id.btn_showError);
        btn_showError.setOnClickListener(this);

        btn_move = (Button) findViewById(R.id.btn_move);
        btn_move.setOnClickListener(this);
        System.out.print(str);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_showError:
                mErrorDialog.showError(activity_main, "测试dialog", true);
                mErrorDialog.setOnErrorClickListener(this);
                break;
            case R.id.btn_move:
                startActivity(new Intent(this, ViewMove.class));
                break;
        }
    }

    @Override
    public void cancleClick() {
        Snackbar.make(findViewById(R.id.activity_main), "取消", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void confrimClick() {
        Snackbar.make(findViewById(R.id.activity_main), "确定", Snackbar.LENGTH_SHORT).show();
    }
}
