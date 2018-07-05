/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.qa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.common.utils.StringUtils;
import org.wxjs.les.modules.qa.entity.Questionanswer;
import org.wxjs.les.modules.qa.dao.QuestionanswerDao;

/**
 * 询问笔录Service
 * @author GLQ
 * @version 2018-07-02
 */
@Service
@Transactional(readOnly = true)
public class QuestionanswerService extends CrudService<QuestionanswerDao, Questionanswer> {
	
	public Questionanswer get(String id) {
		Questionanswer questionanswer = super.get(id);
		return questionanswer;
	}
	
	public List<Questionanswer> findList(Questionanswer questionanswer) {
		return super.findList(questionanswer);
	}
	
	public Page<Questionanswer> findPage(Page<Questionanswer> page, Questionanswer questionanswer) {
		return super.findPage(page, questionanswer);
	}
	
	@Transactional(readOnly = false)
	public void save(Questionanswer questionanswer) {
		super.save(questionanswer);
	}
	
	@Transactional(readOnly = false)
	public void saveInfo(Questionanswer questionanswer) {
		super.save(questionanswer);
	}
	
	@Transactional(readOnly = false)
	public void saveQa(Questionanswer questionanswer) {
		dao.updateQa(questionanswer);
	}
	
	@Transactional(readOnly = false)
	public void delete(Questionanswer questionanswer) {
		super.delete(questionanswer);
	}
	
}