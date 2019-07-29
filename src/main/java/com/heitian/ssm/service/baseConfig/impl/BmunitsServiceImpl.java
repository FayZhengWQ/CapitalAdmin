package com.heitian.ssm.service.baseConfig.impl;

import com.heitian.ssm.dao.baseConfig.BmunitsDao;
import com.heitian.ssm.model.baseconfig.BmunitsModel;
import com.heitian.ssm.service.baseConfig.BmunitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:BmunitsServiceImpl
 * Package:com.heitian.ssm.service.baseConfig.impl
 * Description:
 * author:@Fay
 * Date:2019/4/1019:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BmunitsServiceImpl implements BmunitsService {

    @Autowired
    private BmunitsDao bmunitsDao;

    @Override
    public List<BmunitsModel> queryBmunits(String cbmunitid, String cbmunitname) {
        Map map = new HashMap();
        map.put("cbmunitid",cbmunitid);
        map.put("cbmunitname",cbmunitname);
        List<BmunitsModel> bmunitsList = bmunitsDao.quesryBmunitsByCbmunitid(map);
        return bmunitsList;
    }

    @Override
    public int modifierBumnuitsByCondition(String cbmunitid, String cbmunitname, String url, String urlname, String urlpwd, String sid, String shortname, String source, String kjhsurl, String cpayUrl) {
        Map<String,Object> map = new HashMap<>();
        map.put("cbmunitid",cbmunitid);
        map.put("cbmunitname",cbmunitname);
        map.put("url",url);
        map.put("urlname",urlname);
        map.put("urlpwd",urlpwd);
        map.put("sid",sid);
        map.put("shortname",shortname);
        map.put("source",source);
        map.put("cpayUrl",cpayUrl);
        map.put("kjhsurl",kjhsurl);

        int count = bmunitsDao.modifierBumnuitsByCondition(map);
        return count;
    }

    @Override
    public int addBumunits(String cbmunitid, String cbmunitname, String url, String urlname, String urlpwd, String sid, String shortname, String cmemo, String cpayUrl, String source, String kjhsurl) {
        BmunitsModel bmunits = new BmunitsModel();
        bmunits.setCBMUNITID(cbmunitid);
        bmunits.setCBMUNITNAME(cbmunitname);
        bmunits.setCMEMO(cmemo);
        bmunits.setSHORTNAME(shortname);
        bmunits.setSID(sid);
        bmunits.setURL(url);
        bmunits.setURL_NAME(urlname);
        bmunits.setURL_PWD(urlpwd);
        bmunits.setCPAYURL(cpayUrl);
        bmunits.setSOURCE(source);
        bmunits.setKJHSURL(kjhsurl);
        int count = bmunitsDao.insertBmunits(bmunits);
        return count;
    }

    @Override
    public List<Map<String, Object>> queryAllCbmunit() {
        return bmunitsDao.queryAllCbmunit();
    }
}
