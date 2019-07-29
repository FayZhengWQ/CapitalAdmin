package com.heitian.ssm.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName:DebtDao
 * Package:com.heitian.ssm.dao
 * Description:
 * author:@Fay
 * Date:2019/4/214:35
 */
@Repository
public interface DebtDao {

    List<Map<String, Object>> debtList(Map map);

    List<Map<String, Object>> payList(Map map);
}
