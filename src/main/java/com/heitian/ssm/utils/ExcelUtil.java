package com.heitian.ssm.utils;

import org.apache.poi.hssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * POI导出数据到EXCEL工具类
 * @ClassName: ExcelUtil 
 * @Title: 使用POI技术导出EXCEL
 * @Description: 
 *			  	1.创建 workbook <br/>
 *			  	2.创建 sheet <br/>
 *			    3.写入表头信息  <br/>
 *			    4.写入内容部分  <br/> 
 *			    5.保存文件到filePath中  <br/>
 * @Company: xxx 
 * @version: v1.0
 * @author: xxx
 * @date: 2015年7月16日 下午3:06:51
 */
public class ExcelUtil {
	/**
	 * 1.创建 workbook
	 * 
	 * @return
	 */
	private static HSSFWorkbook getHSSFWorkbook() {
		return new HSSFWorkbook();
	}

	/**
	 * 2.创建 sheet
	 * 
	 * @param hssfWorkbook
	 * @param sheetName
	 *            sheet 名称
	 * @return
	 */
	@SuppressWarnings("unused")
	private static HSSFSheet getHSSFSheet(HSSFWorkbook hssfWorkbook, String sheetName) {
		return hssfWorkbook.createSheet(sheetName);
	}

	/**
	 * 3.写入表头信息
	 * 
	 * @param hssfWorkbook
	 * @param hssfSheet
	 * @param headInfoList
	 *            List<Map<String, Object>> key: title 列标题 columnWidth 列宽 dataKey 列对应的 dataList item key
	 */
	@SuppressWarnings("static-access")
	private static void writeHeader(HSSFWorkbook hssfWorkbook, HSSFSheet hssfSheet, List<Map<String, Object>> headInfoList) {
		HSSFCellStyle cs = hssfWorkbook.createCellStyle();
		HSSFFont font = hssfWorkbook.createFont();
		font.setFontHeightInPoints((short) 12);
//		font.setBoldweight(font.BOLDWEIGHT_BOLD);
		cs.setFont(font);
//		cs.setAlignment(cs.ALIGN_CENTER);
		
		int index= hssfSheet.getPhysicalNumberOfRows();
		HSSFRow r = hssfSheet.createRow(index);
		r.setHeight((short) 380);
		HSSFCell c = null;
		Map<String, Object> headInfo = null;
		// 处理excel表头
		for (int i = 0, len = headInfoList.size(); i < len; i++) {
			headInfo = headInfoList.get(i);
			c = r.createCell(i);
			c.setCellValue(headInfo.get("title").toString());
			c.setCellStyle(cs);
			if (headInfo.containsKey("columnWidth")) {
				hssfSheet.setColumnWidth(i, (short) (((Integer) headInfo.get("columnWidth") * 8) / ((double) 1 / 20)));
			}
		}
	}

	/**
	 * 4.写入内容部分
	 * 
	 * @param hssfWorkbook
	 * @param hssfSheet
	 * @param headInfoList
	 *            List<Map<String, Object>> key: title 列标题 columnWidth 列宽 dataKey 列对应的 dataList item key
	 * @param dataList
	 */
	private static void writeContent(HSSFWorkbook hssfWorkbook, Integer startIndex, HSSFSheet hssfSheet, List<Map<String, Object>> headInfoList, List<Map<String, Object>> dataList) {
		Map<String, Object> headInfo = null;
		HSSFRow r = null;
		HSSFCell c = null;
		// 处理数据
		Map<String, Object> dataItem = null;
		Object v = null;
		for (int i = 0, rownum = startIndex, len = (startIndex + dataList.size()); rownum < len; i++, rownum++) {
			r = hssfSheet.createRow(rownum);
			
			r.setHeightInPoints(16);
			dataItem = dataList.get(i);
			for (int j = 0, jlen = headInfoList.size(); j < jlen; j++) {
				headInfo = headInfoList.get(j);
				c = r.createCell(j);
				v = dataItem.get(headInfo.get("dataKey").toString());

				if (v==null) {
					c.setCellValue("");
				}else if (v instanceof String) {
					c.setCellValue((String) v);
				} else if (v instanceof Boolean) {
					c.setCellValue((Boolean) v);
				} else if (v instanceof Calendar) {
					c.setCellValue((Calendar) v);
				} else if (v instanceof Double) {
					c.setCellValue((Double) v);
				} else if (v instanceof Integer || v instanceof Long || v instanceof Short || v instanceof Float) {
					c.setCellValue(Double.parseDouble(v.toString()));
				} else if (v instanceof HSSFRichTextString) {
					c.setCellValue((HSSFRichTextString) v);
				} else {
					c.setCellValue(v.toString());
				}
			}
		}
	}
	/**
	 * 保存文件到filePath中
	 * @param hssfWorkbook
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	private static HSSFWorkbook write2FilePath(HSSFWorkbook hssfWorkbook, String filePath) throws IOException {
		OutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(filePath);
			hssfWorkbook.write(fileOut);
			return hssfWorkbook;
		} finally {
			if (fileOut != null) {
				fileOut.close();
			}
		}
	}
	
	/**
	 * 导出excel数据 
	 * 
	 * @param headInfoList headInfoList2   双标题
	 *            List<Map<String, Object>> key: title 列标题 columnWidth 列宽 dataKey 列对应的 dataList item key
	 * @param dataList
	 *            List<Map<String, Object>> 导出的数据
	 * @throws IOException
	 * 
	 */
	private static HSSFWorkbook exportExcel2FilePath(List<Map<String, Object>> headInfoList, List<Map<String, Object>> headInfoList2, List<Map<String, Object>> dataList) throws IOException {
		if (headInfoList==null||headInfoList.size()<=0) {
			throw new RuntimeException("exportExcel2FilePath((List<Map<String, Object>> headInfoList,List<Map<String, Object>> headInfoList2,List<Map<String, Object>> dataList))方法中，标题headInfoList 不能为空！");
		}
		if (dataList==null||dataList.size()<=0) {
			throw new RuntimeException("exportExcel2FilePath((List<Map<String, Object>> headInfoList,List<Map<String, Object>> headInfoList2,List<Map<String, Object>> dataList))方法中，数据集合dataList 不能为空！");
		}
		
		// 1.创建 Workbook
		HSSFWorkbook hssfWorkbook = getHSSFWorkbook();
		// 2.创建 Sheet
		HSSFSheet hssfSheet = null;
		int total = dataList.size();
		//默认标题行为1行
		int sheetCount=(total%65535==0)?(total/65535):(total/65535+1);
		for (int i = 0; i < sheetCount; i++) {
			hssfSheet = hssfWorkbook.createSheet();
			// 3.写入 head
			writeHeader(hssfWorkbook, hssfSheet , headInfoList);
			if (headInfoList2!=null&&headInfoList2.size()>0) {
				writeHeader(hssfWorkbook, hssfSheet , headInfoList2);
			}
			// 计算标题行数（非数据部分行数）
			int titleCount = hssfSheet.getPhysicalNumberOfRows();
			int realCount = 65536-titleCount;
			sheetCount = (total%realCount==0)?(total/realCount):(total/realCount+1);
			
			// 4.写入内容
			int startIndex,toIndex;
			startIndex = i*realCount;
			toIndex = (i+1)*realCount;
			if (toIndex>dataList.size()) {
				toIndex = dataList.size();
			}
			writeContent(hssfWorkbook,titleCount ,hssfSheet, headInfoList, dataList.subList(startIndex,toIndex));
		}
	// 5.保存文件到filePath中
		write2FilePath(hssfWorkbook, "/Users/rosyblackliang/Desktop/test3.xlsx");
		return hssfWorkbook;
	}

