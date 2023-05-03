package com.whiner.tool.net;

import android.util.Log;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.GsonUtils;
import com.whiner.tool.net.base.GetRequest;
import com.whiner.tool.net.service.GetService;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public enum NetUtils {
    ONE;

    private static final String TAG = "NetUtils";
    private final GetService getService;

    NetUtils() {
        this.getService = RetrofitUtils.ONE.createStringService(GetService.class);
    }

    public <T> void get(@NonNull GetRequest request, @NonNull OnNetListener<T> onNetListener) {
        if (request.broken()) return;
        final Type dataType = onNetListener.getTypeToken().getType();
        final GetRequest.CacheType cacheType = request.getCacheType();
        boolean returnData = true;
        T data;
        switch (cacheType) {
            case NO_CACHE:
                Log.d(TAG, "get: 不用缓存");
                break;
            case ONLY_CACHE:
                Log.d(TAG, "get: 用缓存，不再请求");
                data = readData(dataType, request.getKey());
                if (data != null) {
                    onNetListener.onStart(null);
                    onNetListener.onSucceeded(data, true);
                    onNetListener.onEnd();
                    return;
                }
                break;
            case FIRST_CACHE:
                Log.d(TAG, "get: 用缓存，再次请求");
                data = readData(dataType, request.getKey());
                if (data != null) {
                    onNetListener.onStart(null);
                    onNetListener.onSucceeded(data, true);
                    onNetListener.onEnd();
                    //继续请求，但是不返回数据了
                    returnData = false;
                }
                break;
            default:
                break;
        }
        final OnNetListener<T> onListener = returnData ? onNetListener : null;
        Observable<String> observable2 = getService.get(request.getUrl2())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach();
        getService.get(request.getUrl1())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(observable2)
                .onTerminateDetach()
                .doOnDispose(() -> {
                    if (onListener != null) {
                        onListener.onFailed(new Exception("主动取消请求"));
                        onListener.onEnd();
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (onListener != null) {
                            onListener.onStart(d);
                        }
                    }

                    @Override
                    public void onNext(String s) {
                        Exception exception = new Exception("解析数据失败了");
                        try {
                            T data = parseData(dataType, s);
                            if (data != null) {
                                //只有当数据格式是正确的才保存
                                saveData(cacheType, request.getCacheTime(), request.getKey(), s);
                                if (onListener != null) {
                                    onListener.onSucceeded(data, false);
                                }
                                return;
                            }
                        } catch (Exception e) {
                            exception = e;
                        }
                        if (onListener != null) {
                            onListener.onFailed(exception);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (onListener != null) {
                            onListener.onFailed(new Exception(e.getMessage()));
                            onListener.onEnd();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (onListener != null) {
                            onListener.onEnd();
                        }
                    }
                });
    }

    private void saveData(@NonNull GetRequest.CacheType cacheType, long cacheTime, @NonNull String key, String data) {
        if (cacheType == GetRequest.CacheType.NO_CACHE || data == null) return;
        NetCacheUtils.ONE.put(key, data, cacheTime);
    }

    private <T> T readData(@NonNull Type type, @NonNull String key) {
        String s = NetCacheUtils.ONE.get(key, null);
        return parseData(type, s);
    }

    @SuppressWarnings("unchecked")
    private <T> T parseData(@NonNull final Type type, final String s) {
        if (s == null) return null;
        T data = null;
        try {
            if (type == String.class) {
                data = (T) s;
            } else {
                data = GsonUtils.fromJson(s, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}
