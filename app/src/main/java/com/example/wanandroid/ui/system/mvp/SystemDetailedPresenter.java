package com.example.wanandroid.ui.system.mvp;

import com.example.mvpbase.mvp.BasePresenter;
import com.example.mvpbase.rx.RxObserver;
import com.example.wanandroid.ui.home.bean.ArticleBean;
import com.google.gson.JsonElement;

import io.reactivex.disposables.Disposable;

/**
 * @author: 雄厚
 * Date: 2020/8/12
 * Time: 17:04
 */
public class SystemDetailedPresenter extends BasePresenter<SystemDetailedView, SystemDetailedModel> {

    private boolean isShow = true;
    public SystemDetailedPresenter(SystemDetailedView view){
        super.setVM(view, new SystemDetailedModel());
    }
    public void isShowDialog(boolean isShow){
        this.isShow = isShow;
    }
    public void getSystemDetailed(String page, String cid){
        if (vmNotNull()){
            this.mModel.getSystemDetailed(page, cid,
                    new RxObserver<ArticleBean>() {
                        @Override
                        public void onDisposable(Disposable d) {
                            if (isShow) {
                                showDialog();
                            }
                            addRxManager(d);
                        }

                        @Override
                        public void onSuccess(ArticleBean bean) {
                            dismissDialog();
                            mView.getSystemDetailedSuc(bean);
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
     * 收藏
     * @param id
     */
    public void collect(String id){
        if (vmNotNull()){
            this.mModel.collect(id,
                    new RxObserver<JsonElement>() {
                        @Override
                        public void onDisposable(Disposable d) {
                            addRxManager(d);
                        }

                        @Override
                        public void onSuccess(JsonElement jsonElement) {
                            dismissDialog();
                            mView.collectSuc();
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
     * 取消收藏
     * @param id
     */
    public void unCollect(String id){
        if (vmNotNull()){
            this.mModel.unCollect(id,
                    new RxObserver<JsonElement>() {
                        @Override
                        public void onDisposable(Disposable d) {
                            addRxManager(d);
                        }

                        @Override
                        public void onSuccess(JsonElement jsonElement) {
                            dismissDialog();
                            mView.unCollectSuc();
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
