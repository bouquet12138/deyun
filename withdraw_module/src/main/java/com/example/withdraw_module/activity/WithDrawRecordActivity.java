package com.example.withdraw_module.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.baselib.util.CollectionsUtil;
import com.example.baselib.util.DensityUtil;
import com.example.baselib.view.SpacesItemDecoration;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.java_bean.WithdrawBean;
import com.example.withdraw_module.R;
import com.example.withdraw_module.adapter.WithdrawRecordAdapter;
import com.example.withdraw_module.contract.WithdrawRecordContract;
import com.example.withdraw_module.presenter.WithdrawRecordPresenter;


import java.util.List;

@Route(path = ARouterContract.WITHDRAW_WITHDRAW_RECORD)
public class WithDrawRecordActivity extends AppMvpBaseActivity implements WithdrawRecordContract.IView {

    private final int WITHDRAW = 0;//提现
    private RecyclerView mRecyclerView;
    private WithdrawRecordAdapter mAdapter;

    @Autowired
    public UserBean mUserBean;

    private WithdrawRecordPresenter mPresenter = new WithdrawRecordPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
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
        //用户信息
        mUserBean = (UserBean) getIntent().getSerializableExtra("userBean");
        if (mUserBean == null)
            mUserBean = NowUserInfo.getNowUserInfo();//用户信息

        if (mUserBean == null)
            return;

        if (mUserBean.getUser_id() == NowUserInfo.getNowUserInfo().getUser_id())
            setSubmitEnable(true);//右边可以用
        else {
            setSubmitEnable(false);//右边不可以用
            mRightText.setText("");//没有文字
            mTitleText.setText(mUserBean.getName() + "的提现记录");
        }

        mPresenter.getWithdrawInfo(mUserBean.getUser_id());//得到提现记录
    }

    @Override
    protected String getTitleName() {
        return "提现记录";
    }

    @Override
    protected String getRightTextName() {
        return "提现";
    }

    @Override
    protected int getFloatBtImgRes() {
        return R.drawable.add;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.withdraw_layout_with_draw_record;
    }

    @Override
    protected void onFloatBtClick() {
        Intent intent = new Intent(WithDrawRecordActivity.this, WithdrawActivity.class);
        startActivityForResult(intent, WITHDRAW);
    }

    /**
     * 展示提现记录
     *
     * @param withdrawRecords 提现记录
     */
    @Override
    public void setWithdrawRecord(List<WithdrawBean> withdrawRecords) {
        if (CollectionsUtil.isEmpty(withdrawRecords)) {
            showNoMoreData();//展示没有更多数据
        } else {
            showNormalView();//展示正常view
            if (mAdapter == null) {
                mAdapter = new WithdrawRecordAdapter(withdrawRecords);
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
     * 刷新
     */
    @Override
    protected void onRefresh() {
        super.onRefresh();
        if (mUserBean != null) {
            showNormalView();//展示正常view
            mPresenter.getWithdrawInfo(mUserBean.getUser_id());//得到提现记录
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case WITHDRAW:
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
}
