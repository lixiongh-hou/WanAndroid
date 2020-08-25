package com.example.wanandroid.api.logger;

import android.text.TextUtils;

import com.example.mvpbase.utils.constant.ConstantUtil;
import com.example.mvpbase.utils.system.SPUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: 雄厚
 * Date: 2020/8/11
 * Time: 9:53
 * 添加登录状态拦截器
 */
public class LoadCookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        String mCookieStr = (String) SPUtil.get(ConstantUtil.COOKIE, "");
        if (!TextUtils.isEmpty(mCookieStr)) {
            //长度减1为了去除最后的逗号
            builder.addHeader("Cookie", mCookieStr.substring(0, mCookieStr.length() - 1));
        }
        return chain.proceed(builder.build());
    }
}
