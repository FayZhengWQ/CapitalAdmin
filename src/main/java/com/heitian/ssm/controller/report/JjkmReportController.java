package com.heitian.ssm.controller.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.report.JjkmAllMoneyTreeNode;
import com.heitian.ssm.model.report.JjkmReportModel;
import com.heitian.ssm.model.report.JjkmTreeNode;
import com.heitian.ssm.service.report.JjkmReportService;
import com.heitian.ssm.utils.FormatHelper;
import com.heitian.ssm.utils.excel.Excel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:JjkmReportController
 * Package:com.heitian.ssm.controller
 * Description:
 * author:@Fay
 * Date:2019/3/19 0019下午 2:23
 */
@Controller
@CrossOrigin
public class JjkmReportController {

    @Resource
    JjkmReportService jjkmReportService;


    /*测试*/
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public List<JjkmTreeNode> Test(@RequestBody List<Map<String,Object>> list) {
        List<Map<String,Object>> childrenToList = FormatHelper.findChildrenToList(list);
        System.out.println(JSON.toJSONString(childrenToList));
        return null;
    }

    /**
     * 乡镇获取经济科目(状态：1)
     * 连接用友数据库,提取数据并将对应保存到C_REPORT_JJKM表,状态为1  并返回数据给前端
     * CO_CODE/ACC_CODE/FIS_PERD/FISCAL
     */
    @RequestMapping(value = "/JJKM/getlist")
    public @ResponseBody
    JsonData addData(HttpServletRequest request) {
        String CO_CODE = request.getParameter("CO_CODE");
        String FISCAL = request.getParameter("FISCAL");
        String FIS_PERD = request.getParameter("FIS_PERD");
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int count = 0;
        int i = 1;
        JsonData jsonData = new JsonData();
        String isExitlist = jjkmReportService.isExitJjkm(CO_CODE, FISCAL, FIS_PERD);
        System.out.print(isExitlist);

        if (isExitlist == null || isExitlist.equals("1")) {

            //删除本地数据库这个月的数据
            int deletecount = jjkmReportService.deleteJjkm(FIS_PERD, FISCAL, CO_CODE);
            System.out.print("deletecount" + deletecount);
            //连接到用友数据库
            DynamicDataSourceHolder.setDataSource("dataSource18");
            //从用友数据库获取数据
            Map map = new HashMap();
            map.put("CO_CODE", CO_CODE);
            map.put("FIS_PERD", FIS_PERD);
            map.put("FISCAL", FISCAL);
            map.put("cur", new ArrayList<>());
            jjkmReportService.getDataFrom18(map);
            List<Map<String, Object>> jjkmList = (List<Map<String, Object>>) map.get("cur");

            if (!jjkmList.isEmpty()) {
                //连接到本地数据库
                DynamicDataSourceHolder.setDataSource("dataSource1");
                //把查出的数据插入到数据库
                List list = new ArrayList();
                for (Map<String, Object> jjkm : jjkmList) {
                    JjkmReportModel jjkmReportModel = new JjkmReportModel();
                    jjkmReportModel.setOUTLAY_CODE(jjkm.get("OUTLAY_CODE").toString());
                    jjkmReportModel.setOUTLAY_NAME(jjkm.get("OUTLAY_NAME").toString());
                    jjkmReportModel.setSTAD_AMT(jjkm.get("MONEY")==null?"":jjkm.get("MONEY").toString()); //当月金额
                    jjkmReportModel.setXZ_STAD_AMT(jjkm.get("MONEY")==null?"":jjkm.get("MONEY").toString()); //累计金额
                    jjkmReportModel.setIS_LOWEST(jjkm.get("IS_LOWEST").toString()); //累计金额
                    jjkmReportModel.setFISCAL(FISCAL);
                    jjkmReportModel.setFIS_PERD(FIS_PERD);
                    jjkmReportModel.setCO_CODE(CO_CODE);
                    list.add(jjkmReportModel);
                    if (i%100==0){
                        count = jjkmReportService.addData(list);
                        i=1;
                        list.clear();
                    }else{
                        i++;
                    }
                }
                if (i>1){
                    count = jjkmReportService.addData(list);
                }

            }
            if (count > 0) {
                Map<String,Object> args = new HashMap<>();
                args.put("CPOSTGUID","01");
                args.put("FISCAL",FISCAL);
                args.put("FIS_PERD",FIS_PERD);
                args.put("CO_CODE",CO_CODE);
                List<Map<String, Object>> list = jjkmReportService.getData(args);
                List<JjkmTreeNode> jjkmTreeNodes = FormatHelper.JjkmListToTreeByIsbnCode(list);
                jsonData = jsonData.success(jjkmTreeNodes, "报表生成", list.size());
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
        return jsonData;
    }

    /**
     * 乡镇和区财政获取数据
     */
    @RequestMapping(value = "/JJKM/list", method = RequestMethod.POST)
    public @ResponseBody
    JsonData getData(@RequestBody String PARAM, HttpServletRequest request, HttpServletResponse response) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();

        Map<String, Object> args   = JSON.parseObject(PARAM, new TypeReference<HashMap<String,Object>>() {
        });

        List<Map<String, Object>> jjkmList = jjkmReportService.getData(args);
        if (args.get("STATUS").equals("1")) {
            if (jjkmList.size() == 0) {
                jsonData = jsonData.fail("未生成该报表");
            } else {
                List<JjkmTreeNode> jjkmTreeNodes = FormatHelper.JjkmListToTreeByIsbnCode(jjkmList);
                jsonData = jsonData.success(jjkmTreeNodes, "获取数据成功", jjkmList.size());
            }
        } else {
            if (jjkmList.size() == 0) {
                jsonData = jsonData.fail("未生成该报表");
            } else {
                List<Map<String, Object>> jjkmList2 = jjkmReportService.getData2(args);
                String[] XZName = {"金额"};
                String sheetName = args.get("SHORTNAME").toString() + args.get("FISCAL") + "年" + args.get("FIS_PERD") + "月政府经济科目支出月报";
                String fileno = args.get("SHORTNAME").toString() + args.get("FISCAL") + "年" +args.get("FIS_PERD") + "月政府经济科目支出月报";
                String[] head1 = {"OUTLAY_CODE", "OUTLAY_NAME", "ID", "MONEY"};
                Excel.exportOne(request, response, jjkmList2, sheetName, fileno, head1, XZName);
            }
        }
        return jsonData;
    }

    /**
     * 乡镇对报表中的数据进行编辑(状态：1)
     * 获取前端返回的数据将金额填入XZ_STAD_AMT字段
     */
    @RequestMapping(value = "/JJKM/update", method = RequestMethod.POST)
    public @ResponseBody
    JsonData updateXZMon(@RequestBody List<Map<String,Object>> list) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        List<Map<String,Object>> jjkmList = FormatHelper.findChildrenToList(list);
        int count = jjkmReportService.updateXZMon(jjkmList);
        if (count == 1) {
            jsonData = jsonData.success("更新成功");
        } else {
            jsonData = jsonData.fail("更新失败");
        }
        return jsonData;
    }