	/******************************************  外部调用接口  *********************************************/
	/**
	 * 导出excel数据到指定路径  最多两行标题
	 * @param dataList 数据源
	 * @param titles titles2 标题数组
	 * @param keys 键集合
	 * return: void
	 * @throws IOException 
	 */
	public static HSSFWorkbook exportExcel2FilePath(List<Map<String, Object>> dataList, String[] titles, String[] titles2, String[] keys) throws IOException {
		if (dataList==null||dataList.size()<=0) {
			throw new RuntimeException("exportExcel2FilePath(List<Map<String, Object>> dataList,String[] titles,String[] keys)方法中，数据集合dataList 不能为空！");
		}
		if (titles==null||titles.length<=0) {
			throw new RuntimeException("exportExcel2FilePath(List<Map<String, Object>> dataList,String[] titles,String[] keys)方法中，标题数组titles 不能为空！");
		}
		if (keys==null||keys.length<=0) {
			throw new RuntimeException("exportExcel2FilePath(List<Map<String, Object>> dataList,String[] titles,String[] keys)方法中，标题键数组keys 不能为空！");
		}
		
		List<Map<String, Object>> headInfoList = new LinkedList<Map<String,Object>>();
		for (int i = 0; i < keys.length; i++) {
			Map<String, Object> itemMap = new HashMap<String, Object>();
			itemMap.put("title", titles[i]);
			itemMap.put("columnWidth", 25);
			itemMap.put("dataKey", keys[i]);
			headInfoList.add(itemMap);
		}
		List<Map<String, Object>> headInfoList2 = new LinkedList<Map<String,Object>>();
		if (titles2!=null&&titles2.length>0) {
			for (int i = 0; i < keys.length; i++) {
				Map<String, Object> itemMap = new HashMap<String, Object>();
				itemMap.put("title", titles2[i]);
				itemMap.put("columnWidth", 25);
				itemMap.put("dataKey", keys[i]);
				headInfoList2.add(itemMap);
			}
		}
		return exportExcel2FilePath(headInfoList,headInfoList2,dataList);
	}
	public static void main(String[] args) {
		//测试代码	
		List<Map<String, Object>> headInfoList = new ArrayList<Map<String, Object>>();
		Map<String, Object> itemMap = new HashMap<String, Object>();
		itemMap.put("title", "序号1");
		itemMap.put("columnWidth", 25);
		itemMap.put("dataKey", "XH1");
		headInfoList.add(itemMap);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "序号2");
		itemMap.put("columnWidth", 50);
		itemMap.put("dataKey", "XH2");
		headInfoList.add(itemMap);

		itemMap = new HashMap<String, Object>();
		itemMap.put("title", "序号3");
		itemMap.put("columnWidth", 25);
		itemMap.put("dataKey", "XH3");
		headInfoList.add(itemMap);

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String, Object> dataItem = null;
		for (int i = 0; i < 200; i++) {
			dataItem = new HashMap<String, Object>();
			dataItem.put("XH1", "data" + i);
			dataItem.put("XH2", 88888888f);
			dataItem.put("XH3", "脉兜V5..");
			dataList.add(dataItem);
		}
		try {
			//单行标题
//			ExcelUtil.exportExcel2FilePath(headInfoList,null, dataList);
			//双行标题
			ExcelUtil.exportExcel2FilePath(headInfoList,headInfoList, dataList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
