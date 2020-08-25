package com.example.mvpbase.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * @author: 雄厚
 * Date: 2020/8/7
 * Time: 10:55
 * 控制左右滑动的ViewPager
 */
public class NoScrollViewPager extends ViewPager {
    private boolean noScroll = false;

    public NoScrollViewPager(Context mContext, AttributeSet attrs) {
        super(mContext, attrs);
    }

    /**
     * @param noScroll true:不能左右滑动
     */
    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !this.noScroll && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !this.noScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }
}
