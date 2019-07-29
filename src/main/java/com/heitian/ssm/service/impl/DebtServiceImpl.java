package com.heitian.ssm.service.impl;

import com.heitian.ssm.dao.DebtDao;
import com.heitian.ssm.service.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:DebtServiceImpl
 * Package:com.heitian.ssm.service.impl
 * Description:
 * author:@Fay
 * Date:2019/4/214:34
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DebtServiceImpl implements DebtService {
    @Autowired
    private DebtDao debtDao;

    @Override
    public List<Map<String, Object>> debtList(String fiscal, String fis_perd) {
        Map map = new HashMap();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        return debtDao.debtList(map);
    }

    @Override
    public List<Map<String, Object>> payList(String fiscal, String fis_perd) {
        Map map = new HashMap();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        List<Map<String,Object>> list = debtDao.payList(map);
        return list;
    }
}
