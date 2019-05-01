/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package org.wxjs.upload.modules.upload.dao;

import org.wxjs.upload.modules.upload.entity.InfPunishResult;

/**
 * inf_punish_resultDAO接口
 * @author GLQ
 * @version 2018-08-21
 */
public interface InfPunishResultDao{
	
	public void insert(InfPunishResult entity);
	
	public void update(InfPunishResult entity);
	
	public void delete(InfPunishResult entity);
	
}