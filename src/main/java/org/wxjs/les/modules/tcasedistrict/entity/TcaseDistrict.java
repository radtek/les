/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcasedistrict.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.DataEntity;
import org.wxjs.les.common.utils.StringUtils;
import org.wxjs.les.common.utils.excel.annotation.ExcelField;
import org.wxjs.les.modules.base.utils.PathUtils;

/**
 * 案件Entity
 * @author GLQ
 * @version 2019-01-31
 */
public class TcaseDistrict extends DataEntity<TcaseDistrict> {
	
	private static final long serialVersionUID = 1L;
	private String caseSeq;		// 事项编号
	private String accepter;		// 受理人
	private Date acceptDate;		// 受理时间
	private Date initialDate;		// 立案日期
	private String initialHandler;		// 立案经办人
	private String projectType;		// 信用扣分类型
	private String deductionMatter;		// 扣分事项
	private String punishType;		// 处罚类型
	private Date decisionDate;		// 处罚决定日期
	private Date settleDate;		// 结案日期
	private String caseSource;		// 案件来源
	private String partyType;		// 当事人类型
	private String orgName;		// 名称
	private String orgAgent;		// 法定代表人
	private String orgCode;		// 统一社会信用代码
	private String orgResponsiblePerson;		// 负责人
	private String orgResponsiblePersonPost;		// 职务
	private String orgAddress;		// 住址
	private String orgPhone;		// 联系电话
	private String psnName;		// 姓名
	private String psnOrganization;		// 工作单位
	private String psnCode;		// 身份证
	private Date psnBirthday;		// 出生年月
	private String psnPost;		// 职务
	private String psnAddress;		// 住址
	private String psnPhone;		// 联系电话
	private String psnSex;		// 性别
	private String caseHappenDate;		// 案发时间
	private String caseHappenAddress;		// 案发地点
	private String projectCode;		// 案件所涉项目代码
	private String projectName;		// 案件所涉项目名称
	private String caseCause;		// 案由
	private String status;		// 状态
	private String handleOrg;		// 经办单位
	private String decisionSeq;		// 决定书编号
	private String punishMoney;		// 罚款金额（元）
	private String filepathInitial;		// 立案审批表
	private String filepathDecision;		// 决定书
	
	private Date dateFrom;		// 起始时间
	private Date dateTo;		// 结束时间
	
	public TcaseDistrict() {
		super();
	}

	public TcaseDistrict(String id){
		super(id);
	}

	@Length(min=1, max=100, message="事项编号长度必须介于 1 和 100 之间")
	public String getCaseSeq() {
		return caseSeq;
	}

	public void setCaseSeq(String caseSeq) {
		this.caseSeq = caseSeq;
	}
	
