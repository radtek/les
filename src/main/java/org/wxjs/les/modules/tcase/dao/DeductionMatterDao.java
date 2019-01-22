/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.dao;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.modules.tcase.entity.DeductionMatter;

/**
 * 处罚扣分事项DAO接口
 * @author GLQ
 * @version 2019-01-18
 */
@MyBatisDao
public interface DeductionMatterDao extends CrudDao<DeductionMatter> {
	
}