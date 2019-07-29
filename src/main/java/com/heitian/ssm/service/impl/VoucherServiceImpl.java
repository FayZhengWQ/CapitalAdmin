package com.heitian.ssm.service.impl;

import com.heitian.ssm.dao.VoucherDao;
import com.heitian.ssm.model.VoucherBean;
import com.heitian.ssm.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VoucherServiceImpl implements VoucherService {


    @Autowired
    private VoucherDao voucherDao;





    @Override
    public  List<VoucherBean> selectInfo(VoucherBean voucherBean){
        return  voucherDao.selectInfo(voucherBean);
    }
 @Override
    public  int updateInfo(VoucherBean voucherBean){
        return  voucherDao.updateInfo(voucherBean);
    }

    @Override
    public int addJson(VoucherBean voucherBean) {
        return voucherDao.addJson(voucherBean) ;
    }

    //根据pno查询数据

    @Override
    public List<Map<String, Object>> getDataByPno(VoucherBean voucherBean) {
        return voucherDao.getDataByPno(voucherBean);
    }
}
