package com.example.main_module.presenter;

import android.os.Handler;
import android.os.Message;

import com.example.baselib.base.MVPBasePresenter;
import com.example.baselib.listener.OnGetInfoListener;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.IntegralModel;
import com.example.common_lib.model.PayrollModel;
import com.example.common_lib.model.WithdrawModel;
import com.example.main_module.contract.PersonContract;

public class PersonPresenter extends MVPBasePresenter<PersonContract.IView>
        implements PersonContract.IPresenter {

    private PayrollModel mPayrollModel = new PayrollModel();
    private IntegralModel mIntegralModel = new IntegralModel();

    private final int INTEGRAL_SUCCESS = 0;//成功
    private final int INTEGRAL_NET_ERROR = 1;//网络错误
    private final int INTEGRAL_COMPLETE = 2;//完成

    private final int PAYROLL_SUCCESS = 3;//成功
    private final int PAYROLL_NET_ERROR = 4;//网络错误
    private final int PAYROLL_COMPLETE = 5;//完成

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {
                case INTEGRAL_SUCCESS:
                    BaseBean<Integer> baseBean = (BaseBean) msg.obj;
                    // getView().showToast(baseBean.getMsg());//展示提示信息
                    if (baseBean.getCode() == 1) {
                        getView().setIntegral(baseBean.getData());
                        getWithDraw();
                    } else {
                        getView().showToast(baseBean.getMsg());//提示信息
                    }
                    break;
                case INTEGRAL_NET_ERROR:
                    getView().showToast("网络错误，积分信息获取失败");//提示信息
                    break;
                case INTEGRAL_COMPLETE:
                    break;
                case PAYROLL_SUCCESS:
                    BaseBean<Integer> baseBean1 = (BaseBean) msg.obj;
                    // getView().showToast(baseBean.getMsg());//展示提示信息
                    if (baseBean1.getCode() == 1) {
                        getView().setWithdraw(baseBean1.getData());//设置提现金额
                    } else {
                        getView().showToast(baseBean1.getMsg());//提示信息
                    }
                    break;
                case PAYROLL_NET_ERROR:
                    getView().showToast("网络错误，提现信息获取失败");//提示信息
                    break;
                case PAYROLL_COMPLETE:
                    break;
            }
        }
    };

    @Override
    public void getInfo() {
        if (!isViewAttached())
            return;
        UserBean userBean = NowUserInfo.getNowUserInfo();
        if (userBean == null)
            return;

        mIntegralModel.totalIntegral(userBean.getUser_id(), new OnGetInfoListener<BaseBean<Integer>>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(INTEGRAL_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(INTEGRAL_NET_ERROR);
            }

            @Override
            public void onResult(BaseBean<Integer> info) {
                Message msg = mHandler.obtainMessage();
                msg.what = INTEGRAL_SUCCESS;
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });
    }

    /**
     * 得到提现信息
     */
    private void getWithDraw() {
        if (!isViewAttached())
            return;
        UserBean userBean = NowUserInfo.getNowUserInfo();
        if (userBean == null)
            return;

        mPayrollModel.total_payroll(userBean.getUser_id(), new OnGetInfoListener<BaseBean<Integer>>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(PAYROLL_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(PAYROLL_NET_ERROR);
            }

            @Override
            public void onResult(BaseBean<Integer> info) {
                Message msg = mHandler.obtainMessage();
                msg.what = PAYROLL_SUCCESS;
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });
    }


}
