package com.heitian.ssm.service.payment;

import com.heitian.ssm.model.payment.RecBankModel;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * ClassName:RecBankService
 * Package:com.heitian.ssm.service.impl
 * Description:
 * author:@Fay
 * Date:2019/3/18 0018上午 11:47
 */
public interface RecBankService {
    List<Map> queryRecBankByCndition(String cbmunitid, String c_year, String POWER);

    int modifierRecBankByCondition(List<Map<String,Object>> list);

    int addRecBank(List<RecBankModel> recBnakList);

    boolean importExcel(InputStream in, String fileName, String path, String CBMUNITID, String C_YEAR, String OPERATOR, String POWER);

    int isExistRec(String year, String month, String cbmunitid);

    int deleteRec(String year, String month, String cbmunitid);

    int delete(List<Map<String,Object>> recBnakList);

    int addOneRecBank(List<Map<String,Object>> recBnakList);
}
