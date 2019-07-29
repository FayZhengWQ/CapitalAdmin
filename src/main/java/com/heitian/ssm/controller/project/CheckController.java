package com.heitian.ssm.controller.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.project.CheckModel;
import com.heitian.ssm.model.project.Project;
import com.heitian.ssm.service.project.CheckService;
import com.heitian.ssm.service.project.ProjectService;
import com.heitian.ssm.utils.excel.Excel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description: 抽查巡查
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-10 19:03
 **/
@Controller
@CrossOrigin
public class CheckController {

    @Resource
    CheckService checkService;

    @Resource
    ProjectService projectService;

    @RequestMapping("/check")  //获取dprog列表
    @ResponseBody
    public JsonData CheckTable(HttpServletRequest request, HttpServletResponse response) {

        DynamicDataSourceHolder.setDataSource("dataSource1");
        String CBMUNITID = request.getParameter("CBMUNITID");//区级编码
        //筛选条件
        String FILENO = request.getParameter("FILENO");//文号
        String CXZPROGNAME = request.getParameter("CXZPROGNAME");//项目名称
        String CBMUNITNAME = request.getParameter("CBMUNITNAME");//单位名称
        //分页
        String IYEAR = request.getParameter("IYEAR");
        String STATUS = request.getParameter("STATUS");

        Project bean = new Project();
        bean.setCPROGTYPE("");
        bean.setCBMUNITID(CBMUNITID);
        bean.setFILENO(FILENO);
        bean.setCXZPROGNAME(CXZPROGNAME);
        bean.setTRANSFER_STATE("");
        bean.setPAY_STATE("");
        bean.setCBMUNITNAME(CBMUNITNAME);

        bean.setCFUNCTIONCODE("");
        bean.setCECONOMYSECTIONCODE("");
        bean.setCECONOMYSECTIONGOVCODE("");
        bean.setCBUDGETCATEGORYCODE("");
        bean.setCRESOURCECODE("");
        bean.setIYEAR(IYEAR);

        JsonData jsonData = new JsonData(true);
        if (STATUS.equals("1")){
            Integer PageSize = Integer.valueOf(request.getParameter("PageSize"));//每页条数
            Integer PageIndex = Integer.valueOf(request.getParameter("PageIndex"));//页码
            PageHelper.startPage(PageIndex, PageSize);

            //获取数据
            List<Project> list = projectService.selectAllDprog(bean);

            PageInfo<Project> pageInfo = new PageInfo<>(list);

//        List<Project> projectList = BasicUtils.TableList(list);

            jsonData = jsonData.success(list, "获取数据成功", Integer.valueOf(String.valueOf(pageInfo.getTotal())));
        }else{
            List<Project> list = projectService.selectAllDprog(bean);
            String sheetName = "xxx";
            String fileno = "ccc";
            Excel.reportProg(request,response,list,sheetName,fileno);
        }




        return jsonData;

    }

    @RequestMapping("/check/add")   //添加抽查单
    @ResponseBody
    public JsonData CheckAdd(HttpServletRequest request) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String CXZPROGID = request.getParameter("CXZPROGID");//乡镇项目编码
        String CXZPROGNAME = request.getParameter("CXZPROGNAME");//乡镇项目名称
        String DCHECKDATE = request.getParameter("DCHECKDATE");//巡查日期
        String DCHECKPJ = request.getParameter("DCHECKPJ");//巡查评价
        String DCHECKRY = request.getParameter("DCHECKRY");//巡查人员
        String SUBSIDYCONTENT = request.getParameter("SUBSIDYCONTENT");//补助内容
        String PROJECTTYPE1 = request.getParameter("PROJECTTYPE1");
        String PROJECTTYPE2 = request.getParameter("PROJECTTYPE2");
        String PROJECTTYPE3 = request.getParameter("PROJECTTYPE3");
        String PROJECTTYPE4 = request.getParameter("PROJECTTYPE4");
        String CBMUNITID = request.getParameter("CBMUNITID");
        String CBMUNITNAME = request.getParameter("CBMUNITNAME");
        String CXZENTERPRISEID = request.getParameter("CXZENTERPRISEID");

        String GDATE = request.getParameter("GDATE");
        String YEAR = request.getParameter("YEAR");
        String MONTH = request.getParameter("MONTH");
        String SHORTNAME = request.getParameter("SHORTNAME");
        String PNO = request.getParameter("PNO");

