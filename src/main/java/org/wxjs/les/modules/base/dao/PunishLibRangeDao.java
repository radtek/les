/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.dao;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.modules.base.entity.PunishLibRange;

/**
 * 处罚基准库DAO接口
 * @author GLQ
 * @version 2018-07-29
 */
@MyBatisDao
public interface PunishLibRangeDao extends CrudDao<PunishLibRange> {
	
	public void deleteAll();
	
}