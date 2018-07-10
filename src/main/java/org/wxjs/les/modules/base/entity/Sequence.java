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
public class Sequence extends DataEntity<Sequence> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 关键字
	private String nextid;		// 编号
	
	public Sequence() {
		super();
	}

	public Sequence(String id){
		super(id);
	}

	@Length(min=0, max=100, message="关键字长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=11, message="编号长度必须介于 0 和 11 之间")
	public String getNextid() {
		return nextid;
	}

	public void setNextid(String nextid) {
		this.nextid = nextid;
	}
	
}