package com.example.org.mydemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/3/8.
 */

public class TextV extends TextView {
    /*Matrix的对图像的处理可分为四类基本变换：

Translate           平移变换

Rotate                旋转变换

Scale                  缩放变换

Skew                  错切变换*/
    private Matrix mMatrix = null;  //
    private LinearGradient mLinearGradient = null; //不断变化的Gradient
    private int mViewWidth = 0; //控件宽度
    private Paint mPaint = null; //画笔

    private int mTranslate = 0; //平移距离

    public TextV(Context context) {
        super(context);
    }

    public TextV(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextV(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mLinearGradient != null) {
            mTranslate+=mViewWidth/5;
            if (mTranslate>2*mViewWidth){
                mTranslate=-mViewWidth;
            }
            //mMatrix.setTranslate(mTranslate,0);
            mMatrix.setScale(mTranslate,0);
            mLinearGradient.setLocalMatrix(mMatrix);
            postInvalidateDelayed(100);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0, new int[]{Color.BLUE, Color.BLACK, Color.RED}, null, Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                mMatrix = new Matrix();
            }
        }
    }

}
