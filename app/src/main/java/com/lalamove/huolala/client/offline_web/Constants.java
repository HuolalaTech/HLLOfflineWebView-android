package com.lalamove.huolala.client.offline_web;

import com.lalamove.huolala.client.lib.ServerConstant;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: Constants
 * @author: kelvin
 * @date: 3/29/22
 * @description:
 * @history:
 */

public interface Constants {
    String BASE_URL = "";
    String LOCALHOST = ServerConstant.LOCALHOST;
    String LOCAL_BASE_URL = LOCALHOST + ServerConstant.PATH_OFF_WEB;

    String RULE_CONFIG = "{\"rules\":[{\"path\":[\"/uapp\"],\"offweb\":\"uappweb-offline\",\"host\":[\"www.huolala.cn\"],\"fragmentprefix\":[\"/xxxxxx\",\"/cd-index\"]},{\"path\":[\"/van/act3-turntable-x\"],\"offweb\":\"act3-2108-turntable\",\"host\":[\"www.huolala.cn\",\"www.baidu.com\"]}]}";
}
