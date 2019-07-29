package com.heitian.ssm.service.project;

import com.heitian.ssm.model.project.BudgetctrlModel;
import com.heitian.ssm.model.DprogsBean;
import com.heitian.ssm.model.project.TransferModel;

import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-11 19:21
 **/
public interface TransferService {

    //获取结转单
    List<DprogsBean> selectTransfer(DprogsBean bean);

    int insertInfo(BudgetctrlModel budgetctrlModel);

    //添加到结转表
    int insertDprogs(TransferModel transferModel);

    //获取结转单列表
     List<TransferModel> selectDprogsList(TransferModel transferModel);

     //一转金额
     int SumDprog(TransferModel transferModel);
     int Txcount();

     //存储过程
     int ts_transitem(Map<String, Object> map);

     List<TransferModel>  selectPay(TransferModel transferModel);

    int updataPay(String PAY_IMONEY, String PAY_STATE, String CXZENTERPRISEID, String CBMUNITID, String IYEAR, String PNO);

    int insertId(String row, String CXZENTERPRISEID);

    List<TransferModel> selectECOIN(int PNO);
    List<TransferModel> selectNo(TransferModel model);
}
