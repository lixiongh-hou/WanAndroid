package com.example.wanandroid.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mvpbase.utils.log.LogUtil;
import com.example.wanandroid.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import org.jetbrains.annotations.NotNull;

/**
 * @author: 雄厚
 * Date: 2020/8/20
 * Time: 16:46
 */
public class CustomRefreshHeader extends LinearLayout implements RefreshHeader {

    private ImageView mImage;
    private TextView mTextView;
    private AnimationDrawable pullDownAnim;
    private AnimationDrawable refreshingAnim;

    private boolean hasSetPullDownAnim = false;

    public CustomRefreshHeader(Context context) {
        this(context, null, 0);
    }

    public CustomRefreshHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.widget_custom_refresh_header, this);
        mImage = (ImageView) view.findViewById(R.id.iv_refresh_header);
        mTextView = (TextView) view.findViewById(R.id.tv_refresh_header);
    }

    /**
     * 获取真实视图（必须返回，不能为null）
     */
    @NonNull
    @Override
    public View getView() {
        return this;
    }

    /**
     * 获取变换方式（必须指定一个：平移、拉伸、固定、全屏）
     */
    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    /**
     * 开始动画（开始刷新或者开始加载动画）
     *
     * @param layout       RefreshLayout
     * @param height       HeaderHeight or FooterHeight
     * @param extendHeight 最大拖动高度
     */
    @Override
    public void onStartAnimator(@NotNull RefreshLayout layout, int height, int extendHeight) {

    }

    /**
     * 状态改变时调用。在这里切换第三阶段的动画卖萌小人
     *
     */
    @Override
    public void onStateChanged(@NotNull RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            //下拉刷新开始。正在下拉还没松手时调用
            case PullDownToRefresh:
                //每次重新下拉时，将图片资源重置为小人的大脑袋
                mTextView.setText("下拉开始刷新");
                mImage.setImageResource(R.drawable.commonui_pull_image);
                break;
            //正在刷新。只调用一次
            case Refreshing:
                mTextView.setText("正在刷新");
                //状态切换为正在刷新状态时，设置图片资源为小人卖萌的动画并开始执行
                mImage.setImageResource(R.drawable.anim_pull_refreshing);
                refreshingAnim = (AnimationDrawable) mImage.getDrawable();
                refreshingAnim.start();
                break;
                //刷新完成
            case ReleaseToRefresh:
                mTextView.setText("释放立即刷新");
            default:
                break;
        }
    }
    /**
     * 下拉过程中不断调用此方法。第一阶段从小变大的小人头动画，和第二阶段翻跟头动画都在这里设置
     */
    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        // 下拉的百分比小于100%时，不断调用 setScale 方法改变图片大小
        if (percent < 1) {
            mImage.setScaleX(percent);
            mImage.setScaleY(percent);

            //是否执行过翻跟头动画的标记
            if (hasSetPullDownAnim) {
                hasSetPullDownAnim = false;
            }
        }
        //当下拉的高度达到Header高度100%时，开始加载正在下拉的初始动画，即翻跟头
        if (percent >= 1.0) {
            //因为这个方法是不停调用的，防止重复
            if (!hasSetPullDownAnim) {
                mImage.setImageResource(R.drawable.anim_pull_end);
                pullDownAnim = (AnimationDrawable) mImage.getDrawable();
                pullDownAnim.start();

                hasSetPullDownAnim = true;
            }
        }
    }

    /**
     * 释放时刻
     */
    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }
    /**
     * 动画结束后调用
     */
    @Override
    public int onFinish(@NotNull RefreshLayout layout, boolean success) {
        // 结束动画
        if (pullDownAnim != null && pullDownAnim.isRunning()) {
            pullDownAnim.stop();
        }
        if (refreshingAnim != null && refreshingAnim.isRunning()) {
            refreshingAnim.stop();
        }
        //重置状态
        hasSetPullDownAnim = false;
        return 0;
    }


    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NotNull RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

}
