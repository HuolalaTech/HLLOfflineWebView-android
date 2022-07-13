package com.lalamove.huolala.offline.webview.resource;

import com.lalamove.huolala.offline.webview.OfflineWebManager;
import com.lalamove.huolala.offline.webview.info.OfflinePackageInfo;
import com.lalamove.huolala.offline.webview.utils.OfflineConstant;
import com.lalamove.huolala.offline.webview.utils.OfflineFileUtils;
import com.lalamove.huolala.offline.webview.utils.OfflinePackageUtil;
import com.lalamove.huolala.offline.webview.utils.OfflineStringUtils;

import java.io.File;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: ReplaceResFlow
 * @author: kelvin
 * @date: 7/19/21
 * @description: 替换离线包
 * @history:
 */

public class ReplaceResFlow implements ResourceFlow.IFlow {

    private ResourceFlow mFlow;

    public ReplaceResFlow(ResourceFlow flow) {
        mFlow = flow;
    }

    @Override
    public void process() throws ResourceFlow.FlowException {

        String unzipPath = OfflineStringUtils.appendUnsafeString(
                OfflinePackageUtil.getBisDir(mFlow.getPackageInfo().getBisName())
                , File.separator
                , OfflineConstant.NEW_DIR_NAME);

        OfflinePackageInfo offlinePackageInfo = mFlow.getPackageInfo();
        String destPath = OfflinePackageUtil.getBisDir(offlinePackageInfo.getBisName());
        if (offlinePackageInfo.isForceRefresh()) {
            //强刷
            //原cur 重命名为old，下次启动删除
            File file = new File(OfflineStringUtils.appendUnsafeString(destPath, File.separator, OfflineConstant.CUR_DIR_NAME));
            if (file.exists()) {
                OfflineFileUtils.rename(file, OfflineConstant.OLD_DIR_NAME);
            }
            OfflineFileUtils.rename(new File(unzipPath), OfflineConstant.CUR_DIR_NAME);
            //执行强刷
            OfflineWebManager.getInstance().getPageManager().reload(mFlow.getPackageInfo().getBisName());
        } else {
            //cur 文件夹不存在则直接应用，改名为cur
            File file = new File(OfflineStringUtils.appendUnsafeString(destPath, File.separator, OfflineConstant.CUR_DIR_NAME));
            if (!file.exists()) {
                OfflineFileUtils.rename(new File(unzipPath), OfflineConstant.CUR_DIR_NAME);
            }
        }
        mFlow.process();
    }
}
