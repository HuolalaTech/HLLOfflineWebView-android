package com.lalamove.huolala.client.lib;

import java.io.File;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: ServerConstant
 * @author: kelvin
 * @date: 3/30/22
 * @description: 服务端配置
 * @history:
 */

public interface ServerConstant {


    String LOCALHOST = "http://xxx.xxx.xxx.xxx:8888/";

    String BIS_NAME = "bisName";

    String PATH_OFF_WEB = "queryOffline";

    String PAHT_PACKAGE = "package";

    String PARAM_OFFLINE_ZIP_VER = "offlineZipVer";

    String ASSETS_PATH = new File("").getAbsolutePath()+File.separator+"assets";

    String NAME_ACT3 = "act3-2108-turntable";

    String PKG_ACT3 = "act3-2108-turntable.zip";

    String ACT3_PATH = ASSETS_PATH + File.separator + PKG_ACT3;

}
