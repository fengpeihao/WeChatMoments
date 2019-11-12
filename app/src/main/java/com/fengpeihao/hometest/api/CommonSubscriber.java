package com.fengpeihao.hometest.api;


import io.reactivex.subscribers.ResourceSubscriber;

/**
 * @author fengpeihao
 * 统一请求返回出来
 */
public abstract class CommonSubscriber<T extends Object> extends ResourceSubscriber<T> {

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        netConnectError();
    }

    @Override
    public void onNext(T t) {
        //成功
        getData(t);
    }

    public abstract void getData(T t);

    //网络访问失败
    public abstract void netConnectError();

}
