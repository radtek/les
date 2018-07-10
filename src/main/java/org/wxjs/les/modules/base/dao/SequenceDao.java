/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.dao;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.modules.base.entity.Sequence;

/**
 * 顺序号DAO接口
 * @author GLQ
 * @version 2018-07-09
 */
@MyBatisDao
public interface SequenceDao extends CrudDao<Sequence> {
	public Sequence getByName(String name);
	
}