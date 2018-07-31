/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.cr.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.wxjs.les.common.persistence.DataEntity;
import org.wxjs.les.modules.base.entity.Signature;

/**
 * 现场检查笔录Entity
 * @author 千里目
 * @version 2018-07-27
 */
public class SiteCheckRecord extends DataEntity<SiteCheckRecord> {
	
	private static final long serialVersionUID = 1L;
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
	private String siteSituation;		// 现场检查记录情况
	private String sitePicture;		// 现场勘察示意图
	private String sitePictureMemo;		// 示意图说明
	
	private Signature partySig = new Signature();		// 当事人签名
	private Signature witnessSig = new Signature(); // 见证人签名
	private Signature checkerSig = new Signature();		// 勘查人签名
	private Signature recorderSig = new Signature();		// 记录人签名
	
	public SiteCheckRecord() {
		super();
	}

	public SiteCheckRecord(String id){
		super(id);
	}

	@Length(min=1, max=32, message="当事人类型长度必须介于 1 和 32 之间")
	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}
	
//	public String getParty(){
//		return "单位".equals(this.partyType)? this.orgName:this.psnName;
//	}
	
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
	
	public String getSiteSituation() {
		return siteSituation;
	}

	public void setSiteSituation(String siteSituation) {
		this.siteSituation = siteSituation;
	}
	
	@Length(min=1, max=500, message="现场勘察示意图长度必须介于 1 和 500 之间")
	public String getSitePicture() {
		return sitePicture;
	}

	public void setSitePicture(String sitePicture) {
		this.sitePicture = sitePicture;
	}
	
	@Length(min=1, max=500, message="示意图说明长度必须介于 1 和 500 之间")
	public String getSitePictureMemo() {
		return sitePictureMemo;
	}

	public void setSitePictureMemo(String sitePictureMemo) {
		this.sitePictureMemo = sitePictureMemo;
	}

	public Signature getPartySig() {
		return partySig;
	}

	public void setPartySig(Signature partySig) {
		this.partySig = partySig;
	}

	public Signature getWitnessSig() {
		return witnessSig;
	}

	public void setWitnessSig(Signature witnessSig) {
		this.witnessSig = witnessSig;
	}

	public Signature getCheckerSig() {
		return checkerSig;
	}

	public void setCheckerSig(Signature checkerSig) {
		this.checkerSig = checkerSig;
	}

	public Signature getRecorderSig() {
		return recorderSig;
	}

	public void setRecorderSig(Signature recorderSig) {
		this.recorderSig = recorderSig;
	}
}