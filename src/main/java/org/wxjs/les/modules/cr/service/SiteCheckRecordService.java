/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.cr.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.cr.dao.SiteCheckRecordDao;
import org.wxjs.les.modules.cr.entity.SiteCheckRecord;

/**
 * 现场检查笔录Service
 * @author 千里目
 * @version 2018-07-27
 */
@Service
@Transactional(readOnly = true)
public class SiteCheckRecordService extends CrudService<SiteCheckRecordDao, SiteCheckRecord> {

	public SiteCheckRecord get(String id) {
		return super.get(id);
	}
	
	public List<SiteCheckRecord> findList(SiteCheckRecord tsiteCheckRecord) {
		return super.findList(tsiteCheckRecord);
	}
	
	public Page<SiteCheckRecord> findPage(Page<SiteCheckRecord> page, SiteCheckRecord siteCheckRecord) {
		return super.findPage(page, siteCheckRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(SiteCheckRecord siteCheckRecord) {
		super.save(siteCheckRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(SiteCheckRecord siteCheckRecord) {
		super.delete(siteCheckRecord);
	}
	
}