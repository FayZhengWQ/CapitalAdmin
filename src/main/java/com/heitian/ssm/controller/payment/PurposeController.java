package com.heitian.ssm.controller.payment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.payment.PurposeModel;
import com.heitian.ssm.service.payment.PurposeService;
import com.heitian.ssm.utils.excel.Excel;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:PurposeController
 * Package:com.heitian.ssm.controller
 * Description:  【用途模块】
 * author:@Fay
 * Date:2019/4/3012:05
 */
@Controller
@CrossOrigin
public class PurposeController {
    @Autowired
    private PurposeService purposeService;

    /**
     * 导入
     */
    @RequestMapping(value = "/purpose/open",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView purposeOpen(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }

    @RequestMapping(value = "/purpose/import",method = RequestMethod.POST)
    @ResponseBody
    public JsonData importPurpose(@RequestBody Map<String,Object> param,HttpServletRequest request) throws IOException {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String CBMUNITID = param.get("CBMUNITID").toString();
        String C_YEAR = param.get("C_YEAR").toString();
        String OPERATOR = param.get("OPERATOR").toString();
        String POWER=param.get("POWER").toString();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        System.out.println("excel文件导入开始" + multipartRequest.getFile("upfile"));


        MultipartFile file = multipartRequest.getFile("upfile");
        String Path=request.getServletContext().getRealPath("/upload")+"/"+file.getOriginalFilename();
        System.out.println(Path);
        File localFile = new File(Path);
        if(!localFile.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            localFile.getParentFile().mkdirs();
        }
        file.transferTo(localFile);

        InputStream in  = file.getInputStream();
        String fileName = file.getOriginalFilename();

        boolean flag = purposeService.importExcel(in, fileName,Path,CBMUNITID,C_YEAR,OPERATOR,POWER);
        JsonData jsonData=new JsonData();
        in.close();
        if (flag){
            FileUtils.deleteQuietly(localFile);
            jsonData=jsonData.success("导入成功");
        }else {
            FileUtils.deleteQuietly(localFile);
            jsonData=jsonData.fail("导入失败");
        }

        return  jsonData;
    }

    /**
     * 单条增加
     */
    @RequestMapping(value = "/purpose/insertone",method = RequestMethod.POST)
    @ResponseBody
    public JsonData addOnePurpose(@RequestBody List<Map<String,Object>> purposeList){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData  = new JsonData();
        int count = purposeService.addOnePurpose(purposeList);
        String pno = purposeList.get(0).get("PNO").toString();
        if (count ==1){
            jsonData = jsonData.success(pno,"插入成功",1);
        }else if(count ==2){
            jsonData = jsonData.fail("用途已存在");
        }else{
            jsonData = jsonData.fail("插入失败");
        }
        return jsonData;
    }

    /**
     * 数据查询
     */
    @RequestMapping(value = "/purpose/list",method = RequestMethod.POST)
    @ResponseBody
    public JsonData getList(@RequestBody Map<String,Object> param,HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        String STATUS = param.get("STATUS").toString();

        if (STATUS.equals("1")){
            Map<String,Object> map = new HashMap<>();
            map.put("CBMUNITID",param.get("CBMUNITID"));
            map.put("C_YEAR",param.get("C_YEAR"));
            map.put("POWER",param.get("POWER"));
            List<Map<String,Object>> dataList = purposeService.getList(map);
            if (dataList.size()>0 ){
                jsonData = jsonData.success(dataList,"获取数据成功",dataList.size());
            }else{
                jsonData = jsonData.fail("获取数据失败");
            }
        }else{
            //下载模板
            String sheetname = "用途模板";
            String fileno = "用途模板";
            String[] head = {"用途名称"};
            Excel.exportModel(request,response,sheetname,head,fileno);
        }

        return jsonData;
    }

    //删除数据
    @RequestMapping(value = "/purpose/delete",method = RequestMethod.POST)
    @ResponseBody
    public JsonData deletePurpose(@RequestBody List<Map<String,Object>> purposeList){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int count = purposeService.deletePurpose(purposeList);
        if (count > 0){
            jsonData = jsonData.success("删除成功");
        }else{
            jsonData = jsonData.fail("删除失败");
        }
        return jsonData;
    }

}
