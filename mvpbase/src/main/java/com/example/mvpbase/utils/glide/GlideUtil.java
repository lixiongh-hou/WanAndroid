package com.example.mvpbase.utils.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvpbase.R;

/**
 * @author: 雄厚
 * Date: 2020/8/7
 * Time: 16:38
 */
public final class GlideUtil {

    private GlideUtil() {
        // 这个类不能实例化
    }

    /**
     * 优先级,低->高: Priority.LOW Priority.NORMAL Priority.HIGH Priority.IMMEDIATE
     * .priority(Priority.NORMAL)
     * <p>
     * 图片显示方式(填满控件且图片不变形,但会显示不全)
     * .centerCrop()
     * <p>
     * 没有动画,直接显示图片
     * .dontAnimate()
     * <p>
     * 加载中显示
     * .placeholder(imgResId)
     * <p>
     * 加载失败显示
     * .error(imgResId)
     * <p>
     * 调整图像尺寸(像素),忽略比例
     * .override(600, 200)
     * <p>
     * 跳过内存缓存
     * .skipMemoryCache(true)
     * <p>
     * 磁盘高速缓存策略
     * .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
     * ____默认的策略是DiskCacheStrategy.AUTOMATIC
     * ____DiskCacheStrategy有五个常量:
     * ________DiskCacheStrategy.ALL   使用DATA和RESOURCE缓存远程数据,仅使用RESOURCE来缓存本地数据.
     * ________DiskCacheStrategy.NONE  不使用磁盘缓存
     * ________DiskCacheStrategy.DATA  在资源解码前就将原始数据写入磁盘缓存
     * ________DiskCacheStrategy.RESOURCE  在资源解码后将数据写入磁盘缓存,即经过缩放等转换后的图片资源.
     * ________DiskCacheStrategy.AUTOMATIC 根据原始图片数据和资源编码策略来自动选择磁盘缓存策略.
     */

    /*--------------------------------------------普通图片加载配置*/
    private static RequestOptions normalOptions = new RequestOptions()
            .placeholder(R.drawable.not_log_in_icon)
            .error(R.drawable.error_img_square)
            .dontAnimate()
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

    private static RequestOptions errorOptions = new RequestOptions()
            // .error(R.mipmap.error_img)
            .dontAnimate()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    /////////////////////////////////////
    // 普通图片
    /////////////////////////////////////

    /**
     * @param context 上下文
     * @param path    图片
     * @param imgView 控件
     */
    public static void loadImg(Context context, Object path, ImageView imgView) {
        loadImg(context, path, imgView, null);
    }

    /**
     * @param context  上下文
     * @param path     图片
     * @param imgView  控件
     * @param listener 成功失败的回调
     */
    public static void  loadImg(Context context, Object path, ImageView imgView, RequestListener<Drawable> listener) {
        if (showImage(context, path, imgView)) {
            Glide.with(context)
                    .load(path)
                    .apply(normalOptions)
                    // 淡入动画.transition(new DrawableTransitionOptions().crossFade())
                    // 缩略图.thumbnail(0.1f)
                    .listener(listener)
                    .into(imgView);
        }
    }
    /**
     * @param context 上下文
     * @param path    图片
     * @param imgView 控件
     */
    public static void loadImgCenterCrop(Context context, Object path, ImageView imgView) {
        loadImgCenterCrop(context, path, imgView, null);
    }

    /**
     * 裁剪的
     * @param context  上下文
     * @param path     图片
     * @param imgView  控件
     * @param listener 成功失败的回调
     */
    public static void  loadImgCenterCrop(Context context, Object path, ImageView imgView, RequestListener<Drawable> listener) {
        if (showImage(context, path, imgView)) {
            Glide.with(context)
                    .load(path)
                    .apply(normalOptions)
                    .centerCrop()

                    // 淡入动画.transition(new DrawableTransitionOptions().crossFade())
                    // 缩略图.thumbnail(0.1f)
                    .listener(listener)
                    .into(imgView);
        }
    }

    /**
     * @param context  上下文
     * @param path     图片
     * @param imgView  控件
     * @param errorImg 错误图片
     */
    public static void loadImg(Context context, Object path, ImageView imgView, @DrawableRes int errorImg) {
        if (showImage(context, path, imgView)) {
            Glide.with(context)
                    .load(path)
                    .apply(errorOptions.error(errorImg))
                    .into(imgView);
        }
    }

    /**
     * @return true:显示图片
     */
    private static boolean showImage(Context context, Object path, ImageView imgView) {
        if (null == context) {
            return false;
        } else if (null == imgView) {
            return false;
        } else if (null == path) {
            path = "";
        }
        Object tag = imgView.getTag(R.id.glide_img_tag);
        if (path.equals(tag)) {
            return false;
        } else {
            imgView.setTag(R.id.glide_img_tag, path);
            return true;
        }
    }

//    /**
//     * 高斯模糊
//     */
//    public static void loadImg(Context mContext, Object imgObj, ImageView mImageView, int radius, int sampling) {
//        GlideApp.with(mContext)
//                .load(imgObj)
//                .apply(RequestOptions.bitmapTransform(new BlurTransformation(radius, sampling)))
//                .into(mImageView);
//    }

//    /*--------------------------------------------圆形图片加载配置*/
//    static RequestOptions circleOptions = new RequestOptions()
//            .placeholder(R.mipmap.photo_loading)
//            .error(R.mipmap.photopicker_delete)
//            .skipMemoryCache(true)
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            //
//            .transform(new GlideCircleTransform());

    /////////////////////////////////////
    // 圆形图片
    /////////////////////////////////////
//    public static void loadCircleImg(Context mContext, Object object, ImageView mImageView) {
//        loadCircleImg(mContext, object, 0.1f, mImageView);
//    }
//    public static void loadCircleImg(Context mContext, Object object, float sizeMultiplier, ImageView mImageView) {
//        GlideApp.with(mContext).load(object).apply(circleOptions)
//                .transition(new DrawableTransitionOptions().crossFade())
//                .thumbnail(sizeMultiplier).into(mImageView);
//    }

//    /*--------------------------------------------圆角图片加载配置*/
//    static RequestOptions roundOptions = new RequestOptions()
//            .placeholder(R.mipmap.photo_loading)
//            .error(R.mipmap.photopicker_delete)
//            .skipMemoryCache(true)
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            //
//            .transform(new GlideRoundTransform());

    /////////////////////////////////////
    // 圆角图片
    /////////////////////////////////////
//    public static void loadRoundImg(Context mContext, Object object, ImageView mImageView) {
//        loadRoundImg(mContext, object, 5f, mImageView);
//    }
//    public static void loadRoundImg(Context mContext, Object object, float radiusDp, ImageView mImageView) {
//        GlideApp.with(mContext).load(object).apply(roundOptions.transform(new GlideRoundTransform(DensityUtil.dp2px(radiusDp))))
//                .transition(new DrawableTransitionOptions().crossFade())
//                .thumbnail(0.1f).into(mImageView);
//    }

}