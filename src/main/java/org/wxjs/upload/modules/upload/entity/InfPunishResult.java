/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package org.wxjs.upload.modules.upload.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import org.wxjs.les.common.persistence.DataEntity;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.Util;

/**
 * inf_punish_resultEntity
 * @author GLQ
 * @version 2018-08-21
 */
public class InfPunishResult extends DataEntity<InfPunishResult> {
	
	private static final long serialVersionUID = 1L;
	private String no;		// no
	private String orgId;		// org_id
	private String internalNo;		// internal_no
	private String itemId;		// item_id
	private String program;		// program
	private String punishSort;		// punish_sort
	private String standard;		// standard
	private String accordance;		// accordance
	private String punishDeside;		// punish_deside
	private String punishClass;		// punish_class
	private String punishResult;		// punish_result
	private String punishResultFine;		// punish_result_fine
	private String punishResultFinePeople;		// punish_result_fine_people
	private String punishResultExpropriation;		// punish_result_expropriation
	private String punishResultExpropriationV;		// punish_result_expropriation_v
	private String punishResultBusiness;		// punish_result_business
	private String punishResultPeople;		// punish_result_people
	private String punishResultDetain;		// punish_result_detain
	private Date finishTime;		// finish_time
	private String attachment;		// attachment
	private String syncErrorDesc;		// sync_error_desc
	private String syncSign;		// sync_sign
	private Date readDate;		// read_date
	
	public InfPunishResult() {
		super();
	}

	public InfPunishResult(String id){
		super(id);
	}

	@Length(min=1, max=50, message="no长度必须介于 1 和 50 之间")
	public String getNo() {
		return Util.getString(no);
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@Length(min=1, max=10, message="org_id长度必须介于 1 和 10 之间")
	public String getOrgId() {
		return Util.getString(orgId);
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@Length(min=1, max=100, message="internal_no长度必须介于 1 和 100 之间")
	public String getInternalNo() {
		return Util.getString(internalNo);
	}

	public void setInternalNo(String internalNo) {
		this.internalNo = internalNo;
	}
	
	@Length(min=1, max=50, message="item_id长度必须介于 1 和 50 之间")
	public String getItemId() {
		return Util.getString(itemId);
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@Length(min=1, max=10, message="program长度必须介于 1 和 10 之间")
	public String getProgram() {
		return Util.getString(program);
	}

	public void setProgram(String program) {
		this.program = program;
	}
	
	@Length(min=1, max=100, message="punish_sort长度必须介于 1 和 100 之间")
	public String getPunishSort() {
		return Util.getString(punishSort);
	}

	public void setPunishSort(String punishSort) {
		this.punishSort = punishSort;
	}
	
	@Length(min=1, max=2000, message="standard长度必须介于 1 和 2000 之间")
	public String getStandard() {
		return Util.getString(standard);
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}
	
	@Length(min=1, max=2000, message="accordance长度必须介于 1 和 2000 之间")
	public String getAccordance() {
		return Util.getString(accordance);
	}

	public void setAccordance(String accordance) {
		this.accordance = accordance;
	}
	
	@Length(min=1, max=10, message="punish_deside长度必须介于 1 和 10 之间")
	public String getPunishDeside() {
		return Util.getString(punishDeside);
	}

	public void setPunishDeside(String punishDeside) {
		this.punishDeside = punishDeside;
	}
	
	@Length(min=1, max=100, message="punish_class长度必须介于 1 和 100 之间")
	public String getPunishClass() {
		return Util.getString(punishClass);
	}

	public void setPunishClass(String punishClass) {
		this.punishClass = punishClass;
	}
	
	@Length(min=1, max=2000, message="punish_result长度必须介于 1 和 2000 之间")
	public String getPunishResult() {
		return Util.getString(punishResult);
	}

	public void setPunishResult(String punishResult) {
		this.punishResult = punishResult;
	}
	
	public String getPunishResultFine() {
		return punishResultFine;
	}

	public void setPunishResultFine(String punishResultFine) {
		this.punishResultFine = punishResultFine;
	}
	
	public String getPunishResultFinePeople() {
		return punishResultFinePeople;
	}

	public void setPunishResultFinePeople(String punishResultFinePeople) {
		this.punishResultFinePeople = punishResultFinePeople;
	}
	
	public String getPunishResultExpropriation() {
		return punishResultExpropriation;
	}

	public void setPunishResultExpropriation(String punishResultExpropriation) {
		this.punishResultExpropriation = punishResultExpropriation;
	}
	
	public String getPunishResultExpropriationV() {
		return punishResultExpropriationV;
	}

	public void setPunishResultExpropriationV(String punishResultExpropriationV) {
		this.punishResultExpropriationV = punishResultExpropriationV;
	}
	
	public String getPunishResultBusiness() {
		return punishResultBusiness;
	}

	public void setPunishResultBusiness(String punishResultBusiness) {
		this.punishResultBusiness = punishResultBusiness;
	}
	
	public String getPunishResultPeople() {
		return punishResultPeople;
	}

	public void setPunishResultPeople(String punishResultPeople) {
		this.punishResultPeople = punishResultPeople;
	}
	
	public String getPunishResultDetain() {
		return punishResultDetain;
	}

	public void setPunishResultDetain(String punishResultDetain) {
		this.punishResultDetain = punishResultDetain;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="finish_time不能为空")
	public Date getFinishTime() {
		return finishTime;
	}
	
	public String getFinishTimeStr() {
		return DateUtils.formatDate(finishTime, "yyyy-MM-dd");
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	public String getAttachment() {
		return Util.getString(attachment);
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	@Length(min=0, max=1000, message="sync_error_desc长度必须介于 0 和 1000 之间")
	public String getSyncErrorDesc() {
		return syncErrorDesc;
	}

	public void setSyncErrorDesc(String syncErrorDesc) {
		this.syncErrorDesc = syncErrorDesc;
	}
	
	@Length(min=0, max=1, message="sync_sign长度必须介于 0 和 1 之间")
	public String getSyncSign() {
		return Util.getString(syncSign);
	}

	public void setSyncSign(String syncSign) {
		this.syncSign = syncSign;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReadDate() {
		return readDate;
	}
	
	public String getReadDateStr() {
		return DateUtils.formatDate(readDate, "yyyy-MM-dd");
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}
	
	public String getUpdateDateStr() {
		return DateUtils.formatDate(this.updateDate, "yyyy-MM-dd");
	}
	
	public String getCreateDateStr() {
		return DateUtils.formatDate(this.createDate, "yyyy-MM-dd");
	}
	
}