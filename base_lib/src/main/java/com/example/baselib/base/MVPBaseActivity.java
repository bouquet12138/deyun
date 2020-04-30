package com.example.baselib.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.baselib.util.NetChangeObserver;
import com.example.baselib.util.NetType;
import com.example.baselib.util.NetworkManager;
import com.example.baselib.progress.AppProgressBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 所有需要mvp设计模式的activity的基类
 * Created by xiaohan on 2018/3/4.
 */

public class MVPBaseActivity extends AppCompatActivity implements IMVPBaseView, NetChangeObserver {


    private AppProgressBar appProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        appProgressBar = new AppProgressBar(this, "");

        NetworkManager.getDefault().init(getApplication());
        NetworkManager.getDefault().setListener(this);
    }

    /**
     * 设置对话框提示信息
     *
     * @param msg
     */
    @Override
    public void setLoadingHint(String msg) {
        if (appProgressBar != null)
            appProgressBar.setHintText(msg);//设置提示信息
    }

    /**
     * 展示加载进度
     *
     * @param msg
     */
    @Override
    public void showLoading(String msg) {
        if (!appProgressBar.isShowing()) {
            appProgressBar.show();
            appProgressBar.setHintText(msg);//设置提示信息
        }
    }


    /**
     * 隐藏加载进度
     */
    @Override
    public void hideLoading() {
        if (appProgressBar.isShowing()) {
            appProgressBar.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLongToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public Context getContext() {
        return MVPBaseActivity.this;
    }

    @Override
    public void startLogin() {
        ActivityCollector.finishAll();//销毁所有activity
        ARouter.getInstance().build("/app/login") // 目标页面
                .navigation();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    /**
     * 销毁时
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        NetworkManager.getDefault().logout();//注销监听
    }

    /**
     * 全屏
     */
    protected void fullScreen() {
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Override
    public void onConnected(NetType type) {
    }

    @Override
    public void onDisConnected() {
    }


}
