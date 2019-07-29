package com.heitian.ssm.controller.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.heitian.ssm.Source.DynamicDataSourceHolder;
import com.heitian.ssm.service.report.ApproveService;
import com.heitian.ssm.utils.excel.Excel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: 基材一体化
 * @description: 功能科目 基本支出 经济科目三张报表
 * @author: liangsizhuuo@163.com
 * @create: 2019-04-27 07:47
 **/
@Controller
@CrossOrigin
public class ApproveController {
    @Autowired
    private ApproveService approveService;


    /**
     * 生成报表：从用友取数
     * 使用人:乡镇
     * 逻辑:
     * <判断报表是否已生成>
     * 1.根据 Setid=null||setid=1 可以重新生成报表
     * 当Setid=null 直接生成报表   存入数据库  并返回给用户
     * 当Setid=1    提醒用户已生成报表 是否重新生成 如果同意，则 删除数据<列删除> 重新生成报表  存入数据库<update>< 并返回给用户
     * 2.根据Setid=3||setid=5  提醒用户已生成报表/该报表已上报
     * 输出格式:Map
     * TABLEID:1 经济科目表 TABLEID:2 基本支出报表 TABLEID:3 功能科目表
     */
    @RequestMapping(value = "/center/getlist", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(HttpServletRequest request) {
        Map args = new HashMap();
        String STATE = request.getParameter("STATE");
        String FISCAL = request.getParameter("FISCAL");
        String FIS_PERD = request.getParameter("FIS_PERD");
        String CO_CODE = request.getParameter("CO_CODE");
        String TABLEID = request.getParameter("TABLEID");
        args.put("FISCAL", FISCAL);
        args.put("FIS_PERD", FIS_PERD);
        args.put("CO_CODE", CO_CODE);

        Map<String, Object> result = new HashMap<>();
        if (TABLEID.equals("1")) {
            args.put("TABLENAME", "C_REPORT_JJKM");
            String setid = approveService.getSetId("dataSource1", args);
            //第一次生成报表

            if (setid == null) {
                //直接生成报表
                /*result = approveService.doJJKM(STATE,args);*/
                //连接用友数据库，调用存储过程
                DynamicDataSourceHolder.setDataSource("dataSource18");
                Map map = new HashMap();
                map.put("FIS_YEAR", FISCAL);
                map.put("FIS_MONTH", FIS_PERD);
                map.put("CO_CODE", CO_CODE);
                map.put("cur", new ArrayList<>());
                approveService.doJJKM(map);
                List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("cur");
                //连接本地数据库将数据插入本地数据库
                DynamicDataSourceHolder.setDataSource("dataSource1");
                int count = 0;
                //第一次直接生成报表，返回数据给前端
                if (STATE.equals("1")) {
                    int i = 1;
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    for (Map<String, Object> map1 : mapList) {
                        map1.put("FISCAL", FISCAL);
                        map1.put("FIS_PERD", FIS_PERD);
                        map1.put("CO_CODE", CO_CODE);
                        map1.put("SETID", 1);
                        dataList.add(map1);
                        if (i % 100 == 0) {
                            count = approveService.addJJKM(dataList);
                            i = 1;
                            dataList.clear();
                        } else {
                            i++;
                        }
                    }
                    if (i > 1) {
                        count = approveService.addJJKM(dataList);
                    }
                }

                if (count > 0) {
                    result.put("msg", "报表生成成功");
                    result.put("data", mapList);
                    result.put("code", true);
                } else {
                    result.put("msg", "报表生成失败");
                    result.put("code", false);
                }
            } else if (setid.equals("1")) {
                //提醒用户已生成报表 是否重新生成
                result.put("code", false);
                result.put("msg", "已生成报表,是否重新生成");
            } else if (setid.equals("3")) {
                result.put("code", false);
                result.put("msg", "已生成报表");
            } else if (setid.equals("5")) {
                result.put("code", false);
                result.put("msg", "该报表已上报");
            }

            if (STATE.equals("2")) {
                //连接用友数据库，调用存储过程
                DynamicDataSourceHolder.setDataSource("dataSource18");
                Map map = new HashMap();
                map.put("FIS_YEAR", FISCAL);
                map.put("FIS_MONTH", FIS_PERD);
                map.put("CO_CODE", CO_CODE);
                map.put("cur", new ArrayList<>());
                approveService.doJJKM(map);
                List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("cur");
                DynamicDataSourceHolder.setDataSource("dataSource1");
                int count = 0;
                count = approveService.updateColumn(args);
                if (count > 0) {
                    for (Map<String, Object> map1 : mapList) {
                        map1.put("FISCAL", FISCAL);
                        map1.put("FIS_PERD", FIS_PERD);
                        map1.put("CO_CODE", CO_CODE);
                        map1.put("SETID", 1);
                        count = approveService.updateAllMoney(map1);

                    }
                    if (count > 0) {
                        result.put("code", true);
                        result.put("msg", "生成报表成功");
                    } else {
                        result.put("code", false);
                        result.put("msg", "生成报表失败");
                    }
                }
            }
        } else if (TABLEID.equals("2")) {
            args.put("TABLENAME", "C_REPORT_JBZC");
            String setid = approveService.getSetId("dataSource1", args);
            //连接用友数据库，调用存储过程
            if (setid == null) {
                DynamicDataSourceHolder.setDataSource("dataSource18");
                Map map = new HashMap();
                map.put("FIS_YEAR", FISCAL);
                map.put("FIS_MONTH", FIS_PERD);
                map.put("CO_CODE", CO_CODE);
                map.put("cur", new ArrayList<>());
                approveService.doJBZC(map);
                List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("cur");
                //连接本地数据库将数据插入本地数据库
                DynamicDataSourceHolder.setDataSource("dataSource1");
                int count = 0;
                //第一次直接生成报表，返回数据给前端
                if (STATE.equals("1")) {
                    int i = 1;
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    for (Map<String, Object> map1 : mapList) {
                        map1.put("FISCAL", FISCAL);
                        map1.put("FIS_PERD", FIS_PERD);
                        map1.put("CO_CODE", CO_CODE);
                        map1.put("SETID", 1);
                       /* dataList.add(map1);
                        if (i % 100 == 0) {
                            count = approveService.addJBZC(dataList);
                            i = 1;
                            dataList.clear();
                        } else {
                            i++;
                        }*/
                        count = approveService.addJBZC(map1);
                    }
                    /*if (i > 1) {
                        count = approveService.addJBZC(dataList);
                    }*/
                }
                if (count > 0) {
                    result.put("msg", "报表生成成功");
                    result.put("data", mapList);
                    result.put("code", true);
                } else {
                    result.put("msg", "报表生成失败");
                    result.put("code", false);
                }
            } else if (setid.equals("1")) {
                //提醒用户已生成报表 是否重新生成
                result.put("code", false);
                result.put("msg", "已生成报表,是否重新生成");
            } else if (setid.equals("3")) {
                result.put("code", false);
                result.put("msg", "已生成报表");
            } else if (setid.equals("5")) {
                result.put("code", false);
                result.put("msg", "该报表已上报");
            }

            if (STATE.equals("2")) {
                //连接用友数据库，调用存储过程
                DynamicDataSourceHolder.setDataSource("dataSource18");
                Map map = new HashMap();
                map.put("FIS_YEAR", FISCAL);
                map.put("FIS_MONTH", FIS_PERD);
                map.put("CO_CODE", CO_CODE);
                map.put("cur", new ArrayList<>());
                approveService.doJBZC(map);
                List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("cur");
                DynamicDataSourceHolder.setDataSource("dataSource1");
                int count = 0;
                count = approveService.updateColumn(args);

                if (count > 0) {
                    for (Map<String, Object> map1 : mapList) {
                        map1.put("FISCAL", FISCAL);
                        map1.put("FIS_PERD", FIS_PERD);
                        map1.put("CO_CODE", CO_CODE);
                        map1.put("SETID", 1);

                        count = approveService.updatejbzcAllMoney(map1);

                    }
                    if (count > 0) {
                        result.put("code", true);
                        result.put("msg", "生成报表成功");
                    } else {
                        result.put("code", false);
                        result.put("msg", "生成报表失败");
                    }
                }
            }
        } else if (TABLEID.equals("3")) {
            args.put("TABLENAME", "C_REPORT_GNKM");
            String setid = approveService.getSetId("dataSource1", args);
            //第一次生成报表

            if (setid == null) {
                //直接生成报表
                DynamicDataSourceHolder.setDataSource("dataSource18");
                Map map = new HashMap();
                map.put("FIS_YEAR", args.get("FISCAL"));
                map.put("FIS_MONTH", args.get("FIS_PERD"));
                map.put("CO_CODE", args.get("CO_CODE"));
                map.put("cur", new ArrayList<>());
                approveService.doGNKM(map);
                List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("cur");
                //连接本地数据库将数据插入本地数据库
                DynamicDataSourceHolder.setDataSource("dataSource1");
                int count = 0;
                //第一次直接生成报表，返回数据给前端
                if (STATE.equals("1")) {
                    int i = 1;
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    for (Map<String, Object> map1 : mapList) {
                        map1.put("FISCAL", FISCAL);
                        map1.put("FIS_PERD", FIS_PERD);
                        map1.put("CO_CODE", CO_CODE);
                        map1.put("SETID", 1);
                        dataList.add(map1);
                        /*if (i % 100 == 0) {
                            count = approveService.addGNKM(dataList);
                            i = 1;
                            dataList.clear();
                        } else {
                            i++;
                        }*/
                        count = approveService.addGNKM(map1);
                    }
                   /* if (i > 1) {
                        count = approveService.addGNKM(dataList);
                    }*/
                }
                if (count > 0) {
                    result.put("msg", "报表生成成功");
                    result.put("data", mapList);
                    result.put("code", true);
                } else {
                    result.put("msg", "报表生成失败");
                    result.put("code", false);
                }
            } else if (setid.equals("1")) {
                //提醒用户已生成报表 是否重新生成
                result.put("code", false);
                result.put("msg", "已生成报表,是否重新生成");
            } else if (setid.equals("3")) {
                result.put("code", false);
                result.put("msg", "已生成报表");
            } else if (setid.equals("5")) {
                result.put("code", false);
                result.put("msg", "该报表已上报");
            }

            if (STATE.equals("2")) {
                //连接用友数据库，调用存储过程
                DynamicDataSourceHolder.setDataSource("dataSource18");
                Map map = new HashMap();
                map.put("FIS_YEAR", FISCAL);
                map.put("FIS_MONTH", FIS_PERD);
                map.put("CO_CODE", CO_CODE);
                map.put("cur", new ArrayList<>());
                approveService.doGNKM(map);
                List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("cur");
                DynamicDataSourceHolder.setDataSource("dataSource1");
                int count = 0;
                count = approveService.updateColumn(args);

                if (count > 0) {
                    for (Map<String, Object> map1 : mapList) {
                        map1.put("FISCAL", FISCAL);
                        map1.put("FIS_PERD", FIS_PERD);
                        map1.put("CO_CODE", CO_CODE);
                        map1.put("SETID", 1);

                        count = approveService.updateGNKMAllMoney(map1);

                    }
                    if (count > 0) {
                        result.put("code", true);
                        result.put("msg", "生成报表成功");
                    } else {
                        result.put("code", false);
                        result.put("msg", "生成报表失败");
                    }
                }
            }

        }

        return result;
    }


    /**
     * 查看报表：从本地取数
     * 使用人:乡镇/区财政  cpostguid01/04
     * 逻辑:
     * <判断报表是否生成>
     * 1.根据Setid=1
     * 乡镇
     * 输出格式:Map
     * TABLEID:1 经济科目表 TABLEID:2 基本支出报表 TABLEID:3 功能科目表
     */
    @RequestMapping(value = "/center/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getData(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        String STATUS = request.getParameter("STATUS");//根据STATUS：1 查看 STATUS：2下载
        String TABLEID = request.getParameter("TABLEID");
        String cpostguid = request.getParameter("cpostguid");
        Map<String, Object> args = new HashMap<>();
        args.put("cpostguid", request.getParameter("cpostguid"));
        args.put("FISCAL", request.getParameter("FISCAL"));
        args.put("FIS_PERD", request.getParameter("FIS_PERD"));
        args.put("CO_CODE", request.getParameter("CO_CODE"));
        args.put("STATUS", STATUS);

        if (TABLEID.equals("1")) {
            result = approveService.getJjkmData(args);
        } else if (TABLEID.equals("2")) {
            result = approveService.getJbzcData(args);
        } else if (TABLEID.equals("3")) {
            result = approveService.getGnkmData(args);
        }
        if (STATUS.equals("1")) {
           return result;
        } else if (STATUS.equals("2")) {
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) result.get("data");
            int size = dataList==null?0:dataList.size();
            if (size==0) {
                result.put("msg", "未生成报表");
                result.put("code", false);
            } else {
                String[] head1 = null;
                String sheetName = null;
                String fileno = null;
                String[] head0 = null;
                if (cpostguid.equals("01")) {

                    if (TABLEID.equals("1")) {
                        head1 = new String[]{"OUTLAY_CODE", "OUTLAY_NAME", "ID", "MONEY"};
                        sheetName = request.getParameter("SHORTNAME") + request.getParameter("FISCAL") + "年" + request.getParameter("FIS_PERD") + "月政府经济科目支出月报";
                        fileno = request.getParameter("SHORTNAME") + request.getParameter("FISCAL") + "年" + request.getParameter("FIS_PERD") + "月政府经济科目支出月报";
                    } else if (TABLEID.equals("2")) {
                        head1 = new String[]{"GOV_OUTLAY_CODE", "GOV_OUTLAY_NAME", "ID", "MONEY"};
                        sheetName = request.getParameter("SHORTNAME") + request.getParameter("FISCAL") + "年" + request.getParameter("FIS_PERD") + "月一般公共预算基本支出科目月报";
                        fileno = request.getParameter("SHORTNAME") + request.getParameter("FISCAL") + "年" + request.getParameter("FIS_PERD") + "月一般公共预算基本支出科目月报";
                    } else if (TABLEID.equals("3")) {
                        head1 = new String[]{"B_ACC_CODE", "B_ACC_NAME", "ID", "MONEY"};
                        sheetName = request.getParameter("SHORTNAME") + request.getParameter("FISCAL") + "年" + request.getParameter("FIS_PERD") + "月一般公共预算支出功能科目月报";
                        fileno = request.getParameter("SHORTNAME") + request.getParameter("FISCAL") + "年" + request.getParameter("FIS_PERD") + "月一般公共预算支出功能科目月报";
                    }
                    String[] XZName = {"金额"};

                    Excel.exportOne(request, response, dataList, sheetName, fileno, head1, XZName);

                } else if (cpostguid.equals("04")) {
                    if (TABLEID.equals("1")) {
                        head1 = new String[]{"OUTLAY_CODE", "OUTLAY_NAME", "ALLMONEY", "MONEY1", "MONEY2", "MONEY3", "MONEY4", "MONEY5", "MONEY6",
                                "MONEY7", "MONEY8", "MONEY9", "MONEY10", "MONEY11", "MONEY12", "MONEY13", "MONEY14", "MONEY15", "MONEY16"};
                        sheetName =  request.getParameter("SHORTNAME") + request.getParameter("FISCAL") + "年" + request.getParameter("FIS_PERD") + "月政府经济科目支出月报";
                        fileno = request.getParameter("SHORTNAME") + request.getParameter("FISCAL") + "年" + request.getParameter("FIS_PERD") + "月政府经济科目支出月报";
                        head0 = new String[]{"编码", "支出科目", "合计数", "马鞍镇",
                                "齐贤镇", "安昌镇", "钱清镇", "扬汛桥", "夏履", "漓渚", "福全", "兰亭", "平水", "王坛", "稽东镇",
                                "柯桥街道", "柯岩街道", "华舍街道", "湖塘街道"};
                    } else if (TABLEID.equals("2")) {
                        head1 = new String[]{"GOV_OUTLAY_CODE", "GOV_OUTLAY_NAME", "ALLMONEY", "MONEY1", "MONEY2", "MONEY3", "MONEY4", "MONEY5", "MONEY6",
                                "MONEY7", "MONEY8", "MONEY9", "MONEY10", "MONEY11", "MONEY12", "MONEY13", "MONEY14", "MONEY15", "MONEY16"};
                        sheetName = request.getParameter("SHORTNAME")+request.getParameter("FISCAL")+"年"+request.getParameter("FIS_PERD")+"月一般公共预算基本支出科目月报";
                        fileno =  request.getParameter("SHORTNAME")+request.getParameter("FISCAL")+"年"+request.getParameter("FIS_PERD")+"月一般公共预算基本支出科目月报";
                         head0 = new String[]{"编码", "支出科目", "合计数", "马鞍镇",
                                "齐贤镇", "安昌镇", "钱清镇", "扬汛桥", "夏履", "漓渚", "福全", "兰亭", "平水", "王坛", "稽东镇",
                                "柯桥街道", "柯岩街道", "华舍街道", "湖塘街道"};
                    } else if (TABLEID.equals("3")) {
                        head1 = new String[]{"B_ACC_CODE", "B_ACC_NAME", "ALLMONEY", "MONEY1", "MONEY2", "MONEY3", "MONEY4", "MONEY5", "MONEY6",
                                "MONEY7", "MONEY8", "MONEY9", "MONEY10", "MONEY11", "MONEY12", "MONEY13", "MONEY14", "MONEY15", "MONEY16"};
                        sheetName =request.getParameter("SHORTNAME")+ request.getParameter("FISCAL")+"年"+request.getParameter("FIS_PERD")+"月一般公共预算支出功能科目月报";
                        fileno= request.getParameter("SHORTNAME")+request.getParameter("FISCAL")+"年"+request.getParameter("FIS_PERD")+"月一般公共预算支出功能科目月报";
                        head0 = new String[]{"编码", "支出科目", "合计数", "马鞍镇",
                                "齐贤镇", "安昌镇", "钱清镇", "扬汛桥", "夏履", "漓渚", "福全", "兰亭", "平水", "王坛", "稽东镇",
                                "柯桥街道", "柯岩街道", "华舍街道", "湖塘街道"};
                    }
                    Excel.exportJJFL(request, response, dataList, sheetName, fileno,head0, head1);
                }
            }
        }

        return result;
    }

