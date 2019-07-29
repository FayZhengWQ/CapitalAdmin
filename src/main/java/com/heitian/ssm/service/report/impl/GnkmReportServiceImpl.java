package com.heitian.ssm.service.report.impl;

import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.dao.report.GlobalDao;
import com.heitian.ssm.dao.report.JjkmReportDao;
import com.heitian.ssm.service.report.GnkmReportService;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GnkmReportServiceImpl implements GnkmReportService {

    private static final Integer DEFAULT_INSERT_NUMBER = 100;

    @Autowired
    private GlobalDao globalDao;


    //判断已生成到几月的报表
    @Override
    public String getMonth(String datasource, final Map<String, Object> args) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String FIS_PERD;
        FIS_PERD = globalDao.getSetId(new SQL() {{
            SELECT("Max(FIS_PERD) as FIS_PERD");
            FROM("C_REPORT_GNKM");
            WHERE(String.format("setid='%s' And CO_CODE='%s' And FISCAL='%s'", 5, args.get("CO_CODE"), args.get("FISCAL")));
            ORDER_BY("FIS_PERD");
        }}.toString());

        return FIS_PERD;
    }


//    判断是否生成月报及状态

    @Override
    public String IsSetid(String datasource, final Map<String, Object> args) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String setid;

        setid = globalDao. getSetId(new SQL() {{
            SELECT("Min(setid) as setid");
            FROM("C_REPORT_GNKM");
            WHERE(String.format("FIS_PERD='%s' And FISCAL='%s' And co_code='%s'", args.get("FIS_PERD"), args.get("FISCAL"), args.get("CO_CODE").toString()));
        }}.toString());

        return setid;

    }

    @Override
    public List<Map<String, Object>> xzGetList(String datasource, final Map<String, Object> args) {
        List<Map<String, Object>> list = new ArrayList<>();
        DynamicDataSourceHolder.setDataSource(datasource);
        String setid;
        setid = globalDao.getSetId(new SQL() {{
            SELECT("Min(setid) as setid");
            FROM("C_REPORT_GNKM");
            WHERE(String.format("FIS_PERD='%s' And FISCAL='%s' And co_code='%s'", args.get("FIS_PERD"), args.get("FISCAL"), args.get("CO_CODE").toString()));
        }}.toString());

        if (setid == null) {

        } else {
            if (setid.equals("1")) {
                String sql = String.format("Select '' as Id,'' As CO_CODE,'' As SETID,'' As B_ACC_CODE, '合计数' As B_ACC_NAME,'' As FISCAL ,'' As FIS_PERD,Sum(STAD_AMT) as STAD_AMT,\n" +
                        "sum(decode(SETID,1,XZ_STAD_AMT,3,QCZ_STAD_AMT,5,QCZ_STAD_AMT))  as MONEY, 'true' as isDisabled,'' as IS_LOWEST" +
                        "        From C_REPORT_GNKM\n" +
                        "        Where FISCAL='%s' And FIS_PERD='%s'  And CO_CODE='%s' And length(B_ACC_CODE)=3\n" +
                        "        Group By CO_CODE\n" +
                        "         Union All\n" +
                        " Select  to_char(ROWNUM) as Id,t.* from(\n" +
                        "         Select to_char(CO_CODE) As CO_CODE,to_char(SETID) As SETID,to_char(B_ACC_CODE) As B_ACC_CODE, to_char(B_ACC_NAME) As B_ACC_NAME,to_char(FISCAL) As FISCAL ,to_char(FIS_PERD) As FIS_PERD,STAD_AMT,decode(SETID,1,XZ_STAD_AMT,3,QCZ_STAD_AMT,5,QCZ_STAD_AMT) as MONEY , decode(length(B_ACC_CODE),3,'true',5,'true',7,'false') as isDisabled,IS_LOWEST" +
                        "                From C_REPORT_GNKM\n" +
                        "                Where FISCAL='%s' And FIS_PERD='%s' And CO_CODE='%s'  Order by B_ACC_CODE\n" +
                        "                 )t"+
                        "\n", args.get("FISCAL").toString(), args.get("FIS_PERD").toString(), args.get("CO_CODE").toString(), args.get("FISCAL").toString(), args.get("FIS_PERD").toString(), args.get("CO_CODE").toString());
                list = globalDao.selectListMap(sql);


            } else if (setid.equals("3") || setid.equals("5")) {
                String sql = String.format("Select '' as Id,'' As CO_CODE,'' As SETID,'' As B_ACC_CODE, '合计数' As B_ACC_NAME,'' As FISCAL ,'' As FIS_PERD,Sum(STAD_AMT) as STAD_AMT,\n" +
                        "\t\tsum(decode(SETID,1,XZ_STAD_AMT,3,QCZ_STAD_AMT,5,QCZ_STAD_AMT))  as MONEY, 'true' as isDisabled\n" +
                        "        From C_REPORT_GNKM\n" +
                        "        Where FISCAL='%s' And FIS_PERD='%s'  And CO_CODE='%s' And length(B_ACC_CODE)=3\n" +
                        "        Group By CO_CODE\n" +
                        "         Union All\n" +
                        " Select  to_char(ROWNUM) as Id,t.* from(\n" +
                        "         Select to_char(CO_CODE) As CO_CODE,to_char(SETID) As SETID,to_char(B_ACC_CODE) As B_ACC_CODE, to_char(B_ACC_NAME) As B_ACC_NAME,to_char(FISCAL) As \t\t\t\tFISCAL ,to_char(FIS_PERD) As FIS_PERD,STAD_AMT,decode(SETID,1,XZ_STAD_AMT,3,QCZ_STAD_AMT,5,QCZ_STAD_AMT) as MONEY , decode(length(B_ACC_CODE),3,'true',5,'true',7,'false') as isDisabled\n" +
                        "                From C_REPORT_GNKM\n" +
                        "                Where FISCAL='%s' And FIS_PERD='%s' And CO_CODE='%s'  Order by B_ACC_CODE\n" +
                        "                 )t" +
                        "\n", args.get("FISCAL").toString(), args.get("FIS_PERD").toString(), args.get("CO_CODE").toString(), args.get("FISCAL").toString(), args.get("FIS_PERD").toString(), args.get("CO_CODE").toString());
                list = globalDao.selectListMap(sql);
            }
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> xzGetList1(String dataSource1, Map<String, Object> args) {
        List<Map<String, Object>> list;
        String sql = String.format(
                "Select '' as Id,'' As CO_CODE,'' As SETID,'' As B_ACC_CODE, '合计数' As B_ACC_NAME,'' As FISCAL ,'' As FIS_PERD,to_char(Sum(STAD_AMT),'FM999,999,999,999,990.00') as STAD_AMT,\n" +
                        "\t\tto_char(sum(decode(SETID,1,XZ_STAD_AMT,3,QCZ_STAD_AMT,5,QCZ_STAD_AMT)),'FM999,999,999,999,990.00')  as MONEY, 'true' as isDisabled\n" +
                        "        From C_REPORT_GNKM\n" +
                        "        Where FISCAL='%s' And FIS_PERD='%s'  And CO_CODE='%s' And length(B_ACC_CODE)=3\n" +
                        "        Group By CO_CODE\n" +
                        "         Union All\n" +
                        " Select  to_char(ROWNUM) as Id,t.* from(\n" +
                        "         Select to_char(CO_CODE) As CO_CODE,to_char(SETID) As SETID,to_char(B_ACC_CODE) As B_ACC_CODE, to_char(B_ACC_NAME) As B_ACC_NAME,to_char(FISCAL) As \t\t\t\tFISCAL ,to_char(FIS_PERD) As FIS_PERD,to_char(STAD_AMT,'FM999,999,999,999,990.00')STAD_AMT ,to_char(decode(SETID,1,XZ_STAD_AMT,3,QCZ_STAD_AMT,5,QCZ_STAD_AMT),'FM999,999,999,999,990.00') as MONEY , decode(length(B_ACC_CODE),3,'true',5,'true',7,'false') as isDisabled\n" +
                        "                From C_REPORT_GNKM\n" +
                        "                Where FISCAL='%s' And FIS_PERD='%s' And CO_CODE='%s'  Order by B_ACC_CODE\n" +
                        "                 )t Where t.money Is Not Null Or t.STAD_AMT Is Not Null",
                args.get("FISCAL").toString(), args.get("FIS_PERD").toString(), args.get("CO_CODE").toString(), args.get("FISCAL").toString(), args.get("FIS_PERD").toString(), args.get("CO_CODE").toString());
        list = globalDao.selectListMap(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> getList(String datasource, final Map<String, Object> args) {
        List<Map<String, Object>> list = new ArrayList<>();
        DynamicDataSourceHolder.setDataSource(datasource);
        if (datasource.equals("dataSource18")) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("CO_CODE",args.get("CO_CODE"));
            map.put("FISCAL",args.get("FISCAL"));
            map.put("FIS_PERD",args.get("FIS_PERD"));
            globalDao.selectCall(GlobalDao.SQLFactory.selectCall("GET_YYKJHS_ZCGN",true,map),map);
            list = (List<Map<String, Object>>) map.get("cur");
        } else if (datasource.equals("dataSource1")) {
            String sql = String.format(" Select '' as Id,'' As CO_CODE,'' As SETID,'' As B_ACC_CODE,\n" +
                    "                  '合计数' As B_ACC_NAME,'' As FISCAL ,'' As FIS_PERD,Sum(QCZ_STAD_AMT)  as QCZ_STAD_AMT,\n" +
                    "                          'true' as isDisabled\n" +
                    "                  From C_REPORT_GNKM\n" +
                    "                  Where FISCAL='%s'\n" +
                    "                  And FIS_PERD='%s'\n" +
                    "                  And CO_CODE='%s'\n" +
                    "                  And setid in (3,5)\n" +
                    "                  And length(B_ACC_CODE)=3\n" +
                    "                  Group By CO_CODE\n" +
                    "                  Union All\n" +
                    "                  Select * from(\n" +
                    "                  Select to_char(ROWNUM) as Id,to_char(CO_CODE) As CO_CODE,to_char(SETID) As SETID,to_char(B_ACC_CODE) As B_ACC_CODE,\n" +
                    "                  to_char(B_ACC_NAME) As B_ACC_NAME,to_char(FISCAL) As FISCAL ,to_char(FIS_PERD) As FIS_PERD,QCZ_STAD_AMT  as QCZ_STAD_AMT,\n" +
                    "                  decode(length(B_ACC_CODE),3,'true',5,'true',7,'false') as isDisabled\n" +
                    "                  From C_REPORT_GNKM\n" +
                    "                  Where FISCAL='%s'\n" +
                    "                  And FIS_PERD='%s'\n" +
                    "                  And CO_CODE='%s'\n" +
                    "                  And setid in (3,5)\n" +
                    "                  Order by B_ACC_CODE" +
                    "                  )" +
                    "\n", args.get("FISCAL").toString(), args.get("FIS_PERD").toString(), args.get("CO_CODE").toString(), args.get("FISCAL").toString(), args.get("FIS_PERD").toString(), args.get("CO_CODE").toString());
            list = globalDao.selectListMap(sql);


        } else {
            throw new RuntimeException("数据源异常");
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> saveList(List<Map<String, Object>> list, Map<String, Object> args) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        List<Map<String, Object>> result = new ArrayList<>();
        globalDao.deleteObject(String.format("DELETE FROM C_REPORT_GNKM WHERE FISCAL='%s' AND FIS_PERD='%s' AND CO_CODE='%s'", args.get("FISCAL").toString(), args.get("FIS_PERD").toString(), args.get("CO_CODE").toString()));
        String sql = "INSERT ALL ";
        Integer i = 1;
        StringBuffer stringBuffer = new StringBuffer(sql);
        for (Map<String, Object> map : list) {
            //map.put("ID", map.get("ROWNUM").toString());
            map.put("FISCAL", args.get("FISCAL").toString());
            map.put("FIS_PERD", args.get("FIS_PERD").toString());
            map.put("CO_CODE", args.get("CO_CODE").toString());
            map.put("SETID", 1);
            map.put("MONEY", map.get("MONEY"));
            //map.put("STAD_AMT",map.get("STAD_AMT"));
            stringBuffer.append(String.format("INTO C_REPORT_GNKM (FISCAL,FIS_PERD,CO_CODE,B_ACC_CODE,B_ACC_NAME,STAD_AMT,XZ_STAD_AMT,IS_LOWEST,SETID) VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s') ",
                    map.get("FISCAL").toString(),
                    map.get("FIS_PERD").toString(),
                    map.get("CO_CODE").toString(),
                    map.get("B_ACC_CODE").toString(),
                    map.get("B_ACC_NAME").toString(),
                    map.get("STAD_AMT") == null ? "" : map.get("STAD_AMT").toString(),
                    map.get("MONEY") == null ? "" : map.get("MONEY").toString(),
                    map.get("IS_LOWEST") ,
                    1));
            if (map.get("B_ACC_CODE").toString().length() == 3 || map.get("B_ACC_CODE").toString().length() == 5) {
                map.put("ISDISABLED", "true");
            } else {
                map.put("ISDISABLED", "false");
            }
            result.add(map);
            if (i % DEFAULT_INSERT_NUMBER == 0) {
                stringBuffer.append(" SELECT 1 FROM DUAL");
                globalDao.insertObject(stringBuffer.toString());
                stringBuffer = new StringBuffer(sql);
                i = 1;
            } else {
                i++;
            }
        }
        if (i > 1) {
            stringBuffer.append(" SELECT 1 FROM DUAL");
            globalDao.insertObject(stringBuffer.toString());
        }
        return result;
    }

    @Override
    public void update(List<Map<String,Object>> list, final Map<String, Object> args) {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        String sql = "BEGIN ";
        Integer i = 1;
        StringBuffer stringBuffer = new StringBuffer(sql);
        for (final Map map : list) {
            stringBuffer.append(new SQL() {{
                UPDATE("C_REPORT_GNKM");
                SET(String.format("B_ACC_CODE='%s'", map.get("B_ACC_CODE").toString()));
                if (args.get("STATUS").equals("1") && map.get("MONEY") != null)         //乡镇编辑    STATUS为1
                    SET(String.format("XZ_STAD_AMT='%s'", map.get("MONEY").toString()));
                if (args.get("STATUS").equals("3") && map.get("QCZ_STAD_AMT") != null)       //区财政编辑   status为3  QCZ_STAD_AMT
                    SET(String.format("QCZ_STAD_AMT='%s'", map.get("QCZ_STAD_AMT").toString()));
                else if (args.get("STATUS").equals("3") && map.get("MONEY") != null)    //上报      status为3
                    SET(String.format("QCZ_STAD_AMT='%s'", map.get("MONEY").toString()));
                WHERE(String.format("FISCAL='%s' AND FIS_PERD='%s' AND CO_CODE='%s' AND B_ACC_CODE='%s'",
                        map.get("FISCAL").toString(),
                        map.get("FIS_PERD").toString(),
                        map.get("CO_CODE").toString(),
                        map.get("B_ACC_CODE").toString()));
            }}.toString() + ";");
            if (i % DEFAULT_INSERT_NUMBER == 0) {
                stringBuffer.append("END;");
                globalDao.updateObject(stringBuffer.toString());
                stringBuffer = new StringBuffer(sql);
                i = 1;
            } else {
                i++;
            }
        }
        if (i > 1) {
            stringBuffer.append("END;");
            globalDao.updateObject(stringBuffer.toString());
        }
    }

    @Override
    public void updateSETID(Integer setId, Map<String, Object> args) {
        globalDao.updateObject(String.format("UPDATE C_REPORT_GNKM SET SETID='%s' WHERE FISCAL='%s' AND FIS_PERD='%s' AND CO_CODE='%s' ", setId,
                args.get("FISCAL").toString(),
                args.get("FIS_PERD").toString(),
                args.get("CO_CODE").toString()));
    }

    @Override
    public void clearAMT(String column, String setid, Map<String, Object> args) {
        globalDao.updateObject(String.format("UPDATE C_REPORT_GNKM SET %s='',SETID='%s' WHERE FISCAL='%s' AND FIS_PERD='%s' AND CO_CODE='%s'",
                column,
                setid,
                args.get("FISCAL").toString(),
                args.get("FIS_PERD").toString(),
                args.get("CO_CODE").toString()));
    }


    @Override                                                      
    public List<Map<String, Object>> getAllMoney(Map<String, Object> args) {
        List<Map<String, Object>> list;
        String sql = String.format(
                "Select * from (\n"+
                " Select    B_ACC_CODE,'合计数' as B_ACC_NAME,0 ID,Sum(ALLMONEY) ALLMONEY,\n" +
                        "     Sum(money1) money1,Sum(money2) money2,Sum(money3) money3,Sum(money4) money4,Sum(money5) money5,Sum(money6) money6,Sum(money7) money7,Sum(money8) money8,\n" +
                        "     Sum(money9) money9,Sum(money10) money10,Sum(money11) money11,Sum(money12) money12,Sum(money13) money13,Sum(money14) money14,Sum(money15) money15,Sum(money16) money16\n" +
                        " From (\n" +
                        "      Select ''B_ACC_CODE, ''B_ACC_NAME,\n" +
                        "      decode(Sum( decode(QCZ_STAD_AMT,Null,0, QCZ_STAD_AMT)),0,'0.00',Sum( decode(QCZ_STAD_AMT,Null,0, QCZ_STAD_AMT)) ) As ALLMONEY,\n" +
                        "      decode(decode(CO_CODE,605002,SUM(QCZ_STAD_AMT)),null,'0.00',decode(CO_CODE,605002,SUM(QCZ_STAD_AMT))) as money1,\n" +
                        "      decode(decode(CO_CODE,606002,SUM(QCZ_STAD_AMT)),null,'0.00',decode(CO_CODE, 606002,SUM(QCZ_STAD_AMT)))As money2,\n" +
                        "      decode(decode(CO_CODE,607002,SUM(QCZ_STAD_AMT)),null,'0.00',decode(CO_CODE, 607002,SUM(QCZ_STAD_AMT)))As money3,\n" +
                        "      decode(decode(CO_CODE,608002,SUM(QCZ_STAD_AMT)),null,'0.00',decode(CO_CODE, 608002,SUM(QCZ_STAD_AMT)))As money4,\n" +
                        "      decode(decode(CO_CODE,609002,SUM(QCZ_STAD_AMT)),null,'0.00',decode(CO_CODE, 609002,SUM(QCZ_STAD_AMT)))As money5,\n" +
                        "      decode(decode(CO_CODE,611002,SUM(QCZ_STAD_AMT)),null,'0.00',decode(CO_CODE, 611002,SUM(QCZ_STAD_AMT)))As money6,\n" +
                        "      decode(decode(CO_CODE,612002,SUM(QCZ_STAD_AMT)),null,'0.00',decode(CO_CODE, 612002,SUM(QCZ_STAD_AMT)))as money7,\n" +
                        "      decode(decode(CO_CODE,610002,SUM(QCZ_STAD_AMT)),null,'0.00',decode(CO_CODE, 610002,SUM(QCZ_STAD_AMT)))As money8,\n" +
                        "      decode(decode(CO_CODE,613002,SUM(QCZ_STAD_AMT)),null,'0.00',decode(CO_CODE, 613002,SUM(QCZ_STAD_AMT)))as money9,\n" +
                        "      decode(decode(CO_CODE,614002,SUM(QCZ_STAD_AMT)),null,'0.00',decode(CO_CODE, 614002,SUM(QCZ_STAD_AMT)))as money10,\n" +
                        "      decode(decode(CO_CODE,615002,SUM(QCZ_STAD_AMT)),null,'0.00',decode(CO_CODE, 615002,SUM(QCZ_STAD_AMT)))as money11,\n" +
                        "      decode(decode(CO_CODE,616002,SUM(QCZ_STAD_AMT)),null,'0.00',decode(CO_CODE, 616002,SUM(QCZ_STAD_AMT)))as money12,\n" +
                        "      decode(decode(CO_CODE,601002,SUM(QCZ_STAD_AMT)),null,'0.00',decode(CO_CODE, 601002,SUM(QCZ_STAD_AMT)))as money13,\n" +
                        "      decode(decode(CO_CODE,603002,SUM(QCZ_STAD_AMT)),null,'0.00',decode(CO_CODE, 603002,SUM(QCZ_STAD_AMT)))as money14,\n" +
                        "      decode(decode(CO_CODE,602002,SUM(QCZ_STAD_AMT)),null,'0.00',decode(CO_CODE, 602002,SUM(QCZ_STAD_AMT)))as money15,\n" +
                        "      decode(decode(CO_CODE,604002,SUM(QCZ_STAD_AMT)),null,'0.00',decode(CO_CODE,604002,SUM(QCZ_STAD_AMT)))as money16\n" +
                        "      From c_report_gnkm Where  FISCAL='%s' And '%s'=FIS_PERD And length(B_ACC_CODE)=3 Group By CO_CODE\n" +
                        " )\n" +
                        " Group By B_ACC_CODE,B_ACC_NAME\n" +
                        "\n" +
                        " Union All\n" +
                        "\n" +
                        "  select B_ACC_CODE, B_ACC_NAME,row_number()over(order by B_ACC_CODE) ID,\n" +
                        "  Sum(decode(QCZ_STAD_AMT,Null,0, QCZ_STAD_AMT)) As ALLMONEY,\n" +
                        "  sum(decode(CO_CODE, 605002 ,decode(QCZ_STAD_AMT,Null,0, QCZ_STAD_AMT),0))As money1,\n" +
                        "  sum(decode(CO_CODE, 606002 ,decode(QCZ_STAD_AMT,Null,0, QCZ_STAD_AMT),0))As money2,\n" +
                        "  sum(decode(CO_CODE, 607002 ,decode(QCZ_STAD_AMT,Null,0, QCZ_STAD_AMT),0))As money3,\n" +
                        "  sum(decode(CO_CODE, 608002 ,decode(QCZ_STAD_AMT,Null,0, QCZ_STAD_AMT),0))As money4,\n" +
                        "  sum(decode(CO_CODE, 609002 ,decode(QCZ_STAD_AMT,Null,0, QCZ_STAD_AMT),0))As money5,\n" +
                        "  sum(decode(CO_CODE, 611002 ,decode(QCZ_STAD_AMT,Null,0, QCZ_STAD_AMT),0))As money6,\n" +
                        "  sum(decode(CO_CODE, 612002 ,decode(QCZ_STAD_AMT,Null,0, QCZ_STAD_AMT),0))As money7,\n" +
                        "  sum(decode(CO_CODE, 610002 ,decode(QCZ_STAD_AMT,Null,0, QCZ_STAD_AMT),0))As money8,\n" +
                        "  sum(decode(CO_CODE, 613002 ,decode(QCZ_STAD_AMT,Null,0, QCZ_STAD_AMT),0))As money9,\n" +
                        "  sum(decode(CO_CODE, 614002 ,decode(QCZ_STAD_AMT,Null,0, QCZ_STAD_AMT),0))As money10,\n" +
                        "  sum(decode(CO_CODE, 615002 ,decode(QCZ_STAD_AMT,Null,0, QCZ_STAD_AMT),0))As money11,\n" +
                        "  sum(decode(CO_CODE, 616002 ,decode(QCZ_STAD_AMT,Null,0, QCZ_STAD_AMT),0))As money12,\n" +
                        "  sum(decode(CO_CODE, 601002 ,decode(QCZ_STAD_AMT,Null,0, QCZ_STAD_AMT),0))As money13,\n" +
                        "  sum(decode(CO_CODE, 603002 ,decode(QCZ_STAD_AMT,Null,0, QCZ_STAD_AMT),0))As money14,\n" +
                        "  sum(decode(CO_CODE, 602002 ,decode(QCZ_STAD_AMT,Null,0, QCZ_STAD_AMT),0))As money15,\n" +
                        "  sum(decode(CO_CODE, 60400 ,decode(QCZ_STAD_AMT,Null,0, QCZ_STAD_AMT),0))As money16\n" +
                        "  From  c_report_gnkm Where  FISCAL='%s' And '%s'=FIS_PERD\n" +
                        "   Group By B_ACC_CODE, B_ACC_NAME\n" +
                        " ) ",
                args.get("FISCAL").toString(),
                args.get("FIS_PERD").toString(),
                args.get("FISCAL").toString(),
                args.get("FIS_PERD").toString());
        list = globalDao.selectListMap(sql);
        return list;
    }
}
