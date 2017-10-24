package com.example.org.mydemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/3/9.
 */

public class LinerRect extends View {
    private Paint paint;
    public LinerRect(Context context) {
        super(context);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int rectCount = 10;
        int mWidth = 5;
        int mHeight = 8;
        int mRectWidth = 10;
        paint.setColor(Color.BLUE);
        for (int i = 0; i < rectCount; i++) {
            canvas.drawRect((float) ((mWidth ) + (mRectWidth * i) + 2), mWidth, (float) (mWidth + mRectWidth * (i + 1)),mHeight, paint);
            float random = (float) Math.random();
            float currentHeight = (mHeight * random);
            postInvalidateDelayed(300);
        }
    }

    public LinerRect(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinerRect(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
