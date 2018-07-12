/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.entity;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;

import org.wxjs.les.common.persistence.DataEntity;
import org.wxjs.les.modules.sys.entity.User;

/**
 * 案件Entity
 * @author GLQ
 * @version 2018-07-09
 */
public class Tcase extends DataEntity<Tcase> {
	
	private static final long serialVersionUID = 1L;
	private String caseId;		// 事项编号
	private String accepter;		// 受理人
	private Date acceptDate;		// 受理时间
	private String caseSource;		// 案件来源
	private String partyType;		// 当事人类型
	private String orgName;		// 名称
	private String orgAgent;		// 法定代表人
	private String orgCode;		// 统一社会信用代码
	private String orgResponsiblePerson;		// 负责人
	private String orgAddress;		// 住址
	private String orgPhone;		// 联系电话
	private String psnName;		// 姓名
	private String psnOrganization;		// 工作单位
	private String psnCode;		// 身份证
	private String psnPost;		// 职务
	private String psnAddress;		// 住址
	private String psnPhone;		// 联系电话
	private String psnSex;		// 性别
	private String caseHappenDate;		// 案发时间
	private String caseHappenAddress;		// 案发地点
	private String projectCode;		// 案件所涉项目代码
	private String projectName;		// 案件所涉项目名称
	private String caseCause;		// 案由
	private String caseSummary;		// 案情摘要
	private String caseHandler;		// 办案人
	private String caseStage;       //事项类型

	private String caseStageStatus;		// 事项类型状态
	private String procInstId10;		// 受理流程号
	private String procInstId20;		// 立案流程号
	private String procInstId30;		// 受理流程号
	private String procInstId40;		// 告知流程号
	private String procInstId50;		// 决定流程号
	private String procInstId60;		// 结案流程号
	private String procInstId70;		// 结束流程号
	private String procInstIdSerious;		// 重大流程号
	private String procInstIdCancel;		// 撤销流程号
	
	private String procInstId;		// 流程号
	
	public Tcase() {
		super();
	}

	public Tcase(String id){
		super(id);
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
	@Length(min=1, max=32, message="受理人长度必须介于 1 和 32 之间")
	public String getAccepter() {
		return accepter;
	}

	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}
	
	@Length(min=1, max=100, message="案件来源长度必须介于 1 和 100 之间")
	public String getCaseSource() {
		return caseSource;
	}

	public void setCaseSource(String caseSource) {
		this.caseSource = caseSource;
	}
	
