package com.heitian.ssm.model.report;

/**
 * ClassName:BudgetModel
 * Package:com.heitian.ssm.model
 * Description:
 * author:@Fay
 * Date:2019/4/39:56
 */
public class BudgetModel {
    private String CO_CODE;
    private String B_ACC_CODE;
    private String B_ACC_NAME;
    private String MONEY1;
    private String MONEY2;
    private String MONEY3;
    private String MONEY4;
    private String MONEY5;
    private String MONEY6;
    private String PLAN;
    private String MONEY7;
    private String MONEY8;
    private String FISCAL;
    private String FIS_PERD;
    private String STAD_AMT;//当月金额
    private String MONEY;//累计金额
    private String IS_LOWEST;

    public String getSTAD_AMT() {
        return STAD_AMT;
    }

    public void setSTAD_AMT(String STAD_AMT) {
        this.STAD_AMT = STAD_AMT;
    }

    public String getMONEY() {
        return MONEY;
    }

    public void setMONEY(String MONEY) {
        this.MONEY = MONEY;
    }

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

    public void setB_ACC_NAME(String c_ACC_NAME) {
        B_ACC_NAME = c_ACC_NAME;
    }

    public String getMONEY1() {
        return MONEY1;
    }

    public void setMONEY1(String MONEY1) {
        this.MONEY1 = MONEY1;
    }

    public String getMONEY2() {
        return MONEY2;
    }

    public void setMONEY2(String MONEY2) {
        this.MONEY2 = MONEY2;
    }

    public String getMONEY3() {
        return MONEY3;
    }

    public void setMONEY3(String MONEY3) {
        this.MONEY3 = MONEY3;
    }

    public String getMONEY4() {
        return MONEY4;
    }

    public void setMONEY4(String MONEY4) {
        this.MONEY4 = MONEY4;
    }

    public String getMONEY5() {
        return MONEY5;
    }

    public void setMONEY5(String MONEY5) {
        this.MONEY5 = MONEY5;
    }

    public String getMONEY6() {
        return MONEY6;
    }

    public void setMONEY6(String MONEY6) {
        this.MONEY6 = MONEY6;
    }

    public String getPLAN() {
        return PLAN;
    }

    public void setPLAN(String PLAN) {
        this.PLAN = PLAN;
    }

    public String getMONEY7() {
        return MONEY7;
    }

    public void setMONEY7(String MONEY7) {
        this.MONEY7 = MONEY7;
    }

    public String getMONEY8() {
        return MONEY8;
    }

    public void setMONEY8(String MONEY8) {
        this.MONEY8 = MONEY8;
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

    public String getIS_LOWEST() {
        return IS_LOWEST;
    }

    public void setIS_LOWEST(String IS_LOWEST) {
        this.IS_LOWEST = IS_LOWEST;
    }
}