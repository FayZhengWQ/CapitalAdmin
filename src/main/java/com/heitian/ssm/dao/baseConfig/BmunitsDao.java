package com.heitian.ssm.dao.baseConfig;

import com.heitian.ssm.model.baseconfig.BmunitsModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName:BmunitsDao
 * Package:com.heitian.ssm.dao.baseConfig
 * Description:
 * author:@Fay
 * Date:2019/4/1019:54
 */
@Repository
public interface BmunitsDao {

    List<BmunitsModel> quesryBmunitsByCbmunitid(Map map);

    int modifierBumnuitsByCondition(Map<String, Object> map);

    int insertBmunits(BmunitsModel bmunits);

    List<Map<String, Object>> queryAllCbmunit();
}
