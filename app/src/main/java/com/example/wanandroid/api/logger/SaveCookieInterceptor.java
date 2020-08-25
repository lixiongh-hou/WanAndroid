package com.example.wanandroid.api.logger;

import android.util.Log;

import com.example.mvpbase.utils.constant.ConstantUtil;
import com.example.mvpbase.utils.system.SPUtil;
import com.example.wanandroid.api.UrlParam;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: 雄厚
 * Date: 2020/8/11
 * Time: 9:49
 * 保存登录状态拦截器
 */
public class SaveCookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        List<String> mCookieList = response.headers("Set-Cookie");
        //保存Cookie
        if (!mCookieList.isEmpty() && request.url().toString().endsWith(UrlParam.LogIn.URL)) {
            StringBuilder sb = new StringBuilder();
            for (String cookie : mCookieList) {
                //注意Cookie请求头字段中的每个Cookie之间用逗号或分号分隔
                sb.append(cookie).append(",");
            }
            SPUtil.put(ConstantUtil.COOKIE, sb.toString());
            Log.e(SaveCookieInterceptor.class.getSimpleName(), "intercept: url : " + request.url());
        }
        return response;
    }
}
