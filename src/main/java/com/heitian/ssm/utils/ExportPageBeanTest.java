package com.heitian.ssm.utils;

import com.heitian.ssm.model.project.CapitalModel;
import com.heitian.ssm.model.CellModel;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 符柱成 on 2017/8/25.
 */
public class ExportPageBeanTest {

    /**
     * @param xlsName      表名
     * @param cellNameList 表头名称
     * @param cellRow      表头所在行码
     * @param rowMapper    单元格名称
     * @author: Wendy
     * @description: 多表头导出
     * @date: 16:24 2017/11/14
     */
    public static File createCSVUtil(String xlsName, List<CellModel> cellNameList, Integer cellRow, List<CapitalModel> list , LinkedHashMap rowMapper, String outPutPath) throws Exception {
        File XlsUtil = null; //表格创建&命名
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(xlsName);

        sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
        sheet.addMergedRegion(new CellRangeAddress(0,1,1,1));
        sheet.addMergedRegion(new CellRangeAddress(0,1,2,2));
        sheet.addMergedRegion(new CellRangeAddress(0,1,3,3));
        sheet.addMergedRegion(new CellRangeAddress(0,1,4,4));
        sheet.addMergedRegion(new CellRangeAddress(0,1,5,5));
        sheet.addMergedRegion(new CellRangeAddress(0,0,6,9));
        sheet.addMergedRegion(new CellRangeAddress(0,0,10,11));
        sheet.addMergedRegion(new CellRangeAddress(0,0,12,15));

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFRow row = sheet.createRow(cellRow);
        for (CellModel cellModel : cellNameList) {

            HSSFCell cell = row.createCell(cellModel.getStartColumn());
            cell.setCellValue(cellModel.getCellName());
            cell.setCellStyle(cellStyle);
        }

        cellRow++;
        HSSFRow row1 = sheet.createRow(cellRow);
        Iterator<Map.Entry> iterator = rowMapper.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            Integer key = Integer.valueOf(entry.getKey().toString());
            String value = entry.getValue().toString();
            HSSFCell cell = row1.createCell(key - 1);
            cell.setCellValue(value);
            cell.setCellStyle(cellStyle);
        }


        for (int i = 0; i < list.size(); i++) {
            cellRow++;
            HSSFRow row2 = sheet.createRow(cellRow);  //创建一行

            //文件名称
            HSSFCell cell1 = row2.createCell(0);
            cell1.setCellValue(list.get(0).getCXZPROGNAME());

            //部门
            HSSFCell cell2 = row2.createCell(1);
            cell2.setCellValue("");

            //文号
            HSSFCell cell3 = row2.createCell(2);
            cell3.setCellValue(list.get(i).getFILENO());

            //类别
            HSSFCell cell4 =row2.createCell(3) ;
            cell4.setCellValue(list.get(i).getCPROGRAMTYPENAME());

            //补助内容
            HSSFCell cell5=row2.createCell(4);
            cell5.setCellValue("");

            //金额
            HSSFCell cell6=row2.createCell(5);
            cell6.setCellValue(list.get(i).getIMONEY());

            //发文日期
            HSSFCell cell7=row2.createCell(6);
            cell7.setCellValue(list.get(i).getDPRODATE());

            //收文日期
            HSSFCell cell8=row2.createCell(7);
            cell8.setCellValue(list.get(i).getGDATE());

            //资金拨入日期
            HSSFCell cell9=row2.createCell(8);
            cell9.setCellValue("");

            //资金拨入日期
            HSSFCell cell10=row2.createCell(9);
            cell10.setCellValue("");

            //公示日期
            HSSFCell cell11=row2.createCell(10);
            cell11.setCellValue(list.get(i).getSTART_DATE());

            //公示类型
            HSSFCell cell12=row2.createCell(11);
            cell12.setCellValue(list.get(i).getCANNOUNCETYPE());

            //巡查日期
            HSSFCell cell13=row2.createCell(12);
            cell13.setCellValue(list.get(i).getDCHECKDATE());

            //巡查人员
            HSSFCell cell14=row2.createCell(13);
            cell14.setCellValue(list.get(i).getDCHECKRY());

            //巡查评价
            HSSFCell cell15=row2.createCell(14);
            cell15.setCellValue(list.get(i).getDCHECKPJ());


            //巡查签证
            HSSFCell cell16=row2.createCell(15);
            cell16.setCellValue("");

        }

        FileOutputStream output = new FileOutputStream(outPutPath + xlsName + ".xlsx");
        workbook.write(output);
        output.flush();
        XlsUtil = new File(outPutPath + xlsName + ".csv");
        return XlsUtil;
    }

}