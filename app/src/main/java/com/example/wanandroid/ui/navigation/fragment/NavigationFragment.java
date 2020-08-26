package com.example.wanandroid.ui.navigation.fragment;

import android.widget.TextView;

import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseInterfaceFragment;
import com.example.mvpbase.mvp.BasePresenter;
import com.example.wanandroid.R;
import com.example.wanandroid.widget.MyScrollView;

import butterknife.BindView;

/**
 * @author: 雄厚
 * Date: 2020/8/11
 * Time: 17:34
 */
@BindLayoutRes(R.layout.fragment_navigation)
public class NavigationFragment extends BaseInterfaceFragment implements MyScrollView.OnScrollChangeListener  {

    private Boolean isFirst = true;
    @BindView(R.id.scrollView)
    MyScrollView scrollView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
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
        scrollView.setOnScrollChangeListener(this);
    }


    @Override
    public void requestData() {

    }

    @Override
    public void onScrollChange(MyScrollView view, int x, int y, int oldx, int oldy) {

    }

}
