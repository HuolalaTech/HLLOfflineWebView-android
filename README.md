<img src=Image/title.png width=100% height=100% />

[![license](https://img.shields.io/hexpm/l/plug.svg)](https://www.apache.org/licenses/LICENSE-2.0) ![release](https://img.shields.io/badge/release-v1.0.0-green.svg) ![Platform](https://img.shields.io/badge/platform-android-blue.svg) ![API](https://img.shields.io/badge/API-21-green.svg)

HLLOfflineWebView is a lightweight and high-performance hybrid framework developed by HUOLALA mobile team, which is intended to improve the load speed of websites on mobile phone. It base on [WebView](https://developer.android.google.cn/guide/webapps/webview) at Android system.  
HLLOfflineWebView can cache html, css, js, png and other static resource on the disk. When the app load the web page, it directly load the resource from disk and reduce network request.

## Before VS After Using HLLOfflineWebView

| |Before Using HLLOfflineWebVC |   After Using HLLOfflineWebVC
|--|:-------------------------:|:-------------------------:
|Movie|<img src=Image/1.gif width=60% height=40% />  | <img src=Image/2.gif width=60% height=40% />

## Features

- Safe and reliable: no hook and no private API,  three degrade strategy.
- Easy to maintain: three layer structure and modular design.
- Fully functional: it contains H5 offline resource managing, url and offline resource mapping config, data reporting.


## How To Use
If you want to bring the code into your project, you need to do the following:

### Develop A HTTP Service
-  HTTP Request

   https://www.xxx.com/queryOffline?clientType=Android&clientVer=1.0.0&offlineZipVer=1.0.0&bisName=xx

   &emsp;requet data parameters description:

| parameter name         | parameter meaning                     | note                                |
|------------------|-------------------------------|-------------------------------------|
| clientType               | operating system type                     | iOS，Android                        |
| clientVersion    | app version                   |  eg: 1.0                                   |
| bisName          | unique identifier of your offline web page    |   eg: act3-offline-package-test                |
| offlineZipVer    | the local offline web file version        | 0 means no offline web cache            |



&emsp;respond data format is a json，parameters description:

| parameter name       | parameter meaning     |      note                                                                               |
|--------------|-------------|--------------------------------------------------------------------------------------------|
| bisName      | unique identifier of your offline web page       | eg: act3-offline-package-test                                                                                           |
| result       | query result        | -1: disable offline web, 0: no update, 1: has new version   |
| url          | zip file download url   | if no update, the url is null                                                                           |
| refreshMode  |notify client how to update     | 0:update next(default)   1:update immediately                                                           |
| version      | offline web pages version  | eg: 25609-j56gfa                               |

- Cross Origin
  When an offline web page make a network request, the origin is null，should modify your gateway or server to support.

- Set up a local service

  1.Set LOCALHOST in ServerConstant to your IP
  2.Run HttpServerStarter with Coverage
  3.Test in the chrome
  http://127.0.0.1:8888/offweb?bisName=act3-2108-turntable&offlineZipVer=ab
  http://127.0.0.1:8888/package?bisName=act3-2108-turntable

  ```
  requestServer(new DefaultLocalRequest())
  ```
-  Modify HTML And JS File 
   use relative path, no absolute path.
   cookie、local storage should support the situation that host is null.
   add a file to describe the offline web version .the file name is ".offweb.json" and the content is:
   { "bisName": "xxx", "ver": "xxx" }
  

### Getting Started

1) Simple init HLLOfflineWebView 

```
OfflineConfig offlineConfig = new OfflineConfig.Builder(true)
        //.addDisable("act3-2109-turntable")//disable package
        .addPreDownload("uappweb-offline")//PreDownload the package
        .build();
OfflineWebClient.init(context.getApplicationContext(),
        new OfflineParams()
                .config(offlineConfig)
                .requestServer(new DefaultLocalRequest())
                //.requestServer(new DefaultRequest())
                .isDebug(BuildConfig.DEBUG)
    );
```

2) Full init HLLOfflineWebView

```
OfflineWebClient.init(context.getApplicationContext(),
        new OfflineParams()
                //need config
                .config(offlineConfig)
                //need network request
                .requestServer(new DefaultLocalRequest())
                //need Debug mode
                .isDebug(BuildConfig.DEBUG)
                //Optional DownLoader
                .downloader(new IDownLoader() {})
                //Optional rule
                .addRule("act3-2108-turntable", host, path, fragment)
                //Optional Rules in Json format
                .setRule(Constants.RULE_CONFIG)
                //Optional Rules Config
                .setRule(offlineRuleConfig)
                //Optional Logger
                .logger(new Logger() {})
                //Optional Url Matcher
                .matcher(new DefaultMatcher())
                //Optional ExecutorService
                .executorServiceProvider(new IExecutorServiceProvider() {})
                //Optional WebMonitor
                .monitor(new IEnhWebMonitor() {})
                //Optional Interceptor
                .interceptor(new IInterceptor() {})
                //Optional update callback
                .flowResultHandleStrategy(new IFlowResultHandleStrategy() {})

    );
```

## Communication
- If you find a bug, open an issue.
- If you have a feature request, open an issue.
- If you want to contribute, submit a pull request.

## Blog
[货拉拉安卓端H5离线包原理与实践](https://juejin.cn/post/7119662876578545678/)

## Author
[HUOLALA mobile technology team](https://juejin.cn/user/1768489241815070).
## License
HLLOfflineWebView is released under the Apache 2.0 license. See [LICENSE](LICENSE) for details.