package com.fengpeihao.hometest.base;

/**
 * @author fengpeihao
 * view基类
 */
public interface BaseView {

    void showToast(String msg);

    void netError();

    void showLoadingDialog();

    void cancelLoadingDialog();
}
