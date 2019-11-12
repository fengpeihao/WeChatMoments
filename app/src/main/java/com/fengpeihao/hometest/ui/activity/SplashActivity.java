package com.fengpeihao.hometest.ui.activity;

import com.fengpeihao.hometest.R;
import com.fengpeihao.hometest.base.SimpleActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * @author fengpeihao
 * 启动页
 */
public class SplashActivity extends SimpleActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {
        //延迟0.5秒进入主页
        Observable.timer(500, TimeUnit.MILLISECONDS).subscribe(aLong -> {
            startActivity(MainActivity.class);
            finish();
        });
    }
}
