package com.whiner.goblin.ui;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.whiner.goblin.databinding.ActivityWidgetBinding;
import com.whiner.tool.ui.base.BaseActivity;

public class WidgetActivity extends BaseActivity<ActivityWidgetBinding> {

    public static void start() {
        ActivityUtils.startActivity(WidgetActivity.class);
    }

    @Override
    public ActivityWidgetBinding getViewBinding() {
        return ActivityWidgetBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        Glide.with(viewBinding.ivRound)
                .load("http://lmg.jj20.com/up/allimg/4k/s/02/210924233115O14-0-lp.jpg")
                .into(viewBinding.ivRound);
    }

}
