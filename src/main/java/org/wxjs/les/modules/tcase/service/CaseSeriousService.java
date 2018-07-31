/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.common.utils.Util;
import org.wxjs.les.modules.sys.dao.UserDao;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.utils.UserUtils;
import org.wxjs.les.modules.tcase.entity.CaseSerious;
import org.wxjs.les.modules.tcase.dao.CaseAttachDao;
import org.wxjs.les.modules.tcase.dao.CaseSeriousDao;

/**
 * 重大行政处罚Service
 * @author GLQ
 * @version 2018-07-27
 */
@Service
@Transactional(readOnly = true)
public class CaseSeriousService extends CrudService<CaseSeriousDao, CaseSerious> {
	
	@Autowired
	private	UserDao userDao;

	public CaseSerious get(String id) {
		CaseSerious entity = super.get(id);
		
		//fill name
		if(entity!=null){
			List<User> list = userDao.findAllList(new User());
			String masters = "," + entity.getMaster().getLoginName()+",";
			String voters = "," + entity.getVoter().getLoginName()+",";
			String recorders = "," + entity.getRecorder().getLoginName()+",";
			String attendees = "," + entity.getAttendee().getLoginName()+",";
			
			StringBuffer masterBuffer = new StringBuffer();
			StringBuffer voterBuffer = new StringBuffer();
			StringBuffer recorderBuffer = new StringBuffer();
			StringBuffer attendeeBuffer = new StringBuffer();
			
			for(User user: list){

				if(masters.contains("," + user.getLoginName()+",")){
					masterBuffer.append(",").append(user.getName());
				}
				if(voters.contains("," + user.getLoginName()+",")){
					voterBuffer.append(",").append(user.getName());
				}
				if(recorders.contains("," + user.getLoginName()+",")){
					recorderBuffer.append(",").append(user.getName());
				}
				if(attendees.contains("," + user.getLoginName()+",")){
					attendeeBuffer.append(",").append(user.getName());
				}

			}
			
			if(masterBuffer.length()>0){
				entity.getMaster().setName(masterBuffer.substring(1));
			}
			if(voterBuffer.length()>0){
				entity.getVoter().setName(voterBuffer.substring(1));
			}
			if(recorderBuffer.length()>0){
				entity.getRecorder().setName(recorderBuffer.substring(1));
			}
			if(attendeeBuffer.length()>0){
				entity.getAttendee().setName(attendeeBuffer.substring(1));
			}

		}
		
		return entity;
	}
	
	public List<CaseSerious> findList(CaseSerious caseSerious) {
		return super.findList(caseSerious);
	}
	
	public Page<CaseSerious> findPage(Page<CaseSerious> page, CaseSerious caseSerious) {
		return super.findPage(page, caseSerious);
	}
	
	@Transactional(readOnly = false)
	public void save(CaseSerious caseSerious) {
		super.save(caseSerious);
	}
	
	@Transactional(readOnly = false)
	public void delete(CaseSerious caseSerious) {
		super.delete(caseSerious);
	}
	
}