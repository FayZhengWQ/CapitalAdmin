package com.heitian.ssm.service.project;

import com.heitian.ssm.model.project.NoticeBean;
import com.heitian.ssm.model.project.NoticeEditorModel;
import com.heitian.ssm.model.project.Project;

import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-07 10:11
 **/
public interface NoticeService {


    //查询公告单列表 添加公告单 公告单详情

    List<Project> getAllNotice(String ROWNUM, String RN, String CBMUNITID, String FILENO, String CXZPROGID, String CXZPROGNAME);

    //添加公告单

    int AddNotice(NoticeBean noticeBean);

    //查询公告单详情
    List<NoticeBean> getNoticeDetail(NoticeBean noticeBean);

    //更新公告单详情
    int UpdateNotice(NoticeBean noticeBean);

    int UpdateNoticeDoc(NoticeBean noticeBean);


    List<NoticeEditorModel> selectNoticeDprog(NoticeEditorModel selectNoticeDprog);


    int falsh(List<Map<String, Object>> list);
}
