package com.example.org.mydemo.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;


/**
 * Created by Administrator on 2017/3/9.
 */

public class CircleView extends android.view.View {
    private Paint mPaint;
    private Paint textPaint;

    public CircleView(Context context) {
        super(context);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        textPaint = new Paint();
        mPaint.setColor(Color.RED);
        canvas.drawCircle(100, 100, 60, mPaint);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(40);
        canvas.drawText("圆", 0, 1, 80, 110, textPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
