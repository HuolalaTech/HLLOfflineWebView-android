package com.lalamove.huolala.offline.webview.Interceptor;

/**
 * create by zhii.yang 2021/12/6
 * desc : 拦截器，判断当前离线包是否可用
 */
public interface IInterceptor {

    /**
     * @param bisName 业务名称
     * @return 是否拦截
     */
    boolean isIntercept(String bisName);

}
