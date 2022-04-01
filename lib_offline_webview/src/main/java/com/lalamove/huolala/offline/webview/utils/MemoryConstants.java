package com.lalamove.huolala.offline.webview.utils;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: MemoryConstants
 * @author: kelvin
 * @date: 7/19/21
 * @description: 内存大小 常量类
 * @history:
 */
public final class MemoryConstants {

    public static final int BYTE = 1;
    public static final int KB = 1024;
    public static final int MB = 1048576;
    public static final int GB = 1073741824;

    @IntDef({BYTE, KB, MB, GB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}