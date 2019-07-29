package com.heitian.ssm.service;

import java.util.List;
import java.util.Map;

/**
 * ClassName:DebtService
 * Package:com.heitian.ssm.service
 * Description:
 * author:@Fay
 * Date:2019/4/214:33
 */
public interface DebtService {
    List<Map<String, Object>> debtList(String fiscal, String fis_perd);

    List<Map<String, Object>> payList(String fiscal, String fis_perd);
}
