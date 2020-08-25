package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BasePresenter;
import com.example.mvpbase.rx.RxObserver;
import com.example.wanandroid.main.bean.SquareListBean;
import com.google.gson.JsonElement;

import io.reactivex.disposables.Disposable;

/**
 * @author: 雄厚
 * Date: 2020/8/21
 * Time: 15:05
 */
public class SquarePresenter extends BasePresenter<SquareView, SquareModel> {

    public SquarePresenter(SquareView view){
        super.setVM(view, new SquareModel());
    }

    /**
     * 广场列表数据
     * @param page
     */
    public void getSquare(String page){
        if (vmNotNull()){
            this.mModel.getSquareList(page,
                    new RxObserver<SquareListBean>() {
                        @Override
                        public void onDisposable(Disposable d) {
                            addRxManager(d);
                        }

                        @Override
                        public void onSuccess(SquareListBean bean) {
                            dismissDialog();
                            mView.getSquareListSuc(bean);
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
