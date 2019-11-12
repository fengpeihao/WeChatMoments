package com.fengpeihao.hometest.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author fengpeihao
 */
public class ApiModule {

    private static ApiModule mApiModule;
    private static Retrofit mRetrofit;
    /**
     * 网络地址
     */
    private static final String BASE_HOST = "http://thoughtworks-ios.herokuapp.com";

    private ApiModule() {
    }

    public static ApiModule getInstance() {
        if (mApiModule == null) {
            synchronized (ApiModule.class) {
                if (mApiModule == null) {
                    mApiModule = new ApiModule();
                    init();
                }
            }
        }
        return mApiModule;
    }

    /**
     * 网络框架 init
     */
    private static void init() {
        //Retrofit初始化
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_HOST)
                .client(UnsafeOkHttpClient.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public ApiService provideApiService() {
        return mRetrofit.create(ApiService.class);
    }
}
