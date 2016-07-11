package dingwen.jersey.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Device {
	@XmlElement
	@JsonProperty("PIN")
	private String PIN;

	@XmlElement
	private String type;

	@XmlElement
	private Msisdn msisdn;



	public String getPIN() {
		return PIN;
	}

	public void setPIN(String pIN) {
		PIN = pIN;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Msisdn getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(Msisdn msisdn) {
		this.msisdn = msisdn;
	}

	/*@Override
	public
	String toString(){
		 return "device [PIN=" + PIN + "]";
	}*/
	
}
