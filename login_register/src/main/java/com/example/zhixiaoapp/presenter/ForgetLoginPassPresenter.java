package com.example.zhixiaoapp.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.example.baselib.base.MVPBasePresenter;
import com.example.baselib.listener.OnGetInfoListener;
import com.example.baselib.util.NetWorkUtils;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.LoginAndRegisterModel;
import com.example.common_lib.model.UserModel;
import com.example.zhixiaoapp.contract.ForgetLoginContract;

public class ForgetLoginPassPresenter extends MVPBasePresenter<ForgetLoginContract.IView>
        implements ForgetLoginContract.IPresenter {

    private LoginAndRegisterModel mModel = new LoginAndRegisterModel();//登陆和注册model
    private UserModel mUserModel = new UserModel();

    private int mCountNum;

    private final int QR_SUCCESS = 0;
    private final int QR_NET_ERROR = 1;//网络错误
    private final int QR_ON_COMPLETE = 2;//完成

    private final int QR_COUNT = 3;//短信验证码计数

    private final int INFO_ON_RESULT = 4;
    private final int INFO_NET_ERROR = 5;//网络错误
    private final int INFO_ON_COMPLETE = 6;//完成

    private final int SUBMIT_ON_RESULT = 7;
    private final int SUBMIT_NET_ERROR = 8;//网络错误
    private final int SUBMIT_ON_COMPLETE = 9;//完成

    private String mVerCode = "1234";//验证码

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())//没有视图绑定直接返回
                return;
            switch (msg.what) {
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

                    if (baseBean1.getCode() == 1) {
                        submitPassword(baseBean1.getData().getUser_id());
                    } else {
                        getView().showToast("该手机号不存在");//弹出提示信息
                        getView().hideLoading();//隐藏进度框
                    }
                    break;
                case INFO_NET_ERROR:
                    getView().hideLoading();//隐藏对话框
                    getView().showToast("网络错误");
                    break;
                case SUBMIT_ON_RESULT:
                    BaseBean<UserBean> baseBean2 = (BaseBean<UserBean>) msg.obj;//得到用户id
                    getView().showToast(baseBean2.getMsg());//弹出提示信息
                    if (baseBean2.getCode() == 1) {
                        getView().finishActivity();//销毁活动
                    }
                    break;
                case SUBMIT_NET_ERROR:
                    getView().showToast("网络错误，密码修改失败");
                    break;
                case SUBMIT_ON_COMPLETE:
                    getView().hideLoading();//隐藏进度框
                    break;
            }
        }
    };

    /**
     * 提交密码
     *
     * @param user_id
     */
    private void submitPassword(int user_id) {

        if (getView().isForgetPay()){//忘记支付密码
            mModel.modify_pay_password(user_id, getView().getLoginPassword(), new OnGetInfoListener<BaseBean>() {
                @Override
                public void onComplete() {
                    mHandler.sendEmptyMessage(SUBMIT_ON_COMPLETE);
                }

                @Override
                public void onNetError() {
                    mHandler.sendEmptyMessage(SUBMIT_NET_ERROR);//网络错误
                }

                @Override
                public void onResult(BaseBean info) {
                    Message msg = mHandler.obtainMessage();
                    msg.what = SUBMIT_ON_RESULT;//结果
                    msg.obj = info;
                    mHandler.sendMessage(msg);//发送信息
                }
            });
        }else{
            mModel.modify_login_password(user_id, getView().getLoginPassword(), new OnGetInfoListener<BaseBean>() {
                @Override
                public void onComplete() {
                    mHandler.sendEmptyMessage(SUBMIT_ON_COMPLETE);
                }

                @Override
                public void onNetError() {
                    mHandler.sendEmptyMessage(SUBMIT_NET_ERROR);//网络错误
                }

                @Override
                public void onResult(BaseBean info) {
                    Message msg = mHandler.obtainMessage();
                    msg.what = SUBMIT_ON_RESULT;//结果
                    msg.obj = info;
                    mHandler.sendMessage(msg);//发送信息
                }
            });
        }

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
    public void submit() {
        if (!isViewAttached())//没有视图绑定,直接返回
            return;
        if (!NetWorkUtils.isNetWorkAvailable()) {
            getView().showErrorHint("网络错误");
            return;
        }
        if (TextUtils.isEmpty(mVerCode)) {//如果验证码为空
            getView().showErrorHint("请先获取验证码");
            return;
        }

        if (!mVerCode.equals(getView().getVerCode())) {//如果验证码不相同
            getView().showErrorHint("请输入正确的验证码");
            return;
        }


        mUserModel.getUserInfoWithPhone(getView().getPhoneNum(), new OnGetInfoListener<BaseBean<UserBean>>() {
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
