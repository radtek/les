/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.task.dao;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;

import org.wxjs.les.modules.task.entity.CaseAct;

/**
 * 审批DAO接口
 * @author thinkgem
 * @version 2014-05-16
 */
@MyBatisDao
public interface CaseActDao extends CrudDao<CaseAct> {

	public int updateProcInsIdByBusinessId(CaseAct act);
	
}