	@Length(min=1, max=32, message="当事人类型长度必须介于 1 和 32 之间")
	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}
	
	@Length(min=0, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	@Length(min=0, max=32, message="法定代表人长度必须介于 1 和 32 之间")
	public String getOrgAgent() {
		return orgAgent;
	}

	public void setOrgAgent(String orgAgent) {
		this.orgAgent = orgAgent;
	}
	
	@Length(min=0, max=32, message="统一社会信用代码长度必须介于 1 和 32 之间")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	@Length(min=0, max=100, message="负责人长度必须介于 1 和 100 之间")
	public String getOrgResponsiblePerson() {
		return orgResponsiblePerson;
	}

	public void setOrgResponsiblePerson(String orgResponsiblePerson) {
		this.orgResponsiblePerson = orgResponsiblePerson;
	}
	
	@Length(min=0, max=100, message="住址长度必须介于 1 和 100 之间")
	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	
	@Length(min=0, max=32, message="联系电话长度必须介于 1 和 32 之间")
	public String getOrgPhone() {
		return orgPhone;
	}

	public void setOrgPhone(String orgPhone) {
		this.orgPhone = orgPhone;
	}
	
	@Length(min=0, max=32, message="姓名长度必须介于 1 和 32 之间")
	public String getPsnName() {
		return psnName;
	}

	public void setPsnName(String psnName) {
		this.psnName = psnName;
	}
	
	@Length(min=0, max=100, message="工作单位长度必须介于 1 和 100 之间")
	public String getPsnOrganization() {
		return psnOrganization;
	}

	public void setPsnOrganization(String psnOrganization) {
		this.psnOrganization = psnOrganization;
	}
	
	@Length(min=0, max=32, message="身份证长度必须介于 1 和 32 之间")
	public String getPsnCode() {
		return psnCode;
	}

	public void setPsnCode(String psnCode) {
		this.psnCode = psnCode;
	}
	
	@Length(min=0, max=100, message="职务长度必须介于 1 和 100 之间")
	public String getPsnPost() {
		return psnPost;
	}

	public void setPsnPost(String psnPost) {
		this.psnPost = psnPost;
	}
	
	@Length(min=0, max=100, message="住址长度必须介于 1 和 100 之间")
	public String getPsnAddress() {
		return psnAddress;
	}

	public void setPsnAddress(String psnAddress) {
		this.psnAddress = psnAddress;
	}
	
	@Length(min=0, max=32, message="联系电话长度必须介于 1 和 32 之间")
	public String getPsnPhone() {
		return psnPhone;
	}

	public void setPsnPhone(String psnPhone) {
		this.psnPhone = psnPhone;
	}
	
	@Length(min=0, max=8, message="性别长度必须介于 1 和 8 之间")
	public String getPsnSex() {
		return psnSex;
	}

	public void setPsnSex(String psnSex) {
		this.psnSex = psnSex;
	}
	
	public String getCaseHappenDate() {
		return caseHappenDate;
	}

	public void setCaseHappenDate(String caseHappenDate) {
		this.caseHappenDate = caseHappenDate;
	}
	
	@Length(min=1, max=100, message="案发地点长度必须介于 1 和 100 之间")
	public String getCaseHappenAddress() {
		return caseHappenAddress;
	}

	public void setCaseHappenAddress(String caseHappenAddress) {
		this.caseHappenAddress = caseHappenAddress;
	}
	
	@Length(min=1, max=32, message="案件所涉项目代码长度必须介于 1 和 32 之间")
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@Length(min=1, max=100, message="案件所涉项目名称长度必须介于 1 和 100 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=1, max=200, message="案由长度必须介于 1 和 200 之间")
	public String getCaseCause() {
		return caseCause;
	}

	public void setCaseCause(String caseCause) {
		this.caseCause = caseCause;
	}
	
	@Length(min=1, max=500, message="案情摘要长度必须介于 1 和 500 之间")
	public String getCaseSummary() {
		return caseSummary;
	}

	public void setCaseSummary(String caseSummary) {
		this.caseSummary = caseSummary;
	}
	
	@Length(min=1, max=200, message="办案人长度必须介于 1 和 200 之间")
	public String getCaseHandler() {
		return caseHandler;
	}

	public void setCaseHandler(String caseHandler) {
		this.caseHandler = caseHandler;
	}
	
	public List<String> getCaseHandlerList() {
		List<String> list = Lists.newArrayList();
		if(!StringUtils.isEmpty(this.caseHandler)){
			String[] strs = this.caseHandler.split(",");
			for(String str : strs){
				list.add(str);
			}
		}
		
		return list;
	}

	public void setCaseHandlerList(List<String> list) {
		StringBuffer buffer = new StringBuffer();
		for(String name : list){
			buffer.append(",").append(name);
		}
		this.caseHandler = buffer.substring(1);
	}

	public String getCaseStage() {
		return caseStage;
	}

	public void setCaseStage(String caseStage) {
		this.caseStage = caseStage;
	}

	public String getCaseStageStatus() {
		return caseStageStatus;
	}

	public void setCaseStageStatus(String caseStageStatus) {
		this.caseStageStatus = caseStageStatus;
	}

	public String getProcInstId10() {
		return procInstId10;
	}

	public void setProcInstId10(String procInstId10) {
		this.procInstId10 = procInstId10;
	}

	public String getProcInstId20() {
		return procInstId20;
	}

	public void setProcInstId20(String procInstId20) {
		this.procInstId20 = procInstId20;
	}

	public String getProcInstId30() {
		return procInstId30;
	}

	public void setProcInstId30(String procInstId30) {
		this.procInstId30 = procInstId30;
	}

	public String getProcInstId40() {
		return procInstId40;
	}

	public void setProcInstId40(String procInstId40) {
		this.procInstId40 = procInstId40;
	}

	public String getProcInstId50() {
		return procInstId50;
	}

	public void setProcInstId50(String procInstId50) {
		this.procInstId50 = procInstId50;
	}

	public String getProcInstId60() {
		return procInstId60;
	}

	public void setProcInstId60(String procInstId60) {
		this.procInstId60 = procInstId60;
	}

	public String getProcInstId70() {
		return procInstId70;
	}

	public void setProcInstId70(String procInstId70) {
		this.procInstId70 = procInstId70;
	}

	public String getProcInstIdSerious() {
		return procInstIdSerious;
	}

	public void setProcInstIdSerious(String procInstIdSerious) {
		this.procInstIdSerious = procInstIdSerious;
	}

	public String getProcInstIdCancel() {
		return procInstIdCancel;
	}

	public void setProcInstIdCancel(String procInstIdCancel) {
		this.procInstIdCancel = procInstIdCancel;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	
}