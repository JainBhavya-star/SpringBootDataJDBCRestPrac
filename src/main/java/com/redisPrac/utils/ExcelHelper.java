package com.redisPrac.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.redisPrac.entity.Student;

public class ExcelHelper {

	 public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	    // Validate if the uploaded file is an Excel format (.xlsx)
	    public static boolean hasExcelFormat(MultipartFile file) {
	        return TYPE.equals(file.getContentType());
	    }

	    public static List<Student> excelToProducts(InputStream is) {
	        try {
	            Workbook workbook = new XSSFWorkbook(is);
	            Sheet sheet = workbook.getSheetAt(0); // Read the first sheet
	            Iterator<Row> rows = sheet.iterator();
	            List<Student> students = new ArrayList<>();

	            int rowNumber = 0;
	            while (rows.hasNext()) {
	                Row currentRow = rows.next();

	                // Skip header row
	                if (rowNumber == 0) {
	                    rowNumber++;
	                    continue;
	                }

	                Student s = new Student();
	                s.setId((int) currentRow.getCell(0).getNumericCellValue());
	                s.setName(currentRow.getCell(1).getStringCellValue());
	                s.setCity(currentRow.getCell(2).getStringCellValue());

	                students.add(s);
	            }
	            workbook.close();
	            return students;
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
	        }
	    }
}
