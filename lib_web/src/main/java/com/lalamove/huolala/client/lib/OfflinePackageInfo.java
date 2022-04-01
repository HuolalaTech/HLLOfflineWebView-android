package com.lalamove.huolala.client.lib;

import java.io.Serializable;
import java.util.Objects;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OfflinePackageInfo
 * @author: kelvin
 * @date: 7/21/21
 * @description: 离线包接口返回数据体
 * @history:
 */

public class OfflinePackageInfo implements Serializable {


    /**
     * bisName : huoyunjie
     * result : 0
     * url : www.xxx.com
     * version : xxxxxx
     */

    private String bisName;
    private int result;
    private String url;
    private int refreshMode;
    private String version;

    public OfflinePackageInfo(String bisName, int result, String url, int refreshMode, String offlineZipVer) {
        this.bisName = bisName;
        this.result = result;
        this.url = url;
        this.refreshMode = refreshMode;
        this.version = offlineZipVer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OfflinePackageInfo that = (OfflinePackageInfo) o;
        return Objects.equals(bisName, that.bisName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bisName);
    }

    @Override
    public String toString() {
        return "OfflinePackageInfo{" +
                "bisName='" + bisName + '\'' +
                ", result=" + result +
                ", url='" + url + '\'' +
                ", refreshMode=" + refreshMode +
                ", version='" + version + '\'' +
                '}';
    }
}
