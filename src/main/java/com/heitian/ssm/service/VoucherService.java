package com.heitian.ssm.service;

import com.heitian.ssm.model.VoucherBean;

import java.util.List;
import java.util.Map;

public interface VoucherService {

    List<VoucherBean> selectInfo(VoucherBean voucherBean);


    int updateInfo(VoucherBean voucherBean);

    int addJson(VoucherBean voucherBean);

    List<Map<String, Object>> getDataByPno(VoucherBean voucherBean);
}
