<img src=Image/title.png width=100% height=100% />

[![license](https://img.shields.io/hexpm/l/plug.svg)](https://www.apache.org/licenses/LICENSE-2.0) ![release](https://img.shields.io/badge/release-v1.0.0-green.svg) ![Platform](https://img.shields.io/badge/platform-android-blue.svg) ![API](https://img.shields.io/badge/API-21-green.svg)

## 介绍
HLLOfflineWebView是货拉拉自研的轻量级高性能H5离线包sdk，可以显著的提升H5页面加载速度，Android端基于WebView实现。
主要原理为：提前缓存html、js、css、图片等资源文件到静态到本地，当H5页面请求资源时，尽量从本地获取数据，减少网络请求。

## 比较
| |未使用离线包 |   使用离线包
|--|:-------------------------:|:-------------------------:
|视频|<img src=Image/1.gif width=60% height=50% />  | <img src=Image/2.gif width=60% height=50% />


-  安全可靠：无hook，无私有API，具有三重降级策略，保证可靠性
-  容易维护：三层架构模式和模块化设计
-  功能完备：功能可配置，数据埋点


## 项目使用
如果要在实际项目中使用，需要采取如下步骤：
### 离线包服务搭建
- 实现查询接口请求格式
https://www.xxx.com/queryOffline?clientType=Android&clientVer=1.0.0&offlineZipVer=1.0.0&bisName=xxx
请求参数接口参数说明：

| 参数名           | 参数含义                      | 备注                                |
|------------------|-------------------------------|-------------------------------------|
| os               | 终端类型                      | iOS，Android                        |
| clientVersion    | 客户端版本                    |                                     |
| bisName          | 业务名，每个页面的离线包独立     | 自定义参数，业务名                  |
| offlineZipVer    | 本地离线包版本                | 自定义参数，0表示本地无             |


 查询结果返回结果为json，参数说明：

| 参数名       | 参数含义    | 备注                                                                                       |
|--------------|-------------|--------------------------------------------------------------------------------------------|
| bisName      | 业务名      |                                                                                            |
| result       | 结果        | -1 禁用离线包  0 无更新   1  有新离线包，全量包   |
| url          | 离线包（zip压缩包）下载地址   | 没有时为空字符串                                                                           |
| refreshMode  | 刷新模式    | 0 下次刷新（默认）   1  马上强制刷新（极端情况下使用）新                                                           |
| version      | 离线包版本  | 区分版本                                  |

- 接口跨域处理
  H5离线包加载的路径为文件路径为，发起cgi请求时，origin为null，需要网关或者后端添加跨域支持。

- 本地服务搭建

  1.本地服务IP修改：ServerConstant中LOCALHOST修改为自己的ip地址（mac 获取 终端输入命令行：ifconfig | grep "inet"）
  2.Coverage模式运行HttpServerStarter
  3.电脑浏览器测试两个接口
  http://127.0.0.1:8888/offweb?bisName=act3-2108-turntable&offlineZipVer=ab
  http://127.0.0.1:8888/package?bisName=act3-2108-turntable

  ```
  requestServer(new DefaultLocalRequest())
  ```
- H5端改造 
  使用相对路径。引用的本地js、css等文件路径需要改成相对路径。 
  cookie、localstorage等存储跨域支持。 
  加入版本文件。离线包资源包大包时加入版本描述文件".offweb.json",格式为：
  { "bisName": "xxx", "ver": "xxx" }
### 客户端接入

1) 简易初始化

```
OfflineConfig offlineConfig = new OfflineConfig.Builder(true)
        //.addDisable("act3-2109-turntable")//禁用业务名称
        .addPreDownload("uappweb-offline")//预加载业务名称
        .build();
OfflineWebClient.init(context.getApplicationContext(),
        new OfflineParams()
                .config(offlineConfig)//必须
                .requestServer(new DefaultLocalRequest())//必须
                //.requestServer(new DefaultRequest())
                .isDebug(BuildConfig.DEBUG)
    );
```

2) 完整初始化

```
OfflineWebClient.init(context.getApplicationContext(),
        new OfflineParams()
                //离线包配置必须
                .config(offlineConfig)
                //离线包更新请求必须
                .requestServer(new DefaultLocalRequest())
                //是否为debug包
                .isDebug(BuildConfig.DEBUG)
                //可选 下载器
                .downloader(new IDownLoader() {})
                //可选 添加单个匹配规则
                .addRule("act3-2108-turntable", host, path, fragment)
                //可选 json形式添加规则
                .setRule(Constants.RULE_CONFIG)
                //可选 实体类形式添加规则
                .setRule(offlineRuleConfig)
                //可选 日志打印
                .logger(new Logger() {})
                //可选 匹配器
                .matcher(new DefaultMatcher())
                //可选 线程池
                .executorServiceProvider(new IExecutorServiceProvider() {})
                //可选 监控
                .monitor(new IEnhWebMonitor() {})
                //可选 拦截器
                .interceptor(new IInterceptor() {})
                //可选 获取更新包流程结束回调 实现之后
                .flowResultHandleStrategy(new IFlowResultHandleStrategy() {})

    );
```

## 问题交流
- 如果你发现了bug或者有其他功能诉求，欢迎提issue。
- 如果想贡献代码，可以直接发起MR。

## 技术博客
&emsp;&emsp; [货拉拉安卓端H5离线包原理与实践](https://juejin.cn/post/7119662876578545678/)
## 作者
&emsp;&emsp; [货拉拉移动端技术团队](https://juejin.cn/user/1768489241815070)
## 许可证
&emsp;&emsp;采用Apache 2.0协议，详情参考[LICENSE](LICENSE)
