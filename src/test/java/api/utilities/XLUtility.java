package api.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtility {
	
	private static final Logger logger = LogManager.getLogger(XLUtility.class);
//	public FileInputStream fi;
	public FileOutputStream fo;
	
//	public XSSFSheet sheet;
//	public XSSFRow row;
//	public XSSFCell cell;
//	public CellStyle style;
	private final String path;
	
	public XLUtility(String path) {
		this.path = path;
	}
	
	// Opens workbook — caller is responsible for closing
    private XSSFWorkbook openWorkbook(FileInputStream fi) throws IOException {
        return new XSSFWorkbook(fi);
    }
    
	public int getRowCount(String sheetName) throws IOException {
		FileInputStream fi = new FileInputStream(path);
		XSSFWorkbook workbook = openWorkbook(fi); 
		int rowCount = workbook.getSheet(sheetName).getLastRowNum();
		logger.info("Row Count in sheet ["+ sheetName+"]:" +rowCount);
		return rowCount;
	}
	
	public int getCellCount(String sheetName, int rownum) throws IOException {
		FileInputStream fi = new FileInputStream(path);
		XSSFWorkbook workbook = openWorkbook(fi);
		XSSFRow row = workbook.getSheet(sheetName).getRow(rownum);
		int cellCount = row.getLastCellNum();
		logger.info("Cell count in row ["+ rownum+ "]" + cellCount);
		return cellCount;
	}

	public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
		FileInputStream fi = new FileInputStream(path);
		XSSFWorkbook workbook = openWorkbook(fi);
		XSSFRow row = workbook.getSheet(sheetName).getRow(rownum);
		XSSFCell cell = row.getCell(colnum);
		String data;
		
		try {
			DataFormatter formatter = new DataFormatter();
			data = formatter.formatCellValue(cell);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.warn("Empty or null cell at row " + rownum + ",col" + colnum);
			data = "";
		}

		return data;
	}
	public void setCellData(String sheetName, int rownum, int colnum,String data) throws IOException {
		// Create File if it dosn't exist
		File xlfile = new File(path);
		if(!xlfile.exists()) { // if the file does not exists create new file
			XSSFWorkbook workbook = new XSSFWorkbook();
			FileOutputStream fo= new FileOutputStream(path);
			workbook.write(fo);
			logger.info("Created new Excel file at :" +path);
		}
		FileInputStream fi = new FileInputStream(path);
		XSSFWorkbook workbook = openWorkbook(fi);
		
		if(workbook.getSheetIndex(sheetName)==-1)
			workbook.createSheet(sheetName);
		
		XSSFSheet sheet = workbook.getSheet(sheetName);
		
		if(sheet.getRow(rownum)==null)
			sheet.createRow(rownum);
		
		XSSFRow row = sheet.getRow(rownum);
		
		XSSFCell cell = row.createCell(colnum);
		cell.setCellValue(data);
		FileOutputStream fo = new FileOutputStream(path);
		workbook.write(fo);
		logger.info("data written to [" + sheetName+ "] row:" + rownum + " col:"+ colnum);

	}
	
	// shared color fill method 
	private void fillcolor(String sheetName, int rownum, int colnum, IndexedColors color) throws IOException {
		FileInputStream fi = new FileInputStream(path);
		XSSFWorkbook workbook = openWorkbook(fi);
		
		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(color.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		workbook.getSheet(sheetName).getRow(rownum).getCell(colnum).setCellStyle(style);
		
		FileOutputStream fo = new FileOutputStream(path);
		workbook.write(fo);
		logger.info("Filled " + color.name() + " color at row:" + rownum+ " col:"+colnum);
	}
	public void fillGreenColor(String sheetName, int rownum,int colnum) throws IOException {
		fillcolor(sheetName, rownum, colnum, IndexedColors.GREEN);
	}
	public void fillRedColor(String sheetName, int rownum,int colnum) throws IOException {
		fillcolor(sheetName, rownum, colnum, IndexedColors.RED);
	}

}
