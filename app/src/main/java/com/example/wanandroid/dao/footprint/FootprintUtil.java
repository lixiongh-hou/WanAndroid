package com.example.wanandroid.dao.footprint;

import android.content.Context;

import com.example.mvpbase.utils.log.LogUtil;
import com.example.wanandroid.ui.home.bean.DatasBean;
import com.google.gson.Gson;

/**
 * @author: 雄厚
 * Date: 2020/8/14
 * Time: 9:43
 */
public class FootprintUtil {
    private static RepoUtils reposing = new RepoUtils();

    public static void updateFootPrint(Context mContext, DatasBean bean){
        reposing.queryArticleById(mContext, bean.getId()).subscribe(beans -> {
            beans.setCollect(bean.isCollect());
            reposing.updateFootPrint(mContext, beans).subscribe(integer ->
                    LogUtil.e("更新的条目是" + integer)
            );
        }, throwable -> LogUtil.e(throwable.getMessage()));
    }

    public static void insertFootPrint(Context mContext, DatasBean bean){
        reposing.queryArticleById(mContext, bean.getId()).subscribe(datasBean -> {
            LogUtil.e("通过ID查询到的数据", new Gson().toJson(datasBean));
            reposing.deleteArticle(mContext, datasBean).subscribe(integer -> {
                LogUtil.e("相同数据删除成功", integer + "");
                reposing.insertFootPrint(mContext, bean).subscribe(aLong ->
                        LogUtil.e("删除成功后插入成功", aLong+"")
                );
            });
        }, throwable -> {
            if (reposing.ERROR.equals(throwable.getMessage())){
                reposing.insertFootPrint(mContext, bean).subscribe(aLong ->
                        LogUtil.e("插入成功", aLong+""));
            }
        });
    }
}
