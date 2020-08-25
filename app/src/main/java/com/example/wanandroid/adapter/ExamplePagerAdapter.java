package com.example.wanandroid.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 雄厚
 * Date: 2020/8/12
 * Time: 14:49
 */
public class ExamplePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;
    private List<String> titles;
    public ExamplePagerAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return Math.max(fragments.size(), 0);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if (null == titles) {
            return super.getPageTitle(position);
        } else {
            return titles.get(position);
        }
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // 覆写destroyItem并且空实现,这样每个Fragment中的视图就不会被销毁
        // super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
