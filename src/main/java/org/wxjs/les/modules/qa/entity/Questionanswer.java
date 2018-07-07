/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.qa.entity;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

import com.google.common.collect.Lists;

import org.wxjs.les.common.persistence.DataEntity;
import org.wxjs.les.common.utils.IdGen;
import org.wxjs.les.modules.base.entity.Signature;

/**
 * 询问笔录Entity
 * @author GLQ
 * @version 2018-07-02
 */
public class Questionanswer extends DataEntity<Questionanswer> {
	
	private static final long serialVersionUID = 1L;
	private String caseCause;		// 案由
	private Date fromDate;		// 开始时间
	private Date toDate;		// 结束时间
	private String location;		// 地点
	private String quizzer;		// 调查询问人
	private String recorder;		// 记录人
	private String answerer;		// 被询问人
	private String answererSex;		// 性别
	private String answererCode;		// 身份证
	private String answererOrganization;		// 工作单位
	private String answererPost;		// 职务
	private Date answererBirthday;		// 出生年月
	private String answererAddress;		// 住址
	private String answererPhone;		// 联系电话
	private String zipCode;		// 身份证
	private String qaContent;
	private Signature qsig = new Signature();
	private Signature asig = new Signature();
	
	public Questionanswer() {
		super();
	}

	public Questionanswer(String id){
		super(id);
	}

	@Length(min=1, max=200, message="案由长度必须介于 1 和 200 之间")
	public String getCaseCause() {
		return caseCause;
	}

	public void setCaseCause(String caseCause) {
		this.caseCause = caseCause;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	@Length(min=1, max=100, message="地点长度必须介于 1 和 100 之间")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	@Length(min=0, max=32, message="调查询问人长度必须介于 0 和 32 之间")
	public String getQuizzer() {
		return quizzer;
	}

	public void setQuizzer(String quizzer) {
		this.quizzer = quizzer;
	}
	
	@Length(min=0, max=32, message="记录人长度必须介于 0 和 32 之间")
	public String getRecorder() {
		return recorder;
	}

	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}
	
	@Length(min=0, max=32, message="被询问人长度必须介于 0 和 32 之间")
	public String getAnswerer() {
		return answerer;
	}

	public void setAnswerer(String answerer) {
		this.answerer = answerer;
	}
	
	@Length(min=1, max=8, message="性别长度必须介于 1 和 8 之间")
	public String getAnswererSex() {
		return answererSex;
	}

	public void setAnswererSex(String answererSex) {
		this.answererSex = answererSex;
	}
	
	@Length(min=1, max=32, message="身份证长度必须介于 1 和 32 之间")
	public String getAnswererCode() {
		return answererCode;
	}

	public void setAnswererCode(String answererCode) {
		this.answererCode = answererCode;
	}
	
	@Length(min=1, max=100, message="工作单位长度必须介于 1 和 100 之间")
	public String getAnswererOrganization() {
		return answererOrganization;
	}

	public void setAnswererOrganization(String answererOrganization) {
		this.answererOrganization = answererOrganization;
	}
	
	@Length(min=1, max=100, message="职务长度必须介于 1 和 100 之间")
	public String getAnswererPost() {
		return answererPost;
	}

	public void setAnswererPost(String answererPost) {
		this.answererPost = answererPost;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAnswererBirthday() {
		return answererBirthday;
	}

	public void setAnswererBirthday(Date answererBirthday) {
		this.answererBirthday = answererBirthday;
	}
	
	@Length(min=1, max=100, message="住址长度必须介于 1 和 100 之间")
	public String getAnswererAddress() {
		return answererAddress;
	}

	public void setAnswererAddress(String answererAddress) {
		this.answererAddress = answererAddress;
	}
	
	@Length(min=1, max=32, message="联系电话长度必须介于 1 和 32 之间")
	public String getAnswererPhone() {
		return answererPhone;
	}

	public void setAnswererPhone(String answererPhone) {
		this.answererPhone = answererPhone;
	}
	
	@Length(min=1, max=32, message="身份证长度必须介于 1 和 32 之间")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getQaContent() {
		return qaContent;
	}

	public void setQaContent(String qaContent) {
		this.qaContent = qaContent;
	}

	public Signature getQsig() {
		return qsig;
	}

	public void setQsig(Signature qsig) {
		this.qsig = qsig;
	}

	public Signature getAsig() {
		return asig;
	}

	public void setAsig(Signature asig) {
		this.asig = asig;
	}
	
}