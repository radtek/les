/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.entity;

import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import org.wxjs.les.common.persistence.DataEntity;

/**
 * 处罚基准库Entity
 * @author GLQ
 * @version 2018-07-29
 */
public class PunishLib extends DataEntity<PunishLib> {
	
	private static final long serialVersionUID = 1L;
	private String seq;		// 编号
	private String behavior;		// 行为名称
	private String lawBasis;		// 法律依据
	private String punishType;		// 处罚种类
	private List<PunishLibRange> punishLibRangeList = Lists.newArrayList();		// 子表列表
	
	
	private String filepath;
	
	public PunishLib() {
		super();
	}

	public PunishLib(String id){
		super(id);
	}

	@Length(min=0, max=32, message="编号长度必须介于 0 和 32 之间")
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	@Length(min=1, max=200, message="行为名称长度必须介于 1 和 200 之间")
	public String getBehavior() {
		return behavior;
	}

	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}
	
	public String getLawBasis() {
		return lawBasis;
	}

	public void setLawBasis(String lawBasis) {
		this.lawBasis = lawBasis;
	}
	
	@Length(min=1, max=200, message="处罚种类长度必须介于 1 和 200 之间")
	public String getPunishType() {
		return punishType;
	}

	public void setPunishType(String punishType) {
		this.punishType = punishType;
	}
	
	public List<PunishLibRange> getPunishLibRangeList() {
		return punishLibRangeList;
	}

	public void setPunishLibRangeList(List<PunishLibRange> punishLibRangeList) {
		this.punishLibRangeList = punishLibRangeList;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	
}