package com.whiner.tool.weather.tianqi;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.whiner.tool.R;
import com.whiner.tool.databinding.LayoutTianqiViewBinding;
import com.whiner.tool.weather.OnWeatherListener;
import com.whiner.tool.weather.WeatherBean;

public class TianqiView extends FrameLayout implements LifecycleEventObserver, OnWeatherListener {


    public TianqiView(@NonNull Context context) {
        this(context, null);
    }

    public TianqiView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TianqiView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private LayoutTianqiViewBinding binding;
    private TianqiFactory tianqiFactory;

    private void initView() {
        binding = LayoutTianqiViewBinding.inflate(LayoutInflater.from(getContext()), this, true);
        tianqiFactory = new TianqiFactory();
    }

    @Override
    public void onWeather(WeatherBean bean) {
        Log.d("TAG", "onWeather: " + bean);
        if (bean == null) {
            binding.ivWeatherWea.setImageResource(R.drawable.ic_weather_err);
            binding.tvWeatherWea.setText("");
            binding.tvWeatherCity.setText("");
            binding.tvWeatherTem.setText("");
            binding.tvWeatherWendu.setText("");
            binding.tvWeatherErr.setText("暂无数据");
        } else {
            binding.ivWeatherWea.setImageResource(bean.getWea_img());
            binding.tvWeatherWea.setText(bean.getWea());
            binding.tvWeatherCity.setText(bean.getCity());
            binding.tvWeatherTem.setText(bean.getTem());
            String wendu = bean.getTem_night() + " ~ " + bean.getTem_day();
            binding.tvWeatherWendu.setText(wendu);
            binding.tvWeatherErr.setText("");
        }
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        switch (event) {
            case ON_RESUME:
                tianqiFactory.startGetWeather(TianqiView.this);
                break;
            case ON_PAUSE:
                tianqiFactory.stopGetWeather();
                break;
            default:
                break;
        }
    }

}
