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


    String LOCALHOST = "http://172.20.197.41:9999/";

    String BIS_NAME = "bisName";

    String PATH_OFF_WEB = "offweb";

    String PAHT_PACKAGE = "package";

    String PARAM_OFFLINE_ZIP_VER = "offlineZipVer";

    String ASSETS_PATH = new File("").getAbsolutePath()+File.separator+"assets";

    String NAME_ACT3 = "act3-2108-turntable";

    String PKG_ACT3 = "48559-11no5bw.zip";

    String NAME_UAPPWEB_OFFLINE = "uappweb-offline";

    String PKG_UAPPWEB_OFFLINE = "111204-c4k7hh_1.zip";

    String ACT3_PATH = ASSETS_PATH + File.separator + PKG_ACT3;

    String UAPPWEB_OFFLINE_PATH = ASSETS_PATH + File.separator + PKG_UAPPWEB_OFFLINE;

}
