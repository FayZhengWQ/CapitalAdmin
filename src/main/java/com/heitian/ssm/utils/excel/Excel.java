package com.heitian.ssm.utils.excel;

import com.heitian.ssm.model.project.CapitalModel;
import com.heitian.ssm.model.project.Project;
import com.heitian.ssm.model.report.SpecialrModel;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:Excel
 * Package:com.excelbiaotou.www.util
 * Description:中文字体10；数字9
 * author:@Fay
 * Date:2019/3/21 0021上午 10:57
 */
public class Excel {

    /**
     * 导出监管台账
     *
     * @param request
     * @param response
     * @param dataList
     * @param sheetName
     */
    public static void exportCapital(HttpServletRequest request, HttpServletResponse response, List<CapitalModel> dataList,
                                     String sheetName, String fileno) {

        String[] head0 = new String[]{"项目名称", "部门", "项目情况", "项目情况", "项目情况", "项目情况", "补助内容", "金额",
                "信息通达", "信息通达", "信息通达", "信息通达", "公告公示", "公告公示",
                "抽查巡查", "抽查巡查", "抽查巡查", "抽查巡查"};
        String[] head1 = new String[]{"资金来源", "资金大类", "资金分类", "项目名称", "", "", "发文日期", "收文日期",
                "资金拨入日期", "资金拨出日期", "公告日期",
                "公告形式", "巡查日期", "巡查人员", "巡查评价", "巡查签证单号"};
        String[] headnum0 = new String[]{"1,2,0,0", "1,2,1,1", "1,1,2,5", "1,2,6,6", "1,2,7,7", "1,1,8,11", "1,1,12,13", "1,1,14,17"};

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFCellStyle headstyle = setHeadStyle(workbook);//表头标题样式
        HSSFCellStyle style = cellStyle(workbook);//列名样式

        sheet.setDefaultRowHeight((short) 360);//设置行高
        // 第一行表头标题
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, head0.length - 1));
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 0x349);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(headstyle);
        CellUtil.setCellValue(cell, sheetName);
        // 第二行表头列名
        row = sheet.createRow(1);
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        row.setHeight((short) 0x250);
        //动态合并单元格
        MergedRegion(sheet, headnum0);

        //第三行(要修改)
        //设置合并单元格的参数并初始化带边框的表头（这样做可以避免因为合并单元格后有的单元格的边框显示不出来）
        row = sheet.createRow(2);//因为下标从0开始，所以这里表示的是excel中的第3行
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);//设置excel中第三行的1、2、3、4、5列的边框
            if (i > 4) {
                for (int j = 0; j < head1.length; j++) {
                    cell = row.createCell(j + 2);
                    cell.setCellValue(head1[j]);//给excel中第三行的6/7/8/9/10/11/12/13/14/15列赋值（"单位编号", "单位名称", "付款账号", "付款单位","付款银行"）
                    cell.setCellStyle(style);//设置excel中第三行的6/7/8/9/10/11/12/13/14/15列的边框
                }
            }
        }

        // 设置列值-内容
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow(i + 3);
            CapitalModel capital = dataList.get(i);
            row.createCell(0).setCellValue(capital.getCXZPROGNAME());
            row.createCell(1).setCellValue(capital.getCBMUNITNAME());
            row.createCell(2).setCellValue(capital.getProjecttype1());
            row.createCell(3).setCellValue(capital.getProjecttype2());
            row.createCell(4).setCellValue(capital.getProjecttype3());
            row.createCell(5).setCellValue(capital.getProjecttype4());
            row.createCell(6).setCellValue(capital.getSUBSIDYCONTENT());
            row.createCell(7).setCellValue(capital.getIMONEY());
            row.createCell(8).setCellValue(capital.getDDATE());
            row.createCell(9).setCellValue(capital.getGDATE());
            row.createCell(10).setCellValue(capital.getDate1());
            row.createCell(11).setCellValue(capital.getDate2());
            row.createCell(12).setCellValue(capital.getSTART_DATE());
            row.createCell(13).setCellValue(capital.getCANNOUNCETYPE());
            row.createCell(14).setCellValue(capital.getDCHECKDATE());
            row.createCell(15).setCellValue(capital.getDCHECKRY());
            row.createCell(16).setCellValue(capital.getDCHECKPJ());
            row.createCell(17).setCellValue(capital.getFORMNO());

        }
        // 设置列宽  （第几列，宽度）
        for (int i = 0; i < head0.length; i++) {
            sheet.autoSizeColumn(i);
        }
        setSizeColumn(sheet, head0.length);
        // 第六步，将文件存到指定位置
        print(response, workbook, fileno);

    }

    /**
     * 导出上级资金项目
     *
     * @param request
     * @param response
     * @param dataList
     * @param sheetName
     */

    public static void reportProg(HttpServletRequest request, HttpServletResponse response, List<Project> dataList, String sheetName, String fileno) {
        String[] head0 = new String[]{"项目名称", "金额", "文号", "单位", "功能科目分类",
                "部门经济分类", "政府经济分类", "预算指标", "资金来源"};//在excel中的第二行每列的参数
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFCellStyle headstyle = setHeadStyle(workbook);//表头标题样式

        HSSFCellStyle style = cellStyle(workbook);//列名样式

        sheet.setDefaultRowHeight((short) 360);//设置行高
        // 第一行表头标题
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, head0.length - 1));
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 0x349);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(headstyle);
        CellUtil.setCellValue(cell, sheetName);
        // 第二行表头列名
        row = sheet.createRow(1);
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        row.setHeight((short) 0x250);
        //设置列值-内容
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow((int) i + 2);
            Project project = dataList.get(i);

            // 第四步，创建单元格，并设置值
            row.createCell(0).setCellValue(project.getCPROGRAMNAME());
            row.createCell(1).setCellValue(project.getIMONEY());
            row.createCell(2).setCellValue(project.getFILENO());
            row.createCell(3).setCellValue(project.getCENTERPRISE());
            row.createCell(4).setCellValue(project.getCFUNCTION());
            row.createCell(5).setCellValue(project.getCECONOMYSECTION());
            row.createCell(6).setCellValue(project.getCECONOMYSECTIONGOV());
            row.createCell(7).setCellValue(project.getCBUDGETCATEGORY());
            row.createCell(8).setCellValue(project.getCRESOURCE());
        }
        // 设置列宽  （第几列，宽度）
        for (int i = 0; i < head0.length; i++) {
            sheet.autoSizeColumn(i);
        }
        setSizeColumn(sheet, head0.length);
        //导出到指定目录
        print(response, workbook, fileno);


    }

    /**
     * 导出银行存款明细表和现金日记账项目表
     *
     * @param request
     * @param response
     * @param dataList
     * @param sheetName
     */
    public static void exportSubsidiaryAndCash(HttpServletRequest request, HttpServletResponse response,
                                               List<Map<String, Object>> dataList, String sheetName, String fileno, String[] head0, String[] head1) {
        //在excel中的第二行每列的参数
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表

        HSSFCellStyle headstyle = setHeadStyle(workbook);//表头标题样式
        HSSFCellStyle style = comCellStyle(workbook);//列名样式
        HSSFCellStyle rightStyle = RightCellStyle(workbook);//列名样式
        HSSFCellStyle style3 = ColorYellowRight(workbook);
        HSSFCellStyle style4 = ColorYellowCenter(workbook);
        HSSFCellStyle style5 = ColorBlueRight(workbook);
        HSSFCellStyle style6 = ColorBlueCenter(workbook);

        sheet.setDefaultRowHeight((short) 360);//设置行高
        // 第一行表头标题
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, head0.length - 1));
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 0x349);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(headstyle);
        CellUtil.setCellValue(cell, sheetName);

        // 第二行表头列名
        row = sheet.createRow(1);
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);

        }
        // 设置列宽  （第几列，宽度）
        for (int i = 0; i < head0.length; i++) {
            sheet.setColumnWidth(i, 3500);
        }
        row.setHeight((short) 0x250);
        //设置列值-内容
        if (fileno.contains("项目")) {
            for (int i = 0; i < dataList.size(); i++) {
                row = sheet.createRow((int) i + 2);
                // 第四步，创建单元格，并设置值
                for (int j = 0; j < head0.length; j++) {
                    Map tempmap = (HashMap) dataList.get(i);
                    Object data = tempmap.get(head1[j]);
                    cell = row.createCell(j);
                    cell.setCellStyle(style);
                    cell.setCellType(CellType.STRING);
                    CellUtil.setCellValue(cell, data);
                }
            }
        } else {
            for (int i = 0; i < dataList.size(); i++) {
                row = sheet.createRow((int) i + 2);
                // 第四步，创建单元格，并设置值
                for (int j = 0; j < head0.length; j++) {
                    Map tempmap = (HashMap) dataList.get(i);
                    Object data = tempmap.get(head1[j]);
                    if (j < 7) {
                        cell = row.createCell(j);
                        cell.setCellStyle(style);
                        cell.setCellType(CellType.STRING);
                        CellUtil.setCellValue(cell, data);
                    } else {
                        cell = row.createCell(j);
                        cell.setCellStyle(rightStyle);
                        cell.setCellType(CellType.STRING);
                        CellUtil.setCellValue(cell, data);
                    }
                    if (dataList.get(i).get("REMARKS") != null && dataList.get(i).get("REMARKS").equals("合计")) {
                        if (j < 7) {
                            cell.setCellStyle(style4);
                        } else {
                            cell.setCellStyle(style3);
                        }
                    }
                    if (dataList.get(i).get("REMARKS") != null && dataList.get(i).get("REMARKS").equals("累计")) {
                        if (j < 7) {
                            cell.setCellStyle(style6);
                        } else {
                            cell.setCellStyle(style5);
                        }
                    }

                }
            }
        }
        //导出到指定目录
        print(response, workbook, fileno);

    }

    /**
     * 导出16个乡镇经济分类报表、基本支出经济分类报表
     *
     * @param request
     * @param response
     * @param dataList
     * @param sheetName
     */
    public static void exportJJFL(HttpServletRequest request, HttpServletResponse response,
                                  List<Map<String, Object>> dataList, String sheetName, String fileno, String[] head0, String[] head1) {
        ;//在excel中的第二行每列的参数

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表

        HSSFCellStyle headstyle = setHeadStyle(workbook);//表头标题样式
        HSSFCellStyle style = cellStyle(workbook);//列名样式
        HSSFCellStyle leftStyle = LeftCellStyle(workbook);//列名样式
        HSSFCellStyle rightStyle = RightCellStyle(workbook);//列名样式
        HSSFCellStyle style3 = ColorYellowRight(workbook);
        HSSFCellStyle style4 = ColorYellowLeft(workbook);
        HSSFCellStyle style5 = ColorBlueRight(workbook);
        HSSFCellStyle style6 = ColorBlueLeft(workbook);


        sheet.setDefaultRowHeight((short) 360);//设置行高
        // 第一行表头标题
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, head0.length - 1));
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 0x349);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(headstyle);
        CellUtil.setCellValue(cell, sheetName);
        // 第二行表头列名
        row = sheet.createRow(1);
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        row.setHeight((short) 0x250);

        for (int i = 0; i < head0.length; i++) {
            if (i == 0) {
                sheet.setColumnWidth(i, 2500);
            } else if (i == 1) {
                sheet.setColumnWidth(i, 9200);
            } else {
                sheet.setColumnWidth(i, 3500);
            }
        }
        //设置列值-内容
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow((int) i + 2);
            int length = 0;
            if (fileno.contains("经济科目")) {
                length = dataList.get(i).get("OUTLAY_CODE") == null ? 0 : dataList.get(i).get("OUTLAY_CODE").toString().length();
            } else if (fileno.contains("基本支出")) {
                length = dataList.get(i).get("GOV_OUTLAY_CODE") == null ? 0 : dataList.get(i).get("GOV_OUTLAY_CODE").toString().length();
            } else {
                length = dataList.get(i).get("B_ACC_CODE") == null ? 0 : dataList.get(i).get("B_ACC_CODE").toString().length();
            }
            // 第四步，创建单元格，并设置值
            for (int j = 0; j < head0.length; j++) {
                Map tempmap = (HashMap) dataList.get(i);
                Object data = tempmap.get(head1[j]);
                if (j < 2) {
                    cell = row.createCell(j);
                    cell.setCellStyle(leftStyle);
                    cell.setCellType(CellType.STRING);
                    CellUtil.setCellValue(cell, data);
                } else {
                    cell = row.createCell(j);
                    cell.setCellStyle(rightStyle);
                    cell.setCellType(CellType.STRING);
                    CellUtil.setCellValue1(cell, data);
                }
                if (length == 3) {
                    if (j < 2) {
                        cell.setCellStyle(style4);
                    } else {
                        cell.setCellStyle(style3);
                    }

                } else if (length == 5) {
                    if (j < 2) {
                        cell.setCellStyle(style6);
                    } else {
                        cell.setCellStyle(style5);
                    }
                }
            }
        }
        //导出到指定目录
        print(response, workbook, fileno);
    }

    /**
     * 导出单个乡镇经济分类报表,收入支出表
     *
     * @param request
     * @param response
     * @param dataList
     * @param sheetName
     */
    public static void exportOne(HttpServletRequest request, HttpServletResponse response,
                                 List<Map<String, Object>> dataList, String sheetName,
                                 String fileno, String[] head1, String[] head2) {
        String[] head0 = new String[]{"科目编码", "科目名称", "栏号"};//在excel中的第二行每列的参数
        int head0len = head0.length;
        head0 = Arrays.copyOf(head0, head0len + head2.length);//扩容
        System.arraycopy(head2, 0, head0, head0len, head2.length);//将head2拼到head0上
        System.out.println(Arrays.toString(head0));

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFCellStyle headstyle = setHeadStyle(workbook);//表头标题样式
        HSSFCellStyle style = cellStyle(workbook);//列名样式
        HSSFCellStyle leftStyle = LeftCellStyle(workbook);//列名样式
        HSSFCellStyle rightStyle = RightCellStyle(workbook);//列名样式
        HSSFCellStyle comStyle = comCellStyle(workbook);
        HSSFCellStyle style3 = ColorYellowRight(workbook);
        HSSFCellStyle style4 = ColorYellowLeft(workbook);
        HSSFCellStyle style5 = ColorBlueRight(workbook);
        HSSFCellStyle style6 = ColorBlueLeft(workbook);
        HSSFCellStyle YcenterStyle = ColorYellowCenter(workbook);
        HSSFCellStyle BcenterStyle = ColorBlueCenter(workbook);

        sheet.setDefaultRowHeight((short) 360);//设置行高

        for (int i = 0; i < head0.length; i++) {
            if (i == 0) {
                sheet.setColumnWidth(i, 2500);
            } else if (i == 1) {
                sheet.setColumnWidth(i, 10000);
            } else if (i == 2) {
                sheet.setColumnWidth(i, 1500);
            } else {
                sheet.setColumnWidth(i, 6000);
            }
        }
        // 第一行表头标题
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, head0.length - 1));
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 0x349);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(headstyle);
        CellUtil.setCellValue(cell, sheetName);
        // 第二行表头列名
        row = sheet.createRow(1);
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        row.setHeight((short) 0x250);

        //设置列值-内容
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow((int) i + 2);
            int length = 0;
            if (fileno.contains("经济科目")) {
                length = dataList.get(i).get("OUTLAY_CODE") == null ? 0 : dataList.get(i).get("OUTLAY_CODE").toString().length();
            } else if (fileno.contains("基本支出")) {
                length = dataList.get(i).get("GOV_OUTLAY_CODE") == null ? 0 : dataList.get(i).get("GOV_OUTLAY_CODE").toString().length();
            } else {
                length = dataList.get(i).get("B_ACC_CODE") == null ? 0 : dataList.get(i).get("B_ACC_CODE").toString().length();
            }
            // 第四步，创建单元格，并设置值
            for (int j = 0; j < head0.length; j++) {
                Map tempmap = (HashMap) dataList.get(i);
                Object data = tempmap.get(head1[j]);
                if (j < 2) {
                    cell = row.createCell(j);
                    cell.setCellStyle(leftStyle);
                    cell.setCellType(CellType.STRING);
                    CellUtil.setCellValue(cell, data);
                } else if (j == 2) {
                    cell = row.createCell(j);
                    cell.setCellStyle(comStyle);
                    cell.setCellType(CellType.STRING);
                    CellUtil.setCellValue(cell, data);
                } else {
                    cell = row.createCell(j);
                    cell.setCellStyle(rightStyle);
                    cell.setCellType(CellType.STRING);
                    CellUtil.setCellValue1(cell, data);
                }
                if (length == 3) {
                    if (j < 2) {
                        cell.setCellStyle(style4);
                    } else if (j == 2) {
                        cell.setCellStyle(YcenterStyle);
                    } else {
                        cell.setCellStyle(style3);
                    }

                } else if (length == 5) {
                    if (j < 2) {
                        cell.setCellStyle(style6);
                    } else if (j == 2) {
                        cell.setCellStyle(BcenterStyle);
                    } else {
                        cell.setCellStyle(style5);
                    }
                }
            }
        }
        //导出到指定目录
        print(response, workbook, fileno);
    }


    /**
     * 导出专户表
     */
    public static void exportAccount(HttpServletRequest request, HttpServletResponse response, List<SpecialrModel> dataList,
                                     String sheetName, String[] head0, String[] headnum0, String[] head1, String danwei, String fileno)
            throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFCellStyle headstyle = setHeadStyle(workbook);//表头标题样式
        HSSFCellStyle biaotoustyle = biaotouStyleRIGHT(workbook);//表头样式
        HSSFCellStyle style = cellStyle(workbook);//列名样式
        HSSFCellStyle leftStyle = LeftCellStyle(workbook);//列名样式
        HSSFCellStyle rightStyle = RightCellStyle(workbook);//列名样式

        sheet.setDefaultRowHeight((short) 360);//设置行高

        // 第一行表头标题
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, head0.length - 1));
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 700);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(headstyle);
        CellUtil.setCellValue(cell, sheetName);
        // 第二行单位
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, head0.length - 1));
        HSSFRow row1 = sheet.createRow(1);
        row1.setHeight((short) 400);
        HSSFCell cell1 = row1.createCell(0);
        cell1.setCellStyle(biaotoustyle);
        CellUtil.setCellValue(cell1, danwei);
        // 第三行表头列名
        row = sheet.createRow(2);
        row.setHeight((short) 500);
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        //动态合并单元格
        MergedRegion(sheet, headnum0);

        //第四行(要修改)
        //设置合并单元格的参数并初始化带边框的表头（这样做可以避免因为合并单元格后有的单元格的边框显示不出来）
        row = sheet.createRow(3);//因为下标从0开始，所以这里表示的是excel中的第四行
        row.setHeight((short) 500);
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);//设置excel中第四行的1列的边框
            if (i >= 1) {
                for (int j = 0; j < head1.length; j++) {
                    cell = row.createCell(j + 1);
                    cell.setCellValue(head1[j]);//给excel中第四行的2,3、4、5列赋值
                    cell.setCellStyle(style);//设置excel中第四行的2,3、4、5列的边框
                }
            }
        }
        for (int i = 0; i < head0.length; i++) {
            if (i == 0) {
                sheet.setColumnWidth(i, 5000);
            } else {
                sheet.setColumnWidth(i, 4000);
            }
        }

        // 设置列值-内容
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow(i + 4);
            row.setHeight((short) 500);
            SpecialrModel specialr = dataList.get(i);
            if (i > dataList.size() - 5 && i < dataList.size() - 1) {
                specialr = new SpecialrModel();
                specialr.setACC_NAME("");
                specialr.setMONEY1("");
                specialr.setMONEY2("");
                specialr.setMONEY3("");
                specialr.setMONEY4("");
            }
            // 第四步，创建单元格，并设置值
            HSSFCell cell2 = row.createCell(0);
            cell2.setCellValue(specialr.getACC_NAME() == null ? "" : specialr.getACC_NAME());
            cell2.setCellStyle(leftStyle);
            HSSFCell cell3 = row.createCell(1);
            cell3.setCellValue(specialr.getMONEY1() == null ? "" : specialr.getMONEY1());
            cell3.setCellStyle(rightStyle);
            HSSFCell cell4 = row.createCell(2);
            cell4.setCellValue(specialr.getMONEY2() == null ? "" : specialr.getMONEY2());
            cell4.setCellStyle(rightStyle);
            HSSFCell cell5 = row.createCell(3);
            cell5.setCellValue(specialr.getMONEY3() == null ? "" : specialr.getMONEY3());
            cell5.setCellStyle(rightStyle);
            HSSFCell cell6 = row.createCell(4);
            cell6.setCellValue(specialr.getMONEY4() == null ? "" : specialr.getMONEY4());
            cell6.setCellStyle(rightStyle);
            HSSFCell cell7 = row.createCell(5);
            cell7.setCellValue("");
            cell7.setCellStyle(rightStyle);
        }
        //导出到指定目录
        print(response, workbook, fileno);

    }


    /**
     * 导出资产负债表
     */
    public static void exportDebt(HttpServletRequest request, HttpServletResponse response, List<Map<String, Object>> dataList,
                                  String sheetName, String[] head0, String[] headnum0, String[] head1, String[] detail, String[] head, String[] headnum, String fileno)
            throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFCellStyle headstyle = setHeadStyle(workbook);//表头标题样式
        HSSFCellStyle biaotoustyle = biaotouStyle(workbook);//表头样式
        HSSFCellStyle style = cellStyle(workbook);//列名样式
        HSSFCellStyle styleLeft = LeftCellStyle(workbook);//列名样式
        HSSFCellStyle styleRight = RightCellStyle(workbook);//列名样式
        // 设置列宽  （第几列，宽度）
        for (int i = 0; i < head0.length; i++) {
            if (i == 0 || i == 3) {
                sheet.setColumnWidth(i, 6600);
            } else {
                sheet.setColumnWidth(i, 5000);
            }

        }
        sheet.setDefaultRowHeight((short) 360);//设置行高

        // 第一行表头标题
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, head0.length - 1));
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 700);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(headstyle);
        CellUtil.setCellValue(cell, sheetName);
        // 第二行表头列名：制表单位、单位
        row = sheet.createRow(1);
        row.setHeight((short) 350);
        for (int i = 0; i < head.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head[i]);
            cell.setCellStyle(biaotoustyle);
        }
        //动态合并单元格
        MergedRegion(sheet, headnum);
        // 第三行表头列名
        row = sheet.createRow(2);
        row.setHeight((short) 500);
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        //动态合并单元格
        MergedRegion(sheet, headnum0);

        //第四行(要修改)
        //设置合并单元格的参数并初始化带边框的表头（这样做可以避免因为合并单元格后有的单元格的边框显示不出来）
        row = sheet.createRow(3);//因为下标从0开始，所以这里表示的是excel中的第四行
        row.setHeight((short) 500);
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head1[i]);//给excel中第四行的1,2,3、4、5,6列赋值
            cell.setCellStyle(style);//设置excel中第四行的1,2,3、4、5,6列的边框

        }

        // 设置列值-内容
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow(i + 4);
            row.setHeight((short) 500);
            for (int j = 0; j < detail.length; j++) {
                Map tempmap = (HashMap) dataList.get(i);
                Object data = tempmap.get(detail[j]);
                if (j == 0 || j == 3) {
                    cell = row.createCell(j);
                    cell.setCellStyle(styleLeft);
                    cell.setCellType(CellType.STRING);
                    CellUtil.setCellValue(cell, data);
                } else {
                    cell = row.createCell(j);
                    cell.setCellStyle(styleRight);
                    cell.setCellType(CellType.STRING);
                    CellUtil.setCellValue1(cell, data);
                }
            }
        }
        //导出到指定目录
        print(response, workbook, fileno);

    }

    /**
     * 导出执行表、支出表
     */
    public static void exportExecution(HttpServletRequest request, HttpServletResponse response, List<Map<String, Object>> dataList,
                                       String sheetName, String head, String[] head0, String[] headnum0, String[] head1, String[] headnum1, String[] head2, String[] detail, String fileno) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFCellStyle headstyle = setHeadStyle(workbook);//表头标题样式
        HSSFCellStyle style = cellStyle(workbook);//列名样式、
        HSSFCellStyle styleLeft = LeftCellStyle(workbook);//普通列名样式居左
        HSSFCellStyle styleRight = RightCellStyle(workbook);//普通列名样式居右
        HSSFCellStyle style3 = ColorYellowRight(workbook);
        HSSFCellStyle style4 = ColorYellowLeft(workbook);
        HSSFCellStyle styleYCenter = ColorYellowCenter(workbook);
        HSSFCellStyle style5 = ColorBlueRight(workbook);
        HSSFCellStyle style6 = ColorBlueLeft(workbook);
        HSSFCellStyle styleBCenter = ColorBlueCenter(workbook);
        HSSFCellStyle comStyle = comCellStyle(workbook);
        HSSFCellStyle noBorderStyle = NoBorderStyle(workbook);
        HSSFCellStyle biaotoustyle = biaotouStyleRIGHT(workbook);//表头样式

        // 设置列宽  （第几列，宽度）执行表
        if (head0.length == 11) {
            for (int i = 0; i < head0.length; i++) {
                if (i == 0 || i == 8 || i == 10) {
                    sheet.setColumnWidth(i, 2000);
                } else if (i == 1) {
                    sheet.setColumnWidth(i, 8200);
                } else if (i == 2) {
                    sheet.setColumnWidth(i, 1500);
                } else {
                    sheet.setColumnWidth(i, 3500);
                }
            }
        } else {
            //支出表
            for (int i = 0; i < head0.length; i++) {
                if (i == 0 || i == 9) {
                    sheet.setColumnWidth(i, 2000);
                } else if (i == 1) {
                    sheet.setColumnWidth(i, 8200);
                } else if (i == 2) {
                    sheet.setColumnWidth(i, 1500);
                } else {
                    sheet.setColumnWidth(i, 3500);
                }
            }
        }
        sheet.setDefaultRowHeight((short) 360);//设置行高

        // 第一行表头标题
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, head0.length - 1));
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 0x349);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(headstyle);
        CellUtil.setCellValue(cell, sheetName);

        // 第二行单位
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, head0.length - 1));
        HSSFRow row1 = sheet.createRow(1);
        row1.setHeight((short) 400);
        HSSFCell cell1 = row1.createCell(0);
        cell1.setCellStyle(biaotoustyle);
        CellUtil.setCellValue(cell1, head);
        //动态合并单元格
        /*MergedRegion(sheet,headnum);*/

        // 第三行表头列名
        row = sheet.createRow(2);
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        //动态合并单元格
        MergedRegion(sheet, headnum0);

        //第四行表头列名
        row = sheet.createRow(3);
        for (int i = 0; i < head1.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head1[i]);
            cell.setCellStyle(style);
        }
        //动态合并单元格
        MergedRegion(sheet, headnum1);


        //第五行(要修改)
        //设置合并单元格的参数并初始化带边框的表头（这样做可以避免因为合并单元格后有的单元格的边框显示不出来）
        row = sheet.createRow(4);//因为下标从0开始，所以这里表示的是excel中的第四行
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);//设置excel中第四行的1列的边框
            if (i >= 3 && i <= 6 || i >= 10 && i <= 11) {
                for (int j = 0; j < head2.length; j++) {
                    cell = row.createCell(j + 3);
                    cell.setCellValue(head2[j]);//给excel中第5行的4,5,6,7,11,12,13,14列赋值
                    cell.setCellStyle(style);//设置excel中第5行的4,5,6,7,11,12,13,14列的边框
                }
            }
        }

        // 设置列值-内容
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow(i + 5);
            int length = dataList.get(i).get("B_ACC_CODE") == null ? 0 : dataList.get(i).get("B_ACC_CODE").toString().length();
            if (i >= dataList.size() - 2) {
                for (int k = 0; k < detail.length; k++) {
                    Map tempmap = (HashMap) dataList.get(i);
                    Object data = tempmap.get(detail[k]);
                    cell = row.createCell(k);
                    cell.setCellStyle(noBorderStyle);
                    cell.setCellType(CellType.STRING);
                    CellUtil.setCellValue(cell, data);
                }
            } else {
                for (int j = 0; j < detail.length; j++) {
                    Map tempmap = (HashMap) dataList.get(i);
                    Object data = tempmap.get(detail[j]);

                    if (j < 2) {
                        cell = row.createCell(j);
                        cell.setCellStyle(styleLeft);
                        cell.setCellType(CellType.STRING);
                        CellUtil.setCellValue(cell, data);
                    } else if (j == 2) {
                        cell = row.createCell(j);
                        cell.setCellStyle(comStyle);
                        cell.setCellType(CellType.STRING);
                        CellUtil.setCellValue(cell, data);
                    } else {
                        cell = row.createCell(j);
                        cell.setCellStyle(styleRight);
                        cell.setCellType(CellType.STRING);
                        CellUtil.setCellValue1(cell, data);
                    }
                    if (length == 3) {
                        if (j < 2) {
                            cell.setCellStyle(style4);
                        } else if (j == 2) {
                            cell.setCellStyle(styleYCenter);
                        } else {
                            cell.setCellStyle(style3);
                        }

                    } else if (length == 5) {
                        if (j < 2) {
                            cell.setCellStyle(style6);
                        } else if (j == 2) {
                            cell.setCellStyle(styleBCenter);
                        } else {
                            cell.setCellStyle(style5);
                        }
                    }
                }
            }

        }
        //导出到指定目录
        print(response, workbook, fileno);
    }


    /**
     * 导出教育支出表
     */
    public static void exportEducation(HttpServletRequest request, HttpServletResponse response,
                                       List<Map<String, Object>> dataList, String[] head0, String head, String sheetName, String fileno, String[] detail) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表

        HSSFCellStyle headstyle = setHeadStyle(workbook);//表头标题样式
        HSSFCellStyle style = cellStyle(workbook);//列名样式
        HSSFCellStyle styleRight = RightCellStyle(workbook);//居右
        HSSFCellStyle styleLeft = LeftCellStyle(workbook);//居左
        HSSFCellStyle biaotouStyle = biaotouStyleRIGHT(workbook);

        sheet.setDefaultRowHeight((short) 360);//设置行高
        // 第一行表头标题
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, head0.length - 1));
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 0x349);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(headstyle);
        CellUtil.setCellValue(cell, sheetName);
        // 第二行表头列名：制表单位、单位
        /*row = sheet.createRow(1);
        for (int i = 0; i < head.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head[i]);
            cell.setCellStyle(style);
        }*/
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, head0.length - 1));
        row = sheet.createRow(1);
        row.setHeight((short) 0x120);
        cell = row.createCell(0);
        cell.setCellStyle(biaotouStyle);
        CellUtil.setCellValue(cell, head);
        // 第三行表头列名
        row = sheet.createRow(2);
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        row.setHeight((short) 0x250);

        for (int i = 0; i < head0.length; i++) {
            sheet.setColumnWidth(i, 6000);
        }
        //设置列值-内容
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow((int) i + 3);

            // 第四步，创建单元格，并设置值
            for (int j = 0; j < head0.length; j++) {
                Map tempmap = (HashMap) dataList.get(i);
                Object data = tempmap.get(detail[j]);
                if (j == 0) {
                    cell = row.createCell(j);
                    cell.setCellStyle(styleLeft);
                    cell.setCellType(CellType.STRING);
                    CellUtil.setCellValue(cell, data);
                } else {
                    cell = row.createCell(j);
                    cell.setCellStyle(styleRight);
                    cell.setCellType(CellType.STRING);
                    CellUtil.setCellValue(cell, data);
                }

            }
        }
        // 设置列宽  （第几列，宽度）
        for (int i = 0; i < head0.length; i++) {
            sheet.autoSizeColumn(i);
        }
        setSizeColumn(sheet, head0.length);
        //导出到指定目录
        print(response, workbook, fileno);
    }

    /**
     * 政府账
     */
    /**
     * 导出资产负债表
     */

    public static void exportDebtGov(HttpServletRequest request, HttpServletResponse response, List<Map<String, Object>> dataList,
                                     String sheetName, String[] head0, String[] headnum0, String[] head1, String[] detail, String fileno)
            throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFCellStyle headstyle = setHeadStyle(workbook);//表头标题样式
        HSSFCellStyle style = cellStyle(workbook);//列名样式
        HSSFCellStyle styleLeft = LeftCellStyle(workbook);//普通列名样式
        HSSFCellStyle styleRight = RightCellStyle(workbook);//普通列名样式
        HSSFCellStyle biaotouStyle = biaotouStyle(workbook);
        // 设置列宽  （第几列，宽度）
        // 设置列宽  （第几列，宽度）
        for (int i = 0; i < head0.length; i++) {
            if (i == 0 || i == 4) {
                sheet.setColumnWidth(i, 2700);
            } else if (i == 1 || i == 5) {
                sheet.setColumnWidth(i, 6000);
            } else {
                sheet.setColumnWidth(i, 3500);
            }
        }
        sheet.setDefaultRowHeight((short) 360);//设置行高

        // 第一行表头标题
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, head0.length - 1));
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 0x349);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(headstyle);
        CellUtil.setCellValue(cell, sheetName);
        // 第二行表头列名：制表单位、单位
        row = sheet.createRow(1);
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(biaotouStyle);
        }
        //动态合并单元格
        MergedRegion(sheet, headnum0);

        // 第三行表头列名
        row = sheet.createRow(2);
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head1[i]);
            cell.setCellStyle(style);
        }
        //第四行(要修改)
        //设置合并单元格的参数并初始化带边框的表头（这样做可以避免因为合并单元格后有的单元格的边框显示不出来）
        row = sheet.createRow(3);//因为下标从0开始，所以这里表示的是excel中的第四行
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head1[i]);//给excel中第四行的1,2,3、4、5,6列赋值
            cell.setCellStyle(style);//设置excel中第四行的1,2,3、4、5,6列的边框

        }
        // 设置列值-内容
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow(i + 3);
            for (int j = 0; j < detail.length; j++) {
                Map tempmap = (HashMap) dataList.get(i);
                Object data = tempmap.get(detail[j]);
                if (j == 0 || j == 1 || j == 4 || j == 5) {
                    cell = row.createCell(j);
                    cell.setCellStyle(styleLeft);
                    cell.setCellType(CellType.STRING);
                    CellUtil.setCellValue(cell, data);
                } else {
                    cell = row.createCell(j);
                    cell.setCellStyle(styleRight);
                    cell.setCellType(CellType.STRING);
                    CellUtil.setCellValue1(cell, data);
                }
            }
        }

        //导出到指定目录
        print(response, workbook, fileno);

    }


    /**
     * 导出收入支出表、经费支出表、工资福利支出明细表
     * 商品和服务支出明细表、其他资本性支出明细表、对个人和家庭的补助明细表
     */
    public static void exportGov(HttpServletRequest request, HttpServletResponse response, List<Map<String, Object>> dataList,
                                 String sheetName, String[] head0, String[] headnum0, String[] head1, String[] headnum1,
                                 String[] head2, String[] headnum2, String[] head3, String[] detail, String fileno) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFCellStyle headstyle = setHeadStyle(workbook);//表头标题样式
        HSSFCellStyle style = cellStyle(workbook);//列名样式
        HSSFCellStyle styleLeft = LeftCellStyle(workbook);//普通列名样式
        HSSFCellStyle styleRight = RightCellStyle(workbook);//普通列名样式
        HSSFCellStyle biaotouStyle = biaotouStyle(workbook);
        // 设置列宽  （第几列，宽度）
        for (int i = 0; i < head0.length; i++) {
            if (i == 0) {
                sheet.setColumnWidth(i, 1000);
            } else if (i == 1) {
                sheet.setColumnWidth(i, 8000);
            } else {
                sheet.setColumnWidth(i, 3000);
            }
        }
        sheet.setDefaultRowHeight((short) 360);//设置行高

        // 第一行表头标题
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, head0.length - 1));
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 0x349);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(headstyle);
        CellUtil.setCellValue(cell, sheetName);

        // 第二行表头列名：制表单位、单位
        row = sheet.createRow(1);
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(biaotouStyle);
        }
        //动态合并单元格
        MergedRegion(sheet, headnum0);

        // 第三行表头列名
        row = sheet.createRow(2);
        row.setHeight((short) 520);
        for (int i = 0; i < head1.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head1[i]);
            cell.setCellStyle(style);
        }
        //动态合并单元格
        MergedRegion(sheet, headnum1);
        //第四行(要修改)
        //设置合并单元格的参数并初始化带边框的表头（这样做可以避免因为合并单元格后有的单元格的边框显示不出来）
        row = sheet.createRow(3);//因为下标从0开始，所以这里表示的是excel中的第四行
        for (int i = 0; i < head2.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head2[i]);//给excel中第四行的1,2,3、4、5,6列赋值
            cell.setCellStyle(style);//设置excel中第四行的1,2,3、4、5,6列的边框

        }
        //动态合并单元格
        MergedRegion(sheet, headnum2);

        //第五行
        row = sheet.createRow(4);//因为下标从0开始，所以这里表示的是excel中的第四行
        for (int i = 0; i < head3.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head3[i]);//给excel中第四行的1,2,3、4、5,6列赋值
            cell.setCellStyle(style);//设置excel中第四行的1,2,3、4、5,6列的边框

        }
        // 设置列值-内容
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow(i + 5);
            for (int j = 0; j < detail.length; j++) {
                Map tempmap = (HashMap) dataList.get(i);
                if (j < 2) {
                    Object data = tempmap.get(detail[j]);
                    cell = row.createCell(j);
                    cell.setCellStyle(styleLeft);
                    cell.setCellType(CellType.STRING);
                    CellUtil.setCellValue2(cell, data);
                } else {
                    Object data = tempmap.get(detail[j]);
                    cell = row.createCell(j);
                    cell.setCellStyle(styleRight);
                    cell.setCellType(CellType.STRING);
                    CellUtil.setCellValue2(cell, data);
                }

            }

        }
        //导出到指定目录
        print(response, workbook, fileno);
    }

    /**
     * 预算收入表
     */
    public static void exportIncome(HttpServletRequest request, HttpServletResponse response, List<Map<String, Object>> dataList,
                                    String sheetName, String[] head0, String[] headnum0, String[] head1, String[] detail, String date, String fileno)
            throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFCellStyle headstyle = setHeadStyle(workbook);//表头标题样式
        HSSFCellStyle biaotoustyle = biaotouStyle(workbook);//表头样式
        HSSFCellStyle style = cellStyle(workbook);//列名样式
        HSSFCellStyle styleRight = RightCellStyle(workbook);//居右
        HSSFCellStyle styleLeft = LeftCellStyle(workbook);//居左样式
        HSSFCellStyle style3 = ColorYellowRight(workbook);
        HSSFCellStyle style4 = ColorYellowLeft(workbook);
        HSSFCellStyle style5 = ColorBlueRight(workbook);
        HSSFCellStyle style6 = ColorBlueLeft(workbook);
        for (int k = 0; k < head0.length; k++) {
            if (k == 0) {
                sheet.setColumnWidth(k, 7000);
            } else if (k == 4 || k == 8) {
                sheet.setColumnWidth(k, 2000);
            } else {
                sheet.setColumnWidth(k, 3500);
            }
        }
        sheet.setDefaultRowHeight((short) 360);//设置行高

        // 第一行表头标题
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, head0.length - 1));
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 0x349);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(headstyle);
        CellUtil.setCellValue(cell, sheetName);
        // 第二行表头标题
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, head0.length - 1));
        HSSFRow row1 = sheet.createRow(1);
        row1.setHeight((short) 0x240);
        HSSFCell cell1 = row1.createCell(0);
        cell1.setCellStyle(biaotoustyle);
        CellUtil.setCellValue(cell1, date);

        // 第三行表头列名
        row = sheet.createRow(2);
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        //动态合并单元格
        MergedRegion(sheet, headnum0);

        //第四行
        row = sheet.createRow(3);//因为下标从0开始，所以这里表示的是excel中的第四行
        for (int i = 0; i < head1.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head1[i]);//给excel中第四行的1,2,3、4、5,6列赋值
            cell.setCellStyle(style);//设置excel中第四行的1,2,3、4、5,6列的边框

        }
        // 设置列值-内容
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow(i + 4);
            int length = dataList.get(i).get("GL_ITEM_CODE") == null ? 0 : dataList.get(i).get("GL_ITEM_CODE").toString().length();
            for (int j = 0; j < detail.length; j++) {
                Map tempmap = (HashMap) dataList.get(i);
                Object data = tempmap.get(detail[j]);
                if (j == 0) {
                    cell = row.createCell(j);
                    cell.setCellStyle(styleLeft);
                    cell.setCellType(CellType.STRING);
                    CellUtil.setCellValue1(cell, data);
                } else {
                    cell = row.createCell(j);
                    cell.setCellStyle(styleRight);
                    cell.setCellType(CellType.STRING);
                    CellUtil.setCellValue1(cell, data);
                }
                if (length == 3) {
                    if (j == 0) {
                        cell.setCellStyle(style4);
                    } else {
                        cell.setCellStyle(style3);
                    }

                } else if (length == 5) {
                    if (j == 0) {
                        cell.setCellStyle(style6);
                    } else {
                        cell.setCellStyle(style5);
                    }
                }
            }
        }

        //导出到指定目录
        print(response, workbook, fileno);
    }

    //导出模板
    public static void exportModel(HttpServletRequest request, HttpServletResponse response, String sheetName, String[] head0, String fileno)
            throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFCellStyle headstyle = setHeadStyle(workbook);//表头标题样式
        HSSFCellStyle style = cellStyle(workbook);//列名样式
        for (int k = 0; k < head0.length; k++) {
            sheet.setColumnWidth(k, 4500);
        }
        sheet.setDefaultRowHeight((short) 360);//设置行高

        // 第一行表头标题
        if (fileno.contains("用途")) {
            HSSFRow row = sheet.createRow(0);
            row.setHeight((short) 0x349);
            HSSFCell cell = row.createCell(0);
            cell.setCellStyle(headstyle);
            CellUtil.setCellValue(cell, sheetName);
        } else {
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, head0.length - 1));
            HSSFRow row = sheet.createRow(0);
            row.setHeight((short) 0x349);
            HSSFCell cell = row.createCell(0);
            cell.setCellStyle(headstyle);
            CellUtil.setCellValue(cell, sheetName);

        }
        // 第二行表头标题
        HSSFRow row = sheet.createRow(1);
        for (int i = 0; i < head0.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }


        //导出到指定目录
        print(response, workbook, fileno);
    }


    //导出拨款单
    public static void exportPaySon(HttpServletRequest request, HttpServletResponse response, String sheetName, String[] head0, String fileno, List<Map<String, Object>> dataList, String[] detail)
            throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFCellStyle headstyle = setHeadStyle(workbook);//表头标题样式
        HSSFCellStyle style = cellStyle(workbook);//列名样式
        HSSFCellStyle styleLeft = LeftCellStyle(workbook);
        HSSFCellStyle styleRight = RightCellStyle(workbook);
        for (int k = 0; k < head0.length; k++) {
            sheet.setColumnWidth(k, 4500);
        }
        sheet.setDefaultRowHeight((short) 360);//设置行高

        // 第一行表头标题
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, head0.length - 1));
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 0x349);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(headstyle);
        CellUtil.setCellValue(cell, sheetName);

        // 第二行表头标题
        HSSFRow row1 = sheet.createRow(1);
        for (int i = 0; i < head0.length; i++) {
            HSSFCell cell1 = row1.createCell(i);
            cell1.setCellValue(head0[i]);
            cell1.setCellStyle(style);
        }
        //设置列值-内容
        for (int i = 0; i < dataList.size(); i++) {
            row = sheet.createRow((int) i + 2);

            // 第四步，创建单元格，并设置值
            for (int j = 0; j < head0.length; j++) {
                Map tempmap = (HashMap) dataList.get(i);
                Object data = tempmap.get(detail[j]);
                if (j == 8) {
                    cell = row.createCell(j);
                    cell.setCellStyle(styleRight);
                    cell.setCellType(CellType.STRING);
                    CellUtil.setCellValue(cell, data);
                } else {
                    cell = row.createCell(j);
                    cell.setCellStyle(styleLeft);
                    cell.setCellType(CellType.STRING);
                    CellUtil.setCellValue(cell, data);
                }

            }
        }

        //导出到指定目录
        print(response, workbook, fileno);
    }

    /**
     * 中文列宽自适应
     *
     * @param sheet
     * @param size
     */
    private static void setSizeColumn(HSSFSheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == CellType.STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256);
        }
    }

    /**
     * 表头标题样式
     * 表头单位样式
     * 列名样式
     * 普通单元格样式（中文）
     */
    //设置上下边框
    public static void SetBoder(HSSFCellStyle style) {
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框

    }

    public static HSSFCellStyle setHeadStyle(HSSFWorkbook workbook) {
        // 表头标题样式
        HSSFFont headfont = workbook.createFont();
        headfont.setFontName("宋体");
        headfont.setFontHeightInPoints((short) 15);// 字体大小
        headfont.setBold(true);
        HSSFCellStyle headstyle = workbook.createCellStyle();
        headstyle.setFont(headfont);
        headstyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        headstyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        headstyle.setLocked(true);
        return headstyle;
    }

    //设置第二行表头样式居中
    public static HSSFCellStyle biaotouStyle(HSSFWorkbook workbook) {
        HSSFFont biaotoufont = workbook.createFont();
        biaotoufont.setFontName("宋体");
        biaotoufont.setFontHeightInPoints((short) 10);// 字体大小
        HSSFCellStyle biaotoustyle = workbook.createCellStyle();
        biaotoustyle.setFont(biaotoufont);
        biaotoustyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        biaotoustyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        biaotoustyle.setLocked(true);
        return biaotoustyle;
    }

    //设置第二行表头样式居右
    public static HSSFCellStyle biaotouStyleRIGHT(HSSFWorkbook workbook) {
        HSSFFont biaotoufont = workbook.createFont();
        biaotoufont.setFontName("宋体");
        biaotoufont.setFontHeightInPoints((short) 10);// 字体大小
        HSSFCellStyle biaotoustyle = workbook.createCellStyle();
        biaotoustyle.setFont(biaotoufont);
        biaotoustyle.setAlignment(HorizontalAlignment.RIGHT);// 左右居中
        biaotoustyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        biaotoustyle.setLocked(true);
        return biaotoustyle;
    }

    //无边框列名样式
    public static HSSFCellStyle NoBorderStyle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setBold(false);
        font.setFontHeightInPoints((short) 10);// 字体大小
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.NONE); //下边框
        style.setBorderLeft(BorderStyle.NONE);//左边框
        style.setBorderTop(BorderStyle.NONE);//上边框
        style.setBorderRight(BorderStyle.NONE);//右边框
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);// 左右居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        style.setWrapText(true);
        style.setLocked(true);
        return style;
    }

    // 列名样式
    public static HSSFCellStyle cellStyle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);// 字体大小
        HSSFCellStyle style = workbook.createCellStyle();
        SetBoder(style);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        style.setWrapText(true);
        style.setLocked(true);
        return style;
    }

    // 普通单元格样式（中文）居中
    public static HSSFCellStyle comCellStyle(HSSFWorkbook workbook) {
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short) 10);
        HSSFCellStyle style2 = workbook.createCellStyle();
        SetBoder(style2);
        style2.setFont(font2);
        style2.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        style2.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居
        return style2;
    }

    // 普通单元格样式（中文）居右
    public static HSSFCellStyle RightCellStyle(HSSFWorkbook workbook) {
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short) 9);
        HSSFCellStyle style2 = workbook.createCellStyle();
        SetBoder(style2);
        style2.setFont(font2);
        style2.setAlignment(HorizontalAlignment.RIGHT);// 左右居右
        style2.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居
        return style2;
    }

    // 普通单元格样式（中文）居左
    public static HSSFCellStyle LeftCellStyle(HSSFWorkbook workbook) {
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short) 10);
        HSSFCellStyle style2 = workbook.createCellStyle();
        SetBoder(style2);
        style2.setFont(font2);
        style2.setAlignment(HorizontalAlignment.LEFT);// 左右居左
        style2.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居
        return style2;
    }

    //设置黄色背景色
    public static HSSFCellStyle ColorYellowRight(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short) 9);
        SetBoder(style);
        style.setFont(font2);
        style.setAlignment(HorizontalAlignment.RIGHT);// 左右居右
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        return style;
    }

    public static HSSFCellStyle ColorYellowLeft(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short) 10);
        SetBoder(style);
        style.setFont(font2);
        style.setAlignment(HorizontalAlignment.LEFT);// 左右居右
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        return style;
    }

    public static HSSFCellStyle ColorYellowCenter(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short) 10);
        SetBoder(style);
        style.setFont(font2);
        style.setAlignment(HorizontalAlignment.CENTER);// 左右居右
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        return style;
    }

    //设置蓝色背景色
    public static HSSFCellStyle ColorBlueRight(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short) 9);
        SetBoder(style);
        style.setFont(font2);
        style.setAlignment(HorizontalAlignment.RIGHT);// 左右居右
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex());

        return style;
    }

    //设置蓝色背景色
    public static HSSFCellStyle ColorBlueLeft(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short) 10);
        SetBoder(style);
        style.setFont(font2);
        style.setAlignment(HorizontalAlignment.LEFT);// 左右居右
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex());

        return style;
    }

    public static HSSFCellStyle ColorBlueCenter(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short) 10);
        SetBoder(style);
        style.setFont(font2);
        style.setAlignment(HorizontalAlignment.CENTER);// 左右居右
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex());

        return style;
    }


    //动态合并单元格
    public static void MergedRegion(HSSFSheet sheet, String[] headnum) {
        for (int i = 0; i < headnum.length; i++) {
            String[] temp = headnum[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
        }
    }

    /**
     * 浏览器导出
     */
    public static void print(HttpServletResponse response, HSSFWorkbook workbook, String fileno) {
        try {
            response.setContentType("application/ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileno + ".xls", "UTF-8"))));
            OutputStream out;
            out = response.getOutputStream();
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
