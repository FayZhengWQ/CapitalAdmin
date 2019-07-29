package com.heitian.ssm.utils.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Author by 哼唧
 * Date on 2018/9/28.上午2:02
 * today is beatuful day
 */
public class TreeBuilder {

    /**
     * 两层循环实现建树     * @param treeNodes 传入的树节点列表     * @return
     */
    public static List<TreeNode> bulid(List<TreeNode> treeNodes) {
        List<TreeNode> trees = new ArrayList<TreeNode>();
        for (TreeNode treeNode : treeNodes) {
            if (0==treeNode.getParentId()) {
                trees.add(treeNode);
            }
            for (TreeNode it : treeNodes) {
                if (it.getParentId() == treeNode.getKey()) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<TreeNode>());
                    }
                    treeNode.getChildren().add(it);
                }
            }
        }
        return trees;
    }


    /**
     * 两层循环实现建树     * @param treeNodes 传入的树节点列表     * @return
     */
    public static List<TreeFunctionNode> funcbuild(List<TreeFunctionNode> funcNodes) {
        List<TreeFunctionNode> trees = new ArrayList<TreeFunctionNode>();
        for (TreeFunctionNode funcNode : funcNodes) {
            if ("0".equals(funcNode.getParentId())) {
                trees.add(funcNode);
            }
            for (TreeFunctionNode it : funcNodes) {
                if (it.getParentId() == funcNode.getKey()) {
                    if (funcNode.getChildren() == null) {
                        funcNode.setChildren(new ArrayList<TreeFunctionNode>());
                    }
                    funcNode.getChildren().add(it);
                }
            }
        }
        return trees;
    }



}
