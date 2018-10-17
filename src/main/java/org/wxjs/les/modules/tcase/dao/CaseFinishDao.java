/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.dao;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.modules.tcase.entity.CaseFinish;

/**
 * 案件结束DAO接口
 * @author GLQ
 * @version 2018-07-24
 */
@MyBatisDao
public interface CaseFinishDao extends CrudDao<CaseFinish> {
	
	public void deleteByCaseId(String caseId);
	
}