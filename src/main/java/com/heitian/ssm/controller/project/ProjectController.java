package com.heitian.ssm.controller.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heitian.ssm.Source.DynamicDataSource;
import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.project.Project;
import com.heitian.ssm.service.project.ProjectService;
import com.heitian.ssm.utils.Date.DateUtil;
import com.heitian.ssm.utils.config.FileImportException;
import com.heitian.ssm.utils.excel.Excel;
import com.heitian.ssm.utils.excel.ImportExcelUtil;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hpsf.GUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @program: CapitalAdmin
 * @description: 乡镇项目库
 * @author: liangsizhuuo@163.com
 * @create: 2018-10-30 11:09
 **/

@Controller
@CrossOrigin
public class ProjectController {

    @Resource
    private ProjectService projectService;

    //------------------------------------xmk重构------------------------------------------------------

    /**
     * 新增项目
     */
    @RequestMapping(value = "/project/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonData addProject(@RequestBody  List<Map<String,Object>> list) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
/*

         Map<String,Object> map = new HashMap<>();
        map.put("NAME","项目修改1");
        map.put("STANDARD_CODE","0000001");
        map.put("ISLEAF",1);
        map.put("BUDGETTYPE",1);
        map.put("SPANTYPE",1);
        map.put("ITEMSOURCESCODE","上级补助项目1");
        map.put("DECLAREDATE","2019-7-25");
        map.put("STARTYEAR",2013);
        map.put("ENDYEAR",2022);
        map.put("MANAGER","张三");
        map.put("ITEMTYPE",1);
        map.put("ITEMCAUSE","项目依据");
        map.put("CONTENT","项目内容");
        map.put("ITEMBRIEAF","项目绩效");
        map.put("ISACCEPT",0);
        map.put("EID","001");
        map.put("EID_ISBN","2019_001");
        map.put("YEAR",2019);
        map.put("CBMUNITID",627006);
        map.put("MONEY",1000);
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map);
*/

        JsonData jsonData = new JsonData();
        int count = projectService.addProject(list);
        if (count > 0) {
            jsonData = jsonData.success("新增项目成功");
        } else {
            jsonData = jsonData.fail("新增项目失败");
        }
        return jsonData;
    }

    @RequestMapping(value = "/project/open",method = RequestMethod.GET)
    public ModelAndView open(){
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }

    /**
     * 附件上传
     * （附件上传之后，在附件表中新增附件）
     * param:GUID/OPERATOR/
     */
    @RequestMapping(value = "/project/uplode", method = RequestMethod.POST)   //添加文号文件
    @ResponseBody
    public JsonData FileUplode(HttpServletRequest request, HttpServletResponse res) throws FileNotFoundException, FileImportException, URISyntaxException, ParseException {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        Map<String,Object> map = new HashMap<>();
        map.put("OPERATOR",request.getParameter("OPERATOR"));
        map.put("XMK_GUID",request.getParameter("GUID"));
        /*map.put("OPERATOR","褚金兰");
        map.put("XMK_GUID","2");*/
        int YEAR = Calendar.getInstance().get(Calendar.YEAR);

        System.out.print("路径：" + request.getSession().getServletContext());
        String fileName = "";
        String PATH = "";
        //解析器解析request的上下文
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //先判断request中是否包涵multipart类型的数据，
        if (multipartResolver.isMultipart(request)) {
            //再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                //这里的name为fileItem的alias属性值，相当于form表单中name
                String name = (String) iter.next();
                //根据name值拿取文件
                MultipartFile file = multiRequest.getFile(name);
                if (file != null) {
                    fileName = file.getOriginalFilename();
                    PATH = "D://项目附件//" + YEAR + "//" + fileName;
                    System.out.println("PATH:"+PATH);
                    File localFile = new File(PATH);
                    if (!localFile.getParentFile().exists()) {
                        //如果目标文件所在的目录不存在，则创建父目录
                        localFile.getParentFile().mkdirs();
                    }
                    try {
                        //写文件到本地
                        file.transferTo(localFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.print(e);
                    } finally {
                        //上传成功，添加项目和附件
                        map.put("PATH",PATH);
                        map.put("FILENAME",fileName);
                        int count = projectService.addFile(map);
                        if (count > 0) {
                            jsonData = jsonData.success("项目新增成功");
                        }
                    }
                }
            }
        }
        return jsonData;
    }

