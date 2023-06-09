package com.whiner.tool.download;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.AppUtils;
import com.hjq.toast.Toaster;
import com.whiner.tool.R;
import com.whiner.tool.databinding.FragmentDownloadDialogBinding;
import com.whiner.tool.download.iface.IDownloadView;
import com.whiner.tool.install.ApkReceiver;
import com.whiner.tool.install.InstallEvent;
import com.whiner.tool.install.InstallUtils;
import com.whiner.tool.ui.base.BaseDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

public abstract class AbsDownloadDialog extends BaseDialogFragment<FragmentDownloadDialogBinding> implements IDownloadView, View.OnClickListener {

    private static final String TAG = "AbsDownloadDialog";

    protected String title;
    protected String msg;
    protected String url;
    protected String path;
    private ApkReceiver apkReceiver;

    public AbsDownloadDialog(String title, String msg, String url, String path) {
        this.title = title;
        this.msg = msg;
        this.url = url;
        this.path = path;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn1) {
            //下载与暂停
            if (isDownloading()) {
                stopDownload();
            } else {
                startDownload();
            }
        } else {
            //取消
            hideFragment();
        }
    }

    @Override
    public void onViewStart() {
        //设置按钮文本为暂停
        setBtn1("暂停");
    }

    @Override
    public void onViewStop() {
        //设置按钮文本为重试
        setBtn1("重试");
    }

    @Override
    public void onViewProgress(int progress) {
        setProgressBar(progress);
    }

    @Override
    public void onDownSuccess(File file) {
        try {
            //获取安装包信息
            AppUtils.AppInfo appInfo = AppUtils.getApkInfo(file);
            if (appInfo != null) {
                //获取的信息保存起来
                String apkPackageName = appInfo.getPackageName();
                //开始安装
                int code = InstallUtils.install(file);
                if (code == 1) {
                    //方式二 取消用户手动hide的权限
                    showInstalling();
                    //添加安装进度的监听
                    Activity activity = getActivity();
                    if (activity != null) {
                        apkReceiver = new ApkReceiver(apkPackageName, activity);
                        getLifecycle().addObserver(apkReceiver);
                        return;
                    }
                }
            } else {
                Log.d(TAG, "onDownSuccess: 读取APK信息失败");
                Toaster.show("APK文件错误");
            }
            //自动关闭
            hideFragment();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDownFail() {
        Toaster.showShort("下载失败");
        onViewStop();
    }

    @Override
    public void onDownEnd() {
        Toaster.showShort("下载结束");
        onViewStop();
    }

    @Override
    public FragmentDownloadDialogBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentDownloadDialogBinding.inflate(inflater, container, false);
    }

    @Override
    public void initView() {
        //不允许用户返回取消
        setCancelable(false);
        viewBinding.tvTitle.setText(title);
        viewBinding.tvMsg.setText(msg);
        viewBinding.btn1.setOnClickListener(this);
        viewBinding.btn2.setOnClickListener(this);
        //初始化下载
        initDownTask(url, path);
        Log.d(TAG, "initView: " + url + " - " + path);
        EventBus.getDefault().register(this);
        Log.d(TAG, "initView: 监听Event");
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Log.d(TAG, "onDismiss: 注销Event");
        EventBus.getDefault().unregister(this);
        //停止下载
        stopDownload();
        super.onDismiss(dialog);
    }

    protected void setBtn1(final String text) {
        if (viewBinding != null && isVisible()) {
            viewBinding.btn1.setText(text);
        }
    }

    protected void setProgressBar(int p) {
        if (viewBinding != null && isVisible()) {
            viewBinding.tvProgress.setText(getString(R.string.tv_progress, p));
            viewBinding.progressBar.setProgress(p);
        }
    }

    protected void showInstalling() {
        if (viewBinding != null && isVisible()) {
            viewBinding.tvProgress.setText("");
            viewBinding.progressBar.setIndeterminate(true);
            viewBinding.btn1.setText("安装中");
            viewBinding.btn1.setOnClickListener(null);
            viewBinding.btn2.setOnClickListener(null);
        }
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onInstallEvent(InstallEvent event) {
        Log.d(TAG, "onInstallEvent: 收到了安装成功事件");
        if (apkReceiver != null) {
            if (event.getPackageName().equals(apkReceiver.getApkPackageName())) {
                Toaster.show(event.getName() + " - 安装成功");
                //安装成功了
                hideFragment();
            }
        }
    }

}
