package com.example.payroll_module.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.baselib.adapter.MyFragmentAdapter;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.PayrollBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.payroll_module.R;
import com.example.payroll_module.contract.PayrollRecordContract;
import com.example.payroll_module.fragment.PayrollRecordFragment;
import com.example.payroll_module.presenter.PayrollRecordPresenter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterContract.PAYROLL_RECORD)
public class PayrollRecordActivity extends AppMvpBaseActivity implements PayrollRecordContract.IView {


    private TabLayout mTableLayout;
    private ViewPager mViewPager;

    @Autowired(name = "type")
    public String mType;//工资类型

    private final int TRANSFER = 0;//提现

    private PayrollRecordPresenter mPresenter = new PayrollRecordPresenter();
    private UserBean mUserBean;
    private List<Fragment> mFragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        setSubmitEnable(true);//按钮可以用
        initView();
        mPresenter.attachView(this);
        initFragment();//初始化碎片
        initData();
    }


    /**
     * 初始化view
     */
    private void initView() {
        mTableLayout = mNormalView.findViewById(R.id.tableLayout);
        mViewPager = mNormalView.findViewById(R.id.viewPager);
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

        mPresenter.getPayrollRecord(mUserBean.getUser_id());//得到所有的积分记录

    }

    /**
     * 初始化碎片
     */
    private void initFragment() {

        mFragments = new ArrayList<>();
        mFragments.add(new PayrollRecordFragment());
        mFragments.add(new PayrollRecordFragment());
        List<String> titles = new ArrayList<>();
        titles.add("推广收益");
        titles.add("工资互转");

        MyFragmentAdapter myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(myFragmentAdapter);//设置适配器
        mTableLayout.setupWithViewPager(mViewPager, true);//设置上面的标题

        if (PayrollBean.TRANSFERS_BETWEEN.equals(mType))
            mViewPager.setCurrentItem(1);//选中第二个

    }


    @Override
    protected String getTitleName() {
        return "工资记录";
    }

    @Override
    protected String getRightTextName() {
        return "提现";
    }

    @Override
    protected int getFloatBtImgRes() {
        return 0;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.payroll_layout_record;
    }

    @Override
    protected void onFloatBtClick() {
        startActivity(new Intent(this, PayrollTransfersActivity.class));
    }

    /**
     * 推广收入
     *
     * @param payrollBeans 工资记录
     */
    @Override
    public void setPromoteIncomeRecord(List<PayrollBean> payrollBeans) {
        PayrollRecordFragment payrollRecordFragment = (PayrollRecordFragment) mFragments.get(0);
        if (payrollRecordFragment != null)
            payrollRecordFragment.setPayrollRecord(payrollBeans, PayrollBean.PROMOTE_INCOME);
    }

    @Override
    public void setTransferBetweenRecord(List<PayrollBean> payrollBeans) {
        PayrollRecordFragment payrollRecordFragment = (PayrollRecordFragment) mFragments.get(1);
        if (payrollRecordFragment != null)
            payrollRecordFragment.setPayrollRecord(payrollBeans, PayrollBean.TRANSFERS_BETWEEN);
    }

    @Override
    public String getPayrollType() {
        return mType;
    }
}
