package com.lalamove.huolala.offline.webview.resource;

import com.lalamove.huolala.offline.webview.utils.OfflineConstant;
import com.lalamove.huolala.offline.webview.utils.OfflineFileUtils;
import com.lalamove.huolala.offline.webview.utils.OfflineStringUtils;
import com.lalamove.huolala.offline.webview.utils.OfflinePackageUtil;

import java.io.File;
import java.util.List;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: ParsePackageFlow
 * @author: kelvin
 * @date: 7/19/21
 * @description: 解析离线包
 * @history:
 */

public class ParsePackageFlow implements ResourceFlow.IFlow {

    private static final String TAG = ParsePackageFlow.class.getSimpleName();
    private ResourceFlow mResourceFlow;

    public ParsePackageFlow(ResourceFlow resourceFlow) {

        mResourceFlow = resourceFlow;

    }

    @Override
    public void process() throws ResourceFlow.FlowException {
        try {
            //data/data/pkgName/offline_web/bisname/new
            String unzipTempPath = OfflineStringUtils.appendUnsafeString(
                    OfflinePackageUtil.getBisDir(mResourceFlow.getPackageInfo().getBisName())
                    , File.separator
                    , OfflineConstant.TEMP_DIR_NAME);
            String unzipNewPath = OfflineStringUtils.appendUnsafeString(
                    OfflinePackageUtil.getBisDir(mResourceFlow.getPackageInfo().getBisName())
                    , File.separator
                    , OfflineConstant.NEW_DIR_NAME);
            mResourceFlow.getReportParams().unZipStart();
            //删除原temp
            File tempDir = new File(unzipTempPath);
            File newDir = new File(unzipNewPath);
            OfflineFileUtils.deleteDir(tempDir);
            //data/data/pkgName/offline_web/bisname/V1.zip
            String zipPackagePath = OfflineStringUtils.appendUnsafeString(
                    OfflinePackageUtil.getBisDir(mResourceFlow.getPackageInfo().getBisName())
                    , File.separator
                    , mResourceFlow.getPackageInfo().getVersion()
                    , OfflineConstant.ZIP_SUFFIX);
            //解压
            List<File> files = OfflineFileUtils.unzipFile(zipPackagePath, unzipTempPath);
            //解压完毕 删除new
            OfflineFileUtils.deleteDir(newDir);
            //重命名temp为new
            OfflineFileUtils.rename(tempDir, OfflineConstant.NEW_DIR_NAME);
            mResourceFlow.getReportParams().unZipEnd(true, "");
            mResourceFlow.process();
        } catch (Exception e) {
            mResourceFlow.getReportParams().unZipEnd(false, OfflineStringUtils.getErrorString(e));
            mResourceFlow.error(e);
        }
    }


}
