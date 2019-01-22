/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.entity;

import org.hibernate.validator.constraints.Length;

import org.wxjs.les.common.persistence.DataEntity;

/**
 * 处罚扣分事项Entity
 * @author GLQ
 * @version 2019-01-18
 */
public class DeductionMatter extends DataEntity<DeductionMatter> {
	
	private static final long serialVersionUID = 1L;
	private String matterCode;		// 扣分事项代码
	private String punishType;		// 处罚类型
	private String projectType;		// 信用扣分类别
	private String matter;		// 扣分事项
	
	public DeductionMatter() {
		super();
	}

	public DeductionMatter(String id){
		super(id);
	}

	@Length(min=1, max=64, message="扣分事项代码长度必须介于 1 和 64 之间")
	public String getMatterCode() {
		return matterCode;
	}

	public void setMatterCode(String matterCode) {
		this.matterCode = matterCode;
	}
	
	@Length(min=1, max=64, message="处罚类型长度必须介于 1 和 64 之间")
	public String getPunishType() {
		return punishType;
	}

	public void setPunishType(String punishType) {
		this.punishType = punishType;
	}
	
	@Length(min=1, max=64, message="信用扣分类别长度必须介于 1 和 64 之间")
	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	
	@Length(min=1, max=200, message="扣分事项长度必须介于 1 和 200 之间")
	public String getMatter() {
		return matter;
	}

	public void setMatter(String matter) {
		this.matter = matter;
	}
	
}