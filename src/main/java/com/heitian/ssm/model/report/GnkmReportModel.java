package com.heitian.ssm.model.report;

/**
 * ClassName:JjkmReportModel
 * Package:com.heitian.ssm.model
 * Description:
 * author:@Fay
 * Date:2019/3/19 0019下午 2:56
 */
public class GnkmReportModel {
    private String FISCAL;
    private String FIS_PERD;
    private String CO_CODE;
    private String B_ACC_CODE;
    private String B_ACC_NAME;
    private String STAD_AMT;
    private String XZ_STAD_AMT;
    private String QCZ_STAD_AMT;
    private String SETID;
    private String MONEY;
    private String ID;
    private String isDisabled;
    private String IS_LOWEST;

    public String getB_ACC_CODE() {
        return B_ACC_CODE;
    }

    public void setB_ACC_CODE(String b_ACC_CODE) {
        B_ACC_CODE = b_ACC_CODE;
    }

    public String getB_ACC_NAME() {
        return B_ACC_NAME;
    }

    public void setB_ACC_NAME(String b_ACC_NAME) {
        B_ACC_NAME = b_ACC_NAME;
    }

    public String getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(String isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMONEY() {
        return MONEY;
    }

    public void setMONEY(String MONEY) {
        this.MONEY = MONEY;
    }

    public String getFISCAL() {
        return FISCAL;
    }

    public void setFISCAL(String FISCAL) {
        this.FISCAL = FISCAL;
    }

    public String getFIS_PERD() {
        return FIS_PERD;
    }

    public void setFIS_PERD(String FIS_PERD) {
        this.FIS_PERD = FIS_PERD;
    }

    public String getCO_CODE() {
        return CO_CODE;
    }

    public void setCO_CODE(String CO_CODE) {
        this.CO_CODE = CO_CODE;
    }

    public String getSTAD_AMT() {
        return STAD_AMT;
    }

    public void setSTAD_AMT(String STAD_AMT) {
        this.STAD_AMT = STAD_AMT;
    }

    public String getXZ_STAD_AMT() {
        return XZ_STAD_AMT;
    }

    public void setXZ_STAD_AMT(String XZ_STAD_AMT) {
        this.XZ_STAD_AMT = XZ_STAD_AMT;
    }

    public String getQCZ_STAD_AMT() {
        return QCZ_STAD_AMT;
    }

    public void setQCZ_STAD_AMT(String QCZ_STAD_AMT) {
        this.QCZ_STAD_AMT = QCZ_STAD_AMT;
    }

    public String getSETID() {
        return SETID;
    }

    public void setSETID(String SETID) {
        this.SETID = SETID;
    }

    public String getIS_LOWEST() {
        return IS_LOWEST;
    }

    public void setIS_LOWEST(String IS_LOWEST) {
        this.IS_LOWEST = IS_LOWEST;
    }
}
