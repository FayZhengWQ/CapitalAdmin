package com.heitian.ssm.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-08 14:35
 **/
@Getter
@Setter
public class JczlJsonData {

    private boolean code;
    private String msg;
    private int count;
    private Object enterprise;
    private Object functionsection;
    private Object economysection;
    private Object economysectiongov;
    private Object resourcecategory;
    private Object budgetcategory;
    private Object options;
    private Object division;

    public JczlJsonData() {
    }

    public JczlJsonData(boolean code) {
        this.code = code;
    }


    public static JczlJsonData success_jczl(Object Unit, Object fun, Object Econ, Object list_EconGov, Object Resoure, Object Budget, Object options, Object division, String msg) {
        JczlJsonData jsonData = new JczlJsonData(true);
        jsonData.enterprise = Unit;
        jsonData.functionsection = fun;
        jsonData.economysection=Econ;
        jsonData.economysectiongov=list_EconGov;
        jsonData.resourcecategory=Resoure;
        jsonData.budgetcategory=Budget;
        jsonData.options=options;
        jsonData.division=division;
        jsonData.msg = msg;
        return jsonData;
    }
}
