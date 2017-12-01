package com.frutacloud.propertyanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainAc extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainAc";
    private TextView text;

    private LinearLayout layout;
    private float mDesity = 0;
    private int mLayoutHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    //rotation,translationX ,scaleX ,pivotX
    private void initView() {
        text = (TextView) findViewById(R.id.text);
        findViewById(R.id.btn_alpha).setOnClickListener(this);
        findViewById(R.id.btn_rotation).setOnClickListener(this);
        findViewById(R.id.btn_scale).setOnClickListener(this);
        findViewById(R.id.btn_translation).setOnClickListener(this);
        findViewById(R.id.animation).setOnClickListener(this);
        findViewById(R.id.jump).setOnClickListener(this);
        findViewById(R.id.showAnimation).setOnClickListener(this);
        layout = (LinearLayout) findViewById(R.id.layout);

        text.setVisibility(View.GONE);


        mDesity = getResources().getDisplayMetrics().density;
        //获取布局高度
        mLayoutHeight = (int) (mDesity * layout.getLayoutParams().height + 0.5);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_alpha:
                alpha();
                break;
            case R.id.btn_rotation:
                rotation();
                break;
            case R.id.btn_scale:
                scale();
                break;
            case R.id.btn_translation:
                translation();
                break;
            case R.id.animation:
                animations();
                break;
            case R.id.jump:
                startActivity(new Intent(this, ViewAc.class));
                break;
            case R.id.showAnimation:
                show();
                break;
        }
    }

    private void show() {
        if (layout.getVisibility() == View.VISIBLE) {
            closeAnimation(layout);
        } else {
            openAnitiom(layout);
        }
    }

    private void closeAnimation(final LinearLayout layout) {
        int height = layout.getHeight();
        final ValueAnimator anim = ValueAnimator.ofInt(height, 0);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams lp = layout.getLayoutParams();
                lp.height = value;
                layout.setLayoutParams(lp);
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {  //动画结束时监听
            @Override
            public void onAnimationEnd(Animator animation) {
                layout.setVisibility(View.GONE);
            }
        });
        anim.start();
    }

    private void openAnitiom(final LinearLayout layout) {
        layout.setVisibility(View.VISIBLE);
        ValueAnimator value = ValueAnimator.ofInt(0, mLayoutHeight);
        value.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int va = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams lp = layout.getLayoutParams();
                lp.height = va;
                layout.setLayoutParams(lp);
            }
        });

        value.start();
    }

    private void animations() {
        //Transaltion  移动
        float y = text.getTranslationY();
        float x = text.getTranslationX();
        ObjectAnimator tranlastion = ObjectAnimator.ofFloat(text, "translationX", -500, x);
        text.setVisibility(View.VISIBLE);
        //rotation  旋转
        ObjectAnimator rotation = ObjectAnimator.ofFloat(text, "rotation", 0f, 360f);
        //scale 缩放
        ObjectAnimator scale = ObjectAnimator.ofFloat(text, "scaleY", 1f, 5f, 1f);
        //alpha  渐变透明度
        ObjectAnimator alpha = ObjectAnimator.ofFloat(text, "alpha", 0f, 1f, 1f);

        AnimatorSet set = new AnimatorSet();
        set.play(rotation).after(tranlastion).before(alpha).with(scale);
        set.setDuration(5000);
        set.start();
    }

    private void alpha() {
        ObjectAnimator obj = ObjectAnimator.ofFloat(text, "alpha", 0f, 1f, 0.5f);
        obj.setDuration(5000);
        obj.start();
    }

    private void translation() {
        float x = text.getTranslationX();
        ObjectAnimator obj = ObjectAnimator.ofFloat(text, "translationX", x, -500f, x);
        obj.setDuration(5000);
        obj.start();
    }

    private void scale() {
        float scaleX = text.getScaleX();
        float scaleY = text.getScaleY();
        ObjectAnimator obj = ObjectAnimator.ofFloat(text, "scaleX", 1f, 3f, 2f);
        obj.setDuration(5000);
        obj.start();
    }

    private void rotation() {
        ObjectAnimator obj = ObjectAnimator.ofFloat(text, "rotation", 0f, 360f, 180f);
        obj.setDuration(5000);
        obj.start();
    }


}
