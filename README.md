本地服务：
1.ServerConstant:
    ASSETS_PATH修改为自己的path
    LOCALHOST修改为自己的ip地址
2.Coverage模式运行HttpServerStarter
3.电脑浏览器测试两个接口
http://127.0.0.1:9999/offweb?bisName=act3-2108-turntable&offlineZipVer=ab
http://127.0.0.1:9999/package?bisName=act3-2108-turntable
4.Demo中确定OfflineWebInitJob初始化中的request为 DefaultLocalRequest
    ".requestServer(new DefaultLocalRequest())""

