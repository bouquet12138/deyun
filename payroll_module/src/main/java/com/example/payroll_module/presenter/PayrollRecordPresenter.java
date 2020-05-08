package com.example.payroll_module.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.baselib.base.MVPBasePresenter;
import com.example.baselib.listener.OnGetInfoListener;
import com.example.baselib.util.NetWorkUtils;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.PayrollBean;
import com.example.common_lib.model.PayrollModel;
import com.example.payroll_module.contract.PayrollRecordContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PayrollRecordPresenter extends MVPBasePresenter<PayrollRecordContract.IView>
        implements PayrollRecordContract.IPresenter {

    private PayrollModel mModel = new PayrollModel();

    private static final String TAG = "PayrollRecordPresenter";

    private List<PayrollBean> mPromoteIncomeBeans = new ArrayList<>();
    private List<PayrollBean> mTransfersBetweenBeans = new ArrayList<>();

    private final int SUCCESS = 0;//成功
    private final int NET_ERROR = 1;//网络错误
    private final int COMPLETE = 2;//完成

    private boolean mSuccess = false;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {
                case SUCCESS:
                    BaseBean<List<PayrollBean>> baseBean = (BaseBean) msg.obj;
                    if (baseBean.getCode() == 1) {
                        mPromoteIncomeBeans.clear();//清空一下
                        mTransfersBetweenBeans.clear();
                        if (baseBean.getData() != null)
                            for (PayrollBean payrollBean : baseBean.getData()) {
                                if (payrollBean.getType().equals(PayrollBean.TRANSFERS_BETWEEN))//积分类型
                                    mTransfersBetweenBeans.add(payrollBean);//添加一下
                                else if (payrollBean.getType().equals(PayrollBean.PROMOTE_INCOME))
                                    mPromoteIncomeBeans.add(payrollBean);//添加一下
                            }
                        Collections.reverse(mPromoteIncomeBeans);//反转一下数据
                        Collections.reverse(mTransfersBetweenBeans);//反转一下数据
                        mSuccess = true;//信息获取成功
                        getView().setPromoteIncomeRecord(mPromoteIncomeBeans);//设置推广记录
                        getView().setTransferBetweenRecord(mTransfersBetweenBeans);//设置互转记录
                    } else {
                        getView().showErrorHint(baseBean.getMsg());
                        if (!mSuccess)
                            getView().showNetError();//展示网络错误
                    }
                    break;
                case NET_ERROR:
                    if (!mSuccess)
                        getView().showNetError();//网络错误
                    else getView().showToast("网络错误");
                    break;
                case COMPLETE:
                    getView().hideLoading();//隐藏进度框
                    break;
            }
        }
    };


    @Override
    public void getPayrollRecord(int user_id) {
        if (!isViewAttached())
            return;
        if (!NetWorkUtils.isNetWorkAvailable()) {
            getView().showErrorHint("网络错误");
            return;
        }

        Log.d(TAG, "getMyTeamInfo: " + user_id);

        getView().showLoading("信息加载中..");
        mModel.total_payroll_record(user_id, new OnGetInfoListener<BaseBean<List<PayrollBean>>>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(NET_ERROR);
            }

            @Override
            public void onResult(BaseBean info) {
                Message msg = mHandler.obtainMessage();
                msg.what = SUCCESS;
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });
    }

}
