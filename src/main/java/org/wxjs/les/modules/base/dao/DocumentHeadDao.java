/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.dao;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.modules.base.entity.DocumentHead;

/**
 * 发文字号DAO接口
 * @author GLQ
 * @version 2019-03-14
 */
@MyBatisDao
public interface DocumentHeadDao extends CrudDao<DocumentHead> {
	
}