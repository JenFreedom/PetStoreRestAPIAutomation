package api.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

public class DataProviders {
	private static final Logger logger = LogManager.getLogger(DataProviders.class);
	private static final String SHEET_NAME = "Sheet1";
	private static final String File_PATH = System.getProperty("user.dir")+File.separator + "testData"
				+File.separator + "UserData.xlsx";
	
	/// shared utility method 
	private XLUtility getXLUtility() throws IOException{
		logger.info("Loading Excel file from :" +File_PATH);
		return new XLUtility(File_PATH);
	}
	
	@DataProvider (name="Data")
	public String[][] getAllData() throws IOException{
		XLUtility xl = getXLUtility();
		
		int rowCount = xl.getRowCount(SHEET_NAME);
		int colCount = xl.getCellCount(SHEET_NAME,1);
		
		String apidata[][] = new String[rowCount][colCount];
		
		for(int i=1;i<=rowCount;i++) {
			for (int j=0;j<colCount;j++) {
				apidata[i-1][j] = xl.getCellData(SHEET_NAME, i, j);
			}
		}
		return apidata;
	}
	@DataProvider(name="UserNames")
	public String [] getUserNames() throws IOException {
		XLUtility xl = getXLUtility();
		
		int rowCount = xl.getRowCount(SHEET_NAME);
		
		String apidata[]=new String[rowCount];
		
		for(int i=1;i<=rowCount;i++) {
			apidata[i-1]=xl.getCellData(SHEET_NAME, i, 1);
		}
		return apidata;
		
	}
}
