package com.lalamove.huolala.client.lib;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;

/**
 * 下载处理器类
 */
public class DownloadHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) {
        try {
            System.out.println("DownloadHandler handle start");
            String requestParam = getRequestParam(httpExchange);
            Map<String, String> paramMap = ParamsUtil.getParamMap(requestParam);
            String bisName = paramMap.get(ServerConstant.BIS_NAME);
            String zipVersion = paramMap.get(ServerConstant.PARAM_OFFLINE_ZIP_VER);
            File zipFile = null;
            System.out.println("download bisName = " + bisName);
            if (ServerConstant.NAME_ACT3.equals(bisName)) {
                zipFile = new File(ServerConstant.ACT3_PATH);
            } else if (ServerConstant.NAME_UAPPWEB_OFFLINE.equals(bisName)) {
                zipFile = new File(ServerConstant.UAPPWEB_OFFLINE_PATH);
            }
            if (zipFile != null) {
                System.out.println("download path = " + zipFile.getAbsolutePath());
            }
            String fileName = bisName;
            handleResponse(httpExchange, zipFile, fileName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取请求参数
     *
     * @param httpExchange
     * @return
     * @throws Exception
     */
    private String getRequestParam(HttpExchange httpExchange) throws Exception {
        String paramStr = "";

        if (httpExchange.getRequestMethod().equals("GET")) {
            //GET请求读queryString
            paramStr = httpExchange.getRequestURI().getQuery();
        } else {
            //非GET请求读请求体
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody(), "utf-8"));
            StringBuilder requestBodyContent = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                requestBodyContent.append(line);
            }
            paramStr = requestBodyContent.toString();
        }

        return paramStr;
    }

    /**
     * 处理响应
     *
     * @param httpExchange
     * @param file
     * @throws Exception
     */
    private void handleResponse(HttpExchange httpExchange, File file, String fileName) throws Exception {
        //设置响应头，必须在sendResponseHeaders方法之前设置！
        httpExchange.getResponseHeaders().add("Content-Type:", "application/vnd.ms-excel;charset=utf-8");
        httpExchange.getResponseHeaders().add("Content-Disposition", "attachment;filename=" + fileName + ".zip");
//        设置响应码和响应体长度，必须在getResponseBody方法之前调用！
        if (file == null || !file.exists()) {
            httpExchange.sendResponseHeaders(200, 0);
            System.out.println("下载失败 文件无法找到");
            OutputStream out = httpExchange.getResponseBody();
            out.flush();
            out.close();
            return;
        }
        httpExchange.sendResponseHeaders(200, file.length());
        InputStream in = null;
        OutputStream out = null;
        try {
            //获取要下载的文件输入流
            in = new FileInputStream(file);
            int len = 0;
            //创建数据缓冲区
            byte[] buffer = new byte[1024];
            //通过response对象获取outputStream流
            out = httpExchange.getResponseBody();
            //将FileInputStream流写入到buffer缓冲区
            while ((len = in.read(buffer)) > 0) {
                //使用OutputStream将缓冲区的数据输出到浏览器
                out.write(buffer, 0, len);
            }
            out.flush();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
