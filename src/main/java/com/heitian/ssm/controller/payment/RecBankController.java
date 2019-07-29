package com.heitian.ssm.controller.payment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.payment.RecBankModel;
import com.heitian.ssm.service.payment.RecBankService;
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
import java.util.List;
import java.util.Map;

/**
 * ClassName:收款单位
 * Package:com.heitian.ssm.controller
 * Description:
 * author:@Fay
 * Date:2019/3/14 0014上午 9:14
 */
@Controller
@CrossOrigin
public class RecBankController {

    @Resource
    RecBankService recBankService;

    /**
     * 获取收款单位的银行信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/recbank/list",method =RequestMethod.POST)
    public @ResponseBody
    JsonData queryRecBank(@RequestBody Map<String,Object> PARAM,HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        String STATUS = PARAM.get("STATUS").toString();
        if (STATUS.equals("1")){
            String CBMUNITID = PARAM.get("CBMUNITID").toString();
            String C_YEAR = PARAM.get("C_YEAR").toString();
            String POWER=PARAM.get("POWER").toString();
            List<Map> recBankList = recBankService.queryRecBankByCndition(CBMUNITID,C_YEAR,POWER);
            if (recBankList.size()==0){
                jsonData= jsonData.fail("未生成数据");
            }else{
                jsonData = JsonData.success(recBankList,"获取数据成功",recBankList.size());
            }

        }else{
            //下载
            String fileno = "收款单位模板";
            String sheetName = "收款单位模板";
            String[] head0 =new  String[]{"收款单位账号","收款单位名称","收款单位开户银行","行号","信用代码（税号）","地区号","地区名称","地址","邮编","电话","传真","联系人","户名"};
            Excel.exportModel(request,response,sheetName,head0,fileno);
        }
        return jsonData;
    }


    /**
     * 收款单位新航信息
     * @return
     */
    @RequestMapping(value = "/recbank/update",method =RequestMethod.POST)
    public @ResponseBody
    JsonData modifierRecBank(@RequestBody List<Map<String,Object>> list) {
        JsonData jsonData = new JsonData();
        int count = recBankService.modifierRecBankByCondition(list);
        if (count == 1) {
            jsonData = jsonData.success("收款单位修改成功");
        } else {
            jsonData = jsonData.fail("收款单位修改失败");
        }
        return jsonData;
    }

    /**
     * 添加一条收款单位信息
     */
    @RequestMapping(value = "/recbank/add",method =RequestMethod.POST)
    public @ResponseBody
    JsonData addRecBank(@RequestBody List<Map<String,Object>> recBnakList) {
        JsonData jsonData = new JsonData();
        int count = recBankService.addOneRecBank(recBnakList);
        String reciid = recBnakList.get(0).get("PNO").toString();
        System.out.println("reciid="+reciid);
        if (count == 1) {
            jsonData = jsonData.success(reciid,"收款单位信息插入成功",1);
        } else if(count ==2){
            jsonData = jsonData.fail("收款单位已存在");
        }else{
            jsonData = jsonData.fail("收款单位信息插入失败");
        }
        return jsonData;
    }


    @RequestMapping(value="/recbank/open",method= RequestMethod.GET)
    @ResponseBody
    public ModelAndView uploadExcel(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }
    //收入表2003excel导入
    @RequestMapping(value = "/recbank/import",method = RequestMethod.POST)
    @ResponseBody
    public JsonData importIncome(HttpServletRequest request) throws Exception {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String C_YEAR = request.getParameter("C_YEAR");
        String OPERATOR = request.getParameter("OPERATOR");
        String CBMUNITID = request.getParameter("CBMUNITID");
        String POWER = request.getParameter("POWER");
        /*String C_YEAR = "2019";
        String OPERATOR = "sss";
        String CBMUNITID = "60012";
        String POWER = "01";*/


       /* //判断[收入表]数据库中是否有数据
        int count = recBankService.isExistRec(YEAR,MONTH,CBMUNITID);
        //删除当月的本地数据库数据
        if (count > 0){
            int deletecount = recBankService.deleteRec(YEAR,MONTH,CBMUNITID);
        }*/
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

        boolean flag = recBankService.importExcel(in, fileName,Path,CBMUNITID,C_YEAR,OPERATOR,POWER);
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
     * 批量删除
     * @param
     * @return
     */
    @RequestMapping(value = "/recbank/delete",method = RequestMethod.POST)
    @ResponseBody
    public JsonData delete(@RequestBody List<Map<String,Object>> recBnakList){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();

        int count = recBankService.delete(recBnakList);
        if (count > 0){
            jsonData = jsonData.success("删除成功");
        }else{
            jsonData = jsonData.fail("删除失败");
        }
        return jsonData;
    }
}
