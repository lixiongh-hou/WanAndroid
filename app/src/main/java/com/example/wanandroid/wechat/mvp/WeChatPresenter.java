package com.example.wanandroid.wechat.mvp;

import com.example.mvpbase.mvp.BasePresenter;
import com.example.mvpbase.rx.RxObserver;
import com.example.wanandroid.wechat.bean.WeChatListBean;
import com.example.wanandroid.wechat.bean.WeChatNameBean;
import com.google.gson.JsonElement;


import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author: 雄厚
 * Date: 2020/8/14
 * Time: 10:04
 */
public class WeChatPresenter extends BasePresenter<WeChatView, WeChatModel> {
    private boolean isShow = true;
    public WeChatPresenter(WeChatView view){
        super.setVM(view, new WeChatModel());
    }

    public void setShowDialog(boolean isShow){
        this.isShow = isShow;
    }
    /**
     * 公众号名称
     */
    public void getWeChatName(){
        if (vmNotNull()){
            this.mModel.getWeChatName(new RxObserver<List<WeChatNameBean>>() {
                @Override
                public void onDisposable(Disposable d) {
                    if (isShow) {
                        showDialog();
                    }
                    addRxManager(d);
                }

                @Override
                public void onSuccess(List<WeChatNameBean> weChatNameBeans) {
                    dismissDialog();
                    mView.getWeChatNameSuc(weChatNameBeans);
                }

                @Override
                public void onError(String msg) {
                    dismissDialog();
                    showErrorMsg(msg);
                }
            });
        }
    }

    /**
     * 公众号名称对应的数据
     * @param id
     * @param page
     */
    public void getWeChatList(String id, String page){
        if (vmNotNull()){
            this.mModel.getWeChatList(id, page,
                    new RxObserver<WeChatListBean>() {
                        @Override
                        public void onDisposable(Disposable d) {
                            addRxManager(d);
                        }

                        @Override
                        public void onSuccess(WeChatListBean bean) {
                            dismissDialog();
                            mView.getWeChatListSuc(bean);
                        }

                        @Override
                        public void onError(String msg) {
                            dismissDialog();
                            showErrorMsg(msg);
                        }
                    });
        }
    }
}
