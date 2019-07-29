package com.heitian.ssm.utils.excel;
/**
 * ClassName:ImportExcelUtil
 * Package:com.poiexcel.util
 * Description:
 * author:@Fay
 * Date:2019/2/21 0021上午 10:02
 */

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

//import org.apache.poi.xssf.streaming.SXSSFWorkbook;


public class ImportExcelUtil {

    private final static String excel2003L = ".xls";    //2003- 版本的excel
    private final static String excel2007U = ".xlsx";   //2007+ 版本的excel

    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     *
     * @param in,fileName
     * @return
     * @throws IOException
     */
    public List<List<Object>> getBankListByExcel(InputStream in, String fileName, String Path) throws Exception {
        List<List<Object>> list = null;

        //创建Excel工作薄

//        if(null == work){
//            throw new Exception("创建Excel工作薄为空！");
//        }

        list = new ArrayList<List<Object>>();

        String fileType = fileName.substring(fileName.lastIndexOf("."));

        if (excel2003L.equals(fileType)) {
            Sheet sheet = null;
            Row row = null;
            Cell cell = null;

            Workbook wb = new HSSFWorkbook(in);  //2003-
            System.out.print("+\n+getNumberOfSheets" + wb.getNumberOfSheets());
            System.out.print("+\n+getNumberOfNames" + wb.getNumberOfNames());
            //遍历Excel中所有的sheet
            sheet = wb.getSheetAt(0);

            //遍历当前sheet中的所有行
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }
                //遍历所有的列
                List<Object> li = new ArrayList<Object>();
                for (int y = sheet.getRow(0).getFirstCellNum(); y < sheet.getRow(0).getLastCellNum(); y++) {
                    cell = row.getCell(y);

                    if (cell==null){
                        li.add("");
                    }else {
                        Object obj = this.getCellValue(cell);
                        li.add(obj);
                    }
                }
                list.add(li);
            }
            wb.close();

        } else if (excel2007U.equals(fileType)) {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(in);
            Row row = null;
            Cell cell = null;
            File excelFile = new File(Path);
            FileInputStream inw = new FileInputStream(excelFile);
            Workbook Workbook = getWorkbok(inw, excelFile);
            Sheet sheet = Workbook.getSheetAt(0);   // 遍历第一个Sheet

            // 为跳过第一行目录设置count
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                System.out.println(j);
                row = sheet.getRow(j);
                try {
                    if (row == null || row.getFirstCellNum() == j) {
                        continue;
                    }

                    //获取总列数(空格的不计算)
                    int columnTotalNum = row.getPhysicalNumberOfCells();
                    System.out.println("总列数：" + columnTotalNum);

                    System.out.println("最大列数：" + row.getLastCellNum());

                    List<Object> li = new ArrayList<Object>();
                    for (int i = sheet.getRow(0).getFirstCellNum(); i <sheet.getRow(0).getLastCellNum(); i++) {
                        cell = row.getCell(i);
                        if (cell==null){
                            li.add("");
                        }else {
                            Object obj = this.getCellValue(cell);
                            li.add(obj);
                        }
                    }
                    list.add(li);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Workbook.close();
        } else {
            throw new Exception("解析的文件格式有误！");
        }

        return list;
    }

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public static Workbook getWorkbok(InputStream in, File file) throws IOException {
        Workbook wb = null;
        if (file.getName().endsWith(EXCEL_XLS)) {  //Excel 2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) {  // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }


    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
//    public  Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{
//        Workbook wb = null;
//        String fileType = fileName.substring(fileName.lastIndexOf("."));
//
//        if(excel2003L.equals(fileType)){
//            System.out.print("1");
//            wb = new HSSFWorkbook(inStr);  //2003-
//            return wb;
//        }else if(excel2007U.equals(fileType)){
//            System.out.print("2");
//            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inStr);
//            SXSSFWorkbook wb2 = new SXSSFWorkbook(xssfWorkbook, 1000);
//            return     wb2;
//        }else{
//            throw new Exception("解析的文件格式有误！");
//        }
////        return wb;
//    }

    /**
     * 描述：对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
    public Object getCellValue(Cell cell) {
        Object value = null;
        DecimalFormat df = new DecimalFormat("0");  //格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字

        switch (cell.getCellType()) {
            case STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case BLANK:
                value = "";
                break;
            case FORMULA:
                try {
                    value = String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    value = "";
                }
                break;
            default:
                break;
        }
        return value;
    }
}