package com.heitian.ssm.model.baseconfig;

/**
 * 导航栏model
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-02-22 16:00
 **/
public class FunctionModel {


    public String TITLE;
    public String ICON;
    public String VISIBLE;
    public String ROUTERLINK;
    public String GUID;
    public String PARENT_GUID;
    public String LEVEL_NUM;
    public String ISOPEN;
    public String ISSELECTED;
    public String ISDISABLEED ;
    public String OPERATOR;
    public String GDATE;
    public String YEAR;
    public String MONTH;
    public String CBMUNITID;

    public String getCBMUNITID() {
        return CBMUNITID;
    }

    public void setCBMUNITID(String CBMUNITID) {
        this.CBMUNITID = CBMUNITID;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getICON() {
        return ICON;
    }

    public void setICON(String ICON) {
        this.ICON = ICON;
    }

    public String getVISIBLE() {
        return VISIBLE;
    }

    public void setVISIBLE(String VISIBLE) {
        this.VISIBLE = VISIBLE;
    }

    public String getROUTERLINK() {
        return ROUTERLINK;
    }

    public void setROUTERLINK(String ROUTERLINK) {
        this.ROUTERLINK = ROUTERLINK;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getPARENT_GUID() {
        return PARENT_GUID;
    }

    public void setPARENT_GUID(String PARENT_GUID) {
        this.PARENT_GUID = PARENT_GUID;
    }

    public String getLEVEL_NUM() {
        return LEVEL_NUM;
    }

    public void setLEVEL_NUM(String LEVEL_NUM) {
        this.LEVEL_NUM = LEVEL_NUM;
    }

    public String getISOPEN() {
        return ISOPEN;
    }

    public void setISOPEN(String ISOPEN) {
        this.ISOPEN = ISOPEN;
    }

    public String getISSELECTED() {
        return ISSELECTED;
    }

    public void setISSELECTED(String ISSELECTED) {
        this.ISSELECTED = ISSELECTED;
    }

    public String getISDISABLEED() {
        return ISDISABLEED;
    }

    public void setISDISABLEED(String ISDISABLEED) {
        this.ISDISABLEED = ISDISABLEED;
    }

    public String getOPERATOR() {
        return OPERATOR;
    }

    public void setOPERATOR(String OPERATOR) {
        this.OPERATOR = OPERATOR;
    }

    public String getGDATE() {
        return GDATE;
    }

    public void setGDATE(String GDATE) {
        this.GDATE = GDATE;
    }

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }

    public String getMONTH() {
        return MONTH;
    }

    public void setMONTH(String MONTH) {
        this.MONTH = MONTH;
    }

}
