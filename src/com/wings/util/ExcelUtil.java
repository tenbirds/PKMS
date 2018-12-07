package com.wings.util;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import com.pkms.common.mail.service.MailService;

public class ExcelUtil {
	static Logger logger = Logger.getLogger(ExcelUtil.class);
	/**
	 * <p>
	 * 에러나 이벤트와 관련된 각종 메시지를 로깅하기 위한 Log 오브젝트
	 * </p>
	 */

//	private static Log log = LogFactory.getLog(ExcelUtil.class);
	
	public static String write(String filename, String localFilePath, String[] headers, List<List<String>> dataList) throws Exception {
		List<String> headerList = new ArrayList<String>();
		for(String header : headers) {
			headerList.add(header);
		}
		return write(filename, localFilePath, headerList, dataList);
	}
	
	public static String write(String filename, String localFilePath, List<String> headerList, List<List<String>> dataList) throws Exception {
		String yy = String.valueOf(DateUtil.getCurrentYearAsInt());
		String mm = WingsStringUtil.lpad(String.valueOf(DateUtil.getCurrentMonthAsInt()), 2, "0");
		String dd = WingsStringUtil.lpad(String.valueOf(DateUtil.getCurrentDayAsInt()), 2, "0");
		String yymmdd = yy + mm + dd;

		// template_file_name Set
		String template_file_name = "PKMS_" + filename + "_" + yymmdd + ".xls";
		String template_file = localFilePath + template_file_name;

		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();

		// Detail Header Set
		setHeader(wb, sheet, headerList);

		int rowCnt = 1;
		String dataText = null;
		int maxLine = 0;
		String[] dataLine = null;

		// Data Setting
		List<String> cellList;
		HSSFRow rowi;
		HSSFCell dataCell = null;
		HSSFCellStyle dataStyle = wb.createCellStyle();
		dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_JUSTIFY);
		dataStyle.setWrapText(true);
		
		for (int i = 0; i < dataList.size(); i++) {
			rowi = sheet.createRow(rowCnt);
			rowCnt++;

			cellList = dataList.get(i);
			
			for(int colIndex = 0; colIndex < cellList.size(); colIndex++) {
				dataCell = rowi.createCell(colIndex);
				dataText = cellList.get(colIndex);
				dataCell.setCellValue(new HSSFRichTextString(dataText));
				dataCell.setCellStyle(dataStyle);
				
				sheet.autoSizeColumn(colIndex);
				
				//높이 구하기
				if(dataText != null) {
					dataLine = dataText.split("\n");
					if(dataLine != null) {
						if(maxLine < dataLine.length) {
							maxLine = dataLine.length;
						}
					}
				}
			}
			
			rowi.setHeight((short) (maxLine * 300));
			maxLine = 0;
			dataText = null;
			dataLine = null;
		}

		write(wb, template_file);
		
		return template_file_name;
	}

	public static List<List<String>> extractExcelData(List<Object> objectList, String[] fields) throws Exception {
		List<List<String>> rowList = new ArrayList<List<String>>();
		List<String> colList;
		
		for(Object object : objectList) {
			colList = new ArrayList<String>();
			 
			for(String field : fields) {
				
				colList.add(ObjectUtil.getObjectFieldValue(object, field)==null?"":ObjectUtil.getObjectFieldValue(object, field).toString());
			}
			rowList.add(colList);
		}
		
		return rowList;
	}
	
	public static String write4Template(String filename, String localFilePath, List<String> headerList) throws Exception {
		String template_file_name = filename + ".xls";
		String template_file = localFilePath + template_file_name;
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();

		setHeader(wb, sheet, headerList);
		
		write(wb, template_file);
		
		return template_file_name;
	}

	private static void setHeader(HSSFWorkbook wb, HSSFSheet sheet, List<String> headerList) throws Exception {
		HSSFCellStyle headerStyle = wb.createCellStyle();
//      headerStyle.setFont(font);
		headerStyle.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
		headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		HSSFCell headerCell = null;
		
		HSSFRow row0 = sheet.createRow(0);
		
		for (int colIndex = 0; colIndex < headerList.size(); colIndex++) {
			
			headerCell = row0.createCell(colIndex);
			
			headerCell.setCellValue(new HSSFRichTextString(headerList.get(colIndex)));
			headerCell.setCellStyle(headerStyle);

			sheet.autoSizeColumn(colIndex);
			
		}
	}
	
	private static void write(HSSFWorkbook wb, String file) throws Exception {
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(file);
			wb.write(fileOut);
		} catch (Exception ex) {
			throw new Exception("error.sys.엑셀다운로드 중 에러가 발생하였습니다. 에러가 계속되는 경우 관리자에게 문의하시기 바랍니다."); 
		} finally {
			if(fileOut != null) {
				fileOut.close();
			}
		}
	}
