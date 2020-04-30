package com.example.common_view.utils;


import android.text.TextUtils;
import android.util.Log;

import com.example.common_view.bean.DayBean;


public class SolarLunarConverter {

    private static final String TAG = "SolarLunarConverter";

    /*	 * |----4位闰月|-------------13位1为30天，0为29天|	 */
    private static int[] lunar_month_days = {1887, 0x1694, 0x16aa, 0x4ad5, 0xab6, 0xc4b7, 0x4ae, 0xa56, 0xb52a, 0x1d2a, 0xd54, 0x75aa, 0x156a, 0x1096d, 0x95c, 0x14ae, 0xaa4d, 0x1a4c, 0x1b2a, 0x8d55, 0xad4, 0x135a, 0x495d, 0x95c, 0xd49b, 0x149a, 0x1a4a, 0xbaa5, 0x16a8, 0x1ad4, 0x52da, 0x12b6, 0xe937, 0x92e, 0x1496, 0xb64b, 0xd4a, 0xda8, 0x95b5, 0x56c, 0x12ae, 0x492f, 0x92e, 0xcc96, 0x1a94, 0x1d4a, 0xada9, 0xb5a, 0x56c, 0x726e, 0x125c, 0xf92d, 0x192a, 0x1a94, 0xdb4a, 0x16aa, 0xad4, 0x955b, 0x4ba, 0x125a, 0x592b, 0x152a, 0xf695, 0xd94, 0x16aa, 0xaab5, 0x9b4, 0x14b6, 0x6a57, 0xa56, 0x1152a, 0x1d2a, 0xd54, 0xd5aa, 0x156a, 0x96c, 0x94ae, 0x14ae, 0xa4c, 0x7d26, 0x1b2a, 0xeb55, 0xad4, 0x12da, 0xa95d, 0x95a, 0x149a, 0x9a4d, 0x1a4a, 0x11aa5, 0x16a8, 0x16d4, 0xd2da, 0x12b6, 0x936, 0x9497, 0x1496, 0x1564b, 0xd4a, 0xda8, 0xd5b4, 0x156c, 0x12ae, 0xa92f, 0x92e, 0xc96, 0x6d4a, 0x1d4a, 0x10d65, 0xb58, 0x156c, 0xb26d, 0x125c, 0x192c, 0x9a95, 0x1a94, 0x1b4a, 0x4b55, 0xad4, 0xf55b, 0x4ba, 0x125a, 0xb92b, 0x152a, 0x1694, 0x96aa, 0x15aa, 0x12ab5, 0x974, 0x14b6, 0xca57, 0xa56, 0x1526, 0x8e95, 0xd54, 0x15aa, 0x49b5, 0x96c, 0xd4ae, 0x149c, 0x1a4c, 0xbd26, 0x1aa6, 0xb54, 0x6d6a, 0x12da, 0x1695d, 0x95a, 0x149a, 0xda4b, 0x1a4a, 0x1aa4, 0xbb54, 0x16b4, 0xada, 0x495b, 0x936, 0xf497, 0x1496, 0x154a, 0xb6a5, 0xda4, 0x15b4, 0x6ab6, 0x126e, 0x1092f, 0x92e, 0xc96, 0xcd4a, 0x1d4a, 0xd64, 0x956c, 0x155c, 0x125c, 0x792e, 0x192c, 0xfa95, 0x1a94, 0x1b4a, 0xab55, 0xad4, 0x14da, 0x8a5d, 0xa5a, 0x1152b, 0x152a, 0x1694, 0xd6aa, 0x15aa, 0xab4, 0x94ba, 0x14b6, 0xa56, 0x7527, 0xd26, 0xee53, 0xd54, 0x15aa, 0xa9b5, 0x96c, 0x14ae, 0x8a4e, 0x1a4c, 0x11d26, 0x1aa4, 0x1b54, 0xcd6a, 0xada, 0x95c, 0x949d, 0x149a, 0x1a2a, 0x5b25, 0x1aa4, 0xfb52, 0x16b4, 0xaba, 0xa95b, 0x936, 0x1496, 0x9a4b, 0x154a, 0x136a5, 0xda4, 0x15ac};

