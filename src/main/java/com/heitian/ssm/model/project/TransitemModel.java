package com.heitian.ssm.model.project;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-12-20 09:36
 **/
public class TransitemModel {


    private String IYEAR;
    private String FILENO;
    private String DIVISIONGUID;
    private String DIVISIONNAME;
    private String CBUDGETCATEGORYGUID;
    private String OPERATORGUID;
    private String SUMMARY;
    private int PNO;
    private String ISSOURCE;
    private String url;
    private String url_NAME;
    private String url_PWD;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl_NAME() {
        return url_NAME;
    }

    public void setUrl_NAME(String url_NAME) {
        this.url_NAME = url_NAME;
    }

    public String getUrl_PWD() {
        return url_PWD;
    }

    public void setUrl_PWD(String url_PWD) {
        this.url_PWD = url_PWD;
    }

    public String getIYEAR() {
        return IYEAR;
    }

    public void setIYEAR(String IYEAR) {
        this.IYEAR = IYEAR;
    }

    public String getSUMMARY() {
        return SUMMARY;
    }

    public void setSUMMARY(String SUMMARY) {
        this.SUMMARY = SUMMARY;
    }

    public String getFILENO() {
        return FILENO;
    }

    public void setFILENO(String FILENO) {
        this.FILENO = FILENO;
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

    public String getCBUDGETCATEGORYGUID() {
        return CBUDGETCATEGORYGUID;
    }

    public void setCBUDGETCATEGORYGUID(String CBUDGETCATEGORYGUID) {
        this.CBUDGETCATEGORYGUID = CBUDGETCATEGORYGUID;
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

    public String getISSOURCE() {
        return ISSOURCE;
    }

    public void setISSOURCE(String ISSOURCE) {
        this.ISSOURCE = ISSOURCE;
    }
}
