package com.heitian.ssm.model;

/**
 * Author by 哼唧
 * Date on 2018/9/27.下午2:34
 * today is beatuful day
 */
public class JczlBean {

    private String GUID;
    private String IN_CODE;
    private String ISBN_CODE;
    private String CODE;
    private String LEVEL_NUM;
    private String NAME;
    private String PARENT_GUID;
    private String JCZLNAME;
    private String ECOGOVGUID;

    public String getECOGOVGUID() {
        return ECOGOVGUID;
    }

    public void setECOGOVGUID(String ECOGOVGUID) {
        this.ECOGOVGUID = ECOGOVGUID;
    }

    public String getJCZLNAME() {
        return getCODE()+" "+getNAME();
    }

    public void setJCZLNAME(String JCZLNAME) {
        this.JCZLNAME = JCZLNAME;
    }

    public String getPARENT_GUID() {
        return PARENT_GUID;
    }

    public void setPARENT_GUID(String PARENT_GUID) {
        this.PARENT_GUID = PARENT_GUID;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getIN_CODE() {
        return IN_CODE;
    }

    public void setIN_CODE(String IN_CODE) {
        this.IN_CODE = IN_CODE;
    }

    public String getISBN_CODE() {
        return ISBN_CODE;
    }

    public void setISBN_CODE(String ISBN_CODE) {
        this.ISBN_CODE = ISBN_CODE;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getLEVEL_NUM() {
        return LEVEL_NUM;
    }

    public void setLEVEL_NUM(String LEVEL_NUM) {
        this.LEVEL_NUM = LEVEL_NUM;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
