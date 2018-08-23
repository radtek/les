/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package org.wxjs.upload.modules.upload.dao;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.upload.common.persistence.annotation.MyBatisDao;
import org.wxjs.upload.modules.upload.entity.InfPunish;

/**
 * inf_punishDAO接口
 * @author GLQ
 * @version 2018-08-21
 */

@MyBatisDao
public interface InfPunishDao extends CrudDao<InfPunish> {
	
}