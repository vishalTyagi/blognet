package com.ttn.blognet.servlet;

import com.ttn.blognet.service.UserService;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by vishal on 15/12/17.
 */
@SlingServlet(paths = "/bin/blognet/registration", extensions = "html", methods = "POST") public class RegistrationServlet extends SlingAllMethodsServlet {

	@Reference UserService userService;

	public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		String string = "";
		Map<String, String> requestMap = getRequestMap(request);
		boolean flag = userService.createUser(requestMap);
		if (flag) {
			string = "Welcome " + request.getParameter("name");
		} else {
			string = "User with username " + request.getParameter("email") + " exists.";
		}
		try {
			response.sendRedirect("/content/blognet/main.html?msg=" + string);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private HashMap<String, String> getRequestMap(SlingHttpServletRequest request) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", request.getParameter("email"));
		map.put("name", request.getParameter("name"));
		map.put("gender", request.getParameter("gender"));
		map.put("dob", request.getParameter("dob"));
		map.put("password", request.getParameter("password"));
		System.out.println("Password is  =====>> " + map.get("password"));
		return map;
	}
}
