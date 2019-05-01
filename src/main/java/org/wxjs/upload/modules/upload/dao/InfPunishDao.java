/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package org.wxjs.upload.modules.upload.dao;

import org.wxjs.upload.modules.upload.entity.InfPunish;

/**
 * inf_punishDAO接口
 * @author GLQ
 * @version 2018-08-21
 */

public interface InfPunishDao{
	
	public InfPunish get(InfPunish entity);
	
	public void insert(InfPunish entity);
	
	public void update(InfPunish entity);
	
	public void delete(InfPunish entity);
	
}