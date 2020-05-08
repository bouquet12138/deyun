package com.example.common_lib.java_bean;

/**
 * 工资bean
 */
public class PayrollBean {

    /**
     * promote_income推广收入
     * <p>
     * withdraw 提现
     * <p>
     * transfers_between 互转
     * <p>
     * other 其他
     */
    public static final String PROMOTE_INCOME = "promote_income";
    public static final String WITHDRAW = "withdraw";
    public static final String TRANSFERS_BETWEEN = "transfers_between";
    public static final String OTHER = "other";

    private int payroll_id;
    private long payroll_amount;
    private String transaction_time;
    private int user_id;
    private int target_user_id;
    private String type;
    private String transaction_remark;
    private String promote_income_type;//推广收入类型 比如 1:1:0 3:3:0 等
    private int cumulative_day;
    private long remain_record;

    public PayrollBean(long payroll_amount, String transaction_time,
                       int user_id, String type, String transaction_remark,
                       String promote_income_type, int cumulative_day, long remain_record) {
        this.payroll_amount = payroll_amount;
        this.transaction_time = transaction_time;
        this.user_id = user_id;
        this.type = type;
        this.transaction_remark = transaction_remark;
        this.promote_income_type = promote_income_type;
        this.cumulative_day = cumulative_day;
        this.remain_record = remain_record;
    }

    public int getPayroll_id() {
        return payroll_id;
    }

    public void setPayroll_id(int payroll_id) {
        this.payroll_id = payroll_id;
    }

    public long getPayroll_amount() {
        return payroll_amount;
    }

    public void setPayroll_amount(long payroll_amount) {
        this.payroll_amount = payroll_amount;
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


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTransaction_remark() {
        return transaction_remark;
    }

    public void setTransaction_remark(String transaction_remark) {
        this.transaction_remark = transaction_remark;
    }

    public String getPromote_income_type() {
        return promote_income_type;
    }

    public void setPromote_income_type(String promote_income_type) {
        this.promote_income_type = promote_income_type;
    }

    public int getCumulative_day() {
        return cumulative_day;
    }

    public void setCumulative_day(int cumulative_day) {
        this.cumulative_day = cumulative_day;
    }

    public long getRemain_record() {
        return remain_record;
    }

    public void setRemain_record(long remain_record) {
        this.remain_record = remain_record;
    }

    public int getTarget_user_id() {
        return target_user_id;
    }

    public void setTarget_user_id(int target_user_id) {
        this.target_user_id = target_user_id;
    }
}
