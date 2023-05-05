package com.whiner.tool.download;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.RegexUtils;
import com.hjq.toast.Toaster;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.StatusUtil;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

public class OkDownDialog extends AbsDownloadDialog {

    private static final String TAG = "OkDownDialog";

    private final DownloadListener1 downloadListener = new DownloadListener1() {
        @Override
        public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist.Listener1Model model) {
            Log.d(TAG, "taskStart: 下载开始");
            onViewStart();
        }

        @Override
        public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {

        }

        @Override
        public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {

        }

        @Override
        public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
            int p = (int) (currentOffset * 100f / totalLength);
            onViewProgress(p);
        }

        @Override
        public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull Listener1Assist.Listener1Model model) {
            Log.d(TAG, "taskEnd: " + cause);
            if (cause == EndCause.COMPLETED) {
                onDownSuccess(task.getFile());
            } else if (cause == EndCause.CANCELED) {
                onDownEnd();
            } else {
                onDownFail();
            }
            if (realCause != null) {
                Log.e(TAG, "taskEnd: ", realCause);
            }
        }
    };

    private DownloadTask downloadTask;

    public OkDownDialog(String title, String msg, String url, String path) {
        super(title, msg, url, path);
    }

    @Override
    public void initDownTask(String url, String path) {
        try {
            if (!RegexUtils.isURL(url)) return;
            downloadTask = new DownloadTask.Builder(url, path, "")
                    .setConnectionCount(1)
                    .setMinIntervalMillisCallbackProcess(50)
                    .setPassIfAlreadyCompleted(false)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startDownload() {
        try {
            if (downloadTask != null) {
                downloadTask.enqueue(downloadListener);
            } else {
                Toaster.showShort("下载地址为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopDownload() {
        try {
            if (downloadTask != null) {
                downloadTask.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isDownloading() {
        try {
            if (downloadTask != null) {
                return StatusUtil.Status.RUNNING.equals(StatusUtil.getStatus(downloadTask));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
