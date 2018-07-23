package org.wxjs.les.modules.base.utils;

import java.util.List;

import org.wxjs.les.common.utils.SpringContextHolder;
import org.wxjs.les.modules.base.dao.OpinionTemplateDao;
import org.wxjs.les.modules.base.entity.OpinionTemplate;

public class BaseUtils {
	
	private static OpinionTemplateDao opinionTemplateDao = SpringContextHolder.getBean(OpinionTemplateDao.class);
	
	public static List<OpinionTemplate> getOpinionTemplates(){
		
		return opinionTemplateDao.findAllList(new OpinionTemplate());
		
	}

}
