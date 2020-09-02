package com.example.wanandroid.main.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvpbase.annotation.BindEventBus;
import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseActivity;
import com.example.mvpbase.utils.log.LogUtil;
import com.example.wanandroid.dilaog.PromptDialog;
import com.example.mvpbase.eventbus.EventBusUtil;
import com.example.mvpbase.swipeBack.ActivityLifecycleManage;
import com.example.mvpbase.utils.constant.ConstantUtil;
import com.example.mvpbase.utils.glide.GlideUtil;
import com.example.mvpbase.utils.system.SPUtil;
import com.example.mvpbase.utils.toast.ToastUtil;
import com.example.mvpbase.widget.BottomNavBar;
import com.example.mvpbase.widget.NoScrollViewPager;
import com.example.mvpbase.widget.StatusBarView;
import com.example.mvpbase.widget.image.CircleImageView;
import com.example.wanandroid.R;
import com.example.wanandroid.event.Type;
import com.example.wanandroid.main.event.NightModeEvent;
import com.example.wanandroid.ui.home.event.HomeEvent;
import com.example.wanandroid.ui.home.fragment.HomeFragment;
import com.example.wanandroid.ui.login.activity.LogInActivity;
import com.example.wanandroid.main.event.ChangeThemeEvent;
import com.example.wanandroid.main.event.MainEvent;
import com.example.wanandroid.ui.navigation.fragment.NavigationFragment;
import com.example.wanandroid.ui.project.fragment.ProjectFragment;
import com.example.wanandroid.ui.system.fragment.SystemFragment;
import com.example.mvpbase.utils.ThemeColorUtil;
import com.example.wanandroid.utils.UserBiz;
import com.example.wanandroid.wechat.fragment.WeChatFragment;
import com.google.android.material.internal.NavigationMenuPresenter;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 12:01
 */
