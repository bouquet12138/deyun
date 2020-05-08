package com.example.common_lib.java_bean;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class UserBean implements Serializable {
    private int user_id;
    private String name;
    private int grade;
    private int head_img_id = 0;
    private String phone_num;
    private String login_password;
    private String pay_password;
    private String sex;
    private String birthday;
    private String province;
    private String city;
    private String district;
    private String id_card;
    private String bank_card;
    private String recommend_user_phone;
    private int child_a;
    private int child_b;
    private int child_c;
    private String register_time;
    private String has_three;

    private String old_pay_password;
    private String new_pay_password;

    private String old_login_password;
    private String new_login_password;

    private String vertex_user_phone;

    private int is_merchant;
    private ImageBean head_img;


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getHead_img_id() {
        return head_img_id;
    }

    public void setHead_img_id(int head_img_id) {
        this.head_img_id = head_img_id;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getLogin_password() {
        return login_password;
    }

    public void setLogin_password(String login_password) {
        this.login_password = login_password;
    }

    public String getPay_password() {
        return pay_password;
    }

    public void setPay_password(String pay_password) {
        this.pay_password = pay_password;
    }


    public void setHead_img_id(Integer head_img_id) {
        this.head_img_id = head_img_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getRecommend_user_phone() {
        return recommend_user_phone;
    }

    public void setRecommend_user_phone(String recommend_user_phone) {
        this.recommend_user_phone = recommend_user_phone;
    }

    public void setChild_a(int child_a) {
        this.child_a = child_a;
    }

    public void setChild_b(int child_b) {
        this.child_b = child_b;
    }

    public void setChild_c(int child_c) {
        this.child_c = child_c;
    }

    public String getHas_three() {
        return has_three;
    }

    public void setHas_three(String has_three) {
        this.has_three = has_three;
    }

    public String getOld_pay_password() {
        return old_pay_password;
    }

    public void setOld_pay_password(String old_pay_password) {
        this.old_pay_password = old_pay_password;
    }

    public String getNew_pay_password() {
        return new_pay_password;
    }

    public void setNew_pay_password(String new_pay_password) {
        this.new_pay_password = new_pay_password;
    }

    public String getOld_login_password() {
        return old_login_password;
    }

    public void setOld_login_password(String old_login_password) {
        this.old_login_password = old_login_password;
    }

    public String getNew_login_password() {
        return new_login_password;
    }

    public void setNew_login_password(String new_login_password) {
        this.new_login_password = new_login_password;
    }

    public String getVertex_user_phone() {
        return vertex_user_phone;
    }

    public void setVertex_user_phone(String vertex_user_phone) {
        this.vertex_user_phone = vertex_user_phone;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "user_id=" + user_id +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", head_img_id=" + head_img_id +
                ", phone_num='" + phone_num + '\'' +
                ", login_password='" + login_password + '\'' +
                ", pay_password='" + pay_password + '\'' +
                ", sex=" + sex +
                ", birthday='" + birthday + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", id_card='" + id_card + '\'' +
                ", bank_card='" + bank_card + '\'' +
                ", recommend_user_phone='" + recommend_user_phone + '\'' +
                ", child_a=" + child_a +
                ", child_b=" + child_b +
                ", child_c=" + child_c +
                ", register_time='" + register_time + '\'' +
                ", has_three='" + has_three + '\'' +
                ", old_pay_password='" + old_pay_password + '\'' +
                ", new_pay_password='" + new_pay_password + '\'' +
                ", old_login_password='" + old_login_password + '\'' +
                ", new_login_password='" + new_login_password + '\'' +
                ", vertex_user_phone='" + vertex_user_phone + '\'' +
                ", is_merchant=" + is_merchant +
                ", head_img=" + head_img +
                '}';
    }

    public Integer getChild_a() {
        return child_a;
    }

    public void setChild_a(Integer child_a) {
        this.child_a = child_a;
    }

    public Integer getChild_b() {
        return child_b;
    }

    public void setChild_b(Integer child_b) {
        this.child_b = child_b;
    }

    public Integer getChild_c() {
        return child_c;
    }

    public void setChild_c(Integer child_c) {
        this.child_c = child_c;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }


    /**
     * 设置用户的信息
     *
     * @param modifyUserBean
     */
    public void setInfo(@NotNull UserBean modifyUserBean) {
        this.head_img_id = modifyUserBean.getHead_img_id();
        this.name = modifyUserBean.getName();
        this.sex = modifyUserBean.getSex();
        this.birthday = modifyUserBean.getBirthday();//生日
        this.province = modifyUserBean.getProvince();
        this.city = modifyUserBean.getCity();
        this.district = modifyUserBean.getDistrict();//区
        this.id_card = modifyUserBean.id_card;
        this.bank_card = modifyUserBean.getBank_card();
        this.head_img = modifyUserBean.getHead_img();
    }

    public int getIs_merchant() {
        return is_merchant;
    }

    public void setIs_merchant(int is_merchant) {
        this.is_merchant = is_merchant;
    }

    public ImageBean getHead_img() {
        return head_img;
    }

    public void setHead_img(ImageBean head_img) {
        this.head_img = head_img;
    }
}
