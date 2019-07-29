package com.heitian.ssm.model.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GnkmTreeNode extends HashMap<String,Object> implements Serializable {
    private String MONEY;
    private String B_ACC_CODE;
    private String B_ACC_NAME;
    private String IS_LOWEST;
    private String FISCAL;
    private String FIS_PERD;
    private String CO_CODE;
    private String ID;
    private String ISDISABLED;
    private String SETID;
    private String STAD_AMT;
    private String XZ_STAD_AMT;
    private String QCZ_STAD_AMT;
    private List<GnkmTreeNode> children;

    public GnkmTreeNode() {
    }

    public GnkmTreeNode(String MONEY, String B_ACC_CODE, String B_ACC_NAME, String IS_LOWEST,String FISCAL,String FIS_PERD,String CO_CODE,String ID,String ISDISABLED,String SETID,String STAD_AMT,String XZ_STAD_AMT,String QCZ_STAD_AMT) {
        super();
        this.MONEY = MONEY;
        this.B_ACC_CODE = B_ACC_CODE;
        this.B_ACC_NAME = B_ACC_NAME;
        this.IS_LOWEST = IS_LOWEST;
        this.FISCAL = FISCAL;
        this.FIS_PERD = FIS_PERD;
        this.CO_CODE = CO_CODE;
        this.ID = ID;
        this.ISDISABLED = ISDISABLED;
        this.SETID = SETID;
        this.STAD_AMT = STAD_AMT;
        this.XZ_STAD_AMT = XZ_STAD_AMT;
        this.QCZ_STAD_AMT = QCZ_STAD_AMT;
        this.children = new ArrayList<>();
        super.put("children",children);
    }

    public String getMONEY() {
        return MONEY;
    }

    public void setMONEY(String MONEY) {
        this.MONEY = MONEY;
    }

    public String getB_ACC_CODE() {
        return B_ACC_CODE;
    }

    public void setB_ACC_CODE(String b_ACC_CODE) {
        this.B_ACC_CODE = b_ACC_CODE;
    }

    public String getB_ACC_NAME() {
        return B_ACC_NAME;
    }

    public void setB_ACC_NAME(String b_ACC_NAME) {
        this.B_ACC_NAME = b_ACC_NAME;
    }

    public List<GnkmTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<GnkmTreeNode> children) {
        this.children = children;
        super.put("children",children);
    }

    public String getIS_LOWEST() {
        return IS_LOWEST;
    }

    public void setIS_LOWEST(String IS_LOWEST) {
        this.IS_LOWEST = IS_LOWEST;
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

    public String getCO_CODE() {
        return CO_CODE;
    }

    public void setCO_CODE(String CO_CODE) {
        this.CO_CODE = CO_CODE;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getISDISABLED() {
        return ISDISABLED;
    }

    public void setISDISABLED(String ISDISABLED) {
        this.ISDISABLED = ISDISABLED;
    }

    public String getSETID() {
        return SETID;
    }

    public void setSETID(String SETID) {
        this.SETID = SETID;
    }

    public String getSTAD_AMT() {
        return STAD_AMT;
    }

    public void setSTAD_AMT(String STAD_AMT) {
        this.STAD_AMT = STAD_AMT;
    }

    public String getXZ_STAD_AMT() {
        return XZ_STAD_AMT;
    }

    public void setXZ_STAD_AMT(String XZ_STAD_AMT) {
        this.XZ_STAD_AMT = XZ_STAD_AMT;
    }

    public String getQCZ_STAD_AMT() {
        return QCZ_STAD_AMT;
    }

    public void setQCZ_STAD_AMT(String QCZ_STAD_AMT) {
        this.QCZ_STAD_AMT = QCZ_STAD_AMT;
    }
}
