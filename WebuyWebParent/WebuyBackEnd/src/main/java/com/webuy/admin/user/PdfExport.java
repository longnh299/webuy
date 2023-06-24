package com.webuy.admin.user;

import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.StaleObjectStateException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.webuy.common.entity.User;

import jakarta.servlet.http.HttpServletResponse;

public class PdfExport implements ExportInterface {

	@Override
	public void exportInfo(List<User> listUsers, HttpServletResponse response) throws DocumentException, IOException {
		// TODO Auto-generated method stub
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		
		String time = dateFormat.format(new Date());
		
		String file = "users_info_" + time + ".pdf";
	
		response.setContentType("application/pdf");
		
		String headerkey = "Content-Disposition";
		String headervalue = "attachment; filename=" + file;
		
		response.setHeader(headerkey, headervalue);
		
		Document doc = new Document(PageSize.A4);
		PdfWriter.getInstance(doc, response.getOutputStream());
		doc.open();
		Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
		font.setSize(16);
		font.setColor(Color.BLACK);
		
		Paragraph para = new Paragraph("List of users at webuy", font);
		para.setAlignment(Paragraph.ALIGN_CENTER);
		doc.add(para);
		
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100f);
		float[] columnWidth = {2.0f, 3.5f, 3.0f, 3.0f, 3.5f, 2.0f}; 
		table.setWidths(columnWidth);
		table.setSpacingBefore(10);
		
		writeTablePdfHeader(table);
		writeTablePdfData(table, listUsers);
		
		doc.add(table);
		
		doc.close();
		
		
	}

	//method: write data of pdf file 
	public void writeTablePdfData(PdfPTable table, List<User> listUsers) {
		
		for(User user : listUsers) {
			table.addCell(String.valueOf(user.getId()));
			table.addCell(user.getEmail());
			table.addCell(user.getFirstName());
			table.addCell(user.getLastName());
			table.addCell(user.getRoles().toString());
			table.addCell(String.valueOf(user.isEnabled()));
		}
	}

	// method: write header of pdf file
	public void writeTablePdfHeader(PdfPTable table) {
		// TODO Auto-generated method stub
		PdfPCell cell = new PdfPCell();
		//cell.setBackgroundColor(null);
		cell.setPadding(4);
		
		Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
		font.setSize(14);
		font.setColor(Color.BLACK);
		
		cell.setPhrase(new Phrase("User ID", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Email", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("First Name", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Last Name", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Roles", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Enabled", font));
		table.addCell(cell);
		
		
		
		
	}
	
	

}
