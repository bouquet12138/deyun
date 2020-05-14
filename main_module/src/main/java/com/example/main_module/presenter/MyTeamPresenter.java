package com.example.main_module.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.baselib.base.MVPBasePresenter;
import com.example.baselib.listener.OnGetInfoListener;
import com.example.baselib.util.NetWorkUtils;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.UserModel;
import com.example.main_module.contract.MyTeamContract;

import java.util.List;

public class MyTeamPresenter extends MVPBasePresenter<MyTeamContract.IView>
        implements MyTeamContract.IPresenter {

    private UserModel mModel = new UserModel();

    private final int SUCCESS = 0;//成功
    private final int NET_ERROR = 1;//网络错误
    private final int COMPLETE = 2;//完成

    private final int INFO_ON_RESULT = 3;
    private final int INFO_NET_ERROR = 4;//网络错误
    private final int INFO_ON_COMPLETE = 5;//完成

    private boolean mSuccess = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {
                case SUCCESS:
                    BaseBean<List<UserBean>> baseBean = (BaseBean) msg.obj;
                    if (baseBean.getCode() == 1) {
                        mSuccess = true;//信息获取成功
                        getView().setTeamInfo(baseBean.getData());//设置团队信息
                    } else {
                        getView().showToast(baseBean.getMsg());//展示提示信息
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
                case INFO_ON_RESULT:
                    BaseBean<UserBean> baseBean1 = (BaseBean<UserBean>) msg.obj;//得到用户id
                    // getView().showToast(baseBean.getMsg());//弹出提示信息
                    if (baseBean1.getCode() == 1) {
                        if (baseBean1.getData().getUser_id() == NowUserInfo.getNowUserId())
                            NowUserInfo.setNowUserInfo(baseBean1.getData());//设置当前用户信息
                        Log.d(TAG, "handleMessage: " + baseBean1.getData());
                        getMyTeamInfoNext(baseBean1.getData().getUser_id());
                    } else {
                        getView().hideLoading();//隐藏加载进度框
                    }
                    break;
                case INFO_NET_ERROR:
                    getView().hideLoading();//隐藏加载进度框
                    getView().showToast("网络错误");
                    break;
                case INFO_ON_COMPLETE:
                    break;
            }
        }
    };

    private static final String TAG = "MyTeamPresenter";

    @Override
    public void getMyTeamInfo(int userId) {
        if (!isViewAttached())
            return;
        if (!NetWorkUtils.isNetWorkAvailable()) {
            getView().showErrorHint("网络错误");
            getView().showNetError();//展示网络错误
            return;
        }

        mModel.getUserInfo(userId, new OnGetInfoListener<BaseBean<UserBean>>() {
            @Override
            public void onResult(BaseBean<UserBean> info) {
                Message msg = mHandler.obtainMessage();
                msg.what = INFO_ON_RESULT;//结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(INFO_NET_ERROR);//网络错误
            }

            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(INFO_ON_COMPLETE);//完成
            }
        });

    }

    private void getMyTeamInfoNext(int userId) {
        if (!isViewAttached())
            return;


        Log.d(TAG, "getMyTeamInfo: " + userId);

        getView().showLoading("信息加载中..");
        mModel.myTeam(userId, new OnGetInfoListener<BaseBean<List<UserBean>>>() {
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
