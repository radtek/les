package org.wxjs.les.modules.tcase.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wxjs.les.common.utils.Base64Utils;

@XmlRootElement(name = "row")
public class ProjectAndEntity{
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name="PrjNum") 
	private String prj_num;
	
	@XmlElement(name="PrjName") 
	private String prj_name;
	
	@XmlElement(name="PrjAddress") 
	private String prj_address;
	
	@XmlElement(name="TenderType") 
	private String tender_type;
	
	@XmlElement(name="orgCode") 
	private String org_code;
	
	@XmlElement(name="orgName") 
	private String org_name;
	
	@XmlElement(name="orgAddress") 
	private String org_address;
	
	@XmlElement(name="orgResponsiblePerson") 
	private String org_responsible_person;
	
	@XmlElement(name="orgPhone") 
	private String org_phone;
	
	@XmlElement(name="orgAgent") 
	private String org_agent;
	
	public ProjectAndEntity(){
		
	}

	public void setPrj_num(String prj_num) {
		this.prj_num = prj_num;
	}

	public void setPrj_name(String prj_name) {
		this.prj_name = prj_name;
	}

	public void setPrj_address(String prj_address) {
		this.prj_address = prj_address;
	}

	public void setTender_type(String tender_type) {
		this.tender_type = tender_type;
	}
	
	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public void setOrg_address(String org_address) {
		this.org_address = org_address;
	}

	public void setOrg_responsible_person(String org_responsible_person) {
		this.org_responsible_person = org_responsible_person;
	}

	public void setOrg_phone(String org_phone) {
		this.org_phone = org_phone;
	}

	public void setOrg_agent(String org_agent) {
		this.org_agent = org_agent;
	}

	public String getPrjNum() {
		return Base64Utils.decode(prj_num);
	}

	public String getPrjName() {
		return Base64Utils.decode(prj_name);
	}

	public String getPrjAddress() {
		return Base64Utils.decode(prj_address);
	}

	public String getTenderType() {
		return Base64Utils.decode(tender_type);
	}

	public String getOrgCode() {
		return Base64Utils.decode(org_code);
	}

	public String getOrgName() {
		return Base64Utils.decode(org_name);
	}

	public String getOrgAddress() {
		return Base64Utils.decode(org_address);
	}

	public String getOrgResponsiblePerson() {
		return Base64Utils.decode(org_responsible_person);
	}

	public String getOrgPhone() {
		return Base64Utils.decode(org_phone);
	}

	public String getOrgAgent() {
		return Base64Utils.decode(org_agent);
	}

	/*
	public Tcase toTcase(){
		Tcase rst = new Tcase();
		
		rst.setProjectCode(Base64Utils.decode(this.prj_num));
		rst.setProjectName(Base64Utils.decode(this.prj_name));
		rst.setCaseHappenAddress(Base64Utils.decode(this.prj_address));
		
		rst.setTenderType(Base64Utils.decode(this.tender_type));
		rst.setOrgName(Base64Utils.decode(this.org_name));
		rst.setOrgCode(Base64Utils.decode(this.org_code));
		rst.setOrgAddress(Base64Utils.decode(this.org_address));
		rst.setOrgAgent(Base64Utils.decode(this.org_agent));
		rst.setOrgPhone(Base64Utils.decode(this.org_phone));
		rst.setOrgResponsiblePerson(Base64Utils.decode(this.org_responsible_person));
		
		return rst;
	}
	
	public String getJsonString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\"orgAddress\":\"").append(this.org_address).append("\",");
		buffer.append("\"orgAgent\":\"").append(this.org_agent).append("\",");
		buffer.append("\"orgCode\":\"").append(this.org_code).append("\",");
		buffer.append("\"orgName\":\"").append(this.org_name).append("\",");
		buffer.append("\"orgPhone\":\"").append(this.org_phone).append("\",");
		buffer.append("\"orgResponsiblePerson\":\"").append(this.org_responsible_person).append("\",");
		buffer.append("\"prjAddress\":\"").append(this.prj_address).append("\",");
		buffer.append("\"prjName\":\"").append(this.prj_name).append("\",");
		buffer.append("\"prjNum\":\"").append(this.prj_num).append("\"");
		buffer.append("}");
		
		String rst = Base64Utils.encode(buffer.toString());
		logger.debug("src:{}, decoded:{}", buffer.toString(), Base64Utils.decode(rst));
		
		return rst;
	}
	
	*/
	
	public String getJsonString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\"orgAddress\":\"").append(this.getOrgAddress()).append("\",");
		buffer.append("\"orgAgent\":\"").append(this.getOrgAgent()).append("\",");
		buffer.append("\"orgCode\":\"").append(this.getOrgCode()).append("\",");
		buffer.append("\"orgName\":\"").append(this.getOrgName()).append("\",");
		buffer.append("\"orgPhone\":\"").append(this.getOrgPhone()).append("\",");
		buffer.append("\"orgResponsiblePerson\":\"").append(this.getOrgResponsiblePerson()).append("\",");
		buffer.append("\"prjAddress\":\"").append(this.getPrjAddress()).append("\",");
		buffer.append("\"prjName\":\"").append(this.getPrjName()).append("\",");
		buffer.append("\"prjNum\":\"").append(this.getPrjNum()).append("\"");
		buffer.append("}");
		
		String rst = Base64Utils.encode(buffer.toString());
		logger.debug("src:{}, decoded:{}", buffer.toString(), Base64Utils.decode(rst));
		
		return rst;
	}

}
