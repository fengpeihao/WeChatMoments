package com.fengpeihao.hometest.base;


import com.fengpeihao.hometest.api.ApiModule;
import com.fengpeihao.hometest.api.ApiService;

/**
 * @author fengpeihao
 * model基类
 */
public interface BaseModel {
    ApiService mService = ApiModule.getInstance().provideApiService();
}
