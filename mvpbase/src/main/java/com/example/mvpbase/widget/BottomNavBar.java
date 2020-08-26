package com.example.mvpbase.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mvpbase.R;
import com.example.mvpbase.adapter.BaseFragmentAdapter;
import com.example.mvpbase.utils.check.CheckUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/7
 * Time: 10:48
 */
public class BottomNavBar extends LinearLayout {
    public BottomNavBar(Context context) {
        super(context);
    }

    public BottomNavBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomNavBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BottomNavBar, defStyleAttr, 0);
        normalIvColor = typedArray.getResourceId(R.styleable.BottomNavBar_bt_NormalPictureColor, normalIvColor);
        selectIvColor = typedArray.getResourceId(R.styleable.BottomNavBar_bt_SelectPictureColor, selectIvColor);
        isDoublePicture = typedArray.getBoolean(R.styleable.BottomNavBar_bt_isDoublePicture, isDoublePicture);
        typedArray.recycle();
    }

    private NoScrollViewPager mViewPager;
    /**
     * 适配器和数据源
     */
    private FragmentPagerAdapter mVpAdapter;
    private List<Fragment> mFragments = new ArrayList<>();
    private List<TabParam> mTabParams = new ArrayList<>();
    /**
     * 文字的颜色
     */
    private int normalTvColor = ContextCompat.getColor(getContext(), R.color.black);
    private int selectTvColor = ContextCompat.getColor(getContext(), R.color.theme_color);
    /**
     * 图片底色
     */
    private int normalIvColor = ContextCompat.getColor(getContext(), R.color.black);
    private int selectIvColor = ContextCompat.getColor(getContext(), R.color.theme_color);
    /**
     * 是否是双图片
     */
    private boolean isDoublePicture = false;

    /**
     * 设置文字颜色
     */
    public void setTextColor(@ColorInt int normalColorRes, @ColorInt int selectColorRes) {
        this.normalTvColor = normalColorRes;
        this.selectTvColor = selectColorRes;
    }
    /**
     * 设置图片颜色颜色
     */
    public void setImageColor(@ColorInt int normalColorRes, @ColorInt int selectColorRes) {
        this.normalIvColor = normalColorRes;
        this.selectIvColor = selectColorRes;
    }
    /**
     * 添加Tab标签
     * <p>
     * 占位方法:
     * BottomNavBar.addTab(null, new BottomNavBar.TabParam("占位"));
     */
    public void addTab(@Nullable Fragment fragment, TabParam tabParam) {
        View tabView = LayoutInflater.from(getContext()).inflate(R.layout.act_main_tab, null);
        ImageView imageView = tabView.findViewById(R.id.act_main_tab_img);
        TextView textView = tabView.findViewById(R.id.act_main_tab_title);
        textView.setText(tabParam.title);
        textView.setTextColor(normalTvColor);

        if (null != fragment) {
            tabView.setTag(mTabParams.size());
            tabView.setOnClickListener(v -> {
                // 设置当前Item
                setCurrentItem(Integer.parseInt(String.valueOf(v.getTag())));
            });
            mFragments.add(fragment);

            tabParam.imgIcon = imageView;
            tabParam.tvTitle = textView;
            mTabParams.add(tabParam);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }
        tabView.setLayoutParams(
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        this.addView(tabView);
    }

    /**
     * 设置ViewPager
     *
     * @param fm         Fragment管理器
     * @param viewPager  ViewPager
     * @param isNoScroll 没有滚动
     * @param mListener  是否选中Tab的事件
     */
    public void setViewPager(FragmentManager fm, NoScrollViewPager viewPager, boolean isNoScroll, OnSelectTabListener mListener) {
        mVpAdapter = new BaseFragmentAdapter(fm, null, mFragments);
        viewPager.setAdapter(mVpAdapter);
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setNoScroll(isNoScroll);
        viewPager.addOnPageChangeListener(new NoScrollViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int i) {
                // 设置当前Item
                setCurrentItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        this.mViewPager = viewPager;
        this.onSelectTabListener = mListener;

        // 设置当前Item
        setCurrentItem(0);
    }

    /**
     * 设置当前Item
     */
    public void setCurrentItem(int index) {
        if (null != onSelectTabListener) {
            if (onSelectTabListener.isSelectTab(index)) {

                mViewPager.setCurrentItem(index);

                if (CheckUtil.isListNotEmpty(mFragments)) {
                    for (int i = 0; i < mFragments.size(); i++) {
                        final TabParam tabParam = mTabParams.get(i);
                        tabParam.tvTitle.setText(tabParam.title);
                        //双图片
                        if (isDoublePicture) {
                            if (i != index) {
                                tabParam.imgIcon.setImageResource(tabParam.normalImageResId);
                                tabParam.tvTitle.setTextColor(normalTvColor);
                            } else {
                                tabParam.imgIcon.setImageResource(tabParam.selectImageResId);
                                tabParam.tvTitle.setTextColor(selectTvColor);
                            }
                            //单图片
                        } else {
                            tabParam.imgIcon.setImageResource(tabParam.selectImageResId);
                            if (i != index) {
                                tabParam.imgIcon.setColorFilter(normalIvColor);
                                tabParam.tvTitle.setTextColor(normalTvColor);
                            } else {
                                tabParam.imgIcon.setColorFilter(selectIvColor);
                                tabParam.tvTitle.setTextColor(selectTvColor);
                            }
                        }

                    }
                }
            }
        }
    }

    public int getCurrentItem() {
        return mViewPager.getCurrentItem();
    }

    /**
     * 接口回调
     */
    public interface OnSelectTabListener {
        /**
         * 是否选中
         *
         * @param index
         * @return true:选中当前下标 false:不选中当前下标
         */
        boolean isSelectTab(int index);
    }

    private OnSelectTabListener onSelectTabListener;

    /**
     * Tab参数
     */
    public static class TabParam {

        public String title;
        public int normalImageResId;
        public int selectImageResId;

        public ImageView imgIcon;
        public TextView tvTitle;

        public TabParam(String title) {
            this.title = title;
            this.normalImageResId = 0;
            this.selectImageResId = 0;
        }

        /**
         * 当有两张图片走这个 isDoublePicture = true
         *
         * @param title
         * @param normalImageResId
         * @param selectImageResId
         */
        public TabParam(String title, @DrawableRes int normalImageResId, @DrawableRes int selectImageResId) {
            this.title = title;
            this.normalImageResId = normalImageResId;
            this.selectImageResId = selectImageResId;
        }

        /**
         * 当只有一张图片走这个 isDoublePicture = false
         *
         * @param title
         * @param selectImageResId
         */
        public TabParam(String title, @DrawableRes int selectImageResId) {
            this.title = title;
            this.selectImageResId = selectImageResId;
        }
    }
}
