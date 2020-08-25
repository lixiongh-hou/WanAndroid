package com.example.wanandroid.ui.navigation.fragment;

import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseInterfaceFragment;
import com.example.mvpbase.mvp.BasePresenter;
import com.example.wanandroid.R;

/**
 * @author: 雄厚
 * Date: 2020/8/11
 * Time: 17:34
 */
@BindLayoutRes(R.layout.fragment_navigation)
public class NavigationFragment extends BaseInterfaceFragment {

    public static NavigationFragment getInstance(){
        return new NavigationFragment();
    }

    @Override
    public BasePresenter<?, ?> initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
    }


    @Override
    public void requestData() {

    }
}
