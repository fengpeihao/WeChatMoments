package com.fengpeihao.hometest.ui.contract;


import com.fengpeihao.hometest.api.CommonSubscriber;
import com.fengpeihao.hometest.base.BaseModel;
import com.fengpeihao.hometest.base.BasePresenter;
import com.fengpeihao.hometest.base.BaseView;
import com.fengpeihao.hometest.ui.bean.TweetsBean;
import com.fengpeihao.hometest.ui.bean.UserInfoBean;

import java.util.List;

/**
 * @author fengpeihao
 * MainActivity契约类
 */
public interface MainContract {
    interface View extends BaseView {
        void setUserInfo(UserInfoBean userInfoBean);

        void setTweets(List<TweetsBean> tweetsBeans, boolean isLoadMore);
    }

    interface Model extends BaseModel {
        void getUserInfo(CommonSubscriber<UserInfoBean> subscriber);

        void getTweets(CommonSubscriber<List<TweetsBean>> subscriber);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getUserInfo();

        public abstract void getTweets(int pageIndex, int pageSize, boolean isLoadMore);
    }
}
