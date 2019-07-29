package com.heitian.ssm.service.baseConfig.impl;

import com.heitian.ssm.dao.baseConfig.FunctionDao;
import com.heitian.ssm.dao.baseConfig.UserDao;
import com.heitian.ssm.model.baseconfig.FunctionModel;
import com.heitian.ssm.service.baseConfig.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:FunctionServiceImpl
 * Package:com.heitian.ssm.service.baseConfig.impl
 * Description:
 * author:@Fay
 * Date:2019/4/1019:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FunctionServiceImpl implements FunctionService {
    @Autowired
    private FunctionDao functionDao;
    @Autowired
    private UserDao userDao;


    public List<FunctionModel> selectfunction(FunctionModel functionModel){
        return   functionDao.selectfunction(functionModel);
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
    public int addFunc(String title, String icon, String visible, String routerlink, String levelNum, String cpostguid, String parentGuid) {
        //判断是否存在这个title的function
        FunctionModel func = functionDao.getFunByTitle(title);
        if (func == null ){
            if(levelNum.equals("1")){
                //层级为1的时候，生成parentguid =0
                // 生成GUID(获取做大的parent再在前面拼接一个0)
                parentGuid = "0";
                Map map = new HashMap();
                map.put("parentGuid",parentGuid);
                map.put("levelNum",levelNum);
                Integer preGuid = functionDao.getMaxGuid(map);
                String guid = "0"+preGuid;

                FunctionModel function = new FunctionModel();
                function.setGUID(guid);
                function.setICON(icon);
                function.setLEVEL_NUM(levelNum);
                function.setPARENT_GUID(parentGuid);
                function.setROUTERLINK(routerlink);
                function.setTITLE(title);
                function.setVISIBLE(visible);
                function.setOPERATOR("艾迪");

                int count = functionDao.addFunc(function);
                Map map2 = new HashMap();
                map2.put("cpostguid",cpostguid);
                map2.put("cfunctionguid",guid);
                int count2 = functionDao.addPostFunction(map2);

                if (count == 1 && count2 == 1){
                    return 1;
                }else{
                    return 0;
                }

            }else{

                //生成guid
                Map map = new HashMap();
                map.put("parentGuid",parentGuid);
                map.put("levelNum",levelNum);
                Integer preGuid = functionDao.getMaxGuid(map);
                String guid = "0"+preGuid;

                FunctionModel function = new FunctionModel();
                function.setGUID(guid);
                function.setICON(icon);
                function.setLEVEL_NUM(levelNum);
                function.setPARENT_GUID(parentGuid);
                function.setROUTERLINK(routerlink);
                function.setTITLE(title);
                function.setVISIBLE(visible);
                function.setOPERATOR("艾迪");

                int count = functionDao.addFunc(function);
                Map map2 = new HashMap();
                map2.put("cpostguid",cpostguid);
                map2.put("cfunctionguid",guid);
                int count2 = functionDao.addPostFunction(map2);

                if (count == 1 && count2 == 1){
                    return 1;
                }else{
                    return 0;
                }

            }
        }else{
            System.out.println("title已存在");
            return 2;
        }
    }

    @Override
    public int isVisible(String visible, String guid, String icon, String routerlink) {
        Map map = new HashMap();
        map.put("visible",visible);
        map.put("guid",guid);
        map.put("icon",icon);
        map.put("routerlink",routerlink);
        int count = functionDao.isViaible(map);
        return count;
    }
}
