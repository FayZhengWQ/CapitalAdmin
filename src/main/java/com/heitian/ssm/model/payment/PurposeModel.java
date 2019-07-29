package com.heitian.ssm.model.payment;

/**
 * ClassName:PurposeModel
 * Package:com.heitian.ssm.model
 * Description:
 * author:@Fay
 * Date:2019/4/3012:29
 */
public class PurposeModel {
    private String PURPOSEID;
    private String PURPOSENAME;
    private String CBMUNITID;
    private String CBMUNITNAME;
    private String C_YEAR;
    private String GDATE;
    private String YEAR;
    private String MONTH;
    private String OPERATOR;
    private String PNO;
    private String POWER;


    public String getPOWER() {
        return POWER;
    }

    public void setPOWER(String POWER) {
        this.POWER = POWER;
    }

    public String getPNO() {
        return PNO;
    }

    public void setPNO(String PNO) {
        this.PNO = PNO;
    }

    public String getPURPOSEID() {
        return PURPOSEID;
    }

    public void setPURPOSEID(String PURPOSEID) {
        this.PURPOSEID = PURPOSEID;
    }

    public String getPURPOSENAME() {
        return PURPOSENAME;
    }

    public void setPURPOSENAME(String PURPOSENAME) {
        this.PURPOSENAME = PURPOSENAME;
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

    public String getC_YEAR() {
        return C_YEAR;
    }

    public void setC_YEAR(String c_YEAR) {
        C_YEAR = c_YEAR;
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
}
