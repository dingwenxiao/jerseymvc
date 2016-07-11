package dingwen.jersey.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceListUtil {

	private List<DeviceModel> deviceList;
	
	private Map<String, Boolean> responseParam = new HashMap<>();

	public void setResponseParam(Map<String, Boolean> map) {
		this.responseParam = map;
	}

	public List<DeviceModel> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<DeviceModel> deviceList) {
		this.deviceList = deviceList;
	}
	
}
