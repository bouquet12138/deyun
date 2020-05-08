package com.example.zhixiaoapp.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.baselib.base.MVPBasePresenter;
import com.example.baselib.listener.OnGetInfoListener;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.LoginAndRegisterModel;
import com.example.common_lib.model.UserModel;
import com.example.zhixiaoapp.contract.RegisterContract;

public class RegisterPresenter extends MVPBasePresenter<RegisterContract.IView>
        implements RegisterContract.IPresenter {

    private UserModel mModel = new UserModel();
    private LoginAndRegisterModel mRegisterModel = new LoginAndRegisterModel();

    private final int QR_SUCCESS = 0;
    private final int QR_NET_ERROR = 1;//网络错误
    private final int QR_ON_COMPLETE = 2;//完成

    private final int QR_COUNT = 3;//短信验证码计数

    private final int INFO_ON_RESULT = 4;
    private final int INFO_NET_ERROR = 5;//网络错误
    private final int INFO_ON_COMPLETE = 6;//完成

    private final int REGISTER_ON_RESULT = 7;
    private final int REGISTER_NET_ERROR = 8;//网络错误
    private final int REGISTER_ON_COMPLETE = 9;//完成


    private int mCountNum = 60;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!isViewAttached())//没有view绑定直接返回
                return;
            switch (msg.what) {
                case QR_SUCCESS:
                    break;
                case QR_NET_ERROR:
                    break;
                case QR_ON_COMPLETE:
                    break;
                case QR_COUNT:
                    if (mCountNum <= 0) {
                        getView().setSendBtEnable(true);//发送验证码的按钮可用
                        getView().setSendBtText("获取验证码");
                    } else {
                        getView().setSendBtText("(" + mCountNum + "s后重试)");
                        mHandler.sendEmptyMessageDelayed(QR_COUNT, 1000);//1秒刷新一下
                    }
                    mCountNum--;
                    break;
                case INFO_ON_RESULT:
                    BaseBean<UserBean> baseBean1 = (BaseBean<UserBean>) msg.obj;//得到用户id
                    // getView().showToast(baseBean.getMsg());//弹出提示信息
                    if (baseBean1.getCode() == 1) {
                        getView().setUserInfo(baseBean1.getData());
                    }
                    break;
                case INFO_NET_ERROR:
                    //  getView().setUserInfo(baseBean1.getData());
                    break;
                case REGISTER_ON_RESULT:
                    BaseBean<Integer> baseBean2 = (BaseBean<Integer>) msg.obj;//得到用户id
                    // getView().showToast(baseBean.getMsg());//弹出提示信息
                    if (baseBean2.getCode() == 1) {
                        getView().registerSuccess(baseBean2.getData());
                    }else{
                        getView().showErrorHint(baseBean2.getMsg());
                    }
                    break;
                case REGISTER_NET_ERROR:
                    getView().showToast("网络错误");
                    break;
                case REGISTER_ON_COMPLETE:
                    getView().hideLoading();//隐藏加载进度框
                    break;
            }
        }
    };

    //TODO:记得改
    @Override
    public String getQrCode() {
        return "1234";
    }

    /**
     * 发送验证码
     */
    @Override
    public void sendQrCode() {
        mCountNum = 60;
        getView().setSendBtEnable(false);//发送按钮不可用
        mHandler.sendEmptyMessage(QR_COUNT);//计数
    }

    @Override
    public void register(String phone_num, String login_password, String pay_password, String recommend_user_phone, String vertex_user_phone) {

        if (!isViewAttached())
            return;

        getView().showLoading("注册中..");
        mRegisterModel.register(phone_num, login_password, pay_password, recommend_user_phone, vertex_user_phone, new OnGetInfoListener<BaseBean<Integer>>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(REGISTER_ON_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(REGISTER_NET_ERROR);
            }

            @Override
            public void onResult(BaseBean<Integer> info) {
                Message msg = mHandler.obtainMessage();
                msg.what = REGISTER_ON_RESULT;//结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });

    }

    @Override
    public void getUserInfo(String phone_num) {
        if (!isViewAttached())
            return;

        mModel.getUserInfoWithPhone(phone_num, new OnGetInfoListener<BaseBean<UserBean>>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(INFO_ON_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(INFO_NET_ERROR);//网络错误
            }

            @Override
            public void onResult(BaseBean<UserBean> info) {
                Message msg = mHandler.obtainMessage();
                msg.what = INFO_ON_RESULT;//结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });
    }
}
