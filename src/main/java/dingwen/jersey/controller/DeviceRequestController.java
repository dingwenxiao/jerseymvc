package dingwen.jersey.controller;

import java.io.IOException;
import java.util.Map;

import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dingwen.jersey.entity.Setting;
import dingwen.jersey.filter.annotation.BasicAuth;
import dingwen.jersey.service.DeviceService;
import dingwen.jersey.utilities.ResponseStatusConstant;


@Singleton
@Path("/device")
@BasicAuth
public class DeviceRequestController {
	private final static Logger log = LogManager
			.getLogger(DeviceRequestController.class);

	@Context
	HttpServletRequest webRequest;

	@GET
	@Path("/{PIN}/service/{serviceName}")
	@Produces({ "application/xml", "application/json" })
	public Response getServicesWithDevice(
			@PathParam("PIN") String pin,
			@PathParam("serviceName") String serviceName,
			@DefaultValue("false") @QueryParam("allExisting") boolean allExisting,
			@DefaultValue("false") @QueryParam("serviceAttributes") boolean serviceAttributes

	) {
		Map<String, Setting> settingMap = (Map<String, Setting>) webRequest
				.getSession().getAttribute("settingMap");
		if (!settingMap.isEmpty()) {
			if (settingMap.containsKey("Get stolen attributes")) {
				Setting setting = settingMap.get("Get stolen attributes");
				if (setting.getDelay() != null && !setting.getDelay().equals(0)) {
					try {
						log.debug("Delay for ...", setting.getDelay());
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
			response = DeviceService.getServiceByDev(pin, allExisting,
					serviceAttributes,(Integer)webRequest.getSession().getAttribute("userId"));
		} catch (ServletException | IOException e) {
			response = Response.status(
					ResponseStatusConstant.Internal_Server_Error).build();
			log.error(e);
		}
		return response;
	}

	@DELETE
	@Path("/{PIN}/{serviceID}")
	public Response deleteAssociation(@PathParam("PIN") String pin,
			@PathParam("serviceID") String serviceId,
			@DefaultValue("") @QueryParam("svcname") String serviceName) {

		Map<String, Setting> settingMap = (Map<String, Setting>) webRequest
				.getSession().getAttribute("settingMap");
		if (!settingMap.isEmpty()) {
			if (settingMap.containsKey("dissociation")) {
				Setting setting = settingMap.get("dissociation");
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
		int userId = (Integer) webRequest.getSession().getAttribute("userId");
		try {
			response = "".equals(serviceName) ? Response.status(
					ResponseStatusConstant.Bad_Request).build() : DeviceService
					.diassosiateServIDtoPin(serviceId, pin, userId);
		} catch (ServletException | IOException e) {
			response = Response.status(
					ResponseStatusConstant.Internal_Server_Error).build();
			log.error(e);
		}
		return response;
	}
}