    private static int[] solar_1_1 = {1887, 0xec04c, 0xec23f, 0xec435, 0xec649, 0xec83e, 0xeca51, 0xecc46, 0xece3a, 0xed04d, 0xed242, 0xed436, 0xed64a, 0xed83f, 0xeda53, 0xedc48, 0xede3d, 0xee050, 0xee244, 0xee439, 0xee64d, 0xee842, 0xeea36, 0xeec4a, 0xeee3e, 0xef052, 0xef246, 0xef43a, 0xef64e, 0xef843, 0xefa37, 0xefc4b, 0xefe41, 0xf0054, 0xf0248, 0xf043c, 0xf0650, 0xf0845, 0xf0a38, 0xf0c4d, 0xf0e42, 0xf1037, 0xf124a, 0xf143e, 0xf1651, 0xf1846, 0xf1a3a, 0xf1c4e, 0xf1e44, 0xf2038, 0xf224b, 0xf243f, 0xf2653, 0xf2848, 0xf2a3b, 0xf2c4f, 0xf2e45, 0xf3039, 0xf324d, 0xf3442, 0xf3636, 0xf384a, 0xf3a3d, 0xf3c51, 0xf3e46, 0xf403b, 0xf424e, 0xf4443, 0xf4638, 0xf484c, 0xf4a3f, 0xf4c52, 0xf4e48, 0xf503c, 0xf524f, 0xf5445, 0xf5639, 0xf584d, 0xf5a42, 0xf5c35, 0xf5e49, 0xf603e, 0xf6251, 0xf6446, 0xf663b, 0xf684f, 0xf6a43, 0xf6c37, 0xf6e4b, 0xf703f, 0xf7252, 0xf7447, 0xf763c, 0xf7850, 0xf7a45, 0xf7c39, 0xf7e4d, 0xf8042, 0xf8254, 0xf8449, 0xf863d, 0xf8851, 0xf8a46, 0xf8c3b, 0xf8e4f, 0xf9044, 0xf9237, 0xf944a, 0xf963f, 0xf9853, 0xf9a47, 0xf9c3c, 0xf9e50, 0xfa045, 0xfa238, 0xfa44c, 0xfa641, 0xfa836, 0xfaa49, 0xfac3d, 0xfae52, 0xfb047, 0xfb23a, 0xfb44e, 0xfb643, 0xfb837, 0xfba4a, 0xfbc3f, 0xfbe53, 0xfc048, 0xfc23c, 0xfc450, 0xfc645, 0xfc839, 0xfca4c, 0xfcc41, 0xfce36, 0xfd04a, 0xfd23d, 0xfd451, 0xfd646, 0xfd83a, 0xfda4d, 0xfdc43, 0xfde37, 0xfe04b, 0xfe23f, 0xfe453, 0xfe648, 0xfe83c, 0xfea4f, 0xfec44, 0xfee38, 0xff04c, 0xff241, 0xff436, 0xff64a, 0xff83e, 0xffa51, 0xffc46, 0xffe3a, 0x10004e, 0x100242, 0x100437, 0x10064b, 0x100841, 0x100a53, 0x100c48, 0x100e3c, 0x10104f, 0x101244, 0x101438, 0x10164c, 0x101842, 0x101a35, 0x101c49, 0x101e3d, 0x102051, 0x102245, 0x10243a, 0x10264e, 0x102843, 0x102a37, 0x102c4b, 0x102e3f, 0x103053, 0x103247, 0x10343b, 0x10364f, 0x103845, 0x103a38, 0x103c4c, 0x103e42, 0x104036, 0x104249, 0x10443d, 0x104651, 0x104846, 0x104a3a, 0x104c4e, 0x104e43, 0x105038, 0x10524a, 0x10543e, 0x105652, 0x105847, 0x105a3b, 0x105c4f, 0x105e45, 0x106039, 0x10624c, 0x106441, 0x106635, 0x106849, 0x106a3d, 0x106c51, 0x106e47, 0x10703c, 0x10724f, 0x107444, 0x107638, 0x10784c, 0x107a3f, 0x107c53, 0x107e48};

    private static int GetBitInt(int data, int length, int shift) {
        return (data & (((1 << length) - 1) << shift)) >> shift;
    }

    private static long SolarToInt(int y, int m, int d) {
        m = (m + 9) % 12;
        y = y - m / 10;
        return 365 * y + y / 4 - y / 100 + y / 400 + (m * 306 + 5) / 10 + (d - 1);
    }

    /**
     * 公历转农历
     */
    public static DayBean SolarToLunar(int year, int month, int day) {

        DayBean dateBean = isSolarFestival(month, day);
        if (dateBean != null)//节日不是空
            return dateBean;

        int index = year - solar_1_1[0];
        int data = (year << 9) | (month << 5) | (day);
        int solar11 = 0;
        if (solar_1_1[index] > data) {
            index--;
        }
        solar11 = solar_1_1[index];
        int y = GetBitInt(solar11, 12, 9);
        int m = GetBitInt(solar11, 4, 5);
        int d = GetBitInt(solar11, 5, 0);
        long offset = SolarToInt(year, month, day) - SolarToInt(y, m, d);
        int days = lunar_month_days[index];
        int leap = GetBitInt(days, 4, 13);
        // int lunarYear = index + solar_1_1[0];
        int lunarMonth = 1;
        int lunarDay;
        offset += 1;
        for (int i = 0; i < 13; i++) {
            int dm = GetBitInt(days, 1, 12 - i) == 1 ? 30 : 29;
            if (offset > dm) {
                lunarMonth++;
                offset -= dm;
            } else {
                break;
            }
        }
        lunarDay = (int) (offset);
        if (leap != 0 && lunarMonth > leap) {
            lunarMonth--;
        }

        DayBean dateBean1 = isLunarFestival(lunarMonth, lunarDay, day);
        if (dateBean1 != null)//是否是阴历节日
            return dateBean1;

        String solarTerm = SolarTermUtil.getTermName(year, month, day);
        Log.d(TAG, "SolarToLunar: " + solarTerm);

        if (!TextUtils.isEmpty(solarTerm))
            return new DayBean(day, false, solarTerm);

        return new DayBean(day, false, getLunar(lunarMonth, lunarDay));
    }

