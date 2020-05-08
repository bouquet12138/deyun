package com.example.common_lib.java_bean;

public class IntegralBean {

   // public static final String WITHDRAW = "withdraw";
    public static final String BUY_SET_MEAL = "buy_set_meal";
   public static final String TRANSFERS_BETWEEN = "transfers_between";
   // public static final String PROMOTE_INCOME = "promote_income";
    public static final String RECHARGE = "recharge";
    public static final String OTHER = "other";


    private int integral_id;
    private long transaction_amount;
    private String transaction_time;
    private int user_id;
    private int target_user_id;
    private String integral_type;
    private String transaction_remark;
    private long remain_record;

    private String pay_password;

    public IntegralBean(long transaction_amount, int user_id, int target_user_id,
                        String integral_type, String transaction_remark, String pay_password) {
        this.transaction_amount = transaction_amount;
        this.user_id = user_id;
        this.target_user_id = target_user_id;
        this.integral_type = integral_type;
        this.transaction_remark = transaction_remark;
        this.pay_password = pay_password;
    }

    @Override
    public String toString() {
        return "IntegralBean{" +
                "integral_id=" + integral_id +
                ", transaction_amount=" + transaction_amount +
                ", transaction_time=" + transaction_time +
                ", user_id=" + user_id +
                ", target_user_id=" + target_user_id +
                ", integral_type='" + integral_type + '\'' +
                ", transaction_remark='" + transaction_remark + '\'' +
                ", remain_record=" + remain_record +
                '}';
    }

    public int getIntegral_id() {
        return integral_id;
    }

    public void setIntegral_id(int integral_id) {
        this.integral_id = integral_id;
    }

    public long getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(long transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public String getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(String transaction_time) {
        this.transaction_time = transaction_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTarget_user_id() {
        return target_user_id;
    }

    public void setTarget_user_id(int target_user_id) {
        this.target_user_id = target_user_id;
    }

    public String getIntegral_type() {
        return integral_type;
    }

    public void setIntegral_type(String integral_type) {
        this.integral_type = integral_type;
    }

    public String getTransaction_remark() {
        return transaction_remark;
    }

    public void setTransaction_remark(String transaction_remark) {
        this.transaction_remark = transaction_remark;
    }

    public long getRemain_record() {
        return remain_record;
    }

    public void setRemain_record(long remain_record) {
        this.remain_record = remain_record;
    }
}
