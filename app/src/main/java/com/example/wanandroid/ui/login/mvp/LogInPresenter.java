package com.example.wanandroid.ui.login.mvp;

import com.example.mvpbase.mvp.BasePresenter;
import com.example.mvpbase.rx.RxObserver;
import com.example.wanandroid.ui.login.bean.LogInBean;

import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * @author: 雄厚
 * Date: 2020/8/10
 * Time: 16:39
 */
public class LogInPresenter extends BasePresenter<LogInView, LogInModel> {

    public LogInPresenter(LogInView view){
        super.setVM(view, new LogInModel());
    }

    /**
     * 登录
     * @param map
     */
    public void logIn(Map<String, Object> map){
        if (vmNotNull()){
            this.mModel.logIn(map,
                    new RxObserver<LogInBean>() {
                        @Override
                        public void onDisposable(Disposable d) {
                            addRxManager(d);
                        }

                        @Override
                        public void onSuccess(LogInBean bean) {
                            dismissDialog();
                            mView.logInSuc(bean);
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
