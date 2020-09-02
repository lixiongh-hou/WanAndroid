package com.example.wanandroid.ui.home.fragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mvpbase.annotation.BindEventBus;
import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseInterfaceFragment;
import com.example.mvpbase.divider.ListDivider;
import com.example.mvpbase.loading.NetworkAnomalyCallBack;
import com.example.mvpbase.utils.LayoutParamsUtil;
import com.example.mvpbase.utils.RvUtil;
import com.example.mvpbase.utils.ThemeColorUtil;
import com.example.mvpbase.utils.check.CheckUtil;
import com.example.mvpbase.utils.constant.ConstantUtil;
import com.example.mvpbase.utils.log.LogUtil;
import com.example.mvpbase.utils.system.DensityUtil;
import com.example.mvpbase.utils.toast.ToastUtil;
import com.example.wanandroid.dao.footprint.FootprintUtil;
import com.example.wanandroid.main.activity.WebViewActivity;
import com.example.wanandroid.main.event.ChangeThemeEvent;
import com.example.wanandroid.ui.home.bean.ArticleBean;
import com.example.wanandroid.ui.home.bean.BannerBase;
import com.example.wanandroid.R;
import com.example.wanandroid.ui.home.bean.DatasBean;
import com.example.wanandroid.ui.home.event.HomeEvent;
import com.example.wanandroid.ui.home.mvp.HomePresenter;
import com.example.wanandroid.ui.home.mvp.HomeView;
import com.example.wanandroid.utils.UserBiz;
import com.example.wanandroid.widget.MyScrollView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stx.xhb.xbanner.XBanner;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: 雄厚
 * Date: 2020/8/7
 * Time: 11:29
 */
@BindEventBus
@BindLayoutRes(R.layout.fragment_home)
public class HomeFragment extends BaseInterfaceFragment<HomePresenter> implements HomeView, MyScrollView.OnScrollChangeListener {
    @BindView(R.id.banner)
    XBanner mBanner;
    @BindView(R.id.homeRv)
    RecyclerView mRecyclerView;
    private List<DatasBean> mHomeLists = new ArrayList<>();
    private CommonAdapter<DatasBean> mAdapter;
    @BindView(R.id.fab_add)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.scrollView)
    MyScrollView mScrollView;
    private Boolean isFirst = true;
    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        initRefresh();
        LayoutParamsUtil.getLayoutParams(mBanner.getLayoutParams(), ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(200));
        initRv();
        //这里的view为fragment，minSdkVersion必须大于等于18
        loadService.getLoadLayout().getViewTreeObserver().addOnWindowFocusChangeListener(hasFocus -> {
            // do your stuff here
            if (hasFocus && isFirst){
                mFloatingActionButton.setVisibility(checkIsVisible(mContext, mBanner) ? View.GONE : View.VISIBLE);
                isFirst = false;
            }
        });
        mScrollView.setOnScrollChangeListener(this);
        mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ThemeColorUtil.getThemeColor(mContext)));
    }

    private void initRv() {
        mAdapter = new CommonAdapter<DatasBean>(mContext, R.layout.item_article_rv, mHomeLists) {
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
                Bundle bundle = new Bundle();
                bundle.putString(WebViewActivity.URL, mAdapter.getDatas().get(i).getLink());
                bundle.putString(WebViewActivity.TITLE, handleTitle(mAdapter.getDatas().get(i)));
                gotoActivity(WebViewActivity.class, bundle);
                FootprintUtil.insertFootPrint(mContext, mAdapter.getDatas().get(i));
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

    @OnClick(R.id.fab_add)
    public void onClick(){
        mScrollView.fling(0);
        mScrollView.smoothScrollTo(0, 0);
    }
    /**
     * 因为要进行懒加载和刷新不走这个接口
     */
    @Override
    public void isLoad() {
        super.isLoad();
        if (isVisible) {
            getPresenter().getBanner();
        }

    }


    @Override
    public void requestData() {
        //走接口判断是刷新还是假装
        if (isRefresh()) {
            getPresenter().getArticleTop();
        } else {
            getPresenter().getArticle(getPage());
        }

    }

    /**
     * Banner图数据
     * @param base
     */
    @Override
    public void getBannerSuc(List<BannerBase> base) {
        mBanner.setBannerData(base);
        mBanner.loadImage((banner, model, view, position) -> Glide.with(mContext).load(base.get(position).getXBannerUrl()).into((ImageView) view));
    }

    /**
     * 文章列表
     * @param bean
     */
    @Override
    public void getArticleSuc(ArticleBean bean) {
        mHomeLists.addAll(bean.getDatas());
        mAdapter.notifyDataSetChanged();
        successAfter(mAdapter.getItemCount());
    }

    /**
     * 置顶文章
     * @param bean
     */
    @Override
    public void getArticleTopSuc(List<DatasBean> bean) {
        if (isRefresh()) {
            mHomeLists.clear();
            getPresenter().getArticle(getPage());
        }
        for (int i = 0; i < bean.size(); i++) {
            bean.get(i).setTop(true);
        }
        mHomeLists.addAll(bean);

    }

    /**
     * 收藏
     */
    @Override
    public void collectSuc() {
    }

    /**
     * 取消收藏
     */
    @Override
    public void unCollectSuc() {

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeThemeEvent(ChangeThemeEvent event) {
        mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ThemeColorUtil.getThemeColor(mContext)));
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void homeEvent(HomeEvent event) {
        switch (event.type) {
            case REFRESH_LIST:
                startRefresh();
                break;
            default:
                break;
        }
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showShort(msg);
        if (ConstantUtil.NETWORK.equals(msg)){
            loadService.showCallback(NetworkAnomalyCallBack.class);
        }
        failureAfter(mAdapter.getItemCount());
    }

    @Override
    public void reLoad(View view) {
        super.reLoad(view);
        view.setOnClickListener(v -> {
//            if (NetWorkUtils.isNetworkConnected(BaseApp.getAppContext())){
//                loadService.showCallback(LoadingCallBack.class);
//            }
            getPresenter().getBanner();
            requestData();
        });
    }
    @Override
    public void onScrollChange(MyScrollView view, int x, int y, int oldx, int oldy) {
        if (checkIsVisible(mContext, mBanner)){
            if (mFloatingActionButton.getVisibility() == View.VISIBLE){
                mFloatingActionButton.setVisibility(View.GONE);
            }
        }else {
            if (mFloatingActionButton.getVisibility() == View.GONE){
                mFloatingActionButton.setVisibility(View.VISIBLE);
            }
        }
    }
    public Boolean checkIsVisible(Context context, View view) {
        // 如果已经加载了，判断广告view是否显示出来，然后曝光
        int screenWidth = getScreenMetrics(context).x;
        int screenHeight = getScreenMetrics(context).y;
        Rect rect = new Rect(0, 0, screenWidth, screenHeight);
        int[] location = new int[2];
        view.getLocationInWindow(location);
        //view已不在屏幕可见区域;
        return view.getLocalVisibleRect(rect);
    }
    /**
     * 获取屏幕宽度和高度，单位为px
     * @param context
     * @return
     */
    public Point getScreenMetrics(Context context){
        DisplayMetrics dm =context.getResources().getDisplayMetrics();
        int wScreen = dm.widthPixels;
        int hScreen = dm.heightPixels;
        return new Point(wScreen, hScreen);
    }

    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }


}
