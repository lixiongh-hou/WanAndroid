package com.example.wanandroid.main.fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseInterfaceFragment;
import com.example.mvpbase.divider.ListDivider;
import com.example.mvpbase.utils.check.CheckUtil;
import com.example.mvpbase.utils.toast.ToastUtil;
import com.example.wanandroid.R;
import com.example.wanandroid.dao.footprint.FootprintUtil;
import com.example.wanandroid.main.bean.SearchBean;
import com.example.wanandroid.main.bean.SearchHotKeyBean;
import com.example.wanandroid.main.mvp.SearchPresenter;
import com.example.wanandroid.main.mvp.SearchView;
import com.example.wanandroid.ui.home.bean.DatasBean;
import com.example.wanandroid.utils.UserBiz;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author: 雄厚
 * Date: 2020/8/18
 * Time: 15:44
 */
@BindLayoutRes(R.layout.fragment_search)
public class SearchFragment extends BaseInterfaceFragment<SearchPresenter> implements SearchView {
    private static final String KEY = "key";
    private String key;
    public static SearchFragment getInstance(String key){
        Bundle bundle = new Bundle();
        bundle.putString(KEY, key);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.searchRv)
    RecyclerView mRecyclerView;
    private CommonAdapter<DatasBean> mAdapter;
    private List<DatasBean> mLists = new ArrayList<>();

    @Override
    public SearchPresenter initPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    public void requestData() {
        getPresenter().search(getPage(), key);
    }


    @Override
    public void initView() {
        Bundle bundle = getArguments();
        if (null != bundle){
            key = bundle.getString(KEY);
        }
        super.initView();
        initRefresh();
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
//                    if (UserBiz.hasLogin(mContext)) {
//                        if (item.isCollect()) {
//                            item.setCollect(false);
//                            getPresenter().unCollect(String.valueOf(item.getId()));
//                            holder.setImageResource(R.id.articleCollect, R.drawable.uncollect_selector_icon);
//                        } else {
//                            item.setCollect(true);
//                            getPresenter().collect(String.valueOf(item.getId()));
//                            holder.setImageResource(R.id.articleCollect, R.drawable.collect_selector_icon);
//                        }
//                        FootprintUtil.updateFootPrint(mContext, item);
//                    }
                });
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new ListDivider(5, R.color.trans, false));
        mRecyclerView.setAdapter(mAdapter);
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
    public void searchHotKeySuc(List<SearchHotKeyBean> bean) {

    }

    @Override
    public void searchSuc(SearchBean bean) {
        if (isRefresh()){
            mLists.clear();
        }
        mLists.addAll(bean.getDatas());
        mAdapter.notifyDataSetChanged();
        successAfter(mAdapter.getItemCount());
    }

    @Override
    public void collectSuc() {

    }

    @Override
    public void unCollectSuc() {

    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showShort(msg);
        failureAfter(mAdapter.getItemCount());

    }
}
