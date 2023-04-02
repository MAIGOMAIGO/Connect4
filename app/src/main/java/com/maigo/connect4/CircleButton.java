package com.maigo.connect4;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

public class CircleButton extends androidx.appcompat.widget.AppCompatButton {

    private boolean mAdjustWidth;

    public CircleButton(Context context) {
        super(context,null);
    }

    public CircleButton(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public CircleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mAdjustWidth = false;
        if (attrs != null){
            TypedArray attrsArray = context.obtainStyledAttributes(attrs,R.styleable.CircleButton);
            mAdjustWidth = attrsArray.getBoolean(R.styleable.CircleButton_adjust_width, false);
            attrsArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

        int sideLength = 0;
        if(mAdjustWidth){
            // 横幅合わせ
            sideLength = getMeasuredWidth();
        }else{
            sideLength = getMeasuredHeight();
        }
        setMeasuredDimension(sideLength,sideLength);
    }

    public void setmAdjustWidth(boolean adjustWidth){
        mAdjustWidth = adjustWidth;
    }
}
