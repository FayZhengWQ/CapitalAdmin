package com.heitian.ssm.controller.baseConfig;

import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.baseconfig.BmunitsModel;
import com.heitian.ssm.service.baseConfig.BmunitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * ClassName:BmunitsController
 * Package:com.heitian.ssm.controller.baseConfig
 * Description:
 * author:@Fay
 * Date:2019/4/1019:37
 */
@Controller
@CrossOrigin
public class BmunitsController {
    @Autowired
    private BmunitsService bmunitsService;

    /*单位模块*/
    /**
     *
     * 根据单位编号,单位名称查询单位表数据(后台  单位编码 单位名称选传)
     */
    @RequestMapping("/bmunits/list")
    public @ResponseBody
    JsonData queryBmunitsByCondition(@RequestParam(value = "cbmunitid",required = false)String cbmunitid, @RequestParam(value = "cbmunitname",required = false)String cbmunitname){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        List<BmunitsModel> bmunitsList = bmunitsService.queryBmunits(cbmunitid,cbmunitname);
        jsonData = jsonData.success(bmunitsList,"成功",bmunitsList.size());
        return jsonData;
    }





    /**
     * 修改对应单位信息
     * 根据单位编码,单位名称,年份对url,url_name,url_pwd,sid,shortname做修改
     */
    @RequestMapping("/bmunits/update")
    public @ResponseBody JsonData modifierBumnuitsByCondition(@RequestParam("cbmunitid")String cbmunitid,@RequestParam("cbmunitname")String cbmunitname,
                                                              @RequestParam("url")String url,@RequestParam("urlname")String urlname,@RequestParam("urlpwd")String urlpwd,
                                                              @RequestParam("sid")String sid,@RequestParam("shortname")String shortname, @RequestParam("source")String source,@RequestParam("kjhsurl")String kjhsurl,@RequestParam("cpayUrl")String cpayUrl){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int count = bmunitsService.modifierBumnuitsByCondition(cbmunitid,cbmunitname,url,urlname,urlpwd,sid,shortname,source,kjhsurl,cpayUrl);
        if (count == 1){
            jsonData = jsonData.success("修改成功");
        }else{
            jsonData = jsonData.fail("修改失败");
        }
        return jsonData;
    }

    /**
     * 添加单位信息
     */
    @RequestMapping("/bmunits/add")
    public @ResponseBody JsonData addBumunits(@RequestParam("cbmunitid")String cbmunitid,@RequestParam("cbmunitname")String cbmunitname,
                                              @RequestParam("url")String url,@RequestParam("urlname")String urlname,@RequestParam("urlpwd")String urlpwd,
                                              @RequestParam("sid")String sid,@RequestParam("shortname")String shortname,@RequestParam("cmemo")String cmemo,
                                              @RequestParam("cpayUrl")String cpayUrl,@RequestParam("source")String source,@RequestParam("kjhsurl")String kjhsurl){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int count = bmunitsService.addBumunits(cbmunitid,cbmunitname,url,urlname,urlpwd,sid,shortname,cmemo,cpayUrl,source,kjhsurl);
        if (count == 1){
            jsonData = jsonData.success("添加成功");
        }else{
            jsonData = jsonData.fail("添加失败");
        }
        return jsonData;
    }

    //查询所有单位 下拉框
    @RequestMapping("/cbmunits/query")
    public @ResponseBody JsonData queryAllCbmunit(){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        List<Map<String,Object>> bumnuits =  bmunitsService.queryAllCbmunit();
        if (bumnuits.size() > 0){
            jsonData = jsonData.success(bumnuits,"获取数据成功",bumnuits.size());
        }else{
            jsonData = jsonData.fail("获取数据失败");
        }
        return jsonData;
    }

}
