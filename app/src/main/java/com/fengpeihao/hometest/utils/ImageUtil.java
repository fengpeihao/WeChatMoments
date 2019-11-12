package com.fengpeihao.hometest.utils;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.Util;
import com.fengpeihao.hometest.R;

import java.io.File;

/**
 * @author fengpeihao
 * 加载图片工具类
 */
public class ImageUtil {

    private static ImageUtil mImageUtil;

    public static ImageUtil getInstance() {
        if (mImageUtil == null) {
            synchronized (ImageUtil.class) {
                mImageUtil = new ImageUtil();
            }
        }
        return mImageUtil;
    }

    public void display(int resource, ImageView imageView) {
        if (imageView == null || imageView.getContext() == null) {
            return;
        }

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.empty)// 正在加载中的图片
                .error(R.mipmap.empty) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略
        if (Util.isOnMainThread()) {
            Glide.with(imageView.getContext()).load(resource)
                    .apply(options).into(imageView);
        }
    }

    public void display(String imageUrl, ImageView imageView) {
        if (imageView == null || imageView.getContext() == null) {
            return;
        }
        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = "http://www.baidu.com/img/bd_logo1.png";
        }

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.empty)// 正在加载中的图片
                .error(R.mipmap.empty) // 加载失败的图片
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略
        if (Util.isOnMainThread()) {
            Glide.with(imageView.getContext()).load(imageUrl)
                    .apply(options).into(imageView);
        }
    }

    public void displayEmptyPlaceHolder(String imageUrl, ImageView imageView) {
        if (imageView == null || imageView.getContext() == null) {
            return;
        }
        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = "http://www.baidu.com/img/bd_logo1.png";
        }

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略
        if (Util.isOnMainThread()) {
            Glide.with(imageView.getContext()).load(imageUrl)
                    .apply(options).into(imageView);
        }
    }

    public void displayNoDefault(String imageUrl, ImageView imageView) {
        if (imageView == null || imageView.getContext() == null) {
            return;
        }
        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = "http://www.baidu.com/img/bd_logo1.png";
        }

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略
        if (Util.isOnMainThread()) {
            Glide.with(imageView.getContext()).load(imageUrl)
                    .apply(options).into(imageView);
        }
    }

    public void display(String imageUrl, ImageView imageView, int resourceId) {
        if (imageView == null || imageView.getContext() == null) {
            return;
        }
        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = "http://www.baidu.com/img/bd_logo1.png";
        }

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.empty)// 正在加载中的图片
                .error(resourceId) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略
        if (Util.isOnMainThread()) {
            Glide.with(imageView.getContext()).load(imageUrl)
                    .apply(options).into(imageView);
        }
    }

    public void displayRoundPic(String imageUrl, ImageView imageView, int roundedCorner) {
        if (imageView == null || imageView.getContext() == null) {
            return;
        }
        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = "http://www.baidu.com/img/bd_logo1.png";
        }
        RoundedCorners roundedCorners = new RoundedCorners(roundedCorner);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                .placeholder(R.mipmap.empty)// 正在加载中的图片
                .error(R.mipmap.empty) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略
        if (Util.isOnMainThread()) {
            Glide.with(imageView.getContext()).load(imageUrl)
                    .apply(options).into(imageView);
        }
    }

    /**
     * 加载图片，等比缩放至指定规格
     *
     * @param imageUrl
     * @param imageView
     */
    public void displayWithScale(final String imageUrl, ImageView imageView) {
        if (imageView == null || imageView.getContext() == null) {
            return;
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.empty)// 正在加载中的图片
                .error(R.mipmap.empty) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略
        Glide.with(imageView.getContext()).asBitmap().load(imageUrl)
                .apply(options).into(imageView);
    }

    /**
     * 加载图片，等比缩放至指定规格
     *
     * @param file
     * @param imageView
     */
    public void displayWithScale(final File file, ImageView imageView) {
        if (imageView == null || imageView.getContext() == null) {
            return;
        }

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.empty)// 正在加载中的图片
                .error(R.mipmap.empty) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略
        Glide.with(imageView.getContext()).asBitmap().load(file)
                .apply(options).into(imageView);
    }


    public void displayCircle(String imageUrl, ImageView imageView, Integer errorImgResouce) {
        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = "http://www.baidu.com/img/bd_logo1.png";
        }
        if (errorImgResouce == null) {
            errorImgResouce = R.mipmap.empty;
        }
        RequestOptions options = RequestOptions.circleCropTransform()
                .placeholder(errorImgResouce)
                .error(errorImgResouce)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (Util.isOnMainThread()) {
            Glide.with(imageView.getContext()).load(imageUrl).
                    apply(options).into(imageView);
        }
    }
}
