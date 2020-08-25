package com.example.mvpbase.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.mvpbase.utils.system.ScreenUtil;

/**
 * @author: 雄厚
 * Date: 2020/8/7
 * Time: 9:46
 */
public class StatusBarView extends View {

    private int mBarHeight;

    public StatusBarView(Context context) {
        this(context, null);
    }

    public StatusBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBarHeight = ScreenUtil.getRealStatusBarHeight();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(mBarHeight, MeasureSpec.EXACTLY));
    }

}
