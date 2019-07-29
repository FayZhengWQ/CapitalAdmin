package com.heitian.ssm.service.report;

import com.heitian.ssm.model.report.*;

import java.util.List;
import java.util.Map;

/**
 * ClassName:ReportGovService
 * Package:com.heitian.ssm.service
 * Description:
 * author:@Fay
 * Date:2019/4/98:52
 */
public interface ReportGovService {

   
    int isExitAllow(String fiscal, String fis_perd, String co_code);

    int deleteAllow(String fiscal, String fis_perd, String co_code);

    void doAllow(Map map);

    int addAllow(List<AllowanceModel> allowList);

    List<Map<String, Object>> getAllow(String fiscal, String fis_perd, String co_code);

    int isExistSalary(String fiscal, String fis_perd, String co_code);

    int deleteSalary(String fiscal, String fis_perd, String co_code);

    void doSalary(Map map);

    List<Map<String, Object>> getAllow2(String fiscal, String fis_perd, String co_code);

    int addSalary(List<SalaryModel> salaryList);

    List<Map<String, Object>> getSalary(String fis_perd, String fiscal, String co_code);

    List<Map<String, Object>> getSalary2(String fis_perd, String fiscal, String co_code);

    int isExistOtherPay(String fis_perd, String fiscal, String co_code);

    int deleteOtherPay(String fis_perd, String fiscal, String co_code);

    void doOtherPay(Map map);

    int addOtherPay(List<OtherPayModel> otherPayList);

    List<Map<String, Object>> getOtherPay(String fis_perd, String fiscal, String co_code);

    List<Map<String, Object>> getOtherPay2(String fis_perd, String fiscal, String co_code);

    int isExistGoods(String fis_perd, String fiscal, String co_code);

    int deleteGoods(String fis_perd, String fiscal, String co_code);

    void doGoods(Map map);

    int addGoods(List<GoodsModel> goodsList);

    List<Map<String, Object>> getGoods(String fis_perd, String fiscal, String co_code);

    List<Map<String, Object>> getGoods2(String fis_perd, String fiscal, String co_code);

    int isExistFee(String fiscal, String fis_perd, String co_code);

    int deleteFee(String fiscal, String fis_perd, String co_code);

    void doFee(Map map);

    int addFee(List<FeePayModel> feeList);

    List<Map<String, Object>> getFeeList(String fiscal, String fis_perd, String co_code);

    List<Map<String, Object>> getFeeList2(String fiscal, String fis_perd, String co_code);


   /* int isExistFee(String fiscal, String fis_perd, String co_code);

    int deleteFee(String fiscal, String fis_perd, String co_code);

    void doFee(Map map);

    int addFee(List<FeePayModel> feeList);

    List<Map<String, Object>> getFeeList(String fiscal, String fis_perd, String co_code);*/

    int isExistIncomePay(Map<String,Object> map);

    int deleteIncomePay(Map<String,Object> map);

    void doIncomePay(Map<String,Object> map);

    int addIncomePay(List<Map<String,Object>> incomePaylist);

    List<Map<String, Object>> getIncomePay(Map<String,Object> map);

    List<Map<String, Object>> getIncomePay2(Map map);
}
