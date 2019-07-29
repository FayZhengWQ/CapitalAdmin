package com.heitian.ssm.utils;

import com.heitian.ssm.model.report.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class FormatHelper {

    public static List<JjkmTreeNode> JjkmListToTreeByIsbnCode(List<Map<String, Object>> list) {
        List<JjkmTreeNode> result = new ArrayList<>();
        Stack<JjkmTreeNode> stack = new Stack<>();
        for (Map<String, Object> item : list) {
            if (!item.get("OUTLAY_NAME").toString().equals("合计数")) {
                JjkmTreeNode node = new JjkmTreeNode(item.get("MONEY") == null ? "" : item.get("MONEY").toString(), item.get("OUTLAY_CODE") == null ? "" : item.get("OUTLAY_CODE").toString(), item.get("OUTLAY_NAME").toString(), item.get("IS_LOWEST") == null ? "" : item.get("IS_LOWEST").toString(),
                        item.get("FISCAL").toString(), item.get("FIS_PERD").toString(), item.get("CO_CODE").toString(), item.get("ID").toString(), item.get("ISDISABLED").toString(), item.get("SETID").toString(), item.get("STAD_AMT") == null ? "" : item.get("STAD_AMT").toString(),
                        item.get("XZ_STAD_AMT") == null ? "" : item.get("XZ_STAD_AMT").toString(), item.get("QCZ_STAD_AMT") == null ? "" : item.get("QCZ_STAD_AMT").toString());
                node.putAll(item);
                if (stack.empty()) {
                    result.add(node);
                    stack.push(node);
                } else {
                    JjkmTreeNode lastElement = stack.lastElement();
                    if (node.getOUTLAY_CODE().matches(String.format("^%s.*+$", lastElement.getOUTLAY_CODE()))) {
                        lastElement.getChildren().add(node);
                        stack.push(node);
                    } else {
                        while (!node.getOUTLAY_CODE().matches(String.format("^%s.*+$", lastElement.getOUTLAY_CODE()))) {
                            stack.pop();
                            if (stack.empty()) break;
                            else lastElement = stack.lastElement();
                        }
                        if (stack.empty()) {
                            result.add(node);
                        } else {
                            lastElement.getChildren().add(node);
                        }
                        stack.push(node);
                    }
                }
            }

        }
        return result;
    }

    public static List<JbzcTreeNode> JbzcListToTreeByIsbnCode(List<Map<String, Object>> list) {
        List<JbzcTreeNode> result = new ArrayList<>();
        Stack<JbzcTreeNode> stack = new Stack<>();
        for (Map<String, Object> item : list) {
            if (!item.get("GOV_OUTLAY_NAME").toString().equals("合计数")) {
                JbzcTreeNode node = new JbzcTreeNode(item.get("MONEY") == null ? "" : item.get("MONEY").toString(), item.get("GOV_OUTLAY_CODE") == null ? "" : item.get("GOV_OUTLAY_CODE").toString(), item.get("GOV_OUTLAY_NAME").toString(), item.get("IS_LOWEST") == null ? "" : item.get("IS_LOWEST").toString(),
                        item.get("FISCAL").toString(), item.get("FIS_PERD").toString(), item.get("CO_CODE").toString(), item.get("ID").toString(), item.get("ISDISABLED").toString(), item.get("SETID").toString(), item.get("STAD_AMT") == null ? "" : item.get("STAD_AMT").toString(),
                        item.get("XZ_STAD_AMT") == null ? "" : item.get("XZ_STAD_AMT").toString(), item.get("QCZ_STAD_AMT") == null ? "" : item.get("QCZ_STAD_AMT").toString());
                node.putAll(item);
                if (stack.empty()) {
                    result.add(node);
                    stack.push(node);
                } else {
                    JbzcTreeNode lastElement = stack.lastElement();
                    if (node.getGOV_OUTLAY_CODE().matches(String.format("^%s.*+$", lastElement.getGOV_OUTLAY_CODE()))) {
                        lastElement.getChildren().add(node);
                        stack.push(node);
                    } else {
                        while (!node.getGOV_OUTLAY_CODE().matches(String.format("^%s.*+$", lastElement.getGOV_OUTLAY_CODE()))) {
                            stack.pop();
                            if (stack.empty()) break;
                            else lastElement = stack.lastElement();
                        }
                        if (stack.empty()) {
                            result.add(node);
                        } else {
                            lastElement.getChildren().add(node);
                        }
                        stack.push(node);
                    }
                }
            }

        }
        return result;
    }


    public static List<GnkmTreeNode> GnkmListToTreeByIsbnCode(List<Map<String, Object>> list) {
        List<GnkmTreeNode> result = new ArrayList<>();
        Stack<GnkmTreeNode> stack = new Stack<>();
        for (Map<String, Object> item : list) {
            if (!item.get("B_ACC_NAME").toString().equals("合计数")) {
                GnkmTreeNode node = new GnkmTreeNode(item.get("MONEY") == null ? "" : item.get("MONEY").toString(), item.get("B_ACC_CODE") == null ? "" : item.get("B_ACC_CODE").toString(), item.get("B_ACC_NAME").toString(), item.get("IS_LOWEST") == null ? "" : item.get("IS_LOWEST").toString(),
                        item.get("FISCAL")==null?"":item.get("FISCAL").toString(), item.get("FIS_PERD")==null?"":item.get("FIS_PERD").toString(), item.get("CO_CODE")==null?"":item.get("CO_CODE").toString(), item.get("ID")==null ?"":item.get("ID").toString(), item.get("ISDISABLED") == null ? "" : item.get("ISDISABLED").toString(),
                        item.get("SETID")==null?"":item.get("SETID").toString(), item.get("STAD_AMT") == null ? "" : item.get("STAD_AMT").toString(),
                        item.get("XZ_STAD_AMT") == null ? "" : item.get("XZ_STAD_AMT").toString(), item.get("QCZ_STAD_AMT") == null ? "" : item.get("QCZ_STAD_AMT").toString());
                node.putAll(item);
                if (stack.empty()) {
                    result.add(node);
                    stack.push(node);
                } else {
                    GnkmTreeNode lastElement = stack.lastElement();
                    if (node.getB_ACC_CODE().matches(String.format("^%s.*+$", lastElement.getB_ACC_CODE()))) {
                        lastElement.getChildren().add(node);
                        stack.push(node);
                    } else {
                        while (!node.getB_ACC_CODE().matches(String.format("^%s.*+$", lastElement.getB_ACC_CODE()))) {
                            stack.pop();
                            if (stack.empty()) break;
                            else lastElement = stack.lastElement();
                        }
                        if (stack.empty()) {
                            result.add(node);
                        } else {
                            lastElement.getChildren().add(node);
                        }
                        stack.push(node);
                    }
                }
            }

        }
        return result;
    }

    public static List<EarlyYearTreeNode> EarlyYearListToTreeByIsbnCode(List<Map<String, Object>> list) {
        List<EarlyYearTreeNode> result = new ArrayList<>();
        Stack<EarlyYearTreeNode> stack = new Stack<>();
        for (Map<String, Object> item : list) {
            if (!item.get("B_ACC_NAME").toString().equals("合计")) {
                EarlyYearTreeNode node = new EarlyYearTreeNode(item.get("CO_CODE").toString(), item.get("B_ACC_CODE").toString(),
                        item.get("B_ACC_NAME").toString(), item.get("MONEY") == null ? "" : item.get("MONEY").toString(), item.get("FISCAL") == null ? "" : item.get("FISCAL").toString());
                node.putAll(item);
                if (stack.empty()) {
                    result.add(node);
                    stack.push(node);
                } else {
                    EarlyYearTreeNode lastElement = stack.lastElement();
                    if (node.getB_ACC_CODE().matches(String.format("^%s.*+$", lastElement.getB_ACC_CODE()))) {
                        lastElement.getChildren().add(node);
                        stack.push(node);
                    } else {
                        while (!node.getB_ACC_CODE().matches(String.format("^%s.*+$", lastElement.getB_ACC_CODE()))) {
                            stack.pop();
                            if (stack.empty()) break;
                            else lastElement = stack.lastElement();
                        }
                        if (stack.empty()) {
                            result.add(node);
                        } else {
                            lastElement.getChildren().add(node);
                        }
                        stack.push(node);
                    }
                }
            }

        }
        return result;
    }

    public static List<BudgetTreeNode> OutgoingListToTreeByIsbnCode(List<Map<String, Object>> list) {
        List<BudgetTreeNode> result = new ArrayList<>();
        Stack<BudgetTreeNode> stack = new Stack<>();
        for (Map<String, Object> item : list) {
            if (!item.get("B_ACC_NAME").toString().equals("总计")) {
                BudgetTreeNode node = new BudgetTreeNode(item.get("CO_CODE").toString(), item.get("B_ACC_CODE").toString(),
                        item.get("B_ACC_NAME").toString(), item.get("MONEY1") == null ? "" : item.get("MONEY1").toString(), item.get("MONEY2") == null ? "" : item.get("MONEY2").toString(),
                        item.get("MONEY3") == null ? "" : item.get("MONEY3").toString(), item.get("MONEY4") == null ? "" : item.get("MONEY4").toString(), item.get("MONEY5") == null ? "" : item.get("MONEY5").toString(),
                        item.get("MONEY6") == null ? "" : item.get("MONEY6").toString(), item.get("PLAN") == null ? "" : item.get("PLAN").toString(), item.get("MONEY7") == null ? "" : item.get("MONEY7").toString(),
                        item.get("MONEY8") == null ? "" : item.get("MONEY8").toString(), item.get("FISCAL").toString(), item.get("FIS_PERD").toString());
                node.putAll(item);
                if (stack.empty()) {
                    result.add(node);
                    stack.push(node);
                } else {
                    BudgetTreeNode lastElement = stack.lastElement();
                    if (node.getB_ACC_CODE().matches(String.format("^%s.*+$", lastElement.getB_ACC_CODE()))) {
                        lastElement.getChildren().add(node);
                        stack.push(node);
                    } else {
                        while (!node.getB_ACC_CODE().matches(String.format("^%s.*+$", lastElement.getB_ACC_CODE()))) {
                            stack.pop();
                            if (stack.empty()) break;
                            else lastElement = stack.lastElement();
                        }
                        if (stack.empty()) {
                            result.add(node);
                        } else {
                            lastElement.getChildren().add(node);
                        }
                        stack.push(node);
                    }
                }
            }

        }
        return result;
    }

    public static List<JjkmAllMoneyTreeNode> JjkmAllMoneyListToTreeByIsbnCode(List<Map<String, Object>> list) {
        List<JjkmAllMoneyTreeNode> result = new ArrayList<>();
        Stack<JjkmAllMoneyTreeNode> stack = new Stack<>();
        for (Map<String, Object> item : list) {
            JjkmAllMoneyTreeNode node = new JjkmAllMoneyTreeNode(item.get("OUTLAY_CODE").toString(), item.get("OUTLAY_NAME").toString(), item.get("ALLMONEY") == null ? "" : item.get("ALLMONEY").toString(),
                    item.get("ID")==null?"":item.get("ID").toString(), item.get("MONEY1") == null ? "" : item.get("MONEY1").toString(), item.get("MONEY2") == null ? "" : item.get("MONEY2").toString(),
                    item.get("MONEY3") == null ? "" : item.get("MONEY3").toString(), item.get("MONEY4") == null ? "" : item.get("MONEY4").toString(), item.get("MONEY5") == null ? "" : item.get("MONEY5").toString(),
                    item.get("MONEY6") == null ? "" : item.get("MONEY6").toString(), item.get("MONEY7") == null ? "" : item.get("MONEY7").toString(), item.get("MONEY8") == null ? "" : item.get("MONEY8").toString(),
                    item.get("MONEY9") == null ? "" : item.get("MONEY9").toString(), item.get("MONEY10") == null ? "" : item.get("MONEY10").toString(), item.get("MONEY11") == null ? "" : item.get("MONEY11").toString(),
                    item.get("MONEY12") == null ? "" : item.get("MONEY12").toString(), item.get("MONEY13") == null ? "" : item.get("MONEY13").toString(), item.get("MONEY14") == null ? "" : item.get("MONEY14").toString(),
                    item.get("MONEY15") == null ? "" : item.get("MONEY15").toString(), item.get("MONEY16") == null ? "" : item.get("MONEY16").toString());
            node.putAll(item);
            if (stack.empty()) {
                result.add(node);
                stack.push(node);
            } else {
                JjkmAllMoneyTreeNode lastElement = stack.lastElement();
                if (node.getOUTLAY_CODE().matches(String.format("^%s.*+$", lastElement.getOUTLAY_CODE()))) {
                    lastElement.getChildren().add(node);
                    stack.push(node);
                } else {
                    while (!node.getOUTLAY_CODE().matches(String.format("^%s.*+$", lastElement.getOUTLAY_CODE()))) {
                        stack.pop();
                        if (stack.empty()) break;
                        else lastElement = stack.lastElement();
                    }
                    if (stack.empty()) {
                        result.add(node);
                    } else {
                        lastElement.getChildren().add(node);
                    }
                    stack.push(node);
                }
            }


        }
        return result;
    }

    public static List<JbzcAllMoneyTreeNode> JbzcAllMoneyListToTreeByIsbnCode(List<Map<String, Object>> list) {
        List<JbzcAllMoneyTreeNode> result = new ArrayList<>();
        Stack<JbzcAllMoneyTreeNode> stack = new Stack<>();
        for (Map<String, Object> item : list) {
            JbzcAllMoneyTreeNode node = new JbzcAllMoneyTreeNode(item.get("GOV_OUTLAY_CODE").toString(), item.get("GOV_OUTLAY_NAME").toString(), item.get("ALLMONEY") == null ? "" : item.get("ALLMONEY").toString(),
                    item.get("ID")==null?"":item.get("ID").toString(), item.get("MONEY1") == null ? "" : item.get("MONEY1").toString(), item.get("MONEY2") == null ? "" : item.get("MONEY2").toString(),
                    item.get("MONEY3") == null ? "" : item.get("MONEY3").toString(), item.get("MONEY4") == null ? "" : item.get("MONEY4").toString(), item.get("MONEY5") == null ? "" : item.get("MONEY5").toString(),
                    item.get("MONEY6") == null ? "" : item.get("MONEY6").toString(), item.get("MONEY7") == null ? "" : item.get("MONEY7").toString(), item.get("MONEY8") == null ? "" : item.get("MONEY8").toString(),
                    item.get("MONEY9") == null ? "" : item.get("MONEY9").toString(), item.get("MONEY10") == null ? "" : item.get("MONEY10").toString(), item.get("MONEY11") == null ? "" : item.get("MONEY11").toString(),
                    item.get("MONEY12") == null ? "" : item.get("MONEY12").toString(), item.get("MONEY13") == null ? "" : item.get("MONEY13").toString(), item.get("MONEY14") == null ? "" : item.get("MONEY14").toString(),
                    item.get("MONEY15") == null ? "" : item.get("MONEY15").toString(), item.get("MONEY16") == null ? "" : item.get("MONEY16").toString());
            node.putAll(item);
            if (stack.empty()) {
                result.add(node);
                stack.push(node);
            } else {
                JbzcAllMoneyTreeNode lastElement = stack.lastElement();
                if (node.getGOV_OUTLAY_CODE().matches(String.format("^%s.*+$", lastElement.getGOV_OUTLAY_CODE()))) {
                    lastElement.getChildren().add(node);
                    stack.push(node);
                } else {
                    while (!node.getGOV_OUTLAY_CODE().matches(String.format("^%s.*+$", lastElement.getGOV_OUTLAY_CODE()))) {
                        stack.pop();
                        if (stack.empty()) break;
                        else lastElement = stack.lastElement();
                    }
                    if (stack.empty()) {
                        result.add(node);
                    } else {
                        lastElement.getChildren().add(node);
                    }
                    stack.push(node);
                }
            }

        }
        return result;
    }


    public static List<GnkmAllMoneyTreeNode> GnkmAllMoneyListToTreeByIsbnCode(List<Map<String, Object>> list) {
        List<GnkmAllMoneyTreeNode> result = new ArrayList<>();
        Stack<GnkmAllMoneyTreeNode> stack = new Stack<>();
        for (Map<String, Object> item : list) {
            GnkmAllMoneyTreeNode node = new GnkmAllMoneyTreeNode(item.get("B_ACC_CODE").toString(), item.get("B_ACC_NAME").toString(), item.get("ALLMONEY") == null ? "" : item.get("ALLMONEY")==null?"":item.get("ALLMONEY").toString(),
                    item.get("ID")== null ? "" : item.get("ID").toString(), item.get("MONEY1") == null ? "" : item.get("MONEY1").toString(), item.get("MONEY2") == null ? "" : item.get("MONEY2").toString(),
                    item.get("MONEY3") == null ? "" : item.get("MONEY3").toString(), item.get("MONEY4") == null ? "" : item.get("MONEY4").toString(), item.get("MONEY5") == null ? "" : item.get("MONEY5").toString(),
                    item.get("MONEY6") == null ? "" : item.get("MONEY6").toString(), item.get("MONEY7") == null ? "" : item.get("MONEY7").toString(), item.get("MONEY8") == null ? "" : item.get("MONEY8").toString(),
                    item.get("MONEY9") == null ? "" : item.get("MONEY9").toString(), item.get("MONEY10") == null ? "" : item.get("MONEY10").toString(), item.get("MONEY11") == null ? "" : item.get("MONEY11").toString(),
                    item.get("MONEY12") == null ? "" : item.get("MONEY12").toString(), item.get("MONEY13") == null ? "" : item.get("MONEY13").toString(), item.get("MONEY14") == null ? "" : item.get("MONEY14").toString(),
                    item.get("MONEY15") == null ? "" : item.get("MONEY15").toString(), item.get("MONEY16") == null ? "" : item.get("MONEY16").toString());
            node.putAll(item);
            if (stack.empty()) {
                result.add(node);
                stack.push(node);
            } else {
                GnkmAllMoneyTreeNode lastElement = stack.lastElement();
                if (node.getB_ACC_CODE().matches(String.format("^%s.*+$", lastElement.getB_ACC_CODE()))) {
                    lastElement.getChildren().add(node);
                    stack.push(node);
                } else {
                    while (!node.getB_ACC_CODE().matches(String.format("^%s.*+$", lastElement.getB_ACC_CODE()))) {
                        stack.pop();
                        if (stack.empty()) break;
                        else lastElement = stack.lastElement();
                    }
                    if (stack.empty()) {
                        result.add(node);
                    } else {
                        lastElement.getChildren().add(node);
                    }
                    stack.push(node);
                }
            }


        }
        return result;
    }


    public static List<Map<String, Object>> findChildrenToList(List<Map<String, Object>> list) {
        List<Map<String, Object>> jjkmList = new ArrayList<>();
        for (Map<String, Object> jjkm : list) {
            List<Map<String, Object>> children = (List<Map<String, Object>>) jjkm.get("children");
            if (children != null) {
                for (int i = 0; i < children.size(); i++) {
                    jjkmList.add(children.get(i));
                }
            }
            jjkmList.add(jjkm);
        }
        return jjkmList;
    }

    public static List<Map<String, Object>> findChildrenToList2(List<Map<String, Object>> list) {
        List<Map<String, Object>> jjkmList = new ArrayList<>();
        for (Map<String, Object> jjkm : list) {
            List<Map<String, Object>> children = (List<Map<String, Object>>) jjkm.get("children");
            if (children != null) {
                for (int i = 0; i < children.size(); i++) {
                    List<Map<String, Object>> object = (List<Map<String, Object>>) children.get(i).get("children");
                    if (object != null) {
//                        System.out.println("children(i)===" + object.get((i)));
                        for (int j = 0; j < object.size(); j++) {
                            jjkmList.add(object.get(j));
                        }
                    } else {
                        jjkmList.add(children.get(i));
                    }
                    jjkmList.add(children.get(i));
                }
            }
            jjkmList.add(jjkm);
        }
        return jjkmList;
    }
}
