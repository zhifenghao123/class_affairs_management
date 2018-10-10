/**
 * 
 */
package com.classaffairs.framework.core.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Haozhifeng
 *
 */
public class ExcelResolveUtil {
	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
	public static final String EMPTY = "";
	public static final String POINT = ".";
	public static final String NOT_EXCEL_FILE = " : Not the Excel file!";
	public static final String PROCESSING = "Processing...";
	public static final SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy.MM.dd");
	 
	 public static Date dateString2Date(String dateString){
		 Date date = null;
	     try {
	          date = (Date) simpleDateFormat.parse(dateString);//将这个字符型的时间转换成Date型的时间
	     } catch (ParseException e) {
	         e.printStackTrace();
	     }
	     return date;
	 }
	 /**
	     * get postfix of the path
	     * @param path
	     * @return
	     */
	    public static String getPostfix(String path) {
	        if (path == null || EMPTY.equals(path.trim())) {
	            return EMPTY;
	        }
	        if (path.contains(POINT)) {
	            return path.substring(path.lastIndexOf(POINT) + 1, path.length());
	        }
	        return EMPTY;
	    }
	    
	    @SuppressWarnings("static-access")
	    private static String getValue(XSSFCell xssfRow) {
	        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
	            return String.valueOf(xssfRow.getBooleanCellValue()).toString().trim();
	        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
	        	String posReg = "^-?[0-9]+(.[0]+)?$";
	        	Pattern posPattern = Pattern.compile(posReg);
	        	Matcher posMatcher = posPattern.matcher(String.valueOf(xssfRow.getNumericCellValue()));
	        	boolean posRs = posMatcher.matches();
	        	
	        	String sicenceNumReg = "^((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))$";
	        	Pattern sicenceNumPattern = Pattern.compile(sicenceNumReg);
	        	Matcher sicenceNumMatcher = sicenceNumPattern.matcher(String.valueOf(xssfRow.getNumericCellValue()));
	        	boolean sicenceNumRs = sicenceNumMatcher.matches();
	        	if(sicenceNumRs){
	        		String numericCellValueString = String.valueOf(xssfRow.getNumericCellValue());
	        		String sicenceNumOrdinatyFormat;
	        		int pointPos = numericCellValueString.indexOf(".");
	        		int ePos = 0;
	        		if(numericCellValueString.indexOf("e") != -1){
	        			ePos = numericCellValueString.indexOf("e");
	        		}else if(numericCellValueString.indexOf("E") != -1){
	        			ePos = numericCellValueString.indexOf("E");
	        		}else{//不可能进入这个分支
	        			ePos = 0;
	        		}
	        		sicenceNumOrdinatyFormat = numericCellValueString.substring(0, pointPos) + numericCellValueString.substring(pointPos+1, ePos);
	        		return sicenceNumOrdinatyFormat;
	        	}else if(posRs){
	        		String ind;
	        		int pointPos = String.valueOf(xssfRow.getNumericCellValue()).indexOf(".");
	        		ind = String.valueOf(xssfRow.getNumericCellValue()).substring(0, pointPos);
	        		return ind;
	        	}else{
	        		return String.valueOf(xssfRow.getNumericCellValue());
	        	}
	        } else {
	            return String.valueOf(xssfRow.getStringCellValue()).toString().trim();
	        }
	    }
	    
	    @SuppressWarnings("static-access")
	    private static String getValue(HSSFCell hssfCell) {
	        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
	            return String.valueOf(hssfCell.getBooleanCellValue());
	        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
	            return String.valueOf(hssfCell.getNumericCellValue());
	        } else {
	            return String.valueOf(hssfCell.getStringCellValue());
	        }
	    }
	    
	    /**
	     * read the Excel file
	     * @param path the path of the Excel file
	     * @return
	     * @throws IOException
	     */
	    public static List<List<String>> readExcel(String path) throws IOException {
	        if (path == null || EMPTY.equals(path)) {
	            return null;
	        } else {
	            String postfix = getPostfix(path);
	            if (!EMPTY.equals(postfix)) {
	                if (OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
	                    return readXls(path);
	                } else if (OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
	                    return readXlsx(path);
	                }
	            } else {
	                System.out.println(path + NOT_EXCEL_FILE);
	            }
	        }
	        return null;
	    }
	    
	    /**
	     * Read the Excel 2010
	     * @param path the path of the excel file
	     * @return
	     * @throws IOException
	     */
	    public static List<List<String>> readXlsx(String path) throws IOException {
	        System.out.println(PROCESSING + path);
	        InputStream is = new FileInputStream(path);
	        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
	        List<List<String>> result = new ArrayList<List<String>>();
	        // Read the Sheet
	       // for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
	        //    XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
	        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
	           // if (xssfSheet == null) {
	           //     continue;
	          //  }
	            // Read the Row
	            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum()-1; rowNum++) {
	                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
	                if (xssfRow != null) {
	                  int minColIndex = xssfRow.getFirstCellNum();
	                  int maxColIndex = xssfRow.getLastCellNum();
	                  List<String> rowList = new ArrayList<String>();
	                  for(int colIndex = minColIndex;colIndex < maxColIndex;colIndex++){
	                	  XSSFCell cell = xssfRow.getCell(colIndex);
		                  if(cell == null){
		                	  continue;
		                  }
		                  rowList.add(getValue(cell));
	                  }
	                  
	                  result.add(rowList);
	                }
	                
	            }
	        //}
	        return result;
	    }
	 
	    /**
	     * Read the Excel 2003-2007
	     * @param path the path of the Excel
	     * @return
	     * @throws IOException
	     */
	    public static List<List<String>> readXls(String path) throws IOException {
	        System.out.println(PROCESSING + path);
	        InputStream is = new FileInputStream(path);
	        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
	        List<List<String>> result = new ArrayList<List<String>>();
	        // Read the Sheet
	       // for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
	         //   HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
	        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
	         //   if (hssfSheet == null) {
	         //       continue;
	          //  }
	            // Read the Row
	            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
	                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
	                if (hssfRow != null) {
		                  int minColIndex = hssfRow.getFirstCellNum();
		                  int maxColIndex = hssfRow.getLastCellNum();
		                  List<String> rowList = new ArrayList<String>();
		                  for(int colIndex = minColIndex;colIndex < maxColIndex;colIndex++){
		                	  HSSFCell cell = hssfRow.getCell(colIndex);
			                  if(cell == null){
			                	  continue;
			                  }
			                  rowList.add(getValue(cell));
		                  }
		                  
		                  result.add(rowList);
		                }
	            }
	        //}
	        return result;
	    }
}
