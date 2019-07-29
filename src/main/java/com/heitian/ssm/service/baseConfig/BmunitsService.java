package com.heitian.ssm.service.baseConfig;

import com.heitian.ssm.model.baseconfig.BmunitsModel;

import java.util.List;
import java.util.Map;

/**
 * ClassName:BmunitsService
 * Package:com.heitian.ssm.service.baseConfig
 * Description:
 * author:@Fay
 * Date:2019/4/1019:48
 */
public interface BmunitsService {

    List<BmunitsModel> queryBmunits(String cbmunitid, String cbmunitname);

    int modifierBumnuitsByCondition(String cbmunitid, String cbmunitname, String url, String urlname, String urlpwd, String sid, String shortname, String source, String kjhsurl, String cpayUrl);

    int addBumunits(String cbmunitid, String cbmunitname, String url, String urlname, String urlpwd, String sid, String shortname, String cmemo, String cpayUrl, String source, String kjhsurl);

    List<Map<String, Object>> queryAllCbmunit();
}
