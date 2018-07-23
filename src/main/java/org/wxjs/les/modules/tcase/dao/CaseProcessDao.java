/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.dao;

import java.util.List;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.modules.tcase.entity.CaseProcess;

/**
 * 案件流程DAO接口
 * @author GLQ
 * @version 2018-07-14
 */
@MyBatisDao
public interface CaseProcessDao extends CrudDao<CaseProcess> {
	
	public void initProcess(CaseProcess caseProcess);
	
	public void initProcessExcludeAcceptance(CaseProcess caseProcess);
	
	public List<CaseProcess> findCurrentProcesses(CaseProcess caseProcess);
	
	public void updateProcInfo(CaseProcess caseProcess);
	
	public void updateStageStatus(CaseProcess caseProcess);
	
}