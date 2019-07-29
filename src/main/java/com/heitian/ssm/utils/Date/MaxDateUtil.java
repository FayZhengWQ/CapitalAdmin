package com.heitian.ssm.utils.Date;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * ClassName:MaxDateUtil
 * Package:com.heitian.ssm.utils.Date
 * Description:
 * author:@Fay
 * Date:2019/4/2414:09
 */
public class MaxDateUtil {
    //获取指定年份的最后一天
    public static String getLastDayOfMonth(String yearMonth) {
        int year = Integer.parseInt(yearMonth.split("-")[0]);  //年
        int month = Integer.parseInt(yearMonth.split("-")[1]); //月
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(cal.getTime());
    }
}
