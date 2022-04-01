package com.lalamove.huolala.offline.webview.resource;

import com.lalamove.huolala.offline.webview.OfflineWebManager;
import com.lalamove.huolala.offline.webview.download.IDownLoader;
import com.lalamove.huolala.offline.webview.log.OfflineWebLog;
import com.lalamove.huolala.offline.webview.utils.OfflineConstant;
import com.lalamove.huolala.offline.webview.utils.OfflineFileUtils;
import com.lalamove.huolala.offline.webview.utils.OfflineStringUtils;
import com.lalamove.huolala.offline.webview.utils.OfflinePackageUtil;

import java.io.File;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: DownloadFlow
 * @author: kelvin
 * @date: 7/19/21
 * @description: 下载包
 * @history:
 */

public class DownloadFlow implements ResourceFlow.IFlow {

    private static final String TAG = DownloadFlow.class.getSimpleName();
    private ResourceFlow mResourceFlow;


    public DownloadFlow(ResourceFlow resourceFlow) {
        mResourceFlow = resourceFlow;
    }

    @Override
    public void process() {
        final String downloadPath = mResourceFlow.getPackageInfo().getUrl();
        String destPath = OfflinePackageUtil.getBisDir(mResourceFlow.getPackageInfo().getBisName())
                + File.separator + mResourceFlow.getPackageInfo().getVersion() + OfflineConstant.ZIP_SUFFIX;

        IDownLoader downLoader = OfflineWebManager.getInstance().getDownLoader();
        File file = new File(destPath);
        OfflineWebLog.d(TAG, "start download ... mDestPath=" + destPath + "\n mDownloadPath=" + downloadPath);
        mResourceFlow.getReportParams().downloadStart();
        downLoader.download(downloadPath, file.getParentFile().getAbsolutePath(), file.getName(), new IDownLoader.DownloadCallback() {
            @Override
            public void success(File file, boolean isBrokenDown) {
                if (file == null || !file.exists()) {
                    mResourceFlow.getReportParams().downloadResult(false, "download error local file not found");
                    mResourceFlow.error(new IllegalStateException("download error local file not found"));
                    return;
                }
                mResourceFlow.getReportParams().zipSize(OfflineFileUtils.getFileSize(file));
                mResourceFlow.getReportParams().isBrokenDown(isBrokenDown);
                mResourceFlow.getReportParams().downloadResult(true, downloadPath);
                mResourceFlow.process();
            }

            @Override
            public void fail(Throwable e) {
                mResourceFlow.getReportParams().downloadResult(false, OfflineStringUtils.getErrorString(e));
                mResourceFlow.error(e);
            }
        });
    }
}
