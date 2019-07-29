package com.heitian.ssm.controller.payment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.payment.SubsidiaryModel;
import com.heitian.ssm.service.payment.SubsidiaryService;
import com.heitian.ssm.utils.excel.Excel;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
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
 * ClassName:SubsidiaryController
 * Package:com.heitian.ssm.controller.payment
 * Description:
 * author:@Fay
 * Date:2019/5/289:59
 */
@Controller
@CrossOrigin
public class SubsidiaryController {

    @Autowired
    private SubsidiaryService subsidiaryService;

    /**
     * 添加上年结转
     */
    @RequestMapping(value = "/subsidiary/lastYearBalance", method = RequestMethod.POST)
    @ResponseBody
    public JsonData lastYearBalance(@RequestBody Map<String,Object> param) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        List<Map<String, Object>> subsidiaryList = new ArrayList<>();
        int count = 0;
        String IS_CASH = param.get("IS_CASH").toString();
        Map<String, Object> args = new HashMap<>();
        args.put("YEAR", param.get("YEAR"));
        args.put("MONTH", 0);
        args.put("DAY", 0);
        args.put("REMARKS", "上年结余");
        args.put("POWER", param.get("POWER"));
        args.put("CBMUNITID", param.get("CBMUNITID"));
        args.put("DEBITMONEY", param.get("DEBITMONEY"));
        args.put("CREDITMONEY", param.get("CREDITMONEY"));
        args.put("ACCOUNTGUID", param.get("ACCOUNTGUID"));
        subsidiaryList.add(args);
        int num = subsidiaryService.isExistLastBanlance(args,IS_CASH);
        if(num>0){
            jsonData = jsonData.fail("上年结余数已生成");
        }else{
            count = subsidiaryService.addOne(subsidiaryList, IS_CASH, args);

            if (count > 0) {
                jsonData = jsonData.success("上年借贷金额保存成功");
            } else {
                jsonData = jsonData.fail("上年借贷金额保存失败");
            }
        }

