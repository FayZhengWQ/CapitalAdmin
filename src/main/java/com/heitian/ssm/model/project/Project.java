package com.heitian.ssm.model.project;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-10-30 11:23
 **/

public class Project {

    private  String ROWNUM;

    private String RN;

    private String SIGNED;

    private String CXZPROGID;
    private String CXZPROGNAME;

    private String CPROGRAMNAME;  //项目名称
    private String FILENO;//文号


    private String CUNITID;//单位编码
    private String CUNITNAME;//单位名称

    private String CENTERPRISE;//单位
    private String CENTERPRISECODE;//单位编码
    private String CENTERPRISENAME;//单位名称

    private String CFUNCTION;//功能科目
    private String CFUNCTIONCODE;//功能科目编码
    private String CFUNCTIONNAME;//功能科目名称

    private String CECONOMYSECTION;//部门经济科目
    private String CECONOMYSECTIONCODE;//部门经济科目编码
    private String CECONOMYSECTIONNAME;//部门经济科目名称

    private String CECONOMYSECTIONGOV;//政府经济科目
    private String CECONOMYSECTIONGOVCODE;//政府经济科目编码
    private String CECONOMYSECTIONGOVNAME;//政府经济科目名称

    private String CBUDGETCATEGORY;//预算指标
    private String CBUDGETCATEGORYCODE;//预算指标类型
    private String CBUDGETCATEGORYNAME;//预算指标名称

    private String CRESOURCE;//资金来源
    private String CRESOURCECODE;//资金来源编码
    private String CRESOURCENAME;//资金来源名称

    private String SZTYPE;//收支类型
    private String SZTYPECODE;//收支编码
    private String SZTYPENAME;//收支名称
    private String IMONEY;//金额
    private String CPROGTYPE;//项目类型

    private String NOTICE_STATE;
    private String TRANSFER_STATE;

    private String key;
    private String DDATE;
    private String ENTERPRISEID;

    private String CPROGRAMTYPE;
    private String CPROGRAMTYPECODE;

    private String CPROGRAMTYPENAME;

    private String CMEMO;

    private String IMONEY1;//已结转
    private String IMONEY2;//为结转

    private String CHECK_STATE;
    private String type;
    private String CBMUNITID;

    private String DGKKS;

    private String DIVISIONGUID;
    private String DIVISIONNAME;
    private String GDATE;
    private String PAY_STATE;
    private String CBMUNITNAME;
    private String YEAR;
    private String MONTH;
    private String IYEAR;
    private String IMONTH;
    private String IGPLANID;
    private String CBILLNO;
    private String table;
    private int PNO;
    private String SHORTNAME;
    private String SHORTNAME1;
    private String SHORTNAME0;

    public String getSHORTNAME0() {
        return SHORTNAME0;
    }

    public void setSHORTNAME0(String SHORTNAME0) {
        this.SHORTNAME0 = SHORTNAME0;
    }

    public String getSHORTNAME1() {
        return SHORTNAME1;
    }

    public void setSHORTNAME1(String SHORTNAME1) {
        this.SHORTNAME1 = SHORTNAME1;
    }

    public String getSHORTNAME() {
        return SHORTNAME;
    }

    public void setSHORTNAME(String SHORTNAME) {
        this.SHORTNAME = SHORTNAME;
    }

    private String  CXZENTERPRISEID;


    public String getCXZENTERPRISEID() {
        return CXZENTERPRISEID;
    }

    public void setCXZENTERPRISEID(String CXZENTERPRISEID) {
        this.CXZENTERPRISEID = CXZENTERPRISEID;
    }

    public int getPNO() {
        return PNO;
    }