@BindEventBus
@BindLayoutRes(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @BindView(R.id.statusBarView)
    StatusBarView mStatusBarView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawerMain)
    DrawerLayout mDrawerMain;
    @BindView(R.id.navigationDraw)
    NavigationView mNavigationDraw;
    private List<String> mToolbarTitles = new ArrayList<>();
    @BindView(R.id.mainVp)
    NoScrollViewPager mMainVp;
    @BindView(R.id.mainBar)
    BottomNavBar mMainBar;

    private View headView;
    private CircleImageView mHead;
    private TextView mName;
    private TextView mInfo;

    private long pressTime = 0;

    @Override
    public void initView() {
        hideTitle();
        initDrawerLayout();
        initToolbarTitles();
        initToolBar();
        initColor();
        initUser();
        setToolBarTitle(mToolbarTitles.get(0));
        mMainBar.setTextColor(ContextCompat.getColor(mContext, R.color.black), ThemeColorUtil.getThemeColor(mContext));
        mMainBar.addTab(HomeFragment.getInstance(), new BottomNavBar.TabParam(mToolbarTitles.get(0), R.drawable.navigation_home));
        mMainBar.addTab(SystemFragment.getInstance(), new BottomNavBar.TabParam(mToolbarTitles.get(1), R.drawable.navigation_system));
        mMainBar.addTab(WeChatFragment.getInstance(), new BottomNavBar.TabParam(mToolbarTitles.get(2), R.drawable.navigation_wechat));
        mMainBar.addTab(NavigationFragment.getInstance(), new BottomNavBar.TabParam(mToolbarTitles.get(3), R.drawable.navigation_navigation));
        mMainBar.addTab(ProjectFragment.getInstance(), new BottomNavBar.TabParam(mToolbarTitles.get(4), R.drawable.navigation_project));
        mMainBar.setViewPager(getSupportFragmentManager(), mMainVp, false, index -> {
            setToolBarTitle(mToolbarTitles.get(index));
            return true;
        });


    }


    /**
     * 设置主题色
     */
    private void initColor() {
        mMainBar.setTextColor(ContextCompat.getColor(mContext, R.color.black), ThemeColorUtil.getThemeColor(mContext));
        mMainBar.setImageColor(ContextCompat.getColor(mContext, R.color.black), ThemeColorUtil.getThemeColor(mContext));
        mToolbar.setBackgroundColor(ThemeColorUtil.getTitleColor(mContext));
        headView.setBackgroundColor(ThemeColorUtil.getTitleColor(mContext));
        mStatusBarView.setBackgroundColor(ThemeColorUtil.getTitleColor(mContext));
    }

    private void initToolbarTitles() {
        mToolbarTitles.add(getString(R.string.navigation_home));
        mToolbarTitles.add(getString(R.string.navigation_system));
        mToolbarTitles.add(getString(R.string.navigation_wechat));
        mToolbarTitles.add(getString(R.string.navigation_navigation));
        mToolbarTitles.add(getString(R.string.navigation_project));
    }

    private void initToolBar() {
        //设置导航图标、按钮有旋转特效
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, mDrawerMain, mToolbar, R.string.app_name, R.string.app_name);
        toggle.syncState();
    }

    private void setToolBarTitle(String title) {
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void initUser() {
        if (UserBiz.loginStatus()) {
            GlideUtil.loadImg(mContext, ConstantUtil.HEAD, mHead);
            mName.setText(UserBiz.getUser().getNickname());
            mInfo.setText(String.format("账号id：%s", UserBiz.getUser().getId()));
        } else {
            GlideUtil.loadImg(mContext, R.drawable.not_log_in_icon, mHead);
            mName.setText(getResources().getString(R.string.logout));
            mInfo.setText(String.format("账号id：%s", "--"));
        }
    }

    private void initDrawerLayout() {
        headView = mNavigationDraw.getHeaderView(0);
        mHead = headView.findViewById(R.id.meImage);
        mName = headView.findViewById(R.id.meName);
        mInfo = headView.findViewById(R.id.meInfo);
        mHead.setOnClickListener(v -> {
            if (!UserBiz.loginStatus()) {
                gotoActivity(LogInActivity.class);
            }
            mDrawerMain.closeDrawers();
        });

        mNavigationDraw.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                //排行榜
                case R.id.nav_menu_rank:
                    if (UserBiz.hasLogin(mContext)) {
                        gotoActivity(PointsRankingActivity.class);
                    }
                    break;
                //广场
                case R.id.nav_menu_square:
                    gotoActivity(SquareActivity.class);
                    break;
                //我的收藏
                case R.id.nav_menu_collect:
                    if (UserBiz.hasLogin(mContext)) {
                        gotoActivity(MyCollectionActivity.class);
                    }
                    break;
                //我的分享
                case R.id.nav_menu_share:
                    if (UserBiz.hasLogin(mContext)) {
                        gotoActivity(MyShareActivity.class);
                    }
                    break;
                //每日一问
                case R.id.nav_menu_question:
                    gotoActivity(QuestionArticleActivity.class);
                    break;
                //设置主题色
                case R.id.nav_menu_theme:
                    gotoActivity(ThemeColorActivity.class);
                    break;
                //足迹
                case R.id.nav_menu_footprint:
                    gotoActivity(FootPrintActivity.class);
                    break;
                //设置
                case R.id.nav_menu_setting:
                    gotoActivity(SettingActivity.class);
                    break;
                //退出登录
                case R.id.nav_menu_logout:
                    if (UserBiz.hasLogin(mContext)) {
                        PromptDialog.showDialog(mContext, "您确定要退出登录", "退出登录", (dialog, which) -> {
                            //清楚全部缓存
                            SPUtil.remove(ConstantUtil.USER);
                            SPUtil.remove(ConstantUtil.COOKIE);
                            initUser();
                            EventBusUtil.post(new HomeEvent(Type.REFRESH_LIST));
                        });
                    }
                    break;
                default:
                    break;
            }
            mDrawerMain.closeDrawers();
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerMain.openDrawer(Gravity.START);
                break;
            case R.id.action_search:
                gotoActivity(SearchActivity.class);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int exitTime = 2000;
        if (System.currentTimeMillis() - pressTime > exitTime) {
            ToastUtil.showShort("再按一次退出应用程序");
            pressTime = System.currentTimeMillis();
            return;
        }
        //点击返回键，不退出应用程序。直接返回后台
        ActivityLifecycleManage.getInstance().startHome(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeThemeEvent(ChangeThemeEvent event) {
        initColor();
        mMainBar.setCurrentItem(mMainBar.getCurrentItem());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void nightModeEvent(NightModeEvent event) {
        recreate();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mainEvent(MainEvent event) {
        if (event.type == Type.REFRESH) {
            initUser();
        }
    }
}