//	private static Log log = LogFactory.getLog(ExcelUtil.class);
	 
	public static String write(String filename, String localFilePath, Map<Integer,String[]> headers, List<List<String>> dataList ,List<Integer> mergeCount,int mergeCell,String mergeType) throws Exception {
		List<String> header ;// new ArrayList<String>();
		List<List<String>> headerList= new ArrayList<List<String>>();
		
		 for(int i=0; i<headers.size(); i++){
			 
			 	String[] headerName = headers.get(i);
			 	header = new ArrayList<String>();
			 	for(String stHeader : headerName){
			 		//logger.debug("stHeader------------------>"+stHeader);
			 		header.add(stHeader);
			 	} 
			 	headerList.add(header);
		 }
		 
		
		return write(filename, localFilePath, headerList, dataList, mergeCount,mergeCell,mergeType);
	} 
	
	
	static int  mergeRowCnt  = 0;
	public static String write(String filename, String localFilePath, List<List<String>> headerList, List<List<String>> dataList ,List<Integer> mergeCount,int mergeCell,String mergeType) throws Exception {
		
		//logger.debug("subDataList----------------->"+subDataList);
		
		String yy = String.valueOf(DateUtil.getCurrentYearAsInt());
		String mm = WingsStringUtil.lpad(String.valueOf(DateUtil.getCurrentMonthAsInt()), 2, "0");
		String dd = WingsStringUtil.lpad(String.valueOf(DateUtil.getCurrentDayAsInt()), 2, "0");
		String yymmdd = yy + mm + dd;

		// template_file_name Set
		String template_file_name = "PKMS_" + filename + "_" + yymmdd + ".xls";
		String template_file = localFilePath + template_file_name;

		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		
		 
		setHeaderMerge(wb, sheet, headerList);
		 
		//int rowCnt =sheet.getTopRow();
		
		
	 
		String dataText = null;
		int maxLine = 0;
		String[] dataLine = null;
		String system_seq="";

		// Data Setting
		List<String> cellList;
		List<String> subCellList;
		HSSFRow rowi;
		HSSFCell dataCell = null;
		HSSFCellStyle dataStyle = wb.createCellStyle();
		dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_JUSTIFY);
		dataStyle.setWrapText(true);
		int mergeRowCount=0;
		int mergeCountIndex =0;
		
		
		for (int i = 0; i < dataList.size(); i++) { 
			
			rowi = sheet.createRow(mergeRowCnt); 
			//logger.debug("mergeRowCnt--------------->"+mergeRowCnt);
			
			cellList = dataList.get(i);
			
			for(int colIndex = 0; colIndex < cellList.size(); colIndex++) {
				dataCell = rowi.createCell(colIndex);
				dataText = cellList.get(colIndex);
				dataCell.setCellValue(new HSSFRichTextString(dataText));
				dataCell.setCellStyle(dataStyle);
				
				sheet.autoSizeColumn(colIndex);
				//sheet.setColumnWidth(colIndex, dataText.length()+20);
				
				//높이 구하기
				if(dataText != null) {
					dataLine = dataText.split("\n");
					if(dataLine != null) {
						if(maxLine < dataLine.length) {
							maxLine = dataLine.length;
						}
					}
				}
			}
			
			rowi.setHeight((short) (maxLine * 300));
			maxLine = 0;
			dataText = null;
			dataLine = null;
			//logger.debug("mergeCount===============>"+mergeCount);//[1, 0, 0, 0, 0, 1, 0]
		 
			 
			if(mergeRowCnt>mergeRowCount){//1-0
				 
				if(mergeCount.size()>0){
					
					if((mergeCount.get(mergeCountIndex)-1)>=0){
						 //logger.debug("rowCnt===============>"+rowCnt+"----mergeRowCount---->"+mergeRowCount+"---mergeCountIndex-------------->"+mergeCountIndex+"---"+mergeCount.get(mergeCountIndex));//[0, 0, 2, 1, 0, 0, 0, 2, 1, 1]	 
		 
						
						 mergeRowCount = mergeRowCnt+(mergeCount.get(mergeCountIndex)-1);
						 
						 if(mergeType.equals("R")){
							 for(int cellIndex =0; cellIndex<mergeCell; cellIndex++ ){
								 
								 sheet.addMergedRegion(new CellRangeAddress(mergeRowCnt,mergeRowCount,cellIndex,cellIndex) ); 
								 logger.debug("");
						
							 }	 
						 }else if(mergeType.equals("C")){
							// logger.debug(rowCnt+"<-------------------->"+mergeRowCount);
							 sheet.addMergedRegion(new CellRangeAddress(mergeRowCnt,mergeRowCount,0,mergeCell) );  
						 }
						 
						 
						 
						
					 }else{
						 mergeRowCount++;//2,3,4
					 }
					 mergeCountIndex++;//1,2,3
					
				}
				 
				 
				
				
			}
			 
			mergeRowCnt++;
			
		}

		write(wb, template_file);
		
		return template_file_name;
	}
	
	
	private static void setHeaderMerge(HSSFWorkbook wb, HSSFSheet sheet, List<List<String>> headerList) throws Exception {
		HSSFCellStyle headerStyle = wb.createCellStyle();
//      headerStyle.setFont(font);
		headerStyle.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
		headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
 
		mergeRowCnt=0;
		 
		for (int i = 0; i < headerList.size(); i++) {
			
			HSSFCell headerCell = null; 
			HSSFRow row = sheet.createRow(i);
			List<String> header = headerList.get(i);
			mergeRowCnt++;
			
			for (int colIndex = 0; colIndex <header.size(); colIndex++) {
				
				headerCell = row.createCell(colIndex); 
				headerCell.setCellValue(new HSSFRichTextString(header.get(colIndex)));
				headerCell.setCellStyle(headerStyle); 
				sheet.autoSizeColumn(colIndex);
				
				
				if( colIndex > 0 ){ 
					if(header.get(colIndex-1).equals(header.get(colIndex))){
						
						sheet.addMergedRegion(new CellRangeAddress(i,i,colIndex-1,colIndex) );  
					}
				} 
				 
				if ( i > 0 ){
					
					if(headerList.get(i-1).get(colIndex).equals(header.get(colIndex))){ 
						
						sheet.addMergedRegion(new CellRangeAddress(i-1,i,colIndex,colIndex) );  
					}
					
				} 
			} 
		}
	}
	
	
	
}//END CLASS

