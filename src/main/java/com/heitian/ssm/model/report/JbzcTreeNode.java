package com.heitian.ssm.model.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JbzcTreeNode extends HashMap<String,Object> implements Serializable {
    private String MONEY;
    private String GOV_OUTLAY_CODE;
    private String GOV_OUTLAY_NAME;
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

    private List<JbzcTreeNode> children;

    public JbzcTreeNode() {
    }

    public JbzcTreeNode(String MONEY, String GOV_OUTLAY_CODE, String GOV_OUTLAY_NAME, String IS_LOWEST,String FISCAL,String FIS_PERD,String CO_CODE,String ID,String ISDISABLED,String SETID,String STAD_AMT,String XZ_STAD_AMT,String QCZ_STAD_AMT) {
        super();
        this.MONEY = MONEY;
        this.GOV_OUTLAY_CODE = GOV_OUTLAY_CODE;
        this.GOV_OUTLAY_NAME = GOV_OUTLAY_NAME;
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

    public String getGOV_OUTLAY_CODE() {
        return GOV_OUTLAY_CODE;
    }

    public void setGOV_OUTLAY_CODE(String GOV_OUTLAY_CODE) {
        this.GOV_OUTLAY_CODE = GOV_OUTLAY_CODE;
    }

    public String getGOV_OUTLAY_NAME() {
        return GOV_OUTLAY_NAME;
    }

    public void setGOV_OUTLAY_NAME(String GOV_OUTLAY_NAME) {
        this.GOV_OUTLAY_NAME = GOV_OUTLAY_NAME;
    }

    public List<JbzcTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<JbzcTreeNode> children) {
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
