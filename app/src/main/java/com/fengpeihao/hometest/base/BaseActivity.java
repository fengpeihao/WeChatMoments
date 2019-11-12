package com.fengpeihao.hometest.base;

import android.widget.Toast;

import com.fengpeihao.hometest.widget.LoadingDialog;

/**
 * @author fengpeihao
 * Activity基类
 */
public abstract class BaseActivity<P extends BasePresenter> extends SimpleActivity implements BaseView {

    protected P mPresenter;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onViewCreated() {
        mPresenter = initPresenter();
        super.onViewCreated();
    }

    protected abstract P initPresenter();

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void netError() {

    }

    @Override
    public void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog();
        }
        mLoadingDialog.show(getSupportFragmentManager(), "loadingDialog");
    }

    @Override
    public void cancelLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelLoadingDialog();
    }
}
