package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BasePresenter;
import com.example.mvpbase.rx.RxObserver;
import com.example.wanandroid.main.bean.UserPointsBean;
import com.example.wanandroid.main.bean.UserPointsListBean;

import io.reactivex.disposables.Disposable;

/**
 * @author: 雄厚
 * Date: 2020/8/20
 * Time: 9:33
 */
public class UserPointsPresenter extends BasePresenter<UserPointsView, UserPointsModel> {

    public UserPointsPresenter(UserPointsView view){
        super.setVM(view, new UserPointsModel());
    }

    /**
     * 用户获取积分列表
     * @param page
     */
    public void getUserPointsList(String page){
        if (vmNotNull()){
            this.mModel.getUserPointsList(page,
                    new RxObserver<UserPointsListBean>() {
                        @Override
                        public void onDisposable(Disposable d) {
                            addRxManager(d);
                        }

                        @Override
                        public void onSuccess(UserPointsListBean bean) {
                            dismissDialog();
                            mView.getUserPointsListSuc(bean);
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
     * 用户积分
     */
    public void getUserPoints(){
        if (vmNotNull()){
            this.mModel.getUserPoints(new RxObserver<UserPointsBean>() {
                @Override
                public void onDisposable(Disposable d) {
                    addRxManager(d);
                }

                @Override
                public void onSuccess(UserPointsBean bean) {
                    dismissDialog();
                    mView.getUserPoints(bean);
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
