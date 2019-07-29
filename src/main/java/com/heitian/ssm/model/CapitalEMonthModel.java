package com.heitian.ssm.model;

/**
 * 预算外财政支出model
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-02-21 09:52
 **/
public class CapitalEMonthModel {

    public String CBMUNITID;
    public String CBMUNITNAME;
    public String C_ACC_NAME;
    public String C_MONEY;
    public String DDATE;
    public String IYEAR;
    public String IMONTH;
    public String PNO;

    public String getPNO() {
        return PNO;
    }

    public void setPNO(String PNO) {
        this.PNO = PNO;
    }

    public String getCBMUNITID() {
        return CBMUNITID;
    }

    public void setCBMUNITID(String CBMUNITID) {
        this.CBMUNITID = CBMUNITID;
    }

    public String getCBMUNITNAME() {
        return CBMUNITNAME;
    }

    public void setCBMUNITNAME(String CBMUNITNAME) {
        this.CBMUNITNAME = CBMUNITNAME;
    }

    public String getC_ACC_NAME() {
        return C_ACC_NAME;
    }

    public void setC_ACC_NAME(String c_ACC_NAME) {
        C_ACC_NAME = c_ACC_NAME;
    }

    public String getC_MONEY() {
        return C_MONEY;
    }

    public void setC_MONEY(String c_MONEY) {
        C_MONEY = c_MONEY;
    }

    public String getDDATE() {
        return DDATE;
    }

    public void setDDATE(String DDATE) {
        this.DDATE = DDATE;
    }

    public String getIYEAR() {
        return IYEAR;
    }

    public void setIYEAR(String IYEAR) {
        this.IYEAR = IYEAR;
    }

    public String getIMONTH() {
        return IMONTH;
    }

    public void setIMONTH(String IMONTH) {
        this.IMONTH = IMONTH;
    }
}
