package com.example.main_module.activity;


import android.Manifest;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PathUtils;
import com.bumptech.glide.Glide;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.AppBean;
import com.example.main_module.R;
import com.example.main_module.contract.AppContract;
import com.example.main_module.presenter.AppPresenter;
import com.example.main_module.utils.HProgressDialogUtils;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.service.OnFileDownloadListener;

import java.io.File;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;


import static com.blankj.utilcode.util.AppUtils.getAppVersionCode;
import static com.example.baselib.util.PackageUtils.getAppName;
import static com.example.baselib.util.PackageUtils.getBitmap;
import static com.example.baselib.util.PackageUtils.getVersionName;

@RuntimePermissions
public class AppUpdateActivity extends AppMvpBaseActivity implements View.OnClickListener, AppContract.IView {

    private ImageView mAppIcon;
    private TextView mAppName;
    private TextView mAppVersion;
    private Button mUpdateBt;
    private String mDownloadUrl;//下载链接

    private AppPresenter mPresenter = new AppPresenter();//app支持

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        setSubmitEnable(false);//不展示提交按钮
        mNetErrorView.setBackgroundColor(0xffffffff);
        initView();
        initData();
        initListener();
        mPresenter.attachView(this);//绑定view
        mPresenter.getAppInfo();//得到app信息
    }

    @Override
    protected String getTitleName() {
        return "应用信息";
    }

    @Override
    protected String getRightTextName() {
        return null;
    }

    @Override
    protected int getFloatBtImgRes() {
        return 0;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.main_layout_app_update;
    }

    @Override
    protected void onFloatBtClick() {

    }

    private void initView() {

        mAppIcon = findViewById(R.id.appIcon);
        mAppName = findViewById(R.id.appName);
        mAppVersion = findViewById(R.id.appVersion);
        mUpdateBt = findViewById(R.id.updateBt);
    }

    private void initData() {
        mAppName.setText(getAppName(this));
        mAppVersion.setText("版本名称：" + getVersionName(this) + "\n版本号：" + getAppVersionCode());
        //mAppIcon.setImageBitmap(getBitmap(this));
        Glide.with(this).load(getBitmap(this)).error(R.drawable.icon_circle)
                .into(mAppIcon);
    }


    private void initListener() {
        mUpdateBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateBt:
                useApkDownLoadFunction();
                break;
        }
    }

    @Override
    public void setAppInfo(AppBean appBean) {
        if (appBean != null) {
            Glide.with(this).load(ServerInfo.getImageAddress(appBean.getApp_icon())).error(R.drawable.icon_circle)
                    .into(mAppIcon);
            mAppName.setText(appBean.getApp_name());
            mAppVersion.setText("版本名称：" + appBean.getVersion_name() + "\n版本号：\u3000" + appBean.getVersion_code());//版本号
            mDownloadUrl = ServerInfo.getServerAddress(appBean.getApp_url());
            if (hasUpdate(appBean.getVersion_code(), appBean.getVersion_name())) {
                mUpdateBt.setEnabled(true);//更新按钮可用
                mUpdateBt.setText("下载最新版");
            }
        }
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void useApkDownLoadFunction() {
        XUpdate.newBuild(this)
                .apkCacheDir(PathUtils.getExternalDownloadsPath())
                .build()
                .download(mDownloadUrl, new OnFileDownloadListener() {
                    @Override
                    public void onStart() {
                        HProgressDialogUtils.showHorizontalProgressDialog(getContext(), "下载进度", false);
                    }

                    @Override
                    public void onProgress(float progress, long total) {
                        HProgressDialogUtils.setProgress(Math.round(progress * 100));
                    }

                    @Override
                    public boolean onCompleted(File file) {
                        HProgressDialogUtils.cancel();
                        showSuccessHint("apk下载完毕，文件路径：" + file.getPath());
                        return false;
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        HProgressDialogUtils.cancel();
                    }
                });
    }

    /**
     * @return
     */
    private boolean hasUpdate(int versionCode, String versionName) {

        if (versionCode > AppUtils.getAppVersionCode())
            return true;


        if (!TextUtils.isEmpty(versionName) && !TextUtils.isEmpty(AppUtils.getAppVersionName())) {
            String[] nowVersionsNames = AppUtils.getAppVersionName().split("\\.");
            String[] versionsNames = versionName.split("\\.");


            for (int i = 0; i < nowVersionsNames.length && i < versionsNames.length; i++) {
                int versionCode1 = Integer.parseInt(nowVersionsNames[i]);
                int versionCode2 = Integer.parseInt(versionsNames[i]);
                if (versionCode2 > versionCode1)
                    return true;
            }
        }

        return false;
    }


    /**
     * 销毁时
     */
    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }
}

