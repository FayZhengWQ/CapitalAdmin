package com.heitian.ssm.controller;

import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.FilenoModel;
import com.heitian.ssm.service.FilenoService;
import com.heitian.ssm.utils.Date.DateUtil;
import com.heitian.ssm.utils.config.FileImportException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 上传文号模块
 *
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-02-26 09:35
 **/
@Controller
@CrossOrigin
public class FilenoController {


    @Resource
    FilenoService filenoService;

    //区财政获取文号文件列表
    @RequestMapping(value = "/fileno/getList",method = RequestMethod.POST)
    @ResponseBody
    public JsonData getFileNoList(@RequestBody Map<String,Object> PARAM) throws IOException, SQLException {
        DynamicDataSourceHolder.setDataSource("dataSource1");
//        DynamicDataSourceHolder.setDataSource("dataSource16");
        String IYEAR = PARAM.get("IYEAR").toString();
        String STATUS = PARAM.get("STATUS").toString();
                JsonData jsonData = new JsonData();
        List<Map<String,Object>> fileList = filenoService.getFileList(IYEAR,STATUS);
        System.out.println("=====>"+fileList.size());
        if (fileList.size()==0){
            jsonData = jsonData.fail("无文件");
        }else{
            jsonData = jsonData.success(fileList,"成功",fileList.size());
        }
        return jsonData;
    }


    @RequestMapping(value = "/fileno/open",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView open(){
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }

    @RequestMapping(value = "/fileno/uplode", method = RequestMethod.POST)   //添加文号文件
    @ResponseBody
    public JsonData FilenoUplode(HttpServletRequest request, HttpServletResponse res) throws FileNotFoundException, FileImportException, URISyntaxException, ParseException {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FILENO = request.getParameter("FILENO");
        String OPERATOR = request.getParameter("OPERATOR");
        JsonData jsonData = new JsonData();


        System.out.print("路径：" + request.getSession().getServletContext());
        String fileName = "";
        String path = "";
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
                    path = "D://文号文件//2019//" + fileName;
                    File localFile = new File(path);
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
                        //上传成功
                        FilenoModel filenoModel = new FilenoModel();
                        filenoModel.setPATH(path);
                        filenoModel.setFILENAME(fileName);
                        filenoModel.setFILENO(FILENO);
                        filenoModel.setGDATE(DateUtil.getCurrentTime());
                        filenoModel.setYEAR(DateUtil.getCurrentYear());
                        filenoModel.setMONTH(DateUtil.getCurrentMonth());
                        filenoModel.setOPERATOR(OPERATOR);
                        int row = filenoService.insertFileno(filenoModel);
                        if (row > 0){
                            jsonData = jsonData.success("成功");
                        }
                    }
                }
            }
        }
        return jsonData;
    }

    @RequestMapping(value = "/fileno/list",method = RequestMethod.POST)
    @ResponseBody
    public JsonData FilenoList(@RequestBody Map<String,Object> PARAM) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();


        FilenoModel filenoModel = new FilenoModel();
        filenoModel.setFILENO(PARAM.get("FILENAME").toString());

        List<FilenoModel> list = filenoService.selectFileno(filenoModel);

        System.out.print(list.toString());
        jsonData = jsonData.success(list, "获取数据成功", list.size());

        return jsonData;
    }



    @RequestMapping(value = "/fileno/delete")
    @ResponseBody
    public JsonData FilenoDelete(@RequestBody Map<String,Object> PARAM) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        String FILENAME = PARAM.get("FILENAME").toString();//文件名
        String PATH = PARAM.get("PATH").toString();//路径

        FilenoModel filenoModel = new FilenoModel();
        filenoModel.setFILENO(FILENAME);
        int row = filenoService.deleteFileno(filenoModel);
        System.out.print("row" + row);
        if (row == 1) {//删除成功
            try {
                File file = new File(PATH);
                if (file.exists()) {
                    if (file.isDirectory()) {
                        FileUtils.deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
                jsonData = jsonData.success("删除成功");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (row == 0) {
            jsonData = jsonData.fail("删除失败");
        }
        return jsonData;
    }


    @RequestMapping(value = "/fileno/down1" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonData FilenoDownLoad(@RequestBody Map<String,Object> PARAM,HttpServletResponse response) throws IOException {
        DynamicDataSourceHolder.setDataSource("dataSource1");
//        String fileName =request.getParameter("FILENAME")+ ".doc";
        List<Map<String,Object>> fileList = filenoService.getFileByPno(PARAM.get("PNO").toString());
        String path = fileList.get(0).get("PATH").toString();
        int length = path.split("/").length;
        String fileName =path.split("/")[length-1];
        System.out.println(fileName);
        File tempFile = new File(path.trim());

        //通过文件路径 获取文件名
        System.out.println("文件名test = " + tempFile.getName());


        //得到要下载的文件
        File file = new File(path);
        fileName = new String(fileName.getBytes("ISO8859-1"), "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("multipart/form-data;charset=UTF-8");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + new String(file.getName().getBytes(), "ISO8859-1"));


        FileInputStream in = new FileInputStream(path);
        OutputStream out = response.getOutputStream();
        byte buffer[] = new byte[1024]; // 缓冲区的大小设置是个迷  我也没搞明白
        int len = 0;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();


        JsonData jsonData = new JsonData();
        return jsonData;
    }

    //[乡镇]获取文号文件列表
    @RequestMapping(value = "/fileno/xz/getList",method = RequestMethod.POST)
    @ResponseBody
    public JsonData getXZFileNoList(@RequestBody Map<String,Object> PARAM) throws IOException, SQLException {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String IYEAR = PARAM.get("IYEAR").toString();
        String STATUS = PARAM.get("STATUS").toString();
        String TYPE=PARAM.get("TYPE").toString();
        String SHORTNAME=PARAM.get("SHORTNAME").toString();
        JsonData jsonData = new JsonData();
        List<Map<String,Object>> fileList = filenoService.getxzFileList(IYEAR,STATUS,TYPE,SHORTNAME);
//        System.out.println("=====>"+fileList.size());
        if (fileList.size()==0){
            jsonData = jsonData.fail("无文件");
        }else{
            jsonData = jsonData.success(fileList,"成功",fileList.size());
        }
        return jsonData;
    }

    //【查询文件】根据fileno查询c_prog_file对应的数据
    @RequestMapping(value = "/fileno/xz/getFile",method = RequestMethod.POST)
    @ResponseBody
    public JsonData getxzFile(@RequestBody Map<String,Object> PARAM){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        List<Map<String,Object>> fileList = filenoService.getFile(PARAM.get("FILENO").toString());
        if (fileList.size()==0){
            jsonData = jsonData.fail("无文号文件");
        }else{
            jsonData  = jsonData.success(fileList,"成功",fileList.size());
        }
        return jsonData;
    }



    /**
     * 文件删除
     */
    @RequestMapping(value = "/fileno/deletefile",method = RequestMethod.POST)
    @ResponseBody
    public JsonData deleteFile(@RequestBody Map<String,Object> PARAM){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        List<Map<String,Object>> fileList = filenoService.getFileByPno(PARAM.get("PNO").toString());
        String path = fileList.get(0).get("PATH").toString();
        //删除数据库数据
        int count = filenoService.deleteFileByPno(PARAM.get("PNO").toString());
        if (count > 0){
            //根据path删除文件
            File file = new File(path);
            if (file.isFile()&&file.exists()){
                file.delete();
                jsonData = jsonData.success("删除成功");
            }else{
                jsonData = jsonData.fail("删除失败");
            }
        }

        return jsonData;
    }
}
