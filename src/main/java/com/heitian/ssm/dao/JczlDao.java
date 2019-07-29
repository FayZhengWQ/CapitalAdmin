package com.heitian.ssm.dao;

import com.heitian.ssm.model.JczlBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-08 13:34
 **/
@Repository
public interface JczlDao {


    //单位
    List<JczlBean> SelcetAllUnit(@Param("year") String year, @Param("CUNITNAME") String CUNITNAME);

    //功能科目
    List<JczlBean> SelectAllFun(@Param("year") String year, @Param("CFUNCTIONNAME") String CFUNCTIONNAME);

    //部门经济科目
    List<JczlBean> SelectAllEcon(@Param("year") String year, @Param("CECONOMYSECTIONNAME") String CECONOMYSECTIONNAME);

    //政府经济科目
    List<JczlBean> SelectAllEconGov(@Param("year") String year, @Param("CECONOMYSECTIONGOVNAME") String CECONOMYSECTIONGOVNAME);

    //资金来源
    List<JczlBean> SelectAllResoure(@Param("year") String year, @Param("CRESOURCENAME") String CRESOURCENAME);

    //指标类型
    List<JczlBean> SelectAllBudget(@Param("year") String year, @Param("BUDGETCATEGORYNAME") String BUDGETCATEGORYNAME);

    //项目类型
    List<JczlBean> SelectAllOptions(@Param("year") String year, @Param("CPROGRAMTYPENAME") String CPROGRAMTYPENAME);

    //科室
    List<JczlBean> SelectAllDivision(@Param("year") String year, @Param("DGKKS") String DGKKS);

}
