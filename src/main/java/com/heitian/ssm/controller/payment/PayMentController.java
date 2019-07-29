package com.heitian.ssm.controller.payment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.payment.PayMentSonModel;
import com.heitian.ssm.model.payment.PaymentModel;
import com.heitian.ssm.service.payment.PayService;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description: 拨款单模块  【无指标拨款】
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-11 22:15    拨款管理
 **/
@Controller
@CrossOrigin
public class PayMentController {


    @Resource
    PayService payService;

    /**
     * 拨款单列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/pay/customisze/list")
    @ResponseBody
    public JsonData PayList(HttpServletRequest request) {
        DynamicDataSourceHolder.setDataSource("dataSource1");

        String CBMUNITID = request.getParameter("CBMUNITID");
        String CBMUNITNAME = request.getParameter("CBMUNITNAME");
        String YEAR = request.getParameter("YEAR");
        PaymentModel payment = new PaymentModel();
        payment.setCBMUNITID(CBMUNITID);
        payment.setCBMUNITNAME(CBMUNITNAME);
        payment.setYEAR(YEAR);

        List<PaymentModel> list = payService.selectpayment(payment);
        JsonData jsonData = new JsonData(true);
        jsonData = JsonData.success(list, "获取数据成功", list.size());
        return jsonData;
    }


    /**
     * 插入自定义拨款单表
     */
    @RequestMapping(value = "/pay/customisze", method = RequestMethod.POST)
    @ResponseBody
    public JsonData addProPaymentsSON(@RequestBody List<Map<String,Object>> paysonList) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int count = payService.addProPaymentsSON(paysonList);
        if (count > 0) {
            jsonData = jsonData.success("自定义拨款单表生成成功");
        } else {
            jsonData = jsonData.fail("自定义拨款单表生成失败");
        }
        return jsonData;
    }


    /**
     * 查询自定义拨款单
     */
    @RequestMapping(value = "/pay/customisze/getlist", method = RequestMethod.POST)
    @ResponseBody
    public JsonData getPaymentsSonList(@RequestBody Map<String,Object> PARAM,HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();

        String STATUS = PARAM.get("STATUS").toString();
        if (STATUS.equals("1")) {
            String BEGINDATE = PARAM.get("BEGINDATE").toString();
            if (BEGINDATE.equals("null")){
                BEGINDATE = "";
            }
            String ENDDATE = PARAM.get("ENDDATE").toString();
            if (ENDDATE.equals("null")){
                ENDDATE = "";
            }
            Map<String, Object> map = new HashMap<>();
            map.put("CBMUNITID", PARAM.get("CBMUNITID"));
            map.put("IYEAR", PARAM.get("IYEAR"));
            map.put("STATE", PARAM.get("STATE"));
            map.put("CPOSTGUID", PARAM.get("CPOSTGUID"));
            map.put("POWER", PARAM.get("POWER"));
            map.put("ENDDATE",ENDDATE);
            map.put("BEGINDATE",BEGINDATE);
            map.put("RECNAME",PARAM.get("RECNAME"));
            map.put("SORT",PARAM.get("SORT"));
           /* Integer PageSize = Integer.valueOf(PARAM.get("PageSize").toString());//每页条数
            Integer PageIndex = Integer.valueOf(PARAM.get("PageIndex").toString());//页码
            PageHelper.startPage(PageIndex, PageSize);*/
            List<Map<String, Object>> dataList = payService.getPaymentsSonList(map);
//            PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(dataList);
            if (dataList.size() == 0) {
                jsonData = jsonData.fail("未生成自定义拨款单报表");
            } else {
                jsonData = jsonData.success(dataList, "成功",dataList.size());
            }
        } else {
            //下载
            String fileno = "拨款单模板";
            String sheetName = "拨款单模板";
            String[] head0 = new String[]{"审批状态", "审批人", "拨款时间", "流水号", "(收款单位)开户银行", "(收款单位)账号", "(收款单位)名称",
                    "拨款金额", "用途", "(付款单位)全称", "(付款单位)账号", "(付款单位)开户银行",
                    "功能科目编码", "功能科目名称"};
            Excel.exportModel(request, response, sheetName, head0, fileno);
        }
        return jsonData;
    }


    /**
     * 审批
     * @param PARAM
     * @return
     */
    @RequestMapping(value = "/pay/customisze/exam", method = RequestMethod.POST)
    @ResponseBody
    public JsonData doExam(@RequestBody Map<String,Object> PARAM) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String EXAMER = PARAM.get("EXAMER").toString();   //审批人
        JsonData jsonData = new JsonData();
        List<Map<String,Object>> paySonlist = (List<Map<String, Object>>) PARAM.get("list");

        int count = payService.doExam(EXAMER, paySonlist);
        if (count > 0) {
            jsonData = jsonData.success("审批成功");
        } else {
            jsonData = jsonData.fail("审批失败");
        }
        return jsonData;
    }

    /**
     * 编辑
     *
     * @param paySonlist
     * @return
     */

    @RequestMapping(value = "/pay/customisze/edit", method = RequestMethod.POST)
    @ResponseBody
    public JsonData edit(@RequestBody List<Map<String,Object>> paySonlist) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int count = payService.edit(paySonlist);
        if (count > 0) {
            jsonData = jsonData.success("编辑成功");
        } else {
            jsonData = jsonData.fail("编辑失败");
        }
        return jsonData;
    }

    /**
     * 批量删除拨款单
     *
     * @param paySonlist
     * @return
     */
    @RequestMapping(value = "/pay/customisze/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonData delete(@RequestBody List<Map<String,Object>> paySonlist) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int count = payService.delete(paySonlist);
        if (count > 0) {
            jsonData = jsonData.success("删除成功");
        } else {
            jsonData = jsonData.fail("删除失败");
        }
        return jsonData;
    }


    @RequestMapping(value = "/payson/open", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView uploadExcel(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }

    /**
     * 拨款单导入接口
     *
     * @param request
     * @return
     * @throws IOException
     */

    @RequestMapping(value = "/pay/customisze/import", method = RequestMethod.POST)
    @ResponseBody
    public JsonData importPaySon(@RequestBody Map<String,Object> PARAM,HttpServletRequest request)  throws IOException {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String CBMUNITID = PARAM.get("CBMUNITID").toString();
        String OPERATOR = PARAM.get("OPERATOR").toString();
        String IYEAR = PARAM.get("IYEAR").toString();
        String POWER = PARAM.get("POWER").toString();
        String CPOSTGUID = PARAM.get("CPOSTGUID").toString();

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

        boolean flag = payService.importExcel(in, fileName, Path, CBMUNITID, OPERATOR, IYEAR,POWER,CPOSTGUID);
        JsonData jsonData = new JsonData();
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
     * 【无指标拨款作废接口】
     *
     * @param paySonlist
     * @return
     */
    @RequestMapping(value = "/pay/customisze/throw", method = RequestMethod.POST)
    @ResponseBody
    public JsonData isThrow(@RequestBody List<Map<String,Object>> paySonlist) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int count = payService.isThrow(paySonlist);
        if (count > 0) {
            jsonData = jsonData.success("作废成功");
        } else {
            jsonData = jsonData.fail("作废失败");
        }
        return jsonData;
    }


    /**
     * 【无指标拨款打印更新接口】
     *
     * @param paySonlist
     * @return
     */
    @RequestMapping(value = "/pay/customisze/print", method = RequestMethod.POST)
    @ResponseBody
    public JsonData isPrint(@RequestBody List<Map<String,Object>> paySonlist) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int count = payService.isPrint(paySonlist);
        if (count > 0) {
            jsonData = jsonData.success("打印成功");
        } else {
            jsonData = jsonData.fail("打印失败");
        }
        return jsonData;
    }

    /**
     * 导出拨款单数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/pay/customisze/export",method = RequestMethod.POST)
    @ResponseBody
    public void exportPaySon(@RequestBody Map<String,Object> PARAM, HttpServletRequest request,HttpServletResponse response) throws Exception {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        Map<String, Object> map = new HashMap<>();
        String BEGINDATE = PARAM.get("BEGINDATE").toString();
        if (BEGINDATE.equals("null")){
            BEGINDATE = "";
        }
        String ENDDATE = PARAM.get("ENDDATE").toString();
        if (ENDDATE.equals("null")){
            ENDDATE = "";
        }
        String IYEAR = PARAM.get("IYEAR").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        map.put("CBMUNITID", PARAM.get("CBMUNITID"));
        map.put("IYEAR",IYEAR );
        map.put("STATE",PARAM.get("STATE"));
        map.put("CPOSTGUID",PARAM.get("CPOSTGUID"));
        map.put("POWER", PARAM.get("POWER"));
        map.put("ENDDATE",ENDDATE);
        map.put("BEGINDATE",BEGINDATE);
        map.put("RECNAME",PARAM.get("RECNAME"));
        map.put("SORT",PARAM.get("SORT"));

        String fileno = IYEAR +"年"+FIS_PERD+"月拨款单";
        String sheetName=IYEAR +"年"+FIS_PERD+"月拨款单";

        String[] head0 = new String[]{"单位编码","审批状态", "审批人", "拨款时间", "流水号", "(收款单位)开户银行", "(收款单位)账号", "(收款单位)名称",
                "拨款金额", "用途", "(付款单位)全称", "(付款单位)账号", "(付款单位)开户银行",
                "功能科目(类)", "功能科目(款)","功能科目(项)"};
        List<Map<String, Object>> dataList = payService.getPaymentsSonList(map);
        String[] detail = new String[]{"CBMUNITID","STATUS","EXAMER","PAYDATE","CODE","RECBANKNAME","RECBANKNO"
        ,"RECNAME","IMONEY","PURPOSE","ENAME","PAYBANKNO","PAYBANKNAME","CFUNCTION1","CFUNCTION2","CFUNCTION3"};
        Excel.exportPaySon(request, response,sheetName, head0, fileno,dataList,detail);
    }


}
