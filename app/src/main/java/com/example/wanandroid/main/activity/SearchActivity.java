package com.example.wanandroid.main.activity;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseInterfaceActivity;
import com.example.mvpbase.divider.ListDivider;
import com.example.mvpbase.eventbus.EventBusUtil;
import com.example.mvpbase.utils.RvUtil;
import com.example.mvpbase.utils.check.CheckUtil;
import com.example.mvpbase.utils.log.LogUtil;
import com.example.mvpbase.utils.toast.ToastUtil;
import com.example.mvpbase.widget.StatusBarView;
import com.example.wanandroid.R;
import com.example.wanandroid.dao.footprint.FootprintUtil;
import com.example.wanandroid.dao.search.RepoUtils;
import com.example.wanandroid.dilaog.PromptDialog;
import com.example.wanandroid.event.Type;
import com.example.wanandroid.main.bean.SearchBean;
import com.example.wanandroid.main.bean.SearchHistoryBean;
import com.example.wanandroid.main.bean.SearchHotKeyBean;
import com.example.wanandroid.main.mvp.SearchPresenter;
import com.example.wanandroid.main.mvp.SearchView;
import com.example.wanandroid.ui.home.bean.DatasBean;
import com.example.wanandroid.ui.home.event.HomeEvent;
import com.example.wanandroid.utils.ThemeColorUtil;
import com.example.wanandroid.utils.UserBiz;
import com.google.gson.Gson;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: 雄厚
 * Date: 2020/8/17
 * Time: 17:37
 */
@BindLayoutRes(R.layout.activity_search)
public class SearchActivity extends BaseInterfaceActivity<SearchPresenter> implements SearchView {
    @BindView(R.id.statusBarView)
    StatusBarView mStatusBarView;
    @BindView(R.id.toolbar)
    LinearLayout mToolbar;
    @BindView(R.id.content)
    LinearLayout content;

    @BindView(R.id.searchHistoryLay)
    LinearLayout mHistoryLay;
    @BindView(R.id.searchHistoryRv)
    RecyclerView mHistoryRecyclerView;
    private CommonAdapter<SearchHistoryBean> mHistoryAdapter;
    private List<SearchHistoryBean> mHistoryLists = new ArrayList<>();
    @BindView(R.id.searchPopularRv)
    RecyclerView mPopularRecyclerView;
    private CommonAdapter<SearchHotKeyBean> mPopularAdapter;
    private List<SearchHotKeyBean> mPopularLists = new ArrayList<>();

    @BindView(R.id.searchKey)
    EditText mEditText;
    @BindView(R.id.mPullRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.searchRv)
    RecyclerView mRecyclerView;
    private CommonAdapter<DatasBean> mAdapter;
    private List<DatasBean> mLists = new ArrayList<>();

    private RepoUtils reposing = new RepoUtils();
    /**搜索关键词*/
    private String key;
    private int index = -1;

