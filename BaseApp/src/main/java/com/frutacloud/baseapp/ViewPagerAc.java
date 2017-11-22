package com.frutacloud.baseapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.frutacloud.baseapp.adapter.ViewPagerAd;
import com.frutacloud.baseapp.base.App;
import com.frutacloud.baseapp.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager
 * Created by Administrator on 2017/11/9.
 */

public class ViewPagerAc extends BaseActivity {
    //http://wx2.sinaimg.cn/mw690/4b7b4a73gy1fl9sxf7cmhj21w01f07wi.jpg
    //http://wx1.sinaimg.cn/mw690/4b7b4a73gy1fkziel9fhzj20m80xanpd.jpg
    //http://wx4.sinaimg.cn/mw690/4b7b4a73gy1fkziefsqdzj21w019fnpi.jpg
    //http://wx2.sinaimg.cn/mw690/4b7b4a73ly1fkmtjzlax9j23402c0qv5.jpg

    private List<String> mImgUrls = new ArrayList<String>();
    private ViewPager viewPager;

    private ViewPagerAd mViewAd = null;
    private List<ImageView> image = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_viewpager);

        initData();
        initView();
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

    private void initData() {
        mImgUrls.add("http://wx2.sinaimg.cn/mw690/4b7b4a73gy1fl9sxf7cmhj21w01f07wi.jpg");
        mImgUrls.add("http://wx1.sinaimg.cn/mw690/4b7b4a73gy1fkziel9fhzj20m80xanpd.jpg");
        mImgUrls.add("http://wx4.sinaimg.cn/mw690/4b7b4a73gy1fkziefsqdzj21w019fnpi.jpg");
        mImgUrls.add("http://wx2.sinaimg.cn/mw690/4b7b4a73ly1fkmtjzlax9j23402c0qv5.jpg");

        for (int i = 0; i < mImgUrls.size(); i++) {
            ImageView view = new ImageView(this);
            view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            App.loadImage(mImgUrls.get(i).toString(), view);
            image.add(view);
        }
    }

    private void initView() {


        viewPager = (ViewPager) findViewById(R.id.viewPager);

        mViewAd = new ViewPagerAd(this, image);
        viewPager.setAdapter(mViewAd);
        mViewAd.notifyDataSetChanged();


    }

    @Override
    protected void requestFail(String sign, Bundle bundle) {

    }

    @Override
    protected void requestSuccess(String sign, Bundle bundle) {

    }
}
