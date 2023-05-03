package com.whiner.tool.net.base;

import androidx.annotation.NonNull;

public class GetRequest {

    private String key;
    private String url1;
    private String url2;
    private CacheType cacheType = CacheType.NO_CACHE;
    private long cacheTime = 0;

    public boolean broken() {
        return key == null || url1 == null || cacheType == null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public CacheType getCacheType() {
        return cacheType;
    }

    public void setCacheType(CacheType cacheType) {
        this.cacheType = cacheType;
    }

    public long getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
    }

    public enum CacheType {
        ONLY_CACHE, FIRST_CACHE, NO_CACHE
    }

    @NonNull
    @Override
    public String toString() {
        return "GetRequest{" +
                "key='" + key + '\'' +
                ", url1='" + url1 + '\'' +
                ", url2='" + url2 + '\'' +
                ", cacheType=" + cacheType +
                ", cacheTime=" + cacheTime +
                '}';
    }

}
