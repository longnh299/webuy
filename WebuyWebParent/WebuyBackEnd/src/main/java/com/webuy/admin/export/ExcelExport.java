package com.webuy.admin.export;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.webuy.common.entity.User;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class ExcelExport implements ExportInterface {
	
	private XSSFWorkbook wb;
	private XSSFSheet sheet;
	
	public ExcelExport() {
		wb = new XSSFWorkbook();
	}
	
	public void writeHeader() {
		sheet = wb.createSheet("Users");
		XSSFRow row = sheet.createRow(0);
		
		XSSFCellStyle cellStyle = wb.createCellStyle();
		XSSFFont font = wb.createFont();
		font.setBold(true);
		font.setFontHeight(14);
		cellStyle.setFont(font);
		
		createCellExcel(row, 0, "User ID", cellStyle);
		createCellExcel(row, 1, "Email", cellStyle);
		createCellExcel(row, 2, "First Name", cellStyle);
		createCellExcel(row, 3, "Last Name", cellStyle);
		createCellExcel(row, 4, "Roles", cellStyle);
		createCellExcel(row, 5, "Enabled", cellStyle);
	}
	
	public void createCellExcel(XSSFRow row, int columnIndex, Object value, CellStyle cellStyle) {
		XSSFCell cell = row.createCell(columnIndex);
		sheet.autoSizeColumn(columnIndex);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		
		cell.setCellStyle(cellStyle);
	}
	
	@Override
	public void exportInfo(List<User> listUsers, HttpServletResponse response) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		
		String time = dateFormat.format(new Date());
		
		String file = "users_info_" + time + ".xlsx";
	
		response.setContentType("application/octet-stream");
		
		String headerkey = "Content-Disposition";
		String headervalue = "attachment; filename=" + file;
		
		response.setHeader(headerkey, headervalue);	
		
		writeHeader();
		writeData(listUsers);
		ServletOutputStream outputStream = response.getOutputStream();
		wb.write(outputStream);
		wb.close();
		outputStream.close();
		
	}

	public void writeData(List<User> listUsers) {
		
		int rowIndex = 1;
		
		XSSFCellStyle cellStyle = wb.createCellStyle();
		XSSFFont font = wb.createFont();
		font.setFontHeight(12);
		cellStyle.setFont(font);
		
		for(User user : listUsers) {
			
			XSSFRow row = sheet.createRow(rowIndex);
			rowIndex++;
			int columnIndex = 0;
			createCellExcel(row, columnIndex++, user.getId(), cellStyle);
			createCellExcel(row, columnIndex++, user.getEmail(), cellStyle);
			createCellExcel(row, columnIndex++, user.getFirstName(), cellStyle);
			createCellExcel(row, columnIndex++, user.getLastName(), cellStyle);
			createCellExcel(row, columnIndex++, user.getRoles().toString(), cellStyle);
			createCellExcel(row, columnIndex++, user.isEnabled(), cellStyle);
		}
		
	}

}
