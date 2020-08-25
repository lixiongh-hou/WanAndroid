package com.example.wanandroid.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

/**
 * @author: 雄厚
 * Date: 2020/8/13
 * Time: 17:30
 */
public class AnimationUtil {

    /**
     * 设置透明隐藏动画
     * @param view
     */
    public static void setHideAlpha(View view){
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0f);
        animator.addUpdateListener(valueAnimator -> view.setAlpha((Float) valueAnimator.getAnimatedValue()));
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
            }
        });
        animator.setDuration(300);
        animator.start();
    }
    public static void setShowAlpha(View view){
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(valueAnimator -> view.setAlpha((Float) valueAnimator.getAnimatedValue()));
        animator.setDuration(300);
        animator.start();
    }
}
