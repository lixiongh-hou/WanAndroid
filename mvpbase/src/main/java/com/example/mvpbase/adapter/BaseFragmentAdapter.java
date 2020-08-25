package com.example.mvpbase.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/7
 * Time: 10:53
 * FragmentPagerAdapter的公共适配器
 */
public class BaseFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles;
    private List<Fragment> mFragments;
    public BaseFragmentAdapter(FragmentManager fm, @Nullable String[] mTitles,
                               List<Fragment> mFragments) {
        super(fm);
        this.mTitles = mTitles;
        this.mFragments = mFragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (null == mTitles) {
            return super.getPageTitle(position);
        } else {
            return mTitles[position];
        }
    }

    @Override
    public int getCount() {
        return Math.max(mFragments.size(), 0);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}
