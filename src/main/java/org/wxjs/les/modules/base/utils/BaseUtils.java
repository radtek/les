package org.wxjs.les.modules.base.utils;

import java.util.List;

import org.wxjs.les.common.utils.SpringContextHolder;
import org.wxjs.les.modules.base.dao.OpinionTemplateDao;
import org.wxjs.les.modules.base.entity.OpinionTemplate;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.utils.UserUtils;

public class BaseUtils {
	
	private static OpinionTemplateDao opinionTemplateDao = SpringContextHolder.getBean(OpinionTemplateDao.class);
	
	public static List<OpinionTemplate> getOpinionTemplates(){
		OpinionTemplate opinionTemplate = new OpinionTemplate();
		User user = UserUtils.getUser();
		opinionTemplate.setOwner(user.getLoginName());
		return opinionTemplateDao.findList(opinionTemplate);
		
	}

}
