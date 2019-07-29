package com.heitian.ssm.dao.payment;

import com.heitian.ssm.model.payment.PurposeModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName:PurposeDao
 * Package:com.heitian.ssm.dao
 * Description:
 * author:@Fay
 * Date:2019/4/3012:08
 */
@Repository
public interface PurposeDao {

    int addPurpose(List<PurposeModel> purposeModelList);

    List<Map<String, Object>> getList(Map<String, Object> map);

    int deletePurpose(List<Map<String,Object>> purposeList);

    int addOnePurpose(Map<String,Object> map);

    List<PurposeModel> isExistPurpose(Map map);
}
