package com.heitian.ssm.controller.account;

import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.service.account.AccountConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description: 日记账【账簿】础配置
 * @author: liangsizhuuo@163.com
 * @create: 2019-06-18 23:34
 **/
@Controller
@CrossOrigin
public class AccountConfigController {
    @Autowired
    private AccountConfigService Service;

    /**
     * 查看账簿
     */
    @RequestMapping(method = RequestMethod.GET, value = "/account/config/getlist")
    @ResponseBody
    public JsonData getAccountList(HttpServletRequest request) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        List<Map<String, Object>> getAccountList = Service.getAccountList(request.getParameter("year"), request.getParameter("power"), request.getParameter("cbmunitid"));
        jsonData = jsonData.success(getAccountList, "成功", getAccountList.size());
        return jsonData;
    }

    //添加账簿

    @RequestMapping(method = RequestMethod.GET, value = "/account/config/add")
    @ResponseBody
    public JsonData addAccount(HttpServletRequest request) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int count = Service.addAccount(request.getParameter("cbmunitid"),
                request.getParameter("name"),
                request.getParameter("power"));

        if (count == 1) {
            jsonData = jsonData.success("添加账簿成功");
        } else {
            jsonData = jsonData.success("添加操作失败");
        }
        return jsonData;
    }


    //删除账簿
    @RequestMapping(method = RequestMethod.GET, value = "/account/config/delete")
    @ResponseBody
    public JsonData deleteAccount(HttpServletRequest request) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int count = Service.deleteAccount(request.getParameter("guid"));
        if (count == 1) {
            jsonData = jsonData.success("删除账簿成功");
        } else {
            jsonData = jsonData.success("删除操作失败");
        }
        return jsonData;
    }


    //编辑账簿
    @RequestMapping(method = RequestMethod.GET, value = "/account/config/update")
    @ResponseBody
    public JsonData updateAccount(HttpServletRequest request) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int count = Service.updateAccount(request.getParameter("name"), request.getParameter("guid"));
        if (count == 1) {
            jsonData = jsonData.success("编辑账簿成功");
        } else {
            jsonData = jsonData.success("编辑操作失败");
        }
        return jsonData;
    }


}
