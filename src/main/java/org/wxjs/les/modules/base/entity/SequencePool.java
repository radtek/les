/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.entity;

import org.hibernate.validator.constraints.Length;

import org.wxjs.les.common.persistence.DataEntity;

/**
 * 顺序号Entity
 * @author GLQ
 * @version 2018-07-09
 */
public class SequencePool extends DataEntity<SequencePool> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 关键字
	private String reuseid;		// 编号
	
	public SequencePool() {
		super();
	}

	@Length(min=0, max=100, message="关键字长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReuseid() {
		return reuseid;
	}

	public void setReuseid(String reuseid) {
		this.reuseid = reuseid;
	}
	
}