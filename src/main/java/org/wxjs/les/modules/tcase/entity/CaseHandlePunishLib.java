/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.entity;

import org.hibernate.validator.constraints.Length;
import org.wxjs.les.common.persistence.DataEntity;
import org.wxjs.les.modules.base.entity.PunishLib;

/**
 * 案件裁量权Entity
 * @author GLQ
 * @version 2018-07-30
 */
public class CaseHandlePunishLib extends DataEntity<CaseHandlePunishLib> {
	
	private static final long serialVersionUID = 1L;
	private String caseId;		// 案件编号
	private PunishLib punishLib;		// 裁量权编号
	
	//临时属性
	private String paramUri;		// uri
	
	public CaseHandlePunishLib() {
		super();
	}

	public CaseHandlePunishLib(String id){
		super(id);
	}

	@Length(min=1, max=32, message="案件编号长度必须介于 1 和 32 之间")
	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public PunishLib getPunishLib() {
		return punishLib;
	}

	public void setPunishLib(PunishLib punishLib) {
		this.punishLib = punishLib;
	}
	
	public String getParamUri() {
		return this.paramUri==null?"":this.paramUri.replaceAll("&amp;", "&");
	}

	public void setParamUri(String paramUri) {
		this.paramUri = paramUri;
	}
	
}