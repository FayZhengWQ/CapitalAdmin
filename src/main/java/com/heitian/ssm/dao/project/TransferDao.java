package com.heitian.ssm.dao.project;

import com.heitian.ssm.model.project.BudgetctrlModel;
import com.heitian.ssm.model.DprogsBean;
import com.heitian.ssm.model.project.TransferModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-11 19:20
 **/
@Repository
public interface TransferDao {

    //获取结转单
    List<DprogsBean> GetTransfer(DprogsBean bean);

    int insertInfo(BudgetctrlModel budgetctrlModel);

    //添加到接单表
    int insertDprogs(TransferModel transferModel);

    //获取结转单列表
    List<TransferModel> selectDprogsList(TransferModel transferModel);

    //已转金额
    int SumDprog(TransferModel transferModel);

    //
    int Txcount();

    int ts_transitem(Map<String, Object> map);

    List<TransferModel> selectPay(TransferModel transferModel);

    //更新      tring CXZENTERPRISEID,String CBMUNITID ,String IYEAR
    int updataPay(@Param("PAY_IMONEY") String PAY_IMONEY,
                  @Param("PAY_STATE") String PAY_STATE,
                  @Param("CXZENTERPRISEID") String CXZENTERPRISEID,
                  @Param("CBMUNITID") String CBMUNITID,
                  @Param("IYEAR") String IYEAR,
                  @Param("PNO") String PNO);

    //添加行号
    int insertId(@Param("ROW") String ROW, @Param("CXZENTERPRISEID") String CXZENTERPRISEID);

    //
    List<TransferModel> selectECOIN(@Param("PNO") int PNO);
    List<TransferModel> selectNo(TransferModel model);

}
