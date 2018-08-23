/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package org.wxjs.upload.modules.upload.entity;

import org.hibernate.validator.constraints.Length;
import org.wxjs.les.common.persistence.DataEntity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;



/**
 * inf_punishEntity
 * @author GLQ
 * @version 2018-08-21
 */
public class InfPunish extends DataEntity<InfPunish> {
	
	private static final long serialVersionUID = 1L;
	private String no;		// no
	private String orgId;		// org_id
	private String internalNo;		// internal_no
	private String itemId;		// item_id
	private String department;		// department
	private String ajAddr;		// aj_addr
	private Date ajOccurDate;		// aj_occur_date
	private String source;		// source
	private String fact;		// fact
	private String targetType;		// target_type
	private String punishTarget;		// punish_target
	private String targetCode;		// target_code
	private String targetPaperType;		// target_paper_type
	private String targetPaperNumber;		// target_paper_number
	private String targetPhone;		// target_phone
	private String targetMobile;		// target_mobile
	private String targetAddress;		// target_address
	private String targetZipcode;		// target_zipcode
	private String targetEmail;		// target_email
	private String reporter;		// reporter
	private Date reporterDate;		// reporter_date
	private String reporterPaperType;		// reporter_paper_type
	private String reporterPaperNumber;		// reporter_paper_number
	private String reporterPhone;		// reporter_phone
	private String reporterMobile;		// reporter_mobile
	private String reporterAddress;		// reporter_address
	private String reporterZipcode;		// reporter_zipcode
	private String reporterEmail;		// reporter_email
	private String content;		// content
	private String form;		// form
	private String promise;		// promise
	private String promiseType;		// promise_type
	private String isrisk;		// isrisk
	private String risktype;		// risktype
	private String riskdescription;		// riskdescription
	private String riskresult;		// riskresult
	private String syncSign;		// sync_sign
	private String syncErrorDesc;		// sync_error_desc
	private Date readDate;		// read_date
	private String itemVersion;		// item_version
	
	public InfPunish() {
		super();
	}

	public InfPunish(String id){
		super(id);
	}

	@Length(min=1, max=50, message="no长度必须介于 1 和 50 之间")
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@Length(min=1, max=100, message="org_id长度必须介于 1 和 100 之间")
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@Length(min=1, max=100, message="internal_no长度必须介于 1 和 100 之间")
	public String getInternalNo() {
		return internalNo;
	}

	public void setInternalNo(String internalNo) {
		this.internalNo = internalNo;
	}
	
	@Length(min=1, max=100, message="item_id长度必须介于 1 和 100 之间")
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@Length(min=1, max=100, message="department长度必须介于 1 和 100 之间")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Length(min=1, max=200, message="aj_addr长度必须介于 1 和 200 之间")
	public String getAjAddr() {
		return ajAddr;
	}

	public void setAjAddr(String ajAddr) {
		this.ajAddr = ajAddr;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="aj_occur_date不能为空")
	public Date getAjOccurDate() {
		return ajOccurDate;
	}

	public void setAjOccurDate(Date ajOccurDate) {
		this.ajOccurDate = ajOccurDate;
	}
	
	@Length(min=0, max=1, message="source长度必须介于 0 和 1 之间")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@Length(min=0, max=4000, message="fact长度必须介于 0 和 4000 之间")
	public String getFact() {
		return fact;
	}

	public void setFact(String fact) {
		this.fact = fact;
	}
	
	@Length(min=1, max=1, message="target_type长度必须介于 1 和 1 之间")
	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	
	@Length(min=1, max=200, message="punish_target长度必须介于 1 和 200 之间")
	public String getPunishTarget() {
		return punishTarget;
	}

	public void setPunishTarget(String punishTarget) {
		this.punishTarget = punishTarget;
	}
	
