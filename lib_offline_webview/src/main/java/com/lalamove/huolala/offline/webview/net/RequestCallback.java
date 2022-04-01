package com.lalamove.huolala.offline.webview.net;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: RequestCallBack
 * @author: kelvin
 * @date: 3/29/22
 * @description: 网络请求回调
 * @history:
 */

public interface RequestCallback<T> {

    /**
     * 请求成功
     * @param data 返回数据实体
     */
    void onSuccess(T data);

    /**
     * 请求异常信息
     * @param throwable 异常
     */
    void onFail(Throwable throwable);
}