    public void setPNO(int PNO) {
        this.PNO = PNO;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getIGPLANID() {
        return IGPLANID;
    }

    public void setIGPLANID(String IGPLANID) {
        this.IGPLANID = IGPLANID;
    }

    public String getCBILLNO() {
        return CBILLNO;
    }

    public void setCBILLNO(String CBILLNO) {
        this.CBILLNO = CBILLNO;
    }

    public String getIMONTH() {
        return IMONTH;
    }

    public void setIMONTH(String IMONTH) {
        this.IMONTH = IMONTH;
    }

    public String getIYEAR() {
        return IYEAR;
    }

    public void setIYEAR(String IYEAR) {
        this.IYEAR = IYEAR;
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

    public String getCBMUNITNAME() {
        return CBMUNITNAME;
    }

    public void setCBMUNITNAME(String CBMUNITNAME) {
        this.CBMUNITNAME = CBMUNITNAME;
    }

    public String getSIGNED() {
        return SIGNED;
    }

    public void setSIGNED(String SIGNED) {
        this.SIGNED = SIGNED;
    }


    public String getPAY_STATE() {
        return PAY_STATE;
    }

    public void setPAY_STATE(String PAY_STATE) {
        this.PAY_STATE = PAY_STATE;
    }

    public String getROWNUM() {
        return ROWNUM;
    }

    public void setROWNUM(String ROWNUM) {
        this.ROWNUM = ROWNUM;
    }



    public String getGDATE() {
        return GDATE;
    }

    public void setGDATE(String GDATE) {
        this.GDATE = GDATE;
    }

    public String getDIVISIONGUID() {
        return DIVISIONGUID;
    }

    public String getDIVISIONNAME() {
        return DIVISIONNAME;
    }

    public void setDIVISIONGUID(String DIVISIONGUID) {
        this.DIVISIONGUID = DIVISIONGUID;
    }

    public void setDIVISIONNAME(String DIVISIONNAME) {
        this.DIVISIONNAME = DIVISIONNAME;
    }

    public String getDGKKS() {
        return DGKKS;
    }

    public void setDGKKS(String DGKKS) {
        this.DGKKS = DGKKS;
    }

    public String getCBMUNITID() {
        return CBMUNITID;
    }

    public void setCBMUNITID(String CBMUNITID) {
        this.CBMUNITID = CBMUNITID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCHECK_STATE() {
        return CHECK_STATE;
    }

    public void setCHECK_STATE(String CHECK_STATE) {
        this.CHECK_STATE = CHECK_STATE;
    }

    public String getIMONEY1() {
        return IMONEY1;
    }

    public void setIMONEY1(String IMONEY1) {
        this.IMONEY1 = IMONEY1;
    }

    public String getIMONEY2() {
        return IMONEY2;
    }

    public void setIMONEY2(String IMONEY2) {
        this.IMONEY2 = IMONEY2;
    }

    public String getCMEMO() {
        return CMEMO;
    }

    public void setCMEMO(String CMEMO) {
        this.CMEMO = CMEMO;
    }

    public String getCPROGRAMTYPENAME() {
        return CPROGRAMTYPENAME;
    }

    public void setCPROGRAMTYPENAME(String CPROGRAMTYPENAME) {
        this.CPROGRAMTYPENAME = CPROGRAMTYPENAME;
    }

    public String getCPROGRAMTYPECODE() {
        return CPROGRAMTYPECODE;
    }

    public void setCPROGRAMTYPECODE(String CPROGRAMTYPECODE) {
        this.CPROGRAMTYPECODE = CPROGRAMTYPECODE;
    }

    public String getENTERPRISEID() {
        return ENTERPRISEID;
    }

    public void setENTERPRISEID(String ENTERPRISEID) {
        this.ENTERPRISEID = ENTERPRISEID;
    }

    public String getDDATE() {
        return DDATE;
    }

    public void setDDATE(String  DDATE) {
        this.DDATE = DDATE;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTRANSFER_STATE() {
        return TRANSFER_STATE;
    }

    public void setTRANSFER_STATE(String TRANSFER_STATE) {
        this.TRANSFER_STATE = TRANSFER_STATE;
    }

    public String getCPROGTYPE() {
        return CPROGTYPE;
    }

    public void setCPROGTYPE(String CPROGTYPE) {
        this.CPROGTYPE = CPROGTYPE;
    }

    public String getSZTYPECODE() {
        return SZTYPECODE;
    }

    public void setSZTYPECODE(String SZTYPECODE) {
        this.SZTYPECODE = SZTYPECODE;
    }

    public String getSZTYPENAME() {
        return SZTYPENAME;
    }

    public void setSZTYPENAME(String SZTYPENAME) {
        this.SZTYPENAME = SZTYPENAME;
    }

    public String getCUNITID() {
        return CUNITID;
    }

    public void setCUNITID(String CUNITID) {
        this.CUNITID = CUNITID;
    }

    public String getCUNITNAME() {
        return CUNITNAME;
    }

    public void setCUNITNAME(String CUNITNAME) {
        this.CUNITNAME = CUNITNAME;
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

    public String getNOTICE_STATE() {
        return NOTICE_STATE;
    }

    public void setNOTICE_STATE(String NOTICE_STATE) {
        this.NOTICE_STATE = NOTICE_STATE;
    }

    public String getCBUDGETCATEGORY() {
        return CBUDGETCATEGORY;
    }

    public void setCBUDGETCATEGORY(String CBUDGETCATEGORY) {
        this.CBUDGETCATEGORY = CBUDGETCATEGORY;
    }

    public String getCBUDGETCATEGORYCODE() {
        return CBUDGETCATEGORYCODE;
    }

    public void setCBUDGETCATEGORYCODE(String CBUDGETCATEGORYCODE) {
        this.CBUDGETCATEGORYCODE = CBUDGETCATEGORYCODE;
    }

    public String getCBUDGETCATEGORYNAME() {
        return CBUDGETCATEGORYNAME;
    }

    public void setCBUDGETCATEGORYNAME(String CBUDGETCATEGORYNAME) {
        this.CBUDGETCATEGORYNAME = CBUDGETCATEGORYNAME;
    }

    public String getCENTERPRISE() {
        return CENTERPRISE;
    }

    public void setCENTERPRISE(String CENTERPRISE) {
        this.CENTERPRISE = CENTERPRISE;
    }

    public String getCFUNCTION() {
        return CFUNCTION;
    }

    public void setCFUNCTION(String CFUNCTION) {
        this.CFUNCTION = CFUNCTION;
    }

    public String getCECONOMYSECTION() {
        return CECONOMYSECTION;
    }

    public void setCECONOMYSECTION(String CECONOMYSECTION) {
        this.CECONOMYSECTION = CECONOMYSECTION;
    }

    public String getCECONOMYSECTIONGOV() {
        return CECONOMYSECTIONGOV;
    }

    public void setCECONOMYSECTIONGOV(String CECONOMYSECTIONGOV) {
        this.CECONOMYSECTIONGOV = CECONOMYSECTIONGOV;
    }

    public String getCECONOMYSECTIONGOVCODE() {
        return CECONOMYSECTIONGOVCODE;
    }

    public void setCECONOMYSECTIONGOVCODE(String CECONOMYSECTIONGOVCODE) {
        this.CECONOMYSECTIONGOVCODE = CECONOMYSECTIONGOVCODE;
    }

    public String getCECONOMYSECTIONGOVNAME() {
        return CECONOMYSECTIONGOVNAME;
    }

    public void setCECONOMYSECTIONGOVNAME(String CECONOMYSECTIONGOVNAME) {
        this.CECONOMYSECTIONGOVNAME = CECONOMYSECTIONGOVNAME;
    }

    public String getCRESOURCE() {
        return CRESOURCE;
    }

    public void setCRESOURCE(String CRESOURCE) {
        this.CRESOURCE = CRESOURCE;
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

    public String getCENTERPRISECODE() {
        return CENTERPRISECODE;
    }

    public void setCENTERPRISECODE(String CENTERPRISECODE) {
        this.CENTERPRISECODE = CENTERPRISECODE;
    }

    public String getCENTERPRISENAME() {
        return CENTERPRISENAME;
    }

    public void setCENTERPRISENAME(String CENTERPRISENAME) {
        this.CENTERPRISENAME = CENTERPRISENAME;
    }

    public String getRN() {
        return RN;
    }

    public void setRN(String RN) {
        this.RN = RN;
    }

    public String getCFUNCTIONCODE() {
        return CFUNCTIONCODE;
    }

    public void setCFUNCTIONCODE(String CFUNCTIONCODE) {
        this.CFUNCTIONCODE = CFUNCTIONCODE;
    }

    public String getCFUNCTIONNAME() {
        return CFUNCTIONNAME;
    }

    public void setCFUNCTIONNAME(String CFUNCTIONNAME) {
        this.CFUNCTIONNAME = CFUNCTIONNAME;
    }

    public String getCECONOMYSECTIONCODE() {
        return CECONOMYSECTIONCODE;
    }

    public void setCECONOMYSECTIONCODE(String CECONOMYSECTIONCODE) {
        this.CECONOMYSECTIONCODE = CECONOMYSECTIONCODE;
    }

    public String getCECONOMYSECTIONNAME() {
        return CECONOMYSECTIONNAME;
    }

    public void setCECONOMYSECTIONNAME(String CECONOMYSECTIONNAME) {
        this.CECONOMYSECTIONNAME = CECONOMYSECTIONNAME;
    }

    public String getCRESOURCECODE() {
        return CRESOURCECODE;
    }



    public void setCRESOURCECODE(String CRESOURCECODE) {
        this.CRESOURCECODE = CRESOURCECODE;
    }

    public String getCRESOURCENAME() {
        return CRESOURCENAME;
    }

    public void setCRESOURCENAME(String CRESOURCENAME) {
        this.CRESOURCENAME = CRESOURCENAME;
    }

    public String getSZTYPE() {
        return SZTYPE;
    }

    public void setSZTYPE(String SZTYPE) {
        this.SZTYPE = SZTYPE;
    }

    public String getCPROGRAMTYPE() {
        return CPROGRAMTYPE;
    }

    public void setCPROGRAMTYPE(String CPROGRAMTYPE) {
        this.CPROGRAMTYPE = CPROGRAMTYPE;
    }

    public String getIMONEY() {
        return IMONEY;
    }

    public void setIMONEY(String IMONEY) {
        this.IMONEY = IMONEY;
    }
}
