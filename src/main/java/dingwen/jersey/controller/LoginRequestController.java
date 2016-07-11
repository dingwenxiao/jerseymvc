package dingwen.jersey.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.mvc.Viewable;

import dingwen.jersey.entity.User;
import dingwen.jersey.service.SettingService;
import dingwen.jersey.service.UserService;

@Path("/login")
public class LoginRequestController {
	
	private final static String ERROR = "user name or password invalid";
	private final static Logger log = LogManager
			.getLogger(LoginRequestController.class);
	private final int default_pageNum = 0;
	private final int defatult_pageSize = 10;

	@Context
	HttpServletRequest webRequest;
	
	@POST
	public Viewable login(@FormParam(value = "userName") String userName,
			@FormParam(value = "password") String password) {
		Map<String, Object> map = new HashMap<>();
		try {
			if ((userName != null && !"".equals(userName))
					&& (password != null && !"".equals(password))) {
				User user = UserService.getUser(userName, password);
				if (user != null) {
					webRequest.getSession().setAttribute("userID", user.getUserId());
					map.put("userID", user.getUserId());
					map.put("operationList", SettingService
							.getAvailableOperations(default_pageNum,
									defatult_pageSize));
					map.put("responseList", SettingService.getAllResponseType());
					map.put("settingMap",
							SettingService.getUserSettings(user.getUserId()));
					return new Viewable("/WEB-INF/jsp/exception_setting", map);
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		map.put("error", ERROR);
		return new Viewable("/login",map);
	}
	
}
