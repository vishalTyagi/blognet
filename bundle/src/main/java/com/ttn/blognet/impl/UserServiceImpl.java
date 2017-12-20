package com.ttn.blognet.impl;

import com.drew.lang.annotations.NotNull;
import com.ttn.blognet.dto.SimplePrincipal;
import com.ttn.blognet.service.UserService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import javax.jcr.PropertyType;
import javax.jcr.Session;
import javax.jcr.Value;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * Created by vishal on 18/12/17.
 */
@Component(immediate = true) @Service(UserService.class) public class UserServiceImpl implements UserService {

	@Reference private ResourceResolverFactory resourceResolverFactory;

	public boolean createUser(Map<String, String> requestMap) {
		Session adminSession = null;
		boolean flag = false;
		String userName = requestMap.get("email");
		try {
			ResourceResolver resolver = getResourceResolver();
			adminSession = resolver.adaptTo(Session.class);
			final UserManager userManager = resolver.adaptTo(UserManager.class);
			if (userManager.getAuthorizable(userName) == null) {
				User user = userManager.createUser(userName, requestMap.get("password"), new SimplePrincipal(userName), "/home/users/test");
				Value value = adminSession.getValueFactory().createValue(getCalenderDate(requestMap.get("dob")));
				user.setProperty("./profile/dob", value);
				value = adminSession.getValueFactory().createValue(requestMap.get("name"), PropertyType.STRING);
				user.setProperty("./profile/givenName", value);
				value = adminSession.getValueFactory().createValue(requestMap.get("gender"), PropertyType.STRING);
				user.setProperty("./profile/aboutMe", value);
				value = adminSession.getValueFactory().createValue(requestMap.get("email"), PropertyType.STRING);
				user.setProperty("./profile/email", value);
				flag = true;
			} else {
				flag = false;
			}
			Group group = (Group) (userManager.getAuthorizable("blog-reader"));
			group.addMember(userManager.getAuthorizable(userName));
			if (!userManager.isAutoSave()) {
				adminSession.save();
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@NotNull private Calendar getCalenderDate(String date) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
		try {
			calendar.setTime(dateFormat.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar;
	}

	private ResourceResolver getResourceResolver() throws LoginException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ResourceResolverFactory.SUBSERVICE, "userService");
		return resourceResolverFactory.getServiceResourceResolver(param);
	}

}
