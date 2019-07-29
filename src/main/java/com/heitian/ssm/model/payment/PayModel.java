package com.heitian.ssm.model.payment;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-12-12 02:55
 **/
public class PayModel {

    private String BILLNO; //支付单号
    private String BOOKNO; //单位流水号
    private String ISBATCHED;//  是否批审0否1是
    private String ISVOUCHER;//是否已生成支付凭证
    private String PAYMODE;//支付类型： 0-c 1-直接支付
    private String RECBANKNAME;//收款人开户银行
    private String RECBANKNO;//收款人账号
    private String RECNAME;//收款人名称
    private String SETTLENO;//结算票据号码
    private String PURPOSE;//用途
    private String TOTALMONEY;//核算金额
    private String USERNAME;//单位录入姓名
    private String VOUBOOKNO;//支付中心流水号



    public String getPURPOSE() {
        return PURPOSE;
    }

    public void setPURPOSE(String PURPOSE) {
        this.PURPOSE = PURPOSE;
    }

    public String getBILLNO() {
        return BILLNO;
    }

    public void setBILLNO(String BILLNO) {
        this.BILLNO = BILLNO;
    }

    public String getBOOKNO() {
        return BOOKNO;
    }

    public void setBOOKNO(String BOOKNO) {
        this.BOOKNO = BOOKNO;
    }

    public String getISBATCHED() {
        return ISBATCHED;
    }

    public void setISBATCHED(String ISBATCHED) {
        this.ISBATCHED = ISBATCHED;
    }

    public String getISVOUCHER() {
        return ISVOUCHER;
    }

    public void setISVOUCHER(String ISVOUCHER) {
        this.ISVOUCHER = ISVOUCHER;
    }

    public String getPAYMODE() {
        return PAYMODE;
    }

    public void setPAYMODE(String PAYMODE) {
        this.PAYMODE = PAYMODE;
    }

    public String getRECBANKNAME() {
        return RECBANKNAME;
    }

    public void setRECBANKNAME(String RECBANKNAME) {
        this.RECBANKNAME = RECBANKNAME;
    }

    public String getRECBANKNO() {
        return RECBANKNO;
    }

    public void setRECBANKNO(String RECBANKNO) {
        this.RECBANKNO = RECBANKNO;
    }

    public String getRECNAME() {
        return RECNAME;
    }

    public void setRECNAME(String RECNAME) {
        this.RECNAME = RECNAME;
    }

    public String getSETTLENO() {
        return SETTLENO;
    }

    public void setSETTLENO(String SETTLENO) {
        this.SETTLENO = SETTLENO;
    }

    public String getTOTALMONEY() {
        return TOTALMONEY;
    }

    public void setTOTALMONEY(String TOTALMONEY) {
        this.TOTALMONEY = TOTALMONEY;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getVOUBOOKNO() {
        return VOUBOOKNO;
    }

    public void setVOUBOOKNO(String VOUBOOKNO) {
        this.VOUBOOKNO = VOUBOOKNO;
    }
}
