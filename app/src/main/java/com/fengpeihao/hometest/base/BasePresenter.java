package com.fengpeihao.hometest.base;

/**
 * @author fengpeihao
 * @param <V> view
 * @param <M> model
 * presenter基类
 */
public abstract class BasePresenter<V extends BaseView,M extends BaseModel> {

    protected M mModel;

    protected V mView;

    /**
     * 关联 view、model
     * @param view
     * @param model
     */
    public void setVM(V view,M model){
        mView = view;
        mModel = model;
    }
}
