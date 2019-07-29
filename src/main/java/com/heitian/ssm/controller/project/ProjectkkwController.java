package com.heitian.ssm.controller.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.service.project.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: 乡镇资金监管
 * @description: 柯开委
 * @author: liangsizhuuo@163.com
 * @create: 2019-05-24 09:14
 **/


@Controller
@CrossOrigin
public class ProjectkkwController {

    @Resource
    private ProjectService projectService;

    @RequestMapping(value = "/projectkkw/add",method = RequestMethod.POST) //可开委员下发
    @ResponseBody
    public JsonData AddProject(HttpServletRequest request) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        List<Map<String,Object>> list = JSON.parseObject(request.getParameter("list"), new TypeReference<ArrayList<Map<String,Object>>>() {});
        int count=projectService.addkkwproject(list);
        if (count ==1){
            jsonData=jsonData.success("下发成功");
        }else{
            jsonData=jsonData.fail("下发失败");
        }

        return jsonData;

    }




}
