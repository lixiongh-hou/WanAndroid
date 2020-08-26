package com.example.wanandroid.main.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseActivity;
import com.example.wanandroid.R;
import com.example.mvpbase.utils.ThemeColorUtil;
import com.just.agentweb.AgentWeb;

import java.lang.reflect.Method;

import butterknife.BindView;


/**
 * @author: 雄厚
 * Date: 2020/8/14
 * Time: 16:44
 */
@BindLayoutRes(R.layout.activity_web_view)
public class WebViewActivity extends BaseActivity {
    public static final String URL = "url";
    public static final String TITLE = "title";
    private String url;
    private String title;
    @BindView(R.id.webView)
    LinearLayout mWebView;
    private AgentWeb mAgentWeb;

    private static final String MENU_BUILDER = "MenuBuilder";

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            url = bundle.getString(URL);
            title = bundle.getString(TITLE);
        }
        setTitleBar(title, ThemeColorUtil.getThemeColor(mContext));
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebView, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(ThemeColorUtil.getThemeColor(mContext))
                .createAgentWeb()
                .ready()
                .go(url);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (MENU_BUILDER.equalsIgnoreCase(menu.getClass().getSimpleName())) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.QR_code_share:
                break;
            case R.id.article_share:
                break;
            case R.id.open_it_with_system_browser:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
