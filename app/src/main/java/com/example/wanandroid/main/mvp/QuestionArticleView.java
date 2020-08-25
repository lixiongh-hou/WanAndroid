package com.example.wanandroid.main.mvp;

import com.example.mvpbase.mvp.BaseView;
import com.example.wanandroid.main.bean.QuestionArticleBean;

/**
 * @author: 雄厚
 * Date: 2020/8/25
 * Time: 14:31
 */
public interface QuestionArticleView extends BaseView {

    /**
     * 每日一问
     * @param bean
     */
    void getQuestionArticleSuc(QuestionArticleBean bean);
    /**
     * 收藏
     */
    void collectSuc();

    /**
     * 取消收藏
     */
    void unCollectSuc();
}
