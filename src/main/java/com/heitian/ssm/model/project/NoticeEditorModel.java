package com.heitian.ssm.model.project;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-16 03:32
 **/
public class NoticeEditorModel {

    private String CXZPROGID;
    private String CXZPROGNAME;
    private String CBMUNITID;
    private String PNO;
    private String FILENO;
    private String CUNITNAME;
    private String START_DATE;
    private String END_DATE;
    private String BANNOUNCEMENTDOC;
    private String CBMUNITNAME;
    private String YEAR;

    public String getPNO() {
        return PNO;
    }

    public void setPNO(String PNO) {
        this.PNO = PNO;
    }

    public String getCBMUNITNAME() {
        return CBMUNITNAME;
    }

    public void setCBMUNITNAME(String CBMUNITNAME) {
        this.CBMUNITNAME = CBMUNITNAME;
    }

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
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

    public String getCBMUNITID() {
        return CBMUNITID;
    }

    public void setCBMUNITID(String CBMUNITID) {
        this.CBMUNITID = CBMUNITID;
    }

    public String getFILENO() {
        return FILENO;
    }

    public void setFILENO(String FILENO) {
        this.FILENO = FILENO;
    }

    public String getCUNITNAME() {
        return CUNITNAME;
    }

    public void setCUNITNAME(String CUNITNAME) {
        this.CUNITNAME = CUNITNAME;
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

    public String getBANNOUNCEMENTDOC() {
        return BANNOUNCEMENTDOC;
    }

    public void setBANNOUNCEMENTDOC(String BANNOUNCEMENTDOC) {
        this.BANNOUNCEMENTDOC = BANNOUNCEMENTDOC;
    }
}
