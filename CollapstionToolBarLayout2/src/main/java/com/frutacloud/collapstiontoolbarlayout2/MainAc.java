package com.frutacloud.collapstiontoolbarlayout2;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainAc extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_back;
    private TextView tv_title;
    private TextView tv_more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        tv_more= (TextView) findViewById(R.id.tv_more);
        tv_back= (TextView) findViewById(R.id.tv_back);
        tv_title= (TextView) findViewById(R.id.tv_title);
        tv_back.setOnClickListener(this);
        tv_more.setOnClickListener(this);

        //设置Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("标题");
        //setSupportActionBar(toolbar);
        //显示返回按钮图标
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //设置CollapsingToolbarLayout
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.ctb);
        // collapsingToolbarLayout.setTitle("标题");也可以在这里设置标题
        //设置CollapsingToolbarLayout扩张时的标题颜色
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        //设置CollapsingToolbarLayout收缩时的标题颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);

        //设置TabLayout+ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    //ViewPager的Adapter
    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public CharSequence getPageTitle(int position) {
            return "TAB" + (position + 1);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView textView = new TextView(MainAc.this);
            textView.setTextColor(Color.BLACK);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            textView.setText("pager " + (position + 1));
            container.addView(textView);
            return textView;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_more:
                break;
        }
    }
}
