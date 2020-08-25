package com.example.wanandroid.ui.home.mvp;

import com.example.mvpbase.mvp.BasePresenter;
import com.example.mvpbase.rx.RxObserver;
import com.example.wanandroid.ui.home.bean.ArticleBean;
import com.example.wanandroid.ui.home.bean.BannerBase;
import com.example.wanandroid.ui.home.bean.DatasBean;
import com.google.gson.JsonElement;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author: 雄厚
 * Date: 2020/8/7
 * Time: 13:40
 */
public class HomePresenter extends BasePresenter<HomeView, HomeModel> {

    public HomePresenter(HomeView view){
        super.setVM(view, new HomeModel());
    }

    /**
     * 获取Banner数据
     */
    public void getBanner(){
        if (vmNotNull()){
            this.mModel.getBanner(new RxObserver<List<BannerBase>>() {
                @Override
                public void onDisposable(Disposable d) {
                    showDialog();
                    addRxManager(d);
                }

                @Override
                public void onSuccess(List<BannerBase> bannerBases) {
                    dismissDialog();
                    mView.getBannerSuc(bannerBases);
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
     * 获取首页文章数据
     * @param page
     */
    public void getArticle(String page){
        if (vmNotNull()){
            this.mModel.getArticle(page,
                    new RxObserver<ArticleBean>() {
                        @Override
                        public void onDisposable(Disposable d) {
                            addRxManager(d);
                        }

                        @Override
                        public void onSuccess(ArticleBean bean) {
                            dismissDialog();
                            mView.getArticleSuc(bean);
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
     * 获取首页文章数据
     */
    public void getArticleTop(){
        if (vmNotNull()){
            this.mModel.getArticleTop(
                    new RxObserver<List<DatasBean>>() {
                        @Override
                        public void onDisposable(Disposable d) {
                            addRxManager(d);
                        }

                        @Override
                        public void onSuccess(List<DatasBean> bean) {
                            dismissDialog();
                            mView.getArticleTopSuc(bean);
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
