package com.lalamove.huolala.offline.webview.download;


import java.io.File;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: DefaultDownloader
 * @author: kelvin
 * @date: 3/29/22
 * @description: 默认下载器
 * @history:
 */

public class DefaultDownloader implements IDownLoader {


    @Override
    public void download(String url, String phoneDir, String fileName, DownloadCallback callback) {
        doDownloadByHelper(url, phoneDir, fileName, callback);
    }

    private void doDownloadByHelper(String url, String phoneDir, String fileName, IDownLoader.DownloadCallback callback) {
        FileDownLoadHelper fileDownLoadHelper = new FileDownLoadHelper();
        fileDownLoadHelper.httpGetFile(url, phoneDir, fileName, new FileDownLoadHelper.OnHttpGetFileListener() {
            @Override
            public void onDownloaded(File file) {
                if (callback != null) {
                    callback.success(file, false);
                }
            }

            @Override
            public void onDownloading(int total, int percentage) {
            }
        });
    }
}
