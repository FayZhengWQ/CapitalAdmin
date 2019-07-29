package com.heitian.ssm.utils.tree;

import java.util.List;

/**
 * Author by 哼唧
 * Date on 2018/9/27.下午3:31
 * today is beatuful day
 * https://blog.csdn.net/massivestars/article/details/53911620
 */
public class TreeNode {

    private int key;
    private int parentId;
    private String title;
    private String name;
    private String guid;
    private String in_code;
    private List<TreeNode> children;

    public TreeNode(int key, String title,int parentId, String name,String guid, String in_code) {
        this.key = key;
        this.parentId = parentId;
        this.title = title;
        this.name=name;
        this.guid=guid;
        this.in_code=in_code;
    }

    public TreeNode(int key, String title, TreeNode parent, String name, String guid, String in_code) {
        this.key = key;
        this.parentId = parent.getKey();
        this.title = title;
        this.name=name;
        this.guid=guid;
        this.in_code=in_code;

    }




    public String getIn_code() {
        return in_code;
    }

    public void setIn_code(String in_code) {
        this.in_code = in_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }


    @Override
    public String toString() {
        return "TreeNode{" +
                "key='" + key + '\'' +
                ", parentId='" + parentId + '\'' +
                ", title='" + title + '\'' +
                ", children=" + children +
                '}';
    }
}
