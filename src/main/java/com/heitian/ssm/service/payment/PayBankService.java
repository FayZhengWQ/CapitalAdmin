package com.heitian.ssm.service.payment;

import com.heitian.ssm.model.payment.PayBankModel;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * ClassName:PayBankService
 * Package:com.heitian.ssm.service
 * Description:
 * author:@Fay
 * Date:2019/3/14 0014上午 9:15
 */
public interface PayBankService {
    List<Map> queryPayBankByCondition(String cbmunitid, String c_year, String POWER);

    int modifierPayBankByCondition(List<Map<String,Object>> list);

    int addPayBank(List<PayBankModel> payBanklist);

    boolean importExcel(InputStream in, String fileName, String path, String CBMUNITID, String OPERATOR, String C_YEAR, String POWER);

    int isExistPay(String year, String month, String cbmunitid);

    int deletePay(String year, String month, String cbmunitid);

    int delete(List<Map<String,Object>> payBnakList);

    int addOnePayBank(List<Map<String,Object>> payBnakList);
}
