package com.heitian.ssm.dao.payment;

import com.heitian.ssm.model.payment.PayBankModel;

import java.util.List;
import java.util.Map;

/**
 * ClassName:PayBankDao
 * Package:com.heitian.ssm.dao
 * Description:
 * author:@Fay
 * Date:2019/3/18 0018上午 9:53
 */
public interface PayBankDao {
    List<Map> queryPayBankByCondition(Map<String, String> map);

    int modifierPayBankByCondition(Map<String, Object> map);

    int addPayBank(List<PayBankModel> payBanks);

    int isExistPay(Map map);

    int deletePay(Map map);

    int delete(List<Map<String,Object>> payBnakList);

    int addOnePayBank(Map<String,Object> payBankModel);

    List<PayBankModel> isExistENAME(Map map);
}
