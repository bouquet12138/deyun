package com.example.payroll_module.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baselib.util.CollectionsUtil;
import com.example.baselib.util.DensityUtil;
import com.example.baselib.view.SpacesItemDecoration;
import com.example.common_lib.java_bean.PayrollBean;
import com.example.payroll_module.R;
import com.example.payroll_module.adapter.PayrollRecordAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayrollRecordFragment extends Fragment {

    protected View mView;


    private RecyclerView mRecyclerView;
    private PayrollRecordAdapter mAdapter;
    private ViewGroup mNoMoreData;//没有更多数据

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.payroll_fragment_record, container, false);
            initView();
        }
        return mView;
    }


    /**
     * 初始化view
     */
    private void initView() {
        mRecyclerView = mView.findViewById(R.id.recyclerView);
        mNoMoreData = mView.findViewById(R.id.noMoreData);//没有更多数据
        mNoMoreData.setVisibility(View.GONE);//不可见
    }

    /**
     * 设置工资记录
     */
    public void setPayrollRecord(List<PayrollBean> payrollBeans, String type) {

        if (CollectionsUtil.isEmpty(payrollBeans)) {
            mNoMoreData.setVisibility(View.VISIBLE);//可见
        } else {
            mNoMoreData.setVisibility(View.GONE);//不可见
        }

        if (mAdapter == null) {
            mAdapter = new PayrollRecordAdapter(payrollBeans, type);
            mRecyclerView.setAdapter(mAdapter);//设置适配器
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtil.dipToPx(10)));
        } else {
            mAdapter.notifyDataSetChanged();//唤醒数据更新
        }

    }

}
