package com.heitian.ssm.service.project;

import com.heitian.ssm.model.project.CapitalModel;

import java.util.List;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-15 23:52
 **/
public interface Capitalservice {

    List<CapitalModel> selectCapital(CapitalModel capitalModel);

}
