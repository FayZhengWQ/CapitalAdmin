package com.heitian.ssm.service.report.impl;

import com.heitian.ssm.dao.report.ReportGovDao;
import com.heitian.ssm.model.report.*;
import com.heitian.ssm.service.report.ReportGovService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:ReportGovServiceImpl
 * Package:com.heitian.ssm.service.impl
 * Description:
 * author:@Fay
 * Date:2019/4/98:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ReportGovServiceImpl implements ReportGovService {
    @Autowired
    private ReportGovDao reportGovDao;
    //[对个人和家庭的补助明细表]本地库书否存在当月数据
    @Override
    public int isExitAllow(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap<>();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.isExitAllow(map);
    }

    //[对个人和家庭的补助明细表]删除本地库当月数据
    @Override
    public int deleteAllow(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap<>();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.deleteAllow(map);
    }

    //[对个人和家庭的补助明细表]调用存储过程
    @Override
    public void doAllow(Map map) {
        reportGovDao.doAllow(map);
    }

    //[对个人和家庭的补助明细表]插入本地数据库
    @Override
    public int addAllow(List<AllowanceModel> allowList) {
        return reportGovDao.addAllow(allowList);
    }

    //[对个人和家庭的补助明细表]获取数据
    @Override
    public List<Map<String, Object>> getAllow(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap<>();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.getAllow(map);
    }

    @Override
    public List<Map<String, Object>> getAllow2(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap<>();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.getAllow2(map);
    }

    //[工资福利支出表]判断本地库是否有当月数据
    @Override
    public int isExistSalary(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap<>();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.isExistSalary(map);
    }

    //[工资福利支出表]删除本地库当月数据
    @Override
    public int deleteSalary(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap<>();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.deleteSalary(map);
    }

    @Override
    public void doSalary(Map map) {
        reportGovDao.doSalary(map);
    }

    @Override
    public int addSalary(List<SalaryModel> salaryList) {
        return reportGovDao.addSalary(salaryList);
    }

    @Override
    public List<Map<String, Object>> getSalary(String fis_perd, String fiscal, String co_code) {
        Map map = new HashMap<>();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.getSalary(map);
    }

    @Override
    public List<Map<String, Object>> getSalary2(String fis_perd, String fiscal, String co_code) {
        Map map = new HashMap<>();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.getSalary2(map);
    }

    @Override
    public int isExistOtherPay(String fis_perd, String fiscal, String co_code) {
        Map map = new HashMap<>();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.isExistOtherPay(map);
    }

    @Override
    public int deleteOtherPay(String fis_perd, String fiscal, String co_code) {
        Map map = new HashMap<>();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.deleteOtherPay(map);
    }

    @Override
    public void doOtherPay(Map map) {
        reportGovDao.doOtherPay(map);
    }

    @Override
    public int addOtherPay(List<OtherPayModel> otherPayList) {
        return reportGovDao.addOtherPay(otherPayList);
    }

    @Override
    public List<Map<String, Object>> getOtherPay(String fis_perd, String fiscal, String co_code) {
        Map map = new HashMap<>();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.getOtherPay(map);
    }

    @Override
    public List<Map<String, Object>> getOtherPay2(String fis_perd, String fiscal, String co_code) {
        Map map = new HashMap<>();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.getOtherPay2(map);
    }

    @Override
    public int isExistGoods(String fis_perd, String fiscal, String co_code) {
        Map map = new HashMap<>();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.isExistGoods(map);
    }

    @Override
    public int deleteGoods(String fis_perd, String fiscal, String co_code) {
        Map map = new HashMap<>();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.deleteGoods(map);
    }

    @Override
    public void doGoods(Map map) {
        reportGovDao.doGoods(map);
    }

    @Override
    public int addGoods(List<GoodsModel> goodsList) {
        return reportGovDao.addGoods(goodsList);
    }

    @Override
    public List<Map<String, Object>> getGoods(String fis_perd, String fiscal, String co_code) {
        Map map = new HashMap<>();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.getGoods(map);
    }

    @Override
    public List<Map<String, Object>> getGoods2(String fis_perd, String fiscal, String co_code) {
        Map map = new HashMap<>();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.getGoods2(map);
    }


    //[经费支出表]判断本地库是否存在数据
    @Override
    public int isExistFee(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.isExistFee(map);
    }

    @Override
    public int deleteFee(String fiscal, String fis_perd, String co_code) {
        Map map = new HashMap();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.deleteFee(map);
    }

    @Override
    public void doFee(Map map) {
        reportGovDao.doFee(map);
    }

    @Override
    public int addFee(List<FeePayModel> feeList) {
        return reportGovDao.addFee(feeList);
    }

    @Override
    public List<Map<String, Object>> getFeeList(String fiscal, String fis_perd, String co_code) {
        Map<String,Object> map = new HashMap<>();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.getFeeList(map);
    }

    @Override
    public List<Map<String, Object>> getFeeList2(String fiscal, String fis_perd, String co_code) {
        Map<String,Object> map = new HashMap<>();
        map.put("fiscal",fiscal);
        map.put("fis_perd",fis_perd);
        map.put("co_code",co_code);
        return reportGovDao.getFeeList2(map);
    }

    @Override
    public int isExistIncomePay(Map<String,Object> map) {
        return reportGovDao.isExistIncomePay(map);
    }

    @Override
    public int deleteIncomePay(Map<String,Object> map) {
        return reportGovDao.deleteIncomePay(map);
    }

    @Override
    public void doIncomePay(Map map) {
        reportGovDao.doIncomePay(map);
    }

    @Override
    public int addIncomePay(List<Map<String,Object>> incomePaylist) {
        int count = 0;
        for (Map<String, Object> map :incomePaylist) {
            count = reportGovDao.addIncomePay(map);
        }
        return count;
    }

    @Override
    public List<Map<String, Object>> getIncomePay(Map<String,Object> map) {
        return reportGovDao.getIncomePay(map);
    }

    @Override
    public List<Map<String, Object>> getIncomePay2(Map map) {
        return reportGovDao.getIncomePay2(map);
    }
}
