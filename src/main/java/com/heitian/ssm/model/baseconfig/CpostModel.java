package com.heitian.ssm.model.baseconfig;

/**
 * ClassName:CpostModel
 * Package:com.heitian.ssm.model
 * Description:
 * author:@Fay
 * Date:2019/3/12 0012下午 12:42
 */
public class CpostModel {
    private String CPOSTNAME;
    private String CPOSTGUID;
    private String ISDISABLEED;

    public String getCPOSTNAME() {
        return CPOSTNAME;
    }

    public void setCPOSTNAME(String CPOSTNAME) {
        this.CPOSTNAME = CPOSTNAME;
    }

    public String getCPOSTGUID() {
        return CPOSTGUID;
    }

    public void setCPOSTGUID(String CPOSTGUID) {
        this.CPOSTGUID = CPOSTGUID;
    }

    public String getISDISABLEED() {
        return ISDISABLEED;
    }

    public void setISDISABLEED(String ISDISABLEED) {
        this.ISDISABLEED = ISDISABLEED;
    }

}
