package com.whiner.tool.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.zhpan.bannerview.BannerViewPager;

public class TvBanner extends BannerViewPager<String> implements View.OnFocusChangeListener {

    public TvBanner(Context context) {
        this(context, null);
    }

    public TvBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TvBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /*
    #添加按键处理
     */

    private void initView() {
        setClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setOnFocusChangeListener(this);
        setOnKeyListener(new OnTvBannerListener());
        setAutoPlay(false);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setAutoPlay(false);
            stopLoop();
        } else {
            setAutoPlay(true);
            startLoop();
        }
    }

}
