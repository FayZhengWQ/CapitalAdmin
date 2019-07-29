package com.heitian.ssm.model;

/**
 * @program: web-ssm
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-05-13 09:25
 **/
public class VoucherBean {

    public String VoucherJson;
    public int PNO;
    public String CBMUNITID;
    public String LODOPUP;
    public String LODOPLEFT;

    public String getCBMUNITID() {
        return CBMUNITID;
    }

    public void setCBMUNITID(String CBMUNITID) {
        this.CBMUNITID = CBMUNITID;
    }

    public String getLODOPUP() {
        return LODOPUP;
    }

    public void setLODOPUP(String LODOPUP) {
        this.LODOPUP = LODOPUP;
    }

    public String getLODOPLEFT() {
        return LODOPLEFT;
    }

    public void setLODOPLEFT(String LODOPLEFT) {
        this.LODOPLEFT = LODOPLEFT;
    }

    public String getVoucherJson() {
        return VoucherJson;
    }

    public void setVoucherJson(String voucherJson) {
        VoucherJson = voucherJson;
    }

    public int getPNO() {
        return PNO;
    }

    public void setPNO(int PNO) {
        this.PNO = PNO;
    }
}
