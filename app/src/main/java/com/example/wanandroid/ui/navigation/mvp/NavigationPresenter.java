package com.example.wanandroid.ui.navigation.mvp;

import com.example.mvpbase.mvp.BasePresenter;
import com.example.mvpbase.rx.RxObserver;
import com.example.wanandroid.ui.navigation.bean.NavigationBean;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author: 雄厚
 * Date: 2020/9/2
 * Time: 10:28
 */
public class NavigationPresenter extends BasePresenter<NavigationView, NavigationModel> {

    public NavigationPresenter(NavigationView view){
        super.setVM(view, new NavigationModel());
    }

    /**
     * 导航数据
     */
    public void getNavigation(){
        if (vmNotNull()){
            this.mModel.getNavigation(new RxObserver<List<NavigationBean>>() {
                @Override
                public void onDisposable(Disposable d) {
                    addRxManager(d);
                }

                @Override
                public void onSuccess(List<NavigationBean> beans) {
                    dismissDialog();
                    mView.getNavigationSuc(beans);
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
