package com.example.mvpbase.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.example.mvpbase.R;
import com.example.mvpbase.loading.EmptyCallBack;
import com.example.mvpbase.loading.LoadingCallBack;
import com.example.mvpbase.mvp.BasePresenter;
import com.example.mvpbase.utils.constant.ConstantUtil;
import com.example.mvpbase.utils.log.LogUtil;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 14:11
 */
public abstract class BaseInterfaceActivity<P extends BasePresenter<?, ?>>
        extends BaseActivity {
    /**
     * 初始化Presenter
     *
     * @return Presenter
     */
    public abstract P initPresenter();

    /**
     * 请求数据
     */
    public abstract void requestData();

    /**
     * MVP
     */
    private P mPresenter;


    @Override
    public void initView() {
        // 初始化Presenter
        mPresenter = initPresenter();
        // 请求数据
        this.requestData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭加载弹出框
        dismissLoadingDialog();
        // MVP
        if (null != mPresenter) {
            mPresenter.onDestroy();
        }

    }

    /**
     * 提供给子类调用的方法
     *
     * @return
     */
    public P getPresenter() {
        if (null == mPresenter) {
            throw new NullPointerException("抽象方法(initPresenter)的实现中，未初始化 Presenter");
        }
        return mPresenter;
    }

    // ---------------------------------------------------------------------------------------------
    // 加载弹出框

    public void showLoadingDialog(String msg) {
        iDelegate.showLoadingDialog(this, msg);
    }

    public void showLoadingDialog() {
        /*iDelegate.showLoadingDialog(this);*/
        loadService.showCallback(LoadingCallBack.class);
    }

    public void dismissLoadingDialog() {
        /*iDelegate.dismissLoadingDialog();*/
        loadService.showCallback(SuccessCallback.class);
    }

    // ---------------------------------------------------------------------------------------------
    // 下拉刷新,上拉加载

    /**
     * 刷新布局
     */
    protected RefreshLayout mRefreshLayout;
    /**
     * 无数据显示布局
     */
    protected RelativeLayout mPullEmptyLayout;
    private TextView mPullEmptyTv;
    private ImageView mPullEmptyImg;
    /**
     * 记录当前操作,刷新还是加载
     */
    private int what = ConstantUtil.REFRESH;
    /**
     * 一页的数据数
     */
    private int pageSize = 10;
    /**
     * 页数
     */
    private int page = 0;
    /**
     * 数据长度
     */
    public int dataLength = 0;

    public String getPageSize() {
        return String.valueOf(pageSize);
    }

    public String getPage() {
        return String.valueOf(page);
    }

    public boolean isRefresh() {
        return what == ConstantUtil.REFRESH;
    }


    /**
     * 开始刷新
     */
    public void startRefresh() {
        what = ConstantUtil.REFRESH;
        page = 0;
        this.requestData();
    }

    /**
     * 开始加载
     */
    public void startLoadMore() {
        what = ConstantUtil.LOADING;
        page++;
        this.requestData();
    }

    /**
     * 初始化刷新
     * 不要刷新但是要走接口走这个方法
     */
    public void initEmpty() {
        mPullEmptyLayout = (RelativeLayout) findViewById(R.id.mPullEmptyLayout);
        mPullEmptyTv = (TextView) findViewById(R.id.mPullEmptyTv);
        mPullEmptyImg = (ImageView) findViewById(R.id.mPullEmptyImg);
    }

    /**
     * 初始化刷新
     */
    public void initRefresh() {
        mRefreshLayout = (RefreshLayout) findViewById(R.id.mPullRefreshLayout);
        mPullEmptyLayout = (RelativeLayout) findViewById(R.id.mPullEmptyLayout);
        mPullEmptyTv = (TextView) findViewById(R.id.mPullEmptyTv);
        mPullEmptyImg = (ImageView) findViewById(R.id.mPullEmptyImg);

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                // 开始加载
                startLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout mRefreshLayout) {
                // 开始刷新
                startRefresh();
            }
        });
    }

    /**
     * 数据请求成功后执行
     *
     * @param newLength 响应后数据源的长度
     */
    public void successAfter(int newLength) {
        if (null != mRefreshLayout) {
            if (what == ConstantUtil.LOADING) {
                if (((newLength - this.dataLength) < pageSize) || newLength < pageSize) {
                    // 加载成功没有更多数据
                    mRefreshLayout.finishLoadMore(0, true, true);
                } else {
                    // 加载成功还有更多数据
                    mRefreshLayout.finishLoadMore(0, true, false);
                }
            } else {
                // 刷新成功、本来老版本后面参数有个0
                mRefreshLayout.finishRefresh(true);
                if (newLength < pageSize) {
                    mRefreshLayout.setNoMoreData(true);
                } else {
                    mRefreshLayout.setNoMoreData(false);
                }
            }
        }
        this.dataLength = newLength;
        setEmpty();
    }

    /**
     * 数据请求失败后执行
     *
     * @param newLength 响应后数据源的长度
     */
    public void failureAfter(int newLength) {
        if (null != mRefreshLayout) {
            if (what == ConstantUtil.LOADING) {
                // 加载失败
                mRefreshLayout.finishLoadMore(0, false, false);
                page--;
            } else {
                // 刷新失败、少个0
                mRefreshLayout.finishRefresh(false);
            }
        }
        this.dataLength = newLength;
        setEmpty();
    }

    /**
     * 判断数据源长度,决定是否显示布局
     */
    private void setEmpty() {
        if (null != mPullEmptyLayout) {
            if (this.dataLength > 0) {
                mPullEmptyLayout.setVisibility(View.GONE);
            } else {
                mPullEmptyLayout.setVisibility(View.VISIBLE);
            }
        }else {
            if (this.dataLength > 0) {
                loadService.showCallback(SuccessCallback.class);
            } else {
                loadService.showCallback(EmptyCallBack.class);
            }
        }
    }

    /**
     * 设置空布局
     */
    protected void setEmptyLayout(@DrawableRes int drawRes, CharSequence text) {
        if (null != mPullEmptyImg) {
            mPullEmptyImg.setImageResource(drawRes);
        }
        if (null != mPullEmptyTv) {
            mPullEmptyTv.setText(text);
        }
    }

    /**
     * 设置空布局
     */
    protected void setEmptyLayout(@DrawableRes int drawRes, @StringRes int strRes) {
        this.setEmptyLayout(drawRes, super.getText(strRes));
    }

    /**
     * 启用刷新和加载
     */
    protected void setEnableRefreshAndLoad(boolean enable) {
        if (null != mRefreshLayout) {
            mRefreshLayout.setEnableRefresh(enable);
            mRefreshLayout.setEnableLoadMore(enable);
        }
    }

    /**
     * 启用刷新
     */
    protected void setEnableRefresh(boolean enable) {
        if (null != mRefreshLayout) {
            mRefreshLayout.setEnableRefresh(enable);
        }
    }

    /**
     * 启用加载
     */
    protected void setEnableLoadMore(boolean enable) {
        if (null != mRefreshLayout) {
            mRefreshLayout.setEnableLoadMore(enable);
            mRefreshLayout.setEnableOverScrollDrag(true);
        }
    }
}
