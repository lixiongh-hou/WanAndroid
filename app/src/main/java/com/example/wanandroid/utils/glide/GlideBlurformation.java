package com.example.wanandroid.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;

/**
 * @author: 雄厚
 * Date: 2020/8/19
 * Time: 16:51
 */
public class GlideBlurformation extends BitmapTransformation {
    private Context context;
    public GlideBlurformation(Context context) {
        this.context = context;
    }
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return BlurBitmapUtil.instance().blurBitmap(context, toTransform, 25,outWidth,outHeight);
    }
    @Override
    public void updateDiskCacheKey(@NotNull MessageDigest messageDigest) {
    }

}