    @Override
    public SearchPresenter initPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        initRefresh();
        hideTitle();
        loadService = LoadSir.getDefault().register(content);
        mStatusBarView.setBackgroundColor(ThemeColorUtil.getThemeColor(mContext));
        mToolbar.setBackgroundColor(ThemeColorUtil.getThemeColor(mContext));
        queryAllSearchHistory(mContext);
        initPopularRv();
        initHistoryRv();
        initRv();
        getPresenter().searchHotKey();
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 0){
                    if (mSmartRefreshLayout.getVisibility() == View.VISIBLE) {
                        queryAllSearchHistory(mContext);
                        mSmartRefreshLayout.setVisibility(View.GONE);
                        dismissLoadingDialog();
                        hideSoftKeyBoard();
                    }
                }
            }
        });
    }

    private void initPopularRv() {
        mPopularAdapter = new CommonAdapter<SearchHotKeyBean>(mContext, R.layout.item_search_rv, mPopularLists) {
            @Override
            protected void convert(ViewHolder holder, SearchHotKeyBean item, int position) {
                TextView msgView = holder.getView(R.id.itemSearchName);
                msgView.setBackground(getGradientDrawable());
                msgView.setText(item.getName());
            }
        };
        mPopularRecyclerView.setLayoutManager(RvUtil.getLayoutManager(mContext));
        mPopularRecyclerView.setAdapter(mPopularAdapter);
        RvUtil.solveNestQuestion(mPopularRecyclerView);
        mPopularAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                SearchHistoryBean data = new SearchHistoryBean();
                data.setName(mPopularAdapter.getDatas().get(i).getName());
                insertSearchHistory(mContext, data);
                hideSoftKeyBoard();
                if (mSmartRefreshLayout.getVisibility() == View.GONE) {
                    key = mPopularAdapter.getDatas().get(i).getName();
                    showLoadingDialog();
                    startRefresh();
                }
                mEditText.setText(mPopularAdapter.getDatas().get(i).getName());
                mEditText.setSelection(getStringByUI(mEditText).length());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
    }


    private void initHistoryRv() {
        mHistoryAdapter = new CommonAdapter<SearchHistoryBean>(mContext, R.layout.item_search_rv, mHistoryLists) {
            @Override
            protected void convert(ViewHolder holder, SearchHistoryBean item, int position) {
                TextView msgView = holder.getView(R.id.itemSearchName);
                msgView.setBackground(getGradientDrawable());
                msgView.setText(item.getName());
                holder.setVisible(R.id.itemSearchDown, index == position);
                holder.setOnLongClickListener(R.id.itemSearchName, v -> {
                    index = position;
                    mHistoryAdapter.notifyDataSetChanged();
                    return false;
                });
                holder.setOnClickListener(R.id.itemSearchName, v -> {
                    index = -1;
                    insertSearchHistory(mContext, item);
                    hideSoftKeyBoard();
                    if (mSmartRefreshLayout.getVisibility() == View.GONE) {
                        key = item.getName();
                        showLoadingDialog();
                        startRefresh();
                    }
                    mEditText.setText(item.getName());
                    mEditText.setSelection(getStringByUI(mEditText).length());
                });
                holder.setOnClickListener(R.id.itemSearchDown, v -> {
                    index = -1;
                    deleteSearchHistory(mContext, item);
                    queryAllSearchHistory(mContext);
                });
            }
        };
        mHistoryRecyclerView.setLayoutManager(RvUtil.getLayoutManager(mContext));
        mHistoryRecyclerView.setAdapter(mHistoryAdapter);
        RvUtil.solveNestQuestion(mHistoryRecyclerView);
    }

    private void initHistoryView() {
        if (mHistoryLists.size() <= 0) {
            mHistoryLay.setVisibility(View.GONE);
        } else {
            mHistoryLay.setVisibility(View.VISIBLE);
        }
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
                index = -1;
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

    @OnClick({R.id.back, R.id.search, R.id.searchDel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                if (mSmartRefreshLayout.getVisibility() == View.VISIBLE) {
                    queryAllSearchHistory(mContext);
                    mSmartRefreshLayout.setVisibility(View.GONE);
                    hideSoftKeyBoard();
                    dismissLoadingDialog();
                    mEditText.setText("");
                } else {
                    finish();
                }
                break;
            case R.id.search:
                if (CheckUtil.isEmpty(getStringByUI(mEditText))) {
                    return;
                }
                index = -1;
                SearchHistoryBean data = new SearchHistoryBean();
                data.setName(getStringByUI(mEditText));
                insertSearchHistory(mContext, data);
                hideSoftKeyBoard();
                key = getStringByUI(mEditText);
                showLoadingDialog();
                startRefresh();
                break;
            case R.id.searchDel:
                PromptDialog.showDialog(mContext, getResources().getString(R.string.clear_history),
                        getResources().getString(R.string.clear_all),
                        (dialog, which) -> {
                            deleteAll(mContext);
                            mHistoryLists.clear();
                            mHistoryAdapter.notifyDataSetChanged();
                            initHistoryView();
                        });
                break;
            default:
                break;
        }
    }

    @Override
    public void requestData() {
        if (CheckUtil.isNotEmpty(key)) {
            getPresenter().search(getPage(), key);
        }
    }


    @Override
    public void searchHotKeySuc(List<SearchHotKeyBean> bean) {
        if (isRefresh()) {
            mPopularLists.clear();
        }
        mPopularLists.addAll(bean);
        mPopularAdapter.notifyDataSetChanged();
    }

    @Override
    public void searchSuc(SearchBean bean) {
        mSmartRefreshLayout.setVisibility(View.VISIBLE);
        if (isRefresh()) {
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
    public void onBackPressed() {
        if (mSmartRefreshLayout.getVisibility() == View.VISIBLE) {
            queryAllSearchHistory(mContext);
            mSmartRefreshLayout.setVisibility(View.GONE);
            hideSoftKeyBoard();
            dismissLoadingDialog();
            mEditText.setText("");
        } else {
            super.onBackPressed();
        }

    }

    /**
     * 动态添加按下状态
     *
     * @return
     */
    private StateListDrawable getGradientDrawable() {
        GradientDrawable pressed = new GradientDrawable();
        pressed.setColor(ThemeColorUtil.getThemeColor(mContext));
        pressed.setShape(GradientDrawable.RECTANGLE);
        pressed.setCornerRadius(50);
        GradientDrawable normal = new GradientDrawable();
        normal.setColor(ContextCompat.getColor(mContext, R.color.color_F5));
        normal.setShape(GradientDrawable.RECTANGLE);
        normal.setCornerRadius(50);
        StateListDrawable bg = new StateListDrawable();
        bg.addState(new int[]{android.R.attr.state_pressed}, pressed);
        bg.addState(new int[]{}, normal);
        return bg;
    }

    /**
     * 插入一条数据
     *
     * @param context
     * @param data
     */
    public void insertSearchHistory(Context context, SearchHistoryBean data) {
        reposing.querySearchHistoryByName(context, data.getName()).subscribe(searchHistoryBean -> {
            LogUtil.e("通过ID查询到的数据", new Gson().toJson(searchHistoryBean));
            reposing.deleteSearchHistory(context, searchHistoryBean).subscribe(integer -> {
                LogUtil.e("相同数据删除成功", integer + "");
                reposing.insertSearchHistory(context, data).subscribe(aLong ->
                        LogUtil.e("删除成功后插入成功", aLong + ""));
            });
        }, throwable -> {
            if (reposing.ERROR.equals(throwable.getMessage())) {
                reposing.insertSearchHistory(context, data).subscribe(aLong ->
                        LogUtil.e("插入成功", aLong + ""));
            }
        });
    }

    /**
     * 删除全部
     *
     * @param context
     */
    public void deleteAll(Context context) {
        reposing.deleteAll(context);
    }

    /**
     * 查询全部数据
     *
     * @param context
     */
    public void queryAllSearchHistory(Context context) {
        reposing.queryAllSearchHistory(context).subscribe(searchHistoryBeans -> {
            Collections.reverse(searchHistoryBeans);
                    mHistoryLists.clear();
                    mHistoryLists.addAll(searchHistoryBeans);
                    mHistoryAdapter.notifyDataSetChanged();
                    initHistoryView();
                }
        );
    }

    /**
     * 删除相同数据
     *
     * @param context
     * @param data
     */
    public void deleteSearchHistory(Context context, SearchHistoryBean data) {
        reposing.deleteSearchHistory(context, data).subscribe(integer -> LogUtil.e("删除某条数据成功"));
    }


    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showShort(msg);
        failureAfter(mAdapter.getItemCount());
    }
}
