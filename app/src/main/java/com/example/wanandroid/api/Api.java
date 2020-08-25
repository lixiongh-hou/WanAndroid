package com.example.wanandroid.api;

import com.example.mvpbase.logger.LoggingInterceptor;
import com.example.wanandroid.api.logger.LoadCookieInterceptor;
import com.example.wanandroid.api.logger.SaveCookieInterceptor;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 15:36
 */
public class Api {
    private volatile static Api mApi;

    public static Api getInstance() {
        if (null == mApi) {
            synchronized (Api.class) {
                if (null == mApi) {
                    mApi = new Api();
                }
            }
        }
        return mApi;
    }
    private Retrofit mRetrofit;
    public ApiService mService;
    private Api() {
        // 创建Retrofit
        mRetrofit = createRetrofit();
        // 服务接口
        mService = mRetrofit.create(ApiService.class);
    }
    /**
     * 创建相应的服务接口
     */
    public <T> T create(Class<T> service) {
        checkNotNull(service);
        return mRetrofit.create(service);
    }

    private <T> void checkNotNull(T object) {
        if (object == null) {
            throw new NullPointerException("service is null");
        }
    }
    /**
     * 创建Retrofit
     */
    private Retrofit createRetrofit() {
        // OkHttpClient
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                // 连接超时
                .connectTimeout(100, TimeUnit.SECONDS)
                // 写入超时
                .writeTimeout(100, TimeUnit.SECONDS)
                // 读取超时
                .readTimeout(100, TimeUnit.SECONDS)
                //添加Cookie拦截器 登录判断是否登录
                .addInterceptor(new SaveCookieInterceptor())
                .addInterceptor(new LoadCookieInterceptor())
                // 拦截并打印数据的拦截器
                .addInterceptor(new LoggingInterceptor.Builder()
                        .isDebug(true)
                        // Request
                        .setRequestTag("请求")
                        // Response
                        .setResponseTag("响应")
                        // application/json;charset=utf-8
                        // application/x-www-form-urlencoded;charset=utf-8
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Content-Encoding", "gzip").build());
        // 创建Retrofit
        return new Retrofit.Builder()
                // OkHttpClient
                .client(client.build())
                // BaseUrl
                .baseUrl(UrlParam.Base.HOST)
                // 基本数据类型转换器
                .addConverterFactory(ScalarsConverterFactory.create())
                // Gson转换器
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                // RxJava2回调适配器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                // 构造
                .build();
    }
}
