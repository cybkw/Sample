package com.example.org.mydemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.org.mydemo.R;


/**
 * Created by Administrator on 2017/3/9.
 */

public class ErrorDialog  {
    private Context context;
    private Dialog mDialog=null;

    private ErrorClickListener mErrorClickListenner=null;


    public ErrorDialog(Context context){
        this.context=context;
    }

    public void showError(LinearLayout linearLayout,String msg, boolean isCancle){

        mDialog=new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_error,linearLayout,false);
        TextView tv_msg= (TextView) view.findViewById(R.id.tv_msg);
        Button btn_cancle= (Button) view.findViewById(R.id.btn_cancle);
        Button btn_confrim= (Button) view.findViewById(R.id.btn_confrim);
        //TODO 待办事项
        mDialog.setContentView(view);
        mDialog.show();

        tv_msg.setText(msg);
        if (!isCancle){
            btn_cancle.setVisibility(View.GONE);
        }

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mErrorClickListenner.cancleClick();
            }
        });

        btn_confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mErrorClickListenner.confrimClick();
            }
        });
    }

    public void setOnErrorClickListener( ErrorClickListener mErrorClickListenner){
        this.mErrorClickListenner=mErrorClickListenner;
    }

    public  interface ErrorClickListener{
        void cancleClick();
        void confrimClick();
    }
}