	@Length(min=0, max=32, message="受理人长度必须介于 0 和 32 之间")
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}
	
	@Length(min=0, max=32, message="立案经办人长度必须介于 0 和 32 之间")
	public String getInitialHandler() {
		return initialHandler;
	}

	public void setInitialHandler(String initialHandler) {
		this.initialHandler = initialHandler;
	}
	
	@Length(min=0, max=32, message="信用扣分类型长度必须介于 0 和 32 之间")
	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	
	@Length(min=0, max=32, message="扣分事项长度必须介于 0 和 32 之间")
	public String getDeductionMatter() {
		return deductionMatter;
	}

	public void setDeductionMatter(String deductionMatter) {
		this.deductionMatter = deductionMatter;
	}
	
	@Length(min=0, max=32, message="处罚类型长度必须介于 0 和 32 之间")
	public String getPunishType() {
		return punishType;
	}

	public void setPunishType(String punishType) {
		this.punishType = punishType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDecisionDate() {
		return decisionDate;
	}

	public void setDecisionDate(Date decisionDate) {
		this.decisionDate = decisionDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}
	
	@Length(min=0, max=100, message="案件来源长度必须介于 0 和 100 之间")
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
	
	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	@Length(min=0, max=32, message="法定代表人长度必须介于 0 和 32 之间")
	public String getOrgAgent() {
		return orgAgent;
	}

	public void setOrgAgent(String orgAgent) {
		this.orgAgent = orgAgent;
	}
	
	@Length(min=0, max=32, message="统一社会信用代码长度必须介于 0 和 32 之间")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	@Length(min=0, max=100, message="负责人长度必须介于 0 和 100 之间")
	public String getOrgResponsiblePerson() {
		return orgResponsiblePerson;
	}

	public void setOrgResponsiblePerson(String orgResponsiblePerson) {
		this.orgResponsiblePerson = orgResponsiblePerson;
	}
	
	@Length(min=0, max=100, message="职务长度必须介于 0 和 100 之间")
	public String getOrgResponsiblePersonPost() {
		return orgResponsiblePersonPost;
	}

	public void setOrgResponsiblePersonPost(String orgResponsiblePersonPost) {
		this.orgResponsiblePersonPost = orgResponsiblePersonPost;
	}
	
	@Length(min=0, max=100, message="住址长度必须介于 0 和 100 之间")
	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	
	@Length(min=0, max=32, message="联系电话长度必须介于 0 和 32 之间")
	public String getOrgPhone() {
		return orgPhone;
	}

	public void setOrgPhone(String orgPhone) {
		this.orgPhone = orgPhone;
	}
	
	@Length(min=0, max=32, message="姓名长度必须介于 0 和 32 之间")
	public String getPsnName() {
		return psnName;
	}

	public void setPsnName(String psnName) {
		this.psnName = psnName;
	}
	
	@Length(min=0, max=100, message="工作单位长度必须介于 0 和 100 之间")
	public String getPsnOrganization() {
		return psnOrganization;
	}

	public void setPsnOrganization(String psnOrganization) {
		this.psnOrganization = psnOrganization;
	}
	
	@Length(min=0, max=32, message="身份证长度必须介于 0 和 32 之间")
	public String getPsnCode() {
		return psnCode;
	}

	public void setPsnCode(String psnCode) {
		this.psnCode = psnCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPsnBirthday() {
		return psnBirthday;
	}

	public void setPsnBirthday(Date psnBirthday) {
		this.psnBirthday = psnBirthday;
	}
	
	@Length(min=0, max=100, message="职务长度必须介于 0 和 100 之间")
	public String getPsnPost() {
		return psnPost;
	}

	public void setPsnPost(String psnPost) {
		this.psnPost = psnPost;
	}
	
	@Length(min=0, max=100, message="住址长度必须介于 0 和 100 之间")
	public String getPsnAddress() {
		return psnAddress;
	}

	public void setPsnAddress(String psnAddress) {
		this.psnAddress = psnAddress;
	}
	
	@Length(min=0, max=32, message="联系电话长度必须介于 0 和 32 之间")
	public String getPsnPhone() {
		return psnPhone;
	}

	public void setPsnPhone(String psnPhone) {
		this.psnPhone = psnPhone;
	}
	
	@Length(min=0, max=8, message="性别长度必须介于 0 和 8 之间")
	public String getPsnSex() {
		return psnSex;
	}

	public void setPsnSex(String psnSex) {
		this.psnSex = psnSex;
	}
	
	@Length(min=1, max=32, message="案发时间长度必须介于 1 和 32 之间")
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
	
	@Length(min=0, max=32, message="案件所涉项目代码长度必须介于 0 和 32 之间")
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
	
	@Length(min=0, max=32, message="状态长度必须介于 0 和 32 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=32, message="经办单位长度必须介于 0 和 32 之间")
	public String getHandleOrg() {
		return handleOrg;
	}

	public void setHandleOrg(String handleOrg) {
		this.handleOrg = handleOrg;
	}
	
	@ExcelField(title="当事人", type=1, align=2, sort=30)
	public String getPartyDisplay(){
		return Global.PartyTypeOrg.equals(this.partyType)? this.orgName:this.psnName;
	}
	
	public String getPartyCode(){
		return Global.PartyTypeOrg.equals(this.partyType)? this.orgCode:this.psnCode;
	}
	
	public String getPartyPhone(){
		return Global.PartyTypeOrg.equals(this.partyType)? this.orgPhone:this.psnPhone;
	}
	
	public String getPartyAddress(){
		return Global.PartyTypeOrg.equals(this.partyType)? this.orgAddress:this.psnAddress;
	}	
	
	@Length(min=0, max=32, message="决定书编号长度必须介于 0 和 32 之间")
	public String getDecisionSeq() {
		return decisionSeq;
	}

	public void setDecisionSeq(String decisionSeq) {
		this.decisionSeq = decisionSeq;
	}
	
	public String getPunishMoney() {
		return punishMoney;
	}

	public void setPunishMoney(String punishMoney) {
		this.punishMoney = punishMoney;
	}
	
	@Length(min=0, max=300, message="立案审批表长度必须介于 0 和 300 之间")
	public String getFilepathInitial() {
		return filepathInitial;
	}

	public void setFilepathInitial(String filepathInitial) {
		this.filepathInitial = filepathInitial;
	}
	
	public String getFilenameInitial(){
		return this.parseFilename(this.filepathInitial);
	}
	
	@Length(min=0, max=300, message="决定书长度必须介于 0 和 300 之间")
	public String getFilepathDecision() {
		return filepathDecision;
	}

	public void setFilepathDecision(String filepathDecision) {
		this.filepathDecision = filepathDecision;
	}
	
	public String getFilenameDecision(){
		return this.parseFilename(this.filepathDecision);
	}
	
	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	private String parseFilename(String filepath){
		String filename = "";
		if(!StringUtils.isEmpty(filepath)){
			filename = filepath.substring(filepath.lastIndexOf("/")+1);
		}
		return PathUtils.decodeUri(filename);
	}
	
}