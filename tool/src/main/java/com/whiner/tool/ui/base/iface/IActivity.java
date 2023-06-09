package com.whiner.tool.ui.base.iface;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

public interface IActivity<V extends ViewBinding> {

    /**
     * 获取ViewBinding
     *
     * @return ViewBinding
     */
    V getViewBinding();

    /**
     * 预初始化
     */
    void preInit();

    /**
     * 初始化View
     */
    void initView();

    /**
     * 是否启用APP全局背景
     *
     * @return true false
     */
    boolean enableAppBackground();

    /**
     * 初始化APP背景
     */
    void initAppBackground();

    /**
     * 加载url到ImageView
     *
     * @param src 图片资源
     */
    void loadAppBackground(Object src);

    /**
     * 读取APP背景地址
     *
     * @return 图片资源
     */
    Object readAppBackgroundSrc();

    /**
     * 写入APP背景地址到缓存中
     *
     * @param src 图片资源
     */
    void writeAppBackgroundSrc(@NonNull Object src);

    /**
     * 显示LoadingView
     *
     * @param msg 按返回键提示文本
     */
    void showLoadingView(@NonNull String msg);

    /**
     * 隐藏LoadingView
     */
    void hideLoadingView();

}
