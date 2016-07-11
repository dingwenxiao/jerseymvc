package dingwen.jersey.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "device")
public class DeviceModel {
	@Id
	@Column(name = "pin")
	//@XmlElement
	@JsonProperty("PIN")
	private String PIN;

	//@XmlElement
	@Column(name = "type")
	private String type;

	//@XmlElement
	@Column(name = "misisdnValue")
	private String misisdnValue;

	//@XmlElement
	@Column(name = "misReliable")
	private Boolean misReliable;

	//@XmlElement
	@Column(name = "osVer")
	private String osVer;
	
	//@XmlElement
	@Column(name = "appVer")
	private String appVer;
	
	//@XmlElement
	@Column(name = "verReliable")
	private String verReliable;
	
	//@XmlElement
	@Column(name = "sapSoldTo")
	private String sapSoldTo;
	
	//@XmlElement
	@Column(name = "alias")
	private String alias;
	
	



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

	public String getMisisdnValue() {
		return misisdnValue;
	}

	public void setMisisdnValue(String misisdnValue) {
		this.misisdnValue = misisdnValue;
	}

	public Boolean isMisReliable() {
		return misReliable;
	}

	public void setMisReliable(Boolean misReliable) {
		this.misReliable = misReliable;
	}

	public String getOsVer() {
		return osVer;
	}

	public void setOsVer(String osVer) {
		this.osVer = osVer;
	}

	public String getAppVer() {
		return appVer;
	}

	public void setAppVer(String appVer) {
		this.appVer = appVer;
	}

	public String getVerReliable() {
		return verReliable;
	}

	public void setVerReliable(String verReliable) {
		this.verReliable = verReliable;
	}

	public String getSapSoldTo() {
		return sapSoldTo;
	}

	public void setSapSoldTo(String sapSoldTo) {
		this.sapSoldTo = sapSoldTo;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

/*	@Override
	public String toString() {
		StringBuilder returnBuffer = new StringBuilder();
		returnBuffer.append("{\"PIN\":");
		returnBuffer.append(PIN);
		returnBuffer.append("\"type\":");
		returnBuffer.append(type);
		returnBuffer.append("\",\"msisdn\":{\"misisdnValue\":\"");
		returnBuffer.append(misisdnValue);
		returnBuffer.append("\",\"reliable\":\"");
		returnBuffer.append(misReliable);
		returnBuffer.append("\"}");
		return returnBuffer.toString();
	}*/
}
