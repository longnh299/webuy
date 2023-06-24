package com.webuy.admin.user;

import java.awt.RenderingHints.Key;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.webuy.common.entity.User;

import jakarta.servlet.http.HttpServletResponse;

//export csv service
public class CsvExport {
	
	public void exportInfo(List<User> listUsers, HttpServletResponse response) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		
		String time = dateFormat.format(new Date());
		
		String file = "users_info_" + time + ".csv";
	
		response.setContentType("text/csv");
		
		String headerkey = "Content-Disposition";
		String headervalue = "attachment; filename=" + file;
		
		response.setHeader(headerkey, headervalue);
		
		ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeadArray = {"User ID", "Email", "First Name", "Last Name", "Roles", "Enabled"};
		String[] attributesMapping = {"id", "email", "firstName", "lastName", "roles", "enabled"};
		
		csvBeanWriter.writeHeader(csvHeadArray);
		
		for (User user : listUsers) {
			csvBeanWriter.write(user, attributesMapping);
		}
		
		csvBeanWriter.close(); // dong response
	}

}
