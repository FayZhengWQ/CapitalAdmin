package com.heitian.ssm.model.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName:EarlyYearTreeNode
 * Package:com.heitian.ssm.model.report
 * Description:
 * author:@Fay
 * Date:2019/7/1013:11
 */
public class EarlyYearTreeNode extends HashMap<String,Object> implements Serializable {

    private String CO_CODE ;
    private String B_ACC_CODE;
    private String B_ACC_NAME;
    private String MONEY ;
    private String FISCAL ;
    private List<EarlyYearTreeNode> children;

    public EarlyYearTreeNode(String CO_CODE, String B_ACC_CODE, String B_ACC_NAME, String MONEY, String FISCAL) {
        super();
        this.CO_CODE = CO_CODE;
        this.B_ACC_CODE = B_ACC_CODE;
        this.B_ACC_NAME = B_ACC_NAME;
        this.MONEY = MONEY;
        this.FISCAL = FISCAL;
        this.children = new ArrayList<>();
        super.put("children",children);
    }

    public String getCO_CODE() {
        return CO_CODE;
    }

    public void setCO_CODE(String CO_CODE) {
        this.CO_CODE = CO_CODE;
    }

    public String getB_ACC_CODE() {
        return B_ACC_CODE;
    }

    public void setB_ACC_CODE(String b_ACC_CODE) {
        B_ACC_CODE = b_ACC_CODE;
    }

    public String getB_ACC_NAME() {
        return B_ACC_NAME;
    }

    public void setB_ACC_NAME(String b_ACC_NAME) {
        B_ACC_NAME = b_ACC_NAME;
    }

    public String getMONEY() {
        return MONEY;
    }

    public void setMONEY(String MONEY) {
        this.MONEY = MONEY;
    }

    public String getFISCAL() {
        return FISCAL;
    }

    public void setFISCAL(String FISCAL) {
        this.FISCAL = FISCAL;
    }

    public List<EarlyYearTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<EarlyYearTreeNode> children) {
        this.children = children;
        super.put("children",children);
    }
}
