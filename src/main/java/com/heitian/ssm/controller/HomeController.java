package com.heitian.ssm.controller;

import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.service.HomeService;
import com.heitian.ssm.service.project.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-23 10:12
 **/
@Controller
@CrossOrigin
public class HomeController {

    @Resource
    HomeService homeService;

    @Resource
    ProjectService projectService;

    @RequestMapping("/user/home")
    @ResponseBody
    public JsonData HomeSpecial(HttpServletRequest request){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String CBMUNITID=request.getParameter("CBMUNITID");
        String IYEAR=request.getParameter("IYEAR");


        List<Map<String,Object>> list=projectService.slectHome(CBMUNITID,IYEAR);

        JsonData jsonData=new JsonData(true);
        jsonData=jsonData.success(list,"获取成功", list.size());
        return  jsonData;
    }





}