    /**
     * 得到对应的阴历表示
     *
     * @return
     */
    private static String getLunar(int lunarMonth, int lunarDay) {

        String months[] = new String[]{"正月", "二月", "三月", "四月", "五月", "六月",
                "七月", "八月", "九月", "十月", "十一月", "腊月"};
        String days[] = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
        if (lunarDay == 1) {
            return months[lunarMonth - 1];
        }

        if (lunarDay <= 10) {
            return "初" + days[lunarDay - 1];
        } else if (lunarDay < 20) {
            return "十" + days[lunarDay % 10 - 1];
        } else if (lunarDay == 20)
            return "二十";
        else if (lunarDay < 30)
            return "廿" + days[lunarDay % 10 - 1];

        return "三十";
    }

    /**
     * 是否是阴历节日
     *
     * @param lunarMonth
     * @param lunarDay
     * @return
     */
    private static DayBean isLunarFestival(int lunarMonth, int lunarDay, int day) {

        if (lunarMonth == 1 && lunarDay == 1)
            return new DayBean(day, true, "春节");
        if (lunarMonth == 1 && lunarDay == 15)
            return new DayBean(day, false, "元宵节");
        if (lunarMonth == 1 && lunarDay == 15)
            return new DayBean(day, true, "端午节");
        if (lunarMonth == 7 && lunarDay == 7)
            return new DayBean(day, false, "七夕节");
        if (lunarMonth == 7 && lunarDay == 15)
            return new DayBean(day, false, "中元节");
        if (lunarMonth == 8 && lunarDay == 15)
            return new DayBean(day, true, "中秋节");
        if (lunarMonth == 9 && lunarDay == 9)
            return new DayBean(day, false, "重阳节");
        if (lunarMonth == 10 && lunarDay == 1)
            return new DayBean(day, false, "寒衣节");
        if (lunarMonth == 10 && lunarDay == 15)
            new DayBean(day, false, "下元节");
        if (lunarMonth == 12 && lunarDay == 8)
            return new DayBean(day, false, "腊八节");
        if (lunarMonth == 12 && lunarDay == 29)
            return new DayBean(day, true, "除夕");

        return null;
    }

    /**
     * 是否是公历节日
     *
     * @return
     */
    private static DayBean isSolarFestival(int month, int day) {

        if (month == 1 && day == 1)
            return new DayBean(day, true, "元旦");
        if (month == 2 && day == 14)
            return new DayBean(day, false, "情人节");
        if (month == 3 && day == 8)
            return new DayBean(day, false, "妇女节");
        if (month == 3 && day == 12)
            return new DayBean(day, false, "植树节");
        if (month == 4 && day == 1)
            return new DayBean(day, false, "愚人节");
        if (month == 4 && day == 5)
            return new DayBean(day, true, "清明节");
        if (month == 4 && day == 22)
            return new DayBean(day, false, "地球日");
        if (month == 5 && day == 1)
            return new DayBean(day, true, "劳动节");
        if (month == 5 && day == 4)
            return new DayBean(day, false, "五四青年节");
        if (month == 6 && day == 1)
            return new DayBean(day, false, "儿童节");
        if (month == 6 && day == 23)
            return new DayBean(day, false, "奥林匹克日");
        if (month == 7 && day == 1)
            return new DayBean(day, false, "建党日");
        if (month == 8 && day == 1)
            return new DayBean(day, false, "建军节");
        if (month == 9 && day == 10)
            return new DayBean(day, false, "教师节");
        if (month == 10 && day == 1)
            return new DayBean(day, true, "国庆节");
        if (month == 11 && day == 28)
            return new DayBean(day, false, "感恩节");
        if (month == 12 && day == 1)
            return new DayBean(day, false, "艾滋病日");
        if (month == 12 && day == 13)
            return new DayBean(day, false, "国家公祭日");
        if (month == 12 && day == 24)
            return new DayBean(day, false, "平安夜");
        if (month == 12 && day == 25)
            return new DayBean(day, false, "圣诞节");

        return null;
    }

}

