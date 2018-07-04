/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.qa.entity;

import org.hibernate.validator.constraints.Length;

import org.wxjs.les.common.persistence.DataEntity;

/**
 * 询问笔录Entity
 * @author GLQ
 * @version 2018-07-02
 */
public class QuestionanswerItem extends DataEntity<QuestionanswerItem> {
	
	private static final long serialVersionUID = 1L;
	private Questionanswer qaId;		// 询问笔录编号 父类
	private String qaContent;		// 问答
	
	public QuestionanswerItem() {
		super();
	}

	public QuestionanswerItem(String id){
		super(id);
	}

	public QuestionanswerItem(Questionanswer qaId){
		this.qaId = qaId;
	}

	@Length(min=1, max=11, message="询问笔录编号长度必须介于 1 和 11 之间")
	public Questionanswer getQaId() {
		return qaId;
	}

	public void setQaId(Questionanswer qaId) {
		this.qaId = qaId;
	}
	
	@Length(min=1, max=500, message="问答长度必须介于 1 和 500 之间")
	public String getQaContent() {
		return qaContent;
	}

	public void setQaContent(String qaContent) {
		this.qaContent = qaContent;
	}
	
}