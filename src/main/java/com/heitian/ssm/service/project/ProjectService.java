package com.heitian.ssm.service.project;

import com.heitian.ssm.model.project.CheckModel;
import com.heitian.ssm.model.project.NoticeBean;
import com.heitian.ssm.model.project.Project;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description: 乡镇项目库
 * @author: liangsizhuuo@163.com
 * @create: 2018-10-30 11:22
 **/
public interface ProjectService {

    //query dprog查询条件

    List<Map<String, Object>> slectHome(String CBMUNITID, String IYEAR);


    List<Project> selectAllDprog(Project project);

    //从区级数据
//    List<Project>  selectAllProject(Project project);

//    List<Project> selectPlate(Project project);

    List<Project> selectNoticeCheck(Project project);

    //删除项目
//    int deleteDprog(Project project);
    //修改项目
    int updateDprog(Project project);


    //修改项目notice_state状态
    int UpdateNoticeState(NoticeBean noticeBean);

    int UpdateCheckState(CheckModel checkModel);

    //添加[上级专项][上级补助]
//    int addProject(Project project);
    int selectDprogCount(Project project);

    int selectProjectCount(Project project);

//    int countProg(Project project);


//    Map<String,Object> importExcel(InputStream in, String fileName, String path, String cbmunitid, String cbmunitname, String state);


    //***************************************************************可开委接口*************************************************//
    int addkkwproject(List<Map<String, Object>> list);


//    List<Map<String,Object>> getCECONOMYSECTION(Map<String, Object> map);


    //--------------------------------------------xmk重构-----------------------------------------------------
    int addProject(List<Map<String, Object>> list);

    int addFile(Map<String,Object> map);

    List<Map<String, Object>> getProject(Map<String, Object> param) throws IOException, SQLException;

    int editProject(List<Map<String, Object>> list);

    int deleteProject(Map<String, Object> param);

    List<Map<String, Object>> getFileList(Map<String, Object> param);

    int deleteFile(List<Map<String, Object>> list);
}
