package com.example.baselib.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.example.baselib.BuildConfig;
import com.example.baselib.http.OKHttpUpdateHttpService;
import com.example.baselib.router.IComponentApplication;
import com.example.baselib.router.ModuleConfig;
import com.example.baselib.util.CrashHandler;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.tencent.smtt.sdk.QbSdk;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.UpdateError;
import com.xuexiang.xupdate.listener.OnUpdateFailureListener;
import com.xuexiang.xupdate.utils.UpdateUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import static com.xuexiang.xupdate.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION;

public class MyApplication extends Application {

    public synchronized static Application getInstance() {
        return sInstance;
    }

    private static Application sInstance;
    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    private List<Activity> activities = new ArrayList<>();


    private void yichang() {
        Thread.setDefaultUncaughtExceptionHandler(restartHandler); // 程序崩溃时触发线程
        registerActivityListener();//activity管理类
    }

    public Thread.UncaughtExceptionHandler restartHandler = (thread, ex) -> {
        //下面为调试用的代码，发布时可注释
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.flush();
        printWriter.close();
        String result = info.toString();
        Log.i("sss", result);
        for (int i = 0; i < activities.size(); i++) {
            Log.i("sss", activities.get(i).getLocalClassName());
            if (activities.get(i) != null)
                activities.get(i).finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    };

    private void registerActivityListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    /**
                     *  监听到 Activity创建事件 将该 Activity 加入list
                     */
                    activities.add(activity);

                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    if (null == activities && activities.isEmpty()) {
                        return;
                    }
                    if (activities.contains(activity)) {
                        /**
                         *  监听到 Activity销毁事件 将该Activity 从list中移除
                         */
                        activities.remove(activity);
                    }
                }
            });
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sContext = this;

        // yichang();

        Utils.init(this);

        QMUISwipeBackActivityManager.init(this);
        //路由初始化
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);

        //Module类的APP初始化
        modulesApplicationInit();

        /**
         *   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         *              NotificationChannels.createAllNotificationChannels(this);//创建通知
         *         }
         */

        //CrashHandler.getInstance().init(this);
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);

        initAppUpdate();

    }

    /**
     * 初始化app更新
     */
    private void initAppUpdate() {
        //设置版本更新出错的监听
        XUpdate.get()
                .debug(true)
                .isWifiOnly(true)                                               //默认设置只在wifi下检查版本更新
                .isGet(false)                                                   //默认设置使用get请求检查版本
                .isAutoMode(false)                                              //默认设置非自动模式，可根据具体使用配置
                .param("versionCode", UpdateUtils.getVersionCode(this))         //设置默认公共请求参数
                .param("appKey", getPackageName())
                .setOnUpdateFailureListener(error -> {
                    if (error.getCode() != CHECK_NO_NEW_VERSION) {          //对不同错误进行处理
                        //ToastUtils.toast(error.toString());
                    }
                })
                .supportSilentInstall(true)                                     //设置是否支持静默安装，默认是true
                .setIUpdateHttpService(new OKHttpUpdateHttpService())           //这个必须设置！实现网络请求功能。
                .init(this);                                                    //这个必须初始化
    }

    /**
     * 搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
     */
    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

        @Override
        public void onViewInitFinished(boolean arg0) {
            //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            Log.e("APPApplication", " onViewInitFinished is " + arg0);
        }

        @Override
        public void onCoreInitFinished() {
            Log.e("APPApplication", " onCoreInitFinished");
        }
    };

    /**
     * 模块application 初始化
     */
    private void modulesApplicationInit() {
        for (String moduleImpl : ModuleConfig.MODULESLIST) {
            try {
                Class<?> clazz = Class.forName(moduleImpl);
                Object obj = clazz.newInstance();
                if (obj instanceof IComponentApplication) {
                    ((IComponentApplication) obj).onCreate(sInstance);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onTerminate() {
        //  ARouter.getInstance().destroy();//销毁
        super.onTerminate();
    }
}
