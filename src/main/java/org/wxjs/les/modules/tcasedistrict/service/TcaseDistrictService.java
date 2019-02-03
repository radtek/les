/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcasedistrict.service;

import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.modules.tcasedistrict.entity.TcaseDistrict;
import org.wxjs.upload.common.CaseUploadUtils;
import org.wxjs.les.modules.base.utils.WebServiceUtils;
import org.wxjs.les.modules.tcase.entity.CaseDecision;
import org.wxjs.les.modules.tcase.entity.PunishInfo4Xml;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcasedistrict.dao.TcaseDistrictDao;

/**
 * 案件Service
 * @author GLQ
 * @version 2019-01-31
 */
@Service
@Transactional(readOnly = true)
public class TcaseDistrictService extends CrudService<TcaseDistrictDao, TcaseDistrict> {

	public TcaseDistrict get(String id) {
		return super.get(id);
	}
	
	public List<TcaseDistrict> findList(TcaseDistrict tcaseDistrict) {
		return super.findList(tcaseDistrict);
	}
	
	public Page<TcaseDistrict> findPage(Page<TcaseDistrict> page, TcaseDistrict tcaseDistrict) {
		return super.findPage(page, tcaseDistrict);
	}
	
	@Transactional(readOnly = false)
	public void save(TcaseDistrict tcaseDistrict) {
		super.save(tcaseDistrict);
	}
	
	@Transactional(readOnly = false)
	public void commit(TcaseDistrict tcaseDistrict) {
		tcaseDistrict.setStatus("1");
		super.save(tcaseDistrict);
		//upload
		try {
			this.upload(tcaseDistrict);
		} catch (MalformedURLException e) {
			logger.error("上传失败", e);
		} catch (Exception e) {
			logger.error("上传失败", e);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(TcaseDistrict tcaseDistrict) {
		super.delete(tcaseDistrict);
	}
	
	private void upload(TcaseDistrict tcaseDistrict) throws MalformedURLException, Exception{
		//上传处罚信息到四库
		
		PunishInfo4Xml punishInfo4Xml =new PunishInfo4Xml();
		
		CaseUploadUtils utils = new CaseUploadUtils();
		
		punishInfo4Xml.setAjNo(tcaseDistrict.getDecisionSeq());
		
		punishInfo4Xml.setJasj(DateUtils.formatDate(tcaseDistrict.getDecisionDate(), "yyyy-MM-dd HH:mm:ss"));
		punishInfo4Xml.setLasj(DateUtils.formatDate(tcaseDistrict.getInitialDate(), "yyyy-MM-dd HH:mm:ss"));
		punishInfo4Xml.setPrjNum(tcaseDistrict.getProjectCode());
		
		punishInfo4Xml.setXykflb(tcaseDistrict.getProjectType());
		//get by xykflb,市政有单独编号
		String cflx = utils.handleCflx(tcaseDistrict.getProjectType(), tcaseDistrict.getPunishType());
		punishInfo4Xml.setCflx(cflx);
		//get by xykflb,cflx
		punishInfo4Xml.setKfsx(utils.handleKfsx(tcaseDistrict.getProjectType(), cflx));
		
		punishInfo4Xml.setWfwgxm(tcaseDistrict.getProjectName());
		punishInfo4Xml.setStlx(tcaseDistrict.getPartyType());
		punishInfo4Xml.setWfwgdwry(tcaseDistrict.getPartyDisplay());
		punishInfo4Xml.setZzjgdmSfzh(tcaseDistrict.getPartyCode()); 
		punishInfo4Xml.setWfxw(tcaseDistrict.getCaseCause());
		punishInfo4Xml.setUpdateFlag("U");
		punishInfo4Xml.setSource("行政处罚"); 
		
		boolean flag = WebServiceUtils.saveXzcfToZx(punishInfo4Xml);
		logger.info("upload case info to lib4: {}, success: {}", tcaseDistrict.getCaseSeq(), flag);			
	}
	
}