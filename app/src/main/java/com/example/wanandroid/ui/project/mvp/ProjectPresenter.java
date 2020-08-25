package com.example.wanandroid.ui.project.mvp;

import com.example.mvpbase.mvp.BasePresenter;
import com.example.mvpbase.rx.RxObserver;
import com.example.wanandroid.ui.project.bean.ProjectListBean;
import com.example.wanandroid.wechat.bean.WeChatNameBean;
import com.google.gson.JsonElement;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author: 雄厚
 * Date: 2020/8/14
 * Time: 13:57
 */
public class ProjectPresenter extends BasePresenter<ProjectView, ProjectModel> {
    public ProjectPresenter(ProjectView view){
        super.setVM(view, new ProjectModel());
    }

    /**
     * 项目列表
     */
    public void getProjectTree(){
        if (vmNotNull()){
            this.mModel.getProjectTree(new RxObserver<List<WeChatNameBean>>() {
                @Override
                public void onDisposable(Disposable d) {
                    showDialog();
                    addRxManager(d);
                }

                @Override
                public void onSuccess(List<WeChatNameBean> bean) {
                    dismissDialog();
                    mView.getProjectTreeSuc(bean);
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
     * 项目列表数据
     * @param page
     * @param cid
     */
    public void getProjectList(String page, String cid){
        if (vmNotNull()){
            this.mModel.getProjectList(page, cid,
                    new RxObserver<ProjectListBean>() {
                        @Override
                        public void onDisposable(Disposable d) {
                            addRxManager(d);
                        }

                        @Override
                        public void onSuccess(ProjectListBean bean) {
                            dismissDialog();
                            mView.getProjectListSuc(bean);
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
