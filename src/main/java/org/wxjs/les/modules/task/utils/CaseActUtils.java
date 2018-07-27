/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.task.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.wxjs.les.common.annotation.FieldName;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.Encodes;
import org.wxjs.les.common.utils.ObjectUtils;
import org.wxjs.les.common.utils.StringUtils;
import org.wxjs.les.modules.sys.entity.Role;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.task.entity.CaseAct;

/**
 * 流程工具
 * @author ThinkGem
 * @version 2013-11-03
 */
public class CaseActUtils {

//	private static Logger logger = LoggerFactory.getLogger(ActUtils.class);
	
	/**
	 * 定义流程定义KEY，必须以“PD_”开头
	 * 
	 * 组成结构：string[]{"流程标识","业务主表表名"}
	 */
	//public static final String[] PD_LEAVE = new String[]{"leave", "oa_leave"};
	//public static final String[] PD_TEST_AUDIT = new String[]{"test_audit", "oa_test_audit"};
	
//	/**
//	 * 流程定义Map（自动初始化）
//	 */
//	private static Map<String, String> procDefMap = new HashMap<String, String>() {
//		private static final long serialVersionUID = 1L;
//		{
//			for (Field field : ActUtils.class.getFields()){
//				if(StringUtils.startsWith(field.getName(), "PD_")){
//					try{
//						String[] ss = (String[])field.get(null);
//						put(ss[0], ss[1]);
//					}catch (Exception e) {
//						logger.debug("load pd error: {}", field.getName());
//					}
//				}
//			}
//		}
//	};
//	
//	/**
//	 * 获取流程执行（办理）URL
//	 * @param procId
//	 * @return
//	 */
//	public static String getProcExeUrl(String procId) {
//		String url = procDefMap.get(StringUtils.split(procId, ":")[0]);
//		if (StringUtils.isBlank(url)){
//			return "404";
//		}
//		return url;
//	}
	
	@SuppressWarnings({ "unused" })
	public static Map<String, Object> getMobileEntity(Object entity,String spiltType){
		if(spiltType==null){
			spiltType="@";
		}
		Map<String, Object> map = Maps.newHashMap();

		List<String> field = Lists.newArrayList();
		List<String> value = Lists.newArrayList();
		List<String> chinesName =Lists.newArrayList();
		
		try{
			for (Method m : entity.getClass().getMethods()){
				if (m.getAnnotation(JsonIgnore.class) == null && m.getAnnotation(JsonBackReference.class) == null && m.getName().startsWith("get")){
					if (m.isAnnotationPresent(FieldName.class)) {
						Annotation p = m.getAnnotation(FieldName.class);
						FieldName fieldName=(FieldName) p;
						chinesName.add(fieldName.value());
					}else{
						chinesName.add("");
					}
					if (m.getName().equals("getAct")){
						Object act = m.invoke(entity, new Object[]{});
						Method actMet = act.getClass().getMethod("getTaskId");
						map.put("taskId", ObjectUtils.toString(m.invoke(act, new Object[]{}), ""));
					}else{
						field.add(StringUtils.uncapitalize(m.getName().substring(3)));
						value.add(ObjectUtils.toString(m.invoke(entity, new Object[]{}), ""));
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		map.put("beanTitles", StringUtils.join(field, spiltType));
		map.put("beanInfos", StringUtils.join(value, spiltType));
		map.put("chineseNames", StringUtils.join(chinesName, spiltType));
		
		return map;
	}
	
	/**
	 * 获取流程表单URL
	 * @param formKey
	 * @param act 表单传递参数
	 * @return
	 */
	public static String getFormUrl(String formKey, CaseAct caseAct){
		
		StringBuilder formUrl = new StringBuilder();
		
		String formServerUrl = Global.getConfig("activiti.form.server.url");
		if (StringUtils.isBlank(formServerUrl)){
			formUrl.append(Global.getAdminPath());
		}else{
			formUrl.append(formServerUrl);
		}
		
		formUrl.append(formKey).append(formUrl.indexOf("?") == -1 ? "?" : "&");
		formUrl.append("taskId=").append(caseAct.getTaskId() != null ? caseAct.getTaskId() : "");
		formUrl.append("&taskName=").append(caseAct.getTaskName() != null ? Encodes.urlEncode(caseAct.getTaskName()) : "");
		formUrl.append("&taskDefKey=").append(caseAct.getTaskDefKey() != null ? caseAct.getTaskDefKey() : "");
		formUrl.append("&procInsId=").append(caseAct.getProcInsId() != null ? caseAct.getProcInsId() : "");
		formUrl.append("&procDefId=").append(caseAct.getProcDefId() != null ? caseAct.getProcDefId() : "");
		formUrl.append("&status=").append(caseAct.getStatus() != null ? caseAct.getStatus() : "");
		formUrl.append("&id=").append(caseAct.getBusinessId() != null ? caseAct.getBusinessId() : "");
		formUrl.append("&businesskey=").append(caseAct.getBusinesskey());
		formUrl.append("&operateType=").append(caseAct.getOperateType());
		
		return formUrl.toString();
	}
	
	/**
	 * 转换流程节点类型为中文说明
	 * @param type 英文名称
	 * @return 翻译后的中文名称
	 */
	public static String parseToZhType(String type) {
		Map<String, String> types = new HashMap<String, String>();
		types.put("userTask", "用户任务");
		types.put("serviceTask", "系统任务");
		types.put("startEvent", "开始节点");
		types.put("endEvent", "结束节点");
		types.put("exclusiveGateway", "条件判断节点(系统自动根据条件处理)");
		types.put("inclusiveGateway", "并行处理任务");
		types.put("callActivity", "子流程");
		return types.get(type) == null ? type : types.get(type);
	}

	public static UserEntity toActivitiUser(User user){
		if (user == null){
			return null;
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setId(user.getLoginName());
		userEntity.setFirstName(user.getName());
		userEntity.setLastName(StringUtils.EMPTY);
		userEntity.setPassword(user.getPassword());
		userEntity.setEmail(user.getEmail());
		userEntity.setRevision(1);
		return userEntity;
	}
	
	public static GroupEntity toActivitiGroup(Role role){
		if (role == null){
			return null;
		}
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setId(role.getEnname());
		groupEntity.setName(role.getName());
		groupEntity.setType(role.getRoleType());
		groupEntity.setRevision(1);
		return groupEntity;
	}
	
	public static void main(String[] args) {
		 User user = new User();
		 System.out.println(getMobileEntity(user, "@"));
	}
}