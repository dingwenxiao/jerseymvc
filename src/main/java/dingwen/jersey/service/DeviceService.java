package dingwen.jersey.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dingwen.jersey.dao.AssociationDao;
import dingwen.jersey.dao.DeviceDao;
import dingwen.jersey.dao.ServiceDao;
import dingwen.jersey.entity.Association;
import dingwen.jersey.entity.DeviceModel;
import dingwen.jersey.entity.ServiceAttributeModel;
import dingwen.jersey.entity.ServiceModel;
import dingwen.jersey.utilities.ResponseStatusConstant;

/**
 * device service
 * 
 * @author dixiao
 *
 */
public class DeviceService {

	private static AssociationDao associationDao = new AssociationDao();
	private static ServiceDao serviceDao = new ServiceDao();
	private static DeviceDao deviceDao = new DeviceDao();
	private final static Logger log = LogManager.getLogger(DeviceService.class);

	public static Response diassosiateServIDtoPin(final String serviceId,
			final String pin, int userId) throws ServletException, IOException {

		List<DeviceModel> deviceList = deviceDao.queryList(pin);
		List<ServiceModel> serviceList = serviceDao.queryServiceById(serviceId);
		if (deviceList.isEmpty() || serviceList.isEmpty()) {
			return Response.status(ResponseStatusConstant.Not_Found).build();
		}

		List<Association> association = associationDao.getAssociationBySerIdPINUserId(serviceId, pin,
				userId);
		if (association.isEmpty()) {
			return Response.noContent().build();
		}

		//association.setActive(false);
		if (associationDao.disassociation(association.get(0))) {
			log.debug("dissociation success");
			return Response.noContent().build();
		} else {
			log.error("Internal error");
			return Response.serverError().build();
		}
	}

	public static Response getServiceByDev(final String pin,
			final boolean allExisting, final boolean serviceAttributes, final int userId)
			throws ServletException, IOException {
		Boolean isContainActive = new Boolean(false);
		List<DeviceModel> deviceList = deviceDao.queryList(pin);
		if(deviceList.isEmpty()) {
			//no devices found return 404
			log.debug("no devices found");
			return Response.status(ResponseStatusConstant.Not_Found).build();
		}
		
		List<Association> associationList = associationDao
				.getAssociationsByPin(pin,userId);
		if (associationList.isEmpty()) {
			// no such association, but the pin exists
			log.debug("no such association, but the pin exists");
			return Response.noContent().build();
		}

		String resJson = toJson(associationList, allExisting,
				serviceAttributes, isContainActive);
		
		if (!resJson.contains("serviceID")) {// no active association
			log.debug("no active association");
			return Response.noContent().build();
		}

		if (resJson != null) {
			log.debug("OK");
			return Response.ok().entity(resJson).build();
		} else {
			log.error("Internal error");
			return Response
					.status(ResponseStatusConstant.Internal_Server_Error)
					.build();
		}
	}

	public static String toJson(final List<Association> associationList,
			final boolean allExisting, final boolean serviceAttributes,
			Boolean isContainActive) {
		StringBuilder returnBuffer = new StringBuilder();
		returnBuffer.append("{\"service\":[");

		for (int i = 0; i < associationList.size(); i++) {
			Association association = associationList.get(i);
			if (!association.isActive()) {
				continue;
			}
			if (i != 0) {
				returnBuffer.append(",");
			}
			returnBuffer.append("{\"serviceID\":\"");
			returnBuffer.append(association.getServiceId());
			returnBuffer.append("\",\"serviceName\":\"");
			returnBuffer.append(association.getServiceName());
			returnBuffer.append("\"");
			if (allExisting) {
				returnBuffer.append(",\"serviceState\":\"");
				returnBuffer.append(association.isActive() ? "active"
						: "inactive");
				returnBuffer.append("\"");
			}

			if (serviceAttributes) {
				returnBuffer.append(",\"serviceAttributes\":");
				List<ServiceAttributeModel> attrList = serviceDao
						.getServiceAttributesByAssociation(association);
				if (attrList != null && attrList.size() > 1) {
					returnBuffer.append("[");
				}
				for (int j = 0; attrList != null && j < attrList.size(); j++) {
					if (j != 0) {
						returnBuffer.append(",");
					}
					returnBuffer.append("{\"name\":\"");
					returnBuffer.append(attrList.get(j).getAttrName());
					returnBuffer.append("\",");
					returnBuffer.append("\"value\":\"");
					returnBuffer.append(attrList.get(j).getAttrValue());
					returnBuffer.append("\"}");
				}

				if (attrList != null && attrList.size() > 1) {
					returnBuffer.append("]");
				}
			}
			returnBuffer.append("}");
		}

		returnBuffer.append("]}");
		return returnBuffer.toString();
	}
}
