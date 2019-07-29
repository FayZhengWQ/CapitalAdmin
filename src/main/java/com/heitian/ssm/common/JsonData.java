package com.heitian.ssm.common;


import lombok.Getter;
import lombok.Setter;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-02 14:55
 **/
@Getter
@Setter
public class JsonData {
    private boolean code;
    private String msg;
    private Object dataList;
    private int count;

    public JsonData() {
    }

    public JsonData(boolean code) {
        this.code = code;
    }



    public static JsonData success(Object object, String msg, int count) {
        JsonData jsonData = new JsonData(true);
        jsonData.count = count;
        jsonData.dataList = object;
        jsonData.msg = msg;
        return jsonData;
    }

    public static JsonData success(String msg) {

        JsonData jsonData = new JsonData(true);
        jsonData.msg=msg;
        return jsonData;
    }


    public static JsonData fail(String msg) {
        JsonData jsonData = new JsonData(false);
        jsonData.msg = msg;
        return jsonData;

    }
}
