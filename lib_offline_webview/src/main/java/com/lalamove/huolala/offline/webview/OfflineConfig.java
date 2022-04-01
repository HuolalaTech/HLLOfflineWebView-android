package com.lalamove.huolala.offline.webview;

import java.util.Collection;
import java.util.HashSet;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OfflineConfig
 * @author: kelvin
 * @date: 3/28/22
 * @description: 离线包配置参数
 * @history:
 */

public class OfflineConfig {

    private boolean isOpen;
    private HashSet<String> predownloadlist;
    private HashSet<String> disablelist;

    public OfflineConfig(boolean isOpen, HashSet<String> predownloadlist, HashSet<String> disablelist) {
        this.isOpen = isOpen;
        this.predownloadlist = predownloadlist;
        this.disablelist = disablelist;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public HashSet<String> getPredownloadlist() {
        return predownloadlist;
    }

    public boolean isDisable(String bisName) {
        if (disablelist == null || disablelist.size() == 0) {
            return false;
        }
        return disablelist.contains(bisName);
    }

    public static class Builder {
        private boolean isOpen;
        private HashSet<String> predownloadlist;
        private HashSet<String> disablelist;

        public Builder(boolean isOpen) {
            this.isOpen = isOpen;
            predownloadlist = new HashSet<String>();
            disablelist = new HashSet<>();
        }

        public Builder addPreDownload(String bizName) {
            predownloadlist.add(bizName);
            return this;
        }

        public Builder addPreDownloadList(Collection<String> preDownloadList) {
            this.predownloadlist.addAll(preDownloadList);
            return this;
        }

        public Builder addDisable(String bizName) {
            disablelist.add(bizName);
            return this;
        }

        public Builder addDisableList(Collection<String> disableList) {
            this.disablelist.addAll(disableList);
            return this;
        }

        public OfflineConfig build() {
            return new OfflineConfig(isOpen, predownloadlist, disablelist);
        }


    }

}
