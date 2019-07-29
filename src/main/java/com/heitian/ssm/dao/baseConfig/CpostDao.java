package com.heitian.ssm.dao.baseConfig;

import com.heitian.ssm.model.baseconfig.CPostFunctionModel;
import com.heitian.ssm.model.baseconfig.CpostModel;
import com.heitian.ssm.model.baseconfig.OperPostFunctionModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName:CpostDao
 * Package:com.heitian.ssm.dao.baseConfig
 * Description:
 * author:@Fay
 * Date:2019/4/1019:54
 */
@Repository
public interface CpostDao {
    List<CpostModel> queryAllCpost();

    int modifierisDisable(Map map);

    int deleteFuncGuid(String cpostguid);

    int insertPostFunction(List<CPostFunctionModel> pfList);

    List<OperPostFunctionModel> slectOperPostFunction(String cpostguid);

    int deleteOPF(String cpostguid);

    int insetOPF(List opfList);

    List<CpostModel> getCPostByCPostguid(String cpostguid, String cpostname);

    int addcpost(CpostModel cpostModel);
}
