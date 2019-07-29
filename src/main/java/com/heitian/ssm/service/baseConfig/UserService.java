package com.heitian.ssm.service.baseConfig;

import com.heitian.ssm.model.baseconfig.FunctionModel;
import com.heitian.ssm.model.baseconfig.OperPostFunctionModel;
import com.heitian.ssm.model.baseconfig.UserModel;

import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-14 14:10
 **/
public interface UserService {


    List<UserModel> selectUser(UserModel userModel);

    List<UserModel> selectBMUNITS();

    int insertUser(UserModel userModel);

    List<UserModel> SelectUser();

    int DeleteUser(UserModel userModel);

    List<UserModel> qureyAllUser();

    List<Map<String,Object>> login(String cusername, String cpassword);

    UserModel queryUserByName(String cusername, String nickname);

    Map<String,Object> register(String cusername, String cpassword, String sfzh, String cbmnuitid, String cbmunitname, String cpostguid, String phone, String nickname, String shorter);

    List<FunctionModel> queryFuncByCondition(String cpostguid);

    int insertOperpostfunction(List<OperPostFunctionModel> list);

    int modefierUser(String isDisable, String isTest, String cusername, String cpostguid, String cuserid, String cpassword, String phone, String nickname, String shorter);

    int userUpdateCpost(String cpostguid, String cuserid);


}
