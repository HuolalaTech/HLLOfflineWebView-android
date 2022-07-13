package com.lalamove.huolala.client.offline_web.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: DisplayUtils
 * @author: huangyuchen
 * @date: 7/12/21
 * @description: 屏幕宽高，dp,sp,px转换工具类
 * @history:
 */
public class DisplayUtils {

    private DisplayUtils() {

    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        if (context == null) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * @param context
     * @param dpValue
     * @return int
     * @description: 单位转换：dp转px
     */
    public static int dp2px(Context context, float dpValue) {
        return (int) dp2pxF(context, dpValue);
    }

    /**
     * @param context
     * @param dpValue
     * @return float
     * @description: 单位转换：dp转px
     */
    public static float dp2pxF(Context context, float dpValue) {
        if (context == null) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }

    public static int dpTpx(Context context, float dpVal) {
        if (context == null) {
            return 0;
        }
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public static float sp2px(Context context, float spValue) {
        if (context == null) {
            return 0;
        }
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * scaledDensity;
    }


}
