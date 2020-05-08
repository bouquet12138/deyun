package com.example.zhixiaoapp.contract;

import com.example.common_lib.base.IAppMvpView;
import com.example.common_lib.java_bean.ImageBean;
import com.example.common_lib.java_bean.UserBean;

public interface InformationContract {
    interface IView extends IAppMvpView {

        UserBean getModifyUserBean();//得到修改的用户信息

        String getImageLocalPath();//得到图片本地链接

        boolean isModifyHeadImage();//是否修改了图片

        void setImageBean(ImageBean imageBean);//设置头像数据

        /**
         * 得到用户id
         *
         * @return
         */
        int getUserId();
    }

    interface IPresenter {
        void submit();//提交
    }
}
