package com.whiner.tool.install;

import androidx.annotation.NonNull;

public class InstallEvent {

    private String name;
    private String packageName;
    private boolean success;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @NonNull
    @Override
    public String toString() {
        return "InstallEvent{" +
                "name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", success=" + success +
                '}';
    }

}
