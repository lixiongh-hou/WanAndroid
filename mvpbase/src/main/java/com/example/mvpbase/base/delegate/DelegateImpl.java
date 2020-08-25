package com.example.mvpbase.base.delegate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mvpbase.R;
import com.example.mvpbase.loading.LoadingCallBack;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 13:56
 * 委托的实现
 */
public class DelegateImpl implements IDelegatePublic {

    /**
     * Handler管理
     */
    private Handler mHandler;

    @Override
    public Handler getHandler(Handler.Callback callback) {
        if (null == mHandler) {
            mHandler = new Handler(callback);
        } else {
            throw new RuntimeException("只能管理一个Handler，只能获取一次Handler");
        }
        return mHandler;
    }

    @Override
    public void clearHandler() {
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    @Override
    public void showLoadingDialog(Context context, String msg) {

    }

    @Override
    public void showLoadingDialog(Context context) {
    }

    @Override
    public void dismissLoadingDialog() {
    }

    @Override
    public void gotoActivity(Activity activity, Class<? extends Activity> cls, @Nullable Bundle mBundle, int requestCode) {
        if (null != activity) {
            Intent intent = new Intent();
            intent.setClass(activity, cls);
            if (null != mBundle) {
                intent.putExtras(mBundle);
            }
            if (requestCode < 0) {
                activity.startActivity(intent);
            } else {
                activity.startActivityForResult(intent, requestCode);
            }
            activity.overridePendingTransition(R.anim.act_enter_right, R.anim.act_exit_left);
        }
    }

    @Override
    public void gotoActivity(Activity activity, Class<? extends Activity> cls, @Nullable Bundle mBundle) {
        gotoActivity(activity, cls, mBundle, -1);
    }

    @Override
    public void gotoActivity(Activity activity, Class<? extends Activity> cls, int requestCode) {
        gotoActivity(activity, cls, null, requestCode);
    }

    @Override
    public void gotoActivity(Activity activity, Class<? extends Activity> cls) {
        gotoActivity(activity, cls, null, -1);
    }

    @Override
    public void showToast(@NonNull String message) {

    }
}
