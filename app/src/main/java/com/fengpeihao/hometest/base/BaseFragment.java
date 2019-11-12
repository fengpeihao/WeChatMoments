package com.fengpeihao.hometest.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.fengpeihao.hometest.widget.LoadingDialog;


/**
 * @author fengpeihao
 * Activity基类
 */
public abstract class BaseFragment<P extends BasePresenter> extends SimpleFragment implements BaseView {

    protected P mPresenter;
    private LoadingDialog mLoadingDialog;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = initPresenter();
    }

    protected abstract P initPresenter();

    @Override
    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void netError() {

    }

    @Override
    public void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog();
        }
        mLoadingDialog.show(getChildFragmentManager(), "loadingDialog");
    }

    @Override
    public void cancelLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelLoadingDialog();
    }
}
