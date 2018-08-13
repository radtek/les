/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.entity;

import org.hibernate.validator.constraints.Length;

import org.wxjs.les.common.persistence.DataEntity;

/**
 * 文档类型Entity
 * @author GLQ
 * @version 2018-08-13
 */
public class CaseDocType extends DataEntity<CaseDocType> {
	
	private static final long serialVersionUID = 1L;
	private String caseStage;		// 阶段
	private String documentType;		// 文档类型
	private String mandatory;		// 是否必须
	private String uploadMethod;		// 上传方式
	
	public CaseDocType() {
		super();
	}

	public CaseDocType(String id){
		super(id);
	}

	@Length(min=1, max=64, message="阶段长度必须介于 1 和 64 之间")
	public String getCaseStage() {
		return caseStage;
	}

	public void setCaseStage(String caseStage) {
		this.caseStage = caseStage;
	}
	
	@Length(min=1, max=64, message="文档类型长度必须介于 1 和 64 之间")
	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	@Length(min=1, max=8, message="是否必须长度必须介于 1 和 8 之间")
	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}
	
	@Length(min=1, max=16, message="上传方式长度必须介于 1 和 16 之间")
	public String getUploadMethod() {
		return uploadMethod;
	}

	public void setUploadMethod(String uploadMethod) {
		this.uploadMethod = uploadMethod;
	}
	
}