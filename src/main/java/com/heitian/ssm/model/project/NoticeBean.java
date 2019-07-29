package com.heitian.ssm.model.project;

/**
 * @program: CapitalAdmin
 * @description: 公告单bean
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-07 10:19
 **/
public class NoticeBean {


    private String CXZPROGID;
    private String CXZPROGNAME;
    private String CANNOUNCETYPE;
    private String START_DATE;
    private String END_DATE;
    private String CBMUNITID;
    private String CBMUNITNAME;
    private String BANNOUNCEMENTDOC;
    private String GDATE;
    private String YEAR;
    private String MONTH;
    private String PNO;
    private String NOTICE_STATE;

    


    public String getNOTICE_STATE() {
        return NOTICE_STATE;
    }

    public void setNOTICE_STATE(String NOTICE_STATE) {
        this.NOTICE_STATE = NOTICE_STATE;
    }

    public String getCXZPROGID() {
        return CXZPROGID;
    }

    public void setCXZPROGID(String CXZPROGID) {
        this.CXZPROGID = CXZPROGID;
    }

    public String getCXZPROGNAME() {
        return CXZPROGNAME;
    }

    public void setCXZPROGNAME(String CXZPROGNAME) {
        this.CXZPROGNAME = CXZPROGNAME;
    }

    public String getCANNOUNCETYPE() {
        return CANNOUNCETYPE;
    }

    public void setCANNOUNCETYPE(String CANNOUNCETYPE) {
        this.CANNOUNCETYPE = CANNOUNCETYPE;
    }

    public String getSTART_DATE() {
        return START_DATE;
    }

    public void setSTART_DATE(String START_DATE) {
        this.START_DATE = START_DATE;
    }

    public String getEND_DATE() {
        return END_DATE;
    }

    public void setEND_DATE(String END_DATE) {
        this.END_DATE = END_DATE;
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

    public String getBANNOUNCEMENTDOC() {
        return BANNOUNCEMENTDOC;
    }

    public void setBANNOUNCEMENTDOC(String BANNOUNCEMENTDOC) {
        this.BANNOUNCEMENTDOC = BANNOUNCEMENTDOC;
    }

    public String getGDATE() {
        return GDATE;
    }

    public void setGDATE(String GDATE) {
        this.GDATE = GDATE;
    }

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }

    public String getMONTH() {
        return MONTH;
    }

    public void setMONTH(String MONTH) {
        this.MONTH = MONTH;
    }

    public String getPNO() {
        return PNO;
    }

    public void setPNO(String PNO) {
        this.PNO = PNO;
    }
}
