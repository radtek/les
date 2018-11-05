package org.wxjs.les.modules.tcase.listener;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.SpringContextHolder;
import org.wxjs.les.modules.message.DbMessageSender;
import org.wxjs.les.modules.message.MessageSender;
import org.wxjs.les.modules.sys.dao.UserDao;
import org.wxjs.les.modules.sys.entity.Role;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.utils.DictUtils;
import org.wxjs.les.modules.tcase.dao.CaseProcessDao;
import org.wxjs.les.modules.tcase.dao.TcaseDao;
import org.wxjs.les.modules.tcase.entity.CaseProcess;
import org.wxjs.les.modules.tcase.entity.Tcase;

public class CaseFinishListener implements ExecutionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2293194672706571664L;

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	UserDao userDao = SpringContextHolder.getBean(UserDao.class);

	TcaseDao caseDao = SpringContextHolder.getBean(TcaseDao.class);

	CaseProcessDao caseProcessDao = SpringContextHolder.getBean(CaseProcessDao.class);

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		
		String businesskey = execution.getProcessBusinessKey();
		
		String caseStage = "";
		
		String caseId = "";
		String caseProcessId = "";
		String[] strs = businesskey.split(":");
		if(strs.length>0){
			caseId = strs[0];
			Tcase tcase = caseDao.get(caseId);
			
			logger.debug("businesskey:{}", businesskey);
			
			if(strs.length>1){
				caseProcessId = strs[1];
				CaseProcess caseProcess = caseProcessDao.get(caseProcessId);
				caseStage = caseProcess.getCaseStage();
				
				if(Global.CASE_STAGE_ACCEPTANCE.equals(caseStage)){
					//no doc to export
					
				}else if(Global.CASE_STAGE_INITIAL.equals(caseStage)){
					//finish initial

					tcase.setInitialDate(Calendar.getInstance().getTime());
					tcase.setInitialHandler(caseProcess.getCaseHandler());
					caseDao.finishInitial(tcase);
					//export 立案审批表
					
				}else if(Global.CASE_STAGE_HANDLE.equals(caseStage)){
					//export 调查报告
					//export 处理审批表
					
				}else if(Global.CASE_STAGE_NOTIFY.equals(caseStage)){
					//export 告知书
					
				}else if(Global.CASE_STAGE_DECISION.equals(caseStage)){
					//export 发文稿
					
					//export 处罚决定书
					
				}else if(Global.CASE_STAGE_SETTLE.equals(caseStage)){
					//finish settle

					tcase.setSettleDate(Calendar.getInstance().getTime());
					caseDao.finishSettle(tcase);					
					//export 结案审批表
					
				}else if(Global.CASE_STAGE_FINISH.equals(caseStage)){
					//export 备考表
					
					//update status for case

					tcase.setStatus(Global.CASE_STATUS_FINISHED);
					caseDao.updateStatus(tcase);					
				}
				
				stageFinishNotify(tcase, caseProcess);
			}
		}
	}
	
	private void stageFinishNotify(Tcase tcase, CaseProcess caseProcess){
		MessageSender sender = new DbMessageSender();
		try {
			String handler = caseProcess.getCaseHandler();
			String cause = tcase.getCaseCause();
			String stage = caseProcess.getCaseStage();
			
			if(Global.CASE_STAGE_HANDLE.equals(stage)){
				//因下一环节告知书的发起由审理科来做，需要通知审理科
				String roleStr = "slkky,slkfzr";
				User user = new User();
				Role role = new Role();
				role.setEnname(roleStr);
				user.setRole(role);
				List<User> users = userDao.findUserByRoleEnname(user);
				StringBuffer buffer = new StringBuffer();
				for(User e: users){
					buffer.append(",");
					buffer.append(e.getLoginName());
				}
				if(buffer.length()>0){
					handler = handler + buffer.toString();
				}
			}
			
			sender.send(handler, 
					"综合行政执法系统事项办结通知。项目名称："+ cause
					+", 事项："+DictUtils.getDictLabel(stage, "case_stage", "")+"。");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
