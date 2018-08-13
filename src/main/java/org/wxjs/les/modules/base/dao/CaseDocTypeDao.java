/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.dao;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.modules.base.entity.CaseDocType;

/**
 * 文档类型DAO接口
 * @author GLQ
 * @version 2018-08-13
 */
@MyBatisDao
public interface CaseDocTypeDao extends CrudDao<CaseDocType> {
	
}