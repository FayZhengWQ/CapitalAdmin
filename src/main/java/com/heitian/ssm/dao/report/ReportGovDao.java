package com.heitian.ssm.dao.report;

import com.heitian.ssm.model.report.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName:ReportGovDao
 * Package:com.heitian.ssm.dao
 * Description:
 * author:@Fay
 * Date:2019/4/98:54
 */
@Repository
public interface ReportGovDao {

    int isExitAllow(Map map);

    int deleteAllow(Map map);

    void doAllow(Map map);

    int addAllow(List<AllowanceModel> allowList);

    List<Map<String, Object>> getAllow(Map map);

    List<Map<String, Object>> getAllow2(Map map);

    int isExistSalary(Map map);

    int deleteSalary(Map map);

    void doSalary(Map map);

    int addSalary(List<SalaryModel> salaryList);

    List<Map<String, Object>> getSalary(Map map);

    List<Map<String, Object>> getSalary2(Map map);

    int isExistOtherPay(Map map);

    int deleteOtherPay(Map map);

    void doOtherPay(Map map);

    int addOtherPay(List<OtherPayModel> otherPayList);

    List<Map<String, Object>> getOtherPay(Map map);

    List<Map<String, Object>> getOtherPay2(Map map);

    int isExistGoods(Map map);

    int deleteGoods(Map map);

    void doGoods(Map map);

    int addGoods(List<GoodsModel> goodsList);

    List<Map<String, Object>> getGoods(Map map);

    List<Map<String, Object>> getGoods2(Map map);

    int isExistFee(Map map);

    int deleteFee(Map map);

    void doFee(Map map);

    int addFee(List<FeePayModel> feeList);

    List<Map<String, Object>> getFeeList(Map<String, Object> map);

    List<Map<String, Object>> getFeeList2(Map<String, Object> map);


    int isExistIncomePay(Map map);

    int deleteIncomePay(Map map);

    void doIncomePay(Map map);

    int addIncomePay(Map<String,Object> income);

    List<Map<String, Object>> getIncomePay(Map map);

    List<Map<String, Object>> getIncomePay2(Map map);
}
