package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BasePresenter;
import com.example.mvpbase.rx.RxObserver;
import com.google.gson.JsonElement;

import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * @author: 雄厚
 * Date: 2020/8/21
 * Time: 13:38
 */
public class AddCollectionPresenter extends BasePresenter<AddCollectionView, AddCollectionModel> {

    public AddCollectionPresenter(AddCollectionView view){
        super.setVM(view, new AddCollectionModel());
    }

    /**
     * 添加文章
     * @param map
     */
    public void addCollection(Map<String, Object> map){
        if (vmNotNull()){
            this.mModel.addCollection(map,
                    new RxObserver<JsonElement>() {
                        @Override
                        public void onDisposable(Disposable d) {
                            addRxManager(d);
                        }

                        @Override
                        public void onSuccess(JsonElement jsonElement) {
                            dismissDialog();
                            mView.addCollectionSuc();
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
     * 分享文章
     * @param map
     */
    public void shareArticles(Map<String, Object> map){
        if (vmNotNull()){
            this.mModel.shareArticles(map,
                    new RxObserver<JsonElement>() {
                        @Override
                        public void onDisposable(Disposable d) {
                            addRxManager(d);
                        }

                        @Override
                        public void onSuccess(JsonElement jsonElement) {
                            dismissDialog();
                            mView.shareArticlesSuc();
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
