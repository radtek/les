/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.cr.dao;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.modules.cr.entity.SiteCheckRecord;

/**
 * 现场检查笔录DAO接口
 * @author 千里目
 * @version 2018-07-27
 */
@MyBatisDao
public interface SiteCheckRecordDao extends CrudDao<SiteCheckRecord> {
	
}