package com.heitian.ssm.controller.payment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.payment.PayBankModel;
import com.heitian.ssm.service.payment.PayBankService;
import com.heitian.ssm.utils.excel.Excel;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:付款单位
 * Package:com.heitian.ssm.controller
 * Description:
 * author:@Fay
 * Date:2019/3/14 0014上午 9:10
 */
@Controller
@CrossOrigin
public class PayBankController {

    @Resource
    PayBankService payBankService;

    /**
     * 获取付款单位的银行信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/paybank/list",method = RequestMethod.POST)
    public @ResponseBody JsonData queryPayBank(@RequestBody Map<String,Object> param,HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        String STATUS = param.get("STATUS").toString();

        if (STATUS.equals("1")){
            String CBMUNITID = param.get("CBMUNITID").toString();
            String C_YEAR = param.get("C_YEAR").toString();
            String POWER =param.get("POWER").toString();
            List<Map> payBankList = payBankService.queryPayBankByCondition(CBMUNITID,C_YEAR,POWER);
            if (payBankList.size()==0){
                jsonData = jsonData.fail("未生成数据");
            }else {
                jsonData = jsonData.success(payBankList,"获取数据成功",payBankList.size());
            }
        }else{
            //下载模板
            String sheetName = "付款单位模板";
            String fileno = "付款单位模板";
            String[] head0 = new String[]{"付款单位账号","付款单位全称","付款单位开户银行 ","付款单位行号","信用代码（税号）"};
            Excel.exportModel(request,response,sheetName,head0,fileno);
        }


        return jsonData;
    }

    /**
     * 单条付款单位信息作修改
     * @return
     */
    @RequestMapping(value = "/paybank/update",method =RequestMethod.POST)
    public @ResponseBody JsonData modifierPayBank(@RequestBody List<Map<String,Object>> list ){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int count = payBankService.modifierPayBankByCondition(list);
        System.out.println(count);
        if(count == 1){
            jsonData = jsonData.success("付款单位修改成功");
        }else{
            jsonData = jsonData.fail("付款单位修改失败");
        }
        return jsonData;
    }

    /***
     * 单条付款单位添加
     *
     * @return
     */
    @RequestMapping(value = "/paybank/add",method =RequestMethod.POST)
    public @ResponseBody JsonData addPayBank(@RequestBody List<Map<String,Object>> payBnakList ){
        JsonData jsonData = new JsonData();
        int count = payBankService.addOnePayBank(payBnakList);
        String  payid = payBnakList.get(0).get("PNO").toString();
        System.out.println("payid="+payid);
        if (count == 1){
            jsonData = jsonData.success(payid,"插入成功",1);
        }else if (count ==2){
            jsonData = jsonData.fail("付款单位已存在");
        }else{
            jsonData = jsonData.fail("插入失败");
        }
        return jsonData;
    }





    @RequestMapping(value="/paybank/open",method= RequestMethod.GET)
    @ResponseBody
    public ModelAndView uploadExcel(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }

    /**
     * 付款信息导入到数据库
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/paybank/import",method = RequestMethod.POST)
    @ResponseBody
    public JsonData importIncome(@RequestBody Map<String,Object> param,HttpServletRequest request) throws Exception {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String CBMUNITID = param.get("CBMUNITID").toString();
        String OPERATOR = param.get("OPERATOR").toString();
        String C_YEAR = param.get("C_YEAR").toString();
        String POWER= param.get("POWER").toString();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
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

        boolean flag = payBankService.importExcel(in, fileName,Path,CBMUNITID,OPERATOR,C_YEAR,POWER);
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
     * 删除付款银行信息
     * @param
     * @return
     */
    @RequestMapping(value = "/paybank/delete",method = RequestMethod.POST)
    @ResponseBody
    public JsonData deletePayBank(@RequestBody List<Map<String,Object>> payBnakList){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();

        int count = payBankService.delete(payBnakList);
        if (count > 0){
            jsonData = jsonData.success("删除成功");
        }else{
            jsonData = jsonData.fail("删除失败");
        }
        return jsonData;
    }

}
