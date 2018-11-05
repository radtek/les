/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.dao;

import org.wxjs.les.common.persistence.CrudDao;
import org.wxjs.les.common.persistence.annotation.MyBatisDao;
import org.wxjs.les.modules.base.entity.Signature;

/**
 * 签名DAO接口
 * @author GLQ
 * @version 2018-07-06
 */
@MyBatisDao
public interface SignatureDao extends CrudDao<Signature> {
	
	public void updateOpinion(Signature signature);
	
	public void updateSignatureTime(Signature signature);
	
	public Signature getByTaskId(String taskId);
	
	public Signature getLatestSignatureByLoginName(String loginName);
	
	public void deleteByProcInsId(Signature signature);

}