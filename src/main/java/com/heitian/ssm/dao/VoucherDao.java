package com.heitian.ssm.dao;

import com.heitian.ssm.model.VoucherBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName:VoucherDao
 * Package:com.heitian.ssm.dao
 * Description:
 * author:@Fay
 * Date:2019/5/515:20
 */
@Repository
public interface VoucherDao {

    List<VoucherBean> selectInfo(VoucherBean voucherBean);

    int updateInfo(VoucherBean voucherBean);

    int addJson(VoucherBean voucherBean);

    List<Map<String, Object>> getDataByPno(VoucherBean voucherBean);

}
