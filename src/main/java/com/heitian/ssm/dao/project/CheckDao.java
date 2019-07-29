package com.heitian.ssm.dao.project;

import com.heitian.ssm.model.project.CheckModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-12 23:20
 **/
@Repository
public interface CheckDao {


    //添加抽查担子
    int insertCheck(CheckModel model);

    //获取单抽查单列表

    List<CheckModel> SelectCheckId(CheckModel model);


    //获取抽查单列表
    List<CheckModel> selectCheck(CheckModel checkModel);

    //添加单抽查单文本
    int UpdateCheckDoc(CheckModel checkModel);

    int falsh(List<Map<String, Object>> list);

    int selectCount(String IYEAR, String CBMUNITID);
}
