package selenium.Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelFunctions 
{
	
	public int getColumnNumber(String fileName, String sheetName, String columnValue) throws IOException
	{
		int columnNumber=0;
		int k=0;
		FileInputStream fis= new FileInputStream(fileName);
		
		XSSFWorkbook workBook = new XSSFWorkbook(fis);
		
		//1. Identify TestCase Column by scanning entire first Row
		
		int sheetCount = workBook.getNumberOfSheets();
		
			for(int i=0;i<sheetCount;i++)
			{
				if(workBook.getSheetName(i).equalsIgnoreCase(sheetName))
				{			
					XSSFSheet sheet= workBook.getSheetAt(i);	    
					Iterator<Row> rows = sheet.rowIterator(); //Sheet is collection of Rows
					Row firstRow= rows.next();
					Iterator<Cell> ce= firstRow.cellIterator(); // Row is collection of Cells
					while(ce.hasNext())
					{
						Cell cellValue= ce.next();
						if(cellValue.getStringCellValue().equalsIgnoreCase(columnValue))
						{
							columnNumber=k;
						}
						k++;
					}
				}
			}
		workBook.close();
		return columnNumber;
	}
	
	public int getRowNumber(String fileName, String sheetName, String columnValue, String rowValue) throws IOException
	{
		int rowNumber=0;
		int columnNumber=0;
		int k=0;
		int j=0;
		FileInputStream fis= new FileInputStream(fileName);
		
		XSSFWorkbook workBook = new XSSFWorkbook(fis);
		
		//1. Identify Column Number by scanning entire first Row
		
		int sheetCount = workBook.getNumberOfSheets();
		
			for(int i=0;i<sheetCount;i++)
			{
				if(workBook.getSheetName(i).equalsIgnoreCase(sheetName))
				{			
					XSSFSheet sheet= workBook.getSheetAt(i);	    
					Iterator<Row> rows = sheet.rowIterator(); //Sheet is collection of Rows
//					Row firstRow= rows.next();
//					Iterator<Cell> ce= firstRow.cellIterator(); // Row is collection of Cells
//					while(ce.hasNext())
//					{
//						Cell cellValue= ce.next();
//						if(cellValue.getStringCellValue().equalsIgnoreCase(columnValue))
//						{
//							columnNumber=k;
//						}
//						k++;
//					}
					columnNumber = getColumnNumber(fileName,sheetName,columnValue);
					while(rows.hasNext())
					{
						Row r=  rows.next();
						if(r.getCell(columnNumber).getStringCellValue().equalsIgnoreCase(rowValue))
						{
							rowNumber=j;					
						}
						j++;
					}
					
				}
			}
			workBook.close();
		return rowNumber;
	}
	
	public boolean updateExcelCell(String fileName, String sheetName, int columnNumber, int rowNumber, int fieldValue) throws IOException
	{
							
				FileInputStream fis= new FileInputStream(fileName);
				
				XSSFWorkbook workBook = new XSSFWorkbook(fis);
			
					int sheetCount = workBook.getNumberOfSheets();
				
					for(int i=0;i<sheetCount;i++)
					{
						if(workBook.getSheetName(i).equalsIgnoreCase(sheetName))
						{			
							XSSFSheet sheet= workBook.getSheetAt(i);
							System.out.println("Row Values : " + sheet.getRow(rowNumber));
							Row row= sheet.getRow(rowNumber);
							System.out.println("Column Values : " + row.getCell(columnNumber));
							Cell ce = row.getCell(columnNumber);
							ce.setCellValue(fieldValue);
						}
					}
					FileOutputStream fos = new FileOutputStream(fileName);
		            workBook.write(fos);
		            fos.close();
		            workBook.close();
		            return true;
	}
	
	public ArrayList<String> getExcelData(String sheetName, String testCaseName) throws IOException
	{
		
	        	//1. Identify TestCase Column by scanning entire first Row
				//2. Once column is identified, scan entire column to identify a row for TC for which Test data is required
				//3. Once TC row is identified, scan entire cells for that row to fetch required test data.
				
				ArrayList<String> a = new ArrayList<String>();
						
				FileInputStream fis= new FileInputStream("/Users/santoshkedar/Documents/TestData.xlsx");
				
				XSSFWorkbook workBook = new XSSFWorkbook(fis);
				
				//1. Identify TestCase Column by scanning entire first Row
				
				int sheetCount = workBook.getNumberOfSheets();
				
				for(int i=0;i<sheetCount;i++)
				{
					
					if(workBook.getSheetName(i).equalsIgnoreCase(sheetName))
					{
						System.out.println(i);
						
						XSSFSheet sheet= workBook.getSheetAt(i);
						
						System.out.println(sheet.getSheetName());
					    
						Iterator<Row> rows = sheet.rowIterator(); //Sheet is collection of Rows
						Row firstRow= rows.next();
						Iterator<Cell> ce= firstRow.cellIterator(); // Row is collection of Cells
						int k=0;
						int column=0;
				
						while(ce.hasNext())
						{
							Cell cellValue= ce.next();
						//	System.out.println(cellValue.getStringCellValue());
							if(cellValue.getStringCellValue().equalsIgnoreCase("Testcases"))
							{
								column=k;
							}
							k++;
						}
						System.out.println(column);
						
					  //2. Once column is identified, scan entire column to identify a row for TC for which Test data is required
						
						while(rows.hasNext())
						{
							Row r=  rows.next();
							if(r.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName))
							{
								//3. Once TC row is identified, scan entire cells for that row to fetch required test data.
								
								Iterator<Cell> cv= r.iterator();
								
								while(cv.hasNext())
								{
									Cell c = cv.next();
									if(c.getCellType()==CellType.STRING)
									{
										a.add(c.getStringCellValue());
									}else
									{
										a.add(NumberToTextConverter.toText(c.getNumericCellValue()));
									}
								}
								
							}
						}
					}
				}
				return a;
		}
		
		
//		public Object[][] getData(String fileName, String sheetName) throws IOException
//		{
//			
//					DataFormatter formatter = new DataFormatter();
//					FileInputStream fis= new FileInputStream(fileName);
//					XSSFWorkbook workBook = new XSSFWorkbook(fis);
//					
//					int sheetCount = workBook.getNumberOfSheets();
//					
//					for(int i=0;i<sheetCount;i++)
//					{
//						
//						if(workBook.getSheetName(i).equalsIgnoreCase(sheetName))
//						{
//							XSSFSheet sheet= workBook.getSheetAt(i);
//							int rowCount = sheet.getPhysicalNumberOfRows();
//							XSSFRow row = sheet.getRow(0);
//							int columnCount = row.getLastCellNum();
//							
//							Object data[][] = new Object[rowCount-1][columnCount];
//							
//							for(int k=0;i<rowCount-1;k++)
//							{
//								row = sheet.getRow(i+1);
//								
//								for(int j=0;j<columnCount;j++)
//								{
//									XSSFCell cell = row.getCell(j);
//									data[i][j] = formatter.formatCellValue(cell);
//								}
//								
//							}
//						}
//					}
//				workBook.close();
//				return data;
//		}


}
