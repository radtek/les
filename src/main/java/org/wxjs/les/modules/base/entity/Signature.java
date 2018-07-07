/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.entity;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.wxjs.les.common.persistence.DataEntity;
import org.wxjs.les.common.utils.IdGen;

/**
 * 签名Entity
 * @author GLQ
 * @version 2018-07-06
 */
public class Signature extends DataEntity<Signature> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 头
	private String signature;		// 签名
	
	public Signature() {
		super();
		this.initialId();
	}

	public Signature(String id){
		super(id);
	}

	@Length(min=1, max=100, message="头长度必须介于 1 和 100 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public String getData(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("data:")
		.append(this.title)
		.append(",")
		.append(this.signature);
		return buffer.toString();
	}
	
	public void initialId(){
		if(StringUtils.isBlank(this.id)){
			this.id = IdGen.uuid();
		}
	}
	
}