package com.example.common_lib.java_bean;

public class WithdrawBean {
    private Integer withdraw_id;
    private Integer withdraw_amount;
    private String withdraw_time;
    private Integer user_id;
    private Integer integral_id;
    private String withdraw_remark;
    private String bank_card;
    private String alipay;
    private String wechat;
    private Integer remain_record;

    private String pay_password;//支付密码

    private int is_process;//是否处理
    private String handle_time;//处理时间

    public WithdrawBean(Integer user_id, Integer withdraw_amount, String pay_password,
                        String withdraw_remark, String bank_card,
                        String alipay, String wechat) {
        this.withdraw_amount = withdraw_amount;
        this.user_id = user_id;
        this.withdraw_remark = withdraw_remark;
        this.bank_card = bank_card;
        this.alipay = alipay;
        this.wechat = wechat;
        this.pay_password = pay_password;
    }

    @Override
    public String toString() {
        return "WithdrawBean{" +
                "withdraw_id=" + withdraw_id +
                ", withdraw_amount=" + withdraw_amount +
                ", withdraw_time=" + withdraw_time +
                ", user_id=" + user_id +
                ", integral_id=" + integral_id +
                ", withdraw_remark='" + withdraw_remark + '\'' +
                ", bank_card='" + bank_card + '\'' +
                ", alipay='" + alipay + '\'' +
                ", wechat='" + wechat + '\'' +
                ", remain_record=" + remain_record +
                '}';
    }

    public Integer getWithdraw_id() {
        return withdraw_id;
    }

    public void setWithdraw_id(Integer withdraw_id) {
        this.withdraw_id = withdraw_id;
    }

    public Integer getWithdraw_amount() {
        return withdraw_amount;
    }

    public void setWithdraw_amount(Integer withdraw_amount) {
        this.withdraw_amount = withdraw_amount;
    }

    public String getWithdraw_time() {
        return withdraw_time;
    }

    public void setWithdraw_time(String withdraw_time) {
        this.withdraw_time = withdraw_time;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getIntegral_id() {
        return integral_id;
    }

    public void setIntegral_id(Integer integral_id) {
        this.integral_id = integral_id;
    }

    public String getWithdraw_remark() {
        return withdraw_remark;
    }

    public void setWithdraw_remark(String withdraw_remark) {
        this.withdraw_remark = withdraw_remark;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public Integer getRemain_record() {
        return remain_record;
    }

    public void setRemain_record(Integer remain_record) {
        this.remain_record = remain_record;
    }

    public String getPay_password() {
        return pay_password;
    }

    public void setPay_password(String pay_password) {
        this.pay_password = pay_password;
    }

    public int getIs_process() {
        return is_process;
    }

    public void setIs_process(int is_process) {
        this.is_process = is_process;
    }

    public String getHandle_time() {
        return handle_time;
    }

    public void setHandle_time(String handle_time) {
        this.handle_time = handle_time;
    }
}
