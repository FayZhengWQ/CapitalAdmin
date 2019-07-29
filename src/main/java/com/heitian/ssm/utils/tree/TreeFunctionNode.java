package com.heitian.ssm.utils.tree;

import java.util.List;

/**
 * Author by 哼唧
 * Date on 2018/9/27.下午3:31
 * today is beatuful day
 * https://blog.csdn.net/massivestars/article/details/53911620
 */
public class TreeFunctionNode {

    private String parentId;
    private String title;
    private String key;
    private List<TreeFunctionNode> children;
    private String icon;
    private String routerlink;
    private String Level_NUM;
    private String ISOPEN;
    private String ISSELECTED;
    private String ISDISABLEED;
    private Boolean expanded;
    private String VISIBLE;




    public TreeFunctionNode(String key, String title, String parentId, Boolean expanded,  String routerlink,String Level_NUM,String ISOPEN,String ISSELECTED,String ISDISABLEED,String VISIBLE ,String icon ) {
        this.key = key;
        this.parentId = parentId;
        this.title = title;
        this.icon=icon;
        this.expanded=expanded;
        this.routerlink=routerlink;
        this.Level_NUM=Level_NUM;
        this.ISOPEN=ISOPEN;
        this.ISSELECTED=ISSELECTED;
        this.ISDISABLEED=ISDISABLEED;
        this.VISIBLE=VISIBLE;

    }

    public TreeFunctionNode(String key, String title, TreeFunctionNode parent, Boolean expanded, String routerlink, String Level_NUM, String ISOPEN, String ISSELECTED, String ISDISABLEED, String  VISIBLE , String icon) {
        this.key = key;
        this.parentId = parent.getKey();
        this.title = title;
        this.icon=icon;
        this.expanded=expanded;
        this.routerlink=routerlink;
        this.Level_NUM=Level_NUM;
        this.ISOPEN=ISOPEN;
        this.ISSELECTED=ISSELECTED;
        this.ISDISABLEED=ISDISABLEED;
        this.VISIBLE=VISIBLE;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getVISIBLE() {
        return VISIBLE;
    }

    public void setVISIBLE(String VISIBLE) {
        this.VISIBLE = VISIBLE;
    }

    public String getLevel_NUM() {
        return Level_NUM;
    }

    public void setLevel_NUM(String level_NUM) {
        Level_NUM = level_NUM;
    }

    public String getISOPEN() {
        return ISOPEN;
    }

    public void setISOPEN(String ISOPEN) {
        this.ISOPEN = ISOPEN;
    }

    public String getISSELECTED() {
        return ISSELECTED;
    }

    public void setISSELECTED(String ISSELECTED) {
        this.ISSELECTED = ISSELECTED;
    }

    public String getISDISABLEED() {
        return ISDISABLEED;
    }

    public void setISDISABLEED(String ISDISABLEED) {
        this.ISDISABLEED = ISDISABLEED;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

//    public String getIcon() {
//        return icon;
//    }
//
//    public void setIcon(String icon) {
//        this.icon = icon;
//    }

    public String getRouterlink() {
        return routerlink;
    }

    public void setRouterlink(String routerlink) {
        this.routerlink = routerlink;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TreeFunctionNode> getChildren() {
        return children;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setChildren(List<TreeFunctionNode> children) {
        this.children = children;
    }


    @Override
    public String toString() {
        return "TreeNode{" +
                ", parentId='" + parentId + '\'' +
                ", title='" + title + '\'' +
                ", expanded='" + expanded + '\'' +
                ", icon=' " + icon +'\''+
                ", VISIBLE=' " + VISIBLE +'\''+
                ", routerlink=' " + routerlink +'\''+
                ", Level_NUM=' " + Level_NUM +'\''+
                ", ISOPEN=' " + ISOPEN +'\''+
                ", ISSELECTED=' " + ISSELECTED +'\''+
                ", ISDISABLEED=' " + ISDISABLEED +'\''+
                ", key=' " + key +'\''+
                ", ISDISABLEED=" + children +

                '}';
    }
}
