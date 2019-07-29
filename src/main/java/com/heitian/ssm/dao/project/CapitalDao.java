package com.heitian.ssm.dao.project;

import com.heitian.ssm.model.project.CapitalModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-15 23:51
 **/
@Repository
public interface CapitalDao {

    List<CapitalModel> selectCapital(CapitalModel capitalModel);

}
