package com.heitian.ssm.model;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-12 20:56
 **/
public class QueryModel {

    private String FILENO;
    private String CXZPROGID;
    private String CXZPROGNAME;
    private String CPROGRAMNAME;

    public String getCXZPROGID() {
        return CXZPROGID;
    }

    public void setCXZPROGID(String CXZPROGID) {
        this.CXZPROGID = CXZPROGID;
    }

    public String getCPROGRAMNAME() {
        return CPROGRAMNAME;
    }

    public void setCPROGRAMNAME(String CPROGRAMNAME) {
        this.CPROGRAMNAME = CPROGRAMNAME;
    }

    public String getFILENO() {
        return FILENO;
    }

    public void setFILENO(String FILENO) {
        this.FILENO = FILENO;
    }

    public String getCXZPROGNAME() {
        return CXZPROGNAME;
    }

    public void setCXZPROGNAME(String CXZPROGNAME) {
        this.CXZPROGNAME = CXZPROGNAME;
    }
}
