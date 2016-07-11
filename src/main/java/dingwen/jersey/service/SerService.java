package dingwen.jersey.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dingwen.jersey.dao.AssociationDao;
import dingwen.jersey.dao.DeviceDao;
import dingwen.jersey.dao.ServiceDao;
import dingwen.jersey.dao.SubscribeDao;
import dingwen.jersey.entity.Association;
import dingwen.jersey.entity.DeviceModel;
import dingwen.jersey.entity.DeviceModelList;
import dingwen.jersey.entity.Entity;
import dingwen.jersey.entity.ServiceAttribute;
import dingwen.jersey.entity.ServiceAttributeModel;
import dingwen.jersey.entity.ServiceModel;
import dingwen.jersey.entity.Subscribe;
import dingwen.jersey.utilities.ResponseStatusConstant;

public class SerService {

	private static AssociationDao associationDao = new AssociationDao();
	private static DeviceDao deviceDao = new DeviceDao();
	private static ServiceDao serviceDao = new ServiceDao();
	private static SubscribeDao subscribeDao = new SubscribeDao();
	private final static Logger log = LogManager.getLogger(SerService.class);

	public static Response associateSIDtoPin(Entity entity, int userId) throws ServletException, IOException {
		boolean isSuccess = false;
		Set<ServiceAttributeModel> serviceAttributes = new HashSet<>();
		// verify ecoid and pin
		if (entity != null && (entity.getEcoID() != null && !entity.getEcoID().equals(""))
				&& (entity.getDevice() != null)) {
			if (entity.getDevice().getPIN() != null && entity.getDevice().getPIN() != null) {

				List<Association> associationList = associationDao.getAssociationBySerIdPINUserId(
						entity.getService().getServiceID(), entity.getDevice().getPIN(), userId);

				if (associationList.isEmpty()) {
					// if the association doesn't exist, then create a
					// association
					log.debug("association is empty");
					// check if the pin and ecoId are associated
					List<Subscribe> subscribeList = subscribeDao.querySubscribe(entity.getEcoID(),
							entity.getDevice().getPIN(), userId);

					// if empty then it's inactive, otherwise active
					boolean isActive = !subscribeList.isEmpty();
					log.debug("active state: {}", isActive);

					Association association = new Association();
					association.setServiceName(entity.getService().getServiceName());
					association.setPin(entity.getDevice().getPIN());
					association.setServiceId(entity.getService().getServiceID());
					association.setUserId(userId);
					association.setActive(isActive);
					tranferServiceAttrToModel(entity, association, serviceAttributes, true);
					association.setServiceAttributes(serviceAttributes);
					isSuccess = associationDao.insertAssociationAndAttr(association);

				} else if (!entity.getService().getServiceAttributes().isEmpty()) {
					log.debug("association is not empty, then set service attributes");
					// association is already established, and only set
					// attributes
					tranferServiceAttrToModel(entity, associationList.get(0), serviceAttributes, false);
					isSuccess = associationDao.insertAttrByAssociation(associationList.get(0), serviceAttributes);
				}
			}

		}
		return Response.noContent().build();
	}

	/**
	 * set service attributes reference to association
	 * 
	 * @param entity
	 * @param association
	 * @param serviceAttributes
	 */
	private static void tranferServiceAttrToModel(Entity entity, Association association,
			Set<ServiceAttributeModel> serviceAttributes, boolean referenceToAssociation) {
		for (ServiceAttribute serviceAttribute : entity.getService().getServiceAttributes()) {
			ServiceAttributeModel serviceAttributeModel = new ServiceAttributeModel();
			serviceAttributeModel.setAttrName(serviceAttribute.getName());
			serviceAttributeModel.setAttrValue(serviceAttribute.getValue());
			if (referenceToAssociation) {
				serviceAttributeModel.setAssociation(association);
			}
			serviceAttributes.add(serviceAttributeModel);
		}
	}

	public static Response getDevicesByServiceID(MultivaluedMap<String, String> queryParams,
			MultivaluedMap<String, String> pathParams, int userId) throws ServletException, IOException {// /intersect/service/BBM/xx5678Wtr/device?type&msisdn
		List<DeviceModel> deviceList = new ArrayList<>();
		List<ServiceModel> servicesExists = serviceDao.queryServiceById(pathParams.getFirst("serviceID"));
		if (servicesExists.isEmpty()) {
			// no pin record return not found
			log.debug("service doesn't exist, return not found");
			return Response.status(ResponseStatusConstant.Not_Found).build();
		}

		List<Association> associationList = associationDao
				.getAssociationsByServiceId(pathParams.getFirst("serviceID"), userId);

		for (Association association : associationList) {
			if (association.isActive()) {// only looking for active association
				deviceList.add(deviceDao.queryList(association.getPin()).get(0));
			}
		}
		DeviceModelList device = new DeviceModelList();
		device.setDevice(deviceList);
		if (deviceList.isEmpty()) {
			log.debug("no device is active");
			return Response.noContent().build();
		} else {
			return Response.ok(device, MediaType.APPLICATION_JSON).build();
		}
	}

	public static String toJson(List<DeviceModel> deviceList, MultivaluedMap<String, String> queryParams) {
		StringBuilder returnBuffer = new StringBuilder();
		returnBuffer.append("{\"device\":[");

		for (int i = 0; i < deviceList.size(); i++) {
			DeviceModel device = deviceList.get(i);
			if (i != 0) {
				returnBuffer.append(",");
			}
			returnBuffer.append("{\"PIN\":\"");
			returnBuffer.append(device.getPIN());
			returnBuffer.append("\"");
			if (queryParams.get("type") != null && device.getType() != null) {
				returnBuffer.append(",\"type\":\"");
				returnBuffer.append(device.getType());
				returnBuffer.append("\"");
			}
			if (queryParams.get("swVer") != null && device.getVerReliable() != null) {
				returnBuffer.append("\",\"swVersion\":{\"osVer\":\"");
				returnBuffer.append(device.getOsVer());
				returnBuffer.append("\",\"appVer\":\"");
				returnBuffer.append(device.getAppVer());
				returnBuffer.append("\",\"reliable\":\"");
				returnBuffer.append(device.getVerReliable());
				returnBuffer.append("\"}");
			}
			if (queryParams.get("carrier") != null && device.getSapSoldTo() != null) {
				returnBuffer.append("\",\"carrier\":{\"sapSoldTo\":\"");
				returnBuffer.append(device.getSapSoldTo());
				returnBuffer.append("\",\"alias\":\"");
				returnBuffer.append(device.getAlias() == null ? "" : device.getAlias());
				returnBuffer.append("\"}");
			}
			if (queryParams.get("msisdn") != null && device.getMisisdnValue() != null) {
				returnBuffer.append("\",\"msisdn\":{\"misisdnValue\":\"");
				returnBuffer.append(device.getMisisdnValue());
				returnBuffer.append("\",\"reliable\":\"");
				returnBuffer.append(device.isMisReliable() == null ? "" : device.isMisReliable());
				returnBuffer.append("\"}");
			}
			returnBuffer.append("}");
		}

		returnBuffer.append("]}");
		return returnBuffer.toString();
	}

}
