package com.heitian.ssm.model.payment;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-03-01 22:22
 **/
public class PayBankModel {
    public String PAYBANKNO;
    public String ENAME;
    public String PAYBANKNAME;
    public String LINENUM;
    public String CBMUNITID;
    public String CBMUNITNAME;
    public String TAXID;
    public String C_YEAR;
    public String OPERATOR;
    public String PNO;
    public String GDATE;
    public String YEAR;
    public String MONTH;
    public String POWER ;

    public String getPOWER() {
        return POWER;
    }

    public void setPOWER(String POWER) {
        this.POWER = POWER;
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

    public String getOPERATOR() {
        return OPERATOR;
    }

    public void setOPERATOR(String OPERATOR) {
        this.OPERATOR = OPERATOR;
    }


    public String getPNO() {
        return PNO;
    }

    public void setPNO(String PNO) {
        this.PNO = PNO;
    }

    public String getC_YEAR() {
        return C_YEAR;
    }

    public void setC_YEAR(String c_YEAR) {
        C_YEAR = c_YEAR;
    }

    public String getPAYBANKNO() {
        return PAYBANKNO;
    }

    public void setPAYBANKNO(String PAYBANKNO) {
        this.PAYBANKNO = PAYBANKNO;
    }

    public String getENAME() {
        return ENAME;
    }

    public void setENAME(String ENAME) {
        this.ENAME = ENAME;
    }

    public String getPAYBANKNAME() {
        return PAYBANKNAME;
    }

    public void setPAYBANKNAME(String PAYBANKNAME) {
        this.PAYBANKNAME = PAYBANKNAME;
    }

    public String getLINENUM() {
        return LINENUM;
    }

    public void setLINENUM(String LINENUM) {
        this.LINENUM = LINENUM;
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

    public String getTAXID() {
        return TAXID;
    }

    public void setTAXID(String TAXID) {
        this.TAXID = TAXID;
    }
}
