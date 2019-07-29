package com.heitian.ssm.utils.tree;

import com.heitian.ssm.model.JczlBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-12-11 11:44
 **/
                                                                                                                                                                              
//树状数据处理
public class TreePostUtils {



        public  static  List<TreeNode>  TreeList(List<JczlBean> list){
            List<TreeNode> cache=new ArrayList<TreeNode>();


            for (int i = 0; i < list.size(); i++) {

                if (list.get(i).getPARENT_GUID().equals("0")){
                    TreeNode treeNode = new TreeNode(Integer.parseInt(list.get(i).getISBN_CODE()), list.get(i).getISBN_CODE()+" "+list.get(i).getNAME(),0,list.get(i).getNAME(),list.get(i).getGUID()
                                                        ,list.get(i).getIN_CODE());
                    cache.add(treeNode);

                }else {
                    for (int j = 0; j < list.size(); j++) {

                        if (list.get(i).getPARENT_GUID().equals(list.get(j).getGUID())){

                            TreeNode treeNode = new TreeNode(Integer.parseInt(list.get(i).getISBN_CODE()),
                                    list.get(i).getISBN_CODE()+" "+list.get(i).getNAME(),


                                    new TreeNode(Integer.parseInt(list.get(j).getISBN_CODE()),
                                            list.get(j).getISBN_CODE()+" "+list.get(j).getNAME(),
                                                 Integer.parseInt(list.get(j).getPARENT_GUID()),list.get(j).getNAME(),list.get(j).getGUID(),list.get(j).getIN_CODE()),list.get(i).getNAME() ,list.get(i).getGUID()     ,list.get(i).getIN_CODE()
                            );
                            cache.add(treeNode);
                        }
                    }
                }
            }

            List<TreeNode> trees =TreeBuilder.bulid(cache);

            return trees;

        }

//
//    public  static  List<TreeFunctionNode>  TreeList2(List<FunctionModel> list){
//        List<TreeFunctionNode> cache=new ArrayList<TreeFunctionNode>();
//
//
//        for (int i = 0; i < list.size(); i++) {
//
//            if (list.get(i).getPARENT_GUID().equals("0")){
//                TreeFunctionNode functionNode = new TreeFunctionNode(list.get(i).getGUID(), list.get(i).getTITLE(),"0",list.get(i).getICON(),list.get(i).getROUTERLINK(),list.get(i).getLEVEL_NUM(),list.get(i).getISOPEN(),list.get(i).getISSELECTED(),list.get(i).getISDISABLEED());
//                cache.add(functionNode);
//
//            }else {
//                for (int j = 0; j < list.size(); j++) {
//
//
//                    if (list.get(i).getPARENT_GUID().equals(list.get(j).getGUID())){
//
//                        TreeFunctionNode functionNode = new TreeFunctionNode(
//                                      list.get(i).getGUID(),
//                                      list.get(i).getTITLE(),
//
//
//                                new TreeFunctionNode(list.get(j).getGUID(), list.get(j).getTITLE(), list.get(j).getPARENT_GUID(),list.get(j).getICON(),list.get(j).getROUTERLINK(),list.get(j).getLEVEL_NUM(),list.get(j).getISOPEN(),list.get(j).getISSELECTED(),list.get(j).getISDISABLEED()),list.get(i).getICON() ,list.get(i).getROUTERLINK(),list.get(i).getLEVEL_NUM(),list.get(i).getISOPEN(),list.get(i).getISSELECTED(),list.get(i).getISDISABLEED()  );
//                        cache.add(functionNode);
//                    }
//                }
//            }
//        }
//
//        List<TreeFunctionNode> trees =TreeBuilder.funcbuild(cache);
//
//        return trees;
//
//    }


}
