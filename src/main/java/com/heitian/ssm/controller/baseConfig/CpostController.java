package com.heitian.ssm.controller.baseConfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.baseconfig.CPostFunctionModel;
import com.heitian.ssm.model.baseconfig.CpostModel;
import com.heitian.ssm.service.baseConfig.CpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:CpostController
 * Package:com.heitian.ssm.controller.baseConfig
 * Description:
 * author:@Fay
 * Date:2019/4/1019:38
 */
@Controller
@CrossOrigin
public class CpostController {
    @Autowired
    private CpostService cpostService;

    /*岗位模块*/
    /**查询所有数据*/
    @RequestMapping("/cpost/queryAllCpost")
    public @ResponseBody
    JsonData queryAllCpost(){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        List<CpostModel> cpostModelList = cpostService.queryAllCpost();
        jsonData = jsonData.success(cpostModelList,"成功",cpostModelList.size());
        return jsonData;
    }


    /**
     * 禁用对应数据
     */
    @RequestMapping("/cpost/isDisable")
    public @ResponseBody JsonData modifierisDisable(@RequestParam("isdisableed")String isdisableed, @RequestParam("cpostguid")String cpostguid){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int count = cpostService.modifierisDisable(isdisableed,cpostguid);
        if (count == 1){
            jsonData = jsonData.success("更新成功");
        }else{
            jsonData = jsonData.fail("更新失败");
        }
        return jsonData;
    }


    /**
     * 根据岗位修改功能
     * 先根据cpostguid删除【postfunction】中对应的数据
     * 更具cpostguid 添加对应的数据     前端传list==>[cfunctionguid]
     * 再根据cpostguid将【operpostfunction】对应的cuserid查出 在更具cpostguid删除数据
     * 将cpostguid cfunctionguid cuserid
     */
    @RequestMapping(value = "/cpost/updatecpost" ,method= RequestMethod.POST)
    public @ResponseBody JsonData updatecpost(HttpServletRequest request){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData=new JsonData();

        List<CPostFunctionModel> PFlist = JSON.parseObject(request.getParameter("postfunction"), new TypeReference<ArrayList<CPostFunctionModel>>(){});
        String cpostguid = request.getParameter("cpostguid");

        int count = cpostService.doFuncGuid(cpostguid,PFlist);
        if (count >= 0){
            jsonData = jsonData.success("操作成功");
        }else{
            jsonData = jsonData.fail("操作失败");
        }
        return jsonData;
    }


    /**
     * 添加一条岗位模块
     */
    @RequestMapping(value = "/cpost/addcpost", method= RequestMethod.POST)
    public @ResponseBody JsonData addcpost(HttpServletRequest request){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<CPostFunctionModel> PFlist = JSON.parseObject(request.getParameter("postfunction"), new TypeReference<ArrayList<CPostFunctionModel>>(){});
        String cpostguid = request.getParameter("cpostguid");
        String cpostname = request.getParameter("cpostname");

        JsonData jsonData  = new JsonData();
        if (PFlist.size()>0){
            int count = cpostService.addcpost(cpostname,cpostguid,PFlist);
            if (count >= 1){
                jsonData = jsonData.success("插入成功");
            }else if (count == 0){
                jsonData = jsonData.fail("岗位guid或岗位名称已存在");
            }
        }else{
            jsonData=jsonData.fail("请选择功能");
        }
        return jsonData;
    }
}
