package com.whiner.tool.download.iface;

import java.io.File;

public interface IDownloadView {

    /**
     * 初始化下载任务
     *
     * @param url  下载地址
     * @param path 下载目录
     */
    void initDownTask(String url, String path);

    /**
     * 开始下载
     */
    void startDownload();

    /**
     * 停止下载
     */
    void stopDownload();

    /**
     * 判断是否下载中
     *
     * @return 下载状态
     */
    boolean isDownloading();

    /**
     * 下载已启动时候的View状态
     */
    void onViewStart();

    /**
     * 下载已停止时候的View状态
     */
    void onViewStop();

    /**
     * 刷新下载进度
     *
     * @param progress 进度
     */
    void onViewProgress(int progress);

    /**
     * 下载成功
     *
     * @param file 下载的文件
     */
    void onDownSuccess(File file);

    /**
     * 下载失败
     */
    void onDownFail();

    /**
     * 下载停止
     */
    void onDownEnd();

}
