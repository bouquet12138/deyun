package com.example.withdraw_module.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.baselib.base.MVPBasePresenter;
import com.example.baselib.listener.OnGetInfoListener;
import com.example.baselib.util.NetWorkUtils;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.WithdrawBean;
import com.example.common_lib.model.WithdrawModel;
import com.example.withdraw_module.contract.WithdrawRecordContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WithdrawRecordPresenter extends MVPBasePresenter<WithdrawRecordContract.IView>
        implements WithdrawRecordContract.IPresenter {

    private WithdrawModel mModel = new WithdrawModel();

    private final int SUCCESS = 0;//成功
    private final int NET_ERROR = 1;//网络错误
    private final int COMPLETE = 2;//完成

    private boolean mSuccess = false;

    private List<WithdrawBean> mWithdrawBeanRecords = new ArrayList<>();


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {
                case SUCCESS:
                    BaseBean<List<WithdrawBean>> baseBean = (BaseBean) msg.obj;
                    if (baseBean.getCode() == 1) {
                        mSuccess = true;//信息获取成功
                        mWithdrawBeanRecords.clear();
                        if (baseBean.getData() != null)
                            mWithdrawBeanRecords.addAll(baseBean.getData());
                        Collections.reverse(mWithdrawBeanRecords);
                        getView().setWithdrawRecord(mWithdrawBeanRecords);//设置提现信息
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

    private static final String TAG = "WithdrawRecordPresenter";

    /**
     * 得到提现记录
     *
     * @param user_id
     */
    @Override
    public void getWithdrawInfo(int user_id) {

        if (!isViewAttached())
            return;
        if (!NetWorkUtils.isNetWorkAvailable()) {
            getView().showErrorHint("网络错误");
            return;
        }

        Log.d(TAG, "getMyTeamInfo: " + user_id);

        getView().showLoading("信息加载中..");
        mModel.withdrawRecord(user_id, new OnGetInfoListener<BaseBean<List<WithdrawBean>>>() {
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
