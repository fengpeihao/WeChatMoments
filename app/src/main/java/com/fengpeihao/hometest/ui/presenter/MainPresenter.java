package com.fengpeihao.hometest.ui.presenter;


import android.text.TextUtils;

import com.fengpeihao.hometest.api.CommonSubscriber;
import com.fengpeihao.hometest.ui.bean.TweetsBean;
import com.fengpeihao.hometest.ui.bean.UserInfoBean;
import com.fengpeihao.hometest.ui.contract.MainContract;
import com.fengpeihao.hometest.ui.model.MainModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author fengpeihao
 */
public class MainPresenter extends MainContract.Presenter {
    /**
     * 为了模拟分页效果
     */
    private List<TweetsBean> mTweetsBeans = new ArrayList<>();
    private boolean isGetUserInfoLoading, isGetTweetsLoading;

    public MainPresenter(MainContract.View view) {
        setVM(view, new MainModel(view));
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        isGetUserInfoLoading = true;
        mModel.getUserInfo(new CommonSubscriber<UserInfoBean>() {
            @Override
            public void getData(UserInfoBean userInfoBean) {
                isGetUserInfoLoading = false;
                mView.setUserInfo(userInfoBean);
                cancelLoading();
            }

            @Override
            public void netConnectError() {
                isGetUserInfoLoading = false;
                cancelLoading();
            }
        });
    }

    /**
     * 获取Tweets
     * @param pageIndex 页码
     * @param pageSize 每页条数
     * @param isLoadMore 是否是加载更多
     */
    public void getTweets(int pageIndex, int pageSize, boolean isLoadMore) {
        isGetTweetsLoading = true;
        if (mTweetsBeans.isEmpty()) {
            mModel.getTweets(new CommonSubscriber<List<TweetsBean>>() {
                @Override
                public void getData(List<TweetsBean> tweetsBeans) {
                    for (TweetsBean tweetsBean : tweetsBeans) {
                        if (tweetsBean.getSender() != null &&
                                ((tweetsBean.getImages() != null
                                        && tweetsBean.getImages().size() > 0)
                                        || !TextUtils.isEmpty(tweetsBean.getContent()))) {
                            mTweetsBeans.add(tweetsBean);
                        }
                    }
                    //第一次加载或下拉刷新，只显示前五条数据
                    mView.setTweets(mTweetsBeans.subList(0, pageSize), isLoadMore);
                    isGetTweetsLoading = false;
                    cancelLoading();
                }

                @Override
                public void netConnectError() {
                    isGetTweetsLoading = false;
                    cancelLoading();
                }
            });
        } else {
            if (!isLoadMore) {
                //第一次加载或下拉刷新，只显示前五条数据
                Observable.timer(300, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aLong -> {
                            mView.setTweets(mTweetsBeans.subList(0, pageSize), isLoadMore);
                            isGetTweetsLoading = false;
                            cancelLoading();
                        });
            } else {
                if (pageIndex <= mTweetsBeans.size() / pageSize) {
                    //加载更多 延迟500毫秒模拟网络请求
                    Observable.timer(300, TimeUnit.MILLISECONDS)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(aLong -> {
                                int toIndex = pageSize * pageIndex;
                                if (pageSize * pageIndex > mTweetsBeans.size()) {
                                    toIndex = mTweetsBeans.size();
                                }
                                mView.setTweets(mTweetsBeans.subList((pageIndex - 1) * pageSize, toIndex),
                                        isLoadMore);
                                isGetTweetsLoading = false;
                                cancelLoading();
                            });
                } else {
                    mView.setTweets(new ArrayList<>(), isLoadMore);
                    isGetTweetsLoading = false;
                    cancelLoading();
                }
            }
        }
    }

    /**
     * 取消loading
     */
    private void cancelLoading() {
        synchronized (MainPresenter.class) {
            if (!isGetUserInfoLoading && !isGetTweetsLoading) {
                mView.cancelLoadingDialog();
            }
        }
    }
}
