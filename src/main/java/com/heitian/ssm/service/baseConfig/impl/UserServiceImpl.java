package com.heitian.ssm.service.baseConfig.impl;

import com.heitian.ssm.dao.baseConfig.UserDao;
import com.heitian.ssm.model.baseconfig.CPostFunctionModel;
import com.heitian.ssm.model.baseconfig.FunctionModel;
import com.heitian.ssm.model.baseconfig.OperPostFunctionModel;
import com.heitian.ssm.model.baseconfig.UserModel;
import com.heitian.ssm.service.baseConfig.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-14 14:08
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl  implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public List<UserModel> selectUser(UserModel userModel) {
        return userDao.selectUser(userModel);
    }

    //查询selectBMUNITS
    public List<UserModel> selectBMUNITS(){
        return  userDao.selectBMUNITS() ;
    }

    //注册
    public  int insertUser(UserModel userModel){
        return  userDao.insertUser(userModel);

    }

    //查询所有用户
    public List<UserModel> SelectUser(){
        return  userDao.SelectUser();
    }

    //删除制定用户
    public int DeleteUser(UserModel userModel){
        return userDao.DeleteUser(userModel);
    }

    //查询所有用户
    @Override
    public List<UserModel> qureyAllUser() {
        return userDao.queryAllUser();
    }

    //登录
    @Override
    public List<Map<String,Object>> login(String cusername, String cpassword) {
        Map map = new HashMap();
        map.put("cusername",cusername);
        map.put("cpassword",cpassword);
        return userDao.login(map);
    }

    //根据名字查询用户是否存在
    @Override
    public UserModel queryUserByName(String cusername, String nickname) {
        Map map = new HashMap();
        map.put("cusername",cusername);
        map.put("nickname",nickname);
        return userDao.queryUserByName(map);
    }

    //注册
    @Override
    public Map<String,Object> register(String cusername, String cpassword, String sfzh, String cbmnuitid, String cbmunitname,String cpostguid, String phone,String nickname,String shorter) {
        Map<String,Object> result = new HashMap<>();
        UserModel user = new UserModel();
        user.setCPASSWORD(cpassword);
        user.setCUSERNAME(cusername);
        user.setCBMUNITID(cbmnuitid);
        user.setCBMUNITNAME(cbmunitname);
        user.setCPOSTGUID(cpostguid);
        user.setPHONE(phone);
        user.setSFZH(sfzh);
        user.setNICKNAME(nickname);
        user.setSHORTER(shorter);
        System.out.print("来到这里");
        int count = userDao.register(user);
        String cuserid = user.getCUSERID()+"";
        System.out.println(cuserid);
        if (count > 0 ){
            result.put("flag","true");
            result.put("cuserid",cuserid);
        }else{
            result.put("flag","false");
        }
        return result;
    }


    @Override
    public List<FunctionModel> queryFuncByCondition(String cpostguid) {
        List<FunctionModel>  func = null;
        if (cpostguid.isEmpty()){
            func = userDao.getAllFunc();
        }else {
            func = userDao.queryFuncByCondition(cpostguid);
        }
        return func;
    }

    @Override
    public int insertOperpostfunction(List<OperPostFunctionModel> list){
        return  userDao.insertOperpostfunction(list);
    }

    @Override
    public int modefierUser(String isDisable, String isTest, String cusername, String cpostguid, String cuserid, String cpassword, String phone,String nickname,String shorter) {
        UserModel user = new UserModel();
        user.setISTEST(isTest);
        user.setISDISABLE(isDisable);
        user.setCUSERNAME(cusername);
        user.setCPOSTGUID(cpostguid);
        user.setCUSERID(Integer.valueOf(cuserid));
        user.setCPASSWORD(cpassword);
        user.setPHONE(phone);
        user.setSHORTER(shorter);
        user.setNICKNAME(nickname);
        int count = userDao.updateUser(user);
        return count;
    }

    @Override
    public int userUpdateCpost(String cpostguid, String cuserid) {

        int deleteOPF = userDao.deleteOPFByCondiction(cuserid);
        System.out.println("删除数据条数："+deleteOPF);

        //根据cpost查询postFunction对应的数据列表
        List<CPostFunctionModel> PFModel = userDao.selectPF(cpostguid);

        //到operpostfunction增加数据
        List OPFList = new ArrayList();
        for(int i = 0;i < PFModel.size();i++){
            OperPostFunctionModel operPostFunction = new OperPostFunctionModel();
            operPostFunction.setCFUNCTIONGUID(PFModel.get(i).getCFUNCTIONGUID());
            operPostFunction.setCPOSTGUID(cpostguid);
            operPostFunction.setCUSERID(cuserid);
            OPFList.add(operPostFunction);
        }
        int insertCount = userDao.insetOPF(OPFList);
        System.out.println("插入OPF条数："+insertCount);

        //根据cuserid更新cpostguid字段
        Map map = new HashMap();
        map.put("cpostguid",cpostguid);
        map.put("cuserid",cuserid);
        int count = userDao.updateCpostByCuserid(map);
        System.out.println("更新的user表的条数"+count);
        return count;
    }


}
