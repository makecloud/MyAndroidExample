package com.liuyihui.mylibrary.entitiy.system;

import java.io.Serializable;

/**
 * app版本实体类
 * Created by liuyh on 2016/11/25.
 */

public class AppVersion implements Serializable {
    /** app最新版本 */
    private String latestVersion = null;
    /** app强制版本 */
    private String forceVersion = null;
    /** app安装包url */
    private String packageUrl = null;

    public String getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(String latestVersion) {
        this.latestVersion = latestVersion;
    }

    public String getForceVersion() {
        return forceVersion;
    }

    public void setForceVersion(String forceVersion) {
        this.forceVersion = forceVersion;
    }

    public String getPackageUrl() {
        return packageUrl;
    }

    public void setPackageUrl(String packageUrl) {
        this.packageUrl = packageUrl;
    }
}
