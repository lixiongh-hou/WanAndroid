package com.example.wanandroid.ui.system.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseInterfaceActivity;
import com.example.mvpbase.mvp.BasePresenter;
import com.example.wanandroid.R;
import com.example.wanandroid.adapter.ExamplePagerAdapter;
import com.example.wanandroid.ui.system.bean.SystemBean;
import com.example.wanandroid.ui.system.fragment.SystemDetailedFragment;
import com.example.mvpbase.utils.ThemeColorUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: 雄厚
 * Date: 2020/8/12
 * Time: 14:26
 */
@BindLayoutRes(R.layout.activity_system_detailed)
public class SystemDetailedActivity extends BaseInterfaceActivity {
    public static final String TITLE = "title";
    public static final String SYSTEM_CHILDREN = "systemChildren";
    public static final String INDEX = "index";
    private List<SystemBean.ChildrenBean> systemChildren = new ArrayList<>();
    private String title;
    private int index;

    @BindView(R.id.systemDetailedToolbar)
    TabLayout mTableLayout;
    @BindView(R.id.systemDetailedVp)
    ViewPager mViewPager;
    private List<String> mDataList = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    /**自定义视图的全局变量*/
    private TextView tvTab;
    @Override
    public BasePresenter<?, ?> initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            title = bundle.getString(TITLE);
            systemChildren  = bundle.getParcelableArrayList(SYSTEM_CHILDREN);
            index = bundle.getInt(INDEX, 0);
        }
        setTitleBar(title, ThemeColorUtil.getThemeColor(mContext));
        initEvent();


    }

    /**
     * 初始化TabLayout和ViewPage
     * 通过改变TabLayout源码TextView来改变选中改变字体大小
     */
    private void initEvent() {
        for (int i = 0; i < systemChildren.size(); i++) {
            mFragments.add(SystemDetailedFragment.getInstance(systemChildren.get(i).getId()));
            mDataList.add(systemChildren.get(i).getName());
        }
        ExamplePagerAdapter mAdapter = new ExamplePagerAdapter(getSupportFragmentManager(), mFragments, mDataList);
        mViewPager.setAdapter(mAdapter);
        mTableLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(index);
        mTableLayout.setSelectedTabIndicatorColor(ThemeColorUtil.getThemeColor(mContext));
        //用来循环适配器中的视图总数
        for (int i = 0; i < mAdapter.getCount(); i++) {
            //获取每一个tab对象
            TabLayout.Tab tabAt = mTableLayout.getTabAt(i);
            //将每一个条目设置我们自定义的视图
            if (tabAt != null) {
                tabAt.setCustomView(R.layout.tab_layout_text);
                //默认选中第一个
                if (i == index) {
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
                    textView.setText(systemChildren.get(i).getName());
                }

            }
        }
        mTableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    public void requestData() {

    }
}