    /**
     * 编辑：乡镇/区财政 cpostguid=01/04
     * 乡镇编辑setid=1 更新XZ_STAD_AMT
     * 区财政编辑 setid=3 更新QCZ_STAD_AMT
     * 输出格式:Map
     * TABLEID:1 经济科目表 TABLEID:2 基本支出报表 TABLEID:3 功能科目表
     */
    @RequestMapping(value = "/center/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> edit(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> args = new HashMap<>();
        String cpostguid = request.getParameter("cpostguid");
        args.put("TABLEID", request.getParameter("TABLEID"));
        List<Map<String, Object>> dataList = JSON.parseObject(request.getParameter("list"), new TypeReference<ArrayList<Map<String, Object>>>() {
        });
        if (cpostguid.equals("01")) {
            result = approveService.XZEdit(args, dataList);
        } else if (cpostguid.equals("04")) {
            result = approveService.QCZEdit(args, dataList);
        }
        return result;
    }

    /**
     * 上报： 乡镇
     * 将金额填入XZ_STAD_AMT,QCZ_STAD_AMT字段,修改状态成3
     * TABLEID:1 经济科目 TABLEID：2 基本支出 TABLEID:3 功能科目
     */
    @RequestMapping(value = "/center/xz/report", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> report(HttpServletRequest request) {
        Map<String, Object> result = null;
        String TABLEID = request.getParameter("TABLEID");
        List<Map<String, Object>> dataList = JSON.parseObject(request.getParameter("list"), new TypeReference<ArrayList<Map<String, Object>>>() {
        });
        result = approveService.report(TABLEID, dataList);
        return result;
    }


    /**
     * 确认：区财政
     * setid ==> 5
     * 保存QCZ_STAD_AMT
     * TABLEID:1 经济科目 TABLEID：2 基本支出 TABLEID:3 功能科目
     */
    @RequestMapping(value = "/center/qcz/confirm", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> confirm(HttpServletRequest request) {
        Map<String, Object> result = null;
        String TABLEID = request.getParameter("TABLEID");
        List<Map<String, Object>> dataList = JSON.parseObject(request.getParameter("list"), new TypeReference<ArrayList<Map<String, Object>>>() {
        });
        result = approveService.confirm(TABLEID, dataList);
        return result;
    }

    /**
     * 取消确认：区财政
     * QCZ_STAD_AMT 清空
     * setid ==> 3
     * TABLEID:1 经济科目 TABLEID：2 基本支出 TABLEID:3 功能科目
     */
    @RequestMapping(value = "/center/qcz/cancel",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> cancelConfirm(HttpServletRequest request) {
        Map<String, Object> result = null;
        String TABLEID = request.getParameter("TABLEID");
        List<Map<String, Object>> dataList = JSON.parseObject(request.getParameter("list"), new TypeReference<ArrayList<Map<String, Object>>>() {
        });
        result = approveService.cancelConfirm(TABLEID, dataList);
        return result;
    }

    /**
     * 退回（5-->1）：区财政
     * 清空QCZ_STAD_AMT字段值，状态5为1
     * TABLEID:1 经济科目 TABLEID：2 基本支出 TABLEID:3 功能科目
     */
    @RequestMapping(value = "/center/qcz/back" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> back(HttpServletRequest request) {
        Map<String, Object> result = null;
        String TABLEID = request.getParameter("TABLEID");
        List<Map<String, Object>> dataList = JSON.parseObject(request.getParameter("list"), new TypeReference<ArrayList<Map<String, Object>>>() {
        });
       /* List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("FISCAL",2019);
        map1.put("FIS_PERD",3);
        map1.put("CO_CODE",606001);
        map1.put("OUTLAY_CODE",501);
        map1.put("OUTLAY_NAME","机关工资福利支出");
        map1.put("QCZ_STAD_AMT","500");
        Map<String, Object> map2= new HashMap<>();
        map2.put("FISCAL",2019);
        map2.put("FIS_PERD",3);
        map2.put("CO_CODE",606001);
        map2.put("OUTLAY_CODE",50101);
        map2.put("OUTLAY_NAME","工资奖金津补贴");
        map2.put("QCZ_STAD_AMT","600");
        dataList.add(map1);
        dataList.add(map2);*/
        result = approveService.back(TABLEID, dataList);
        return result;
    }


}
