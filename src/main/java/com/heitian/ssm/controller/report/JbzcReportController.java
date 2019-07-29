package com.heitian.ssm.controller.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.report.JbzcAllMoneyTreeNode;
import com.heitian.ssm.model.report.JbzcReportModel;
import com.heitian.ssm.model.report.JbzcTreeNode;
import com.heitian.ssm.service.report.JbzcReportService;
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
 * ClassName:JbzcReportController
 * Package:com.heitian.ssm.controller
 * Description:
 * author:@Fay
 * Date:2019/3/22 0022下午 4:31
 */
@Controller
@CrossOrigin
public class JbzcReportController {
    @Autowired
    private JbzcReportService jbzcReportService;

    @RequestMapping(value = "/JBZC/getlist")
    @ResponseBody
    public JsonData addData(HttpServletRequest request){
        String CO_CODE = request.getParameter("CO_CODE");
        String FISCAL = request.getParameter("FISCAL");
        String FIS_PERD = request.getParameter("FIS_PERD");
        DynamicDataSourceHolder.setDataSource("dataSource1");
        int i = 1;
        int count = 0;
        JsonData jsonData = new JsonData();
        String  isExitlist = jbzcReportService.isExitJbzc(CO_CODE,FISCAL,FIS_PERD);
        if (isExitlist == null || isExitlist.equals("1") ){
            //删除本地数据库这个月的数据
            int deleteCount = jbzcReportService.deleteJbzc(FIS_PERD,FISCAL,CO_CODE);
            System.out.println(deleteCount);
            //连接到用友数据库
            DynamicDataSourceHolder.setDataSource("dataSource18");
            //从用友数据库获取数据
            Map map = new HashMap();
            map.put("CO_CODE",CO_CODE);
            map.put("FIS_PERD",FIS_PERD);
            map.put("FISCAL",FISCAL);
            map.put("cur",new ArrayList<>());
            jbzcReportService.getDataFrom18(map);

            List<Map<String,Object>> jbzcList = (List<Map<String, Object>>) map.get("cur");
            if (!jbzcList.isEmpty()){
                //连接到本地数据库
                DynamicDataSourceHolder.setDataSource("dataSource1");
                //把查出的数据插入到数据库
                List list = new ArrayList();
                for(Map<String,Object> jbzc:jbzcList){
                    JbzcReportModel jbzcReportModel = new JbzcReportModel();
                    jbzcReportModel.setGOV_OUTLAY_CODE(jbzc.get("GOV_OUTLAY_CODE").toString());
                    jbzcReportModel.setGOV_OUTLAY_NAME(jbzc.get("GOV_OUTLAY_NAME").toString());
                    jbzcReportModel.setSTAD_AMT(jbzc.get("MONEY")==null?"":jbzc.get("MONEY").toString());
                    jbzcReportModel.setXZ_STAD_AMT(jbzc.get("MONEY")==null?"":jbzc.get("MONEY").toString());
                    jbzcReportModel.setIS_LOWEST(jbzc.get("IS_LOWEST").toString());
                    jbzcReportModel.setFISCAL(FISCAL);
                    jbzcReportModel.setFIS_PERD(FIS_PERD);
                    jbzcReportModel.setCO_CODE(CO_CODE);
                    list.add(jbzcReportModel);
                    if (i%100==0){
                        count = jbzcReportService.addData(list);
                        i=1;
                        list.clear();
                    }else{
                        i++;
                    }
                }
                if (i>1) {
                    count = jbzcReportService.addData(list);
                }
            }
            if (count > 0){
                Map<String,Object> args = new HashMap<>();
                args.put("CPOSTGUID","01");
                args.put("FISCAL",FISCAL);
                args.put("FIS_PERD",FIS_PERD);
                args.put("CO_CODE",CO_CODE);
                List<Map<String,Object>> list = jbzcReportService.getData(args);
                List<JbzcTreeNode> jbzcTreeNodes = FormatHelper.JbzcListToTreeByIsbnCode(list);
                System.out.println(JSON.toJSONString(jbzcTreeNodes));
                jsonData = jsonData.success(jbzcTreeNodes,"添加成功",list.size());
            }else{
                jsonData = jsonData.fail("添加失败");
            }

        }else {
            if (isExitlist.equals("3")) { //不能生成报表
                jsonData = jsonData.fail("该报表已上报,无法重新生成");
            }else {
                jsonData = jsonData.fail("该报表已上报,该报表已确认");

            }
        }


        return jsonData;
    }


