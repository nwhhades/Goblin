package com.whiner.tool.cache;

import com.tencent.mmkv.MMKV;
import com.whiner.tool.cache.iface.IKVCache;

public enum MMKVCache implements IKVCache {
    ONE;

    private final MMKV mmkv;

    MMKVCache() {
        mmkv = MMKV.defaultMMKV();
    }

    private String getKeySuffix() {
        return "_TIME";
    }

    @Override
    public long getNotExpireTime() {
        return -1;
    }

    @Override
    public String get(String key, String def) {
        if (key == null) return def;
        long outTime = mmkv.getLong(key + getKeySuffix(), 0);
        if (outTime > System.currentTimeMillis() || outTime == getNotExpireTime()) {
            return mmkv.getString(key, def);
        } else {
            mmkv.removeValueForKey(key);
            mmkv.removeValueForKey(key + getKeySuffix());
        }
        return def;
    }

    @Override
    public void put(String key, String val, long keepTime) {
        if (key == null || val == null) return;
        mmkv.putString(key, val);
        if (keepTime > 0) {
            mmkv.putLong(key + getKeySuffix(), System.currentTimeMillis() + keepTime);
        } else {
            mmkv.putLong(key + getKeySuffix(), getNotExpireTime());
        }
    }

    @Override
    public void put(String key, String val) {
        put(key, val, getNotExpireTime());
    }

}
