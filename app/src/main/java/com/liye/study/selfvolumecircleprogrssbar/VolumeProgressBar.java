package com.liye.study.selfvolumecircleprogrssbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liye on 2017/4/29. content:渐变声音进度条
 */

public class VolumeProgressBar extends View {
    private static final String TAG = VolumeProgressBar.class.getSimpleName();

    private int mMaxProgress = 0;
    private int mCurrentProgress = 0;
    private float mStrokeWidth = 5.0f;
    //画圆形进度条的背景的画笔
    private Paint mPaintBg = new Paint();
    //画圆形进度条进度的画笔
    private Paint mPaintRun = new Paint();
    private RectF mRectf = new RectF();

    private int mPainBackgroundColor = Color.GRAY;
    //进度条在走时进度的颜色
    private int mRunPaintColor[] = {Color.RED, R.color.colorPrimary, Color.GREEN};

    public VolumeProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(mPaintBg);
        initPaint(mPaintRun);
    }


    /**
     * 初始化画笔
     *
     * @param paint
     */
    private void initPaint(Paint paint) {
        paint.setColor(mPainBackgroundColor);
        paint.setDither(true);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(mStrokeWidth);
        paint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 绘制画布
     *
     * @param canvas
     */
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int halfWidth = getWidth() / 2;
        int halfHeight = getHeight() / 2;
        int radius = halfWidth < halfHeight ? halfWidth : halfHeight;
        float halfStrokeWidth = mStrokeWidth / 2;
        //绘制当前进度
        mRectf.top = halfHeight - radius + halfStrokeWidth;
        mRectf.bottom = halfHeight + radius - halfStrokeWidth;
        mRectf.left = halfWidth - radius + halfStrokeWidth;
        mRectf.right = halfWidth + radius - halfStrokeWidth;

        //初始化并绘制进度条的背景
        canvas.drawArc(mRectf, -90, 360, false, mPaintBg);

        //梯形渲染器
//        SweepGradient gradient = new SweepGradient(getMeasuredWidth() / 2, getMeasuredHeight() / 2, mRunPaintColor, null);
//        mPaintRun.setShader(gradient);
//线形渲染器
        LinearGradient shader = new LinearGradient(3, 3, getMeasuredWidth() - 3, getMeasuredHeight() - 3, mRunPaintColor, null,
                Shader.TileMode.CLAMP);
        mPaintRun.setShader(shader);
        canvas.drawArc(mRectf, -90, getRateOfProgress() * 360, false, mPaintRun);
        canvas.save();

    }

    public void setMax(int max) {
        if (max < 0) {
            max = 0;
        }
        mMaxProgress = max;
    }

    public int getMax() {
        return mMaxProgress;
    }

    /**
     * set progressBar's current values
     *
     * @param currentProgress
     */
    public void setCurrentProgress(int currentProgress) {
        if (currentProgress > mMaxProgress) {
            currentProgress = mMaxProgress;
        }
        mCurrentProgress = currentProgress;
        if (mOnChangeListener != null) {
            mOnChangeListener.onChange(mMaxProgress, currentProgress, getRateOfProgress());
        }
        invalidate();
    }

    /**
     * get progressBar current values
     *
     * @return
     */
    public int getCurrentProgress() {
        return mCurrentProgress;
    }

    /**
     * set progressBar 's background color
     */
    public void setBackgroundColor(int color) {
        mPainBackgroundColor = color;
    }

    /**
     * set progressBar 's progress color
     */
    public void setPrimaryColor(int color[]) {
        mRunPaintColor = color;
    }

    /**
     * set circle progressBar's width
     *
     * @param width
     */
    public void setCircleWidth(float width) {
        mStrokeWidth = width;

    }

    private float getRateOfProgress() {
        return (float) mCurrentProgress / mMaxProgress;
    }

    public interface OnProgressChangeListener {
        /**
         * @param maxProgress
         * @param currentProgress
         * @param rate            : rate = (float)currentProgress / maxProgress
         */
        public void onChange(int maxProgress, int currentProgress, float rate);
    }

    private OnProgressChangeListener mOnChangeListener;

    /**
     * 设置进度条改变的监听事件
     *
     * @param l
     */
    public void setOnProgressChangeListener(OnProgressChangeListener l) {
        mOnChangeListener = l;
    }

}
