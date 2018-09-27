/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.entity;

import org.hibernate.validator.constraints.Length;

import org.wxjs.les.common.persistence.DataEntity;
import org.wxjs.les.common.utils.StringUtils;
import org.wxjs.les.modules.sys.entity.User;

/**
 * 签名库Entity
 * @author GLQ
 * @version 2018-09-12
 */
public class SignatureLib extends DataEntity<SignatureLib> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 拥有者
	private String title;		// 头
	private String signature;		// 签名
	
	private String filepath;
	
	public SignatureLib() {
		super();
	}

	public SignatureLib(String id){
		super(id);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getData(){
		StringBuffer buffer = new StringBuffer();
		if(StringUtils.isNotEmpty(this.signature)){
			buffer.append("data:")
			.append(this.title)
			.append(",")
			.append(this.signature);			
		}
		return buffer.toString();
	}
	
}