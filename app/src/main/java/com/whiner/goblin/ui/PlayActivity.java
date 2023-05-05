package com.whiner.goblin.ui;

import com.blankj.utilcode.util.ActivityUtils;
import com.whiner.goblin.databinding.ActivityPlayBinding;
import com.whiner.tool.ui.base.BaseActivity;

public class PlayActivity extends BaseActivity<ActivityPlayBinding> {

    public static void start() {
        ActivityUtils.startActivity(PlayActivity.class);
    }

    @Override
    public ActivityPlayBinding getViewBinding() {
        return ActivityPlayBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {

    }

}
