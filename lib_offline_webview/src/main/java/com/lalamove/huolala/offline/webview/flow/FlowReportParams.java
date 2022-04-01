package com.lalamove.huolala.offline.webview.flow;


/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: FlowReportParams
 * @author: kelvin
 * @date: 8/12/21
 * @description: 更新包流程参数
 * @history:
 */

public class FlowReportParams {

    /**
     * 离线包业务名
     */
    private String mBisName;
    /**
     * 查询耗时
     */
    private long mQueryTime;
    /**
     * 查询返回结果是否正确 0 网络请求成功，-1 网络请求失败
     */
    private int mQueryResult;
    /**
     * 查询返回信息
     */
    private String mQueryMsg;
    /**
     * 下载耗时(没有下载过程填0)
     */
    private long mDownloadTime;
    /**
     * -1 下载失败  0下载成功   1  无需下载
     */
    private int mDownloadResult = 1;
    /**
     * 下载信息
     */
    private String mDownloadMsg;
    /**
     * 解压耗时（如果没有解压过程则填0）
     */
    private long mUnzipTime;
    /**
     * -1解压失败   0解压成功 ，1 无需解压
     */
    private int mUnzipResult = 1;
    /**
     * 错误时填错误原因。
     */
    private String mUnzipMsg;
    /**
     * 压缩包大小
     */
    private long mZipSize;
    /**
     * 0 非断点续传 1 断点续传
     */
    private boolean mIsBrokenDown;

    private long mQueryStartTime;
    private long mDownloadStartTime;
    private long mUnZipStartTime;

    public String getBisName() {
        return mBisName;
    }

    public long getQueryTime() {
        return mQueryTime;
    }

    public int getQueryResult() {
        return mQueryResult;
    }

    public String getQueryMsg() {
        return mQueryMsg;
    }

    public long getDownloadTime() {
        return mDownloadTime;
    }

    public int getDownloadResult() {
        return mDownloadResult;
    }

    public String getDownloadMsg() {
        return mDownloadMsg;
    }

    public long getUnzipTime() {
        return mUnzipTime;
    }

    public int getUnzipResult() {
        return mUnzipResult;
    }

    public String getUnzipMsg() {
        return mUnzipMsg;
    }

    public long getZipSize() {
        return mZipSize;
    }

    public boolean isBrokenDown() {
        return mIsBrokenDown;
    }

    public FlowReportParams(String bisName) {
        this.mBisName = bisName;
    }

    public void queryStart() {
        mQueryStartTime = System.currentTimeMillis();
    }

    public void queryEnd() {
        mQueryTime = System.currentTimeMillis() - mQueryStartTime;
    }

    public void setQueryResult(boolean isSuccess, String queryMsg) {
        mQueryResult = isSuccess ? 0 : -1;
        mQueryMsg = queryMsg;
    }

    public void downloadStart() {
        mDownloadStartTime = System.currentTimeMillis();
    }

    public void downloadResult(boolean success, String msg) {
        mDownloadTime = System.currentTimeMillis() - mDownloadStartTime;
        mDownloadResult = success ? 0 : -1;
        mDownloadMsg = msg;
    }

    public void unZipStart() {
        mUnZipStartTime = System.currentTimeMillis();
    }

    public void unZipEnd(boolean success, String error) {
        mUnzipTime = System.currentTimeMillis() - mUnZipStartTime;
        mUnzipResult = success ? 0 : -1;
        mUnzipMsg = error;
    }

    public void zipSize(long size) {
        mZipSize = size;
    }

    public void isBrokenDown(boolean isBrokenDown) {
        mIsBrokenDown = isBrokenDown;
    }
}
