package com.lalamove.huolala.offline.webview.resource;


import android.text.TextUtils;

import com.lalamove.huolala.offline.webview.utils.OfflineFileUtils;


/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: CheckPackageFlow
 * @author: kelvin
 * @date: 7/19/21
 * @description: 检查zip包
 * @history:
 */

public class CheckPackageFlow implements ResourceFlow.IFlow {

    private ResourceFlow mFlow;
    private String mPath;
    private String mMd5Str;

    public CheckPackageFlow(ResourceFlow flow, String path, String md5Str) {
        mFlow = flow;
        mPath = path;
        mMd5Str = md5Str;
    }

    @Override
    public void process() throws ResourceFlow.FlowException {

        if (TextUtils.isEmpty(mMd5Str)) {
            mFlow.error(new IllegalStateException("md5 = null !"));
            return;
        }

        String fileMd5 = OfflineFileUtils.bytes2HexString(OfflineFileUtils.getFileMD5(mPath), true);

        if (mMd5Str.equalsIgnoreCase(fileMd5)) {
            mFlow.process();
        } else {
            mFlow.error(new IllegalStateException("MD5 Does not match ：" + mMd5Str + " ！= " + fileMd5));
        }

    }
}
