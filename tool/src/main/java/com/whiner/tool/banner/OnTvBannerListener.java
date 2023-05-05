package com.whiner.tool.banner;

import android.view.KeyEvent;
import android.view.View;

import com.youth.banner.util.BannerUtils;

public class OnTvBannerListener implements View.OnKeyListener {

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (v instanceof TvBanner) {
            TvBanner banner = (TvBanner) v;
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        actionBanner(1, banner);
                        return true;
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                        actionBanner(2, banner);
                        return true;
                    default:
                        break;
                }
            } else {
                banner.start();
            }
        }
        return false;
    }

    private void actionBanner(int action, TvBanner banner) {
        banner.stop();
        int count = banner.getItemCount();
        int index;
        if (action == 1) {
            //左
            index = (banner.getCurrentItem() - 1) % count;
        } else {
            //右
            index = (banner.getCurrentItem() + 1) % count;
        }
        if (index == 0) {
            index = banner.getRealCount();
            banner.setCurrentItem(index, false);
            int real = BannerUtils.getRealPosition(banner.isInfiniteLoop(), banner.getCurrentItem(), banner.getRealCount());
            banner.getIndicator().onPageSelected(real);
        } else if (index == count - 1) {
            index = 1;
            banner.setCurrentItem(index, false);
            int real = BannerUtils.getRealPosition(banner.isInfiniteLoop(), banner.getCurrentItem(), banner.getRealCount());
            banner.getIndicator().onPageSelected(real);
        } else {
            banner.setCurrentItem(index);
        }
    }

}
