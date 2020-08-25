package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BasePresenter;
import com.example.mvpbase.rx.RxObserver;
import com.example.wanandroid.main.bean.SearchBean;
import com.example.wanandroid.main.bean.SearchHotKeyBean;
import com.google.gson.JsonElement;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author: 雄厚
 * Date: 2020/8/18
 * Time: 11:09
 */
public class SearchPresenter extends BasePresenter<SearchView, SearchModel> {

    public SearchPresenter(SearchView view){
        super.setVM(view, new SearchModel());
    }

    /**
     * 搜索热词
     */
    public void searchHotKey(){
        if (vmNotNull()){
            this.mModel.searchHotKey(new RxObserver<List<SearchHotKeyBean>>() {
                @Override
                public void onDisposable(Disposable d) {
                    addRxManager(d);
                }

                @Override
                public void onSuccess(List<SearchHotKeyBean> beans) {
                    dismissDialog();
                    mView.searchHotKeySuc(beans);
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
     * 搜索关键词
     * @param page
     * @param k
     */
    public void search(String page, String k){
        if (vmNotNull()){
            this.mModel.search(page, k,
                    new RxObserver<SearchBean>() {
                        @Override
                        public void onDisposable(Disposable d) {
                            addRxManager(d);
                        }

                        @Override
                        public void onSuccess(SearchBean bean) {
                            dismissDialog();
                            mView.searchSuc(bean);
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
