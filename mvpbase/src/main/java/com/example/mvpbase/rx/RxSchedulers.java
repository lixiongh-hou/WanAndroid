package com.example.mvpbase.rx;

import com.example.mvpbase.bean.BaseBean;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 15:33
 * 调度器
 */
public class RxSchedulers {
    public static <T> ObservableTransformer<BaseBean<T>, BaseBean<T>> io_main() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static <T> ObservableTransformer<T, T> io_mains() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
