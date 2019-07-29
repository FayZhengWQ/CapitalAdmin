package com.heitian.ssm.dao.payment;

import com.heitian.ssm.model.payment.RecBankModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName:RecBankDao
 * Package:com.heitian.ssm.dao
 * Description:
 * author:@Fay
 * Date:2019/3/14 0014上午 9:18
 */
@Repository
public interface RecBankDao {
    List<Map> queryRecBankByCndition(Map<String, String> map);

    int modifierRecBankByCondition(Map<String, Object> map);

    int addRecBank(List<RecBankModel> recBanks);

    int isExistRec(Map map);

    int deleteRec(Map map);

    int delete(List<Map<String,Object>> recBnakList);

    int addOneRecBank(Map<String,Object> recBankModel);

    List<RecBankModel> isExistRecName(Map map);
}
