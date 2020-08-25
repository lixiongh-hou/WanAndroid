package com.example.wanandroid.dao.footprint;

import android.content.Context;

import com.example.mvpbase.utils.log.LogUtil;
import com.example.wanandroid.ui.home.bean.DatasBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: 雄厚
 * Date: 2020/8/13
 * Time: 10:37
 * 数据操作需要在后台线程执行，这里使用RxJava操作
 */
public class RepoUtils {
    public final String ERROR = "查询id为空";

    /**
     * 增加一条数据
     * @param context
     * @param data
     */
    public Observable<Long> insertFootPrint(Context context, DatasBean data){
        return Observable.create((ObservableOnSubscribe<Long>) emitter -> {
            emitter.onNext( FootPrintDataBase.getInstance(context)
                    .getFootPrintDao()
                    .insertFootPrint(data));
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 查询所有数据
     * @param context
     * @return
     */
    public Observable<List<DatasBean>> queryAllFootPrint(final Context context){
        return Observable.create((ObservableOnSubscribe<List<DatasBean>>) emitter -> {
            emitter.onNext(FootPrintDataBase.getInstance(context)
                    .getFootPrintDao()
                    .queryAllFootPrint());
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 通过ID查询数据
     * @param context
     * @param id
     * @return
     */
    public Observable<DatasBean> queryArticleById(final Context context, int id){
        return Observable.create((ObservableOnSubscribe<DatasBean>) emitter -> {
            DatasBean bean =
                    FootPrintDataBase.getInstance(context).getFootPrintDao().queryArticleById(id);
            if (bean == null) {
                LogUtil.e("RepoUtils", "this id:" + null + "don't exist");
                emitter.onError(new Throwable(ERROR));
                return;
            }
            emitter.onNext(bean);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 删除相同数据
     * @param context
     * @param data
     * @return
     */
    public Observable<Integer> deleteArticle(final Context context, DatasBean data){
        return Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(FootPrintDataBase.getInstance(context)
                    .getFootPrintDao()
                    .deleteArticle(data));
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 删除全部数据
     * @param context
     */
    public void deleteAll(final Context context){
        Observable.create((ObservableOnSubscribe<Void>) e -> FootPrintDataBase.getInstance(context)
                .getFootPrintDao()
                .deleteAll()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }

    /**
     * 更新某条数据
     * @param context
     * @param data
     */
    public Observable<Integer> updateFootPrint(final Context context, DatasBean data) {
        return Observable.create((ObservableOnSubscribe<Integer>) e -> {
            e.onNext(FootPrintDataBase.getInstance(context)
                    .getFootPrintDao()
                    .updateFootPrint(data));
            e.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