    /**
     * 查询数据
     */
    @RequestMapping(value = "/project/getList", method = RequestMethod.GET)
    @ResponseBody
    public JsonData getProject(@RequestBody Map<String, Object> param,HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        /*Map<String, Object> param = new HashMap<>();
        param.put("CBMUNITID","627006");
        param.put("YEAR","2019");
        param.put("DECLAREDATE","2019-07-25");
        param.put("STATUS","2");*/
        List<Map<String, Object>> projectList = projectService.getProject(param);
        if (param.get("STATUS").equals("1")) {
            if (projectList.size() == 0) {
                jsonData = jsonData.fail("项目申报数为零");
            } else {
                jsonData = jsonData.success(projectList, "数据查询成功", projectList.size());
            }
        }else{
            String[] head1 = new String[]{"NAME", "STANDARD_CODE", "BUDGETTYPE", "SPANTYPE", "ITEMSOURCESCODE", "DECLAREDATE",
                    "STARTYEAR", "ENDYEAR","MANAGER","ITEMTYPE","ITEMBRIEAF","ITEMCAUSE","CONTENT","ISACCEPT","EID","EID_ISBN",
            "MONEY","FILENAME"};
            String sheetName = param.get("FIS_PERD") + "年项目表";
            String fileno = param.get("FIS_PERD") + "年项目表";
            String[] head0 = new String[]{"项目名称", "项目标准码", "预算类型", "项目跨年度属性",
                    "项目来源", "申报时间", "开工时间", "结束时间","项目负责人","项目类别 ","项目绩效","项目依据","项目内容","状态",
                    "项目录入年度的单位INCODE","单位ISBN编码","申报金额","附件"};
            Excel.exportSubsidiaryAndCash(request,response,projectList,sheetName,fileno,head0,head1);
        }
        return jsonData;
    }

    /**
     * 编辑项目
     */
    @RequestMapping(value = "/project/edit", method = RequestMethod.POST)
    @ResponseBody
    public JsonData editProject(@RequestBody List<Map<String, Object>> list) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();

        int count = projectService.editProject(list);
        if (count == 0) {
            jsonData = jsonData.fail("项目更新失败");
        } else {
            jsonData = jsonData.success("项目编辑成功");
        }
        return jsonData;
    }


    /**
     * 删除项目
     */
    @RequestMapping(value = "/project/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonData deleteProject(/*@RequestBody Map<String, Object> param*/) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        Map<String, Object> param = new HashMap<>();
        param.put("PATH","D://项目附件//2019//xmk反馈.docx");
        param.put("GUID","1");
        int count = projectService.deleteProject(param);
        if (count == 0) {
            jsonData = jsonData.fail("项目删除失败");
        } else if (count ==1){
            jsonData = jsonData.success("项目删除成功");
        }else{
            jsonData = jsonData.fail("附件删除失败");
        }
        return jsonData;
    }

    /**
     * 获取某项目下的所有附件
     * param:项目GUID
     */
    @RequestMapping(value = "/project/getFileList", method = RequestMethod.POST)
    @ResponseBody
    public JsonData getFileList(/*@RequestBody Map<String, Object> param*/) {
        JsonData jsonData = new JsonData();
        Map<String, Object> param = new HashMap<>();
        param.put("GUID","2");
        List<Map<String, Object>> fileList = projectService.getFileList(param);
        if (fileList.size() == 0) {
            jsonData = jsonData.fail("该项目未上传附件");
        } else {
            jsonData = jsonData.success(fileList, "获取附件成功", fileList.size());
        }
        return jsonData;
    }

    /**
     * 删除附件
     * param:附件GUID,PATH
     */
    @RequestMapping(value = "/project/deleteFile", method = RequestMethod.POST)
    @ResponseBody
    public JsonData deleteFile(/*@RequestBody List<Map<String, Object>> list*/) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        //删除数据库记录
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("PATH","D://项目附件//2019//xmk反馈.docx");
        map.put("GUID","2");
        Map<String,Object> map1 = new HashMap<>();
        map1.put("PATH","D://项目附件//2019//0.jpg");
        map1.put("GUID","3");
        list.add(map);
        list.add(map1);
        int count = projectService.deleteFile(list);
        //删除文件
        if (count > 0) {
            try {
                for (int i = 0; i <list.size() ; i++) {
                    String PATH = list.get(i).get("PATH").toString();
                    File file = new File(PATH);
                    if (file.exists()) {
                        if (file.isDirectory()) {
                            FileUtils.deleteDirectory(file);
                        } else {
                            file.delete();
                        }
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            jsonData = jsonData.success("附件删除成功");
        } else {
            jsonData = jsonData.fail("附件删除失败");
        }

        return jsonData;
    }

}
