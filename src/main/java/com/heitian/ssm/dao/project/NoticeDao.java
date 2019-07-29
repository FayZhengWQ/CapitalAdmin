package com.heitian.ssm.dao.project;

import com.heitian.ssm.model.project.NoticeBean;
import com.heitian.ssm.model.project.NoticeEditorModel;
import com.heitian.ssm.model.project.Project;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-07 10:21
 **/

@Repository
public interface NoticeDao {

    //查询所有项目
    List<Project> selcetAllNotice(
            @Param("ROWNUM") String ROWNUM,
            @Param("RN") String RN,
            @Param("CBMUNITID") String CBMUNITID,
            @Param("FILENO") String FILENO,
            @Param("CXZPROGID") String CXZPROGID,
            @Param("CXZPROGNAME") String CXZPROGNAME

    );
    //添加公告单
    int AddNotice(NoticeBean noticeBean);

    //查询公告单详情
    List<NoticeBean> SelectNoticeDetail(NoticeBean noticeBean);

    //更新公告单详情
    int UpdateNotice(NoticeBean noticeBean);


    //修改公告单
    int UpdateNoticeDoc(NoticeBean noticeBean);

    //联表查询
    List<NoticeEditorModel> selectNoticeDprog(NoticeEditorModel noticeBean);

    int  falsh(List<Map<String, Object>> list);
}
