/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.DataEntity;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.Util;
import org.wxjs.les.modules.base.utils.DocumentHeadUtils;
import org.wxjs.les.modules.sys.utils.DictUtils;

/**
 * 案件决定书Entity
 * @author GLQ
 * @version 2018-07-24
 */
public class CaseDecision extends DataEntity<CaseDecision> {
	
	private static final long serialVersionUID = 1L;
	private String caseId;		// 案件编号
	private String decisionType;		// 处罚书类型
	private String year;		// 年份
	private String seq = "";		// 流水号
	private String recordOrg;		// 备案单位
	private String compileDate;		// 拟稿日期
	private String printCount;		// 印数
	private String destinationAddress;		// 送达地点
	private String partyName;		// 名称
	private String content;		// 正文
	private String punishMoney;		// 实际罚款金额（元）
	private String launchDept;		// 发证部门
	private Date launchDate;		// 发证时间
	
	//临时属性
	private String areaId;
	private String handleOrg;
	
	
	private String paramUri;		// uri
	
	public CaseDecision() {
		super();
	}

	public CaseDecision(String id){
		super(id);
	}
	
	public static CaseDecision getInstance(Tcase tcase){
		CaseDecision entity = new CaseDecision();
		
		if(tcase!=null){
			entity.setCaseId(tcase.getId());
			entity.setPartyName(tcase.getPartyDisplay());
			entity.setLaunchDept(Global.getConfig("defaultLaunchDept"));
			entity.setRecordOrg(Global.getConfig("defaultRecordOrg"));
			entity.setLaunchDate(Calendar.getInstance().getTime());
			entity.setYear(DateUtils.getYear());
			
			entity.setAreaId(tcase.getAreaId());
			entity.setHandleOrg(tcase.getHandleOrg());
		}
		
		return entity;
	}

	@Length(min=1, max=32, message="案件编号长度必须介于 1 和 32 之间")
	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
	public String getDecisionType() {
		return decisionType;
	}

	public void setDecisionType(String decisionType) {
		this.decisionType = decisionType;
	}

	@Length(min=1, max=8, message="年份长度必须介于 1 和 8 之间")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@Length(min=0, max=8, message="流水号长度必须介于 0 和 8 之间")
	public String getSeq() {
		return Util.fillZeroPre(seq, 3);
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	@Length(min=1, max=64, message="备案单位长度必须介于 1 和 64 之间")
	public String getRecordOrg() {
		return recordOrg;
	}

	public void setRecordOrg(String recordOrg) {
		this.recordOrg = recordOrg;
	}
	
	@Length(min=1, max=64, message="拟稿日期长度必须介于 1 和 64 之间")
	public String getCompileDate() {
		return compileDate;
	}

	public void setCompileDate(String compileDate) {
		this.compileDate = compileDate;
	}
	
	@Length(min=1, max=64, message="印数长度必须介于 1 和 64 之间")
	public String getPrintCount() {
		return printCount;
	}

	public void setPrintCount(String printCount) {
		this.printCount = printCount;
	}
	
	@Length(min=0, max=100, message="送达地点长度必须介于 0 和 100 之间")
	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
	
	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getPunishMoney() {
		return punishMoney;
	}

	public void setPunishMoney(String punishMoney) {
		this.punishMoney = punishMoney;
	}

	@Length(min=1, max=100, message="发证部门长度必须介于 1 和 100 之间")
	public String getLaunchDept() {
		return launchDept;
	}

	public void setLaunchDept(String launchDept) {
		this.launchDept = launchDept;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}
	
	public String getFullDecisionNumber(String areaId, String handleOrg){
		StringBuffer buffer = new StringBuffer();

		buffer.append(DocumentHeadUtils.getDocumentHead(areaId, handleOrg, "decision", this.decisionType));
		
		buffer.append("[");
		buffer.append(this.year);
		buffer.append("]年第(");
		buffer.append(this.seq);
		buffer.append(")号");
		return buffer.toString();
	}
	
	public String getFullDecisionNumberAj(){
		return this.getFullDecisionNumber(this.areaId, "03");
	}
	
	public String getFullDecisionNumberZj(){
		return this.getFullDecisionNumber(this.areaId, "04");
	}
	
	public String getFullDecisionNumber(){
		return this.getFullDecisionNumber(this.areaId, "01");
	}
	
	public String getDocNumber(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		buffer.append(this.year);
		buffer.append("](");
		buffer.append(this.seq);
		buffer.append(")号");
		return buffer.toString();
	}

	public String getParamUri() {
		return this.paramUri==null?"":this.paramUri.replaceAll("&amp;", "&");
	}

	public void setParamUri(String paramUri) {
		this.paramUri = paramUri;
	}
	
	public String getSeqKey(){
		String rst = "";
		
		if(Global.WXAreaCode.equals(this.areaId) && Global.WXZhiduiHandleOrg.equals(this.handleOrg)){
			//to compatible seq for zhidui 
			rst = "DecisionSeq_"+this.getDecisionType()+"_"+this.getYear();
		}else{
			//seq for new
			rst = "DecisionSeq_"+this.getDecisionType()+"_"+this.areaId+"_"+this.handleOrg+"_"+this.getYear();
		}
		
		return rst;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getHandleOrg() {
		return handleOrg;
	}

	public void setHandleOrg(String handleOrg) {
		this.handleOrg = handleOrg;
	}
	
}