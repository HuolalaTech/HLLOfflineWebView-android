package com.lalamove.huolala.client.offline_web.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.ColorRes;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: GradientColor
 * @author: huangyuchen
 * @date: 8/10/21
 * @description: 一个产生渐变色，并将其设置到GradientDrawable中的帮助类
 * @history:
 */
public class GradientColor {
    private int type;
    private float centerX;
    private float centerY;
    private float radius;
    private int[] colors;
    private GradientDrawable.Orientation orientation;

    private GradientColor() {

    }

    public void into(GradientDrawable drawable) {
        drawable.setGradientType(type);
        //GradientCenter只有在非LINEAR_GRADIENT下起作用
        if (type != GradientDrawable.LINEAR_GRADIENT) {
            drawable.setGradientCenter(centerX, centerY);
        }
        //GradientRadius只有在RADIAL_GRADIENT下起作用
        if (type == GradientDrawable.RADIAL_GRADIENT) {
            drawable.setGradientRadius(radius);
        }
        if (type == GradientDrawable.LINEAR_GRADIENT) {
            drawable.setOrientation(orientation);
        }
        drawable.setColors(colors);
    }

    public static class Builder {
        private int type;
        private float centerX;
        private float centerY;
        private float radius = -1;
        private GradientDrawable.Orientation orientation;
        private Context appContext;
        private int startColor;
        private int centerColor;
        private int endColor;
        private int[] mColors;

        public static Builder with(Context context, int type) {
            return new Builder(context, type);
        }

        private Builder(Context context, int type) {
            if (type < 0) {
                type = GradientDrawable.LINEAR_GRADIENT;
            }
            this.type = type;
            this.appContext = context.getApplicationContext();
        }

        public Builder center(float centerX, float centerY) {
            if (centerX <= 0) {
                centerX = 0.5f;
            }
            if (centerY <= 0) {
                centerY = 0.5f;
            }
            this.centerX = centerX;
            this.centerY = centerY;
            return this;
        }

        public Builder radius(float radius) {
            this.radius = radius;
            return this;
        }

        public Builder startColor(@ColorRes int color) {
            startColor = appContext.getResources().getColor(color);
            return this;
        }

        public Builder centerColor(@ColorRes int color) {
            centerColor = appContext.getResources().getColor(color);
            return this;
        }

        public Builder endColor(@ColorRes int color) {
            endColor = appContext.getResources().getColor(color);
            return this;
        }

        public Builder colors(@ColorRes int[] colors) {
            if (colors != null && colors.length >= 2) {
                mColors = new int[colors.length];
                Resources res = appContext.getResources();
                for (int i = 0; i < colors.length; i++) {
                    mColors[i] = res.getColor(colors[i]);
                }
            }
            return this;
        }

        public Builder angle(int angle) {
            if (type != GradientDrawable.LINEAR_GRADIENT) {
                return this;
            }
            if (angle % 45 != 0) {
                throw new IllegalArgumentException("Linear gradient requires 'angle' attribute "
                        + "to be a multiple of 45");
            }
            GradientDrawable.Orientation orientation;
            switch (angle) {
                case 0:
                    orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                    break;
                case 45:
                    orientation = GradientDrawable.Orientation.BL_TR;
                    break;
                case 90:
                    orientation = GradientDrawable.Orientation.BOTTOM_TOP;
                    break;
                case 135:
                    orientation = GradientDrawable.Orientation.BR_TL;
                    break;
                case 180:
                    orientation = GradientDrawable.Orientation.RIGHT_LEFT;
                    break;
                case 225:
                    orientation = GradientDrawable.Orientation.TR_BL;
                    break;
                case 270:
                    orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                    break;
                case 315:
                    orientation = GradientDrawable.Orientation.TL_BR;
                    break;
                default:
                    // Should not get here as exception is thrown above if angle is not multiple
                    // of 45 degrees
                    orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                    break;
            }
            this.orientation = orientation;
            return this;
        }

        public GradientColor build() {
            if (type == GradientDrawable.RADIAL_GRADIENT && radius <= 0) {
                throw new IllegalArgumentException(" error GradientColor RADIAL_GRADIENT type must have radius " + radius);
            }

            if (startColor == 0 && endColor == 0 && mColors == null) {
                throw new IllegalArgumentException(" error GradientColor must have startColor and endColor ");
            }
            int[] colors;
            if (mColors != null && mColors.length >= 2) {
                colors = mColors;
            } else if (centerColor == 0) {
                colors = new int[2];
                colors[0] = startColor;
                colors[1] = endColor;
            } else {
                colors = new int[3];
                colors[0] = startColor;
                colors[1] = centerColor;
                colors[2] = endColor;
            }

            GradientColor gColor = new GradientColor();
            gColor.centerX = centerX;
            gColor.centerY = centerY;
            gColor.orientation = orientation;
            gColor.colors = colors;
            gColor.radius = radius;
            gColor.type = type;
            return gColor;
        }
    }
}
