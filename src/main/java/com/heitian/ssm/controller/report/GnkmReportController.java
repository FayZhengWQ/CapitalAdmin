package com.heitian.ssm.controller.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.report.GnkmAllMoneyTreeNode;
import com.heitian.ssm.model.report.GnkmReportModel;
import com.heitian.ssm.model.report.GnkmTreeNode;
import com.heitian.ssm.service.report.GnkmReportService;
import com.heitian.ssm.service.report.GnkmReportService2;
import com.heitian.ssm.utils.FormatHelper;
import com.heitian.ssm.utils.excel.Excel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:GnkmReportController
 * Package:com.heitian.ssm.controller.report
 * Description:
 * author:@Fay
 * Date:2019/7/814:13
 */
@Controller
@CrossOrigin
public class GnkmReportController {
    @Autowired
    private GnkmReportService2 gnkmReportService2;

    /**
     * 获取功能科目列表
     */
    @RequestMapping(value = "/GNKM/getGNKMList",method = RequestMethod.POST)
    @ResponseBody
    public JsonData getGNKMList(@RequestBody Map<String,Object> param){
        DynamicDataSourceHolder.setDataSource("dataSource18");
        JsonData jsonData = new JsonData();
        Map map = new HashMap();
        map.put("CO_CODE", param.get("CO_CODE"));
        map.put("FIS_PERD", param.get("FIS_PERD"));
        map.put("FISCAL", param.get("FISCAL"));
        map.put("cur", new ArrayList<>());
        gnkmReportService2.getDataFrom18(map);
        List<Map<String, Object>> gnkmList = (List<Map<String, Object>>) map.get("cur");
        for (Map<String, Object> args: gnkmList){
            String ID = args.get("B_ACC_CODE").toString()+" "+args.get("B_ACC_NAME");
            args.put("ID",ID);
        }
        List<GnkmTreeNode> gnkmTreeNodeList = FormatHelper.GnkmListToTreeByIsbnCode(gnkmList);
        if (gnkmTreeNodeList.size()==0){
            jsonData = jsonData.fail("数据获取失败");
        }else{
            jsonData = jsonData.success(gnkmTreeNodeList,"数据获取成功",gnkmTreeNodeList.size());
        }
        return jsonData;
    }



