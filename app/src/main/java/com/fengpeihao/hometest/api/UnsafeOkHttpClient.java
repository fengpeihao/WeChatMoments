package com.fengpeihao.hometest.api;

import android.util.Log;

import com.fengpeihao.hometest.BuildConfig;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author fengpeihao
 * 忽略证书
 */
public class UnsafeOkHttpClient {

    /**
     * 获取忽略证书的 OkHttpClient
     * @return
     */
    public static OkHttpClient getOkHttpClient() {
        //原生Log日志拦截
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.e("print", "okhttp=>" + message));
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .sslSocketFactory(ignoreSSLSocketFactory())
                .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                .addInterceptor(loggingInterceptor)
                //其他配置
                .build();
    }

    /**
     * 忽略https证书验证
     */
    private static SSLSocketFactory ignoreSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取TrustManager
     *
     * @return
     */
    private static javax.net.ssl.TrustManager[] getTrustManager() {
        return new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        }};
    }
}
