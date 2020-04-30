package com.example.baselib.util;

import java.text.NumberFormat;

/**
 * Created by xiaohan on 2018/3/11.
 */

public class DigitUtil {

    /**
     * 构造器私有
     */
    private DigitUtil() {
    }

    /**
     * 判断一个数是多少位
     *
     * @param number
     * @return
     */
    public static int digitNum(int number) {
        int count = 0;
        if (number >= 0) {
            if (number == 0)
                count = 1;//位数为1
            while (number > 0) {
                number = number / 10;
                count++;
            }
        } else {
            while (number < 0) {
                number = number / 10;
                count++;
            }
        }
        return count;
    }

    /**
     * 格式化一个浮点数
     *
     * @param f
     * @param digit
     * @return
     */
    public static String formatFloat(float f, int digit) {

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(digit);
        return nf.format(f);
    }

}
