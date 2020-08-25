package com.example.mvpbase.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.example.mvpbase.R;
import com.example.mvpbase.utils.system.ScreenUtil;

/**
 * @author: 雄厚
 * Date: 2020/8/10
 * Time: 16:58
 */
public abstract class BaseDialog extends Dialog {
    /**
     * 获取布局资源Id
     *
     * @return 布局资源Id
     */
    public abstract int getLayoutResId();

    /**
     * 窗口
     */
    private Window mWindow;

    /**
     * 布局参数
     */
    private WindowManager.LayoutParams mParams;

    public BaseDialog(@NonNull Context context) {
        this(context, R.style.style_dialog_fuzzy);
    }
    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        // 窗口
        mWindow = super.getWindow();
        if (null != mWindow) {
            // 布局参数
            mParams = mWindow.getAttributes();
        }

        // 获取布局资源Id
        super.setContentView(this.getLayoutResId());

        // 设置可以取消
        setCanCancel(true);
        // 弹出框位置
        setGravity(Gravity.CENTER);
        // 设置宽度比例
        setWidthPercent(1.0f);
        // 设置动画
        setAnimation(R.style.DialogCentreAnim);
    }
    /**
     * 设置可以取消
     */
    public void setCanCancel(boolean cancel) {
        super.setCancelable(cancel);
        super.setCanceledOnTouchOutside(cancel);
    }


    /**
     * 设置可以取消
     */
    public void setCanCancel1(boolean cancel) {
        super.setCancelable(cancel);
    }
    /**
     * 弹出框位置
     */
    public void setGravity(int gravity) {
        if (null != mParams) {
            mParams.gravity = gravity;
            super.onWindowAttributesChanged(mParams);
        }
    }

    /**
     * 设置宽度比例
     */
    public void setWidthPercent(@FloatRange(from = 0.0, to = 1.0) float percent) {
        if (null != mWindow && null != mParams) {
            mParams.width = (int) (ScreenUtil.getScreenWidth() * percent);
            mWindow.setAttributes(mParams);
        }
    }

    /**
     * 设置高度比例
     * @param percent
     */
    public void setHeightPercent(@FloatRange(from = 0.0, to = 1.0) float percent) {
        if (null != mWindow && null != mParams) {
            mParams.height = (int) (ScreenUtil.getScreenHeight() * percent);
            mWindow.setAttributes(mParams);
        }
    }

    /**
     * 设置动画
     */
    public void setAnimation(@StyleRes int resId) {
        if (null != mWindow) {
            mWindow.setWindowAnimations(resId);
        }
    }
    @Override
    public void dismiss() {

        super.dismiss();
    }

    /**
     * 点击空白处隐藏键盘
     */
    @CallSuper
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if(view instanceof TextView){
                InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
