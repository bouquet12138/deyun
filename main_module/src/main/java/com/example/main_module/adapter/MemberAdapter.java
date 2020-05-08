package com.example.main_module.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.baselib.util.DensityUtil;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.util.HeadImageUtil;
import com.example.main_module.R;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private Context mContext;
    private List<UserBean> mUserBeans;

    public MemberAdapter(Context context, List<UserBean> userBeans) {
        mContext = context;
        mUserBeans = userBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_member, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserBean bean = mUserBeans.get(position);//得到用户

        holder.mRoot.setRadiusAndShadow(DensityUtil.dipToPx(5), DensityUtil.dipToPx(5), 1f);

        holder.mRoot.setOnClickListener(view -> {
            if (mOnItemClickListener != null)
                mOnItemClickListener.onClick(bean);//将用户传递过去
        });

        if (TextUtils.isEmpty(bean.getName()))
            holder.mNameText.setText("姓名：\u3000\u3000" + "");
        else
            holder.mNameText.setText("姓名：\u3000\u3000" + bean.getName());

        if (TextUtils.isEmpty(bean.getPhone_num()))
            holder.mPhoneText.setText("手机号：\u3000" + "");
        else
            holder.mPhoneText.setText("手机号：\u3000" + bean.getPhone_num());

        HeadImageUtil.setUserHead(bean, mContext, holder.mHeadImg);//设置头像
    }

    @Override
    public int getItemCount() {
        return mUserBeans == null ? 0 : mUserBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        QMUILinearLayout mRoot;
        ImageView mHeadImg;
        TextView mNameText;
        TextView mPhoneText;

        public ViewHolder(View itemView) {
            super(itemView);
            mRoot = itemView.findViewById(R.id.root);
            mHeadImg = itemView.findViewById(R.id.headImg);
            mNameText = itemView.findViewById(R.id.nameText);
            mPhoneText = itemView.findViewById(R.id.phoneText);
        }
    }

    public interface OnItemClickListener {
        void onClick(UserBean userBean);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}