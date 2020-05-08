package com.example.integral_module.activity;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.baselib.util.CollectionsUtil;
import com.example.baselib.util.DensityUtil;
import com.example.baselib.view.SpacesItemDecoration;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.IntegralBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.integral_module.R;
import com.example.integral_module.adapter.IntegralTransferRecordAdapter;
import com.example.integral_module.contract.IntegralRecordContract;
import com.example.integral_module.presenter.IntegralRecordPresenter;

import java.util.List;

@Route(path = ARouterContract.INTEGRAL_TRANSFERS_RECORD)
public class IntegralRecordActivity extends AppMvpBaseActivity implements IntegralRecordContract.IView {


    @Autowired(name = "integralType")
    public String mIntegralType;//积分类型

    private final int TRANSFER = 0;//提现
    private RecyclerView mRecyclerView;
    private IntegralTransferRecordAdapter mAdapter;

    @Autowired
    public UserBean mUserBean;

    private IntegralRecordPresenter mPresenter = new IntegralRecordPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setSubmitEnable(false);//提交按钮不可用
        mPresenter.attachView(this);//绑定一下
        initData();//初始化数据
    }

    /**
     * 初始化view
     */
    private void initView() {
        mRecyclerView = mNormalView.findViewById(R.id.recyclerView);//列表布局
    }

    /**
     * 初始化数据
     */
    private void initData() {
        ARouter.getInstance().inject(this);//绑定数据
        //用户信息
        mUserBean = (UserBean) getIntent().getSerializableExtra("userBean");
        if (mUserBean == null)
            mUserBean = NowUserInfo.getNowUserInfo();//用户信息

        if (mUserBean == null)
            return;
        /*
        if (mUserBean.getUser_id() == NowUserInfo.getNowUserInfo().getUser_id())
            setSubmitEnable(true);//右边可以用
        else {
            setSubmitEnable(false);//右边不可以用
            mRightText.setText("");//没有文字
            mTitleText.setText(mUserBean.getName() + "的提现记录");
        }*/

        mPresenter.getIntegralTransfersRecord(mUserBean.getUser_id());//得到所有的积分记录

        if (mIntegralType.equals(IntegralBean.BUY_SET_MEAL)) {//购买套餐
            mTitleText.setText("推广记录");

        } else if (mIntegralType.equals(IntegralBean.RECHARGE)) {//购买套餐
            mTitleText.setText("充值记录");
        }

    }

    @Override
    protected String getTitleName() {
        return "充值记录";
    }

    @Override
    protected String getRightTextName() {
        return "";
    }

    @Override
    protected int getFloatBtImgRes() {
        return R.drawable.add;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.integral_layout_transfer_record;
    }

    @Override
    protected void onFloatBtClick() {
        Intent intent = new Intent(this, IntegralTransfersActivity.class);
        startActivityForResult(intent, TRANSFER);
    }


    /**
     * 刷新
     */
    @Override
    protected void onRefresh() {
        super.onRefresh();
        if (mUserBean != null) {
            showNormalView();//展示正常view
            mPresenter.getIntegralTransfersRecord(mUserBean.getUser_id());//得到提现记录
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TRANSFER:
                if (resultCode == RESULT_OK) {//刷新一下数据
                    onRefresh();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }

    /**
     * 设置互转记录
     *
     * @param integralBeans 积分记录
     */
    @Override
    public void setIntegralTransfersRecord(List<IntegralBean> integralBeans) {
        if (CollectionsUtil.isEmpty(integralBeans)) {
            showNoMoreData();//展示没有更多数据
        } else {
            showNormalView();//展示正常view
            if (mAdapter == null) {
                mAdapter = new IntegralTransferRecordAdapter(integralBeans, mIntegralType);
                mRecyclerView.setAdapter(mAdapter);//设置适配器
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                mRecyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtil.dipToPx(10)));
                // mAdapter.setOnItemClickListener(userBean -> MyTeamActivity.actionStart(this, userBean));
            } else {
                mAdapter.notifyDataSetChanged();//唤醒数据更新
            }
        }
    }

    /**
     * 积分类型
     *
     * @return
     */
    @Override
    public String getIntegralType() {
        return mIntegralType;
    }
}
