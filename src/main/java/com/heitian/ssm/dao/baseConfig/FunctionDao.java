package com.heitian.ssm.dao.baseConfig;

import com.heitian.ssm.model.baseconfig.FunctionModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName:FunctionDao
 * Package:com.heitian.ssm.dao.baseConfig
 * Description:
 * author:@Fay
 * Date:2019/4/1019:54
 */
@Repository
public interface FunctionDao {

    List<FunctionModel> selectfunction(FunctionModel functionModel);

    FunctionModel getFunByTitle(String title);

    Integer getMaxGuid(Map map);

    int addFunc(FunctionModel function);

    int addPostFunction(Map map2);

    int isViaible(Map map);


}
