package com.example.gradleasm;

public class TimerExtension {
    private long threshold = -1;
    private String packageName = "";

    public TimerExtension(){}

    public long getThreshold() {
        return threshold;
    }

    public void setThreshold(long threshold) {
        this.threshold = threshold;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
