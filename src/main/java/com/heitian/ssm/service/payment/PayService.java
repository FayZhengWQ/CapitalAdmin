package com.heitian.ssm.service.payment;

import com.heitian.ssm.model.payment.*;
import com.heitian.ssm.model.project.Project;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-12-03 03:55
 **/
public interface PayService {

    List<Project> selectdprog(String CBMUNITID);

    List<PayModel> selectPayzfgl();

    int insetpayment(PaymentModel paymentModel);

    List<PaymentModel> selectpayment(PaymentModel payment);

    List<Project> selectdprog2(String CBMUNITID, String CBMUNITNAME, String IYEAR);

    //收款单位
    List<RecBankModel> selectRecBankList(RecBankModel recBankModel);

    //收款单位
    List<PayBankModel> selectPayBankList(PayBankModel payBankModel);

    int addProPaymentsSON(List<Map<String,Object>> paysonList);

    List<Map<String, Object>> getPaymentsSonList(Map<String, Object> map);
    int deleteByPno(String pno);

    int doExam(String EXAMER, List<Map<String,Object>> paySonlist);

    int edit(List<Map<String,Object>> paySonlist);

    int delete(List<Map<String,Object>> paySonlist);

    //导入拨款单
    boolean importExcel(InputStream in, String fileName, String path, String cbmunitid, String operator, String iyear, String POWER, String CPOSTGUID);

    //是否作废
    int isThrow(List<Map<String,Object>> paySonlist);
    int isPrint(List<Map<String,Object>> paySonlist);
}
