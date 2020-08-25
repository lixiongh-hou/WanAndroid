package com.example.wanandroid.ui.system.mvp;

import com.example.mvpbase.mvp.BasePresenter;
import com.example.mvpbase.rx.RxObserver;
import com.example.wanandroid.ui.system.bean.SystemBean;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author: 雄厚
 * Date: 2020/8/12
 * Time: 9:28
 */
public class SystemPresenter extends BasePresenter<SystemView, SystemModel> {

    private boolean isShow = true;
    public SystemPresenter(SystemView view){
        super.setVM(view, new SystemModel());
    }
    public void setShowDialog(boolean isShow){
        this.isShow = isShow;
    }

    /**
     * 获取体系数据
     */
    public void getSystem(){
        if (vmNotNull()){
            this.mModel.getSystem(new RxObserver<List<SystemBean>>() {
                @Override
                public void onDisposable(Disposable d) {
                    if (isShow){
                        showDialog();
                    }
                    addRxManager(d);
                }

                @Override
                public void onSuccess(List<SystemBean> beans) {
                    dismissDialog();
                    mView.getSystemSuc(beans);
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
