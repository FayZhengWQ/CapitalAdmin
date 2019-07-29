package com.heitian.ssm.controller.project;

import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.project.BudgetctrlModel;
import com.heitian.ssm.model.project.Project;
import com.heitian.ssm.model.project.TransferModel;
import com.heitian.ssm.model.project.TransitemModel;
import com.heitian.ssm.service.project.ProjectService;
import com.heitian.ssm.service.project.TransferService;
import com.heitian.ssm.utils.JdbcUtil;
import com.heitian.ssm.utils.MathMoney;
import com.heitian.ssm.utils.MoneyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * @program: CapitalAdmin
 * @description: 转指标
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-11 19:20
 **/

@Controller
@CrossOrigin
public class TransferController {

    @Resource
    TransferService transferService;
    @Resource
    ProjectService projectService;

    @RequestMapping("/transfer")
    @ResponseBody
    public JsonData GetTransfer(HttpServletRequest request) throws ParseException {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String CBMUNITID = request.getParameter("CBMUNITID");//区级编码
        String FILENO = request.getParameter("FILENO");//文号
        String CXZPROGNAME = request.getParameter("CXZPROGNAME");//项目名称
        String CBMUNITNAME = request.getParameter("CBMUNITNAME");//单位名称
        String IYEAR=request.getParameter("IYEAR");

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

       //获取数据
        List<Project> list = projectService.selectAllDprog(bean);

        List<Project> projectList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Project project = new Project();
            project.setCXZPROGID(list.get(i).getCXZPROGID());
            project.setCXZPROGNAME(list.get(i).getCXZPROGNAME());
            project.setCPROGRAMNAME(list.get(i).getCXZPROGID() + " " + list.get(i).getCXZPROGNAME());
            project.setFILENO(list.get(i).getFILENO());
            project.setCUNITID(list.get(i).getCUNITID());
            project.setCUNITNAME(list.get(i).getCUNITNAME());
            project.setCENTERPRISE(list.get(i).getCUNITID() + " " + list.get(i).getCUNITNAME());
            project.setCFUNCTIONNAME(list.get(i).getCFUNCTIONNAME());
            project.setCFUNCTIONCODE(list.get(i).getCFUNCTIONCODE());
            project.setCFUNCTION(list.get(i).getCFUNCTIONCODE() + " " + list.get(i).getCFUNCTIONNAME());
            project.setCECONOMYSECTIONCODE(list.get(i).getCECONOMYSECTIONCODE());
            project.setCECONOMYSECTIONNAME(list.get(i).getCECONOMYSECTIONNAME());
            project.setCECONOMYSECTION(list.get(i).getCECONOMYSECTIONCODE() + " " + list.get(i).getCECONOMYSECTIONNAME());
            project.setCECONOMYSECTIONGOVCODE(list.get(i).getCECONOMYSECTIONGOVCODE());
            project.setCECONOMYSECTIONGOVNAME(list.get(i).getCECONOMYSECTIONGOVNAME());
            project.setCECONOMYSECTIONGOV(list.get(i).getCECONOMYSECTIONGOVCODE() + " " + list.get(i).getCECONOMYSECTIONGOVNAME());
            project.setCBUDGETCATEGORYCODE(list.get(i).getCBUDGETCATEGORYCODE());
            project.setCBUDGETCATEGORYNAME(list.get(i).getCBUDGETCATEGORYNAME());
            project.setCBUDGETCATEGORY(list.get(i).getCBUDGETCATEGORYCODE() + " " + list.get(i).getCBUDGETCATEGORYNAME());
            project.setCRESOURCECODE(list.get(i).getCRESOURCECODE());
            project.setCRESOURCENAME(list.get(i).getCRESOURCENAME());
            project.setCRESOURCE(list.get(i).getCRESOURCECODE() + " " + list.get(i).getCRESOURCENAME());
            project.setSZTYPE(list.get(i).getSZTYPECODE() + " " + list.get(i).getSZTYPENAME());
            project.setSZTYPECODE(list.get(i).getSZTYPECODE());
            project.setSZTYPENAME(list.get(i).getSZTYPENAME());
//            String imoney=MoneyUtils.fmtMicrometer(String.valueOf(list.get(i).getIMONEY()));
//            System.out.print("imoney: "+String.valueOf(list.get(i).getIMONEY())+"\n");
            project.setIMONEY(String.valueOf(list.get(i).getIMONEY()));   //金额
            project.setNOTICE_STATE(list.get(i).getNOTICE_STATE());
            project.setIYEAR(list.get(i).getIYEAR());
            project.setSIGNED(list.get(i).getSIGNED());
            project.setIGPLANID(list.get(i).getIGPLANID());
            project.setCBILLNO(list.get(i).getCBILLNO());
            project.setPNO(list.get(i).getPNO());
//            project.setTRANSFER_STATE(list.get(i).getTRANSFER_STATE());
            //已结转
            TransferModel transferModel = new TransferModel();
            transferModel.setCXZPROGID(list.get(i).getCXZPROGID());
            transferModel.setCXZPROGNAME(list.get(i).getCXZPROGNAME());
            transferModel.setCBMUNITID(list.get(i).getCBMUNITID());
            transferModel.setCPNO(String.valueOf(list.get(i).getPNO()));
            transferModel.setSIGNED(list.get(i).getSIGNED());
            transferModel.setIGPLANID(list.get(i).getIGPLANID());
            transferModel.setCBILLNO(list.get(i).getCBILLNO());
            transferModel.setIYEAR(list.get(i).getIYEAR());
            transferModel.setCBMUNITNAME(list.get(i).getCBMUNITNAME());

            int SumDprog = transferService.SumDprog(transferModel);
            project.setIMONEY1(MoneyUtils.fmtMicrometer(String.valueOf(SumDprog)));//已结转    imoney 金额  imoney1 已结转 imoney2 未结转

            double d1 = new DecimalFormat().parse(list.get(i).getIMONEY()).doubleValue(); //这里使用的是parse，不是format
            project.setIMONEY2(MoneyUtils.fmtMicrometer(MathMoney.sub(String.valueOf(d1), String.valueOf(SumDprog)))); //未结转

            if(String.valueOf(list.get(i).getIMONEY()).equals(project.getIMONEY1())){
                project.setTRANSFER_STATE("已结转");
            }else {
                project.setTRANSFER_STATE("未结转");
            }

            projectList.add(project);
        }

