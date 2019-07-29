package com.heitian.ssm.controller;
import com.alibaba.fastjson.JSON;
import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JczlJsonData;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.JczlBean;
import com.heitian.ssm.service.JczlService;
import com.heitian.ssm.utils.tree.TreeListUtils;
import com.heitian.ssm.utils.tree.TreeNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * @program: CapitalAdmin
 * @description:   基础资料接口
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-08 10:40
 * 功能科目到第三层 部门,政府经济科目都到第二层就可以了 部门和政府联动
 **/
@Controller
@CrossOrigin
public class JczlController {

    @Resource
    JczlService jczlService;

    //查询单位  功能科目 部门经济科目  政府经济科目  资金来源  指标类型  项目类型  科室
    @RequestMapping("/jczl")
    @ResponseBody
    public JczlJsonData GetProject(HttpServletRequest request)  {
        String year =request.getParameter("YEAR");
        String source=request.getParameter("SOURCE");

        DynamicDataSourceHolder.setDataSource(source);

        //获取数据
        //单位
        List<JczlBean> list_Unit=jczlService.getAllUnit(year,"");

        //功能科目  做成树形式
        List<JczlBean> list_fun=jczlService.getAllFun(year,"");
        List<TreeNode> node_fun=TreeListUtils.TreeList(list_fun);

        //部门经济科目
        List<JczlBean> list_Econ=jczlService.getAllEcon(year,"");
//        List<TreeNode> node_econ=TreeListUtils.TreeList(list_Econ);

        //政府经济科目
        List<JczlBean> list_EconGov=jczlService.getAllEconGov(year,"");
//        List<TreeNode> node_econGov=TreeListUtils.TreeList(list_EconGov);

        //资金来源
        List<JczlBean> list_Resoure=jczlService.getAllResoure(year,"");

        //指标类型
        List<JczlBean> list_Budget=jczlService.getAllBudget(year,"");

        //项目类型
        List<JczlBean> list_options=jczlService.getAllOptions(year,"");
        
        //科室
        List<JczlBean> list_division=jczlService.getAlldivision(year,"");


        JczlJsonData jsonData = new JczlJsonData(true);
        jsonData = jsonData.success_jczl(list_Unit,node_fun,list_Econ,list_EconGov,list_Resoure,list_Budget,list_options,list_division, "获取数据成功");

        return jsonData;

    }


    @RequestMapping(value = "/jczl/gnkm",method = RequestMethod.POST)
    @ResponseBody
    public JsonData getGnkmlist(@RequestBody Map<String,Object> param){
        DynamicDataSourceHolder.setDataSource("dataSource2");
        JsonData jsonData = new JsonData();
        List<JczlBean> list_fun=jczlService.getAllFun(param.get("YEAR").toString(),"");
        List<TreeNode> node_fun=TreeListUtils.TreeList(list_fun);
        jsonData = jsonData.success(node_fun,"获取数据成功",node_fun.size());
        return jsonData;
    }



    
}
