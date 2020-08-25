package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BasePresenter;
import com.example.mvpbase.rx.RxObserver;
import com.example.wanandroid.main.bean.MyCollectionBean;
import com.google.gson.JsonElement;

import io.reactivex.disposables.Disposable;

/**
 * @author: 雄厚
 * Date: 2020/8/11
 * Time: 16:01
 */
public class MyCollectionPresenter extends BasePresenter<MyCollectionView, MyCollectionModel> {

    private boolean show = true;

    public MyCollectionPresenter(MyCollectionView view){
        super.setVM(view, new MyCollectionModel());
    }

    public void isShowDialog(boolean show){
        this.show = show;
    }

    /**
     * 我的收藏
     * @param page
     */
    public void getMyCollection(String page){
        if (vmNotNull()){
            this.mModel.getMyCollection(page,
                    new RxObserver<MyCollectionBean>() {
                        @Override
                        public void onDisposable(Disposable d) {
                            if (show) {
                                showDialog();
                            }
                            addRxManager(d);
                        }

                        @Override
                        public void onSuccess(MyCollectionBean bean) {
                            dismissDialog();
                            mView.getMyCollectionSuc(bean);
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
    public void unMyCollect(String id, String originId){
        if (vmNotNull()){
            this.mModel.unMyCollect(id, originId,
                    new RxObserver<JsonElement>() {
                        @Override
                        public void onDisposable(Disposable d) {
                            addRxManager(d);
                        }

                        @Override
                        public void onSuccess(JsonElement jsonElement) {
                            dismissDialog();
                            mView.unMyCollectSuc();
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
