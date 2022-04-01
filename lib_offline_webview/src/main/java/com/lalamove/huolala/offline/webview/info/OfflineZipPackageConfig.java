package com.lalamove.huolala.offline.webview.info;

import java.io.Serializable;
import java.util.Objects;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OfflineZipPackageConfig
 * @author: kelvin
 * @date: 7/20/21
 * @description: 单个离线包数据   压缩包中的 offweb.json
 * @history:
 */


public class OfflineZipPackageConfig implements Serializable {


    /**
     * bisName : yanagi-test-1
     * ver : v1
     */

    private String bisName;
    private String ver;

    public String getBisName() {
        return bisName;
    }

    public String getVer() {
        return ver;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bisName);
    }

    public void setBisName(String bisName) {
        this.bisName = bisName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OfflineZipPackageConfig that = (OfflineZipPackageConfig) o;
        return Objects.equals(bisName, that.bisName);
    }

    @Override
    public String toString() {
        return "OfflineZipPackageConfig{" +
                "bisName='" + bisName + '\'' +
                ", ver='" + ver + '\'' +
                '}';
    }
}
