package com.heitian.ssm.controller.baseConfig;

import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.common.JsonData;
import com.heitian.ssm.model.baseconfig.FunctionModel;
import com.heitian.ssm.model.baseconfig.OperPostFunctionModel;
import com.heitian.ssm.model.baseconfig.UserModel;
import com.heitian.ssm.service.baseConfig.UserService;
import com.heitian.ssm.utils.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-14 14:07
 **/
@Controller
@CrossOrigin
public class UserController {


    @Resource
    UserService userService;

    

    @RequestMapping("/user/bmunits")
    @ResponseBody
    public JsonData UserBmunits(HttpServletRequest request) {
        DynamicDataSourceHolder.setDataSource("dataSource1");

        List<UserModel> list = userService.selectBMUNITS();
        JsonData jsonData = new JsonData(true);
        jsonData = jsonData.success(list, "获取失败", list.size());

        return jsonData;
    }

    @RequestMapping("/user/list")
    @ResponseBody
    public JsonData UserList(HttpServletRequest request){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<UserModel> list=userService.SelectUser();
        JsonData jsonData=new JsonData();

        if (list.size()>0){
            jsonData=jsonData.success(list,"获取成功",list.size());
        } else {
            jsonData=jsonData.fail("获取失败");
        }

        return jsonData;
    }

    @RequestMapping("/user/delete")
    @ResponseBody
    public JsonData UserDelete(HttpServletRequest request){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String CUSERNAME=request.getParameter("CUSERNAME");
        String CPASSWORD=request.getParameter("CPASSWORD");
        UserModel userModel=new UserModel();
        userModel.setCUSERNAME(CUSERNAME);
        userModel.setCPASSWORD(CPASSWORD);

        int row=userService.DeleteUser(userModel);
        JsonData jsonData=new JsonData();
        if (row==1){
            jsonData.success("删除成功"+CUSERNAME);
        }else {
            jsonData.fail("删除失败");
        }

        return  jsonData;
    }

    /**
     * 查询用户列表
     */
    @RequestMapping("/user/queryAllUser")
    public @ResponseBody JsonData queryAllUser(){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();
        List<UserModel> userList = userService.qureyAllUser();
        jsonData = jsonData.success(userList,"成功",userList.size());
        return jsonData;
    }



    /**
     * 登陆验证
     */
    @RequestMapping("/user/login")
    public @ResponseBody JsonData login( @RequestParam("CUSERNAME")String cusername, @RequestParam("CPASSWORD")String cpassword)  {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<Map<String,Object>> list= userService.login(cusername, cpassword);
        System.out.print(list.size());
        JsonData jsonData = new JsonData();

        //如果用户不为空，登陆成功，跳转到登陆成功的首页页面
        if (list.size()>0) {
            jsonData = jsonData.success(list,"登陆成功",list.size());

        } else {
            //用户和为空，登陆失败，跳转到登陆页面
            jsonData = jsonData.fail("用户名或密码错误");
        }
        return jsonData;
    }

    /**
     * 注册
     */
    @RequestMapping("/user/register")
    public @ResponseBody  JsonData regist(@RequestParam(value="CUSERNAME")String cusername,@RequestParam(value="CPASSWORD")String cpassword,
                                          @RequestParam(value="CBMUNITID")String cbmnuitid,@RequestParam(value="CBMUNITNAME")String cbmunitname,
                                          @RequestParam(value="SFZH",required = false)String sfzh, @RequestParam(value="PHONE",required = false)String phone,@RequestParam("CPOSTGUID")String cpostguid,
                                          @RequestParam(value="NICKNAME",required = false)String nickname,@RequestParam(value="SHORTER",required = false)String shorter){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        UserModel user = userService.queryUserByName(cusername,nickname);
        JsonData jsonData = new JsonData();
        System.out.print(user);

        if (user == null){
            cpassword = MD5Util.getMD5(cpassword);
            Map<String,Object> result = userService.register(cusername,cpassword,sfzh,cbmnuitid,cbmunitname,cpostguid,phone,nickname,shorter);
            if (result.get("flag").equals("true")){
                jsonData=jsonData.success("用户注册成功");
                //先查岗位对功能
                List<FunctionModel> funcList = userService.queryFuncByCondition(cpostguid);
                if (funcList.size()>0) {
                    //获取cuserid
                    String id = result.get("cuserid").toString();
                    System.out.println("cuserid"+id);
                    List<OperPostFunctionModel> list = new ArrayList<>();
                    for (int i = 0; i < funcList.size(); i++) {
                        OperPostFunctionModel model = new OperPostFunctionModel();
                        model.setCUSERID(id);
                        model.setCPOSTGUID(cpostguid);
                        model.setCFUNCTIONGUID(funcList.get(i).getGUID());
                        list.add(model);
                    }
                    int rows = userService.insertOperpostfunction(list);
                    System.out.print("row:"+rows);
                }

            }else{
                jsonData=jsonData.fail("用户注册失败");
            }
        }else{
            jsonData=jsonData.fail("该用户名已注册");

        }
        return jsonData;
    }


    /**
     * 用户修改
     */
    @RequestMapping("/user/update")
    public @ResponseBody JsonData updateUser(@RequestParam("ISDISABLE")String isDisable,@RequestParam("ISTEST")String isTest,@RequestParam("CUSERNAME")String cusername,
                                             @RequestParam("CPASSWORD")String cpassword,@RequestParam("PHONE")String phone,@RequestParam("CPOSTGUID")String cpostguid,
                                             @RequestParam("CUSERID")String cuserid, @RequestParam(value="NICKNAME",required = false)String nickname,
                                             @RequestParam(value="SHORTER",required = false)String shorter){
        DynamicDataSourceHolder.setDataSource("dataSource1");
        JsonData jsonData = new JsonData();

        int count = userService.modefierUser(isDisable,isTest,cusername,cpostguid,cuserid,cpassword,phone,nickname,shorter);
        if (count == 1){
            int rows = userService.userUpdateCpost(cpostguid,cuserid);
            if (rows!=0){
                jsonData = jsonData.success("更新成功");
            }
        }else{
            jsonData = jsonData.fail("更新失败");
        }

        return jsonData;
    }





}
