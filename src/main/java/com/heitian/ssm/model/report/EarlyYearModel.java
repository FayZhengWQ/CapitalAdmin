package com.heitian.ssm.model.report;

/**
 * ClassName:EarlyYearModel
 * Package:com.heitian.ssm.model
 * Description:
 * author:@Fay
 * Date:2019/4/1119:39
 */
public class EarlyYearModel {
    private String CO_CODE ;
    private String B_ACC_CODE;
    private String B_ACC_NAME;
    private String MONEY ;
    private String FISCAL ;

    public String getCO_CODE() {
        return CO_CODE;
    }

    public void setCO_CODE(String CO_CODE) {
        this.CO_CODE = CO_CODE;
    }

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
}
