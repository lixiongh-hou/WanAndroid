package com.example.mvpbase.rx;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 14:38
 * 用于管理单个Presenter的RxJava订阅管理
 */
public class RxManager {
    /**
     * {@link CompositeDisposable}
     */
    private CompositeDisposable mComposite;
    public RxManager() {
        this.mComposite = new CompositeDisposable();
    }

    /**
     * 添加
     */
    public void add(Disposable d) {
        mComposite.add(d);
    }
    /**
     * 清除
     */
    public void clear() {
        mComposite.clear();
        mComposite = null;
    }
}
