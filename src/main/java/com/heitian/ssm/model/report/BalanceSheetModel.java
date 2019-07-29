package com.heitian.ssm.model.report;

/**
 * ClassName:BalanceSheetModel
 * Package:com.heitian.ssm.model
 * Description:
 * author:@Fay
 * Date:2019/4/313:58
 */
public class BalanceSheetModel {
    private String ACC_CODE;
    private String ACC_NAME;
    private String ACC_TYPE;
    private String CO_CODE;
    private String FISCAL;
    private String FIS_PERD;
    private String MONEY1;//年初数
    private String MONEY2;//累计数
    private String MONEY3;//年末数

    public String getACC_TYPE() {
        return ACC_TYPE;
    }

    public void setACC_TYPE(String ACC_TYPE) {
        this.ACC_TYPE = ACC_TYPE;
    }

    public String getACC_CODE() {
        return ACC_CODE;
    }

    public void setACC_CODE(String ACC_CODE) {
        this.ACC_CODE = ACC_CODE;
    }

    public String getACC_NAME() {
        return ACC_NAME;
    }

    public void setACC_NAME(String ACC_NAME) {
        this.ACC_NAME = ACC_NAME;
    }

    public String getCO_CODE() {
        return CO_CODE;
    }

    public void setCO_CODE(String CO_CODE) {
        this.CO_CODE = CO_CODE;
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
}
