package com.whiner.tool.banner;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.whiner.tool.R;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.List;

public class TvBannerAdapter extends BannerAdapter<String, BannerImageHolder> {

    public TvBannerAdapter(List<String> list) {
        super(list);
    }

    @Override
    public BannerImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //拉伸铺满
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return new BannerImageHolder(imageView);
    }

    @Override
    public void onBindView(BannerImageHolder holder, String data, int position, int size) {
        Glide.with(holder.imageView)
                .load(data)
                .placeholder(R.drawable.img_bg)
                .error(R.drawable.img_bg_err)
                .into(holder.imageView);
    }

}
