package com.duchen.template.utils.download;

public abstract class FileInfo {

    public abstract String getLocalPath();

    public abstract String getDownloadUrl();

    public boolean needMD5Check() {
        return false;
    }

    public String getMD5() {
        return "";
    }

}
