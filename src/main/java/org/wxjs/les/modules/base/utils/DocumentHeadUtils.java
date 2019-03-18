package org.wxjs.les.modules.base.utils;

import java.util.List;

import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.SpringContextHolder;
import org.wxjs.les.modules.base.dao.DocumentHeadDao;
import org.wxjs.les.modules.base.entity.DocumentHead;
import org.wxjs.les.modules.sys.entity.Dict;
import org.wxjs.les.modules.sys.utils.DictUtils;

import com.google.common.collect.Lists;

public class DocumentHeadUtils {
	
	private static DocumentHeadDao documentHeadDao = SpringContextHolder.getBean(DocumentHeadDao.class);
	
	public static List<Dict> getDocumentHeads(String areaId, String handleOrg, String stage){
		List<Dict> rst = Lists.newArrayList();
		
		if(Global.WXAreaCode.equals(areaId)){
			if("03".equals(handleOrg)){
				rst = DictUtils.getDictList("case_"+stage+"_type_aj");
			}else if("04".equals(handleOrg)){
				rst = DictUtils.getDictList("case_"+stage+"_type_zj");
			}else{
				rst = DictUtils.getDictList("case_"+stage+"_type");
			}			
		}else{
			DocumentHead param = new DocumentHead();
			param.setAreaId(areaId);
			param.setHandleOrg(handleOrg);
			param.setStage(stage);
			List<DocumentHead> list = documentHeadDao.findList(param);
			for(DocumentHead item: list){
				Dict dict = new Dict();
				dict.setLabel(item.getDocHead());
				dict.setValue(item.getType());
				rst.add(dict);
			}
			
		}
		
		return rst;
	}
	
	public static String getDocumentHead(String areaId, String handleOrg, String stage, String type){
		String rst = "";
		
		if(Global.WXAreaCode.equals(areaId)){
			if("03".equals(handleOrg)){
				rst = DictUtils.getDictLabel(type, "case_"+stage+"_type_aj", "");
			}else if("04".equals(handleOrg)){
				rst = DictUtils.getDictLabel(type, "case_"+stage+"_type_zj", "");
			}else{
				rst = DictUtils.getDictLabel(type, "case_"+stage+"_type", "");
			}			
		}else{
			DocumentHead param = new DocumentHead();
			param.setAreaId(areaId);
			param.setHandleOrg(handleOrg);
			param.setStage(stage);
			param.setType(type);
			List<DocumentHead> list = documentHeadDao.findList(param);
			for(DocumentHead item: list){
				rst = item.getDocHead();
			}
		}
		
		return rst;
	}

}
