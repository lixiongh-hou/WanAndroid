package com.example.wanandroid.ui.project.fragment;

import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.mvpbase.annotation.BindEventBus;
import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseApp;
import com.example.mvpbase.base.BaseInterfaceFragment;
import com.example.mvpbase.loading.NetworkAnomalyCallBack;
import com.example.mvpbase.networks.NetWorkUtils;
import com.example.mvpbase.utils.constant.ConstantUtil;
import com.example.mvpbase.utils.toast.ToastUtil;
import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ExamplePagerAdapter;
import com.example.wanandroid.main.event.ChangeThemeEvent;
import com.example.wanandroid.ui.project.bean.ProjectListBean;
import com.example.wanandroid.ui.project.mvp.ProjectPresenter;
import com.example.wanandroid.ui.project.mvp.ProjectView;
import com.example.mvpbase.utils.ThemeColorUtil;
import com.example.wanandroid.wechat.bean.WeChatNameBean;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: 雄厚
 * Date: 2020/8/11
 * Time: 17:36
 */
@BindEventBus
@BindLayoutRes(R.layout.fragment_project)
public class ProjectFragment extends BaseInterfaceFragment<ProjectPresenter> implements ProjectView {

    public static ProjectFragment getInstance(){
        return new ProjectFragment();
    }

    @BindView(R.id.projectToolbar)
    TabLayout mTabLayout;
    @BindView(R.id.projectVp)
    ViewPager mViewPager;
    private List<String> mDataList = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    /**自定义视图的全局变量*/
    private TextView tvTab;

    private ExamplePagerAdapter mAdapter;

    @Override
    public ProjectPresenter initPresenter() {
        return new ProjectPresenter(this);
    }

    @Override
    public void initView() {
        super.initView();

    }

    @Override
    public void requestData() {
        getPresenter().getProjectTree();
    }

    @Override
    public void getProjectTreeSuc(List<WeChatNameBean> bean) {
        initEvent(bean);
    }

    @Override
    public void getProjectListSuc(ProjectListBean bean) {

    }

    @Override
    public void collectSuc() {

    }

    @Override
    public void unCollectSuc() {

    }

    /**
     * 初始化TabLayout和ViewPage
     * 通过改变TabLayout源码TextView来改变选中改变字体大小
     */
    private void initEvent(List<WeChatNameBean> bean) {
        for (int i = 0; i < bean.size(); i++) {
            mFragments.add(ProjectChildrenFragment.getInstance(bean.get(i).getId()));
            mDataList.add(bean.get(i).getName());
        }
        mAdapter = new ExamplePagerAdapter(getChildFragmentManager(), mFragments, mDataList);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(ThemeColorUtil.getThemeColor(mContext));
        initTabLayout();
    }

    private void initTabLayout(){
        //用来循环适配器中的视图总数
        for (int i = 0; i < mAdapter.getCount(); i++) {
            //获取每一个tab对象
            TabLayout.Tab tabAt = mTabLayout.getTabAt(i);
            //将每一个条目设置我们自定义的视图
            if (tabAt != null) {
                tabAt.setCustomView(R.layout.tab_layout_text);
                //默认选中第一个
                if (i == 0) {
                    // 设置第一个tab的TextView是被选择的样式
                    //第一个tab被选中
                    if (tabAt.getCustomView() != null) {
                        tabAt.getCustomView().findViewById(R.id.text1).setSelected(true);
                    }
                    //设置选中标签的文字大小
                    if (tabAt.getCustomView() != null){
                        ((TextView) tabAt.getCustomView().findViewById(R.id.text1)).
                                setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.font_size_16sp));
                        ((TextView) tabAt.getCustomView().findViewById(R.id.text1)).
                                setTextColor(ThemeColorUtil.getThemeColor(mContext));
                        ((TextView) tabAt.getCustomView().findViewById(R.id.text1)).
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    }
                }else {
                    if (tabAt.getCustomView() != null){
                        ((TextView) tabAt.getCustomView().findViewById(R.id.text1)).
                                setTextColor(ContextCompat.getColor(mContext, R.color.tab_text));
                    }
                }
                //通过tab对象找到自定义视图的ID
                if (tabAt.getCustomView() != null) {
                    TextView textView = tabAt.getCustomView().findViewById(R.id.text1);
                    //设置tab上的文字
                    textView.setText(mDataList.get(i));
                }

            }
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //定义方法，判断是否选中
                updateTabView(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //定义方法，判断是否选中
                updateTabView(tab, false);
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    /**
     *  用来改变tabLayout选中后的字体大小及颜色
     */
    private void updateTabView(TabLayout.Tab tab, boolean isSelect) {
        //找到自定义视图的控件ID
        if (tab.getCustomView() != null) {
            tvTab = tab.getCustomView().findViewById(R.id.text1);
        }
        if(isSelect) {
            //设置标签选中
            tvTab.setSelected(true);
            //选中后字体变大
            tvTab.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen.font_size_16sp));
            tvTab.setTextColor(ThemeColorUtil.getThemeColor(mContext));
            tvTab.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        }else{
            //设置标签取消选中
            tvTab.setSelected(false);
            //恢复为默认字体大小
            tvTab.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen.font_size_14sp));
            tvTab.setTextColor(ContextCompat.getColor(mContext, R.color.tab_text));
            tvTab.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
    }
    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showShort(msg);
        if (ConstantUtil.NETWORK.equals(msg)){
            loadService.showCallback(NetworkAnomalyCallBack.class);
        }
    }
    @Override
    public void reLoad(View view) {
        super.reLoad(view);
        view.setOnClickListener(v -> {
            if (NetWorkUtils.isNetworkConnected(BaseApp.getAppContext())){
                requestData();
                return;
            }
            ToastUtil.showShort(ConstantUtil.NETWORK);
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeThemeEvent(ChangeThemeEvent event) {
        mTabLayout.setSelectedTabIndicatorColor(ThemeColorUtil.getThemeColor(mContext));
        //获取当前tab对象
        TabLayout.Tab tabAt = mTabLayout.getTabAt(mViewPager.getCurrentItem());
        //当前tab被选中
        if (tabAt != null) {
            if (tabAt.getCustomView() != null) {
                tabAt.getCustomView().findViewById(R.id.text1).setSelected(true);
            }
            //设置选中标签的文字大小
            if (tabAt.getCustomView() != null) {
                ((TextView) tabAt.getCustomView().findViewById(R.id.text1)).
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.font_size_16sp));
                ((TextView) tabAt.getCustomView().findViewById(R.id.text1)).
                        setTextColor(ThemeColorUtil.getThemeColor(mContext));
                ((TextView) tabAt.getCustomView().findViewById(R.id.text1)).
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
        }
    }
}
