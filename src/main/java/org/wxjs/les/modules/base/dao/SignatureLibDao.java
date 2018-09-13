/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.dao;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.modules.base.entity.SignatureLib;

/**
 * 签名库DAO接口
 * @author GLQ
 * @version 2018-09-12
 */
@MyBatisDao
public interface SignatureLibDao extends CrudDao<SignatureLib> {
	
}