    /**
     * 乡镇和操作员获取数据
     */
    @RequestMapping(value = "/JBZC/list",method = RequestMethod.POST)
    @ResponseBody
    public JsonData getData(@RequestBody String PARAM, HttpServletRequest request,HttpServletResponse response){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        Map<String,Object> args = JSON.parseObject(PARAM,new TypeReference<Map<String, Object>>(){});
        JsonData jsonData = new JsonData();
        List<Map<String,Object>> jbzcList = jbzcReportService.getData(args);
        if (args.get("STATUS").equals("1")){
            if (jbzcList.size()==0){
                jsonData = jsonData.fail("未生成报表");
            }else{
                List<JbzcTreeNode> jbzcTreeNodes = FormatHelper.JbzcListToTreeByIsbnCode(jbzcList);
                jsonData = jsonData.success(jbzcTreeNodes,"数据获取成功",jbzcList.size());
            }
        }else{
            if (jbzcList.size()==0){
                jsonData = jsonData.fail("未生成该报表");
            }else {
                List<Map<String,Object>> jbzcList2 = jbzcReportService.getData2(args);
                String[] XZName = {"金额"};
                String sheetName = args.get("SHORTNAME").toString() + args.get("FISCAL") + "年" + args.get("FIS_PERD") + "月一般公共预算基本支出科目月报";
                String fileno = args.get("SHORTNAME").toString() + args.get("FISCAL") + "年" + args.get("FIS_PERD") + "月一般公共预算基本支出科目月报";
                String[] head1 = {"GOV_OUTLAY_CODE", "GOV_OUTLAY_NAME", "ID", "MONEY"};
                Excel.exportOne(request, response, jbzcList2, sheetName, fileno, head1, XZName);
            }
        }
        return jsonData;
    }


    /**
     * 乡镇对报表中的数据进行编辑(状态：1)
     * 获取前端返回的数据将金额填入XZ_STAD_AMT字段
     */
    @RequestMapping(value = "/JBZC/update",method = RequestMethod.POST)
    @ResponseBody
    public JsonData updateXZMon(@RequestBody List<Map<String,Object>> list){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData=new JsonData();
        List<Map<String,Object>> jbzcList = FormatHelper.findChildrenToList(list);

        int count = jbzcReportService.updateXZMon(jbzcList);
        if (count == 1){
            jsonData = jsonData.success("更新成功");
        }else{
            jsonData = jsonData.fail("更新失败");
        }
        return jsonData;
    }



    /**
     * 乡镇将经济科目报表上报(状态：1–—>3)
     * 将数据保存到数据库 并将金额填入XZ_STAD_AMT,QCZ_STAD_AMT字段,修改状态成3
     */
    @RequestMapping(value = "/JBZC/setid",method = RequestMethod.POST)
    @ResponseBody
    public JsonData report(@RequestBody List<Map<String,Object>> list){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData=new JsonData();
        List<Map<String,Object>> jbzcList = FormatHelper.findChildrenToList(list);
        int count = jbzcReportService.report(jbzcList);
        if (count == 1){
            jsonData = jsonData.success("更新成功");
        }else{
            jsonData = jsonData.fail("更新失败");
        }
        return jsonData;
    }

    /**
     * 李晓伟确认经济科目月报(状态3–—>5)
     *后台将数据保存到QCZ_STAD_AMT中并修改SETID为5
     */
    @RequestMapping(value = "/JBZC/qcz/enter",method = RequestMethod.POST)
    @ResponseBody
    public JsonData comfirmJbzc(@RequestBody List<Map<String,Object>> list){
        DynamicDataSourceHolder.setDataSource("dataSource1");
       List<Map<String,Object>> jbzcList = FormatHelper.findChildrenToList2(list);

        int count = jbzcReportService.qczReport(jbzcList);
        JsonData jsonData = new JsonData();
        if (count == 1){
            jsonData = jsonData.success("更新成功");
        }else{
            jsonData = jsonData.fail("更新失败");
        }
        return jsonData;
    }

    /**
     *李晓伟编辑经济科目月报(状态3)
     * 后台接收数据，对数据库QCZ_STAD_AMT进行调整
     */
    @RequestMapping(value = "/JBZC/qcz/edit",method = RequestMethod.POST)
    @ResponseBody
    public JsonData editJjkm(@RequestBody List<Map<String,Object>> list){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData=new JsonData();
        List<Map<String,Object>> jbzcList = FormatHelper.findChildrenToList2(list);
        int count = jbzcReportService.editJbzc(jbzcList);
        if (count == 1){
            jsonData = jsonData.success("更新成功");
        }else{
            jsonData = jsonData.fail("更新失败");
        }

        return jsonData;

    }



