package com.heitian.ssm.dao;

import com.heitian.ssm.model.FilenoModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-02-26 09:35
 **/
@Repository
public interface FilenoDao {

    //添加文号文件
    int insertFileno(FilenoModel filenoModel);

    //查询文号文件
    List<FilenoModel> selectFileno(FilenoModel filenoModel);

    //删除文号文件
    int  deleteFileno(FilenoModel filenoModel);


    List<Map<String, Object>> getFileList(Map map);

    List<Map<String, Object>> getXZFileList(Map map);

    List<Map<String, Object>> getFileByFILENO(String FILENO);

    List<Map<String, Object>> getFileByPno(String PNO);

    int deleteFileByPno(String PNO);

}
