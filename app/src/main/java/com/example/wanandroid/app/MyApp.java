package com.example.wanandroid.app;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.example.mvpbase.base.BaseApp;
import com.example.mvpbase.utils.ThemeColorUtil;
import com.example.wanandroid.R;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 11:55
 */
public class MyApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        if (ThemeColorUtil.getThemeColorIndex() == 1){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        try {
            getSSLOkHttpClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置https 访问的时候对所有证书都进行信任
     *
     * @throws Exception
     */
    private OkHttpClient getSSLOkHttpClient() throws Exception {
        final X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        return new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier((hostname, session) -> true)
                .build();
    }

}
