package com.heitian.ssm.service.impl;

import com.heitian.ssm.dao.FilenoDao;
import com.heitian.ssm.model.FilenoModel;
import com.heitian.ssm.service.FilenoService;
import com.heitian.ssm.utils.ClobToStringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-02-26 09:36
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class FilenoServiceImpl implements FilenoService {

    @Resource
    FilenoDao filenoDao;

    public int insertFileno(FilenoModel filenoModel) {
        return filenoDao.insertFileno(filenoModel);
    }


    public List<FilenoModel> selectFileno(FilenoModel filenoModel) {
        return filenoDao.selectFileno(filenoModel);
    }

    //删除文号文件
    public int deleteFileno(FilenoModel filenoModel) {
        return filenoDao.deleteFileno(filenoModel);
    }

    @Override
    public List<Map<String, Object>> getFileList(String iyear, String status) throws IOException, SQLException {
        String tableName = "reporttable" + iyear;
        Map map = new HashMap();
        map.put("tableName", tableName);
        map.put("status", status);
        map.put("iyear", iyear);
        List<Map<String, Object>> fileLlist = filenoDao.getFileList(map);
        for (Map<String, Object> file : fileLlist) {
            if (file.get("FILENAME") != null) {
                String filename = ClobToStringUtil.ClobToString((Clob) file.get("FILENAME"));
                file.remove("FILENAME");
                file.put("FILENAME", filename);
            }
        }
        return fileLlist;
    }

    @Override
    public List<Map<String, Object>> getxzFileList(String iyear, String status, String TYPE, String SHORTNAME) throws IOException, SQLException {
        String tableName = "reporttable" + iyear;
        Map map = new HashMap();
        map.put("tableName", tableName);
        map.put("status", status);
        map.put("iyear", iyear);
        map.put("TYPE", TYPE);
        map.put("SHORTNAME", SHORTNAME);
//        马鞍【马鞍镇】 安昌【安昌街道】 齐贤【齐贤街道】 数据加上====>可开委【柯桥经济】  type:1 补助对账


        List<Map<String, Object>> fileLlist = filenoDao.getXZFileList(map);


        return fileLlist;
    }

    @Override
    public List<Map<String, Object>> getFile(String FILENO) {
        return filenoDao.getFileByFILENO(FILENO);
    }

    @Override
    public List<Map<String, Object>> getFileByPno(String PNO) {
        return filenoDao.getFileByPno(PNO);
    }

    @Override
    public int deleteFileByPno(String PNO) {
        return filenoDao.deleteFileByPno(PNO);
    }
}


