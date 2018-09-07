package org.wxjs.les.common.web;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.modules.act.entity.Act;
import org.wxjs.les.modules.act.service.ActTaskService;


@Controller
@RequestMapping(value = "${adminPath}/common/activiti")
public class ActivitiController extends BaseController {

	@Autowired
    RepositoryService repositoryService;
    @Autowired
    ManagementService managementService;
    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    ProcessEngineConfiguration processEngineConfiguration;
    @Autowired
    ProcessEngineFactoryBean processEngine;
    @Autowired
    HistoryService historyService;
    @Autowired
    TaskService taskService;
    
	@Autowired
	private ActTaskService actTaskService;

    /** 
     * 流程是否已经结束 
     *  
     * @param processInstanceId 流程实例ID 
     * @return 
     */  
    public boolean isFinished(String processInstanceId) {  
        return historyService.createHistoricProcessInstanceQuery().finished()  
                .processInstanceId(processInstanceId).count() > 0;  
    }  
    
    @RequestMapping(value = "toProcTrack")
    public String toProcTrack(String procDefId, String executionId, String procInsId, Model model) throws Exception { 
    	
    	model.addAttribute("procDefId", procDefId);
    	model.addAttribute("executionId", executionId);
    	model.addAttribute("procInsId", procInsId);
    	
		if (StringUtils.isNotBlank(procInsId)){
			List<Act> histoicFlowList = actTaskService.histoicFlowList(procInsId, null, null);
			
			//filter signature info
			for(Act entity : histoicFlowList){
				String comment = entity.getComment();
				if(comment==null){
					continue;
				}
				int sigTagIndex = comment.indexOf(Global.SignatureTag);
				if(sigTagIndex>-1){
					comment = comment.substring(0, sigTagIndex);
					entity.setComment(comment);
				}
			}
			model.addAttribute("histoicFlowList", histoicFlowList);
		}
    	
    	return "modules/act/actProcessFlow";
    }
    
