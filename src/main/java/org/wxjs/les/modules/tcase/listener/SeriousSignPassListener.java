package org.wxjs.les.modules.tcase.listener;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeriousSignPassListener implements ExecutionListener {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String eventName = execution.getEventName();
		
		logger.debug("eventName:{}, BusinessKey():{}, ProcessInstanceId():{}",
				eventName, 
				execution.getProcessBusinessKey(),
				execution.getProcessInstanceId());
		Map<String, Object> map = execution.getVariables();
		for(String key :map.keySet()){
			logger.debug("key:{}, value:{}", key, map.get(key));
		}
	}

}
