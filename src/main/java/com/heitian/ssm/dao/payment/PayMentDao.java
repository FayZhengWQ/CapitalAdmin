package com.heitian.ssm.dao.payment;

import com.heitian.ssm.model.payment.*;
import com.heitian.ssm.model.project.Project;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-12-03 03:32
 **/
@Repository
public interface PayMentDao {
    List<Project> selectdprog(@Param("CBMUNITID") String CBMUNITID);
    List<PayModel> selectPayzfgl();
    int insetpayment(PaymentModel paymentModel);

    List<PaymentModel> selectpayment(PaymentModel payment);


    /**
     * 街道获取为拨款完的项目
     * @param CBMUNITID  项目id
     * @param CBMUNITNAME 项目name
     * @param IYEAR     年份
     * @return
     */
    List<Project> selectdprog2(@Param("CBMUNITID") String CBMUNITID,
                               @Param("CBMUNITNAME") String CBMUNITNAME,
                               @Param("IYEAR") String IYEAR) ;


    //收款单位表数据
    List<RecBankModel> selectRecBankList(RecBankModel recBankModel);

    //付款单位数据
    List<PayBankModel> selectPayBankList(PayBankModel payBankModel);


    int addProPaymentsSON(List<Map<String,Object>> paysonList);

    List<Map<String, Object>> getPaymentsSonList(Map map);

    int deleteByPno(String pno);

    int doExam(Map<String,Object> payson);

    int edit(Map<String,Object> payson);

    int delete(List<Map<String,Object>> paySonlist);

    int countNum(String cbmunitid);

    int isThrow(Map<String,Object> paySon);

    int isPrint(Map<String,Object> paySon);

    int importExcel(List<PayMentSonModel> paysonModelList);
}
