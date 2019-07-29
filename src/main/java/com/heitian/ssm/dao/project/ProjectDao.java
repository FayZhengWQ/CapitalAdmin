package com.heitian.ssm.dao.project;

import com.heitian.ssm.model.project.CheckModel;
import com.heitian.ssm.model.project.NoticeBean;
import com.heitian.ssm.model.project.Project;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-01 14:33
 **/
@Repository
public interface ProjectDao {


    List<Map<String,Object>> slectHome(Map map);


    List<Project> selectAllDprog(/*Map map*/Project project);
    List<Project> selectPlate(Project project);

    List<Project>  selectNoticeCheck(Project project);


    //删除项目
    int deleteDprog(Project project);

    //修改项目
    int updateDprog(Project project);


    //从区级获取数据
    List<Project> selectAllProject(Project project);


    //修改NOTICE_STATE 状态
    int UpdateNoticeState(NoticeBean noticeBean);

    //修改CHECK_STATE状态
    int UpdateCheckState(CheckModel checkModel);

    //添加项目
//    int addProject(Project project);

    int selectDprogCount(Project project);

    int selectProjectCount(Project project);

    int countProg(Project project);

    //***************************************************************可开委接口*************************************************//

    

    //下发
    int addkkwproject(Map<String, Object> map);


    List<Map<String,Object>> getCECONOMYSECTION(Map<String, Object> map);

    Map<String, Object> getCECONOMYSECTION1(Map<String, Object> map);


//    ----------------------------------------------xmk重构-------------------------------------------------------
    int addProject(Map<String, Object> map);

    String getXmGuid(Map<String, Object> map);

    int addFile(Map<String, Object> file);

    List<Map<String, Object>> getProject(Map<String, Object> param);

    int editProject(Map<String, Object> map);

    List<Map<String, Object>> getFileList(Map<String, Object> param);

    int deleteFile(List<Map<String, Object>> fileList);

    int deleteProject(Map<String, Object> param);
}
