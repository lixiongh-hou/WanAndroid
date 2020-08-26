package com.example.mvpbase.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mvpbase.R;
import com.example.mvpbase.annotation.BindEventBus;
import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.annotation.BindLayoutResUtil;
import com.example.mvpbase.base.delegate.DelegateImpl;
import com.example.mvpbase.base.delegate.IDelegatePublic;
import com.example.mvpbase.eventbus.EventBusUtil;
import com.example.mvpbase.utils.ThemeColorUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 12:01
 * Activity简单基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 初始化控件
     */
    public abstract void initView();

    protected IDelegatePublic iDelegate = new DelegateImpl();
    /**上下文对象*/
    protected Context mContext;
    /**注解View*/
    private Unbinder mUnbinder;
    /**沉浸式状态栏*/
    private ImmersionBar mStatusBar;
    /**软键盘管理*/
    private InputMethodManager imm;
    /**根布局*/
    protected LoadService<?> loadService;
    protected View rootView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 上下文对象
        mContext = this;
        // 关闭标题
        super.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 竖屏
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        rootView = View.inflate(this, R.layout.activity_title, null);
        addContent();
        setContentView(rootView);
        // 注解View
        mUnbinder = ButterKnife.bind(this);
        // 沉浸式状态栏
        mStatusBar = ImmersionBar.with(this);
        // 状态栏深色字体
        mStatusBar.statusBarDarkFont(false)
                .flymeOSStatusBarFontColor(R.color.rv_item_bg)
                .keyboardEnable(false, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .init();
        // 初始化控件
        this.initView();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtil.register(this);
        }
    }
    private void addContent() {
        FrameLayout flContent = rootView.findViewById(R.id.fl_content);
        mToolbar = rootView.findViewById(R.id.layoutToolbar);
        mLinearLayout = rootView.findViewById(R.id.layoutTitle);
        View content = null;
        if (this.getClass().isAnnotationPresent(BindLayoutRes.class)) {
            Log.e("isAnnotationPresent+", "" + this.getClass().isAnnotationPresent(BindLayoutRes.class));
            content = View.inflate(this, BindLayoutResUtil.getLayoutId(this), null);
        }
        if (content != null){
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            flContent.addView(content, params);
            loadService = LoadSir.getDefault().register(content,
                    (Callback.OnReloadListener) v -> reLoad());
            //这个鬼控件默认会加载一个,由于我没在全局设置一个默认会黑屏，所有要默认隐藏他
            loadService.showSuccess();

        }
    }
    protected void reLoad() {

    }
    protected void hideTitle(){
        mLinearLayout.setVisibility(View.GONE);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 上下文对象
        mContext = null;
        // 沉浸式状态栏
        if (null != mStatusBar) {
            mStatusBar.destroy();
            mStatusBar = null;
        }
        // 注解View
        if (null != mUnbinder) {
            mUnbinder.unbind();
        }
        // 清除Handler
        iDelegate.clearHandler();
        // EventBus
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtil.unregister(this);
        }
    }
    public Handler getHandler(Handler.Callback callback) {
        return iDelegate.getHandler(callback);
    }
    /**
     * 跳转Activity
     * @param cls
     * @param mBundle
     * @param requestCode
     */
    public void gotoActivity(Class<? extends Activity> cls, @Nullable Bundle mBundle, int requestCode) {
        iDelegate.gotoActivity(this, cls, mBundle, requestCode);
    }
    public void gotoActivity(Class<? extends Activity> cls, @Nullable Bundle mBundle) {
        iDelegate.gotoActivity(this, cls, mBundle);
    }

    public void gotoActivity(Class<? extends Activity> cls, int requestCode) {
        iDelegate.gotoActivity(this, cls, requestCode);
    }
    public void gotoActivity(Class<? extends Activity> cls) {
        iDelegate.gotoActivity(this, cls);
    }
    /**
     * Activity退出动画
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.act_enter_left, R.anim.act_exit_right);
    }
    /**
     * 软键盘适配
     */
    protected void softKeyboardAdaptive() {
        if (null != mStatusBar) {
            mStatusBar.keyboardEnable(true, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    .init();
        } else {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }
    /**
     * 隐藏软键盘
     */
    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.imm == null) {
            this.imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((null != localView) && (null != this.imm)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }
    /**
     * 状态栏字体(白色)
     */
    protected void statusBarFontWhite() {
        mStatusBar.statusBarDarkFont(false)
                .flymeOSStatusBarFontColor(R.color.rv_item_bg)
                .init();
    }

    /**
     * 状态栏字体(黑色)
     */
    protected void statusBarFontBlack() {
        mStatusBar.statusBarDarkFont(true, 0.2f)
                .flymeOSStatusBarFontColor(R.color.black)
                .init();
    }

    /**
     * 统一自定义标题栏的（可以根据自己的需求进行修改和添加 只要id相同就行）
     */
    protected Toolbar mToolbar;
    protected LinearLayout mLinearLayout;

    /**
     * 设置默认标题
     * @param title 标题
     * @param color 主题色
     */
    protected void setTitleBar(String title, int color){
        if (mToolbar != null){
            mToolbar.setTitle(title);
            mLinearLayout.setBackgroundColor(ThemeColorUtil.getTitleColor(mContext));
            setSupportActionBar(mToolbar);
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setDisplayHomeAsUpEnabled(true);
                supportActionBar.setDisplayShowHomeEnabled(true);
            }
            mToolbar.setNavigationIcon(R.drawable.back_icon);
            mToolbar.setNavigationOnClickListener(v -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            });
        }
    }

    /**
     * 快速获取textView 或 EditText上文字内容
     * @param view
     * @return
     */
    protected String getStringByUI(View view){
        if (view instanceof EditText) {
            return ((EditText) view).getText().toString().trim();
        } else if (view instanceof TextView) {
            return ((TextView) view).getText().toString().trim();
        }
        return "";
    }
}
