package com.fengpeihao.hometest.app;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.fengpeihao.hometest.api.UnsafeOkHttpClient;

import java.io.InputStream;

/**
 * @author fengpeihao
 */
public class MyApplication extends Application {

    public static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //加载https图片忽略证书
        Glide.get(this).getRegistry().replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(UnsafeOkHttpClient.getOkHttpClient()));
    }
}