    /** 
     * 流程跟踪图片 
     *  
     * @param processDefId 
     *            流程定义ID 
     * @param executionId 
     *            流程运行ID 
     * @param out 
     *            输出流 
     * @throws Exception 
     */  
    @RequestMapping(value = "processTracking")
    public void processTracking(String processDefId, String executionId,  
            OutputStream out) throws Exception {  
        // 当前活动节点、活动线  
        List<String> activeActivityIds = new ArrayList<String>(), highLightedFlows = new ArrayList<String>();  
      
        /** 
         * 获得当前活动的节点 
         */  
        if (this.isFinished(executionId)) {// 如果流程已经结束，则得到结束节点  
            activeActivityIds.add(historyService  
                    .createHistoricActivityInstanceQuery()  
                    .executionId(executionId).activityType("endEvent")  
                    .singleResult().getActivityId());  
        } else {// 如果流程没有结束，则取当前活动节点  
            // 根据流程实例ID获得当前处于活动状态的ActivityId合集  
            activeActivityIds = runtimeService  
                    .getActiveActivityIds(executionId);  
        }  

        logger.debug("activeActivityIds:{}", StringUtils.join(activeActivityIds.toArray(), ","));
        /** 
         * 获得当前活动的节点-结束 
         */  
      
        /** 
         * 获得活动的线 
         */  
        // 获得历史活动记录实体（通过启动时间正序排序，不然有的线可以绘制不出来）  
        List<HistoricActivityInstance> historicActivityInstances = historyService  
                .createHistoricActivityInstanceQuery().processInstanceId(executionId)  
                .orderByHistoricActivityInstanceStartTime().asc()
                .orderByHistoricActivityInstanceEndTime().asc()
                .orderByActivityName().asc()
                .list();  

        // 计算活动线  
        highLightedFlows = this  
                .getHighLightedFlows(  
                        (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
                                .getDeployedProcessDefinition(processDefId),  
                        historicActivityInstances);  
        
        logger.debug("highLightedFlows:{}", StringUtils.join(highLightedFlows.toArray(), ","));
        /** 
         * 获得活动的线-结束 
         */  
      
        /** 
         * 绘制图形 
         */  
        if (null != activeActivityIds) {  
            InputStream imageStream = null;  
            try {  
                // 获得流程引擎配置  
                ProcessEngineConfiguration processEngineConfiguration = processEngine  
                        .getProcessEngineConfiguration();  
                // 根据流程定义ID获得BpmnModel  
                BpmnModel bpmnModel = repositoryService  
                        .getBpmnModel(processDefId);  
                // 输出资源内容到相应对象  
                imageStream = new DefaultProcessDiagramGenerator()  
                        .generateDiagram(bpmnModel, "png", activeActivityIds,  
                                highLightedFlows, processEngineConfiguration.getActivityFontName(),  
                                processEngineConfiguration.getLabelFontName(),  
                                processEngineConfiguration.getAnnotationFontName(),
                                processEngineConfiguration.getClassLoader(),  
                                1.0);  
                IOUtils.copy(imageStream, out);  
            } finally {  
                IOUtils.closeQuietly(imageStream);  
            }  
        }  
    }    
      
    /** 
     * 流程跟踪图片 
     *  
     * @param processDefId 
     *            流程定义ID 
     * @param executionId 
     *            流程运行ID 
     * @param out 
     *            输出流 
     * @throws Exception 
     */  
    @RequestMapping(value = "processTracking20180907")
    public void processTracking20180907(String processDefId, String executionId,  
            OutputStream out) throws Exception {  
        // 当前活动节点、活动线  
        List<String> activeActivityIds = new ArrayList<String>(), highLightedFlows = new ArrayList<String>();  
      
        /** 
         * 获得当前活动的节点 
         */  
        if (this.isFinished(executionId)) {// 如果流程已经结束，则得到结束节点  
            activeActivityIds.add(historyService  
                    .createHistoricActivityInstanceQuery()  
                    .executionId(executionId).activityType("endEvent")  
                    .singleResult().getActivityId());  
        } else {// 如果流程没有结束，则取当前活动节点  
            // 根据流程实例ID获得当前处于活动状态的ActivityId合集  
            activeActivityIds = runtimeService  
                    .getActiveActivityIds(executionId);  
        }  

        logger.debug("activeActivityIds:{}", StringUtils.join(activeActivityIds.toArray(), ","));
        /** 
         * 获得当前活动的节点-结束 
         */  
      
        /** 
         * 获得活动的线 
         */  
        // 获得历史活动记录实体（通过启动时间正序排序，不然有的线可以绘制不出来）  
        List<HistoricActivityInstance> historicActivityInstances = historyService  
                .createHistoricActivityInstanceQuery().executionId(executionId)  
                .orderByHistoricActivityInstanceStartTime().asc().list();  

        // 计算活动线  
        highLightedFlows = this  
                .getHighLightedFlows(  
                        (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
                                .getDeployedProcessDefinition(processDefId),  
                        historicActivityInstances);  
        
        logger.debug("highLightedFlows:{}", StringUtils.join(highLightedFlows.toArray(), ","));
        /** 
         * 获得活动的线-结束 
         */  
      
        /** 
         * 绘制图形 
         */  
        if (null != activeActivityIds) {  
            InputStream imageStream = null;  
            try {  
                // 获得流程引擎配置  
                ProcessEngineConfiguration processEngineConfiguration = processEngine  
                        .getProcessEngineConfiguration();  
                // 根据流程定义ID获得BpmnModel  
                BpmnModel bpmnModel = repositoryService  
                        .getBpmnModel(processDefId);  
                // 输出资源内容到相应对象  
                imageStream = new DefaultProcessDiagramGenerator()  
                        .generateDiagram(bpmnModel, "png", activeActivityIds,  
                                highLightedFlows, processEngineConfiguration.getActivityFontName(),  
                                processEngineConfiguration.getLabelFontName(),  
                                processEngineConfiguration.getAnnotationFontName(),
                                processEngineConfiguration.getClassLoader(),  
                                1.0);  
                IOUtils.copy(imageStream, out);  
            } finally {  
                IOUtils.closeQuietly(imageStream);  
            }  
        }  
    }  
      
    /** 
     * 获得高亮线 
     *  
     * @param processDefinitionEntity 
     *            流程定义实体 
     * @param historicActivityInstances 
     *            历史活动实体 
     * @return 线ID集合 
     */  
    private List<String> getHighLightedFlows(  
            ProcessDefinitionEntity processDefinitionEntity,  
            List<HistoricActivityInstance> historicActivityInstances) {  
      
        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId  
        for (int i = 0; i < historicActivityInstances.size(); i++) {// 对历史流程节点进行遍历  
        	
        	logger.debug(historicActivityInstances.get(i).toString());
        	
            ActivityImpl activityImpl = processDefinitionEntity  
                    .findActivity(historicActivityInstances.get(i)  
                            .getActivityId());// 得 到节点定义的详细信息  
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点  
            if ((i + 1) >= historicActivityInstances.size()) {  
                break;  
            }  
            ActivityImpl sameActivityImpl1 = processDefinitionEntity  
                    .findActivity(historicActivityInstances.get(i + 1)  
                            .getActivityId());// 将后面第一个节点放在时间相同节点的集合里  
            sameStartTimeNodes.add(sameActivityImpl1);  
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {  
                HistoricActivityInstance activityImpl1 = historicActivityInstances  
                        .get(j);// 后续第一个节点  
                HistoricActivityInstance activityImpl2 = historicActivityInstances  
                        .get(j + 1);// 后续第二个节点  
                if (activityImpl1.getStartTime().equals(  
                        activityImpl2.getStartTime())) {// 如果第一个节点和第二个节点开始时间相同保存  
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity  
                            .findActivity(activityImpl2.getActivityId());  
                    sameStartTimeNodes.add(sameActivityImpl2);  
                } else {// 有不相同跳出循环  
                    break;  
                }  
            }  
            List<PvmTransition> pvmTransitions = activityImpl  
                    .getOutgoingTransitions();// 取出节点的所有出去的线  
            for (PvmTransition pvmTransition : pvmTransitions) {// 对所有的线进行遍历  
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition  
                        .getDestination();// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示  
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {  
                    highFlows.add(pvmTransition.getId());  
                }  
            }  
        }  
        return highFlows;  
    } 
    
	@RequestMapping("/processHistory")
	@ResponseBody
	public List<HistoricActivityInstance> processHistory(@RequestParam("businessKey")String businessKey){
		  String instanceid = historyService.createHistoricProcessInstanceQuery().processDefinitionKey("purchase").processInstanceBusinessKey(businessKey).singleResult().getId();
		  System.out.println(instanceid);
		  List<HistoricActivityInstance> his = historyService.createHistoricActivityInstanceQuery().processInstanceId(instanceid).orderByHistoricActivityInstanceStartTime().asc().list();
		  return his;
	}
	
}
