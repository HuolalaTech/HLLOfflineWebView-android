package com.lalamove.huolala.client.lib;

import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class HttpServerStarter {

    public static void main(String[] args) throws IOException {
        //创建一个HttpServer实例，并绑定到指定的IP地址和端口号
        for (String arg : args) {
            System.out.println("kkkkkk:"+arg);
        }
        File assetFile = new File(ServerConstant.ASSETS_PATH);
        if (!assetFile.exists()) {
            System.out.println("请配置asset目录 或者 设置asset路径");
        }
        System.out.println("ASSETS_PATH = " + ServerConstant.ASSETS_PATH);
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(9999), 0);
        //创建一个HttpContext，将路径为/myserver请求映射到MyHttpHandler处理器
        httpServer.createContext("/"+ServerConstant.PATH_OFF_WEB, new OffWebHandler());
        httpServer.createContext("/"+ServerConstant.PAHT_PACKAGE, new DownloadHandler());

        //设置服务器的线程池对象
        httpServer.setExecutor(Executors.newFixedThreadPool(10));

        //启动服务器
        httpServer.start();
    }
}