        CheckModel checkModel = new CheckModel();
        checkModel.setCXZPROGID(CXZPROGID);
        checkModel.setCXZPROGNAME(CXZPROGNAME);
        checkModel.setDCHECKDATE(DCHECKDATE);
        checkModel.setDCHECKPJ(DCHECKPJ);
        checkModel.setDCHECKRY(DCHECKRY);
        checkModel.setCBMUNITID(CBMUNITID);
        checkModel.setCBMUNITNAME(CBMUNITNAME);
        checkModel.setSUBSIDYCONTENT(SUBSIDYCONTENT);
        checkModel.setPROJECTTYPE1(PROJECTTYPE1);
        checkModel.setPROJECTTYPE2(PROJECTTYPE2);
        checkModel.setPROJECTTYPE3(PROJECTTYPE3);
        checkModel.setPROJECTTYPE4(PROJECTTYPE4);
        checkModel.setYEAR(YEAR);
        checkModel.setMONTH(MONTH);
        checkModel.setCXZENTERPRISEID(CXZENTERPRISEID);
        checkModel.setSHORTNAME(SHORTNAME);
        checkModel.setPNO(PNO);
        checkModel.setGDATE(GDATE);

        int rows = checkService.insertCheck(checkModel);
        JsonData jsonData = new JsonData();
        System.out.print("row:" + rows);
        if (rows == 1) {  //插入成功 修改dprog中CHECK_STATE标签
            int row = projectService.UpdateCheckState(checkModel);
            if (row == 1) {
                jsonData = new JsonData(true);
                jsonData = jsonData.success("操作成功");
            } else {
                jsonData = new JsonData(false);
                jsonData = jsonData.success("添加失败");
            }
        } else {
            jsonData = new JsonData(false);
            jsonData = jsonData.fail("添加失败");
        }
        return jsonData;
    }


    @RequestMapping("/check/list") //单项目抽查列表
    @ResponseBody
    public JsonData CheckList(HttpServletRequest request) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String CBMUNITID = request.getParameter("CBMUNITID");
        String CBMUNITNAME = request.getParameter("CBMUNITNAME");
        String PNO = request.getParameter("PNO");

        CheckModel checkModel = new CheckModel();
        checkModel.setCBMUNITID(CBMUNITID);
        checkModel.setCBMUNITNAME(CBMUNITNAME);
        checkModel.setPNO(PNO);

        List<CheckModel> list = checkService.selectCheck(checkModel);

        JsonData jsonData = new JsonData(true);
        jsonData = jsonData.success(list, "获取成功", list.size());

        return jsonData;
    }


    @RequestMapping("/check/editor")
    @ResponseBody
    public ModelAndView CheckPrint(@RequestParam Map<String, Object> model) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String PNO = model.get("PNO").toString();
        CheckModel checkModel = new CheckModel();
        
        checkModel.setPNO(model.get("PNO").toString());
        checkModel.setCBMUNITID(model.get("CBMUNITID").toString());
        checkModel.setYEAR(model.get("YEAR").toString());

        List<CheckModel> list = checkService.SelectCheckId(checkModel);

        ModelAndView mv = new ModelAndView("checkprint");
        mv.addObject("PNO", PNO);
        if (list.get(0).getBCHECKDOC() == null || (list.get(0).getBCHECKDOC() != null && list.get(0).getBCHECKDOC().equals("")))
            mv.addObject("content", "<h2 style='white-space: normal; text-align: center;'><span style='font-family: 宋体, SimSun;'>乡镇财政资金监管巡查签证单<br/></span></h2><p style='white-space: normal; text-align: right;'><span style='font-family: 宋体, SimSun;font-size:14px;'>" +
                    "单号：" +
                    "</span></p><p style='white-space: normal;'><span style='font-family: 宋体, SimSun;font-size:14px;'>" +
                    "被查单位：" + list.get(0).getCBMUNITNAME() +
                    "</span></p><table><tbody><tr class='firstRow' style='height:71px;'><td width='1133' valign='top' style='word-break: break-all;'><p><span style='font-family: 宋体, SimSun;font-size:14px;'><br/></span></p><p><span style='font-family: 宋体, SimSun;font-size:14px;'>" +
                    "巡查的资金项目名称：" + list.get(0).getCXZPROGNAME() +
                    "<br/></span></p></td></tr><tr style='height:154px;'><td width='1133' valign='top' style='word-break: break-all;'><p><span style='font-family:宋体, SimSun;font-size:14px;'>资金的使用情况：</span></p><p style='text-indent: 28px;'><span style='font-family:宋体, SimSun;font-size:14px;'>" +
                    "</span></p></td></tr><tr style='height:129px;'><td width='1133' valign='top' style='word-break: break-all;'><p><span style='font-family: 宋体, SimSun;font-size:14px;'>巡查的主要资料内容："+ list.get(0).getSUBSIDYCONTENT() +"</span></p><p><span style='font-family: 宋体, SimSun;font-size:14px;'><br/></span></p></td></tr><tr><td width='1133' valign='top' style='word-break: break-all;'><p><span style='font-family: 宋体, SimSun;font-size:14px;'>巡查结论：</span></p><p style='text-indent: 28px;'><span style='font-family: 宋体, SimSun;font-size:14px;'><br/></span></p><p style='text-indent: 28px;'><span style='font-family: 宋体, SimSun;font-size: 22px;'>符合规定</span></p><p><span style='font-family: 宋体, SimSun;font-size:14px;'><br/></span></p><p><span style='font-family: 宋体, SimSun;font-size:14px;'><br/></span></p><p><span style='font-family: 宋体, SimSun;font-size:14px;'><br/></span></p><p><span style='font-family: 宋体, SimSun;font-size:14px;'><br/></span></p></td></tr><tr><td width='1133' valign='top' style='word-break: break-all;'><p><span style='font-family: 宋体, SimSun;font-size:14px;'><br/></span></p><p><span style='font-family: 宋体, SimSun;font-size:14px;'>被检查单位相关人员签证</span></p><p><span style='font-family: 宋体, SimSun;font-size:14px;'><br/></span></p><p style='text-indent: 28px;'><span style='font-family: 宋体, SimSun; font-size: 22px;'>情况属实</span></p><p><span style='font-family: 宋体, SimSun;font-size:14px;'><br/></span></p><p><span style='font-family: 宋体, SimSun;font-size:14px;'><br/></span></p><p style='text-indent: 266px;'><span style='font-family: 宋体, SimSun;font-size:14px;'>经办人员或主管人员签字：</span></p><p style='text-indent: 266px;'><span style='font-family: 宋体, SimSun;font-size:14px;'><br/></span></p><p><span style='font-family: 宋体, SimSun;font-size:14px;'><br/></span></p></td></tr></tbody></table><p style='text-align: left;'><span style='font-family: 宋体, SimSun;font-size:14px;'>巡查组制单人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;巡查组组长：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期：</span></p><p><span style='font-family: 宋体, SimSun;font-size:14px;'><br/></span></p><p style='text-indent: 28px;'><span style='font-family: 宋体, SimSun;'><strong>注：</strong>1、此工作签证单需一事一单，并摘录巡查事项发生的日期、内容等。</span></p><p style='text-indent: 56px;'><span style='font-family: 宋体, SimSun;'>2、被巡查单位相关人员签证需认定巡查工作签证单摘录的事项是否真实，如属实，签“情况属实”；如有不同意见，应说明理由，并附相关证据材料。</span></p>");
        else mv.addObject("content", list.get(0).getBCHECKDOC());
        return mv;
    }

    @RequestMapping("/check/save")
    @ResponseBody
    public JsonData CheckSave(@RequestParam Map<String, Object> model) {

        String PNO = model.get("PNO").toString();
        String BCHECKDOC = model.get("BCHECKDOC").toString();

        CheckModel checkModel = new CheckModel();
        checkModel.setBCHECKDOC(BCHECKDOC);
        checkModel.setPNO(PNO);

        int row = checkService.UpdateCheckDoc(checkModel);

        JsonData jsonData = new JsonData();

        if (row == 1) {
            jsonData = jsonData.success("保存成功");
        } else {
            jsonData = jsonData.fail("保存失败");
        }
        return jsonData;

    }




    /**
     * 一键刷公告公示
     */
    @RequestMapping(value = "/check/falsh",method = RequestMethod.POST)
    @ResponseBody
    public JsonData FlashNotice(HttpServletRequest request){

//        String YEAR=request.getParameter("YEAR");
//        String CBMUNITID=request.getParameter("CBMUNITID");
        List<Map<String, Object>> dataList = JSON.parseObject(request.getParameter("list"), new TypeReference<ArrayList<Map<String, Object>>>() {});

//        先查位置
//        int rownum =checkService.selectCount(YEAR,CBMUNITID);

        int count = checkService.falsh(dataList);

        if(count>0){
            for (int i = 0; i < dataList.size(); i++) {
                CheckModel checkModel=new CheckModel();
                checkModel.setPNO(dataList.get(i).get("PNO").toString());
                projectService.UpdateCheckState(checkModel);
            }

        }

        JsonData jsonData=new JsonData(true);


        return jsonData;
    }


    /**
     * selectCount
     */


}
