package com.example.zhixiaoapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.baselib.base.MVPBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.zhixiaoapp.R;
import com.example.zhixiaoapp.contract.StartContract;
import com.example.zhixiaoapp.presenter.StartPresenter;


@Route(path = ARouterContract.LOGIN_START)
public class StartActivity extends MVPBaseActivity implements StartContract.IView {

    private StartPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_start);
        fullScreen();//全屏显示
        mPresenter = new StartPresenter();//初始化一下
        mPresenter.attachView(this);//绑定一下
        mPresenter.initInfo();//初始化信息
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

    /**
     * 启动主界面
     */
    @Override
    public void startMainActivity() {
        overridePendingTransition(R.anim.app_fade_in, R.anim.app_fade_out);//淡入淡出
        ARouter.getInstance().build(ARouterContract.MAIN_MAIN) // 目标页面
                .navigation();
        finish();//销毁自己
    }

    /**
     * 启动登陆页面
     */
    @Override
    public void startLoginActivity() {
        overridePendingTransition(R.anim.app_fade_in, R.anim.app_fade_out);//淡入淡出
        startActivity(new Intent(StartActivity.this, LoginActivity.class));
        finish();//销毁自己
    }

    /**
     * 销毁时
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();//解除绑定
    }

}
