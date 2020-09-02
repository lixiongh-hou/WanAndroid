package com.example.wanandroid.main.activity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.example.mvpbase.annotation.BindLayoutRes;
import com.example.mvpbase.base.BaseActivity;
import com.example.mvpbase.utils.ThemeColorUtil;
import com.example.mvpbase.utils.VersionUtil;
import com.example.mvpbase.utils.toast.ToastUtil;
import com.example.wanandroid.R;
import com.example.wanandroid.dilaog.PromptDialog;
import com.example.wanandroid.utils.DataCleanManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: 雄厚
 * Date: 2020/8/26
 * Time: 16:52
 */
@BindLayoutRes(R.layout.activity_setting)
public class SettingActivity extends BaseActivity {
    @BindView(R.id.cacheSize)
    TextView mCacheSize;
    @BindView(R.id.versionNumber)
    TextView mVersionNumber;

    @Override
    public void initView() {
        setTitleBar("设置", ThemeColorUtil.getTitleColor(mContext));
        mCacheSize.setText(DataCleanManager.getTotalCacheSize(mContext));
        mVersionNumber.setText(String.format("v%s", VersionUtil.getVersionName(mContext)));
    }

    @OnClick({R.id.cacheLay, R.id.update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cacheLay:
                PromptDialog.showDialog(mContext, "清楚缓存", "您确定要清楚缓存吗", (dialog, which) -> {
                    DataCleanManager.clearAllCache(mContext);
                    mCacheSize.setText(DataCleanManager.getTotalCacheSize(mContext));
                });
                break;
            case R.id.update:
                ToastUtil.showShort("已是最新版本");
                break;
            default:
                break;
        }
    }
}
