package com.example.wanandroid.main.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseInterfaceActivity;
import com.example.mvpbase.divider.ListDivider;
import com.example.mvpbase.eventbus.EventBusUtil;
import com.example.mvpbase.utils.RvUtil;
import com.example.mvpbase.utils.check.CheckUtil;
import com.example.mvpbase.utils.toast.ToastUtil;
import com.example.wanandroid.R;
import com.example.wanandroid.dao.footprint.FootprintUtil;
import com.example.wanandroid.event.Type;
import com.example.wanandroid.main.bean.QuestionArticleBean;
import com.example.wanandroid.main.mvp.QuestionArticlePresenter;
import com.example.wanandroid.main.mvp.QuestionArticleView;
import com.example.wanandroid.ui.home.bean.DatasBean;
import com.example.wanandroid.ui.home.event.HomeEvent;
import com.example.wanandroid.utils.ThemeColorUtil;
import com.example.wanandroid.utils.UserBiz;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: 雄厚
 * Date: 2020/8/25
 * Time: 14:24
 */
@BindLayoutRes(R.layout.activity_question_article)
public class QuestionArticleActivity extends BaseInterfaceActivity<QuestionArticlePresenter> implements QuestionArticleView {

    @BindView(R.id.questionArticle)
    RecyclerView mRecyclerView;
    private CommonAdapter<DatasBean> mAdapter;
    private List<DatasBean> mLists = new ArrayList<>();
    @Override
    public QuestionArticlePresenter initPresenter() {
        return new QuestionArticlePresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        setTitleBar("每日一问", ThemeColorUtil.getThemeColor(mContext));
        initRefresh();
        showLoadingDialog();
        initRv();
    }

    private void initRv() {
        mAdapter = new CommonAdapter<DatasBean>(mContext, R.layout.item_article_rv, mLists) {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void convert(ViewHolder holder, DatasBean item, int position) {
                holder.setText(R.id.articleAuthor, handleAuthor(item))
                        .setText(R.id.articleTime, item.getNiceDate())
                        .setText(R.id.articleTitle, handleTitle(item))
                        .setText(R.id.articleType, handleCategory(item))
                        .setVisible(R.id.articleNew, item.isFresh())
                        .setVisible(R.id.articleStatus, item.isTop())
                        .setImageResource(R.id.articleCollect, isCollect(item));

                holder.setOnClickListener(R.id.articleCollect, v -> {
                    if (UserBiz.hasLogin(mContext)) {
                        if (item.isCollect()) {
                            item.setCollect(false);
                            getPresenter().unCollect(String.valueOf(item.getId()));
                            holder.setImageResource(R.id.articleCollect, R.drawable.uncollect_selector_icon);
                        } else {
                            item.setCollect(true);
                            getPresenter().collect(String.valueOf(item.getId()));
                            holder.setImageResource(R.id.articleCollect, R.drawable.collect_selector_icon);
                        }
                        FootprintUtil.updateFootPrint(mContext, item);
                    }
                });
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new ListDivider(5, R.color.trans, false));
        mRecyclerView.setAdapter(mAdapter);
        RvUtil.solveNestQuestion(mRecyclerView);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                FootprintUtil.insertFootPrint(mContext, mAdapter.getDatas().get(i));
                Bundle bundle = new Bundle();
                bundle.putString(WebViewActivity.URL, mAdapter.getDatas().get(i).getLink());
                bundle.putString(WebViewActivity.TITLE, handleTitle(mAdapter.getDatas().get(i)));
                gotoActivity(WebViewActivity.class, bundle);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
    }

    /**
     * 判断用户名
     */
    private String handleAuthor(DatasBean item) {
        if (CheckUtil.isEmpty(item.getAuthor())) {
            return String.format("作者：%s", item.getShareUser());
        } else if (CheckUtil.isEmpty(item.getShareUser())) {
            return String.format("作者：%s", item.getAuthor());
        } else {
            return "匿名用户";
        }
    }

    /**
     * 判断显示内容
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String handleTitle(DatasBean item) {
        return Html.fromHtml(item.getTitle(), Html.FROM_HTML_MODE_COMPACT).toString();
    }

    /**
     * 判断文章分类
     */
    private String handleCategory(DatasBean item) {
        if (CheckUtil.isEmpty(item.getSuperChapterName()) && CheckUtil.isEmpty(item.getChapterName())) {
            return "";
        } else if (CheckUtil.isEmpty(item.getSuperChapterName())) {
            return item.getChapterName();
        } else if (CheckUtil.isEmpty(item.getChapterName())) {
            return item.getSuperChapterName();
        } else {
            return String.format("%s / %s", item.getSuperChapterName(), item.getChapterName());
        }
    }

    private int isCollect(DatasBean item) {
        return item.isCollect() ? R.drawable.collect_selector_icon : R.drawable.uncollect_selector_icon;
    }

    @Override
    public void requestData() {
        getPresenter().getQuestionArticle(getPage());
    }

    @Override
    public void getQuestionArticleSuc(QuestionArticleBean bean) {
        if (isRefresh()){
            mLists.clear();
        }
        mLists.addAll(bean.getDatas());
        mAdapter.notifyDataSetChanged();
        successAfter(mAdapter.getItemCount());
    }

    @Override
    public void collectSuc() {
        EventBusUtil.post(new HomeEvent(Type.REFRESH_LIST));
    }

    @Override
    public void unCollectSuc() {
        EventBusUtil.post(new HomeEvent(Type.REFRESH_LIST));
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showShort(msg);
        failureAfter(mAdapter.getItemCount());
    }
}
