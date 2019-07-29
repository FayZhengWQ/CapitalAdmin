package com.heitian.ssm.controller.project;

import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.project.CapitalModel;
import com.heitian.ssm.model.CellModel;
import com.heitian.ssm.service.project.Capitalservice;
import com.heitian.ssm.utils.ExportPageBeanTest;
import com.heitian.ssm.utils.excel.Excel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @program: CapitalAdmin
 * @description:   资金监管
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-11 22:30
 **/
@Controller
@CrossOrigin
public class CapitalController {

    //获取资金来源
    @Resource
    Capitalservice capitalservice;

    /**
     * 搜索
     * @param request
     * @return
     */
    @RequestMapping(value = "/capital",method = RequestMethod.POST)
    @ResponseBody
    public JsonData CapitalSelect(HttpServletRequest request, HttpServletResponse response){

        String CBMUNITID=request.getParameter("CBMUNITID");
        String FILENO=request.getParameter("FILENO");
        String CXZPROGNAME=request.getParameter("CXZPROGNAME");
        String IYEAR=request.getParameter("IYEAR");
        CapitalModel capitalModel=new CapitalModel();
        capitalModel.setCBMUNITID(CBMUNITID);
        capitalModel.setFILENO(FILENO);
        capitalModel.setCXZPROGNAME(CXZPROGNAME);
        capitalModel.setIYEAR(IYEAR);
        String STATUS = request.getParameter("STATUS");
        JsonData jsonData=new JsonData(true);
        if (STATUS.equals("1")){
//            Integer PageSize = Integer.valueOf(request.getParameter("PageSize"));//每页条数
//            Integer PageIndex = Integer.valueOf(request.getParameter("PageIndex"));//页码
//            PageHelper.startPage(PageIndex, PageSize);

            List<CapitalModel> list=capitalservice.selectCapital(capitalModel);
//            PageInfo<CapitalModel> pageInfo = new PageInfo<>(list);


            jsonData=jsonData.success(list,"获取数据成功",list.size());
        }else{

            List<CapitalModel> datalist = capitalservice.selectCapital(capitalModel);

            String sheetName = "柯桥区资金监管台账";
            String fileno = "柯桥区资金监管台账";
            Excel.exportCapital(request,response,datalist,sheetName,fileno);
        }

        return  jsonData;

    }


    /**
     * 下载报表
     * @param request
     * @return
     */
    @RequestMapping("/capital/export")
    @ResponseBody
    public JsonData CapitalExport(HttpServletRequest request){

        String CBMUNITID=request.getParameter("CBMUNITID");
        String IYEAR=request.getParameter("IYEAR");
        String FILENO=request.getParameter("FILENO");
        String CXZPROGNAME=request.getParameter("CXZPROGNAME");
        CapitalModel capitalModel=new CapitalModel();
        capitalModel.setCBMUNITID(CBMUNITID);
        capitalModel.setFILENO(FILENO);
        capitalModel.setCXZPROGNAME(CXZPROGNAME);
        capitalModel.setIYEAR(IYEAR);
        List<CapitalModel> list=capitalservice.selectCapital(capitalModel);
        String xlsName = "柯桥区乡镇项目资金监管平台";
        List<CellModel> cellNameList = new ArrayList();
        Integer cellRow = 1;

        //标题
        CellModel cellModel0=new CellModel();
        cellModel0.setCellName("柯桥区乡镇项目资金监管平台");
        cellModel0.setStartRow(0);
        cellModel0.setEndRow(0);
        cellModel0.setStartColumn(0);
        cellModel0.setEndColumn(15);

        CellModel cellModel1 = new CellModel();
        cellModel1.setCellName("文件名称");
        cellModel1.setStartRow(1);
        cellModel1.setEndRow(2);
        cellModel1.setStartColumn(0);
        cellModel1.setEndColumn(0);


        CellModel cellModel2 = new CellModel();
        cellModel2.setCellName("部门");
        cellModel2.setStartRow(1);
        cellModel2.setEndRow(2);
        cellModel2.setStartColumn(1);
        cellModel2.setEndColumn(1);

        CellModel cellModel3 = new CellModel();
        cellModel3.setCellName("文号");
        cellModel3.setStartRow(1);
        cellModel3.setEndRow(2);
        cellModel3.setStartColumn(2);
        cellModel3.setEndColumn(2);

        CellModel cellModel4 = new CellModel();
        cellModel4.setCellName("类别");
        cellModel4.setStartRow(1);
        cellModel4.setEndRow(2);
        cellModel4.setStartColumn(3);
        cellModel4.setEndColumn(3);

        CellModel cellModel5 = new CellModel();
        cellModel5.setCellName("补助内容");
        cellModel5.setStartRow(1);
        cellModel5.setEndRow(2);
        cellModel5.setStartColumn(4);
        cellModel5.setEndColumn(4);

        CellModel cellModel6 = new CellModel();
        cellModel6.setCellName("金额");
        cellModel6.setStartRow(1);
        cellModel6.setEndRow(2);
        cellModel6.setStartColumn(5);
        cellModel6.setEndColumn(5);

        CellModel cellModel7 = new CellModel();
        cellModel7.setCellName("信息通达");
        cellModel7.setStartRow(1);
        cellModel7.setEndRow(1);
        cellModel7.setStartColumn(6);
        cellModel7.setEndColumn(9);

        CellModel cellModel8 = new CellModel();
        cellModel8.setCellName("公告公示");
        cellModel8.setStartRow(1);
        cellModel8.setEndRow(1);
        cellModel8.setStartColumn(10);
        cellModel8.setEndColumn(11);

        CellModel cellModel9 = new CellModel();
        cellModel9.setCellName("抽查巡查");
        cellModel9.setStartRow(1);
        cellModel9.setEndRow(1);
        cellModel9.setStartColumn(12);
        cellModel9.setEndColumn(15);

        cellNameList.add(cellModel0);
        cellNameList.add(cellModel1);
        cellNameList.add(cellModel2);
        cellNameList.add(cellModel3);
        cellNameList.add(cellModel4);
        cellNameList.add(cellModel5);
        cellNameList.add(cellModel6);
        cellNameList.add(cellModel7);
        cellNameList.add(cellModel8);
        cellNameList.add(cellModel9);


        LinkedHashMap rowMapper = new LinkedHashMap() {
            {
                put("1", "排名");
                put("2", "时间");
                put("3", "部门");
                put("4", "采用数");
                put("5", "得分");
                put("6", "采用数");
                put("7", "发文日期");
                put("8", "收文日期");
                put("9","资金拨入日期");
                put("10","资金拨出日期");
                put("11","公示日期");
                put("12","公示形式");
                put("13","巡查日期");
                put("14","巡查人员");
                put("15","巡查评价");
                put("16","巡查签证");
            }
        };
        try {
            ExportPageBeanTest.createCSVUtil(xlsName, cellNameList, cellRow, list, rowMapper, "/Users/rosyblackliang/Desktop/");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonData jsonData=new JsonData();
        
        
        return  jsonData;
    }

    public boolean compare(String time1,String time2) throws ParseException

    {
        //如果想比较日期则写成"yyyy-MM-dd"就可以了
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //将字符串形式的时间转化为Date类型的时间
        Date a=sdf.parse(time1);
        Date b=sdf.parse(time2);
        //Date类的一个方法，如果a早于b返回true，否则返回false
        if(a.before(b))
            return true;
        else
            return false;
        /*
         * 如果你不喜欢用上面这个太流氓的方法，也可以根据将Date转换成毫秒
        if(a.getTime()-b.getTime()<0)
            return true;
        else
            return false;
        */
    }

}
