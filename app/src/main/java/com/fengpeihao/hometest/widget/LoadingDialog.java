package com.fengpeihao.hometest.widget;

import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fengpeihao.hometest.R;

/**
 * @author fengpeihao
 * loading动画
 */
public class LoadingDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext(), R.style.loading_dialog);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.dialog_loading, null);
        ImageView spaceshipImage = v.findViewById(R.id.iv_loading);
        // 加载动画
        AnimationDrawable drawable = (AnimationDrawable) spaceshipImage.getDrawable();
        drawable.setOneShot(false);
        drawable.start();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);// 设置是否可以用“返回键”取消
        dialog.setContentView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return dialog;
    }
}
