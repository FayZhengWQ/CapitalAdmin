package com.heitian.ssm.service.payment;

import com.heitian.ssm.model.payment.SubsidiaryModel;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * ClassName:SubsidiaryService
 * Package:com.heitian.ssm.service.payment
 * Description:
 * author:@Fay
 * Date:2019/5/2810:01
 */
public interface SubsidiaryService {
    int isExistLastBanlance(Map<String, Object> args,String IS_CASH);

    int addOne(List<Map<String,Object>> subsidiaryList,String IS_CASH,Map<String,Object> args);

    int editSubsidiary(List<Map<String,Object>> subsidiaryList,String IS_CASH);

    List<Map<String, Object>> getList(Map<String,Object> args);

    boolean importExcel(InputStream in, String fileName, String path, String CBMUNITNAME, String CBMUNITID, String OPERATOR, String ACCOUNTGUID,String POWER,String IS_CASH);

    int deleteSubsidiary(List<Map<String, Object>> subsidiaryList,String IS_CASH);

    void getSubsidiary(Map<String, Object> args);


}