    /**
     * 乡镇获取功能科目(状态：1)
     * 连接用友数据库,提取数据并将对应保存到C_REPORT_JJKM表,状态为1  并返回数据给前端
     * CO_CODE/ACC_CODE/FIS_PERD/FISCAL
     */
    @RequestMapping(value = "/GNKM/getlist")
    public @ResponseBody
    JsonData addData(HttpServletRequest request) {
        String CO_CODE = request.getParameter("CO_CODE");
        String FISCAL = request.getParameter("FISCAL");
        String FIS_PERD = request.getParameter("FIS_PERD");
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        JsonData jsonData = new JsonData();
        String isExitlist = gnkmReportService2.isExitGnkm(CO_CODE, FISCAL, FIS_PERD);
        System.out.print(isExitlist);

        if (isExitlist == null || isExitlist.equals("1")) {

            //删除本地数据库这个月的数据
            int deletecount = gnkmReportService2.deleteGnkm(FIS_PERD, FISCAL, CO_CODE);
            System.out.print("deletecount" + deletecount);
            //连接到用友数据库
            DynamicDataSourceHolder.setDataSource("dataSource18");
            //从用友数据库获取数据
            Map map = new HashMap();
            map.put("CO_CODE", CO_CODE);
            map.put("FIS_PERD", FIS_PERD);
            map.put("FISCAL", FISCAL);
            map.put("cur", new ArrayList<>());
            gnkmReportService2.getDataFrom18(map);
            List<Map<String, Object>> gnkmList = (List<Map<String, Object>>) map.get("cur");
            int i = 1;
            if (!gnkmList.isEmpty()) {
                //连接到本地数据库
                DynamicDataSourceHolder.setDataSource("dataSource1");
                //把查出的数据插入到数据库
                List list = new ArrayList();
                for (Map<String, Object> gnkm : gnkmList) {
                    GnkmReportModel gnkmReportModel = new GnkmReportModel();
                    gnkmReportModel.setB_ACC_CODE(gnkm.get("B_ACC_CODE").toString());
                    gnkmReportModel.setB_ACC_NAME(gnkm.get("B_ACC_NAME").toString());
                    gnkmReportModel.setSTAD_AMT(gnkm.get("MONEY") == null ? "" : gnkm.get("MONEY").toString()); //当月金额
                    gnkmReportModel.setXZ_STAD_AMT(gnkm.get("MONEY") == null ? "" : gnkm.get("MONEY").toString()); //累计金额
                    gnkmReportModel.setIS_LOWEST(gnkm.get("IS_LOWEST").toString()); //累计金额
                    gnkmReportModel.setFISCAL(FISCAL);
                    gnkmReportModel.setFIS_PERD(FIS_PERD);
                    gnkmReportModel.setCO_CODE(CO_CODE);
                    list.add(gnkmReportModel);
                    if (i % 100 == 0) {
                        count = gnkmReportService2.addData(list);
                        list.clear();
                        i = 1;
                    } else {
                        i++;
                    }
                }
                if (i > 1) {
                    count = gnkmReportService2.addData(list);
                }
            }
            if (count > 0) {
                Map<String, Object> args = new HashMap<>();
                args.put("CPOSTGUID", "01");
                args.put("FISCAL", FISCAL);
                args.put("FIS_PERD", FIS_PERD);
                args.put("CO_CODE", CO_CODE);
                List<Map<String, Object>> list = gnkmReportService2.getData(args);
                List<GnkmTreeNode> gnkmTreeNodeList = FormatHelper.GnkmListToTreeByIsbnCode(list);
                jsonData = jsonData.success(gnkmTreeNodeList, "报表生成", list.size());
            } else {
                jsonData = jsonData.fail("添加失败");
            }
        } else {
            if (isExitlist.equals("3")) { //不能生成报表
                jsonData = jsonData.fail("该报表已上报,无法重新生成");
            } else {
                jsonData = jsonData.fail("该报表已上报,该报表已确认");
            }
        }
        System.out.println(JSON.toJSONString(jsonData));
        return jsonData;
    }

    /**
     * 乡镇和区财政获取数据
     */
    @RequestMapping(value = "/GNKM/list", method = RequestMethod.POST)
    public @ResponseBody
    JsonData getData(@RequestBody Map<String, Object> PARAM, HttpServletRequest request, HttpServletResponse response) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();

