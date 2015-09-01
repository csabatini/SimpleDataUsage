package com.slickmobile.simpledatausage.model;

public class InstalledApp {

    private int uid;
    private long bytes;
    private String packageName;
    private String label;

    public InstalledApp(int uid, String packageName, String label) {
        this.uid = uid;
        this.packageName = packageName;
        this.label = label;
        this.bytes = 0;
    }

    public InstalledApp(int uid, long bytes, String packageName, String label) {
        this.uid = uid;
        this.bytes = bytes;
        this.packageName = packageName;
        this.label = label;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public long getBytes() {
        return bytes;
    }

    public void setBytes(long bytes) {
        this.bytes = bytes;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
