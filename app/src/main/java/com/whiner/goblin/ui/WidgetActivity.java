package com.whiner.goblin.ui;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.whiner.goblin.databinding.ActivityWidgetBinding;
import com.whiner.tool.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class WidgetActivity extends BaseActivity<ActivityWidgetBinding> {

    public static void start() {
        ActivityUtils.startActivity(WidgetActivity.class);
    }

    private static final String TAG = "WidgetActivity";

    @Override
    public ActivityWidgetBinding getViewBinding() {
        return ActivityWidgetBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {

        getLifecycle().addObserver(viewBinding.weather);

        Glide.with(viewBinding.ivRound)
                .load("https://t7.baidu.com/it/u=727460147,2222092211&fm=193&f=GIF")
                .into(viewBinding.ivRound);

        usbBanner();
    }

    private void usbBanner() {
        List<String> stringList = new ArrayList<>();
        stringList.add("https://t7.baidu.com/it/u=4198287529,2774471735&fm=193&f=GIF");
        stringList.add("https://t7.baidu.com/it/u=1956604245,3662848045&fm=193&f=GIF");
        stringList.add("https://t7.baidu.com/it/u=825057118,3516313570&fm=193&f=GIF");
        stringList.add("https://img2.baidu.com/it/u=1577373388,3492284830&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800");
        stringList.add("https://img2.baidu.com/it/u=63249423,2260265143&fm=253&fmt=auto&app=120&f=JPEG?w=889&h=500");
        stringList.add("https://img1.baidu.com/it/u=1839135015,723795615&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500");
        stringList.add("https://img0.baidu.com/it/u=922902802,2128943538&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800");

        PicBannerAdapter picBannerAdapter = new PicBannerAdapter();
        viewBinding.banner.registerLifecycleObserver(getLifecycle());
        viewBinding.banner.setAdapter(picBannerAdapter);
        viewBinding.banner.create();

        viewBinding.banner.refreshData(stringList);
    }

}