    /**
     * 李晓伟取消确认经济科目月报 (状态5–——>3)
     * 修改状态值从5变成3
     */
    @RequestMapping(value = "/JBZC/qcz/cancel",method = RequestMethod.POST)
    @ResponseBody
    public JsonData cancleConfirm(@RequestBody List<Map<String,Object>> list){
        DynamicDataSourceHolder.setDataSource("dataSource1");
       List<Map<String,Object>> jbzcList = FormatHelper.findChildrenToList2(list);

        JsonData jsonData = new JsonData();
        int count = jbzcReportService.cancleConfirm(jbzcList);
        if (count == 1){
            jsonData = jsonData.success("更新成功");
        }else{
            jsonData = jsonData.fail("更新失败");
        }
        return jsonData;

    }


    /**
     *判断已上报至几月
     */
    @RequestMapping("/JBZC/month")
    @ResponseBody
    public JsonData MaxFis(@RequestBody Map<String,Object> PARAM){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int maxFis = jbzcReportService.getMaxFis(PARAM.get("CO_CODE").toString(),PARAM.get("FISCAL").toString());
        jsonData = jsonData.success(maxFis,"获取数据成功",maxFis);
        return jsonData;
    }



    /**
     * 李晓伟退回（5-->1）
     */
    @RequestMapping(value = "/JBZC/qcz/goback",method = RequestMethod.POST)
    public @ResponseBody JsonData back(@RequestBody List<Map<String,Object>> list){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<Map<String,Object>> jbzcList = FormatHelper.findChildrenToList2(list);

        JsonData jsonData = new JsonData();
        int count = jbzcReportService.back(jbzcList);
        if (count == 1){
            jsonData = jsonData.success("更新成功");
        }else{
            jsonData = jsonData.fail("更新失败");
        }
        return jsonData;
    }

    /**
     * 取16个乡镇的钱
     */

    @RequestMapping(value = "/JBZC/getAllMoney" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonData getAllMoney(@RequestBody Map<String,Object> PARAM, HttpServletRequest request,HttpServletResponse response){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FISCAL = PARAM.get("FISCAL").toString();
        String FIS_PERD = PARAM.get("FIS_PERD").toString();
        String STATUS = PARAM.get("STATUS").toString();


        JsonData jsonData = new JsonData();
        if (STATUS.equals("1")){
            List<Map<String,Object>> list = jbzcReportService.getAllMoney2(FIS_PERD,FISCAL);
            if (list.size()==0){
                jsonData = jsonData.fail("未生成该报表");
            }else{
                List<JbzcAllMoneyTreeNode> jbzcAllMoneyTreeNodes = FormatHelper.JbzcAllMoneyListToTreeByIsbnCode(list);
                jsonData = jsonData.success(jbzcAllMoneyTreeNodes,"获取数据成功",list.size());
            }

        }else{
            List<Map<String,Object>> list = jbzcReportService.getAllMoney(FIS_PERD,FISCAL);
            if (list.size()==0){
                jsonData = jsonData.fail("未生成该报表");
            }else{
                String[] head1 = new String[]{"GOV_OUTLAY_CODE","GOV_OUTLAY_NAME","ALLMONEY","MONEY1","MONEY2","MONEY3","MONEY4","MONEY5","MONEY6",
                        "MONEY7","MONEY8","MONEY9","MONEY10","MONEY11","MONEY12","MONEY13","MONEY14","MONEY15","MONEY16"};
                String sheetName = FISCAL+"年"+FIS_PERD+"月柯桥区一般公共预算基本支出科目月报";
                String fileno = FISCAL+"年"+FIS_PERD+"月柯桥区一般公共预算基本支出科目月报";
                String[] head0 = new String[]{"科目编码", "科目名称", "合计数", "马鞍镇",
                        "齐贤镇", "安昌镇", "钱清镇", "扬汛桥", "夏履", "漓渚", "福全", "兰亭", "平水", "王坛", "稽东镇",
                        "柯桥街道", "柯岩街道", "华舍街道", "湖塘街道"};
                Excel.exportJJFL(request,response,list,sheetName,fileno,head0,head1);
            }

        }
        return jsonData;

    }

    /**
     * 汇总
     */
    @RequestMapping("/JBZC/collect")
    @ResponseBody
    public JsonData huizong (HttpServletRequest request){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String tableName = request.getParameter("tableName");
        JsonData jsonData = new JsonData();
        List<Map<String,Object>> list = jbzcReportService.huizong(tableName);
        jsonData = jsonData.success(list,"成功",list.size());
        return jsonData;
    }



}
