package dingwen.jersey.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dingwen.jersey.entity.Entity;
import dingwen.jersey.entity.Setting;
import dingwen.jersey.filter.annotation.BasicAuth;
import dingwen.jersey.service.SerService;

@Path("/service")
@BasicAuth
public class ServiceRequestController {
	private final static Logger log = LogManager.getLogger(ServiceRequestController.class);
	@Context
	HttpServletRequest webRequest;

	@Context
	HttpServletResponse webResponse;

	@GET
	@Path("/{serviceName}/{serviceID}/device")
	@Produces({ "application/xml", "application/json" })
	public Response getDevIDwithSerID(@Context UriInfo ui

	) {
		MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
		MultivaluedMap<String, String> pathParams = ui.getPathParameters();

		Map<String, Setting> settingMap = (Map<String, Setting>) webRequest.getSession().getAttribute("settingMap");
		if (!settingMap.isEmpty() && queryParams.isEmpty()) {
			if (settingMap.containsKey("association exists")) {
				Setting setting = settingMap.get("association exists");
				if (setting.getDelay() != null && !setting.getDelay().equals(0)) {
					try {
						Thread.sleep(setting.getDelay());
					} catch (InterruptedException e) {
						log.error(e);
					}
				}

				if (setting.getResponseCode() != 0) {
					return Response.status(setting.getResponseCode()).build();
				}
			}
		}

		Response response = null;
		try {
			response = SerService.getDevicesByServiceID(queryParams, pathParams,
					(Integer) webRequest.getSession().getAttribute("userId"));
		} catch (ServletException | IOException e) {
			log.error(e);
		}

		return response;
	}

	@POST
	@Consumes({ "application/xml", "application/json" })
	public Response assosiateSIDtoPin(Entity entity) {
		Map<String, Setting> settingMap = (Map<String, Setting>) webRequest.getSession().getAttribute("settingMap");
		if (!settingMap.isEmpty()) {
			if (entity.getService().getServiceAttributes().get(0) // association
																	// request
					.getValue().equals("false")) {

				// user set this api response
				if (settingMap.containsKey("association")) {
					Setting setting = settingMap.get("association");

					// check if the user gets delay
					if (setting.getDelay() != null && !setting.getDelay().equals(0)) {
						try {
							Thread.sleep(setting.getDelay());
							log.debug("association delay for ..",setting.getDelay());
						} catch (InterruptedException e) {
							log.error(e);
						}
					}

					// reture response code
					if (setting.getResponseCode() != 0) {
						return Response.status(setting.getResponseCode()).build();
					}
				}

				// set association attributes request
			} else if (entity.getService().getServiceAttributes().get(0).getValue().equals("true")) {

				// check if user set this api response
				if (settingMap.containsKey("Set stolen attributes")) {
					Setting setting = settingMap.get("Set stolen attributes");

					// check delay
					if (setting.getDelay() != null && !setting.getDelay().equals(0)) {
						try {
							log.debug("Set stolen attributes delay for ..",setting.getDelay());
							Thread.sleep(setting.getDelay());
						} catch (InterruptedException e) {
							log.error(e);
						}
					}

					// return response code
					if (setting.getResponseCode() != 0) {
						return Response.status(setting.getResponseCode()).build();
					}
				}
			}
		}

		Response response;
		try {
			int userId = (Integer) webRequest.getSession().getAttribute("userId");
			response = SerService.associateSIDtoPin(entity, userId);
		} catch (ServletException | IOException e) {
			log.error(e);
			response = Response.serverError().build();
		}
		return response;
	}
}
