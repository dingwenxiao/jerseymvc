
package dingwen.jersey.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceList {
	@XmlElement
    private List<Device> device = new ArrayList<Device>();

	public List<Device> getDevice() {
		return device;
	}

	public void setDevice(List<Device> device) {
		this.device = device;
	}


}
