package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BasePresenter;
import com.example.mvpbase.rx.RxObserver;
import com.example.wanandroid.main.bean.PointsRankingBean;
import com.example.wanandroid.main.bean.UserPointsBean;

import io.reactivex.disposables.Disposable;

/**
 * @author: 雄厚
 * Date: 2020/8/19
 * Time: 13:58
 */
public class PointsRankingPresenter extends BasePresenter<PointsRankingView, PointsRankingModel> {

    public PointsRankingPresenter(PointsRankingView view){
        super.setVM(view, new PointsRankingModel());
    }

    /**
     * 获取排行榜
     * @param page
     */
    public void getPointsRanking(String page){
        if (vmNotNull()){
            this.mModel.getPointsRanking(page,
                    new RxObserver<PointsRankingBean>() {
                        @Override
                        public void onDisposable(Disposable d) {
                            addRxManager(d);
                        }

                        @Override
                        public void onSuccess(PointsRankingBean bean) {
                            dismissDialog();
                            mView.getPointsRankingSuc(bean);
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
     * 获取用户积分
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
                    mView.getUserPointsSuc(bean);
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
