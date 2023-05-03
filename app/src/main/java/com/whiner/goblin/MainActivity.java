package com.whiner.goblin;

import android.os.Handler;
import android.view.View;

import com.blankj.utilcode.util.PathUtils;
import com.whiner.goblin.databinding.ActivityMainBinding;
import com.whiner.tool.download.OkDownDialog;
import com.whiner.tool.ui.base.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private static final String TAG = "MainActivity";

    private static final String JSON = "{\"data\":{\"apk_version\":1,\"apk_file\":\"http://101.133.235.5:86/assets/img/qrcode.png\",\"apk_bg_image\":\"http://101.133.235.5:86/assets/img/qrcode.png\",\"apk_home_skin\":\"skin_1\",\"apk_home_logo_image\":\"http://101.133.235.5:86/assets/img/qrcode.png\",\"apk_home_msg\":\"游字\",\"mqtt_url\":\"www.baidu.com\",\"mqtt_taps\":\"123,7887\",\"shop_qrcode_image\":\"http://101.133.235.5:86/assets/img/qrcode.png\",\"shop_qrcode_tip\":\"提示\",\"home_data_version\":\"123456\"},\"total\":0,\"code\":200,\"page\":0,\"msg\":\"请求成功\"}";


    private static final String url = "http://down.wireless-tech.cn/resourceDown/HX-AT-MESH-APK/hx-mesh-release-v0.1.0.apk";

    private static final String url1 = "http://file.mounriver.com/upgrade/MounRiver_Studio_Setup_V184.zip";

    @Override
    public ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        viewBinding.tvTtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showAAA();

                OkDownDialog okDownDialog = new OkDownDialog("下载安装", "爱奇艺", url1, PathUtils.getExternalAppDataPath());
                okDownDialog.showFragment(getSupportFragmentManager());

            }
        });
    }


    private void showAAA() {
        showLoadingView("ssss");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadingView();
            }
        }, 5000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}