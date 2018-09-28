package org.wxjs.les.modules.scheduleJob;

import java.util.Calendar;
import java.util.List;

import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wxjs.les.modules.message.DbMessageSender;
import org.wxjs.les.modules.message.MessageSender;
import org.wxjs.les.modules.sys.dao.UserDao;
import org.wxjs.les.modules.sys.entity.User;

@Component("messageNotifyTask")
public class MessageNotifyTask {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private MessageSender messageSender = new DbMessageSender();
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserDao userDao;

	public void execute() {
		logger.debug("MessageNotifyTask start");
		long start = System.currentTimeMillis();
		
		List<User> list = userDao.findList(new User());
		
		for(User user:list){
			long count = taskService.createTaskQuery().taskAssignee(user.getLoginName()).active().count();
			if(count>0){
				try {
					messageSender.send(user, "综合行政执法系统中您有"+count+"个待办事项。");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		long end = System.currentTimeMillis();
		logger.info("MessageNotifyTask job finish, cost:" + (end - start) + " secs");

	}
}