package com.heitian.ssm.dao.baseConfig;

import com.heitian.ssm.model.baseconfig.CPostFunctionModel;
import com.heitian.ssm.model.baseconfig.FunctionModel;
import com.heitian.ssm.model.baseconfig.OperPostFunctionModel;
import com.heitian.ssm.model.baseconfig.UserModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName:UserDao
 * Package:com.heitian.ssm.dao.baseConfig
 * Description:
 * author:@Fay
 * Date:2019/4/1019:53
 */
@Repository
public interface UserDao {

    List<UserModel> selectUser(UserModel userModel);

    //查询
    List<UserModel> selectBMUNITS();

    //注册
    int insertUser(UserModel userModel);

    List<UserModel>  SelectUser();

    //删除用户
    int DeleteUser(UserModel userModel);


    List<UserModel> queryAllUser();

    List<Map<String,Object>> login(Map map);

    UserModel queryUserByName(Map map);

    int register(UserModel user);

    List<FunctionModel> getAllFunc();

    List<FunctionModel> queryFuncByCondition(String cpostguid);

    int insertOperpostfunction(List<OperPostFunctionModel> list);

    int updateUser(UserModel user);

    int deleteOPFByCondiction(String cuserid);

    List<CPostFunctionModel> selectPF(String cpostguid);

    int insetOPF(List opfList);

    int updateCpostByCuserid(Map map);
}
