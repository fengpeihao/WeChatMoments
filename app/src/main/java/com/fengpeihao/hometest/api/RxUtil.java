package com.fengpeihao.hometest.api;

import com.fengpeihao.hometest.base.BaseView;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author fengpeihao
 * 统一线程处理
 */
public class RxUtil {

    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper(final BaseView view) {
        if (view instanceof RxAppCompatActivity) {
            return observable -> observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(((RxAppCompatActivity)view).<T>bindUntilEvent(ActivityEvent.DESTROY));
        }else if(view instanceof RxFragment){
            return observable -> observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(((RxFragment)view).<T>bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        }else{
            return observable -> observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }
}
