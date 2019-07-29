package com.heitian.ssm.service.payment.impl;

import com.heitian.ssm.dao.payment.PayMentDao;
import com.heitian.ssm.model.payment.*;
import com.heitian.ssm.model.project.Project;
import com.heitian.ssm.service.payment.PayService;
import com.heitian.ssm.utils.excel.ImportExcelUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-12-03 03:56
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class PayServiceImpl implements PayService {

    @Resource

    private PayMentDao payMentDao;

    public List<Project> selectdprog(String CBMUNITID) {
        return payMentDao.selectdprog(CBMUNITID);
    }

    public List<PayModel> selectPayzfgl() {
        return payMentDao.selectPayzfgl();
    }

    public int insetpayment(PaymentModel paymentModel) {
        return payMentDao.insetpayment(paymentModel);
    }

    public List<PaymentModel> selectpayment(PaymentModel payment) {
        return payMentDao.selectpayment(payment);
    }


    public List<Project> selectdprog2(String CBMUNITID, String CBMUNITNAME, String IYEAR) {
        return payMentDao.selectdprog2(CBMUNITID, CBMUNITNAME, IYEAR);
    }

    //收款单位
    public List<RecBankModel> selectRecBankList(RecBankModel recBankModel) {
        return payMentDao.selectRecBankList(recBankModel);
    }

    //收款单位
    public List<PayBankModel> selectPayBankList(PayBankModel payBankModel) {
        return payMentDao.selectPayBankList(payBankModel);
    }

    //插入自定义拨款单
    @Override
    public int addProPaymentsSON(List<Map<String,Object>> paysonList) {

      int count = payMentDao.addProPaymentsSON(paysonList);
        return count;
    }

    //查询自定义拨款单
    @Override
    public List<Map<String, Object>> getPaymentsSonList(Map<String, Object> map) {
        return payMentDao.getPaymentsSonList(map);
    }

    //删除自定义拨款单
    @Override
    public int deleteByPno(String pno) {
        return payMentDao.deleteByPno(pno);
    }

    //审批
    @Override
    public int doExam(String EXAMER,List<Map<String,Object>> paySonlist) {
        int count = 0;
        for (int i = paySonlist.size()-1; i >= 0; i--) {
            Map<String,Object> payson = paySonlist.get(i);
            payson.put("EXAMER",EXAMER);
            count = payMentDao.doExam(payson);
        }
        return count;
    }

    //编辑
    @Override
    public int edit(List<Map<String,Object>> paySonlist) {
        int count = 0;
        for (int i = 0; i < paySonlist.size(); i++) {
            Map<String,Object> payson = paySonlist.get(i);
            count = payMentDao.edit(payson);
        }
        return count;
    }

    //批量删除拨款单
    @Override
    public int delete(List<Map<String,Object>> paySonlist) {
        return payMentDao.delete(paySonlist);
    }

    //导入拨款单
    @Override
    public boolean importExcel(InputStream in, String fileName, String path, String cbmunitid,String operator,String iyear,String POWER,String CPOSTGUID) {
        System.out.println("文件导入开始" + in);

        try {
            int i = 1;
            int count = 0;
            List<PayMentSonModel> paysonModelList = new ArrayList<>();
            List<List<Object>> listob = new ImportExcelUtil().getBankListByExcel(in, fileName, path);
            System.out.print("listob" + listob.size() + "\n");

            for (int j = 1; j < listob.size(); j++) {
                if ( listob.get(j).get(2) != ""){
                    PayMentSonModel payson = new PayMentSonModel();
                    payson.setSTATUS(listob.get(j).get(0) == null ? "" : listob.get(j).get(0).toString());
                    payson.setEXAMER(listob.get(j).get(1) == null ? "" : listob.get(j).get(1).toString());
                    payson.setPAYDATE(listob.get(j).get(2) == null ? "" : listob.get(j).get(2).toString());
                    payson.setCODE(listob.get(j).get(3) == null ? "" : listob.get(j).get(3).toString());
                    payson.setRECBANKNAME(listob.get(j).get(4) == null ? "" : listob.get(j).get(4).toString());
                    payson.setRECBANKNO(listob.get(j).get(5) == null ? "" : listob.get(j).get(5).toString());
                    payson.setRECNAME(listob.get(j).get(6) == null ? "" : listob.get(j).get(6).toString());
                    double d1 = new DecimalFormat().parse(listob.get(j).get(7) == null ? "0" : listob.get(j).get(7).toString()).doubleValue(); //这里使用的是parse，不是format
                    payson.setIMONEY(String.valueOf(d1));
                    payson.setPURPOSE(listob.get(j).get(8) == null ? "" : listob.get(j).get(8).toString());
                    payson.setENAME(listob.get(j).get(9) == null ? "" : listob.get(j).get(9).toString());
                    payson.setPAYBANKNO(listob.get(j).get(10) == null ? "" : listob.get(j).get(10).toString());
                    payson.setPAYBANKNAME(listob.get(j).get(11) == null ? "" : listob.get(j).get(11).toString());
                    String CFUNCTIONCODE = listob.get(j).get(12) == null ? "" : listob.get(j).get(12).toString();
                    if (CFUNCTIONCODE.length()==3){
                        payson.setCFUNCTIONCODE1(CFUNCTIONCODE);
                        payson.setCFUNCTIONNAME1(listob.get(j).get(13) == null ? "" : listob.get(j).get(13).toString());
                    }else if (CFUNCTIONCODE.length()==5){
                        payson.setCFUNCTIONCODE2(CFUNCTIONCODE);
                        payson.setCFUNCTIONNAME2(listob.get(j).get(13) == null ? "" : listob.get(j).get(13).toString());
                    }else if (CFUNCTIONCODE.length()==7){
                        payson.setCFUNCTIONCODE3(CFUNCTIONCODE);
                        payson.setCFUNCTIONNAME3(listob.get(j).get(13) == null ? "" : listob.get(j).get(13).toString());
                    }
                    payson.setCBMUNITID(cbmunitid);
                    payson.setOPERATOR(operator);
                    payson.setIYEAR(iyear);
                    payson.setPOWER(POWER);
                    payson.setCPOSTGUID(CPOSTGUID);
                    paysonModelList.add(payson);
                }

                if (i % 100 == 0) {
                    count = payMentDao.importExcel(paysonModelList);
                    i = 1;
                    paysonModelList.clear();
                } else {
                    i++;
                }
            }

            if (i > 1) {
                count = payMentDao.importExcel(paysonModelList);
            }
            System.out.println("文件导入成功" + count);
            return true;
        } catch (Exception e) {
            System.out.println("文件导入失败");
            e.printStackTrace();
        }
        return false;
    }

    //是否作废
    @Override
    public int isThrow(List<Map<String,Object>> paySonlist) {
        int count = 0;
        for (int  i = 0;i<paySonlist.size();i++){
            count = payMentDao.isThrow(paySonlist.get(i));
        }
        return count;
    }
      //是否作废
    @Override
    public int isPrint(List<Map<String,Object>> paySonlist) {
        int count = 0;
        for (int  i = 0;i<paySonlist.size();i++){
            count = payMentDao.isPrint(paySonlist.get(i));
        }
        return count;
    }


}
