/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.entity;

import org.hibernate.validator.constraints.Length;

import org.wxjs.les.common.persistence.DataEntity;

/**
 * 发文字号Entity
 * @author GLQ
 * @version 2019-03-14
 */
public class DocumentHead extends DataEntity<DocumentHead> {
	
	private static final long serialVersionUID = 1L;
	private String areaId;		// 区域代码
	private String handleOrg;		// 经办单位
	private String stage;		// 阶段：decision, notify
	private String type;		// 类型
	private String sort;
	private String docHead;		// 发文字号
	
	public DocumentHead() {
		super();
	}

	public DocumentHead(String id){
		super(id);
	}

	@Length(min=1, max=32, message="区域代码长度必须介于 1 和 32 之间")
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	@Length(min=1, max=32, message="经办单位长度必须介于 1 和 32 之间")
	public String getHandleOrg() {
		return handleOrg;
	}

	public void setHandleOrg(String handleOrg) {
		this.handleOrg = handleOrg;
	}
	
	@Length(min=1, max=32, message="阶段：decision, notify长度必须介于 1 和 32 之间")
	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}
	
	@Length(min=1, max=32, message="类型长度必须介于 1 和 32 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Length(min=1, max=32, message="发文字号长度必须介于 1 和 32 之间")
	public String getDocHead() {
		return docHead;
	}

	public void setDocHead(String docHead) {
		this.docHead = docHead;
	}
	
}