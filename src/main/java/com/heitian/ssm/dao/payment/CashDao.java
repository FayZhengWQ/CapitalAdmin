package com.heitian.ssm.dao.payment;

import com.heitian.ssm.model.payment.SubsidiaryModel;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName:CashDao
 * Package:com.heitian.ssm.dao.payment
 * Description:
 * author:@Fay
 * Date:2019/7/1116:27
 */
@Repository
public interface CashDao {

     int addOneCash(Map<String, Object> cashModel);

     List<Map<String, Object>> getCashList(Map<String, Object> args);

     int editCash(Map<String,Object> subsidiary);

     int deleteCash(List<Map<String, Object>> subsidiaryList);

     int isExistCash(Map<String, Object> args);

     void upadteOneCash(Map<String, Object> args);

    int isExistLastCash(Map<String, Object> args);
}
