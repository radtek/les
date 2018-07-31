/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.dao;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.modules.tcase.entity.CaseHandlePunishLib;

/**
 * 案件裁量权DAO接口
 * @author GLQ
 * @version 2018-07-30
 */
@MyBatisDao
public interface CaseHandlePunishLibDao extends CrudDao<CaseHandlePunishLib> {
	
	public void updateRange(CaseHandlePunishLib entity);
	
}