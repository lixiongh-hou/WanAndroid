package com.example.wanandroid.dao.search;

import android.content.Context;

import com.example.mvpbase.utils.log.LogUtil;
import com.example.wanandroid.dao.footprint.FootPrintDataBase;
import com.example.wanandroid.main.bean.SearchHistoryBean;
import com.example.wanandroid.ui.home.bean.DatasBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @author: 雄厚
 * Date: 2020/8/18
 * Time: 11:58
 */
public class RepoUtils {
    public final String ERROR = "查询名称为空";

    /**
     * 增加一条数据
     *
     * @param context
     * @param data
     */
    public  Observable<Long> insertSearchHistory(Context context, SearchHistoryBean data) {
        return Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                emitter.onNext(SearchDataBase.getInstance(context)
                        .getSearchDao()
                        .insertSearchHistory(data));
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 查询所有数据
     * @param context
     * @return
     */
    public Observable<List<SearchHistoryBean>> queryAllSearchHistory(final Context context){
        return Observable.create((ObservableOnSubscribe<List<SearchHistoryBean>>) emitter -> {
            emitter.onNext(SearchDataBase.getInstance(context)
                    .getSearchDao()
                    .queryAllSearchHistory());
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 通过Name查询数据
     * @param context
     * @param name
     * @return
     */
    public Observable<SearchHistoryBean> querySearchHistoryByName(final Context context, String name){
        return Observable.create((ObservableOnSubscribe<SearchHistoryBean>) emitter -> {
            SearchHistoryBean bean =
                    SearchDataBase.getInstance(context).getSearchDao().querySearchHistoryByName(name);
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
    public Observable<Integer> deleteSearchHistory(final Context context, SearchHistoryBean data){
        return Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(SearchDataBase.getInstance(context)
                    .getSearchDao()
                    .deleteSearchHistory(data));
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    /**
     * 删除全部数据
     * @param context
     */
    public void deleteAll(final Context context){
        Observable.create((ObservableOnSubscribe<Void>) e -> SearchDataBase.getInstance(context)
                .getSearchDao()
                .deleteAll()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }
}
