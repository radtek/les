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
import org.wxjs.les.common.utils.Util;

/**
 * 案件结束Entity
 * @author GLQ
 * @version 2018-07-24
 */
public class CaseFinish extends DataEntity<CaseFinish> {
	
	private static final long serialVersionUID = 1L;
	private String totalPage;		// 合计页数
	private String wordPage;		// 文字页数
	private String diagramPage;		// 图样页数
	private String photoPage;		// 照片页数
	private String otherPage;		// 其他页数
	private String caseId;		// 案件编号
	private String handleSummary;		// 说明
	private Date finishDate;		// 备考表时间
	
	//临时属性
	private String paramUri;		// uri
	
	public CaseFinish() {
		super();
	}

	public CaseFinish(String id){
		super(id);
	}
	
	public static CaseFinish getInstance(Tcase tcase){
		CaseFinish entity = new CaseFinish();
		
		if(tcase!=null){
			entity.setCaseId(tcase.getId());
			entity.setFinishDate(Calendar.getInstance().getTime());
		}
		
		return entity;
	}

	@Length(min=0, max=11, message="合计页数长度必须介于 0 和 11 之间")
	public String getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}
	
	@Length(min=0, max=11, message="文字页数长度必须介于 0 和 11 之间")
	public String getWordPage() {
		return wordPage;
	}

	public void setWordPage(String wordPage) {
		this.wordPage = wordPage;
	}
	
	@Length(min=0, max=11, message="图样页数长度必须介于 0 和 11 之间")
	public String getDiagramPage() {
		return diagramPage;
	}

	public void setDiagramPage(String diagramPage) {
		this.diagramPage = diagramPage;
	}
	
	@Length(min=0, max=11, message="照片页数长度必须介于 0 和 11 之间")
	public String getPhotoPage() {
		return photoPage;
	}

	public void setPhotoPage(String photoPage) {
		this.photoPage = photoPage;
	}
	
	@Length(min=0, max=11, message="其他页数长度必须介于 0 和 11 之间")
	public String getOtherPage() {
		return otherPage;
	}

	public void setOtherPage(String otherPage) {
		this.otherPage = otherPage;
	}
	
	@Length(min=1, max=32, message="案件编号长度必须介于 1 和 32 之间")
	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
	public String getHandleSummary() {
		return handleSummary;
	}

	public void setHandleSummary(String handleSummary) {
		this.handleSummary = handleSummary;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	
	public int getPageSum(){
		int rst = 0;
		
		rst += Util.getInteger(this.wordPage, 0);
		rst += Util.getInteger(this.diagramPage, 0);
		rst += Util.getInteger(this.photoPage, 0);
		rst += Util.getInteger(this.otherPage, 0);
		
		return rst;
	}

	public String getParamUri() {
		return this.paramUri==null?"":this.paramUri.replaceAll("&amp;", "&");
	}

	public void setParamUri(String paramUri) {
		this.paramUri = paramUri;
	}
	
}