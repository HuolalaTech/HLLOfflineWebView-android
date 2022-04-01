package com.lalamove.huolala.offline.webview.monitor;

/**
 * create by zhii.yang 2021/12/6
 * desc : 监控降级配置
 */
public class MonitorDemotionConfig {

    public static boolean isDisableMonitor(){
//       return EnhWebMarsGlobalConfig.getMonitorDisable() == "1";
       return "1" == "1";
    }
}
