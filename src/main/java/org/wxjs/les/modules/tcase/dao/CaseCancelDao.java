/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.dao;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.modules.tcase.entity.CaseCancel;

/**
 * 案件撤销DAO接口
 * @author GLQ
 * @version 2018-08-08
 */
@MyBatisDao
public interface CaseCancelDao extends CrudDao<CaseCancel> {
	
}