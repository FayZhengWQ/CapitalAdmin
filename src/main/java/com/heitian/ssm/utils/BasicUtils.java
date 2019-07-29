package com.heitian.ssm.utils;

/**
 * @program: CapitalAdmin
 * @description:     基础配置
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-08 09:57
 **/
public class BasicUtils {

    //收支类型
    public static String  getSZTYPE(String sztype){
        String SZTYPENAME="";
        if (sztype.equals("05")){
            SZTYPENAME="公共财政预算"  ;
        }else if (sztype.equals("06")){
            SZTYPENAME="政府性基金"  ;
        }else if (sztype.equals("11")){
            SZTYPENAME="社保基金"  ;
        }else if (sztype.equals("12")){
            SZTYPENAME="国有资本经营性";
        }
        return SZTYPENAME;
    }


}
