/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.entity;

import org.hibernate.validator.constraints.Length;

import org.wxjs.les.common.persistence.DataEntity;

/**
 * 处罚基准库Entity
 * @author GLQ
 * @version 2018-07-29
 */
public class PunishLibRange extends DataEntity<PunishLibRange> {
	
	private static final long serialVersionUID = 1L;
	private PunishLib lib;		// 处罚编号 父类
	private String situation;		// 情形描述
	private String punishRange;		// 裁量幅度
	
	public PunishLibRange() {
		super();
	}

	public PunishLibRange(String id){
		super(id);
	}

	public PunishLibRange(PunishLib lib){
		this.lib = lib;
	}

	
	public PunishLib getLib() {
		return lib;
	}

	public void setLib(PunishLib lib) {
		this.lib = lib;
	}

	@Length(min=1, max=200, message="情形描述长度必须介于 1 和 200 之间")
	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}
	
	@Length(min=1, max=200, message="裁量幅度长度必须介于 1 和 200 之间")
	public String getPunishRange() {
		return punishRange;
	}

	public void setPunishRange(String punishRange) {
		this.punishRange = punishRange;
	}
	
}