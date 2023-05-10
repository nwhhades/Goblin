package com.whiner.tool.banner;

import android.view.KeyEvent;
import android.view.View;

public class OnTvBannerListener implements View.OnKeyListener {

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (v instanceof TvBanner) {
            TvBanner banner = (TvBanner) v;
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        try {
                            actionBanner(1, banner);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                        try {
                            actionBanner(2, banner);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    default:
                        break;
                }
            }
        }
        return false;
    }

    private void actionBanner(int action, TvBanner banner) throws Exception {
        int count = banner.getData().size();
        int start = 0;
        int end = count - 1;
        int index;
        if (action == 1) {
            //左
            index = banner.getCurrentItem() - 1;
        } else {
            //右
            index = banner.getCurrentItem() + 1;
        }
        if (index < start) {
            index = end;
            banner.setCurrentItem(index, false);
        } else if (index > end) {
            index = start;
            banner.setCurrentItem(index, false);
        } else {
            banner.setCurrentItem(index);
        }
    }

}
