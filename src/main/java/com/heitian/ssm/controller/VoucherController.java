package com.heitian.ssm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.VoucherBean;
import com.heitian.ssm.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
public class VoucherController {

    @Autowired
    private VoucherService voucherService;
    @ResponseBody
    @RequestMapping(value = "/voucher",method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        ModelAndView mv = new ModelAndView("lodop");
        String PNO=request.getParameter("PNO");
        String CBMUNITID=request.getParameter("CBMUNITID");
        VoucherBean voucherBean=new VoucherBean();
        voucherBean.setPNO(Integer.parseInt(PNO));
        voucherBean.setCBMUNITID(CBMUNITID);
        List<Map<String,Object>> dataList = voucherService.getDataByPno(voucherBean);
        List<VoucherBean> selectInfo=voucherService.selectInfo(voucherBean);
        System.out.print(dataList.get(0).get("LIST"));
        mv.addObject("data", dataList.get(0).get("LIST") );
        mv.addObject("CBMUNITID", CBMUNITID );
        mv.addObject("LODOPUP",selectInfo.get(0).getLODOPUP());
        mv.addObject("LODOPLEFT",selectInfo.get(0).getLODOPLEFT());

        return  mv;
    }


    /**
     * 添加凭证
     * @param request
     * @return
     */

    @RequestMapping(value = "/vouvher/addJson",method = RequestMethod.POST)
    @ResponseBody
    public JsonData addJson(HttpServletRequest request){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        List<Map<String,Object>> jsonList =  JSON.parseObject(request.getParameter("list"), new TypeReference<ArrayList<Map<String,Object>>>() {});
        VoucherBean voucherBean=new VoucherBean();
        voucherBean.setVoucherJson(request.getParameter("list"));
        int count = voucherService.addJson(voucherBean);
        String  pno =String.valueOf(voucherBean.getPNO());
        if (count > 0){
            jsonData = jsonData.success(pno,"添加成功",1);
        }else{
            jsonData = jsonData.fail("添加失败");
        }
        return jsonData;
    }


    /**
     * 添加保存方案
     */
    @RequestMapping(value = "/voucher/add/info",method = RequestMethod.POST)
    @ResponseBody
    public JsonData addInfo(HttpServletRequest request){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        String CBMUNITID=request.getParameter("CBMUNITID");
        String LODOPUP=request.getParameter("LODOPUP");
        String LODOPLEFT=request.getParameter("LODOPLEFT");
        VoucherBean voucherBean=new VoucherBean();
        voucherBean.setCBMUNITID(CBMUNITID);
        voucherBean.setLODOPLEFT(LODOPLEFT);
        voucherBean.setLODOPUP(LODOPUP);
        int count=voucherService.updateInfo(voucherBean);
        if (count > 0){
            jsonData = jsonData.success("添加成功");
        }else{
            jsonData = jsonData.fail("添加失败");
        }
        return jsonData;
    }
}
