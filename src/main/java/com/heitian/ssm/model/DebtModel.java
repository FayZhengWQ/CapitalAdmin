package com.heitian.ssm.model;

/**
 * 资产负债表
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-02-10 14:59
 **/
public class DebtModel {

    public String CBMUNITID;
    public String CBMUNITNAME;
    public String C_ACC_NAME;
    public String C_START_MONEY;
    public String C_END_MONEY;
    public String D_ACC_NAME;
    public String D_START_MONEY;
    public String D_END_MONEY;
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

    public String getC_START_MONEY() {
        return C_START_MONEY;
    }

    public void setC_START_MONEY(String c_START_MONEY) {
        C_START_MONEY = c_START_MONEY;
    }

    public String getC_END_MONEY() {
        return C_END_MONEY;
    }

    public void setC_END_MONEY(String c_END_MONEY) {
        C_END_MONEY = c_END_MONEY;
    }

    public String getD_ACC_NAME() {
        return D_ACC_NAME;
    }

    public void setD_ACC_NAME(String d_ACC_NAME) {
        D_ACC_NAME = d_ACC_NAME;
    }

    public String getD_START_MONEY() {
        return D_START_MONEY;
    }

    public void setD_START_MONEY(String d_START_MONEY) {
        D_START_MONEY = d_START_MONEY;
    }

    public String getD_END_MONEY() {
        return D_END_MONEY;
    }

    public void setD_END_MONEY(String d_END_MONEY) {
        D_END_MONEY = d_END_MONEY;
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
