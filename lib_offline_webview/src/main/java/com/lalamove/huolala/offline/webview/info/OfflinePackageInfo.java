package com.lalamove.huolala.offline.webview.info;

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
    public OfflinePackageInfo(String bisName,String offlineZipVer) {
        this.bisName = bisName;
        this.version = offlineZipVer;
    }

    public String getBisName() {
        return bisName;
    }

    public int getResult() {
        return result;
    }

    public String getUrl() {
        return url;
    }

    public void setBisName(String bisName) {
        this.bisName = bisName;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRefreshMode() {
        return refreshMode;
    }

    public boolean isForceRefresh() {
        return refreshMode == 1;
    }

    public void setRefreshMode(int refreshMode) {
        this.refreshMode = refreshMode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isEnable() {
        if (result == -1) {
            return false;
        }
        return true;
    }

    public boolean isSameVer() {
        return result == 0;
    }

    public boolean isNeedUpdate() {
        return result == 1;
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
