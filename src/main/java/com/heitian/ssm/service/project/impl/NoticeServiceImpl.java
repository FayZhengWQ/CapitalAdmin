package com.heitian.ssm.service.project.impl;

import com.heitian.ssm.dao.project.NoticeDao;
import com.heitian.ssm.model.project.NoticeBean;
import com.heitian.ssm.model.project.NoticeEditorModel;
import com.heitian.ssm.model.project.Project;
import com.heitian.ssm.service.project.NoticeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-07 10:11
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private NoticeDao noticeDao;

    public List<Project>  getAllNotice(String ROWNUM, String RN, String CBMUNITID,String FILENO,String CXZPROGID,String CXZPROGNAME) {
        return  noticeDao.selcetAllNotice(ROWNUM,RN,CBMUNITID,FILENO,CXZPROGID,CXZPROGNAME);
    }

    public int AddNotice(NoticeBean noticeBean){
        return  noticeDao.AddNotice(noticeBean);
    }

    public List<NoticeBean> getNoticeDetail(NoticeBean noticeBean){
        return noticeDao.SelectNoticeDetail(noticeBean);
    }
    public int UpdateNotice(NoticeBean noticeBean){
        return  noticeDao.UpdateNotice(noticeBean);
    }

    public int UpdateNoticeDoc(NoticeBean noticeBean){
        return  noticeDao.UpdateNoticeDoc(noticeBean);
    }


    public List<NoticeEditorModel> selectNoticeDprog(NoticeEditorModel noticeBean){
        return noticeDao.selectNoticeDprog(noticeBean);
    }

    public int  falsh(List<Map<String,Object>> list){
        return  noticeDao.falsh(list);
    }

}

