package com.lalamove.huolala.client.offline_web.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;

import com.lalamove.huolala.client.offline_web.util.DisplayUtils;


/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: HllRoundBackground
 * @author: kelvin
 * @date: 7/27/21
 * @description:
 * @history: HllRoundBackground.with(this)
 * .solid(R.color.black_gray_color)//背景
 * .solid(ColorStateListBuilder.with(this)//背景
 * .pressed(R.color.orange)
 * .selected(R.color.address_save_bg)
 * .checked(R.color.blue)
 * .focused(R.color.colorPrimary)
 * .normal(R.color.colorPrimary).build())
 * .isRadiusHalfHeight(true)//控件高度的一半的圆角
 * .corner(5)//全部圆角为5dp
 * .corner(0,0,5,5)
 * .stroke(2, R.color.black_87_percent)//描边2dp
 * .stroke(2,ColorStateListBuilder.with(this)//描边
 * .pressed(R.color.color_green)
 * .selected(R.color.blue)
 * .checked(R.color.address_save_bg)
 * .focused(R.color.orange)
 * .unable(R.color.address_save_bg)
 * .normal(R.color.orange).build())//注意顺序
 * .into(tvB5);
 */

public class HllRoundBackground {

    private float mCornerTopLeft;
    private float mCornerTopRight;
    private float mCornerBottomLeft;
    private float mCornerBottomRight;
    private float mCornerAll;
    private int mStrokeWidth;
    private int mStrokeColor;
    private int mSolidColor;
    private int mWidth;
    private int mHeight;
    private boolean mIsRadiusHalfHeight;
    private final Context mApplicationContext;
    private ColorStateList mSolidColorStateList;
    private ColorStateList mStrokeColorList;
    private GradientColor mGradientColor;
    private int mShapeType;

    private HllRoundBackground(@NonNull Context context) {
        mApplicationContext = context.getApplicationContext();
    }

    public static HllRoundBackground with(Context context) {
        return new HllRoundBackground(context);
    }

    public HllRoundBackground shapeType(int shape) {
        mShapeType = shape;
        return this;
    }

    public HllRoundBackground corner(int topLeftDp, int topRightDp, int bottomLeftDp, int bottomRightDp) {
        mCornerTopLeft = DisplayUtils.dp2px(mApplicationContext, topLeftDp);
        mCornerTopRight = DisplayUtils.dp2px(mApplicationContext, topRightDp);
        mCornerBottomLeft = DisplayUtils.dp2px(mApplicationContext, bottomLeftDp);
        mCornerBottomRight = DisplayUtils.dp2px(mApplicationContext, bottomRightDp);
        return this;
    }

    public HllRoundBackground corner(int allDp) {
        mCornerAll = DisplayUtils.dp2px(mApplicationContext, allDp);
        return this;
    }

    public HllRoundBackground cornerWithPx(float topLeft, float topRight, float bottomLeft, float bottomRight) {
        mCornerTopLeft = topLeft;
        mCornerTopRight = topRight;
        mCornerBottomLeft = bottomLeft;
        mCornerBottomRight = bottomRight;
        return this;
    }

    public HllRoundBackground cornerWithPx(float all) {
        mCornerAll = all;
        return this;
    }

    public HllRoundBackground stroke(int widthDp, @ColorRes int color) {
        mStrokeWidth = DisplayUtils.dp2px(mApplicationContext, widthDp);
        mStrokeColor = color;
        return this;
    }

    public HllRoundBackground stroke(int widthDp, ColorStateList color) {
        mStrokeWidth = DisplayUtils.dp2px(mApplicationContext, widthDp);
        mStrokeColorList = color;
        return this;
    }

    public HllRoundBackground strokeWithPx(int width, @ColorRes int color) {
        mStrokeWidth = width;
        mStrokeColor = color;
        return this;
    }

    public HllRoundBackground strokeWithPx(int width, ColorStateList color) {
        mStrokeWidth = width;
        mStrokeColorList = color;
        return this;
    }

    public HllRoundBackground solid(@ColorRes int color) {
        mSolidColor = color;
        return this;
    }

    public HllRoundBackground solid(ColorStateList colorStateList) {
        mSolidColorStateList = colorStateList;
        return this;
    }

    public HllRoundBackground size(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
        return this;
    }

    public HllRoundBackground gradient(GradientColor gColor) {
        mGradientColor = gColor;
        return this;
    }

    /**
     * 圆角大小是否自适应为 View 的高度的一半
     */
    public HllRoundBackground isRadiusHalfHeight(boolean isRadiusHalfHeight) {
        mIsRadiusHalfHeight = isRadiusHalfHeight;
        return this;
    }

    private int getColor(@ColorRes int color) {
        return mApplicationContext.getResources().getColor(color);
    }

    public Drawable drawable() {
        if (mApplicationContext == null) {
            return null;
        }
        HllDrawable hllDrawable = new HllDrawable();
        hllDrawable.setShape(mShapeType);
        //设置背景
        if (mSolidColorStateList != null) {
            hllDrawable.setSolidColor(mSolidColorStateList);
        } else if (mSolidColor != 0) {
            hllDrawable.setSolidColor(ColorStateList.valueOf(getColor(mSolidColor)));
        } else if (mGradientColor != null) {
            mGradientColor.into(hllDrawable);
        }

        //设置描边
        if (mStrokeWidth != 0) {
            if (mStrokeColorList != null) {
                hllDrawable.setStrokeData(mStrokeWidth, mStrokeColorList);
            } else if (mStrokeColor != 0) {
                hllDrawable.setStrokeData(mStrokeWidth, ColorStateList.valueOf(getColor(mStrokeColor)));
            }
        }

        //设置圆角
        if (mCornerTopLeft > 0 || mCornerTopRight > 0 || mCornerBottomLeft > 0 || mCornerBottomRight > 0) {
            float[] radii = new float[]{
                    mCornerTopLeft, mCornerTopLeft,
                    mCornerTopRight, mCornerTopRight,
                    mCornerBottomRight, mCornerBottomRight,
                    mCornerBottomLeft, mCornerBottomLeft
            };
            hllDrawable.setCornerRadii(radii);
            mIsRadiusHalfHeight = false;

        } else {
            if (mCornerAll > 0) {
                hllDrawable.setCornerRadius(mCornerAll);
                mIsRadiusHalfHeight = false;
            }
        }
        if (mWidth > 0 || mHeight > 0) {
            hllDrawable.setSize(mWidth, mHeight);
        }
        hllDrawable.setIsRadiusHalfHeight(mIsRadiusHalfHeight);
        return hllDrawable;
    }

    public void into(View view) {
        if (view != null) {
            view.setBackground(drawable());
        }
    }

}