    /**
     * 乡镇将经济科目报表上报(状态：1–—>3)
     * 将数据保存到数据库 并将金额填入XZ_STAD_AMT,QCZ_STAD_AMT字段,修改状态成3
     */
    @RequestMapping(value = "/JJKM/setid", method = RequestMethod.POST)
    public @ResponseBody
    JsonData report(@RequestBody List<Map<String,Object>> list) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        List<Map<String,Object>> jjkmList = FormatHelper.findChildrenToList(list);
        int count = jjkmReportService.report(jjkmList);
        if (count == 1) {
            jsonData = jsonData.success("更新成功");
        } else {
            jsonData = jsonData.fail("更新失败");
        }
        return jsonData;
    }

    /**
     * 李晓伟退回经济科目月报 (状态3)
     * 接收指令 清空QCZ_STAD_AMT字段值,setid = 1
     */
    @RequestMapping(value = "/JJKM/qcz/goback", method = RequestMethod.POST)
    public @ResponseBody
    JsonData clearQCZ(@RequestBody List<Map<String,Object>> list) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<Map<String,Object>> jjkmList = FormatHelper.findChildrenToList2(list);
        JsonData jsonData = new JsonData();
        int count = jjkmReportService.clearQCZ(jjkmList);
        if (count == 1) {
            jsonData = jsonData.success("清空成功");
        } else {
            jsonData = jsonData.fail("清空失败");
        }
        return jsonData;
    }

    /**
     * 李晓伟确认经济科目月报(状态3–—>5)
     * 后台将数据保存到QCZ_STAD_AMT中并修改SETID为5
     */
    @RequestMapping(value = "/JJKM/qcz/enter", method = RequestMethod.POST)
    public @ResponseBody
    JsonData comfirmJJkm(@RequestBody List<Map<String,Object>> list) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<Map<String,Object>> jjkmList = FormatHelper.findChildrenToList2(list);
        int count = jjkmReportService.qczReport(jjkmList);
        JsonData jsonData = new JsonData();
        if (count == 1) {
            jsonData = jsonData.success("更新成功");
        } else {
            jsonData = jsonData.fail("更新失败");
        }
        return jsonData;
    }

    /**
     * 李晓伟编辑经济科目月报(状态3)
     * 后台接收数据，对数据库QCZ_STAD_AMT进行调整
     */
    @RequestMapping(value = "/JJKM/qcz/edit", method = RequestMethod.POST)
    public @ResponseBody
    JsonData editJjkm(@RequestBody List<Map<String,Object>> list) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        List<Map<String,Object>> jjkmList = FormatHelper.findChildrenToList2(list);
        int count = jjkmReportService.editJjkm(jjkmList);
        if (count == 1) {
            jsonData = jsonData.success("更新成功");
        } else {
            jsonData = jsonData.fail("更新失败");
        }

        return jsonData;

    }


    /**
     * 李晓伟取消确认经济科目月报 (状态5–——>3)
     */
    @RequestMapping("/JJKM/qcz/cancel")
    public @ResponseBody
    JsonData cancleConfirm(@RequestBody List<Map<String,Object>> list) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<Map<String,Object>> jjkmList = FormatHelper.findChildrenToList2(list);
        JsonData jsonData = new JsonData();
        int count = jjkmReportService.cancleConfirm(jjkmList);
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
    @RequestMapping("/JJKM/month")
    @ResponseBody
    public JsonData MaxFis(@RequestBody Map<String,Object> PARAM) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int maxFis = jjkmReportService.getMaxFis(PARAM.get("CO_CODE").toString(), PARAM.get("FISCAL").toString());
        jsonData = jsonData.success(maxFis, "成功", maxFis);
        return jsonData;
    }

    /**
     * 取16个乡镇的钱
     */

    @RequestMapping(value = "/JJKM/getAllMoney", method = RequestMethod.POST)
    @ResponseBody
    public JsonData getAllMoney(@RequestBody Map<String,Object> PARAM, HttpServletRequest request, HttpServletResponse response) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String STATUS = PARAM.get("STATUS").toString();
        JsonData jsonData = new JsonData();


        if (STATUS.equals("1")) {
            //查看
            List<Map<String, Object>> list = jjkmReportService.getAllMoney2(FISCAL, FIS_PERD);
            if (list.size() == 0) {
                jsonData = jsonData.fail("未生成该报表");
            } else {
                List<JjkmAllMoneyTreeNode> jjkmAllMoneyTreeNodes = FormatHelper.JjkmAllMoneyListToTreeByIsbnCode(list);
                jsonData = jsonData.success(jjkmAllMoneyTreeNodes, "获取数据成功", list.size());
            }

        } else {
            //下载
            List<Map<String, Object>> list = jjkmReportService.getAllMoney(FISCAL, FIS_PERD);
            if (list.size() == 0) {
                jsonData = jsonData.fail("未生成该报表");
            } else {

                String[] head1 = new String[]{"OUTLAY_CODE", "OUTLAY_NAME", "ALLMONEY", "MONEY1", "MONEY2", "MONEY3", "MONEY4", "MONEY5", "MONEY6",
                        "MONEY7", "MONEY8", "MONEY9", "MONEY10", "MONEY11", "MONEY12", "MONEY13", "MONEY14", "MONEY15", "MONEY16"};
                String sheetName = FISCAL + "年" + FIS_PERD + "月柯桥区政府经济科目支出月报";
                String fileno = FISCAL + "年" + FIS_PERD + "月柯桥区政府经济科目支出月报";
                String[] head0 = new String[]{"编码", "支出科目", "合计数", "马鞍镇",
                        "齐贤镇", "安昌镇", "钱清镇", "扬汛桥", "夏履", "漓渚", "福全", "兰亭", "平水", "王坛", "稽东镇",
                        "柯桥街道", "柯岩街道", "华舍街道", "湖塘街道"};
                Excel.exportJJFL(request, response, list, sheetName, fileno,head0, head1);
            }
        }

        return jsonData;
    }


}
