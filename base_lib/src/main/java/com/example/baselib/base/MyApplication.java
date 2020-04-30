package com.example.baselib.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.example.baselib.BuildConfig;
import com.example.baselib.router.IComponentApplication;
import com.example.baselib.router.ModuleConfig;
import com.example.baselib.util.CrashHandler;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.tencent.smtt.sdk.QbSdk;

public class MyApplication extends Application {

    public synchronized static Application getInstance() {
        return sInstance;
    }

    private static Application sInstance;
    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sContext = this;

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
