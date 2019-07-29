package com.heitian.ssm.service.project.impl;

import com.heitian.ssm.dao.project.ProjectDao;
import com.heitian.ssm.model.project.CheckModel;
import com.heitian.ssm.model.project.NoticeBean;
import com.heitian.ssm.model.project.Project;
import com.heitian.ssm.service.project.ProjectService;
import com.heitian.ssm.utils.ClobToStringUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-10-30 11:22
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ProjectServiceImpl  implements ProjectService {

    @Resource
    private ProjectDao projectDao;

    public  List<Map<String,Object>> slectHome(String CBMUNITID,String IYEAR){
        Map map = new HashMap();
        map.put("CBMUNITID",CBMUNITID);
        map.put("IYEAR",IYEAR);
        return  projectDao.slectHome(map);
    }

    //query查询条件

    public List<Project> selectAllDprog(/*Map map*/Project project) {
        List<Project> progList = projectDao.selectAllDprog(project);
        return  progList;
    }

    public List<Project> selectNoticeCheck(Project project){
        return  projectDao.selectNoticeCheck(project);
    }

    public List<Project> selectPlate(Project project){
        return  projectDao.selectPlate(project);
    }


    public int deleteDprog(Project project){
        return  projectDao.deleteDprog(project);
    }

    //修改项目
    public int updateDprog(Project project){
        return  projectDao.updateDprog(project);
    }
    public List<Project>   selectAllProject(Project project){
        return  projectDao.selectAllProject(project);
    }


    //修改项目notice_state状态
    public  int UpdateNoticeState(NoticeBean noticeBean){
        return projectDao.UpdateNoticeState(noticeBean);
    }

    public  int UpdateCheckState(CheckModel checkModel){
        return  projectDao.UpdateCheckState(checkModel);
    }
    //添加[上级专项][上级补助]
//    public  int addProject(Project project){
//        return projectDao.addProject(project);
//    }

    public  int selectDprogCount(Project project){
        return projectDao.selectDprogCount(project);
    }

    public  int selectProjectCount(Project project){
        return  projectDao.selectProjectCount(project);
    }

    public int countProg(Project project){
        return projectDao.countProg(project);
    }

    /*@Override
    public Map<String,Object> importExcel(InputStream in, String fileName, String path, String CBMUNITID, String CBMUNITNAME, String STATE) {
        System.out.println("文件导入开始"+in);
        int i = 0;
        Map<String ,Object> result = new HashMap();
        try {

            List<List<Object>> listob = new ImportExcelUtil().getBankListByExcel(in, fileName, path);
            System.out.print("listob" + listob.size() + "\n");

            SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf3= new SimpleDateFormat("yyyy");
            SimpleDateFormat sdf4= new SimpleDateFormat("MM");

            for (int k =1;k<listob.size();k++) {
                Project project=new Project();
                project.setCXZPROGNAME(listob.get(k).get(0)==null?"":listob.get(k).get(0).toString());//项目名称
                project.setFILENO(listob.get(k).get(1)==null?"":listob.get(k).get(1).toString());//文号
                project.setCUNITID(listob.get(k).get(2).toString()==null?"":(listob.get(k).get(2).toString()).split("\\s+")[0]);//单位编码
                project.setCUNITNAME(listob.get(k).get(2).toString()==null?"":(listob.get(k).get(2).toString()).split("\\s+")[1]);//单位名称
                project.setSZTYPENAME("公共财政预算");//收支名称
                project.setCFUNCTIONCODE(listob.get(k).get(3).toString()==null?"":(listob.get(k).get(3).toString()).split("\\s+")[0]);//功能编码
                project.setCFUNCTIONNAME(listob.get(k).get(3).toString()==null?"":(listob.get(k).get(3).toString()).split("\\s+")[1]);//功能名称
                project.setCECONOMYSECTIONCODE(listob.get(k).get(4).toString()==null?"":(listob.get(k).get(4).toString()).split("\\s+")[0]);//部门经济科目编码
                project.setCECONOMYSECTIONNAME(listob.get(k).get(4).toString()==null?"":(listob.get(k).get(4).toString()).split("\\s+")[1]);//部门经济科目名称
                project.setCRESOURCECODE(listob.get(k).get(5).toString()==null?"":(listob.get(k).get(5).toString()).split("\\s+")[0]);//资金来源编码
                project.setCRESOURCENAME(listob.get(k).get(5).toString()==null?"":(listob.get(k).get(5).toString()).split("\\s+")[1]);//资金来源名称
                project.setCBUDGETCATEGORYCODE(listob.get(k).get(6).toString()==null?"":(listob.get(k).get(6).toString()).split("\\s+")[0]);///指标编码
                project.setCBUDGETCATEGORYNAME(listob.get(k).get(6).toString()==null?"":(listob.get(k).get(6).toString()).split("\\s+")[1]);///指标编码
                project.setCPROGRAMTYPECODE(listob.get(k).get(7).toString()==null?"":(listob.get(k).get(7).toString()).split("\\s+")[0]);//资金来源编码
                project.setCPROGRAMTYPENAME(listob.get(k).get(7).toString()==null?"":(listob.get(k).get(7).toString()).split("\\s+")[1]);//资金来源名称
                project.setIMONEY(listob.get(k).get(8).toString()==null?"":listob.get(k).get(8).toString());//金额
                String DDATE= sdf2.format(sdf2.parse(listob.get(k).get(9).toString()==null?"":listob.get(k).get(9).toString()));
                System.out.println("DDTAE"+DDATE);
                project.setDDATE(DDATE);
                project.setIYEAR(sdf3.format(sdf3.parse(listob.get(k).get(9).toString()==null?"":listob.get(k).get(9).toString())));
                project.setIMONTH(sdf4.format(sdf4.parse(listob.get(k).get(9).toString()==null?"":listob.get(k).get(9).toString())));
                project.setCBMUNITID(CBMUNITID);
                if (STATE.equals("0")) {
                    project.setCPROGTYPE("往来资金项目");
                    project.setCXZPROGID("");
                }else if (STATE.equals("1")){
                    project.setCPROGTYPE("自有资金项目");

                    int count=projectDao.countProg(project);
                    project.setCXZPROGID(String.valueOf(count));
                }
                project.setENTERPRISEID("");
                project.setCMEMO("");
                project.setGDATE(DateUtil.getCurrentYear());//获得项目时间
                project.setYEAR(DateUtil.getCurrentYear());
                project.setMONTH(DateUtil.getCurrentMonth());
//              project.setIPROGRAMTYPEGUID("");
                project.setCBMUNITNAME(CBMUNITNAME);
                project.setIGPLANID("");
                project.setCBILLNO("");
                project.setSIGNED("");
                project.setCECONOMYSECTIONGOVCODE("");
                project.setCECONOMYSECTIONGOVNAME("");
                int rows = projectDao.addProject(project);
                if (rows==0){
                    i++;
                }
                if (rows>0){
//                    List<Project> list = projectDao.selectAllDprog(project);
//                    result.put("datalist",list);
                    result.put("msg","success");
                }else{
                    result.put("msg","fail");
                }

            }

        }catch (Exception e){
            e.printStackTrace();
            result.put("msg","failed");
        }
        return result;
    }*/


    //***************************************************************可开委接口*************************************************//


    //下发
    public int addkkwproject(List<Map<String,Object>> list){

        return  projectDao.addkkwproject(list.get(0));
    }

    /*@Override
    public List<Map<String,Object>> getCECONOMYSECTION(Map<String, Object> map) {
        return projectDao.getCECONOMYSECTION(map);
    }*/

    //--------------------------------------------xmk重构------------------------------------------------------------
    @Override
    public int addProject(List<Map<String, Object>> list) {
        int count = 0;
        for (Map<String, Object> map:list) {
            count = projectDao.addProject(map);
        }
        return count;
    }

    @Override
    public int addFile(Map<String,Object> file) {
        int count = projectDao.addFile(file);
        return count;
    }

    @Override
    public List<Map<String, Object>> getProject(Map<String, Object> param) throws IOException, SQLException {
        List<Map<String,Object>> projectList = projectDao.getProject(param);
        for (Map<String,Object> map: projectList) {
            if (map.get("FILENAME")!=null){
                String FILENAME = ClobToStringUtil.ClobToString((Clob) map.get("FILENAME"));
                map.remove("FILENAME");
                map.put("FILENAME", FILENAME);
            }
        }
        return projectList;
    }

    @Override
    public int editProject(List<Map<String, Object>> list) {
        return projectDao.editProject(list.get(0));
    }

    //删除项目的时候先删除附件
    @Override
    public int deleteProject(Map<String, Object> param) {
        List<Map<String,Object>> fileList = projectDao.getFileList(param);
        int count = 0;
        if (!fileList.isEmpty()){
           count = projectDao.deleteFile(fileList);
           if (count > 0){
               //1 成功 0 失败
               try {
                   File file = new File(param.get("PATH").toString());
                   if (file.exists()) {
                       if (file.isDirectory()) {
                           FileUtils.deleteDirectory(file);
                       } else {
                           file.delete();
                       }
                   }
               } catch (Exception ex) {
                   ex.printStackTrace();
               }
               count = projectDao.deleteProject(param);
           }else{
               //2 附件删除失败
               count = 2;
           }
        }else{
            count = projectDao.deleteProject(param);
        }

        return count;
    }

    @Override
    public List<Map<String, Object>> getFileList(Map<String, Object> param) {
        List<Map<String,Object>> fileList = projectDao.getFileList(param);
        return fileList;
    }

    @Override
    public int deleteFile(List<Map<String, Object>> fileList) {
        int count = projectDao.deleteFile(fileList);
        return count;
    }
}
