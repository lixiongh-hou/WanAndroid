package com.example.wanandroid.dilaog;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.example.mvpbase.R;
import com.example.mvpbase.dialog.BaseFragmentDialog;
import com.example.mvpbase.dialog.ViewHolder;
import com.example.mvpbase.utils.check.CheckUtil;
import com.example.mvpbase.utils.ThemeColorUtil;

/**
 * @author: 雄厚
 * Date: 2020/8/10
 * Time: 17:18
 * 加载中的对话框
 */
public class LoadingDialog extends BaseFragmentDialog {
    private String tip;
    public static LoadingDialog newInstance(String tip) {
        Bundle bundle = new Bundle();
        bundle.putString("tip", tip);
        LoadingDialog dialog = new LoadingDialog();
        dialog.setOutCancel(false)
                .setAnimStyle(R.style.DialogCentreAnim)
                .setMargin(80);
        dialog.setArguments(bundle);
        return dialog;
    }
    public static LoadingDialog newInstance() {
        LoadingDialog dialog = new LoadingDialog();
        dialog.setOutCancel(false)
                .setAnimStyle(R.style.DialogCentreAnim)
                .setMargin(80);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        tip = bundle.getString("tip");
    }

    @Override
    public int getLayoutResId() {
        return R.layout.dialog_loading;
    }

    @Override
    public void convertView(ViewHolder holder, BaseFragmentDialog dialog) {
        if (CheckUtil.isNotEmpty(tip)){
            holder.setText(R.id.tvTip, tip);
        }
        ProgressBar bar = holder.getView(R.id.progressbar);
        bar.setIndeterminateTintList(ColorStateList.valueOf(ThemeColorUtil.getThemeColor(mContext)));
    }

//    private static AlertDialog mAlertDialog;
//    /**
//     * 弹出耗时对话框
//     * @param context
//     */
//    public static void showProgressDialog(Context context) {
//        if (mAlertDialog == null) {
//            mAlertDialog = new AlertDialog.Builder(context, R.style.commonStyleDialog).create();
//        }
//
//        View loadView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
//        mAlertDialog.setView(loadView, 0, 0, 0, 0);
//        mAlertDialog.setCanceledOnTouchOutside(false);
//        TextView tvTip = loadView.findViewById(R.id.tvTip);
//        tvTip.setText("加载中...");
//
//        mAlertDialog.show();
//    }
//
//    public static void showProgressDialog(Context context, String tip) {
//        if (TextUtils.isEmpty(tip)) {
//            tip = "加载中...";
//        }
//        if (mAlertDialog == null) {
//            mAlertDialog = new AlertDialog.Builder(context, R.style.commonStyleDialog).create();
//        }
//        View loadView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
//        mAlertDialog.setView(loadView, 0, 0, 0, 0);
//        mAlertDialog.setCanceledOnTouchOutside(false);
//
//        TextView tvTip = loadView.findViewById(R.id.tvTip);
//        tvTip.setText(tip);
//
//        mAlertDialog.show();
//    }
//
//    /**
//     * 隐藏耗时对话框
//     */
//    public static void dismiss() {
//        if (mAlertDialog != null && mAlertDialog.isShowing()) {
//            mAlertDialog.dismiss();
//        }
//    }


}
