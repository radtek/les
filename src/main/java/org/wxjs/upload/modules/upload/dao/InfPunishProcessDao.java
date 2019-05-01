/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package org.wxjs.upload.modules.upload.dao;

import org.wxjs.upload.modules.upload.entity.InfPunishProcess;

/**
 * inf_punish_processDAO接口
 * @author GLQ
 * @version 2018-08-21
 */
public interface InfPunishProcessDao{
	
	public void insert(InfPunishProcess entity);
	
	public void update(InfPunishProcess entity);
	
	public void delete(InfPunishProcess entity);
	
}