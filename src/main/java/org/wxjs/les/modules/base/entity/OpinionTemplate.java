/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.entity;

import org.hibernate.validator.constraints.Length;

import org.wxjs.les.common.persistence.DataEntity;

/**
 * 常用批语Entity
 * @author GLQ
 * @version 2018-07-22
 */
public class OpinionTemplate extends DataEntity<OpinionTemplate> {
	
	private static final long serialVersionUID = 1L;
	private String owner;
	private String opinion;		// 常用批语
	private String sort;		// 排序
	
	public OpinionTemplate() {
		super();
	}

	public OpinionTemplate(String id){
		super(id);
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Length(min=1, max=100, message="常用批语长度必须介于 1 和 100 之间")
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

}