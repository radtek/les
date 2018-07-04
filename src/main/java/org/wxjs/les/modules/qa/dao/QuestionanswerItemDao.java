/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.qa.dao;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.modules.qa.entity.QuestionanswerItem;

/**
 * 询问笔录DAO接口
 * @author GLQ
 * @version 2018-07-02
 */
@MyBatisDao
public interface QuestionanswerItemDao extends CrudDao<QuestionanswerItem> {
	
}