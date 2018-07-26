/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.entity;

import org.hibernate.validator.constraints.Length;

import org.wxjs.les.common.persistence.DataEntity;

/**
 * 案件资料Entity
 * @author GLQ
 * @version 2018-07-09
 */
public class CaseAttach extends DataEntity<CaseAttach> {
	
	private static final long serialVersionUID = 1L;
	private String caseId;		// 案件编号
	private String attachType;		// 资料类型
	private String filename;		// 文件名称
	private String filepath;		// 保存路径
	private String flowNode;		// 流程点
	
	private String paramUri;
	
	public CaseAttach() {
		super();
	}

	public CaseAttach(String id){
		super(id);
	}

	@Length(min=1, max=32, message="案件编号长度必须介于 1 和 32 之间")
	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
	@Length(min=1, max=100, message="资料类型长度必须介于 1 和 100 之间")
	public String getAttachType() {
		return attachType;
	}

	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}
	
	@Length(min=1, max=100, message="文件名称长度必须介于 1 和 100 之间")
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@Length(min=1, max=300, message="保存路径长度必须介于 1 和 300 之间")
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	@Length(min=1, max=32, message="流程点长度必须介于 1 和 32 之间")
	public String getFlowNode() {
		return flowNode;
	}

	public void setFlowNode(String flowNode) {
		this.flowNode = flowNode;
	}

	public String getParamUri() {
		return this.paramUri==null?"":this.paramUri.replaceAll("&amp;", "&");
	}

	public void setParamUri(String paramUri) {
		this.paramUri = paramUri;
	}
	
}