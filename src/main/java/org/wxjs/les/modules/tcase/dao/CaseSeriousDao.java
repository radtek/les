/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.dao;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.modules.tcase.entity.CaseSerious;

/**
 * 重大行政处罚DAO接口
 * @author GLQ
 * @version 2018-07-27
 */
@MyBatisDao
public interface CaseSeriousDao extends CrudDao<CaseSerious> {
	
	public void updateMeetingRecord(CaseSerious entity);
	
	public void deleteByCaseId(String caseId);
	
}