        if (PARAM.get("STATUS").equals("1")) {
            List<Map<String, Object>> gnkmList = gnkmReportService2.getData(PARAM);
            if (gnkmList.size() == 0) {
                jsonData = jsonData.fail("未生成该报表");
            } else {
                List<GnkmTreeNode> gnkmTreeNodes = FormatHelper.GnkmListToTreeByIsbnCode(gnkmList);
                jsonData = jsonData.success(gnkmTreeNodes, "获取数据成功", gnkmList.size());
            }
        } else {
            List<Map<String, Object>> list = gnkmReportService2.getData2(PARAM);
            if (list.size() == 0) {
                jsonData = jsonData.fail("未生成该报表");
            } else {
                String[] XZName = {"金额"};
                String sheetName = PARAM.get("SHORTNAME").toString() + PARAM.get("FISCAL") + "年" + PARAM.get("FIS_PERD") + "月一般公共预算支出功能科目月报";
                String fileno = PARAM.get("SHORTNAME").toString() + PARAM.get("FISCAL") + "年" + PARAM.get("FIS_PERD") + "月一般公共预算支出功能科目月报";
                String[] head1 = {"B_ACC_CODE", "B_ACC_NAME", "ID", "MONEY"};
                Excel.exportOne(request, response, list, sheetName, fileno, head1, XZName);
            }
        }
        return jsonData;
    }

    /**
     * 乡镇对报表中的数据进行编辑(状态：1)
     * 获取前端返回的数据将金额填入XZ_STAD_AMT字段
     */
  /*  @RequestMapping(value = "/GNKM/update", method = RequestMethod.POST)
    public @ResponseBody
    JsonData updateXZMon(@RequestBody List<Map<String, Object>> list) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        List<Map<String, Object>> gnkmList = FormatHelper.findChildrenToList2(list);
        int count = gnkmReportService2.updateXZMon(gnkmList);
        if (count == 1) {
            jsonData = jsonData.success("更新成功");
        } else {
            jsonData = jsonData.fail("更新失败");
        }
        return jsonData;
    }*/

    /**
     * 对报表中的数据进行调整，XZ_STAD_AMT字段和QCZ_STAD_AMT字段使用同一接口
     *
     * @param args STTAUS,list
     * @return jsonData
     */

    @RequestMapping(value = "/GNKM/update", method = RequestMethod.POST)
    public @ResponseBody
    JsonData updateXZMon(@RequestBody Map<String, Object> args) {
        List<Map<String, Object>> list = (List<Map<String, Object>>) args.get("list");
        String STATUS = args.get("STATUS").toString();
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        List<Map<String, Object>> gnkmList = FormatHelper.findChildrenToList2(list);
        gnkmReportService2.update(gnkmList, STATUS);
        jsonData = jsonData.success("更新成功");
        return jsonData;
    }

    /**
     * 乡镇将经济科目报表上报(状态：1–—>3)
     * 将数据保存到数据库 并将金额填入XZ_STAD_AMT,QCZ_STAD_AMT字段,修改状态成3
     */
    @RequestMapping(value = "/GNKM/setid", method = RequestMethod.POST)
    public @ResponseBody
    JsonData report(@RequestBody Map<String, Object> args) {

        DynamicDataSourceHolder.setDataSource("dataSource1");
        Map<String, Object> map = new HashMap<>();
        map.put("CO_CODE", args.get("CO_CODE").toString());
        map.put("FISCAL", args.get("FISCAL").toString());
        map.put("FIS_PERD", args.get("FIS_PERD").toString());
        map.put("SETID", 3);
        List<Map<String, Object>> list = (List<Map<String, Object>>) args.get("list");
        String STATUS = args.get("STATUS").toString();
        JsonData jsonData = new JsonData();
        List<Map<String, Object>> gnkmList = FormatHelper.findChildrenToList2(list);
        gnkmReportService2.update(gnkmList, STATUS);
        gnkmReportService2.updateSetId(map);

        jsonData = jsonData.success("更新成功");

        return jsonData;
    }

    /**
     * 李晓伟退回经济科目月报 (状态3)
     * 接收指令 清空QCZ_STAD_AMT字段值,setid = 1
     */
    @RequestMapping(value = "/GNKM/qcz/goback", method = RequestMethod.POST)
    public @ResponseBody
    JsonData clearQCZ(@RequestBody List<Map<String,Object>> list) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        List<Map<String,Object>> gnkmList = FormatHelper.findChildrenToList2(list);
        int count = gnkmReportService2.clearQCZ(gnkmList);
        if (count == 1) {
            jsonData = jsonData.success("清空成功");
        } else {
            jsonData = jsonData.fail("清空失败");
        }
        return jsonData;
    }


    /**
     * 李晓伟编辑经济科目月报(状态3)
     * 后台接收数据，对数据库QCZ_STAD_AMT进行调整
     */
    @RequestMapping(value = "/GNKM/qcz/edit", method = RequestMethod.POST)
    public @ResponseBody
    JsonData editJjkm(@RequestBody Map<String, Object> PARAM) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<Map<String, Object>> list = (List<Map<String, Object>>) PARAM.get("list");
        String STATUS = PARAM.get("STATUS").toString();
        List<Map<String, Object>> gnkmList = FormatHelper.findChildrenToList2(list);
        JsonData jsonData = new JsonData();
        gnkmReportService2.update(gnkmList, STATUS);
        jsonData = jsonData.success("更新成功");
        return jsonData;
    }

    /**
     * 李晓伟确认经济科目月报(状态3–—>5)
     * 后台将数据保存到QCZ_STAD_AMT中并修改SETID为5
     */
    @RequestMapping(value = "/GNKM/qcz/enter", method = RequestMethod.POST)
    public @ResponseBody
    JsonData comfirmJJkm(@RequestBody Map<String, Object> PARAM) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<Map<String, Object>> list = (List<Map<String, Object>>) PARAM.get("list");
        String STATUS = PARAM.get("STATUS").toString();
        Map<String, Object> map = new HashMap<>();
        map.put("CO_CODE", PARAM.get("CO_CODE").toString());
        map.put("FISCAL", PARAM.get("FISCAL").toString());
        map.put("FIS_PERD", PARAM.get("FIS_PERD").toString());
        map.put("SETID", 5);
        List<Map<String, Object>> gnkmList = FormatHelper.findChildrenToList2(list);
        gnkmReportService2.update(gnkmList, STATUS);
        gnkmReportService2.updateSetId(map);
        JsonData jsonData = new JsonData();

        jsonData = jsonData.success("更新成功");
        return jsonData;
    }


    /**
     * 李晓伟取消确认经济科目月报 (状态5–——>3)
     */
    @RequestMapping("/GNKM/qcz/cancel")
    public @ResponseBody
    JsonData cancleConfirm(@RequestBody List<Map<String,Object>> list) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        List<Map<String,Object>> gnkmList = FormatHelper.findChildrenToList2(list);
        int count = gnkmReportService2.cancleConfirm(gnkmList);
        if (count == 1) {
            jsonData = jsonData.success("更新成功");
        } else {
            jsonData = jsonData.fail("更新失败");
        }
        return jsonData;

    }

    /**
     * 判断已上报至几月
     */
    @RequestMapping("/GNKM/month")
    @ResponseBody
    public JsonData MaxFis(@RequestBody Map<String,Object> PARAM) {
        String CO_CODE = PARAM.get("CO_CODE").toString();
        String FISCAL = PARAM.get("FISCAL").toString();
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int maxFis = gnkmReportService2.getMaxFis(CO_CODE, FISCAL);
        jsonData = jsonData.success(maxFis, "成功", maxFis);
        return jsonData;
    }

    /**
     * 取16个乡镇的钱
     */

    @RequestMapping(value = "/GNKM/getAllMoney", method = RequestMethod.POST)
    @ResponseBody
    public JsonData getAllMoney(@RequestBody Map<String,Object> PARAM, HttpServletRequest request, HttpServletResponse response) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String STATUS = PARAM.get("STATUS").toString();
        JsonData jsonData = new JsonData();

        if (STATUS.equals("1")) {
            List<Map<String, Object>> list = gnkmReportService2.getAllMoney2(FISCAL, FIS_PERD);
            if (list.size()==0){
                jsonData = jsonData.fail("未生成该报表");
            }else{
                List<GnkmAllMoneyTreeNode> gnkmAllMoneyTreeNodes = FormatHelper.GnkmAllMoneyListToTreeByIsbnCode(list);
                jsonData = jsonData.success(gnkmAllMoneyTreeNodes, "获取数据成功", list.size());
            }

        } else {
            List<Map<String, Object>> list = gnkmReportService2.getAllMoney(FISCAL, FIS_PERD);
            if (list.size() == 0) {
                jsonData = jsonData.fail("未生成该报表");
            } else {
                String[] head1 = new String[]{"B_ACC_CODE", "B_ACC_NAME", "ALLMONEY", "MONEY1", "MONEY2", "MONEY3", "MONEY4", "MONEY5", "MONEY6",
                        "MONEY7", "MONEY8", "MONEY9", "MONEY10", "MONEY11", "MONEY12", "MONEY13", "MONEY14", "MONEY15", "MONEY16"};
                String sheetName = FISCAL + "年" + FIS_PERD + "月柯桥区一般公共预算支出功能科目月报";
                String fileno = FISCAL + "年" + FIS_PERD + "月柯桥区一般公共预算支出功能科目月报";
                String[] head0 = new String[]{"科目编码", "科目名称", "合计数", "马鞍镇",
                        "齐贤镇", "安昌镇", "钱清镇", "扬汛桥", "夏履", "漓渚", "福全", "兰亭", "平水", "王坛", "稽东镇",
                        "柯桥街道", "柯岩街道", "华舍街道", "湖塘街道"};
                Excel.exportJJFL(request, response, list, sheetName, fileno, head0,head1);
            }
        }

        return jsonData;
    }
}