        return jsonData;
    }

    /**
     * 添加单条的银行存款明细记录
     *
     * @return
     */
    @RequestMapping(value = "/subsidiary/addOne", method = RequestMethod.POST)
    @ResponseBody
    public JsonData addOne(@RequestBody Map<String,Object> param /*HttpServletRequest request*/) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        String IS_CASH = param.get("IS_CASH").toString();
        List<Map<String, Object>> subsidiaryList = (List<Map<String, Object>>) param.get("list");
        Map<String, Object> args = new HashMap<>();
        args.put("YEAR", param.get("YEAR"));
        args.put("MONTH", param.get("MONTH"));
        args.put("DAY", param.get("DAY"));
        args.put("POWER", param.get("POWER"));
        args.put("ACCOUNTGUID", param.get("ACCOUNTGUID"));
        args.put("CBMUNITID", param.get("CBMUNITID"));
        int count = subsidiaryService.addOne(subsidiaryList, IS_CASH, args);
        if (count > 0) {
            jsonData = jsonData.success("插入成功");
        } else {
            jsonData = jsonData.fail("插入失败");
        }
        return jsonData;
    }

    /**
     * 修改单条银行存款明细账
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/subsidiary/edit", method = RequestMethod.POST)
    @ResponseBody
    public JsonData editSubsidiary(@RequestBody Map<String,Object> param) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        String IS_CASH = param.get("IS_CASH").toString();
        List<Map<String, Object>> subsidiaryList = (List<Map<String, Object>>) param.get("list");

        int count = subsidiaryService.editSubsidiary(subsidiaryList, IS_CASH);
        if (count > 0) {
            jsonData = jsonData.success("修改成功");
        } else {
            jsonData = jsonData.fail("修改失败");
        }
        return jsonData;
    }


    /**
     * 下载模板
     */
    @RequestMapping(value = "/subsidiary/downloadModel", method = RequestMethod.POST)
    @ResponseBody
    public void downloadModel(@RequestBody Map<String,Object> param, HttpServletRequest request,HttpServletResponse response) throws Exception {
        String IS_CASH = param.get("IS_CASH").toString();
            //下载模板
            if (IS_CASH.equals("0")) {
                String fileno = "银行存款明细帐模板";
                String sheetName = "银行存款明细帐模板";
                String[] head0 = new String[]{"年", "月", "日", "银行凭证", "记帐凭证编号", "开户银行",
                        "对方单位", "摘要", "借方金额", "贷方金额"};
                Excel.exportModel(request, response, sheetName, head0, fileno);
            } else if (IS_CASH.equals("1")) {
                String fileno = "现金日记账模板";
                String sheetName = "现金日记账模板";
                String[] head0 = new String[]{"年", "月", "日", "记帐凭证编号",
                        "对方单位", "摘要", "借方金额", "贷方金额"};
                Excel.exportModel(request, response, sheetName, head0, fileno);
            }
    }

    /**
     * 查看银行存款明细记录(根据对方单位、摘要模糊查询，年份、借方金额、贷方金额精确查询)
     * IS_CASH=0 银行存款明细账
     * IS_CASH=1 现金日记账
     * STATUS=1 查看
     * STATUS=2 下载
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/subsidiary/getlist", method = RequestMethod.GET)
    @ResponseBody
    public JsonData getSubsidiaryList(@RequestBody Map<String,Object> param,HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        String STATUS = param.get("STATUS").toString();
        String IS_CASH = param.get("IS_CASH").toString();
        Map<String,Object> args = new HashMap<>();
        args.put("CBMUNITID",param.get("CBMUNITID"));
        args.put("YEAR",param.get("YEAR"));
        args.put("cur",new ArrayList<>());



        if (IS_CASH.equals("0")){
            subsidiaryService.getSubsidiary(args);
            List<Map<String,Object>> dataList = (List<Map<String, Object>>) args.get("cur");
            dataList.remove(1);
            dataList.remove(1);
            if (STATUS.equals("1")){
                if (dataList.size() > 0) {
                    jsonData = jsonData.success(dataList, "获取数据成功", dataList.size());
                } else {
                    jsonData = jsonData.fail("未生成数据");
                }
            }else{
                String fileno = param.get("YEAR") + "年银行存款明细帐";
                String sheetName = param.get("YEAR") + "年银行存款明细帐";
                String[] head0 = new String[]{"月", "日", "银行凭证", "记帐凭证编号", "开户银行",
                        "对方单位", "摘要", "借方金额", "贷方金额", "借方余额"};
                String[] head1 = new String[]{"MONTH","DAY","BANKCODE","VOUCHERCODE","PAYBANKNAME","OPPOCBMUNIT","REMARKS","DEBITMONEY","CREDITMONEY","MONEY1"};
                Excel.exportSubsidiaryAndCash(request,response,dataList,sheetName,fileno,head0,head1);
            }
        }else{
            List<Map<String, Object>> dataList = subsidiaryService.getList(args);
            if (STATUS.equals("1")){
                if (dataList.size() > 0) {
                    jsonData = jsonData.success(dataList, "获取数据成功", dataList.size());
                } else {
                    jsonData = jsonData.fail("未生成数据");
                }
            }else{
                String fileno = param.get("YEAR") + "年现金日记帐";
                String sheetName = param.get("YEAR") + "年现金日记帐";
                String[] head0 = new String[]{"月", "日", "记帐凭证编号",
                        "对方单位", "摘要", "借方金额", "贷方金额", "借方余额"};
                String[] head1 = new String[]{"MONTH","DAY","VOUCHERCODE","OPPOCBMUNIT","REMARKS","DEBITMONEY","CREDITMONEY","MONEY1"};
                Excel.exportSubsidiaryAndCash(request,response,dataList,sheetName,fileno,head0,head1);
            }
        }
        return jsonData;
    }


    @RequestMapping(value = "/subsidiary/open", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView open() {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }

    /**
     * 导入
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/subsidiary/import", method = RequestMethod.POST)
    @ResponseBody
    public JsonData importSubsidiary(@RequestBody Map<String,Object> param,HttpServletRequest request) throws IOException {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        String CBMUNITNAME = param.get("CBMUNITNAME").toString();
        String CBMUNITID = param.get("CBMUNITID").toString();
        String OPERATOR = param.get("OPERATOR").toString();
        String POWER = param.get("POWER").toString();
        String IS_CASH = param.get("IS_CASH").toString();
        String ACCOUNTGUID = param.get("ACCOUNTGUID").toString();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("upfile");
        String Path = request.getServletContext().getRealPath("/upload") + "/" + file.getOriginalFilename();
        File localFile = new File(Path);
        if (!localFile.getParentFile().exists()) {     //如果目标文件所在的目录不存在，则创建父目录
            localFile.getParentFile().mkdirs();
        }
        file.transferTo(localFile);

        InputStream in = file.getInputStream();
        String fileName = file.getOriginalFilename();

        boolean flag = subsidiaryService.importExcel(in, fileName, Path, CBMUNITNAME, CBMUNITID, OPERATOR, ACCOUNTGUID, POWER, IS_CASH);
        in.close();
        if (flag) {
            FileUtils.deleteQuietly(localFile);
            jsonData = jsonData.success("导入成功");
        } else {
            FileUtils.deleteQuietly(localFile);
            jsonData = jsonData.fail("导入失败");
        }
        return jsonData;
    }


    /**
     * 批量删除银行明细账
     */
    @RequestMapping(value = "/subsidiary/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonData deleteSubsidiary(@RequestBody Map<String,Object> param) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        String IS_CASH = param.get("IS_CASH").toString();
        List<Map<String, Object>> subsidiaryList = (List<Map<String, Object>>) param.get("list");
       /* String  IS_CASH = "1";
        List<Map<String,Object>> subsidiaryList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("GUID","2019-7-12-000");
        Map<String,Object> map1 = new HashMap<>();
        map1.put("GUID","2019-7-12-000");
        subsidiaryList.add(map);
        subsidiaryList.add(map1);*/
        int count = subsidiaryService.deleteSubsidiary(subsidiaryList, IS_CASH);

        if (count > 0) {
            jsonData = jsonData.success("删除成功");
        } else {
            jsonData = jsonData.fail("删除失败");
        }
        return jsonData;
    }

}
