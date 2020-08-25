package com.example.mvpbase.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.FloatRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.mvpbase.R;
import com.example.mvpbase.utils.system.DensityUtil;
import com.example.mvpbase.utils.system.ScreenUtil;

/**
 * @author: 雄厚
 * Date: 2020/8/24
 * Time: 11:54
 */
public abstract class BaseFragmentDialog extends DialogFragment {

    @LayoutRes
    protected int mLayoutResId;
    /**背景昏暗度*/
    private float mDimAmount = 0.5f;
    /**左右边距*/
    private int mMargin = 0;
    /**进入退出动画*/
    private int mAnimStyle = 0;
    /**点击外部取消*/
    private boolean mOutCancel = true;
    private WindowManager.LayoutParams params;
    protected Context mContext;
    private int mWidth;
    private int mHeight;

    /**
     * 获取布局资源Id
     *
     * @return 布局资源Id
     */
    public abstract int getLayoutResId();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.style_dialog_fuzzy);
        mLayoutResId = getLayoutResId();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(mLayoutResId, container, false);
        convertView(ViewHolder.create(view), this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();
    }

    private void initParams() {
        Window window = getDialog().getWindow();
        if (window != null){
            params = window.getAttributes();
            params.dimAmount = mDimAmount;
            params.gravity = Gravity.CENTER;
            //设置dialog宽度
            if (mWidth == 0) {
                params.width = ScreenUtil.getScreenWidth() - 2 * DensityUtil.dp2px(mMargin);
            } else {
                params.width = DensityUtil.dp2px(mWidth);
            }

            //设置dialog高度
            if (mHeight == 0) {
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                params.height = DensityUtil.dp2px(mHeight);
            }
            //设置dialog动画
            if (mAnimStyle != 0) {
                window.setWindowAnimations(mAnimStyle);
            }
            window.setAttributes(params);
        }
        //是否点击空白处隐藏
        getDialog().setCanceledOnTouchOutside(mOutCancel);
        getDialog().setOnKeyListener((anInterface, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                //是否拦截返回按钮关闭对话框
                return false;
            }
            return false;
        });
    }
    /**
     * 设置背景昏暗度
     *
     * @param dimAmount
     * @return
     */
    public BaseFragmentDialog setDimAmount(@FloatRange(from = 0, to = 1) float dimAmount) {
        mDimAmount = dimAmount;
        return this;
    }
    /**
     * 弹出框位置
     */
    public BaseFragmentDialog setGravity(int gravity) {
        if (null != params) {
            params.gravity = gravity;
        }
        return this;
    }
    /**
     * 设置宽高
     *
     * @param width
     * @param height
     * @return
     */
    public BaseFragmentDialog setSize(int width, int height) {
        mWidth = width;
        mHeight = height;
        return this;
    }
    /**
     * 设置左右margin
     *
     * @param margin
     * @return
     */
    public BaseFragmentDialog setMargin(int margin) {
        mMargin = margin;
        return this;
    }
    /**
     * 设置进入退出动画
     *
     * @param animStyle
     * @return
     */
    public BaseFragmentDialog setAnimStyle(@StyleRes int animStyle) {
        mAnimStyle = animStyle;
        return this;
    }
    /**
     * 设置是否点击外部取消
     *
     * @param outCancel
     * @return
     */
    public BaseFragmentDialog setOutCancel(boolean outCancel) {
        mOutCancel = outCancel;
        return this;
    }
    public BaseFragmentDialog show(FragmentManager manager) {
        super.show(manager, String.valueOf(System.currentTimeMillis()));
        return this;
    }
    /**
     * 操作dialog布局
     *
     * @param holder
     * @param dialog
     */
    public abstract void convertView(ViewHolder holder, BaseFragmentDialog dialog);

}
