package com.heitian.ssm.utils.tree;

import java.util.List;

/**
 * Author by 哼唧
 * Date on 2018/9/27.下午3:31
 * today is beatuful day
 * https://blog.csdn.net/massivestars/article/details/53911620
 */
public class TreePostNode {

    private String parentId;
    private String title;
    private String guid;
    private List<TreePostNode> children;
    private String icon;
    private String routerlink;
    private String Level_NUM;
    private String ISOPEN;
    private String ISSELECTED;
    private String ISDISABLEED;




    public TreePostNode(String guid, String title, String parentId, String icon, String routerlink, String Level_NUM, String ISOPEN, String ISSELECTED, String ISDISABLEED  ) {
        this.guid = guid;
        this.parentId = parentId;
        this.title = title;
        this.icon=icon;
        this.routerlink=routerlink;
        this.Level_NUM=Level_NUM;
        this.ISOPEN=ISOPEN;
        this.ISSELECTED=ISSELECTED;
        this.ISDISABLEED=ISDISABLEED;

    }

    public TreePostNode(String guid, String title, TreePostNode parent, String icon, String routerlink, String Level_NUM, String ISOPEN, String ISSELECTED, String ISDISABLEED ) {
        this.guid = guid;
        this.parentId = parent.getGuid();
        this.title = title;
        this.icon=icon;
        this.routerlink=routerlink;
        this.Level_NUM=Level_NUM;
        this.ISOPEN=ISOPEN;
        this.ISSELECTED=ISSELECTED;
        this.ISDISABLEED=ISDISABLEED;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

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

    public List<TreePostNode> getChildren() {
        return children;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setChildren(List<TreePostNode> children) {
        this.children = children;
    }


    @Override
    public String toString() {
        return "TreeNode{" +
                ", parentId='" + parentId + '\'' +
                ", title='" + title + '\'' +
                ", icon=' " + icon +'\''+
                ", routerlink=' " + routerlink +'\''+
                ", Level_NUM=' " + Level_NUM +'\''+
                ", ISOPEN=' " + ISOPEN +'\''+
                ", ISSELECTED=' " + ISSELECTED +'\''+
                ", ISDISABLEED=' " + ISDISABLEED +'\''+
                ", ISDISABLEED=" + children +
                '}';
    }
}
