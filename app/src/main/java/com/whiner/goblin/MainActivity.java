package com.whiner.goblin;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.PathUtils;
import com.google.gson.reflect.TypeToken;
import com.whiner.goblin.databinding.ActivityMainBinding;
import com.whiner.goblin.ui.WidgetActivity;
import com.whiner.tool.download.OkDownDialog;
import com.whiner.tool.net.NetUtils;
import com.whiner.tool.net.OnNetListener;
import com.whiner.tool.net.base.CacheType;
import com.whiner.tool.net.base.GetRequest;
import com.whiner.tool.net.base.NetResult;
import com.whiner.tool.net.base.PostRequest;
import com.whiner.tool.ui.base.BaseActivity;
import com.whiner.tool.ui.base.BaseDialogFragment;
import com.whiner.tool.ui.fragment.AlertFragment;
import com.whiner.tool.weather.tianqi.TianqiFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private static final String TAG = "MainActivity";

    private static final String apk_url1 = "http://down.wireless-tech.cn/resourceDown/HX-AT-MESH-APK/hx-mesh-release-v0.1.0.apk";
    private static final String apk_url2 = "http://file.mounriver.com/upgrade/MounRiver_Studio_Setup_V184.zip";

    private static final String url1 = "http://www.dfasdfasdfa.com/";
    private static final String url2 = "http://101.133.235.5:8686/api/settings/getSettings";

    @Override
    public ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {

        TianqiFactory.setTianqiApiUrl("https://www.yiketianqi.com/free/day?appid=43656176&appsecret=I42og6Lm");

        viewBinding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getNetworking();
                postData();
            }
        });
        viewBinding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetActivity.start();
            }
        });
        viewBinding.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkDownDialog okDownDialog = new OkDownDialog("爱奇艺", "下载安装", apk_url1, PathUtils.getExternalAppDataPath());
                okDownDialog.showFragment(getSupportFragmentManager());
            }
        });
        viewBinding.btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkDownDialog okDownDialog = new OkDownDialog("腾讯视频", "下载安装", apk_url2, PathUtils.getExternalAppDataPath());
                okDownDialog.showFragment(getSupportFragmentManager());
            }
        });


        viewBinding.btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertFragment alertFragment = new AlertFragment("A", "B", "c12312312312312312", "d12312312312312312312");
                alertFragment.setOnActionListener(new BaseDialogFragment.OnActionListener() {
                    @Override
                    public void onAction(@NonNull BaseDialogFragment<?> dialogFragment, int btn_index) {
                        Log.d(TAG, "onAction: " + btn_index);
                    }

                    @Override
                    public void onDismiss(@NonNull DialogInterface dialog) {
                        Log.d(TAG, "onDismiss: " + dialog);
                    }

                    @Override
                    public void onCancel(@NonNull DialogInterface dialog) {
                        Log.d(TAG, "onCancel: " + dialog);
                    }

                });
                alertFragment.showFragment(getSupportFragmentManager());
            }
        });
    }

    private Disposable disposable;

    private void getNetworking() {
        GetRequest getRequest = new GetRequest();
        getRequest.setKey("setting");
        getRequest.setUrl1(url1);
        getRequest.setUrl2(url2);
        getRequest.setCacheTime(5000);
        getRequest.setCacheType(CacheType.ONLY_CACHE);
        NetUtils.ONE.get(getRequest, new OnNetListener<NetResult<SettingsBean>>() {
            @Override
            public TypeToken<NetResult<SettingsBean>> getTypeToken() {
                return new TypeToken<NetResult<SettingsBean>>() {
                };
            }

            @Override
            public void onStart(Disposable d) {
                Log.d(TAG, "onStart: 请求开始");
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
                disposable = d;
                showLoadingView("请求中...");
            }

            @Override
            public void onSucceeded(NetResult<SettingsBean> data, boolean isCache) {
                Log.d(TAG, "onSucceeded: 请求成功 " + data + " - " + isCache);
                String text = getRequest + (isCache ? "\n\n\n读取的缓存\n\n\n" : "\n\n\n实时的请求\n\n\n") + new Date() + "\n\n\n" + data;
                viewBinding.msg.setText(text);
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "onFailed: ", e);
                String text = Log.getStackTraceString(e);
                viewBinding.msg.setText(text);
            }

            @Override
            public void onEnd() {
                Log.d(TAG, "onEnd: 请求结束");
                hideLoadingView();
            }

            @Override
            public String decode(String data) {
                Log.d(TAG, "decode: 请求到的原始数据");
                return data;
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private void postData() {
        Map<String, Object> args = new HashMap<>();
        args.put("name", 1);
        args.put("message", "abc");

        PostRequest postRequest = new PostRequest();
        postRequest.setKey("post");
        postRequest.setUrl1("http://192.168.50.52:8080/post");
        postRequest.setUrl2("");
        postRequest.setArgs(args);
        postRequest.setCacheType(CacheType.ONLY_CACHE);
        postRequest.setCacheTime(3000);

        NetUtils.ONE.post(postRequest, new OnNetListener<String>() {
            @Override
            public TypeToken<String> getTypeToken() {
                return new TypeToken<String>() {
                };
            }

            @Override
            public void onStart(Disposable d) {

            }

            @Override
            public void onSucceeded(String data, boolean isCache) {
                Log.d(TAG, "onSucceeded: " + data);
            }

            @Override
            public void onFailed(Exception e) {

            }

            @Override
            public void onEnd() {

            }

            @Override
            public String decode(String data) {
                return data;
            }
        });

    }

}