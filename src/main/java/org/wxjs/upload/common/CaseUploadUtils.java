package org.wxjs.upload.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wxjs.les.common.utils.SpringContextHolder;

import org.wxjs.les.modules.tcase.dao.DeductionMatterDao;

import org.wxjs.les.modules.tcase.entity.DeductionMatter;

public class CaseUploadUtils {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	DeductionMatterDao deductionMatterDao = SpringContextHolder.getBean(DeductionMatterDao.class);
	
	public String handleCflx(String xykflb, String cflx){
		String rst = cflx;
		if("SZ".equalsIgnoreCase(xykflb)){
			if("100".equals(cflx)){
				rst = "800";
			}else if("200".equals(cflx)){
				rst = "900";
			}else if("300".equals(cflx)){
				rst = "1000";
			}else if("400".equals(cflx)){
				rst = "1100";
			}else if("500".equals(cflx)){
				rst = "1200";
			}else if("700".equals(cflx)){
				rst = "1400";
			}
		}
		
		return rst;
	}
	
	public String handleKfsx(String xykflb, String cflx){
		String rst = "";
		DeductionMatter deductionMatter = new DeductionMatter();
		deductionMatter.setProjectType(xykflb);
		deductionMatter.setPunishType(cflx);
		List<DeductionMatter> list = deductionMatterDao.findList(deductionMatter);
		
		if(list!=null && !list.isEmpty()){
			DeductionMatter item = list.get(0);
			rst = item.getMatterCode();
		}
		
		return rst;		
	}

}
