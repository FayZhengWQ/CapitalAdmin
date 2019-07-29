package com.heitian.ssm.service.payment;

import com.heitian.ssm.model.payment.PurposeModel;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * ClassName:PurposeService
 * Package:com.heitian.ssm.service
 * Description:
 * author:@Fay
 * Date:2019/4/3012:06
 */
public interface PurposeService {
    boolean importExcel(InputStream in, String fileName, String path, String CBMUNITID, String C_YEAR, String OPERATOR, String POWER);

    int addOnePurpose(List<Map<String,Object>> purposeList);

    List<Map<String, Object>> getList(Map<String, Object> map);

    int deletePurpose(List<Map<String,Object>> purposeList);
}
