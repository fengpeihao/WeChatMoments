package com.fengpeihao.hometest.api;


import com.fengpeihao.hometest.constant.Constants;
import com.fengpeihao.hometest.ui.bean.TweetsBean;
import com.fengpeihao.hometest.ui.bean.UserInfoBean;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * @author fengpeihao
 *
 */
public interface ApiService {

    @GET(Constants.USER_INFO)
    Flowable<UserInfoBean> getUserInfo();

    @GET(Constants.TWEETS)
    Flowable<List<TweetsBean>> getTeeets();
}
