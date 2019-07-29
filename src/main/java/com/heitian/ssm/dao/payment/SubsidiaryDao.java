package com.heitian.ssm.dao.payment;

import com.heitian.ssm.model.payment.SubsidiaryModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName:SubsidiaryDao
 * Package:com.heitian.ssm.dao.payment
 * Description:
 * author:@Fay
 * Date:2019/5/2810:03
 */
@Repository
public interface SubsidiaryDao {
    int addOne(Map<String,Object> subsidiaryList);

    int editSubsidiary(Map<String,Object> subsidiary);

    List<Map<String, Object>> getList(Map args);

    int deleteSubsidiary(List<Map<String, Object>> subsidiaryList);

    int isExistSubsidiary(Map<String,Object> args);

    void updateFirstSub(Map<String,Object> args);

    void getSubsidiary(Map<String, Object> args);

    int isExistLastBanlance(Map<String, Object> args);

    /*List<Map<String, Object>> getBankList(Map args);

    int addBank(Map<String, Object> map);

    int editBank(Map<String, Object> map);

    int deleteBank(List<Map<String, Object>> mapList);*/
}
