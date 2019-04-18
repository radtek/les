/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.check.entity;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;

import org.wxjs.les.common.persistence.DataEntity;
import org.wxjs.les.modules.base.entity.Signature;
import org.wxjs.les.modules.sys.entity.User;

/**
 * 现场踏勘Entity
 * @author 千里目
 * @version 2018-07-10
 */
public class Tsitecheck extends DataEntity<Tsitecheck> {
	
	private static final long serialVersionUID = 1L;
	private final String FORMAT_DATE = "yyyy-MM-dd";
	private String developOrg;		// 建设单位
	private String developContact;		// 建设单位联系人
	private String developPhone;		// 建设单位联系人电话
	private String constructionOrg;		// 施工单位
	private String constructionContact;		// 施工单位联系人
	private String constructionPhone;		// 施工单位联系人电话
	private String projectName;		// 工程名称
	private String projectAddress;		// 工程地址
	private String siteSituation;		// 现场检查工程情况
	private String sitePicture;		// 现场踏勘示意图
	private String siteCheckResult;		// 现场踏勘情况
	private String checker;		// 勘查人
	private Date checkDate;		// 勘查时间
	
	private String attachment;		// 现场踏勘示意图
	
	private Date beginDate;
	private Date endDate;
	
	private Signature checkerSig = new Signature(); // 勘察人签名
	private Signature partySig = new Signature();		// 当事人签名
	
	//流程相关
	private String handler;		// 办案人
	
	private String caseStatus;
	
	private String procInsId;		// 受理流程号

	private List<User> availableHandlers = Lists.newArrayList();
	
	private String procDefId;		// 流程定义号
	
	private String executionId;		// 执行号
	
	//临时属性
	private String taskDefKey;
	
	private String taskId;
	
	private String taskName;
	
	private String operateType = "";
	
	private String businesskey = "";
	
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		//设置时间边界值，将时分秒设置为23：59：59，就能将当天的数据也能查出来。
		if(endDate != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.set(Calendar.HOUR, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			endDate = calendar.getTime();
		}
		this.endDate = endDate;
	}

	public Tsitecheck() {
		super();
	}

	public Tsitecheck(String id){
		super(id);
	}

	@Length(min=1, max=100, message="建设单位长度必须介于 1 和 100 之间")
	public String getDevelopOrg() {
		return developOrg;
	}

	public void setDevelopOrg(String developOrg) {
		this.developOrg = developOrg;
	}
	
	@Length(min=1, max=32, message="建设单位联系人长度必须介于 1 和 32 之间")
	public String getDevelopContact() {
		return developContact;
	}

	public void setDevelopContact(String developContact) {
		this.developContact = developContact;
	}
	
	@Length(min=1, max=32, message="建设单位联系人电话长度必须介于 1 和 32 之间")
	public String getDevelopPhone() {
		return developPhone;
	}

	public void setDevelopPhone(String developPhone) {
		this.developPhone = developPhone;
	}
	
	@Length(min=1, max=100, message="施工单位长度必须介于 1 和 100 之间")
	public String getConstructionOrg() {
		return constructionOrg;
	}

	public void setConstructionOrg(String constructionOrg) {
		this.constructionOrg = constructionOrg;
	}
	
	@Length(min=1, max=32, message="施工单位联系人长度必须介于 1 和 32 之间")
	public String getConstructionContact() {
		return constructionContact;
	}

	public void setConstructionContact(String constructionContact) {
		this.constructionContact = constructionContact;
	}
	
	@Length(min=1, max=32, message="施工单位联系人电话长度必须介于 1 和 32 之间")
	public String getConstructionPhone() {
		return constructionPhone;
	}

	public void setConstructionPhone(String constructionPhone) {
		this.constructionPhone = constructionPhone;
	}
	
	@Length(min=1, max=100, message="工程名称长度必须介于 1 和 100 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=1, max=100, message="工程地址长度必须介于 1 和 100 之间")
	public String getProjectAddress() {
		return projectAddress;
	}

	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}
	
	@Length(min=1, max=500, message="现场检查工程情况长度必须介于 1 和 500 之间")
	public String getSiteSituation() {
		return siteSituation;
	}

	public void setSiteSituation(String siteSituation) {
		this.siteSituation = siteSituation;
	}
	
	@Length(min=1, max=500, message="现场踏勘示意图长度必须介于 1 和 500 之间")
	public String getSitePicture() {
		return sitePicture;
	}

	public void setSitePicture(String sitePicture) {
		this.sitePicture = sitePicture;
	}
	
	@Length(min=1, max=500, message="现场踏勘情况长度必须介于 1 和 500 之间")
	public String getSiteCheckResult() {
		return siteCheckResult;
	}

	public void setSiteCheckResult(String siteCheckResult) {
		this.siteCheckResult = siteCheckResult;
	}
	
	@Length(min=1, max=32, message="勘查人长度必须介于 1 和 32 之间")
	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}	

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public Signature getCheckerSig() {
		return checkerSig;
	}

	public void setCheckerSig(Signature checkerSig) {
		this.checkerSig = checkerSig;
	}

	public Signature getPartySig() {
		return partySig;
	}

	public void setPartySig(Signature partySig) {
		this.partySig = partySig;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}
	
	public List<String> getHandlerList() {
		List<String> list = Lists.newArrayList();
		if(!StringUtils.isEmpty(this.handler)){
			String[] strs = this.handler.split(",");
			for(String str : strs){
				list.add(str);
			}
		}
		
		return list;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public List<User> getAvailableHandlers() {
		return availableHandlers;
	}

	public void setAvailableHandlers(List<User> availableHandlers) {
		this.availableHandlers = availableHandlers;
	}

	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	
	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getBusinesskey() {
		return businesskey;
	}

	public void setBusinesskey(String businesskey) {
		this.businesskey = businesskey;
	}
	
}