package com.example.mvpbase.mvp;

/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 14:22
 * View层
 */
public interface BaseView {
    /**
     * 显示错误信息
     *
     * @param msg 错误信息
     */
    void showErrorMsg(String msg);
    /**
     * 显示加载弹出框
     */
    void showLoadingDialog();
    /**
     * 关闭加载弹出框
     */
    void dismissLoadingDialog();
}
