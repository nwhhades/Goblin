package com.whiner.goblin.ui;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.whiner.goblin.R;
import com.whiner.tool.banner.TvBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

public class PicBannerAdapter extends TvBannerAdapter {

    @Override
    protected void bindData(BaseViewHolder<String> holder, String data, int position, int pageSize) {
        super.bindData(holder, data, position, pageSize);
        ImageView imageView = holder.itemView.findViewById(R.id.iv_pic);
        if (imageView != null) {
            Glide.with(imageView)
                    .load(data)
                    .into(imageView);
        }
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_banner_pic;
    }

}
