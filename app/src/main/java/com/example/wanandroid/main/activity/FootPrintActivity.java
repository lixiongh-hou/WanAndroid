package com.example.wanandroid.main.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import com.example.wanandroid.R;
import com.example.wanandroid.dao.footprint.RepoUtils;
import com.example.wanandroid.dilaog.PromptDialog;
import com.example.wanandroid.event.Type;
import com.example.wanandroid.main.mvp.FootPrintPresenter;
import com.example.wanandroid.main.mvp.FootPrintView;
import com.example.wanandroid.ui.home.bean.DatasBean;
import com.example.wanandroid.ui.home.event.HomeEvent;
import com.example.wanandroid.utils.AnimationUtil;
import com.example.wanandroid.utils.ThemeColorUtil;
import com.example.wanandroid.utils.UserBiz;
import com.google.gson.Gson;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * @author: 雄厚
 * Date: 2020/8/13
 * Time: 14:05
 */
@BindLayoutRes(R.layout.activity_footprint)
public class FootPrintActivity extends BaseInterfaceActivity<FootPrintPresenter> implements FootPrintView {

    @BindView(R.id.footPrintRv)
    RecyclerView mRecyclerView;
    private CommonAdapter<DatasBean> mAdapter;
    private List<DatasBean> mLists = new ArrayList<>();
    private RepoUtils reposing = new RepoUtils();

    private float scrollX;
    private float scrollY;
    @Override
    public FootPrintPresenter initPresenter() {
        return new FootPrintPresenter(this);
    }
    @Override
    public void initView() {
        super.initView();
        initRefresh();
        setTitleBar("浏览足迹", ThemeColorUtil.getThemeColor(mContext));
        setEnableRefresh(false);
        setEnableLoadMore(false);
        initRv();
        initData();
        initRecyclerView();

    }


    private void initData() {
        reposing.queryAllFootPrint(mContext).subscribe(beans -> {
            LogUtil.e("查询到的数据" + new Gson().toJson(beans));
            Collections.reverse(beans);
            mLists.clear();
            mLists.addAll(beans);
            mAdapter.notifyDataSetChanged();
            successAfter(mAdapter.getItemCount());
        });
    }

    private void initRv() {
        mAdapter = new CommonAdapter<DatasBean>(mContext, R.layout.item_article_rv, mLists) {
            @SuppressLint("ClickableViewAccessibility")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void convert(ViewHolder holder, DatasBean item, int position) {
                holder.setText(R.id.articleAuthor, handleAuthor(item))
                        .setText(R.id.articleTime, item.getNiceDate())
                        .setText(R.id.articleTitle, handleTitle(item))
                        .setText(R.id.articleType, handleCategory(item))
                        .setVisible(R.id.articleNew, item.isFresh())
                        .setVisible(R.id.articleStatus, item.isTop())
                        .setImageResource(R.id.articleCollect, isCollect(item));
                RelativeLayout relativeLayout =holder.getView(R.id.articleView);
                if (item.isShow()){
                    holder.setVisible(R.id.articleView, true);
                    AnimationUtil.setShowAlpha(relativeLayout);
                }else {
                    if (relativeLayout.getVisibility() == View.VISIBLE) {
                        AnimationUtil.setHideAlpha(relativeLayout);
                    }
                }
                holder.setOnTouchListener(R.id.shadowLayout, (v, event) -> {
                    if (event.getAction() == MotionEvent.ACTION_DOWN){
                        for (int i = 0; i < mAdapter.getDatas().size(); i++) {
                            if (mAdapter.getDatas().get(i).isShow()){
                                refreshAdapter();
                            }
                        }
                    }
                    return false;
                });
                holder.setOnLongClickListener(R.id.shadowLayout, v -> {
                    item.setShow(true);
                    mAdapter.notifyDataSetChanged();
                    return false;
                });
                holder.setOnClickListener(R.id.articleCollect, v -> {
                    if (UserBiz.hasLogin(mContext)) {
                        if (item.isCollect()) {
                            item.setCollect(false);
                            getPresenter().unCollect(String.valueOf(mLists.get(position).getId()));
                            holder.setImageResource(R.id.articleCollect, R.drawable.uncollect_selector_icon);
                        } else {
                            item.setCollect(true);
                            getPresenter().collect(String.valueOf(mLists.get(position).getId()));
                            holder.setImageResource(R.id.articleCollect, R.drawable.collect_selector_icon);

                        }
                        reposing.updateFootPrint(mContext, item).subscribe(integer ->
                                        LogUtil.e("更新的条目是"+integer)
                                );
                    }
                });
                holder.setOnClickListener(R.id.articleDel, v -> PromptDialog.showDialog(mContext, "删除文章", "您确定要删除文章吗", (dialog, which) -> reposing.deleteArticle(mContext, item).subscribe(integer -> {
                    mLists.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    // 如果移除的是最后一个，忽略
                    if(position != mLists.size()){
                        mAdapter.notifyItemRangeChanged(position, mLists.size() - position);
                    }
                    successAfter(mAdapter.getItemCount());
                })));
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new ListDivider(5, R.color.white, false));
        mRecyclerView.setAdapter(mAdapter);
        RvUtil.solveNestQuestion(mRecyclerView);
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

    private int isCollect(@NonNull DatasBean item) {
        return item.isCollect() ? R.drawable.collect_selector_icon : R.drawable.uncollect_selector_icon;
    }

    /**
     * 判断是否点击RecyclerView空白处
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initRecyclerView() {
        //如果只监听MotionEvent.ACTION_UP动作，会有一个问题：滑动后仍会触发
        //增加MotionEvent.ACTION_DOWN动作的监听，在MotionEvent.ACTION_UP动作中比较dx和dy，dx和dy小于某个数，认为手指没有滑动
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    scrollX = event.getX();
                    scrollY = event.getY();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (v.getId() != 0 && Math.abs(scrollX - event.getX()) <= 5 && Math.abs(scrollY - event.getY()) <= 5) {
                        //recyclerView空白处点击事件
                        refreshAdapter();
                    }
                }
                return false;
            }
        });
    }

    /**
     * 刷新适配器，执行动画并且隐藏控件
     */
    private void refreshAdapter() {
        for (int i = 0; i < mAdapter.getDatas().size(); i++) {
            mAdapter.getDatas().get(i).setShow(false);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestData() {
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.footprint_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_del_all){
            refreshAdapter();
            PromptDialog.showDialog(mContext, "您确定清除全部", "清除全部", (dialog, which) -> {
                reposing.deleteAll(mContext);
                LogUtil.e("数据全部删除成功");
                mLists.clear();
                mAdapter.notifyDataSetChanged();
                successAfter(mAdapter.getItemCount());
            });
        }
        return super.onOptionsItemSelected(item);
    }
}
