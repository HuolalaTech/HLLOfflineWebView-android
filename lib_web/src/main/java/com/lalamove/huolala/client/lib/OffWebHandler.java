package com.lalamove.huolala.client.lib;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 拉取离线包信息处理器类
 */
public class OffWebHandler implements HttpHandler {

    private Gson mGson = new Gson();

    @Override
    public void handle(HttpExchange httpExchange) {
        try {
            String requestParam = getRequestParam(httpExchange);
            String bisName ="";
            String zipVersion ="";
            Map<String, String> paramMap = ParamsUtil.getParamMap(requestParam);
            if (paramMap != null) {
                bisName = paramMap.get(ServerConstant.BIS_NAME);
                zipVersion = paramMap.get(ServerConstant.PARAM_OFFLINE_ZIP_VER);
            }
            String downloadUrl = null;
            if (ServerConstant.NAME_ACT3.equals(bisName)) {
                StringBuilder downloadSb = new StringBuilder();
                downloadSb.append(ServerConstant.LOCALHOST)
                        .append(ServerConstant.PAHT_PACKAGE)
                        .append("?")
                        .append(ServerConstant.BIS_NAME)
                        .append("=")
                        .append(ServerConstant.NAME_ACT3);
                downloadUrl = downloadSb.toString();
            }
            //result==1
            OfflinePackageInfo offlinePackageInfo = new OfflinePackageInfo(bisName, 1, downloadUrl, 0, zipVersion + "_1");

            //result == 0 无需下载
            //            OfflinePackageInfo offlinePackageInfo = new OfflinePackageInfo(bisName, 0, downloadUrl, 0, zipVersion + "_1");
            String s = mGson.toJson(offlinePackageInfo);
            handleResponse(httpExchange, s);
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
        String paramStr;

        if ("GET".equals(httpExchange.getRequestMethod())) {
            //GET请求读queryString
            paramStr = httpExchange.getRequestURI().getQuery();
        } else {
            //非GET请求读请求体
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8));
            StringBuilder requestBodyContent = new StringBuilder();
            String line;
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
     * @param responsetext
     * @throws Exception
     */
    private void handleResponse(HttpExchange httpExchange, String responsetext) throws Exception {
        System.out.println("resp:"+responsetext);
        byte[] responseContentByte = responsetext.getBytes(StandardCharsets.UTF_8);
        //设置响应头，必须在sendResponseHeaders方法之前设置！
        httpExchange.getResponseHeaders().add("Content-Type:", "application/json");
        //设置响应码和响应体长度，必须在getResponseBody方法之前调用！
        httpExchange.sendResponseHeaders(200, responseContentByte.length);
        OutputStream out = httpExchange.getResponseBody();
        out.write(responseContentByte);
        out.flush();
        out.close();
    }
}
