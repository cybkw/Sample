package com.frutacloud.recycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemClick{
    private static final String TAG="MainAc";

    private RecyclerView recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }
    private List<String> data=new ArrayList<>();
    private void initView() {

        recycler= (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        for (int i = 0; i <200 ; i++) {
            data.add("dexClass"+i);
        }
        Adapter ad=new Adapter(this,data);
        ad.setOnItemClick(this);

        recycler.setAdapter(ad);
    }

    @Override
    public void onClick(View view, int position) {
        Toast.makeText(this,data.get(position).toString(),Toast.LENGTH_SHORT).show();
    }
}
