package com.heitian.ssm.controller.baseConfig;

import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.baseconfig.FunctionModel;
import com.heitian.ssm.service.baseConfig.FunctionService;
import com.heitian.ssm.utils.tree.TreeFunctionNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.heitian.ssm.utils.tree.TreeListUtils.TreeList2;

/**
 * ClassName:FunctionController
 * Package:com.heitian.ssm.controller.baseConfig
 * Description:
 * author:@Fay
 * Date:2019/4/1019:37
 */
@Controller
@CrossOrigin
public class FunctionController {
    
    @Autowired
    private FunctionService functionService;
    /**
     * 搜索
     * @param request
     * @return
     */
    @RequestMapping("/config/function")
    @ResponseBody
    public JsonData SelectFunction(HttpServletRequest request){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String GUID=request.getParameter("GUID");

        FunctionModel functionModel=new FunctionModel();
        functionModel.setGUID(GUID);

        List<FunctionModel> fun_List=functionService.selectfunction(functionModel) ;

        List<TreeFunctionNode> list=TreeList2(fun_List);
        JsonData jsonData=new JsonData();
        jsonData=jsonData.success(list,"成功",list.size());

        return     jsonData;

    }

    /**
     * 功能表查询
     * 功能表根据cpostguid做查询
     */
    @RequestMapping("/function/list")
    public @ResponseBody
    JsonData queryFuncByCondition(@RequestParam(value = "cpostguid",required = false)String cpostguid){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        List<FunctionModel> funcList = functionService.queryFuncByCondition(cpostguid);
        List<TreeFunctionNode> list=TreeList2(funcList);
        jsonData = jsonData.success(list,"成功",list.size());
        return jsonData;
    }



    /**
     * 功能表插入
     * 功能表创建单位id、单位名称为空的数据
     */
    @RequestMapping("/function/add")
    public @ResponseBody
    JsonData addFunc(@RequestParam(value="title")String title, @RequestParam(value="icon")String icon,
                     @RequestParam(value="visible")String visible, @RequestParam(value="routerlink")String routerlink,
                     @RequestParam(value = "levelNum")String levelNum, @RequestParam("cpostguid")String cpostguid,
                     @RequestParam(value = "parentGuid",required = false)String parentGuid){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int count = functionService.addFunc(title,icon,visible,routerlink,levelNum,cpostguid,parentGuid);
        if (count == 1){
            jsonData = jsonData.success("插入成功");
        }else if (count == 0){
            jsonData = jsonData.fail("插入失败");
        }else{
            jsonData = jsonData.fail("该功能已存在");
        }
        return jsonData;
    }



    /**
     * 根据guid修改是否可见、修改图标、修改，路由
     */
    @RequestMapping("/function/visible")
    public @ResponseBody
    JsonData isVisable(@RequestParam("ICON")String icon, @RequestParam("ROUTERLINK")String routerlink, @RequestParam("VISIBLE")String visible, @RequestParam("GUID")String guid){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        int count = functionService.isVisible(visible,guid,icon,routerlink);
        if (count == 1){
            jsonData = jsonData.success("修改成功");
        }else{
            jsonData = jsonData.fail("修改失败");
        }
        return jsonData;

    }

}
