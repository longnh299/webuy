package com.webuy.admin.export;

import java.io.IOException;
import java.util.List;

import com.webuy.common.entity.User;

import jakarta.servlet.http.HttpServletResponse;

public interface ExportInterface {
	
	public void exportInfo(List<User> listUsers, HttpServletResponse response) throws IOException;

}
