package com.lalamove.huolala.offline.webview.info;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @copyright：深圳依时货拉拉科技有限公司
 * @fileName: OfflineRuleConfig
 * @author: kelvin
 * @date: 1/18/22
 * @description: 离线包规则
 * @history:
 */

@Keep
public class OfflineRuleConfig {

    private List<RulesInfo> rules;

    public OfflineRuleConfig() {
        rules = new ArrayList<>();
    }

    public List<RulesInfo> getRules() {
        return rules;
    }

    public void addRule(RulesInfo rulesInfo) {
        if (rules.contains(rulesInfo)) {
            return;
        }
        rules.add(rulesInfo);
    }

    public void addRules(List<RulesInfo> rulesInfoList) {
        for (RulesInfo rulesInfo : rulesInfoList) {
            addRule(rulesInfo);
        }
    }

    @Keep
    public static class RulesInfo {
        /**
         * host : ["www.xxx.cn","xxxx.cn"]
         * path : ["/pathA"]
         * fragmentprefix  : ["/fragmentA","/fragmentB"]
         * offweb : bisname
         */

        @SerializedName("offweb")
        private String offWeb;
        private List<String> host;
        private List<String> path;
        @SerializedName("fragmentprefix")
        private List<String> fragmentPrefix;

        public RulesInfo(String offweb, List<String> host, List<String> path, List<String> fragmentprefix) {
            this.offWeb = offweb;
            this.host = host;
            this.path = path;
            this.fragmentPrefix = fragmentprefix;
        }

        public String getOffWeb() {
            return offWeb;
        }

        public List<String> getHost() {
            return host;
        }

        public List<String> getPath() {
            return path;
        }

        public List<String> getFragmentPrefix() {
            return fragmentPrefix;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            RulesInfo rulesInfo = (RulesInfo) o;
            return Objects.equals(offWeb, rulesInfo.offWeb);
        }

        @Override
        public int hashCode() {
            return Objects.hash(offWeb);
        }
    }
}
