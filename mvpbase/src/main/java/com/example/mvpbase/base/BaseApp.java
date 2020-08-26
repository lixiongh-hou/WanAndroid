package com.example.mvpbase.base;

import android.app.Application;
import android.content.Context;
import android.view.animation.LinearInterpolator;

import androidx.core.content.ContextCompat;

import com.example.mvpbase.R;
import com.example.mvpbase.loading.EmptyCallBack;
import com.example.mvpbase.loading.LoadingCallBack;
import com.example.mvpbase.loading.NetworkAnomalyCallBack;
import com.example.mvpbase.swipeBack.ActivityLifecycleManage;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 15:21
 */
public class BaseApp extends Application {

    /**全局上下文*/
    private static Context appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        // 全局上下文
        appContext = this;
        // Activity生命周期管理
        this.registerActivityLifecycleCallbacks(ActivityLifecycleManage.getInstance());
        LoadSir.beginBuilder()
                .addCallback(new LoadingCallBack())
                .addCallback(new EmptyCallBack())
                .addCallback(new NetworkAnomalyCallBack())
                .commit();
    }
    /**
     * 获取全局上下文
     */
    public static Context getAppContext() {
        return appContext;
    }

    /*
     * 设置默认刷新头部和加载尾部
     */
    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((mContext, layout) -> {
            // 设置头部和尾部的高度
            layout.setHeaderHeight(50).setFooterHeight(50)
                    // 设置主题颜色
                    .setPrimaryColorsId(R.color.white_bg, R.color.refresh_text)
                    // 在内容不满一页的时候，是否可以上拉加载更多
                    .setEnableLoadMoreWhenContentNotFull(false)
                    // 是否在加载更多完成之后滚动内容显示新数据
                    .setEnableScrollContentWhenLoaded(true)
                    // 是否启用越界回弹
                    .setEnableOverScrollBounce(false)
                    // 设置回弹动画时长
                    .setReboundDuration(250)
                    // 设置回弹显示插值器
                    .setReboundInterpolator(new LinearInterpolator());
            return new ClassicsHeader(mContext)
                    .setSpinnerStyle(SpinnerStyle.Translate)
                    .setDrawableArrowSize(16)
                    .setDrawableMarginRight(10)
                    .setDrawableProgressSize(16)
                    .setEnableLastTime(true).setTextSizeTime(10).setTextTimeMarginTop(2)
                    .setFinishDuration(400)
                    .setTextSizeTitle(14)
                    .setPrimaryColor(ContextCompat.getColor(mContext, R.color.white_bg))
                    .setAccentColor(ContextCompat.getColor(mContext, R.color.refresh_text));
        });

        SmartRefreshLayout.setDefaultRefreshFooterCreator((mContext, layout) -> new ClassicsFooter(mContext)
                .setSpinnerStyle(SpinnerStyle.Translate)
                .setDrawableArrowSize(16)
                .setDrawableMarginRight(10)
                .setDrawableProgressSize(16)
                .setFinishDuration(400)
                .setTextSizeTitle(14)
//                        .setPrimaryColor(ContextCompat.getColor(mContext, R.color.white))
                .setAccentColor(ContextCompat.getColor(mContext, R.color.refresh_text)));
    }
}
