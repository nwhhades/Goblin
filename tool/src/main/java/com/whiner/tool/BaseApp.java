package com.whiner.tool;

import android.app.Application;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.hjq.toast.Toaster;
import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVLogLevel;
import com.whiner.tool.toaster.BigBlackToastStyle;

public abstract class BaseApp extends Application {

    private static final String TAG = "BaseApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        initMMKV();
        initToaster();
        init();
    }

    private void initMMKV() {
        String root = MMKV.initialize(this);
        Log.d(TAG, "initMMKV: MMKV root is " + root);
        MMKV.setLogLevel(MMKVLogLevel.LevelWarning);
    }

    protected void initToaster() {
        Toaster.init(this, new BigBlackToastStyle());
    }

    protected abstract void init();

}
