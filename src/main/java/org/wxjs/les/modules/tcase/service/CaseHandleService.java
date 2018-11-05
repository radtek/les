/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.base.dao.PunishLibRangeDao;
import org.wxjs.les.modules.base.entity.PunishLib;
import org.wxjs.les.modules.base.entity.PunishLibRange;
import org.wxjs.les.modules.sys.dao.UserDao;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.tcase.entity.CaseHandle;
import org.wxjs.les.modules.tcase.entity.CaseHandlePunishLib;
import org.wxjs.les.modules.tcase.dao.CaseHandleDao;

/**
 * 案件审理Service
 * @author GLQ
 * @version 2018-07-24
 */
@Service
@Transactional(readOnly = true)
public class CaseHandleService extends CrudService<CaseHandleDao, CaseHandle> {
	
	@Autowired
	private	UserDao userDao;
	
	@Autowired
	private CaseHandlePunishLibService caseHandlePunishLibService;
	
	@Autowired
	private PunishLibRangeDao punishLibRangeDao;

	public CaseHandle get(String caseId) {
		CaseHandle entity = super.get(caseId);
		
		//fill name
		if(entity!=null){
			User param = new User();
			if(entity.getInvestigator()!=null){
				String investigator = entity.getInvestigator().getLoginName();
				param.setLoginName(investigator);
				List<User> list = userDao.findList(param);
				
				StringBuffer buffer = new StringBuffer();
				
				for(User user: list){

					buffer.append(",").append(user.getName());
				}
				
				if(buffer.length()>0){
					entity.getInvestigator().setName(buffer.substring(1));
				}				
			}
		}
		
		return entity;
	}
	
	public List<CaseHandle> findList(CaseHandle caseHandle) {
		return super.findList(caseHandle);
	}
	
	public Page<CaseHandle> findPage(Page<CaseHandle> page, CaseHandle caseHandle) {
		return super.findPage(page, caseHandle);
	}
	
	@Transactional(readOnly = false)
	public void save(CaseHandle caseHandle) {
		super.save(caseHandle);
	}
	
	@Transactional(readOnly = false)
	public void saveUploadInfo(CaseHandle entity) {
		if (entity.getIsNewRecord()){
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.updateUploadInfo(entity); 
		}
		
		//update range lib
		//clear
		caseHandlePunishLibService.deleteByCaseId(entity.getCaseId());
		//save
		CaseHandlePunishLib caseHandlePunishLib;
		String rangeIdsStr = entity.getRangeIds();
		if(StringUtils.isNotEmpty(rangeIdsStr)){
			String[] rangeIds = rangeIdsStr.split(",");
			for(String rangeId : rangeIds){

				PunishLibRange punishLibRange = punishLibRangeDao.get(rangeId);
				logger.debug("rangeId:{}, punishLibRange==null:{}", rangeId, punishLibRange==null);
				caseHandlePunishLib = new CaseHandlePunishLib();
				caseHandlePunishLib.setCaseId(entity.getCaseId());
				PunishLib punishLib = new PunishLib();
				punishLib.setId(punishLibRange.getLib().getId());
				caseHandlePunishLib.setPunishLib(punishLib);
				caseHandlePunishLib.setPunishLibRange(punishLibRange);
				caseHandlePunishLib.setIsNewRecord(true);
				caseHandlePunishLibService.save(caseHandlePunishLib);
			}			
		}
	}

	@Transactional(readOnly = false)
	public void saveReport(CaseHandle entity) {
		if (entity.getIsNewRecord()){
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.updateReport(entity);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(CaseHandle caseHandle) {
		super.delete(caseHandle);
	}
	
}