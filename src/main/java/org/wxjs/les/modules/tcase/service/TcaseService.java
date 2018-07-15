/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.sys.utils.SequenceUtils;
import org.wxjs.les.modules.tcase.entity.CaseProcess;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.dao.CaseProcessDao;
import org.wxjs.les.modules.tcase.dao.TcaseDao;

import com.google.common.collect.Lists;

/**
 * 案件Service
 * @author GLQ
 * @version 2018-07-09
 */
@Service
@Transactional(readOnly = true)
public class TcaseService extends CrudService<TcaseDao, Tcase> {
	@Autowired
	private	CaseProcessDao caseProcessDao;

	public Tcase get(String id) {
		Tcase tcase = super.get(id);
		
		//fill current process info
		if(tcase!=null){
			CaseProcess caseProcessParam = new CaseProcess();
			caseProcessParam.setCaseId(tcase.getId());
			List<CaseProcess> processes = caseProcessDao.findCurrentProcesses(caseProcessParam);
			
			for(CaseProcess caseProcess : processes){
				tcase.getCurrentCaseProcess().add(caseProcess);
			}			
		}
		
		return tcase;
	}
	
	public List<Tcase> findList(Tcase tcase) {

		List<Tcase> rst = Lists.newArrayList();
		List<Tcase> list = super.findList(tcase);
		
		//get current process list
		List<CaseProcess> currentProcesses = caseProcessDao.findCurrentProcesses(new CaseProcess());
		for(Tcase entity : list){
			logger.debug("entity.getCurrentCaseProcess().size():{}", entity.getCurrentCaseProcess().size()+"");
			for(CaseProcess process : currentProcesses){
				if(process.getCaseId().equals(entity.getId())){
					entity.getCurrentCaseProcess().add(process);
				}
			}
			logger.debug("after handle, entity.getCurrentCaseProcess().size():{}", entity.getCurrentCaseProcess().size()+"");
			rst.add(entity);
		}
		
		return rst;
	}
	
	public Page<Tcase> findPage(Page<Tcase> page, Tcase tcase) {
		tcase.setPage(page);
		page.setList(this.findList(tcase));
		return page;		
	}
	
	@Transactional(readOnly = false)
	public void save(Tcase tcase) {
		boolean isNew = tcase.getIsNewRecord();
		if(isNew){
			tcase.setCaseSeq(SequenceUtils.fetchCaseSeqStr());
			logger.debug("tcase.getCaseSeq():{}",tcase.getCaseSeq());
		}
		super.save(tcase);
		
		//init process
		if(isNew){
			CaseProcess caseProcess = new CaseProcess();
			caseProcess.setCaseId(tcase.getId());
			caseProcessDao.initProcess(caseProcess);
			
			//load tcase again for Case
			Tcase tcaseNew = this.get(tcase.getId());
			tcase.getCaseProcess().setId(tcaseNew.getCaseProcess().getId());
			tcase.getCaseProcess().setCaseId(tcaseNew.getCaseProcess().getCaseId());
			tcase.getCaseProcess().setCaseStage(tcaseNew.getCaseProcess().getCaseStage());
		}
		
		caseProcessDao.update(tcase.getCaseProcess());
		
	}
	
	@Transactional(readOnly = false)
	public void delete(Tcase tcase) {
		super.delete(tcase);
	}
	
}