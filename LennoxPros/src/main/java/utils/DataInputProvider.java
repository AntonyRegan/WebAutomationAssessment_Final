package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DataInputProvider {
	static XSSFWorkbook workbook = null;
	static XSSFSheet sheet = null;
	static XSSFRow row = null;
	static XSSFCell cell = null;
	FileInputStream fis;

	public DataInputProvider(String dataSheetName) {

		try {
			fis = new FileInputStream("./data/TestData.xlsx");
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(dataSheetName);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Object[][] getTestData() {
		Object[][] data = null;

		//sheet = workbook.getSheet(dataSheetName);

		// get the number of rows
		int rowCount = sheet.getLastRowNum();
		// get the number of columns
		int columnCount = sheet.getRow(0).getLastCellNum();

		data = new String[rowCount][columnCount];

		for (int i = 1; i <= rowCount; i++) {
			row = (XSSFRow) sheet.getRow(i);
			for (int j = 0; j < columnCount; j++) {
				String cellValue = "";
				cellValue = row.getCell(j).getStringCellValue();
				data[i - 1][j] = cellValue;
				System.out.println("Cell Value == " + cellValue);
			}
		}

		return data;
	}

	public String getCellData(String colName, String runningTestcase) {

		//sheet = workbook.getSheet(dataSheetName);

		int rowCount = sheet.getLastRowNum();
		int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
		String returnValue = "";
		char lastChar = runningTestcase.charAt(runningTestcase.length()-1);
		int testCaseNo = Character.getNumericValue(lastChar);
		System.out.println("Test case count ==== "+runningTestcase.charAt(runningTestcase.length()-1));

		outerloop: for (int i = 0; i <= rowCount; i++) {
			row = sheet.getRow(i);
			System.out.println("Row value ==" + i + " " + sheet.getRow(i).getCell(2).getStringCellValue());

			if (sheet.getRow(i).getCell(2).getStringCellValue().equalsIgnoreCase(runningTestcase)) {
				for (int j = 0; j <= colCount; j++) {
					System.out.println("Row No == "+i);
					System.out.println("test case no == "+testCaseNo);
					String colHeader = sheet.getRow(i-testCaseNo).getCell(j).getStringCellValue();
					String cellValue = sheet.getRow(i).getCell(j).getStringCellValue();
					System.out.println("Column header == " + colHeader);
					System.out.println("Column value == " + cellValue);
					if (colHeader.equals(colName)) {
						returnValue = cellValue;
						break outerloop;
					}
				}
			}
		}

		return returnValue;
	}
}
