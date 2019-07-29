package com.heitian.ssm.service.project;

import com.heitian.ssm.model.project.CheckModel;

import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-12 23:21
 **/
public interface CheckService {

    int insertCheck(CheckModel checkModel);

    //获取单抽查单项目
    List<CheckModel> SelectCheckId(CheckModel checkModel);

    //获取但项目的抽查单
    List<CheckModel> selectCheck(CheckModel checkModel);

    //抽查单添加文本
    int UpdateCheckDoc(CheckModel checkModel);

    int falsh(List<Map<String, Object>> list);

    int selectCount(String YEAR, String CBMUNITID);


}
