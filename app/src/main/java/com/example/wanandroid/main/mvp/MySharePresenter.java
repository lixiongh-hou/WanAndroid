package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BasePresenter;
import com.example.mvpbase.rx.RxObserver;
import com.example.wanandroid.main.bean.MyShareBean;
import com.google.gson.JsonElement;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author: 雄厚
 * Date: 2020/8/24
 * Time: 10:44
 */
public class MySharePresenter extends BasePresenter<MyShareView, MyShareModel> {

    public MySharePresenter(MyShareView view){
        super.setVM(view, new MyShareModel());
    }

    /**
     * 我的分享
     * @param page
     */
    public void myShare(String page){
        if (vmNotNull()){
            this.mModel.myShare(page,
                    new RxObserver<MyShareBean>() {
                        @Override
                        public void onDisposable(Disposable d) {
                            addRxManager(d);
                        }

                        @Override
                        public void onSuccess(MyShareBean beans) {
                            dismissDialog();
                            mView.myShareSuc(beans);
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
     * 删除自己分享
     * @param id
     */
    public void delMyShare(String id){
        if (vmNotNull()){
            this.mModel.delMyShare(id,
                    new RxObserver<JsonElement>() {
                        @Override
                        public void onDisposable(Disposable d) {
                            addRxManager(d);
                        }

                        @Override
                        public void onSuccess(JsonElement jsonElement) {
                            dismissDialog();
                            mView.delMyShareSuc();
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
