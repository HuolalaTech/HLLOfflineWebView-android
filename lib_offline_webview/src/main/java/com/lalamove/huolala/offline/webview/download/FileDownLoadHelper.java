package com.lalamove.huolala.offline.webview.download;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: FileDownLoadHelper
 * @author: huangyuchen
 * @date: 7/12/21
 * @description: 文件下载帮助类，使用HttpURLConnection下载文件
 * @history:
 */
public class FileDownLoadHelper {

    private final static String TAG = "FileDownLoadHelper";

    /**
     * get请求文件 接口
     */
    public interface OnHttpGetFileListener {
        void onDownloaded(File file);

        void onDownloading(int total, int percentage);
    }

    /**
     * 默认的连接超时时间
     */
    public final int DEFAULT_CONNECT_TIMEOUT_IN_MS = 30000;
    /**
     * 默认的读取超时时间
     */
    public final int DEFAULT_READ_TIMEOUT_IN_MS = DEFAULT_CONNECT_TIMEOUT_IN_MS;
    /**
     * 缓存字节大小
     */
    public final int BUFFER_SIZE_IN_BYTE = 1024;

    /**
     * 当前下载进度
     *
     * @param finished  完成的大小
     * @param totalSize 当前的大小
     * @return
     */
    private int calculateDownloadPercentage(int finished, int totalSize) {

        double result = (double) finished / totalSize * 100;
        if (result > 100) {
            result = 100;
        }
        return (int) result;
    }

    /**
     * get请求获取文件
     *
     * @param link      连接地址
     * @param directory 文件缓存目录
     * @param fileName  文件名
     * @param listener  请求监听
     */
    public void httpGetFile(final String link, final String directory, final String fileName, final OnHttpGetFileListener listener) {

        Log.i(TAG, "base: file: " + link);

        new AsyncTask<String, Integer, File>() {
            int connContentLength = 0;

            @Override
            protected void onProgressUpdate(Integer... values) {
                Log.i(TAG, "base: progress: " + values[0] + "% for link: " + link);

                if (listener != null) {
                    listener.onDownloading(connContentLength, values[0]);
                }
            }

            @Override
            protected File doInBackground(String... links) {

                if (links.length != 1) {
                    Log.e(TAG, "base: ERR: " + "Number of link is not 1 but " + links.length);
                    return null;
                }

                Object[] objs = link2HttpGetConnectionAndInputStream(links[0], 1);

                if (objs == null) {
                    Log.e(TAG, "base: ERR: " + "ConnectionAndInputStream err for " + links[0]);
                    return null;
                }
                //获取网络传输相关的对象
                HttpURLConnection conn = (HttpURLConnection) objs[0];
                InputStream is = (InputStream) objs[1];

                //获取文件
                File file = null;
                file = buildFile(directory, fileName);

                if (file == null) {
                    Log.e(TAG, "base: ERR: " + "file init err for " + directory + File.separator + fileName);
                    return null;
                }

                FileOutputStream fos = null;
                try {
                    //写入流
                    fos = new FileOutputStream(file);
                    byte[] buffer = new byte[BUFFER_SIZE_IN_BYTE * 20];
                    int readLength = 0;
                    int countOfRead = 0;
                    int hasReadLength = 0;
                    connContentLength = conn.getContentLength();

                    while ((readLength = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, readLength);
                        countOfRead++;
                        hasReadLength = hasReadLength + readLength;
                        publishProgress(calculateDownloadPercentage(hasReadLength, connContentLength));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "base: ERR: " + "read http or save file err");
                    file = null;
                } finally {
                    try {
                        conn.disconnect();
                        is.close();
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return file;
            }

            @Override
            protected void onPostExecute(File file) {

                if (listener != null) {
                    listener.onDownloaded(file);
                }
            }

        }.execute(link);

    }

    /**
     * 创建文件
     *
     * @param directory 文件路径
     * @param fileName  文件名
     * @return File文件
     */
    private File buildFile(String directory, String fileName) {
        boolean isSucceed = false;

        File path = new File(directory);
        isSucceed = path.exists();
        if (!isSucceed) {
            isSucceed = path.mkdirs();
        }

        if (!isSucceed) {
            return null;
        }

        File file = new File(path, fileName);
        if (file.exists()) {
            isSucceed = file.delete();
        }

        return isSucceed ? file : null;
    }

    /**
     * 根据Url连接网络
     *
     * @param link           Url地址
     * @param maxNoOfRetries 尝试的次数
     * @return 返回connection，和 InputStream
     */
    private Object[] link2HttpGetConnectionAndInputStream(final String link, final int maxNoOfRetries) {

        if (link == null || link.isEmpty()) {
            Log.e(TAG, "base: ERR: " + "link is null or empty");
            return new Object[]{null, null};
        }

        URL url = null;
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (url == null) {
            Log.e(TAG, "base: ERR: " + "malformed URL for " + link);
            return new Object[]{null, null};
        }

        HttpURLConnection conn = null;
        InputStream is = null;

        boolean hasException = false;
        try {

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // will make httpGetFile not work, reason unknown
            // conn.setDoOutput(true);
            conn.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT_IN_MS);
            conn.setReadTimeout(DEFAULT_READ_TIMEOUT_IN_MS);
            conn.setRequestProperty("Accept-Encoding", "identity");
            conn.setRequestProperty("User-Agent", " Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36");

            int countOfRetries = 0;

            while (is == null && countOfRetries < maxNoOfRetries) {

                if (countOfRetries >= 1) {
                    Log.i(TAG, "base: RETRY (" + (countOfRetries + 1) + "): " + link);
                }

                try {
                    is = new BufferedInputStream(conn.getInputStream());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "base: ERR: " + "get input stream error for " + link);
                }
                countOfRetries++;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "base: ERR: " + "setup (not connect) connection error");

            hasException = true;
            try {
                is.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            is = null;
            try {
                conn.disconnect();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            conn = null;
        }
        return hasException ? null : new Object[]{conn, is};
    }
}
