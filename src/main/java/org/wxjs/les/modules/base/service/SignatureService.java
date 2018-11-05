/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.base.entity.Signature;

import com.google.common.collect.Lists;

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
	
	public Signature getByTaskId(String taskId) {
		return dao.getByTaskId(taskId);
	}
	
	public Signature getLatestSignatureByLoginName(String loginName){
		return dao.getLatestSignatureByLoginName(loginName);
	}
	
	public List<Signature> findList(Signature signature) {
		return super.findList(signature);
	}
	
	public List<Signature> findList4Export(Signature signature) {
		List<Signature> list = super.findList(signature);
		//filter, for example, it was ever returned, previous signatures should be abandoned
		Map<String, Signature> map = new LinkedHashMap<String, Signature>();
		for(Signature entity : list){
			map.put(this.getKeyOfTaskCreateBy(entity), entity);
		}
		Collection<Signature> col = map.values();
		List<Signature> rst = Lists.newArrayList();
		rst.addAll(col);
		
		return rst;
	}
	
	private String getKeyOfTaskCreateBy(Signature entity){
		return entity.getTaskName()+"_"+entity.getCreateBy().getId();
	}
	
	public Page<Signature> findPage(Page<Signature> page, Signature signature) {
		return super.findPage(page, signature);
	}
	
	@Transactional(readOnly = false)
	public void save(Signature signature) {
		
		logger.debug("1 signature.getId():{}", signature.getId());
		
		signature.preUpdate();
		int effects = dao.update(signature);
		if(effects == 0){
			signature.setIsNewRecord(true);
			signature.preInsert();
			logger.debug("2 signature.getId():{}", signature.getId());
			dao.insert(signature);
		}
	}
	
	@Transactional(readOnly = false)
	public void updateOpinion(Signature signature) {
		dao.updateOpinion(signature);
	}
	
	@Transactional(readOnly = false)
	public void delete(Signature signature) {
		super.delete(signature);
	}
	
}