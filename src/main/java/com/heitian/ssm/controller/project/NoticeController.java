package com.heitian.ssm.controller.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.project.NoticeBean;
import com.heitian.ssm.model.project.NoticeEditorModel;
import com.heitian.ssm.model.project.Project;
import com.heitian.ssm.service.project.NoticeService;
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

import static com.heitian.ssm.utils.Date.DateTranCnDate.getUpperDate;

/**
 * @program: CapitalAdmin
 * @description: 公告公示
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-07 10:10
 **/
@Controller
@CrossOrigin
public class NoticeController {


    @Resource
    private NoticeService noticeService;
    @Resource
    private ProjectService projectService;

    @RequestMapping("/notice") //获取列表
    @ResponseBody
    public JsonData GetProject(HttpServletRequest request, HttpServletResponse response) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String STATUS = request.getParameter("STATUS");
        Project bean = new Project();
        bean.setCPROGTYPE("");
        bean.setCBMUNITID(request.getParameter("CBMUNITID"));
        bean.setFILENO(request.getParameter("FILENO"));
        bean.setCXZPROGNAME(request.getParameter("CXZPROGNAME"));
        bean.setTRANSFER_STATE("");
        bean.setPAY_STATE("");
        bean.setCBMUNITNAME(request.getParameter("CBMUNITNAME"));
        bean.setCFUNCTIONCODE("");
        bean.setCECONOMYSECTIONCODE("");
        bean.setCECONOMYSECTIONGOVCODE("");
        bean.setCBUDGETCATEGORYCODE("");
        bean.setCRESOURCECODE("");
        bean.setIYEAR(request.getParameter("IYEAR"));

        JsonData jsonData = new JsonData(true);
        if (STATUS.equals("1")){
            Integer PageSize = Integer.valueOf(request.getParameter("PageSize"));//每页条数
            Integer PageIndex = Integer.valueOf(request.getParameter("PageIndex"));//页码
            PageHelper.startPage(PageIndex, PageSize);
            //获取数据
            List<Project> list = projectService.selectAllDprog(bean);

            PageInfo<Project> pageInfo = new PageInfo<>(list);
            jsonData = jsonData.success(list, "获取数据成功", Integer.valueOf(String.valueOf(pageInfo.getTotal())));

        }else{
            List<Project> dataList = projectService.selectAllDprog(bean);
            String sheetName ="xxx";
            String fileno = "aaa";
            Excel.reportProg(request,response,dataList,sheetName,fileno);
        }


