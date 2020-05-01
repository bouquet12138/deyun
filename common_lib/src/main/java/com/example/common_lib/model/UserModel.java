package com.example.common_lib.model;

import com.example.baselib.listener.OnGetInfoListener;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.UserBean;

import static com.example.common_lib.model.ModelUtil.postJsonBean;

public class UserModel {

    /**
     * 修改学生信息
     *
     * @param userBean
     * @param listener
     */
    public void modifyUserInfo(UserBean userBean, OnGetInfoListener<BaseBean> listener) {
        postJsonBean(userBean, "modify_user_info", listener);
    }


}
