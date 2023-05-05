package com.whiner.tool.banner;

import android.content.Context;
import android.util.AttributeSet;

import com.youth.banner.Banner;

public class TvBanner extends Banner<String, TvBannerAdapter> {

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
        setOnKeyListener(new OnTvBannerListener());
    }


    /*
    #修复4.4版本start多次的问题
     */
    private boolean looping = false;

    @Override
    public TvBanner start() {
        if (!looping) {
            super.start();
            looping = true;
        }
        return this;
    }

    @Override
    public TvBanner stop() {
        if (looping) {
            super.stop();
            looping = false;
        }
        return this;
    }

}
