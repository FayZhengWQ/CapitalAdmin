package com.heitian.ssm.service.baseConfig.impl;

import com.heitian.ssm.dao.baseConfig.CpostDao;
import com.heitian.ssm.model.baseconfig.CPostFunctionModel;
import com.heitian.ssm.model.baseconfig.CpostModel;
import com.heitian.ssm.model.baseconfig.OperPostFunctionModel;
import com.heitian.ssm.service.baseConfig.CpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:CpostServiceImpl
 * Package:com.heitian.ssm.service.baseConfig.impl
 * Description:
 * author:@Fay
 * Date:2019/4/1019:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CpostServiceImpl implements CpostService {

    @Autowired
    private CpostDao cpostDao;

    @Override
    public List<CpostModel> queryAllCpost() {
        return cpostDao.queryAllCpost();
    }

    @Override
    public int modifierisDisable(String isdisableed, String cpostguid) {
        Map map = new HashMap();
        map.put("isdisableed",isdisableed);
        map.put("cpostguid",cpostguid);
        return cpostDao.modifierisDisable(map);
    }


    /*CPOSTFUNCTION 表 操作
     * 根据cpostguid 删除postfunction对应的数据
     * 把cpostguid对应的cfunctionguid插入cpostfunction表*/

    @Override
    public int doFuncGuid(String cpostguid,List<CPostFunctionModel> funcguidList) {
        int count =  cpostDao.deleteFuncGuid(cpostguid);
        int insertcount = 0;
        int insertOPF = 0;
        if (count >=0){
            List<CPostFunctionModel> pfList = new ArrayList<>();
            for (int i = 0 ;i<funcguidList.size();i++){
                CPostFunctionModel cPostFunction = new CPostFunctionModel();
                cPostFunction.setCFUNCTIONGUID( funcguidList.get(i).getCFUNCTIONGUID());
                cPostFunction.setCPOSTGUID(cpostguid);
                pfList.add(cPostFunction);
            }
            insertcount= cpostDao.insertPostFunction(pfList);
        }

        //对operpostfunction表进行操作
        //根据cpostguid将【operpostfunction】对应的cuserid查出
        List<OperPostFunctionModel> list = cpostDao.slectOperPostFunction(cpostguid);
        if (list.size()>0) {
            //在根据cpostguid删除数据
            int deleteOPF = cpostDao.deleteOPF(cpostguid);
            //将cpostguid cfunctionguid cuserid插入到OperPostFunction

            if (deleteOPF > 0) {
                //人循环出来 一个人里循环cfunctionguid，放cfunctionguid再放cpostguid
                List OPFList = new ArrayList();
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < funcguidList.size(); j++) {
                        OperPostFunctionModel operPostFunction = new OperPostFunctionModel();
                        operPostFunction.setCFUNCTIONGUID(funcguidList.get(j).getCFUNCTIONGUID());
                        operPostFunction.setCPOSTGUID(cpostguid);
                        operPostFunction.setCUSERID(list.get(i).getCUSERID());
                        OPFList.add(operPostFunction);
                    }

                }

                insertOPF = cpostDao.insetOPF(OPFList);

            }
        }
        return insertOPF;
    }

    @Override
    public int addcpost(String cpostname, String cpostguid,List<CPostFunctionModel> PFlist) {
        int count = 0;
        CpostModel cpostModel = new CpostModel();
        List<CpostModel> pList = cpostDao.getCPostByCPostguid(cpostguid,cpostname);
        if (pList.size() > 0){
            System.out.println("岗位guid已存在");
            return 0;
        }else{
            cpostModel.setCPOSTGUID(cpostguid);
            cpostModel.setCPOSTNAME(cpostname);
            int insertCount= cpostDao.addcpost(cpostModel);
            System.out.println("插入CPOST的条数："+insertCount);

            //插入数据到postFunction表
            List list = new ArrayList();
            for (int i = 0; i<PFlist.size();i++){
                CPostFunctionModel cPFModel = new CPostFunctionModel();
                cPFModel.setCFUNCTIONGUID(PFlist.get(i).getCFUNCTIONGUID());
                cPFModel.setCPOSTGUID(cpostguid);
                list.add(cPFModel);
            }
            count = cpostDao.insertPostFunction(list);
            System.out.println("批量插入条数："+count);
        }

        return count;

    }

}
