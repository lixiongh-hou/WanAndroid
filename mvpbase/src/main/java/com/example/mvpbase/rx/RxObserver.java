package com.example.mvpbase.rx;

import androidx.annotation.NonNull;

import com.example.mvpbase.base.BaseApp;
import com.example.mvpbase.bean.BaseBean;
import com.example.mvpbase.networks.NetWorkUtils;
import com.example.mvpbase.utils.constant.ConstantUtil;
import com.example.mvpbase.utils.log.LogUtil;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 15:10
 * 观察者
 */
public abstract class RxObserver<T> implements Observer<BaseBean<T>> {

    @Override
    public void onSubscribe(Disposable d) {
        this.onDisposable(d);
    }

    @Override
    public void onNext(@NonNull BaseBean<T> bean) {
        if (bean.isSuccess()){
            // 成功
            this.onSuccess(bean.getData());
        }else {
            // 失败(GsonResponseBodyConverter处理了，是不会走到这里的)
            String msg = bean.getErrorMsg();
            LogUtil.eSuper(msg);
            this.onError(msg);

        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        String message = e.getMessage();
        LogUtil.eSuper(e.getClass() + "\u3000Message:" + message);
        if (!NetWorkUtils.isNetworkConnected(BaseApp.getAppContext())){
            // e instanceof UnknownHostException
            message = ConstantUtil.NETWORK;
        }else if (e instanceof SocketTimeoutException){
            message = "请求超时,请稍后再试";
        }else if (e instanceof ConnectException){
            message = "连接异常,请稍后再试";
        } else if (e instanceof HttpException) {
            message = "服务器异常,请稍后再试";
        } else if (e instanceof JsonSyntaxException) {
            message = "数据解析错误";
        } else {
            message = "网络访问错误,请稍后再试";
        }
        this.onError(message);
    }

    @Override
    public void onComplete() {

    }


    /**
     * 回调Disposable
     *
     * @param d Disposable
     */
    public abstract void onDisposable(Disposable d);

    /**
     * 成功
     *
     * @param t 成功数据
     */
    public abstract void onSuccess(T t);
    /**
     * 失败
     *
     * @param msg 失败提示
     */
    public abstract void onError(String msg);
}
