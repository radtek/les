/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.dao;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.modules.tcase.entity.Tcase;

/**
 * 案件DAO接口
 * @author GLQ
 * @version 2018-07-09
 */
@MyBatisDao
public interface TcaseDao extends CrudDao<Tcase> {

	
}