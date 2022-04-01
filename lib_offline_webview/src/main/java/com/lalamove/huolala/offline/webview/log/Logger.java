package com.lalamove.huolala.offline.webview.log;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: Logger
 * @author: kelvin
 * @date: 7/19/21
 * @description: 日志接口
 * @history:
 */

public interface Logger {

    void e(String tag, String content);

    void e(String tag, Throwable throwable);

    void d(String tag, String content);

    void i(String tag, String content);

}
