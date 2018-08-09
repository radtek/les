/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.entity;

import java.util.Calendar;

import org.hibernate.validator.constraints.Length;
import org.wxjs.les.common.persistence.DataEntity;

/**
 * 案件撤销Entity
 * @author GLQ
 * @version 2018-08-08
 */
public class CaseCancel extends DataEntity<CaseCancel> {
	
	private static final long serialVersionUID = 1L;
	private String caseId;		// 案件编号
	private String content;		// 内容
	
	//临时
	private String paramUri;
	
	public CaseCancel() {
		super();
	}

	public CaseCancel(String id){
		super(id);
	}
	
	public static CaseCancel getInstance(Tcase tcase){
		CaseCancel entity = new CaseCancel();
		
		if(tcase!=null){
			entity.setCaseId(tcase.getId());
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
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getParamUri() {
		return paramUri;
	}

	public void setParamUri(String paramUri) {
		this.paramUri = paramUri;
	}
	
}