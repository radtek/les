/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.base.entity.SignatureLib;
import org.wxjs.les.modules.base.dao.SignatureLibDao;

/**
 * 签名库Service
 * @author GLQ
 * @version 2018-09-12
 */
@Service
@Transactional(readOnly = true)
public class SignatureLibService extends CrudService<SignatureLibDao, SignatureLib> {

	public SignatureLib get(String id) {
		return super.get(id);
	}
	
	public List<SignatureLib> findList(SignatureLib signatureLib) {
		return super.findList(signatureLib);
	}
	
	public Page<SignatureLib> findPage(Page<SignatureLib> page, SignatureLib signatureLib) {
		return super.findPage(page, signatureLib);
	}
	
	@Transactional(readOnly = false)
	public void save(SignatureLib signatureLib) {
		//super.save(signatureLib);
		signatureLib.preUpdate();
		int effects = dao.update(signatureLib);
		
		if (effects == 0){
			signatureLib.preInsert();
			dao.insert(signatureLib);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(SignatureLib signatureLib) {
		super.delete(signatureLib);
	}
	
}