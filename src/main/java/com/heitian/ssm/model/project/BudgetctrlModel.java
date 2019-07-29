package com.heitian.ssm.model.project;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-11 20:29
 **/
public class BudgetctrlModel {


    private String SET_YEAR;

    private String PRJCODE;
    private String PROGRAMNAME;
    private String PROGRAMTYPEGUID;
    private String PROGRAMTYPENAME;
    private String ENTERPRISEGUID;

    private String FUNCTIONGUID;//功能科目
    private String FUNCTIONNAME;//

    private String ECONOMYSECTIONGUID;//部门科目
    private String ECONOMYSECTIONNAME;//

    private String RESOURCEGUID;//资金来源
    private String RESOURCENAME;
    private String RESOURCECODE;

    private String BUDGETCATEGORYGUID;
    private String BUDGETCATEGORYNAME;


    private String DIVISIONGUID;
    private String DIVISIONNAME;//科室

    private String MONEY;

    private String GUID;

    public String ENTERPRISENAME;

    public int PNO;

    public String OPERATORGUID;

    public String XMK_ECOGOVCODE;

    public String ECOGOVGUID;

    public String ECOGOVINCODE;

    public String ECOGOVNAME;


    public String getECOGOVGUID() {
        return ECOGOVGUID;
    }

    public void setECOGOVGUID(String ECOGOVGUID) {
        this.ECOGOVGUID = ECOGOVGUID;
    }

    public String getECOGOVINCODE() {
        return ECOGOVINCODE;
    }

    public void setECOGOVINCODE(String ECOGOVINCODE) {
        this.ECOGOVINCODE = ECOGOVINCODE;
    }

    public String getECOGOVNAME() {
        return ECOGOVNAME;
    }

    public void setECOGOVNAME(String ECOGOVNAME) {
        this.ECOGOVNAME = ECOGOVNAME;
    }

    public String getXMK_ECOGOVCODE() {
        return XMK_ECOGOVCODE;
    }

    public void setXMK_ECOGOVCODE(String XMK_ECOGOVCODE) {
        this.XMK_ECOGOVCODE = XMK_ECOGOVCODE;
    }

    public String getOPERATORGUID() {
        return OPERATORGUID;
    }

    public void setOPERATORGUID(String OPERATORGUID) {
        this.OPERATORGUID = OPERATORGUID;
    }


    public int getPNO() {
        return PNO;
    }

    public void setPNO(int PNO) {
        this.PNO = PNO;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getPRJCODE() {
        return PRJCODE;
    }

    public void setPRJCODE(String PRJCODE) {
        this.PRJCODE = PRJCODE;
    }

    public String getBUDGETCATEGORYGUID() {
        return BUDGETCATEGORYGUID;
    }

    public void setBUDGETCATEGORYGUID(String BUDGETCATEGORYGUID) {
        this.BUDGETCATEGORYGUID = BUDGETCATEGORYGUID;
    }

    public String getBUDGETCATEGORYNAME() {
        return BUDGETCATEGORYNAME;
    }

    public void setBUDGETCATEGORYNAME(String BUDGETCATEGORYNAME) {
        this.BUDGETCATEGORYNAME = BUDGETCATEGORYNAME;
    }

    public String getDIVISIONGUID() {
        return DIVISIONGUID;
    }

    public void setDIVISIONGUID(String DIVISIONGUID) {
        this.DIVISIONGUID = DIVISIONGUID;
    }

    public String getDIVISIONNAME() {
        return DIVISIONNAME;
    }

    public void setDIVISIONNAME(String DIVISIONNAME) {
        this.DIVISIONNAME = DIVISIONNAME;
    }

    public String getENTERPRISENAME() {
        return ENTERPRISENAME;
    }

    public void setENTERPRISENAME(String ENTERPRISENAME) {
        this.ENTERPRISENAME = ENTERPRISENAME;
    }

    public String getENTERPRISEGUID() {
        return ENTERPRISEGUID;
    }

    public void setENTERPRISEGUID(String ENTERPRISEGUID) {
        this.ENTERPRISEGUID = ENTERPRISEGUID;
    }

    public String getSET_YEAR() {
        return SET_YEAR;
    }

    public void setSET_YEAR(String SET_YEAR) {
        this.SET_YEAR = SET_YEAR;
    }

    public String getMONEY() {
        return MONEY;
    }

    public void setMONEY(String MONEY) {
        this.MONEY = MONEY;
    }

    public String getRESOURCEGUID() {
        return RESOURCEGUID;
    }

    public void setRESOURCEGUID(String RESOURCEGUID) {
        this.RESOURCEGUID = RESOURCEGUID;
    }

    public String getRESOURCENAME() {
        return RESOURCENAME;
    }

    public void setRESOURCENAME(String RESOURCENAME) {
        this.RESOURCENAME = RESOURCENAME;
    }

    public String getRESOURCECODE() {
        return RESOURCECODE;
    }

    public void setRESOURCECODE(String RESOURCECODE) {
        this.RESOURCECODE = RESOURCECODE;
    }

    public String getECONOMYSECTIONGUID() {
        return ECONOMYSECTIONGUID;
    }

    public void setECONOMYSECTIONGUID(String ECONOMYSECTIONGUID) {
        this.ECONOMYSECTIONGUID = ECONOMYSECTIONGUID;
    }

    public String getECONOMYSECTIONNAME() {
        return ECONOMYSECTIONNAME;
    }

    public void setECONOMYSECTIONNAME(String ECONOMYSECTIONNAME) {
        this.ECONOMYSECTIONNAME = ECONOMYSECTIONNAME;
    }

    public void setPROGRAMNAME(String PROGRAMNAME) {
        this.PROGRAMNAME = PROGRAMNAME;
    }

    public String getPROGRAMNAME() {
        return PROGRAMNAME;
    }

    public String getPROGRAMTYPEGUID() {
        return PROGRAMTYPEGUID;
    }

    public void setPROGRAMTYPEGUID(String PROGRAMTYPEGUID) {
        this.PROGRAMTYPEGUID = PROGRAMTYPEGUID;
    }

    public String getPROGRAMTYPENAME() {
        return PROGRAMTYPENAME;
    }

    public void setPROGRAMTYPENAME(String PROGRAMTYPENAME) {
        this.PROGRAMTYPENAME = PROGRAMTYPENAME;
    }

    public String getFUNCTIONNAME() {
        return FUNCTIONNAME;
    }

    public void setFUNCTIONNAME(String FUNCTIONNAME) {
        this.FUNCTIONNAME = FUNCTIONNAME;
    }

    public String getFUNCTIONGUID() {
        return FUNCTIONGUID;
    }

    public void setFUNCTIONGUID(String FUNCTIONGUID) {
        this.FUNCTIONGUID = FUNCTIONGUID;
    }
}
