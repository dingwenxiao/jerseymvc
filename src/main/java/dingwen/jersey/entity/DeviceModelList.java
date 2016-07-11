
package dingwen.jersey.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceModelList {
	@XmlElement
    private List<DeviceModel> device = new ArrayList<>();

	public List<DeviceModel> getDevice() {
		return device;
	}

	public void setDevice(List<DeviceModel> device) {
		this.device = device;
	}
}
