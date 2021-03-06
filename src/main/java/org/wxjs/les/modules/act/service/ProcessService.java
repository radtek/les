package org.wxjs.les.modules.act.service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.service.BaseService;
import org.wxjs.les.common.utils.StringUtils;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.service.SystemService;

import com.google.common.collect.Lists;

@Service
@Transactional(readOnly = true)
public class ProcessService extends BaseService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;

	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private SystemService systemService;

	/**
	 * 获取用户任务定义
	 * 
	 * @param String
	 *            taskId 任务Id信息
	 * @return 下一个用户任务用户定义
	 * @throws Exception
	 */
	public TaskDefinition getTaskDefinition(String taskId) throws Exception {

		ProcessDefinitionEntity processDefinitionEntity = null;

		String id = null;

		TaskDefinition taskDef = null;

		// 获取流程实例Id信息
		
		logger.debug("current taskId:{}", taskId);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(task.getProcessDefinitionId());

		String excId = task.getExecutionId();

		logger.debug("excId:{}", excId);

		ExecutionEntity execution = (ExecutionEntity) runtimeService
				.createExecutionQuery().executionId(excId).singleResult();

		// 当前流程节点Id信息
		String activitiId = execution.getActivityId();

		// 获取流程所有节点信息
		List<ActivityImpl> activitiList = processDefinitionEntity
				.getActivities();

		// 遍历所有节点信息
		for (ActivityImpl activityImpl : activitiList) {
			id = activityImpl.getId();

			// 找到当前节点信息
			if (activitiId.equals(id)) {

				// 获取下一个节点信息
				taskDef = (TaskDefinition) activityImpl
						.getProperty("taskDefinition");

				break;
			}
		}

		return taskDef;
	}

	public Set<Expression> getTaskGroup(String taskId) throws Exception {

		TaskDefinition task = getTaskDefinition(taskId);
		return task.getCandidateGroupIdExpressions();
	}
	
    /**  
     * 下一个任务节点信息,  
     *  
     * 如果下一个节点为用户任务则直接返回,  
     *  
     * 如果下一个节点为排他网关, 获取排他网关Id信息, 根据排他网关Id信息和execution获取流程实例排他网关Id为key的变量值,  
     * 根据变量值分别执行排他网关后线路中的el表达式, 并找到el表达式通过的线路后的用户任务信息  
     * @param ActivityImpl activityImpl     流程节点信息  
     * @param String activityId             当前流程节点Id信息    
     * @param String processInstanceId      流程实例Id信息  
     * @return  
     */ 
	
	private TaskDefinition nextTaskDefinition(ActivityImpl activityImpl,
			String activityId, String processInstanceId) {

		PvmActivity ac = null;

		Object s = null;

		// 如果遍历节点为用户任务并且节点不是当前节点信息
		if ("userTask".equals(activityImpl.getProperty("type"))
				&& !activityId.equals(activityImpl.getId())) {
			// 获取该节点下一个节点信息
			TaskDefinition taskDefinition = ((UserTaskActivityBehavior) activityImpl.getActivityBehavior()).getTaskDefinition();
			
			return taskDefinition;
		} else {
			// 获取节点所有流向线路信息
			List<PvmTransition> outTransitions = activityImpl
					.getOutgoingTransitions();
			List<PvmTransition> outTransitionsTemp = null;
			for (PvmTransition tr : outTransitions) {
				ac = tr.getDestination(); // 获取线路的终点节点
				
				logger.debug("type:{}", ac.getProperty("type"));
				
				// 如果流向线路为排他网关
				if ("exclusiveGateway".equals(ac.getProperty("type"))) {
					outTransitionsTemp = ac.getOutgoingTransitions();

					// 如果排他网关只有一条线路信息
					if (outTransitionsTemp.size() == 1) {
						return nextTaskDefinition(
								(ActivityImpl) outTransitionsTemp.get(0)
										.getDestination(), activityId,
								 processInstanceId);
					} else if (outTransitionsTemp.size() > 1) { // 如果排他网关有多条线路信息
						for (PvmTransition tr1 : outTransitionsTemp) {
							s = tr1.getProperty("conditionText"); // 获取排他网关线路判断条件信息
							
							logger.debug("conditionText:{}", s.toString());
							// 判断el表达式是否成立
							if (s.toString().contains("'pass'")) {
								return nextTaskDefinition(
										(ActivityImpl) tr1.getDestination(),
										activityId, processInstanceId);
							}
						}
					}
				} else if ("userTask".equals(ac.getProperty("type"))) {
					return ((UserTaskActivityBehavior) ((ActivityImpl) ac)
							.getActivityBehavior()).getTaskDefinition();
				} else {
				}
			}
			return null;
		}
	}
	
    /**  
     * 下一个任务节点信息,  
     *  
     * 如果下一个节点为用户任务则直接返回,  
     *  
     * 如果下一个节点为排他网关, 获取排他网关Id信息, 根据排他网关Id信息和execution获取流程实例排他网关Id为key的变量值,  
     * 根据变量值分别执行排他网关后线路中的el表达式, 并找到el表达式通过的线路后的用户任务信息  
     * @param ActivityImpl activityImpl     流程节点信息  
     * @param String activityId             当前流程节点Id信息    
     * @param String processInstanceId      流程实例Id信息  
     * @return  
     */
	
	private String nextConditionVariable(ActivityImpl activityImpl,
			String activityId, String processInstanceId) {
		
		String rst = "";

		PvmActivity ac = null;

		Object s = null;

		// 获取节点所有流向线路信息
		List<PvmTransition> outTransitions = activityImpl
				.getOutgoingTransitions();
		List<PvmTransition> outTransitionsTemp = null;
		for (PvmTransition tr : outTransitions) {
			ac = tr.getDestination(); // 获取线路的终点节点
			
			logger.debug("type:{}", ac.getProperty("type"));
			
			// 如果流向线路为排他网关
			if ("exclusiveGateway".equals(ac.getProperty("type"))) {
				outTransitionsTemp = ac.getOutgoingTransitions();

				// 如果排他网关只有一条线路信息
				for (PvmTransition tr1 : outTransitionsTemp) {
					s = tr1.getProperty("conditionText"); // 获取排他网关线路判断条件信息
					
					logger.debug("conditionText:{}", s.toString());
					// 判断el表达式是否成立
					if (s.toString().contains("'pass'")) {
						
						String conText = s.toString();
						rst = conText.substring(conText.indexOf("{")+1, conText.indexOf("=="));
						break;
					}
				}
			} else {
				rst = "";
			}
		}
		
		return rst;

	}
	
	
	private String nextConditionTexts(ActivityImpl activityImpl,
			String activityId, String processInstanceId) {
		
		String rst = "";
		
		StringBuffer buffer = new StringBuffer();

		PvmActivity ac = null;

		Object s = null;

		// 获取节点所有流向线路信息
		List<PvmTransition> outTransitions = activityImpl
				.getOutgoingTransitions();
		List<PvmTransition> outTransitionsTemp = null;
		for (PvmTransition tr : outTransitions) {
			ac = tr.getDestination(); // 获取线路的终点节点
			
			logger.debug("type:{}", ac.getProperty("type"));
			
			// 如果流向线路为排他网关
			if ("exclusiveGateway".equals(ac.getProperty("type"))) {
				outTransitionsTemp = ac.getOutgoingTransitions();

				// 如果排他网关只有一条线路信息
				for (PvmTransition tr1 : outTransitionsTemp) {
					s = tr1.getProperty("conditionText"); // 获取排他网关线路判断条件信息
					
					buffer.append(s.toString()).append(",");
				}
				rst = buffer.toString();
			} else {
				rst = "";
			}
		}
		
		return rst;

	}
	
	/**
	 * 获取条件变量
	 * @param taskId
	 * @return
	 */
	public String getConditionVariable(String taskId){

		ProcessDefinitionEntity processDefinitionEntity = null;

		String id = null;

		String variable = "";

		// 获取流程实例Id信息
		
		logger.debug("current taskId:{}", taskId);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(task.getProcessDefinitionId());

		String excId = task.getExecutionId();

		logger.debug("excId:{}", excId);

		ExecutionEntity execution = (ExecutionEntity) runtimeService
				.createExecutionQuery().executionId(excId).singleResult();

		String activitiId = execution.getActivityId();

		logger.debug("activitiId:{}", activitiId);

		// 获取流程所有节点信息
		List<ActivityImpl> activitiList = processDefinitionEntity
				.getActivities();

		// 遍历所有节点信息
		for (ActivityImpl activityImpl : activitiList) {
			id = activityImpl.getId();

			// 找到当前节点信息
			if (activitiId.equals(id)) {

				// 获取下一个节点信息
				variable = nextConditionVariable(activityImpl, activityImpl.getId(), task.getProcessInstanceId());
				
				logger.debug("variable:{}", variable);

				break;
			}
		}

		return variable;
	}
	
	/**
	 * 获取条件表达式
	 * @param taskId
	 * @return
	 */
	public String getConditionTexts(String taskId){

		ProcessDefinitionEntity processDefinitionEntity = null;

		String id = null;

		String conditionTexts = "";

		// 获取流程实例Id信息
		
		logger.debug("current taskId:{}", taskId);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(task.getProcessDefinitionId());

		String excId = task.getExecutionId();

		logger.debug("excId:{}", excId);

		ExecutionEntity execution = (ExecutionEntity) runtimeService
				.createExecutionQuery().executionId(excId).singleResult();

		String activitiId = execution.getActivityId();

		logger.debug("activitiId:{}", activitiId);

		// 获取流程所有节点信息
		List<ActivityImpl> activitiList = processDefinitionEntity
				.getActivities();

		// 遍历所有节点信息
		for (ActivityImpl activityImpl : activitiList) {
			id = activityImpl.getId();

			// 找到当前节点信息
			if (activitiId.equals(id)) {

				// 获取下一个节点信息
				conditionTexts = nextConditionTexts(activityImpl, activityImpl.getId(), task.getProcessInstanceId());
				
				logger.debug("variable:{}", conditionTexts);

				break;
			}
		}

		return conditionTexts;
	}

	/**
	 * 获取下一个用户任务定义
	 * 
	 * @param String
	 *            taskId 任务Id信息
	 * @return 下一个用户任务用户定义
	 * @throws Exception
	 */
	public TaskDefinition getNextTaskDefinition(String taskId) {

		ProcessDefinitionEntity processDefinitionEntity = null;

		String id = null;

		TaskDefinition taskDef = null;

		// 获取流程实例Id信息
		
		logger.debug("current taskId:{}", taskId);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(task.getProcessDefinitionId());

		String excId = task.getExecutionId();

		logger.debug("excId:{}", excId);

		ExecutionEntity execution = (ExecutionEntity) runtimeService
				.createExecutionQuery().executionId(excId).singleResult();

		String activitiId = execution.getActivityId();

		logger.debug("activitiId:{}", activitiId);

		// 获取流程所有节点信息
		List<ActivityImpl> activitiList = processDefinitionEntity
				.getActivities();

		// 遍历所有节点信息
		for (ActivityImpl activityImpl : activitiList) {
			id = activityImpl.getId();

			// 找到当前节点信息
			if (activitiId.equals(id)) {

				// 获取下一个节点信息
				taskDef = nextTaskDefinition(activityImpl, activityImpl.getId(), task.getProcessInstanceId());
				
				logger.debug("taskDef.getKey():{}", taskDef!=null?taskDef.getKey():"");

				break;
			}
		}

		return taskDef;
	}
	
	/**
	 * 获取本关关处理人角色
	 * @param taskId
	 * @return
	 */
	public String getTaskGroupText(String taskId) {
		String rst = "";

		try {
			TaskDefinition taskDef = this.getTaskDefinition(taskId);
			
			if(taskDef != null){
				Set<Expression> groups = taskDef.getCandidateGroupIdExpressions();
				for (Expression exp : groups) {
					rst = exp.getExpressionText();
				}				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rst;
	}

	/**
	 * 获取下一关处理人角色
	 * @param taskId
	 * @return
	 */
	public String getNextTaskGroupText(String taskId) {
		String rst = "";

		try {
			TaskDefinition taskDef = this.getNextTaskDefinition(taskId);
			
			if(taskDef != null){
				Set<Expression> groups = taskDef.getCandidateGroupIdExpressions();
				StringBuffer buffer = new StringBuffer();
				for (Expression exp : groups) {
					buffer.append(",").append(exp.getExpressionText());
				}	
				if(buffer.length()>0){
					rst = buffer.substring(1);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rst;
	}

	public String getBusinesskeyByTaskId(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		String instanceid = task.getProcessInstanceId();
		ProcessInstance ins = runtimeService.createProcessInstanceQuery()
				.processInstanceId(instanceid).singleResult();
		return ins.getBusinessKey();
	}

	public String getBusinesskeyByProcInstId(String procInstId) {
		ProcessInstance ins = runtimeService.createProcessInstanceQuery()
				.processInstanceId(procInstId).singleResult();
		String rst = "";
		if (ins != null) {
			rst = ins.getBusinessKey();
		}
		return rst;
	}
	
	/**
	 * get handlers for next task
	 * @param taskId
	 * @param handleOrg
	 * @param area
	 * @return
	 */
	public List<User> getHandler4Handle(String taskId, String handleOrg, String area){
		
		List<User> list = Lists.newArrayList();

		String group = this.getNextTaskGroupText(taskId);
		
		logger.debug("group:{}", group);
		
		if(!StringUtils.isEmpty(group)){
			list = this.getHandlerByGroup(group, handleOrg, area);
		}
		
		return list;
	}
	
	/**
	 * get handler by group
	 * @param group
	 * @param handleOrg
	 * @param area
	 * @return
	 */
	public List<User> getHandlerByGroup(String group, String handleOrg, String area){
		List<User> rst = Lists.newArrayList();
		
		List<User> list = systemService.findUserByRoleEname(group);	
		
		if(Arrays.asList(Global.NoneZhiduiZhanRoles).contains(group)){
			//rst.addAll(list);
			for(User e : list){
				if(e.getOffice().getAreaId().equals(area)){
					rst.add(e);
				}
			}
		}else{
			logger.debug("handleOrg:{}, area:{}", handleOrg, area);
			for(User e : list){
				logger.debug("e.getOffice().getType():{},e.getOffice().getAreaId():{}", e.getOffice().getType(), e.getOffice().getAreaId());
				
				if(e.getOffice().getType().equals(handleOrg) && e.getOffice().getAreaId().equals(area)){
					rst.add(e);
				}
			}			
		}
		
		return rst;
	}

}
