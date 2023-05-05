package com.whiner.tool.weather.tianqi;

import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.reflect.TypeToken;
import com.whiner.tool.R;
import com.whiner.tool.net.NetUtils;
import com.whiner.tool.net.OnNetListener;
import com.whiner.tool.net.base.GetRequest;
import com.whiner.tool.weather.OnWeatherListener;
import com.whiner.tool.weather.WeatherBean;
import com.whiner.tool.weather.WeatherFactory;

import io.reactivex.disposables.Disposable;

public class TianqiFactory implements WeatherFactory {

    private static final String TAG = "TianqiFactory";
    private static final String defaultUrl = "https://v0.yiketianqi.com/free/day?appid=43656176&appsecret=I42og6Lm";

    public static String getTianqiApiUrl() {
        return SPUtils.getInstance().getString(TAG, defaultUrl);
    }

    public static void setTianqiApiUrl(String url) {
        Log.d(TAG, "setTianqiApiUrl: 已更新天气地址");
        SPUtils.getInstance().put(TAG, url);
    }

    private OnWeatherListener onWeatherListener;
    private Disposable disposable;

    @Override
    public void startGetWeather(OnWeatherListener onWeatherListener) {
        this.onWeatherListener = onWeatherListener;
        stopGetWeather();
        GetRequest request = new GetRequest();
        request.setKey(TAG);
        request.setUrl1(getTianqiApiUrl());
        ///自动切换到备用的域名
        request.setUrl2(getTianqiApiUrl().replace("v0.yiketianqi.com", "yiketianqi.com"));
        request.setCacheType(GetRequest.CacheType.ONLY_CACHE);
        request.setCacheTime(5 * 60 * 1000);
        NetUtils.ONE.get(request, new OnNetListener<TianqiBean>() {
            @Override
            public TypeToken<TianqiBean> getTypeToken() {
                return new TypeToken<TianqiBean>() {
                };
            }

            @Override
            public void onStart(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSucceeded(TianqiBean data, boolean isCache) {
                Log.d(TAG, "onSucceeded: 请求成功" + data);
                if (data != null && data.getCity() != null) {
                    WeatherBean weatherBean = new WeatherBean();
                    weatherBean.setCity(data.getCity());
                    weatherBean.setWea(data.getWea());
                    weatherBean.setWea_img(getResIDByStr(data.getWea_img()));
                    weatherBean.setTem(data.getTem() + "℃");
                    weatherBean.setTem_day(data.getTem_day() + "℃");
                    weatherBean.setTem_night(data.getTem_night() + "℃");
                    getWeatherSuccess(weatherBean);
                } else {
                    getWeatherSuccess(null);
                }
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "onFailed: ", e);
                getWeatherSuccess(null);
            }

            @Override
            public void onEnd() {

            }
        });
    }

    @Override
    public void stopGetWeather() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void getWeatherSuccess(WeatherBean weather) {
        if (onWeatherListener != null) {
            onWeatherListener.onWeather(weather);
        }
    }

    private static int getResIDByStr(String str) {
        if (str == null) {
            return R.drawable.ic_weather_err;
        }
        int icon;
        switch (str) {
            case "xue":
                icon = R.drawable.ic_weather_xue;
                break;
            case "lei":
                icon = R.drawable.ic_weather_lei;
                break;
            case "shachen":
                icon = R.drawable.ic_weather_shachen;
                break;
            case "wu":
                icon = R.drawable.ic_weather_wu;
                break;
            case "bingbao":
                icon = R.drawable.ic_weather_bingbao;
                break;
            case "yun":
                icon = R.drawable.ic_weather_yun;
                break;
            case "yu":
                icon = R.drawable.ic_weather_yu;
                break;
            case "yin":
                icon = R.drawable.ic_weather_yin;
                break;
            case "qing":
                icon = R.drawable.ic_weather_qing;
                break;
            default:
                icon = R.drawable.ic_weather_err;
                break;
        }
        return icon;
    }

}
