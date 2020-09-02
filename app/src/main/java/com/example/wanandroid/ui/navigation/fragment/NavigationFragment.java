package com.example.wanandroid.ui.navigation.fragment;


import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseInterfaceFragment;
import com.example.mvpbase.utils.ThemeColorUtil;
import com.example.mvpbase.utils.toast.ToastUtil;
import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ViewPager2Adapter;
import com.example.wanandroid.ui.navigation.bean.NavigationBean;
import com.example.wanandroid.ui.navigation.mvp.NavigationPresenter;
import com.example.wanandroid.ui.navigation.mvp.NavigationView;


import java.util.List;

import butterknife.BindView;

/**
 * @author: 雄厚
 * Date: 2020/8/11
 * Time: 17:34
 */
@BindLayoutRes(R.layout.fragment_navigation)
public class NavigationFragment extends BaseInterfaceFragment<NavigationPresenter> implements NavigationView {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.viewPager2)
    ViewPager2 mViewPager2;

    public static NavigationFragment getInstance(){
        return new NavigationFragment();
    }

    @Override
    public NavigationPresenter initPresenter() {
        return new NavigationPresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        showLoadingDialog();
        mSwipeRefreshLayout.setColorSchemeColors(ThemeColorUtil.getTitleColor(mContext));
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white_bg);
        mSwipeRefreshLayout.setOnRefreshListener(this::startRefresh);

    }


    @Override
    public void requestData() {
        getPresenter().getNavigation();
    }

    @Override
    public void getNavigationSuc(List<NavigationBean> beans) {
        mSwipeRefreshLayout.setRefreshing(false);
        ViewPager2Adapter mAdapter = new ViewPager2Adapter(mContext, beans);
        mViewPager2.setAdapter(mAdapter);
        mViewPager2.setOffscreenPageLimit(beans.size());
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showShort(msg);
    }
}
