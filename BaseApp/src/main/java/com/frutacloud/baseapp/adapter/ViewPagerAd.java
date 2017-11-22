package com.frutacloud.baseapp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/11/9.
 */

public class ViewPagerAd extends PagerAdapter {
    private static final String TAG = "ViewPagerAd";
    private Context context;

    private List<ImageView> imageViews;

    public ViewPagerAd(Context context, List<ImageView> imageViews) {
        this.context = context;
        this.imageViews = imageViews;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Log.i(TAG, "==============" + imageViews.size());

        container.addView(imageViews.get(position));

        return imageViews.get(position);
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(imageViews.get(position));
    }
}
