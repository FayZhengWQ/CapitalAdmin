package com.heitian.ssm.service.baseConfig;

import com.heitian.ssm.model.baseconfig.FunctionModel;

import java.util.List;

/**
 * ClassName:FunctionService
 * Package:com.heitian.ssm.service.baseConfig
 * Description:
 * author:@Fay
 * Date:2019/4/1019:47
 */
public interface FunctionService {

    List<FunctionModel> selectfunction(FunctionModel functionModel);

    List<FunctionModel> queryFuncByCondition(String cpostguid);

    int addFunc(String title, String icon, String visible, String routerlink, String levelNum, String cpostguid, String parentGuid);

    int isVisible(String visible, String guid, String icon, String routerlink);


}
