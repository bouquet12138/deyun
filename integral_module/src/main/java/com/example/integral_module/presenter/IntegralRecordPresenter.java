package com.example.integral_module.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.baselib.base.MVPBasePresenter;
import com.example.baselib.listener.OnGetInfoListener;
import com.example.baselib.util.NetWorkUtils;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.IntegralBean;
import com.example.common_lib.model.IntegralModel;
import com.example.integral_module.contract.IntegralRecordContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntegralRecordPresenter extends MVPBasePresenter<IntegralRecordContract.IView>
        implements IntegralRecordContract.IPresenter {

    private List<IntegralBean> mIntegralBeans = new ArrayList<>();
    private IntegralModel mModel = new IntegralModel();//积分model

    private final int SUCCESS = 0;//成功
    private final int NET_ERROR = 1;//网络错误
    private final int COMPLETE = 2;//完成

    private boolean mSuccess = false;

    private static final String TAG = "IntegralTransfers";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {
                case SUCCESS:
                    BaseBean<List<IntegralBean>> baseBean = (BaseBean) msg.obj;
                    if (baseBean.getCode() == 1) {
                        mIntegralBeans.clear();
                        for (IntegralBean integralBean : baseBean.getData()) {
                            Log.d(TAG, "handleMessage: " + getView().getIntegralType());
                            if (integralBean.getIntegral_type().equals(getView().getIntegralType()))//积分类型
                                mIntegralBeans.add(integralBean);//添加进去
                        }
                        Collections.reverse(mIntegralBeans);//反转一下数据
                        mSuccess = true;//信息获取成功
                        getView().setIntegralTransfersRecord(mIntegralBeans);//设置积分互转信息
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
    public void getIntegralTransfersRecord(int user_id) {


        if (!isViewAttached())
            return;
        if (!NetWorkUtils.isNetWorkAvailable()) {
            getView().showErrorHint("网络错误");
            return;
        }

        Log.d(TAG, "getMyTeamInfo: " + user_id);

        getView().showLoading("信息加载中..");
        mModel.total_integral_record(user_id, new OnGetInfoListener<BaseBean<List<IntegralBean>>>() {
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
