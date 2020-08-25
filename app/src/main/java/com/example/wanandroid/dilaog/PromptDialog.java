package com.example.wanandroid.dilaog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.example.mvpbase.R;
import com.example.mvpbase.utils.system.DensityUtil;
import com.example.wanandroid.utils.ThemeColorUtil;

/**
 * @author: 雄厚
 * Date: 2020/8/11
 * Time: 10:09
 */
public class PromptDialog {
    public static void showDialog(Context mContext, String title,String content, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        TextView tv = new TextView(mContext);
        tv.setText(title);
        tv.setTextSize(16);
        tv.setPadding(DensityUtil.dp2px(22), DensityUtil.dp2px(16), DensityUtil.dp2px(22), DensityUtil.dp2px(10));
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        AlertDialog alertDialog = builder
                .setNegativeButton("取消", null)
                .setCustomTitle(tv)
                .setMessage(content)
                .setPositiveButton("确定", onClickListener)
                .show();
        Button nButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nButton.setTextColor(ThemeColorUtil.getThemeColor(mContext));
        Button pButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pButton.setTextColor(ThemeColorUtil.getThemeColor(mContext));
    }

    public static void showDialog(Context mContext, String content, String cancel, String determine, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        TextView tv = new TextView(mContext);
        tv.setText(content);
        tv.setTextSize(18);
        tv.setPadding(DensityUtil.dp2px(16), DensityUtil.dp2px(16), DensityUtil.dp2px(16), DensityUtil.dp2px(16));
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        AlertDialog alertDialog = builder
                .setNegativeButton(cancel, null)
                .setCustomTitle(tv)
                .setPositiveButton(determine, onClickListener)
                .show();
        Button nButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nButton.setTextColor(ThemeColorUtil.getThemeColor(mContext));
        Button pButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pButton.setTextColor(ThemeColorUtil.getThemeColor(mContext));
    }

    public static void showDialog(Context mContext, String content, String cancel, String determine,
                                  DialogInterface.OnClickListener cancelOnClickListener,
                                  DialogInterface.OnClickListener determineClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        TextView tv = new TextView(mContext);
        tv.setText(content);
        tv.setTextSize(18);
        tv.setPadding(DensityUtil.dp2px(16), DensityUtil.dp2px(16), DensityUtil.dp2px(16), DensityUtil.dp2px(16));
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        AlertDialog alertDialog = builder
                .setNegativeButton(cancel, cancelOnClickListener)
                .setCustomTitle(tv)
                .setPositiveButton(determine, determineClickListener)
                .show();
        Button nButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nButton.setTextColor(ThemeColorUtil.getThemeColor(mContext));
        Button pButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pButton.setTextColor(ThemeColorUtil.getThemeColor(mContext));
    }
}
