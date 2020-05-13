package com.example.main_module.activity;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.baselib.adapter.MyFragmentAdapter;
import com.example.baselib.base.MVPBaseActivity;
import com.example.baselib.util.NetType;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.info.ServerInfo;
import com.example.common_view.bean.BottomBean;
import com.example.common_view.bottom_view.BottomNavigationView;
import com.example.main_module.R;
import com.example.main_module.custom.CustomUpdateParser;
import com.example.main_module.custom.CustomUpdatePrompter;
import com.example.main_module.fragment.HomeFragment;
import com.example.main_module.fragment.PersonFragment;
import com.example.main_module.utils.CProgressDialogUtils;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.proxy.impl.DefaultUpdateChecker;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterContract.MAIN_MAIN)
public class MainActivity extends MVPBaseActivity {
    private static final String TAG = "StuMainActivity";

    private ViewPager mViewPager;// viewPager
    private BottomNavigationView mBottomNavigation;//底部view

    private List<BottomBean> mBottomBeans;//底部碎片

    private List<Fragment> mFragments;//碎片集合

    private long mLastPressTime;//最后一次按下的时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_main);
        initView();
        initAdapter();
        initListener();
        checkUpdate();
    }

    private void initView() {
        mViewPager = findViewById(R.id.viewPager);
        mBottomNavigation = findViewById(R.id.bottomNavigation);
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mBottomBeans = new ArrayList<>();
        mBottomBeans.add(new BottomBean(R.drawable.main_home_press, R.drawable.main_home,
                "主页"));
        mBottomBeans.add(new BottomBean(R.drawable.main_person_press, R.drawable.main_person,
                "个人中心"));

        mBottomNavigation.setBottomBeans(mBottomBeans);//设置下面的属性

        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());//主页
        mFragments.add(new PersonFragment());//学生信息

        MyFragmentAdapter myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(myFragmentAdapter);//设置适配器
    }

    /**
     * 检测更新
     */
    private void checkUpdate() {

        String appUrl = ServerInfo.getServerAddress("get_app_info");//app信息

        XUpdate.newBuild(this)
                .updateUrl(appUrl)
                .updateChecker(new DefaultUpdateChecker() {
                    @Override
                    public void onBeforeCheck() {
                        super.onBeforeCheck();
                        CProgressDialogUtils.showProgressDialog(MainActivity.this, "查询中...");
                    }

                    @Override
                    public void onAfterCheck() {
                        super.onAfterCheck();
                        CProgressDialogUtils.cancelProgressDialog(MainActivity.this);
                    }
                })
                .updateParser(new CustomUpdateParser())
                .updatePrompter(new CustomUpdatePrompter(MainActivity.this))
                .update();

    }

    /**
     * 初始化监听
     */
    private void initListener() {

        mBottomNavigation.setOnItemClickListener((p) ->
        {
            if (p == mViewPager.getCurrentItem()) {
            } else {
                mViewPager.setCurrentItem(p);
            }
        });//设置当前选择

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mBottomNavigation.move(position, positionOffset);//移动
            }

            @Override
            public void onPageSelected(int position) {
                mBottomNavigation.setCurrentSelected(position);//设置当前选中
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onConnected(NetType type) {
        super.onConnected(type);
        Log.d(TAG, "onConnected: ");
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mLastPressTime < 3000) {
            finish();
        } else {
            mLastPressTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        }
    }
}

