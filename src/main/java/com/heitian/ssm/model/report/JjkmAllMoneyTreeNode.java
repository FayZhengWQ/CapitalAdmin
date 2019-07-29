package com.heitian.ssm.model.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:GnkmAllTreeNode
 * Package:com.heitian.ssm.model.report
 * Description:
 * author:@Fay
 * Date:2019/7/1410:09
 */
public class JjkmAllMoneyTreeNode extends HashMap<String,Object> implements Serializable {
    private  String OUTLAY_CODE;
    private  String OUTLAY_NAME;
    private  String ALLMONEY;
    private  String ID;
    private  String MONEY1;
    private  String MONEY2;
    private  String MONEY3;
    private  String MONEY4;
    private  String MONEY5;
    private  String MONEY6;
    private  String MONEY7;
    private  String MONEY8;
    private  String MONEY9;
    private  String MONEY10;
    private  String MONEY11;
    private  String MONEY12;
    private  String MONEY13;
    private  String MONEY14;
    private  String MONEY15;
    private  String MONEY16;
    private List<JjkmAllMoneyTreeNode> children;

    public JjkmAllMoneyTreeNode(String OUTLAY_CODE, String OUTLAY_NAME, String ALLMONEY,String ID, String MONEY1, String MONEY2, String MONEY3, String MONEY4, String MONEY5, String MONEY6, String MONEY7, String MONEY8, String MONEY9, String MONEY10, String MONEY11, String MONEY12, String MONEY13, String MONEY14, String MONEY15, String MONEY16) {
        super();
        this.OUTLAY_CODE = OUTLAY_CODE;
        this.OUTLAY_NAME = OUTLAY_NAME;
        this.ALLMONEY = ALLMONEY;
        this.ID = ID;
        this.MONEY1 = MONEY1;
        this.MONEY2 = MONEY2;
        this.MONEY3 = MONEY3;
        this.MONEY4 = MONEY4;
        this.MONEY5 = MONEY5;
        this.MONEY6 = MONEY6;
        this.MONEY7 = MONEY7;
        this.MONEY8 = MONEY8;
        this.MONEY9 = MONEY9;
        this.MONEY10 = MONEY10;
        this.MONEY11 = MONEY11;
        this.MONEY12 = MONEY12;
        this.MONEY13 = MONEY13;
        this.MONEY14 = MONEY14;
        this.MONEY15 = MONEY15;
        this.MONEY16 = MONEY16;
        this.children = new ArrayList<>();
        super.put("children",children);

    }

    public String getOUTLAY_CODE() {
        return OUTLAY_CODE;
    }

    public void setOUTLAY_CODE(String OUTLAY_CODE) {
        this.OUTLAY_CODE = OUTLAY_CODE;
    }

    public String getOUTLAY_NAME() {
        return OUTLAY_NAME;
    }

    public void setOUTLAY_NAME(String OUTLAY_NAME) {
        this.OUTLAY_NAME = OUTLAY_NAME;
    }

    public String getALLMONEY() {
        return ALLMONEY;
    }

    public void setALLMONEY(String ALLMONEY) {
        this.ALLMONEY = ALLMONEY;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMONEY1() {
        return MONEY1;
    }

    public void setMONEY1(String MONEY1) {
        this.MONEY1 = MONEY1;
    }

    public String getMONEY2() {
        return MONEY2;
    }

    public void setMONEY2(String MONEY2) {
        this.MONEY2 = MONEY2;
    }

    public String getMONEY3() {
        return MONEY3;
    }

    public void setMONEY3(String MONEY3) {
        this.MONEY3 = MONEY3;
    }

    public String getMONEY4() {
        return MONEY4;
    }

    public void setMONEY4(String MONEY4) {
        this.MONEY4 = MONEY4;
    }

    public String getMONEY5() {
        return MONEY5;
    }

    public void setMONEY5(String MONEY5) {
        this.MONEY5 = MONEY5;
    }

    public String getMONEY6() {
        return MONEY6;
    }

    public void setMONEY6(String MONEY6) {
        this.MONEY6 = MONEY6;
    }

    public String getMONEY7() {
        return MONEY7;
    }

    public void setMONEY7(String MONEY7) {
        this.MONEY7 = MONEY7;
    }

    public String getMONEY8() {
        return MONEY8;
    }

    public void setMONEY8(String MONEY8) {
        this.MONEY8 = MONEY8;
    }

    public String getMONEY9() {
        return MONEY9;
    }

    public void setMONEY9(String MONEY9) {
        this.MONEY9 = MONEY9;
    }

    public String getMONEY10() {
        return MONEY10;
    }

    public void setMONEY10(String MONEY10) {
        this.MONEY10 = MONEY10;
    }

    public String getMONEY11() {
        return MONEY11;
    }

    public void setMONEY11(String MONEY11) {
        this.MONEY11 = MONEY11;
    }

    public String getMONEY12() {
        return MONEY12;
    }

    public void setMONEY12(String MONEY12) {
        this.MONEY12 = MONEY12;
    }

    public String getMONEY13() {
        return MONEY13;
    }

    public void setMONEY13(String MONEY13) {
        this.MONEY13 = MONEY13;
    }

    public String getMONEY14() {
        return MONEY14;
    }

    public void setMONEY14(String MONEY14) {
        this.MONEY14 = MONEY14;
    }

    public String getMONEY15() {
        return MONEY15;
    }

    public void setMONEY15(String MONEY15) {
        this.MONEY15 = MONEY15;
    }

    public String getMONEY16() {
        return MONEY16;
    }

    public void setMONEY16(String MONEY16) {
        this.MONEY16 = MONEY16;
    }

    public List<JjkmAllMoneyTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<JjkmAllMoneyTreeNode> children) {
        this.children = children;
        super.put("children",children);
    }
}
