package com.example.mvpbase.mvp;

import com.example.mvpbase.rx.RxManager;
import com.example.mvpbase.utils.check.CheckUtil;

import io.reactivex.disposables.Disposable;


/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 14:22
 * Presenter层
 */
public class BasePresenter<V extends BaseView, M extends BaseModel> {
    protected V mView = null;
    protected M mModel = null;
    private RxManager mRxManager = null;

    /**
     * 销毁
     */
    public void onDestroy() {
        this.mView = null;
        this.mModel = null;
        if (null != this.mRxManager) {
            this.mRxManager.clear();
            this.mRxManager = null;
        }
    }

    // ---------------------------------------------------------
    // 供BasePresenter子类调用
    /**
     * 设置VM
     * @param v
     * @param m
     */
    protected void setVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
    }

    /**
     * 传入订阅结果进行统一管理
     * @param d
     */
    protected void addRxManager(Disposable d) {
        if (null == mRxManager) {
            mRxManager = new RxManager();
        }
        mRxManager.add(d);
    }

    /**
     * View and Model not null
     */
    protected boolean vmNotNull() {
        return (null != mView) && (null != mModel);
    }

    // ---------------------------------------------------------
    // BasePresenter子类中调用View的基础方法

    /**
     * 显示错误信息
     */
    protected void showErrorMsg(String msg) {
        if (null != mView && CheckUtil.isNotEmpty(msg)) {
            mView.showErrorMsg(msg);
        }
    }
    /**
     * 显示加载弹出框
     */
    protected void showDialog() {
        if (null != mView) {
            mView.showLoadingDialog();
        }
    }

    /**
     * 关闭加载弹出框
     */
    protected void dismissDialog() {
        if (null != mView) {
            mView.dismissLoadingDialog();
        }
    }

}