        JsonData jsonData = new JsonData();
        jsonData = jsonData.success(projectList, "获取成功", projectList.size());

        return jsonData;
    }

    /**
     * 添加结转但的时候 判断是否还需要结转
     * @param request 看插入中间表  如果插入成功 执行存储过程成功 在往艾迪表插入
     * @return
     */
    @RequestMapping("/transfer/add") //添加到中间表 和结转表
    @ResponseBody
    public JsonData TransferAdd(HttpServletRequest request) throws Exception {

        //必传值
        String CXZPROGID = request.getParameter("CXZPROGID");
        String CXZPROGNAME = request.getParameter("CXZPROGNAME");
        String DPRODATE = request.getParameter("DPRODATE");//指标生成日期
        String IMONEY = request.getParameter("IMONEY");//转指标金额
        String CMEMO = request.getParameter("CMEMO");//备注
        String IYEAR = request.getParameter("IYEAR"); //项目生成年份
        String CBMUNITID = request.getParameter("CBMUNITID"); //单位编码
        String ENTERPRISENAME = request.getParameter("ENTERPRISENAME");//单位名称
        String ENTERPRISEGUID = request.getParameter("ENTERPRISEGUID");//单位GUID
        String PROGRAMTYPENAME = request.getParameter("PROGRAMTYPENAME");
        String PROGRAMTYPEGUID = request.getParameter("PROGRAMTYPEGUID");
        String FUNCTIONNAME = request.getParameter("FUNCTIONNAME");//功能科目名称
        String FUNCTIONGUID = request.getParameter("FUNCTIONGUID");//功能科目guid
        String ECONOMYSECTIONNAME = request.getParameter("ECONOMYSECTIONNAME");//部门经济科目名称
        String ECONOMYSECTIONGUID = request.getParameter("ECONOMYSECTIONGUID");//部门经济科目guid
        String XMK_ECOGOVCODE = request.getParameter("XMK_ECOGOVCODE");  //本级政府经济科目
        String ECOGOVNAME = request.getParameter("ECOGOVNAME");
        String ECOGOVINCODE = request.getParameter("ECOGOVINCODE");
        String ECOGOVGUID = request.getParameter("ECOGOVGUID");
        String RESOURCENAME = request.getParameter("RESOURCENAME"); //资金来源
        String RESOURCECODE = request.getParameter("RESOURCECODE");
        String RESOURCEGUID = request.getParameter("RESOURCEGUID");
        String BUDGETCATEGORYNAME = request.getParameter("BUDGETCATEGORYNAME"); //指标
        String BUDGETCATEGORYGUID = request.getParameter("BUDGETCATEGORYGUID");
        String DIVISIONNAME = request.getParameter("DIVISIONNAME");//科室
        String DIVISIONGUID = request.getParameter("DIVISIONGUID");
        String CBMUNITNAME = request.getParameter("CBMUNITNAME");//单位名称
        String FILENO = request.getParameter("FILENO");//文号
        String YEAR=request.getParameter("YEAR");
        String MONTH = request.getParameter("MONTH"); //月份
        String GDATE = request.getParameter("GDATE");//获取数据时间
        String source =request.getParameter("SOURCE");
        String OPERATORGUID=request.getParameter("OPERATORGUID");
        String SIGNED=request.getParameter("SIGNED");
        String CBILLNO=request.getParameter("CBILLNO");
        String IGPLANID=request.getParameter("IGPLANID");
        String url=request.getParameter("url");
        String url_NAME=request.getParameter("url_NAME");
        String url_PWD=request.getParameter("url_PWD");
        String CPNO=request.getParameter("PNO");//D_PROG的PNO

        //if转指标金额==未转指标金额  修改d_porg的

        TransferModel transferModel = new TransferModel();
        transferModel.setCXZPROGID(CXZPROGID);
        transferModel.setCXZPROGNAME(CXZPROGNAME);
        transferModel.setDPRODATE(DPRODATE);
        transferModel.setIMONEY(IMONEY);
        transferModel.setDGKKS(DIVISIONNAME);
        transferModel.setCMEMO(CMEMO);
        transferModel.setCBMUNITID(CBMUNITID);
        transferModel.setCBMUNITNAME(CBMUNITNAME);
        transferModel.setMONTH(MONTH);
        transferModel.setGDATE(GDATE);
        transferModel.setIYEAR(IYEAR);//项目生成时间
        transferModel.setYEAR(YEAR);//指标生成时间
        transferModel.setSIGNED(SIGNED);
        transferModel.setCBILLNO(CBILLNO);
        transferModel.setIGPLANID(IGPLANID);

        // 插入中间表
        BudgetctrlModel budgetctrlModel = new BudgetctrlModel();
        budgetctrlModel.setPRJCODE(CXZPROGID);
        //单位
        budgetctrlModel.setENTERPRISENAME(ENTERPRISENAME);
        budgetctrlModel.setENTERPRISEGUID(ENTERPRISEGUID);
        //项目类型
        budgetctrlModel.setPROGRAMNAME(CXZPROGID + " " + CXZPROGNAME);
        budgetctrlModel.setPROGRAMTYPENAME(PROGRAMTYPENAME);
        budgetctrlModel.setPROGRAMTYPEGUID(PROGRAMTYPEGUID);
        //功能科目
        budgetctrlModel.setFUNCTIONNAME(FUNCTIONNAME);
        budgetctrlModel.setFUNCTIONGUID(FUNCTIONGUID);
        //部门经济科目
        budgetctrlModel.setECONOMYSECTIONNAME(ECONOMYSECTIONNAME);
        budgetctrlModel.setECONOMYSECTIONGUID(ECONOMYSECTIONGUID);
        //政府经济科目
        budgetctrlModel.setXMK_ECOGOVCODE(XMK_ECOGOVCODE);
        budgetctrlModel.setECOGOVNAME(ECOGOVNAME);
        budgetctrlModel.setECOGOVINCODE(ECOGOVINCODE);
        budgetctrlModel.setECOGOVGUID(ECOGOVGUID);

        budgetctrlModel.setRESOURCENAME(RESOURCENAME);
        budgetctrlModel.setRESOURCECODE(RESOURCECODE);
        budgetctrlModel.setRESOURCEGUID(RESOURCEGUID);

        budgetctrlModel.setOPERATORGUID(OPERATORGUID);  //操作人员账号

        budgetctrlModel.setBUDGETCATEGORYNAME(BUDGETCATEGORYNAME);
        budgetctrlModel.setBUDGETCATEGORYGUID(BUDGETCATEGORYGUID);


        budgetctrlModel.setDIVISIONNAME(DIVISIONNAME);
        budgetctrlModel.setDIVISIONGUID(DIVISIONGUID);


        budgetctrlModel.setSET_YEAR(IYEAR);

        budgetctrlModel.setMONEY(IMONEY);

        DynamicDataSourceHolder.setDataSource(source);
        budgetctrlModel.setGUID(String.valueOf(transferService.Txcount()));
        int row = transferService.insertInfo(budgetctrlModel);
        JsonData jsonData = new JsonData();
        if (row == 1) {       //插入zbgl中间表成功则生成了pno 将这个pno保存到xzzjjg
            TransitemModel transitemModel=new TransitemModel();
            transitemModel.setIYEAR(IYEAR);
            transitemModel.setFILENO(FILENO);
            transitemModel.setDIVISIONGUID(DIVISIONGUID);
            transitemModel.setDIVISIONNAME(DIVISIONNAME);
            transitemModel.setCBUDGETCATEGORYGUID(BUDGETCATEGORYGUID);
            transitemModel.setSUMMARY("测试");
            transitemModel.setOPERATORGUID(OPERATORGUID);
            transitemModel.setPNO(budgetctrlModel.getPNO());  //这个pno是中间表生成的行号
            transitemModel.setISSOURCE("0");
            transitemModel.setUrl(url);
            transitemModel.setUrl_NAME(url_NAME);
            transitemModel.setUrl_PWD(url_PWD);
            List<TransitemModel> list_item=new ArrayList<TransitemModel>();
            list_item.add(transitemModel);

            int  result= JdbcUtil.TS_TRANSITEM(list_item);
            if (result==0){  //指标号生成成功  根据行号去查询
              List<TransferModel> list= transferService.selectECOIN(budgetctrlModel.getPNO());
              if (list.get(0).getECONOMYSECTIONID()!=null){
                  DynamicDataSourceHolder.setDataSource("dataSource1");
                  transferModel.setCXZENTERPRISEID(list.get(0).getECONOMYSECTIONID()); //指标号
                  transferModel.setPNO(String.valueOf(budgetctrlModel.getPNO()));  //将中间表的pno 行号 放入d_progs
                  transferModel.setCPNO(CPNO);
                  transferService.insertDprogs(transferModel);

                  jsonData = new JsonData(true);
                  jsonData = jsonData.success("保存成功");
              }

            }else {  //指标号生成失败 保存pno
                jsonData = new JsonData(false);
                jsonData = jsonData.fail("保存失败,请联系管理员"+result);

            }

        }else {
            jsonData = new JsonData(false);
            jsonData = jsonData.fail("插入中间表失败,请联系管理员");

        }



        return jsonData;
    }

    @RequestMapping("/transfer/list") //项目的结转列表
    @ResponseBody
    public JsonData TransferList(HttpServletRequest request) {

        String CBMUNITID = request.getParameter("CBMUNITID");
        String CBMUNITNAME=request.getParameter("CBMUNITNAME");
        String CPNO=request.getParameter("CPNO");
        TransferModel transferModel = new TransferModel();
        transferModel.setCPNO(CPNO);
        transferModel.setCBMUNITID(CBMUNITID);
        transferModel.setCBMUNITNAME(CBMUNITNAME);
        List<TransferModel> list = transferService.selectDprogsList(transferModel);

        JsonData jsonData = new JsonData(true);
        jsonData = jsonData.success(list, "获取成功", list.size());


        return jsonData;
    }


    @RequestMapping("/transfer/No")//查询指标号
    @ResponseBody
    public JsonData TransferNo(HttpServletRequest request){

        String CBMUNITID=request.getParameter("CBMUNITID");
        String CBMUNITNAME=request.getParameter("CBMUNITID");
        String PNO=request.getParameter("PNO");

        TransferModel model=new TransferModel();
        model.setCBMUNITID(CBMUNITID);
        model.setCBMUNITNAME(CBMUNITNAME);
        model.setPNO(PNO);

        List<TransferModel> list=transferService.selectNo(model);

        JsonData jsonData=new JsonData();
        if (list.size()==1){
            jsonData=jsonData.success(list.get(0).getCXZENTERPRISEID());
        }else if (list.size()==0){
            jsonData=jsonData.fail("请拨款完项目再抽查");
        }

        return  jsonData;



    }



}
