package com.whiner.tool.banner;

import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

public abstract class TvBannerAdapter extends BaseBannerAdapter<String> {

    @Override
    protected void bindData(BaseViewHolder<String> holder, String data, int position, int pageSize) {
        holder.itemView.setClickable(false);
        holder.itemView.setFocusable(false);
        holder.itemView.setFocusableInTouchMode(false);
        holder.itemView.setOnClickListener(null);
    }

}
