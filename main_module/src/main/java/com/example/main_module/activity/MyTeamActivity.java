package com.example.main_module.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.baselib.util.CollectionsUtil;
import com.example.baselib.util.DensityUtil;
import com.example.baselib.view.SpacesItemDecoration;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.UserBean;
import com.example.main_module.R;
import com.example.main_module.adapter.MemberAdapter;
import com.example.main_module.contract.MyTeamContract;
import com.example.main_module.presenter.MyTeamPresenter;

import java.util.ArrayList;
import java.util.List;

public class MyTeamActivity extends AppMvpBaseActivity implements MyTeamContract.IView {

    private RecyclerView mRecyclerView;
    private MyTeamPresenter mPresenter = new MyTeamPresenter();
    private MemberAdapter mMemberAdapter;
    private UserBean mUserBean;

    private List<UserBean> mUserBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mPresenter.attachView(this);//绑定一下
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        initData();
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
            setSubmitEnable(false);//右边可以用
            mRightText.setText("");//没有文字
            mTitleText.setText(mUserBean.getName() + "的团队");
        }

        mPresenter.getMyTeamInfo(mUserBean.getUser_id());//获取我的团队信息
    }

    /**
     * 初始化view
     */
    private void initView() {
        mRecyclerView = mNormalView.findViewById(R.id.recyclerView);//列表布局
    }

    @Override
    protected String getTitleName() {
        return "我的团队";
    }

    @Override
    protected String getRightTextName() {
        return "添加成员";
    }

    @Override
    protected int getFloatBtImgRes() {
        return R.drawable.add;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.main_layout_my_team;
    }

    @Override
    protected void onFloatBtClick() {
        ARouter.getInstance().build(ARouterContract.LOGIN_REGISTER) //跳转到注册新用户页面
                .navigation();
    }

    private static final String TAG = "MyTeamActivity";

    @Override
    public void setTeamInfo(List<UserBean> userBeans) {

        mUserBeans.clear();//清空一下

        Log.d(TAG, "setTeamInfo: " + userBeans);

        if (mUserBean.getUser_id() == NowUserInfo.getNowUserId())
            mUserBean = NowUserInfo.getNowUserInfo();//更新一下

        if (userBeans != null)//不是空的话
            for (UserBean userBean : userBeans) {
                if (userBean.getUser_id() == mUserBean.getChild_a() || userBean.getUser_id() == mUserBean.getChild_b()
                        || userBean.getUser_id() == mUserBean.getChild_c())//只要有一个相同
                    mUserBeans.add(userBean);//讲这个用户添加进来

            }

        if (CollectionsUtil.isEmpty(mUserBeans)) {
            showNoMoreData();//展示没有更多数据
        } else {
            showNormalView();//展示正常view
            if (mMemberAdapter == null) {
                mMemberAdapter = new MemberAdapter(this, mUserBeans);
                mRecyclerView.setAdapter(mMemberAdapter);//设置适配器
                mRecyclerView.setLayoutManager(new LinearLayoutManager(MyTeamActivity.this));
                mRecyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtil.dipToPx(10)));
                mMemberAdapter.setOnItemClickListener(userBean -> MyTeamActivity.actionStart(this, userBean));
            } else {
                mMemberAdapter.notifyDataSetChanged();//唤醒数据更新
            }
        }
    }


    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }

    /**
     * 启动活动
     */
    public static void actionStart(Context context, UserBean userBean) {
        Intent intent = new Intent(context, MyTeamActivity.class);
        intent.putExtra("userBean", userBean);
        context.startActivity(intent);//启动Activity
    }

    /**
     * 刷新
     */
    @Override
    protected void onRefresh() {
        super.onRefresh();
        if (mUserBean != null) {
            showNormalView();//展示正常view
            mPresenter.getMyTeamInfo(mUserBean.getUser_id());//获取我的团队信息
        }
    }
}
