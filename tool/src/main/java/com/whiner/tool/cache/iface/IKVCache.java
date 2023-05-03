package com.whiner.tool.cache.iface;

public interface IKVCache {

    long getNotExpireTime();

    String get(String key, String def);

    void put(String key, String val, long keepTime);

    void put(String key, String val);

}
