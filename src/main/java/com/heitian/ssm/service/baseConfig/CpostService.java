package com.heitian.ssm.service.baseConfig;

import com.heitian.ssm.model.baseconfig.CPostFunctionModel;
import com.heitian.ssm.model.baseconfig.CpostModel;

import java.util.List;

/**
 * ClassName:CpostService
 * Package:com.heitian.ssm.service.baseConfig
 * Description:
 * author:@Fay
 * Date:2019/4/1019:46
 */
public interface CpostService {

    List<CpostModel> queryAllCpost();

    int modifierisDisable(String isdisableed, String cpostguid);

    int doFuncGuid(String cpostguid, List<CPostFunctionModel> pFlist);

    int addcpost(String cpostname, String cpostguid, List<CPostFunctionModel> pFlist);
}
