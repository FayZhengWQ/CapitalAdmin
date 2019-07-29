package com.heitian.ssm.service.project.impl;

import com.heitian.ssm.dao.project.TransferDao;
import com.heitian.ssm.model.project.BudgetctrlModel;
import com.heitian.ssm.model.DprogsBean;
import com.heitian.ssm.model.project.TransferModel;
import com.heitian.ssm.service.project.TransferService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-11 19:21
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class TransferServiceImpl  implements TransferService {

    @Resource TransferDao transferDao;


    //获取结转单
    public List<DprogsBean> selectTransfer(DprogsBean bean){
        return  transferDao.GetTransfer(bean );
    }
    //添加中间表
    public  int insertInfo(BudgetctrlModel budgetctrlModel){

        return transferDao.insertInfo(budgetctrlModel);
    }
    //添加到结转表
    public int insertDprogs(TransferModel transferModel){
        return  transferDao.insertDprogs(transferModel);
    }

    //获取结转单列表

    public List<TransferModel> selectDprogsList(TransferModel transferModel){
        return  transferDao.selectDprogsList(transferModel);
    }

    public int SumDprog(TransferModel transferModel){
        return  transferDao.SumDprog(transferModel);
    }

    public  int Txcount(){
        return  transferDao.Txcount();
    }

    public  int ts_transitem(Map<String ,Object> map){
        return  transferDao.ts_transitem(map);
    }


    //
    public  List<TransferModel>  selectPay(TransferModel transferModel){
        return transferDao.selectPay(transferModel);
    }

    public int updataPay(String PAY_IMONEY,String PAY_STATE,String CXZENTERPRISEID,String CBMUNITID ,String IYEAR,String PNO){
        return  transferDao.updataPay(PAY_IMONEY,PAY_STATE,CXZENTERPRISEID,CBMUNITID,IYEAR,PNO);
    }

    public int insertId(String row,String CXZENTERPRISEID){
        return  transferDao.insertId(row,CXZENTERPRISEID);
    }

    public List<TransferModel> selectECOIN(int PNO){
        return transferDao.selectECOIN(PNO);
    }
    public List<TransferModel> selectNo(TransferModel model){
        return transferDao.selectNo(model);
    }

}
