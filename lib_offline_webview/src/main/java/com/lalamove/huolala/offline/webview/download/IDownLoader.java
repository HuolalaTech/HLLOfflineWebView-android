package com.lalamove.huolala.offline.webview.download;

import java.io.File;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: IDownLoader
 * @author: kelvin
 * @date: 7/27/21
 * @description: 下载器
 * @history:
 */

public interface IDownLoader {

    /**
     *
     * @param url 下载路径
     * @param phoneDir 本地路径
     * @param fileName 文件名称
     * @param callback 回调
     */
    void download(String url, String phoneDir, String fileName, DownloadCallback callback);

    interface DownloadCallback {

        /**
         *
         * @param file 下载成功的文件File
         * @param isBrokenDown 是否断点续传
         */
        void success(File file, boolean isBrokenDown);

        /**
         *
         */
        void fail(Throwable throwable);
    }
}