        return jsonData;

    }

    @RequestMapping("/notice/add")  //添加公告单
    @ResponseBody
    public JsonData AddNotice(HttpServletRequest request) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String CXZPROGID = request.getParameter("CXZPROGID");
        String CXZPROGNAME = request.getParameter("CXZPROGNAME");
        String CANNOUNCETYPE = request.getParameter("CANNOUNCETYPE");
        String START_DATE = request.getParameter("START_DATE");
        String END_DATE = request.getParameter("END_DATE");
        String CBMUNITID = request.getParameter("CBMUNITID");
        String MONTH = request.getParameter("MONTH");
        String CBMUNITNAME = request.getParameter("CBMUNITNAME");
        String YEAR = request.getParameter("YEAR");
        String GDATE = request.getParameter("GDATE");
        String PNO = request.getParameter("PNO");

        NoticeBean noticeBean = new NoticeBean();
        noticeBean.setCXZPROGID(CXZPROGID);
        noticeBean.setCXZPROGNAME(CXZPROGNAME);
        noticeBean.setCANNOUNCETYPE(CANNOUNCETYPE);
        noticeBean.setSTART_DATE(START_DATE);
        noticeBean.setEND_DATE(END_DATE);
        noticeBean.setCBMUNITID(CBMUNITID);
        noticeBean.setMONTH(MONTH);
        noticeBean.setCBMUNITNAME(CBMUNITNAME);
        noticeBean.setYEAR(YEAR);
        noticeBean.setGDATE(GDATE);
        noticeBean.setPNO(PNO);

        int rows = noticeService.AddNotice(noticeBean);
        JsonData jsonData = new JsonData();
        if (rows == 1) {
            noticeBean.setNOTICE_STATE("1");
            int row = projectService.UpdateNoticeState(noticeBean);

            jsonData = jsonData.success("添加公告单成功");

        } else {
            jsonData = jsonData.fail("操作失败");
        }

        return jsonData;
    }


    @RequestMapping("/notice/detail")  //获取公告单数据
    @ResponseBody
    public JsonData GetNotice(HttpServletRequest request) {

        DynamicDataSourceHolder.setDataSource("dataSource1");
        String CBMUNITID = request.getParameter("CBMUNITID");
        String CBMUNITNAME = request.getParameter("CBMUNITNAME");
        String PNO = request.getParameter("PNO");
        String YEAR = request.getParameter("YEAR");

        NoticeBean bean = new NoticeBean();
        bean.setCBMUNITID(CBMUNITID);
        bean.setCBMUNITNAME(CBMUNITNAME);
        bean.setYEAR(YEAR);
        bean.setPNO(PNO);

        //获取数据
        List<NoticeBean> list = noticeService.getNoticeDetail(bean);
        //输出json

        JsonData jsonData = new JsonData(true);
        jsonData = jsonData.success(list, "获取数据成功", list.size());

        return jsonData;
    }


    @RequestMapping("/notice/update") //更新公告单数据
    @ResponseBody
    public JsonData Update(HttpServletRequest request) {

        DynamicDataSourceHolder.setDataSource("dataSource1");
        String CXZPROGID = request.getParameter("CXZPROGID");
        String CXZPROGNAME = request.getParameter("CXZPROGNAME");
        String CANNOUNCETYPE = request.getParameter("CANNOUNCETYPE");
        String START_DATE = request.getParameter("START_DATE");
        String END_DATE = request.getParameter("END_DATE");
        String CBMUNITID = request.getParameter("CBMUNITID");
        String MONTH = request.getParameter("MONTH");
        String CBMUNITNAME = request.getParameter("CBMUNITNAME");
        String YEAR = request.getParameter("YEAR");
        String GDATE = request.getParameter("GDATE");
        String PNO = request.getParameter("PNO");

        NoticeBean noticeBean = new NoticeBean();
        noticeBean.setCXZPROGID(CXZPROGID);
        noticeBean.setCXZPROGNAME(CXZPROGNAME);
        noticeBean.setCANNOUNCETYPE(CANNOUNCETYPE);
        noticeBean.setSTART_DATE(START_DATE);
        noticeBean.setEND_DATE(END_DATE);
        noticeBean.setCBMUNITID(CBMUNITID);
        noticeBean.setMONTH(MONTH);
        noticeBean.setCBMUNITNAME(CBMUNITNAME);
        noticeBean.setYEAR(YEAR);
        noticeBean.setGDATE(GDATE);
        noticeBean.setPNO(PNO);

        //获取数据
        int rows = noticeService.UpdateNotice(noticeBean);

        JsonData jsonData = new JsonData();
        if (rows == 1) {
            jsonData = jsonData.success("修改成功");
        } else {
            jsonData = jsonData.fail("操作失败");
        }

        return jsonData;

    }


    @RequestMapping("/notice/editor")//编辑公告单
    @ResponseBody
    public ModelAndView Editor(@RequestParam Map<String, Object> model) {
        DynamicDataSourceHolder.setDataSource("dataSource1");

        String CBMUNITID = model.get("CBMUNITID").toString();
        String CBMUNITNAME = model.get("CBMUNITNAME").toString();
        String YEAR = model.get("YEAR").toString();
        String PNO = model.get("PNO").toString();

        NoticeEditorModel noticeBean = new NoticeEditorModel();
        noticeBean.setCBMUNITID(CBMUNITID);
        noticeBean.setCBMUNITNAME(CBMUNITNAME);
        noticeBean.setYEAR(YEAR);
        noticeBean.setPNO(PNO);

        //获取公告单详情
        List<NoticeEditorModel> list = noticeService.selectNoticeDprog(noticeBean);
        ModelAndView mv = new ModelAndView("noticeeditor");
        mv.addObject("CXZPROGID", list.get(0).getCXZPROGID());
        mv.addObject("CBMUNITID", CBMUNITID);
        mv.addObject("CBMUNITNAME", CBMUNITNAME);
        mv.addObject("YEAR", YEAR);
        mv.addObject("PNO", PNO);
        mv.addObject("FILENO", list.get(0).getFILENO());
        if (list.get(0).getBANNOUNCEMENTDOC() == null || (list.get(0).getBANNOUNCEMENTDOC() != null && list.get(0).getBANNOUNCEMENTDOC().equals("")))
            mv.addObject("content", "<h2 style='white-space: normal; text-align: center;'><span style='font-family: 黑体, SimHei;font-size: 28px;'>&nbsp;</span></h2><h2 style='white-space: normal; text-align: center;'><span style='font-family: 黑体, SimHei;font-size: 28px;'>&nbsp;</span></h2><h2 style='white-space: normal; text-align: center;'><span style='font-family: 黑体, SimHei;font-size: 28px;'><strong>" +
                    list.get(0).getFILENO() +
                    "的公示</strong></span></h2><p><span style='font-family: 宋体, SimSun; font-size: 12px;'>&nbsp;</span></p><p><span style='font-family: 宋体, SimSun; font-size: 12px;'>&nbsp;</span></p><p><span style='font-family: 宋体, SimSun; font-size: 12px;'>&nbsp;</span></p><p><span style='font-family: 宋体, SimSun; font-size: 12px;'>&nbsp;</span></p><p><span style='font-family: 宋体, SimSun; font-size: 12px;'>&nbsp;</span></p><p style='text-indent: 28px;line-height:2.5em;'><span style='font-family: 仿宋, SimSun; font-size: 20px;'>为体现公平、公正、公开和透明的办事原则，根据"+list.get(0).getFILENO()+"文件精神，现将" +
                    list.get(0).getCXZPROGNAME() +
                    "的经费予以公示。</span></p><p style='text-indent: 28px;line-height:2.5em;'><span style='font-family: 仿宋, SimSun; font-size: 20px;'>公示期限为：" +
                    list.get(0).getSTART_DATE().substring(0, 10) +
                    "——" +
                    list.get(0).getEND_DATE().substring(0, 10) +
                    "，任何单位和个人对公示对象持有异议的，请在公示期内以书面形式向本镇反映。</span></p><p style='text-indent: 42px;line-height:2.5em;'><span style='font-family: 仿宋, SimSun; font-size: 20px;'>(具体名单详见文件)</span></p><p><span style='font-family: 仿宋, SimSun; font-size: 20px;line-height:2.5em;'>&nbsp;</span></p><p style='text-indent: 42px;'><span style='font-family: 仿宋, SimSun; font-size: 20px;line-height:2.5em;'>举报电话：85654157 &nbsp;85654330</span></p><p><span style='font-family: 仿宋, SimSun; font-size: 20px;line-height:2.5em;'>&nbsp;</span></p><p><span style='font-family: 仿宋, SimSun; font-size: 20px;'>&nbsp;</span></p><p style='text-indent: 0em; text-align: right;'><span style='font-family: 仿宋, SimSun;font-size: 20px;line-height:2.5em;'>" +
                    CBMUNITNAME +
                    "（盖章） &nbsp; &nbsp;</span></p><p style='text-indent: 0em; text-align: right;'><span style='font-family: 仿宋, SimSun;font-size: 20px;line-height:2.5em;'>"+getUpperDate(list.get(0).getSTART_DATE().substring(0, 10))+" &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span></p>");
        else
            mv.addObject("content", list.get(0).getBANNOUNCEMENTDOC());
        return mv;
    }



    @RequestMapping(value = "/notice/save",method = RequestMethod.POST) //保存编辑文本
    @ResponseBody
    public JsonData SaveNotice(@RequestParam Map<String, Object> model) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        NoticeBean noticeBean = new NoticeBean();
        noticeBean.setCBMUNITID(model.get("CBMUNITID").toString());
        noticeBean.setCBMUNITNAME(model.get("CBMUNITNAME").toString());
        noticeBean.setYEAR( model.get("YEAR").toString());
        noticeBean.setPNO(model.get("PNO").toString());
        noticeBean.setBANNOUNCEMENTDOC( model.get("BANNOUNCEMENTDOC").toString());
        int rows = noticeService.UpdateNoticeDoc(noticeBean);

        JsonData jsonData = new JsonData();
        if (rows == 1) {  //修改成功
            jsonData = jsonData.success("保存成功");
        } else {
            jsonData = jsonData.fail("保存失败");
        }
        return jsonData;
    }




    /**
     * 一键刷公告公示
     */
    @RequestMapping(value = "/notoce/falsh",method = RequestMethod.POST)
    @ResponseBody
    public JsonData FlashNotice(HttpServletRequest request){
        List<Map<String, Object>> dataList = JSON.parseObject(request.getParameter("list"), new TypeReference<ArrayList<Map<String, Object>>>() {});

        int count = noticeService.falsh(dataList);

        if(count>0){
            for (int i = 0; i < dataList.size(); i++) {
                NoticeBean noticeBean=new NoticeBean();
                noticeBean.setPNO(dataList.get(i).get("pno").toString());
                projectService.UpdateNoticeState(noticeBean);
            }

        }

        JsonData jsonData=new JsonData(true);


        return jsonData;
    }




        /** 大写数字 */

        private static final String[] NUMBERS = { "0", "一", "二", "三", "四", "五", "六", "七", "八", "九" };

        private static final String[] data = { "年","月","日"};

        /** 通过 yyyy-MM-dd 得到中文大写格式 yyyy MM dd 日期 */

        public static synchronized String toChinese(String str) {

            StringBuffer sb = new StringBuffer();

            sb.append(getSplitDateStr(str, 0)).append(" ").append(

                    getSplitDateStr(str, 1)).append(" ").append(

                    getSplitDateStr(str, 2));

            return sb.toString();

        }

        public static String getSplitDateStr(String str,int num){


            String[] DateStr = str.split("-");

            StringBuffer sb = new StringBuffer();

            for(int i=0;i<DateStr[num].length();i++){

                sb.append(countnum( i, num ,DateStr[num].substring(i, i + 1)));

            }


            return sb.append(data[num]).toString();

        }

        public static String countnum( int i, int nuum ,String num){

            System.out.print(i+"   "+nuum+"   "+"   "+num+"\n");

            if (i==0 &&nuum==1 &&num.equals("0")){
                return "";
            }else if(i==0 &&nuum==2 &&num.equals("0")){
                return "";
            }
            else {
                return NUMBERS[Integer.valueOf(num)];
            }

        }

        public static void main(String args[]) {

            System.out.println(toChinese("2118-11-12"));

        }











}
