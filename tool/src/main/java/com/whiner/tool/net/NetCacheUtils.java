package com.whiner.tool.net;

import com.tencent.mmkv.MMKV;
import com.whiner.tool.cache.iface.IKVCache;

public enum NetCacheUtils implements IKVCache {
    ONE;

    private static final String TAG = "NetCacheUtils";
    private static final String KEY_TIME_SUFFIX = "_TIME";
    private final MMKV mmkv;

    NetCacheUtils() {
        this.mmkv = MMKV.mmkvWithID(TAG);
    }

    public String getKeySuffix() {
        return KEY_TIME_SUFFIX;
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
