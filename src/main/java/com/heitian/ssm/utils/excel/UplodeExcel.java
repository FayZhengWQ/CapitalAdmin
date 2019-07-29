package com.heitian.ssm.utils.excel;

import com.heitian.ssm.model.MapResult;
import com.heitian.ssm.utils.config.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-01-13 13:57
 **/
public class UplodeExcel {

    public static List<Map> testImport(String url) throws FileImportException, FileNotFoundException, URISyntaxException {
        List<Map> maps=new ArrayList<>();



        ConfigParser configParser = ConfigurationParserFactory.getConfigParser(Configuration.ParserType.XML);
        File importFile = new File(url);
//        File importFile = new File("C:\\Users\\Administrator\\Desktop\\柯桥区基财一体化平台项目支出导入模板.xlsx.xlsx");
        Configuration configuration = null;
        try {
            configuration = configParser.getConfig(UplodeExcel.class.getResourceAsStream("/import/config.xml"));
            MapResult mapResult = (MapResult) FileImportExecutor.importFile(configuration, importFile, importFile.getName());
            maps = mapResult.getResult();
//            for (Map<String, Object> map : maps) {
////                Integer index = (Integer) map.get("index");
////                Float f1 = (Float) map.get("float");
//                String CXZPROGNAME = (String) map.get("CXZPROGNAME");
//                String FILENO = (String) map.get("FILENO");
//                String CENTERPRISE = (String) map.get("CENTERPRISE");
//                String SZTYPE = (String) map.get("SZTYPE");
//                String CFUNCTION = (String) map.get("CFUNCTION");
//
//
////                Date date = (Date) map.get("date");
////                BigDecimal bigDecimal = (BigDecimal) map.get("bigdecimal");
////                System.out.println(" f1 : " + f1 + " string : " + string
////                        + " date : " + date.toString() + " bigdecimal " + bigDecimal);
//            }
        } catch (FileImportException e) {
            System.out.println(e);
        }
        return maps;
    }
}
