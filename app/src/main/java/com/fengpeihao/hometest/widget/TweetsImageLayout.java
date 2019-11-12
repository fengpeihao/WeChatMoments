package com.fengpeihao.hometest.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fengpeihao.hometest.ui.bean.TweetsBean;
import com.fengpeihao.hometest.utils.ImageUtil;

import java.util.List;

/**
 * @author fengpeihao
 * tweeter图片展示
 */
public class TweetsImageLayout extends ViewGroup {
    /**
     * 显示的行数
     */
    private int mColumnCount;
    /**
     * 默认间距
     */
    private final float DEFAULT_SPACING = dip2px(1.5f);
    private float mSpacing;
    /**
     * 图片宽高比
     */
    private float mItemAspectRatio;
    /**
     * 最宽的时候相对可用空间比例（当childCount=1的时候）
     * 当只有一张图片的 最大显示宽度和高度相对于可用宽的的比例
     */
    private final float MAX_WIDTH_PERCENTAGE = 270f / 350;

    private int mItemWidth;
    private int mItemHeight;


    public TweetsImageLayout(Context context) {
        this(context, null);
    }

    public TweetsImageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TweetsImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSpacing = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_SPACING,
                context.getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int count = getChildCount();
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        //当只有一张图片的时候
        if (count == 1) {
            mColumnCount = 1;

            int mItemMaxWidth = (int) (width * MAX_WIDTH_PERCENTAGE);
            int mItemMaxHeight = mItemMaxWidth;
            if (mItemAspectRatio < 1) {
                mItemHeight = mItemMaxHeight;
                mItemWidth = (int) (mItemHeight * mItemAspectRatio);
            } else {
                mItemWidth = mItemMaxWidth;
                mItemHeight = (int) (mItemMaxWidth / mItemAspectRatio);
            }
        } else {
            if (count==2||count == 4) {//等于2张或4张的时候
                mColumnCount = 2;
            } else {
                mColumnCount = 3;
            }
            mItemWidth = (int) ((width - getPaddingLeft() - getPaddingRight() - (mColumnCount - 1) * mSpacing) / mColumnCount);
            mItemHeight = (int) (mItemWidth / mItemAspectRatio);
        }

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = mItemWidth;
            layoutParams.height = mItemHeight;
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
        }

        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    getDesiredHeight(mItemHeight), MeasureSpec.EXACTLY);
        }

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(
                    getDesiredWidth(mItemWidth), MeasureSpec.EXACTLY), heightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec,
                                int parentHeightMeasureSpec) {
        final LayoutParams lp = child.getLayoutParams();
        //获取子控件的宽高约束规则
        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                getPaddingLeft() + getPaddingRight(), lp.width);
        final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
                getPaddingLeft() + getPaddingRight(), lp.height);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    private int getDesiredHeight(int mItemHeight) {
        int totalHeight = getPaddingTop() + getPaddingBottom();
        int count = getChildCount();
        if (count > 0) {
            int row = (count - 1) / mColumnCount;
            totalHeight = (int) ((row + 1) * mItemHeight + (row) * mSpacing) + totalHeight;
        }
        return totalHeight;
    }

    private int getDesiredWidth(int mItemWidth) {
        int totalWidth = getPaddingLeft() + getPaddingRight();
        int count = getChildCount();
        if (count > 0) {
            if (count < mColumnCount) {
                totalWidth = (int) (count * mItemWidth + (count - 1) * mSpacing) + totalWidth;
            } else {
                totalWidth = (int) (count * mItemWidth + (count - 1) * mSpacing) + totalWidth;
            }

        }
        return totalWidth;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View imageView = getChildAt(i);

            int column = i % mColumnCount;
            int row = i / mColumnCount;
            int left = (int) (getPaddingLeft() + column * (mSpacing + mItemWidth));
            int top = (int) (getPaddingTop() + row * (mSpacing + mItemHeight));

            imageView.layout(left, top, left + mItemWidth, top + mItemHeight);
        }
    }


    /**
     * 显示图片
     */
    public void setImageUrls(final List<TweetsBean.ImagesBean> imageUrls) {
        removeAllViews();
        if (imageUrls == null || imageUrls.size() == 0) {
            return;
        }

        int count = imageUrls.size();
        if (count == 1) {
            //一般在url中会有宽高 可以算出宽高比
            mItemAspectRatio = 1000 / 1376f;
        } else {
            mItemAspectRatio = 1;
        }

        for (int i = 0; i < imageUrls.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageUtil.getInstance().display(imageUrls.get(i).getUrl(), imageView);
            addView(imageView);
            //点击查看大图
            imageView.setOnClickListener(v -> Log.e("onclick", "***"));
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}