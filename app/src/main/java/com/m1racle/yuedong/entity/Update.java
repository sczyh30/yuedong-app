package com.m1racle.yuedong.entity;

/**
 * Update Entity
 * recommended to verify the file hash
 * before download
 */
public class Update {

    private int versionCode;
    private String versionName;
    private String downloadUrl;
    private String updateLog;
    private String releaseDate;
    private String file_hash;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getFileHash() {
        return file_hash;
    }

    public void setFileHash(String file_hash) {
        this.file_hash = file_hash;
    }
}
