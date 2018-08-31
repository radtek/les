package org.wxjs.les.modules.tcase.entity;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.wxjs.les.common.persistence.DataEntity;

import com.google.common.collect.Lists;

public class Project extends DataEntity<Project> {
	
	private static final long serialVersionUID = 1L;
	
	private String prjNum;

	private String prjName;

	private String buildCorpName;

	private String buildCorpCode;

	private String prjAddress;
	
	private String prjPassword;
	
	private String contact;
	
	private String mobile;
	
	private String hintMessage;
	
	private String hintShowFlag;
	
	public Project(){
		
	}

	public String getPrjNum() {
		return prjNum;
	}

	public void setPrjNum(String prjNum) {
		this.prjNum = prjNum;
	}

	public String getPrjPassword() {
		return prjPassword;
	}

	public void setPrjPassword(String prjPassword) {
		this.prjPassword = prjPassword;
	}

	public String getPrjName() {
		return StringEscapeUtils.unescapeHtml4(prjName);
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public String getBuildCorpName() {
		return StringEscapeUtils.unescapeHtml4(buildCorpName);
	}

	public void setBuildCorpName(String buildCorpName) {
		this.buildCorpName = buildCorpName;
	}

	public String getBuildCorpCode() {
		return buildCorpCode;
	}

	public void setBuildCorpCode(String buildCorpCode) {
		this.buildCorpCode = buildCorpCode;
	}

	public String getPrjAddress() {
		return StringEscapeUtils.unescapeHtml4(prjAddress);
	}

	public void setPrjAddress(String prjAddress) {
		this.prjAddress = prjAddress;
	}
	
    public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getHintMessage() {
		return hintMessage;
	}

	public void setHintMessage(String hintMessage) {
		this.hintMessage = hintMessage;
	}

	public String getHintShowFlag() {
		return hintShowFlag;
	}

	public void setHintShowFlag(String hintShowFlag) {
		this.hintShowFlag = hintShowFlag;
	}
	
	public boolean getIsMultiplePrjNum(){
		return !StringUtils.isBlank(this.prjNum) && this.prjNum.contains(",");
	}
	
	public List<String> getPrjNums(){
		List<String> list = Lists.newArrayList();
		if(!StringUtils.isBlank(this.prjNum)){
			String[] strs = this.prjNum.split(",");
			for(String str : strs){
				list.add(str);
			}
		}
		return list;
	}

	@Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

	
}
