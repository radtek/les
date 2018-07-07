/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.base.entity.Signature;
import org.wxjs.les.modules.base.dao.SignatureDao;

/**
 * 签名Service
 * @author GLQ
 * @version 2018-07-06
 */
@Service
@Transactional(readOnly = true)
public class SignatureService extends CrudService<SignatureDao, Signature> {

	public Signature get(String id) {
		return super.get(id);
	}
	
	public List<Signature> findList(Signature signature) {
		return super.findList(signature);
	}
	
	public Page<Signature> findPage(Page<Signature> page, Signature signature) {
		return super.findPage(page, signature);
	}
	
	@Transactional(readOnly = false)
	public void save(Signature signature) {
		signature.preUpdate();
		int effects = dao.update(signature);
		if(effects == 0){
			signature.setIsNewRecord(true);
			signature.preInsert();
			dao.insert(signature);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Signature signature) {
		super.delete(signature);
	}
	
}