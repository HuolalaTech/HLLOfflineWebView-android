package com.lalamove.huolala.client.offline_web.widget;

import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.Nullable;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: HllDrawable
 * @author: kelvin
 * @date: 7/28/21
 * @description: 自定义Drawable
 * 主要增加 支持设置控件一半高度的圆角
 * @history:
 *
 */
public class HllDrawable extends GradientDrawable {

    /**
     * 圆角大小是否自适应为 View 的高度的一半
     */
    private boolean mIsRadiusHalfHeight = true;
    private ColorStateList mSolidColors;
    private int mStrokeWidth = 0;
    private ColorStateList mStrokeColors;

    /**
     * 设置按钮的背景色
     */
    public void setSolidColor(@Nullable ColorStateList colors) {
        mSolidColors = colors;
        super.setColor(colors);
    }

    /**
     * 设置按钮的描边粗细和颜色
     */
    public void setStrokeData(int width, @Nullable ColorStateList colors) {
        mStrokeWidth = width;
        mStrokeColors = colors;
        super.setStroke(width, colors);
    }

    /**
     * 设置圆角大小是否自动适应为 View 的高度的一半
     */
    public void setIsRadiusHalfHeight(boolean isRadiusHalfHeight) {
        mIsRadiusHalfHeight = isRadiusHalfHeight;
    }

    @Override
    protected boolean onStateChange(int[] stateSet) {
        boolean superRet = super.onStateChange(stateSet);
        if (mSolidColors != null) {
            int color = mSolidColors.getColorForState(stateSet, 0);
            setColor(color);
            superRet = true;
        }
        if (mStrokeColors != null) {
            int color = mStrokeColors.getColorForState(stateSet, 0);
            setStroke(mStrokeWidth, color);
            superRet = true;
        }
        return superRet;
    }

    @Override
    public boolean isStateful() {
        return (mSolidColors != null && mSolidColors.isStateful())
                || (mStrokeColors != null && mStrokeColors.isStateful())
                || super.isStateful();
    }

    @Override
    protected void onBoundsChange(Rect r) {
        super.onBoundsChange(r);
        if (mIsRadiusHalfHeight) {
            // 修改圆角为短边的一半
            setCornerRadius(Math.min(r.width(), r.height()) / 2.0f);
        }
    }
}