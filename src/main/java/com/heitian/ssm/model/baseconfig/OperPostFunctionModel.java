package com.heitian.ssm.model.baseconfig;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-03-14 14:02
 **/
public class OperPostFunctionModel {

    public String CUSERID;
    public String CPOSTGUID;
    public String CFUNCTIONGUID;

    public String getCUSERID() {
        return CUSERID;
    }

    public void setCUSERID(String CUSERID) {
        this.CUSERID = CUSERID;
    }

    public String getCPOSTGUID() {
        return CPOSTGUID;
    }

    public void setCPOSTGUID(String CPOSTGUID) {
        this.CPOSTGUID = CPOSTGUID;
    }

    public String getCFUNCTIONGUID() {
        return CFUNCTIONGUID;
    }

    public void setCFUNCTIONGUID(String CFUNCTIONGUID) {
        this.CFUNCTIONGUID = CFUNCTIONGUID;
    }
}
