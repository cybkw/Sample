package com.frutacloud.collapstiontoolbarlayout;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity" ;
    Toolbar toolbar;

    RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("CollaptionToolBar");

        recycle= (RecyclerView) findViewById(R.id.recycleView);
        recycle.setLayoutManager(new LinearLayoutManager(this));


        List<String> datas=new ArrayList<>();
        for (int i = 0; i <80 ; i++) {
            datas.add("CardView :"+i);
        }
        final MyAdapter adapter=new MyAdapter(datas,this);

        recycle.setAdapter(adapter);

    }
}
