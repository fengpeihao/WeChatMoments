package com.fengpeihao.hometest.ui.model;


import com.fengpeihao.hometest.api.CommonSubscriber;
import com.fengpeihao.hometest.api.RxUtil;
import com.fengpeihao.hometest.base.BaseView;
import com.fengpeihao.hometest.ui.bean.TweetsBean;
import com.fengpeihao.hometest.ui.bean.UserInfoBean;
import com.fengpeihao.hometest.ui.contract.MainContract;

import java.util.List;

/**
 * @author fengpeihao
 */
public class MainModel implements MainContract.Model {

    private final BaseView mView;

    public MainModel(BaseView view) {
        this.mView = view;
    }

    /**
     * 获取用户信息
     * @param subscriber
     */
    @Override
    public void getUserInfo(CommonSubscriber<UserInfoBean> subscriber) {
        mService.getUserInfo().compose(RxUtil.rxSchedulerHelper(mView)).subscribeWith(subscriber);
    }

    /**
     * 获取Tweets
     * @param subscriber
     */
    @Override
    public void getTweets(CommonSubscriber<List<TweetsBean>> subscriber){
        mService.getTeeets().compose(RxUtil.rxSchedulerHelper(mView)).subscribeWith(subscriber);
    }
}
