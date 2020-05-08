package com.example.integral_module.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.baselib.util.DensityUtil;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.IntegralBean;
import com.example.integral_module.R;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

import java.util.List;

public class IntegralTransferRecordAdapter extends RecyclerView.Adapter<IntegralTransferRecordAdapter.ViewHolder> {

    private List<IntegralBean> mIntegralBeans;
    private String mIntegralType;//积分类型

    public IntegralTransferRecordAdapter(List<IntegralBean> integralBeans, String integralType) {
        mIntegralBeans = integralBeans;
        mIntegralType = integralType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.integral_item_transfer_record, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mRoot.setRadiusAndShadow(DensityUtil.dipToPx(5), DensityUtil.dipToPx(5), 1f);
        IntegralBean bean = mIntegralBeans.get(position);//积分bean
        spaceNum(holder.mIdText, "编号:", bean.getIntegral_id() + "");
        spaceNum(holder.mMoneyText, "交易金额:", bean.getTransaction_amount() + "");
        spaceNum(holder.mTimeText, "交易时间:", bean.getTransaction_time());


     /*   if (mIntegralType.equals(IntegralBean.TRANSFERS_BETWEEN)) {
            holder.mUserIdText.setVisibility(View.VISIBLE);
            holder.mTargetUserIdText.setVisibility(View.VISIBLE);

            if (NowUserInfo.getNowUserId() == bean.getUser_id())
                spaceNum(holder.mUserIdText, "转出方:", "我");
            else
                spaceNum(holder.mUserIdText, "转出方编号:", bean.getUser_id() + "");

            if (NowUserInfo.getNowUserId() == bean.getTarget_user_id())
                spaceNum(holder.mTargetUserIdText, "转入方:", "我");
            else
                spaceNum(holder.mTargetUserIdText, "转入方编号:", bean.getTarget_user_id() + "");
        } else {*/
            holder.mUserIdText.setVisibility(View.GONE);
            holder.mTargetUserIdText.setVisibility(View.GONE);//不可见
       // }

        spaceNum(holder.mRemainAmount, "剩余积分:", bean.getRemain_record() + "");
        spaceNum(holder.mRemarkText, "备注:", bean.getTransaction_remark() + "");

    }

    /**
     * 设置文字
     *
     * @param textView
     * @param key
     * @param value
     */
    private void spaceNum(TextView textView, String key, String value) {
        String str = key;
        for (int i = key.length(); i <= 6; i++)
            str += "\u3000";
        str += value;
        textView.setText(str);
    }

    /**
     * 得到item的数量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mIntegralBeans == null ? 0 : mIntegralBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        QMUILinearLayout mRoot;
        TextView mIdText;
        TextView mMoneyText;
        TextView mTimeText;
        TextView mUserIdText;
        TextView mTargetUserIdText;
        TextView mRemainAmount;
        TextView mRemarkText;

        public ViewHolder(View itemView) {
            super(itemView);
            mRoot = itemView.findViewById(R.id.root);
            mIdText = itemView.findViewById(R.id.idText);
            mMoneyText = itemView.findViewById(R.id.moneyText);
            mTimeText = itemView.findViewById(R.id.timeText);
            mUserIdText = itemView.findViewById(R.id.userIdText);
            mTargetUserIdText = itemView.findViewById(R.id.targetUserIdText);
            mRemainAmount = itemView.findViewById(R.id.remainAmount);
            mRemarkText = itemView.findViewById(R.id.remarkText);
        }
    }
}