	@Length(min=0, max=50, message="target_code长度必须介于 0 和 50 之间")
	public String getTargetCode() {
		return targetCode;
	}

	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}
	
	@Length(min=0, max=2, message="target_paper_type长度必须介于 0 和 2 之间")
	public String getTargetPaperType() {
		return targetPaperType;
	}

	public void setTargetPaperType(String targetPaperType) {
		this.targetPaperType = targetPaperType;
	}
	
	@Length(min=0, max=50, message="target_paper_number长度必须介于 0 和 50 之间")
	public String getTargetPaperNumber() {
		return targetPaperNumber;
	}

	public void setTargetPaperNumber(String targetPaperNumber) {
		this.targetPaperNumber = targetPaperNumber;
	}
	
	@Length(min=0, max=40, message="target_phone长度必须介于 0 和 40 之间")
	public String getTargetPhone() {
		return targetPhone;
	}

	public void setTargetPhone(String targetPhone) {
		this.targetPhone = targetPhone;
	}
	
	@Length(min=0, max=20, message="target_mobile长度必须介于 0 和 20 之间")
	public String getTargetMobile() {
		return targetMobile;
	}

	public void setTargetMobile(String targetMobile) {
		this.targetMobile = targetMobile;
	}
	
	@Length(min=0, max=200, message="target_address长度必须介于 0 和 200 之间")
	public String getTargetAddress() {
		return targetAddress;
	}

	public void setTargetAddress(String targetAddress) {
		this.targetAddress = targetAddress;
	}
	
	@Length(min=0, max=6, message="target_zipcode长度必须介于 0 和 6 之间")
	public String getTargetZipcode() {
		return targetZipcode;
	}

	public void setTargetZipcode(String targetZipcode) {
		this.targetZipcode = targetZipcode;
	}
	
	@Length(min=0, max=100, message="target_email长度必须介于 0 和 100 之间")
	public String getTargetEmail() {
		return targetEmail;
	}

	public void setTargetEmail(String targetEmail) {
		this.targetEmail = targetEmail;
	}
	
	@Length(min=0, max=200, message="reporter长度必须介于 0 和 200 之间")
	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReporterDate() {
		return reporterDate;
	}

	public void setReporterDate(Date reporterDate) {
		this.reporterDate = reporterDate;
	}
	
	@Length(min=0, max=2, message="reporter_paper_type长度必须介于 0 和 2 之间")
	public String getReporterPaperType() {
		return reporterPaperType;
	}

	public void setReporterPaperType(String reporterPaperType) {
		this.reporterPaperType = reporterPaperType;
	}
	
	@Length(min=0, max=50, message="reporter_paper_number长度必须介于 0 和 50 之间")
	public String getReporterPaperNumber() {
		return reporterPaperNumber;
	}

	public void setReporterPaperNumber(String reporterPaperNumber) {
		this.reporterPaperNumber = reporterPaperNumber;
	}
	
	@Length(min=0, max=40, message="reporter_phone长度必须介于 0 和 40 之间")
	public String getReporterPhone() {
		return reporterPhone;
	}

	public void setReporterPhone(String reporterPhone) {
		this.reporterPhone = reporterPhone;
	}
	
	@Length(min=0, max=20, message="reporter_mobile长度必须介于 0 和 20 之间")
	public String getReporterMobile() {
		return reporterMobile;
	}

	public void setReporterMobile(String reporterMobile) {
		this.reporterMobile = reporterMobile;
	}
	
	@Length(min=0, max=200, message="reporter_address长度必须介于 0 和 200 之间")
	public String getReporterAddress() {
		return reporterAddress;
	}

	public void setReporterAddress(String reporterAddress) {
		this.reporterAddress = reporterAddress;
	}
	
	@Length(min=0, max=6, message="reporter_zipcode长度必须介于 0 和 6 之间")
	public String getReporterZipcode() {
		return reporterZipcode;
	}

	public void setReporterZipcode(String reporterZipcode) {
		this.reporterZipcode = reporterZipcode;
	}
	
	@Length(min=0, max=100, message="reporter_email长度必须介于 0 和 100 之间")
	public String getReporterEmail() {
		return reporterEmail;
	}

	public void setReporterEmail(String reporterEmail) {
		this.reporterEmail = reporterEmail;
	}
	
	@Length(min=0, max=200, message="content长度必须介于 0 和 200 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}
	
	public String getPromise() {
		return promise;
	}

	public void setPromise(String promise) {
		this.promise = promise;
	}
	
	@Length(min=1, max=2, message="promise_type长度必须介于 1 和 2 之间")
	public String getPromiseType() {
		return promiseType;
	}

	public void setPromiseType(String promiseType) {
		this.promiseType = promiseType;
	}
	
	public String getIsrisk() {
		return isrisk;
	}

	public void setIsrisk(String isrisk) {
		this.isrisk = isrisk;
	}
	
	@Length(min=0, max=50, message="risktype长度必须介于 0 和 50 之间")
	public String getRisktype() {
		return risktype;
	}

	public void setRisktype(String risktype) {
		this.risktype = risktype;
	}
	
	@Length(min=0, max=200, message="riskdescription长度必须介于 0 和 200 之间")
	public String getRiskdescription() {
		return riskdescription;
	}

	public void setRiskdescription(String riskdescription) {
		this.riskdescription = riskdescription;
	}
	
	@Length(min=0, max=200, message="riskresult长度必须介于 0 和 200 之间")
	public String getRiskresult() {
		return riskresult;
	}

	public void setRiskresult(String riskresult) {
		this.riskresult = riskresult;
	}
	
	@Length(min=0, max=1, message="sync_sign长度必须介于 0 和 1 之间")
	public String getSyncSign() {
		return syncSign;
	}

	public void setSyncSign(String syncSign) {
		this.syncSign = syncSign;
	}
	
	@Length(min=0, max=1000, message="sync_error_desc长度必须介于 0 和 1000 之间")
	public String getSyncErrorDesc() {
		return syncErrorDesc;
	}

	public void setSyncErrorDesc(String syncErrorDesc) {
		this.syncErrorDesc = syncErrorDesc;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}
	
	public String getItemVersion() {
		return itemVersion;
	}

	public void setItemVersion(String itemVersion) {
		this.itemVersion = itemVersion;
	}
	
}