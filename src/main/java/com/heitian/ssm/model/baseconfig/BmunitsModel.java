package com.heitian.ssm.model.baseconfig;

/**
 * ClassName:BmunitsModel
 * Package:com.heitian.ssm.model
 * Description:
 * author:@Fay
 * Date:2019/3/12 0012上午 10:56
 */
public class BmunitsModel  {

    private String CBMUNITID;
    private String CBMUNITNAME;
    private String URL;
    private String URL_NAME;
    private String URL_PWD;
    private String SID;
    private String CMEMO;
    private String SHORTNAME;
    private String SOURCE;
    private String CPAYURL;
    private String KJHSURL;
    private String CO_CODE;
    private String GOVCO_CODE;
    private String FINANCELEADER;



    public String getGOVCO_CODE() {
        return GOVCO_CODE;
    }

    public void setGOVCO_CODE(String GOVCO_CODE) {
        this.GOVCO_CODE = GOVCO_CODE;
    }

    public String getCO_CODE() {
        return CO_CODE;
    }

    public void setCO_CODE(String CO_CODE) {
        this.CO_CODE = CO_CODE;
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

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getURL_NAME() {
        return URL_NAME;
    }

    public void setURL_NAME(String URL_NAME) {
        this.URL_NAME = URL_NAME;
    }

    public String getURL_PWD() {
        return URL_PWD;
    }

    public void setURL_PWD(String URL_PWD) {
        this.URL_PWD = URL_PWD;
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public String getCMEMO() {
        return CMEMO;
    }

    public void setCMEMO(String CMEMO) {
        this.CMEMO = CMEMO;
    }

    public String getSHORTNAME() {
        return SHORTNAME;
    }

    public void setSHORTNAME(String SHORTNAME) {
        this.SHORTNAME = SHORTNAME;
    }

    public String getSOURCE() {
        return SOURCE;
    }

    public void setSOURCE(String SOURCE) {
        this.SOURCE = SOURCE;
    }

    public String getCPAYURL() {
        return CPAYURL;
    }

    public void setCPAYURL(String CPAYURL) {
        this.CPAYURL = CPAYURL;
    }

    public String getKJHSURL() {
        return KJHSURL;
    }

    public void setKJHSURL(String KJHSURL) {
        this.KJHSURL = KJHSURL;
    }

    public String getFINANCELEADER() {
        return FINANCELEADER;
    }

    public void setFINANCELEADER(String FINANCELEADER) {
        this.FINANCELEADER = FINANCELEADER;
    }
}
