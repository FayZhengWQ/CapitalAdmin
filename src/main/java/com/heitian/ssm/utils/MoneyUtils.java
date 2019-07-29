package com.heitian.ssm.utils;

import com.heitian.ssm.model.project.Project;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @program: CapitalAdmin
 * @description: 金额工具类
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-07 15:37
 **/
public class MoneyUtils {


    //金额格式化
    public static String fmtMicrometer(String text) {
        DecimalFormat df = null;
        if (text.indexOf(".") > 0) {
            if (text.length() - text.indexOf(".") - 1 == 0) {
                df = new DecimalFormat("###,##0.");
            } else if (text.length() - text.indexOf(".") - 1 == 1) {
                df = new DecimalFormat("###,##0.0");
            } else {
                df = new DecimalFormat("###,##0.00");
            }
        } else {
            df = new DecimalFormat("###,##0.00");
        }
        double number = 0.0;
        try {
            number = Double.parseDouble(text);
        } catch (Exception e) {
            number = 0.0;
        }
        return df.format(number);
    }


    // 列表输出
    public  static List<Project> ToTableList(List<Project> list) {




        return  list;
    }
}
