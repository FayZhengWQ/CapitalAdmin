package com.heitian.ssm.service;

import com.heitian.ssm.model.FilenoModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-02-26 09:36
 **/
public interface FilenoService {

    //添加文号文件
    int insertFileno(FilenoModel filenoModel);


    //查询文号文件
    List<FilenoModel> selectFileno(FilenoModel filenoModel);


    //删除文号文件
    int deleteFileno(FilenoModel filenoModel);

    //获取文号文件列表
    List<Map<String, Object>> getFileList(String iyear, String status) throws IOException, SQLException;

    //乡镇获取文号文件列表
    List<Map<String, Object>> getxzFileList(String iyear, String status, String TYPE, String SHORTNAME) throws IOException, SQLException;

    List<Map<String, Object>> getFile(String FILENO);

    List<Map<String, Object>> getFileByPno(String PNO);

    int deleteFileByPno(String pno);
}
