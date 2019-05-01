package org.wxjs.les.modules.message;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.wxjs.les.modules.base.jdbc.SmsDAOHelper;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.utils.UserUtils;

public class DbMessageSender implements MessageSender {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean send(User user, String message) throws SQLException {
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(" INSERT INTO T_SendTask(DestNumber,Content,SignName,SendPriority,SendTime"); 
		buffer.append(" ,StatusReport,EnglishFlag,MsgType,PushUrl,RecAction,ValidMinute "); 
		buffer.append(" ,SendFlag,CommPort,SplitCount,batchId ,RingFlag,zt)"); 
		buffer.append(" VALUES"); 
		buffer.append(" ('"+user.getMobile()+"','"+message+"',NULL,16,SYSDATETIME()"); 
		buffer.append(" ,0,0,0,NULL,0,0"); 
		buffer.append(" ,0,0,1,NULL,0,NULL)"); 		

		
		logger.debug(buffer.toString());
		
		int effects = SmsDAOHelper.executeSQL(buffer.toString());
		
		logger.info("Post "+effects+" items to SMS database, mobile:{}, user:{}", user.getMobile(), user.getLoginName());
		
		return true;
		
	}
	
	public void send(List<User> users, String message) throws SQLException{
		for(User user : users){
			this.send(user, message);
		}
	}

	@Override
	public boolean send(String loginName, String message) throws SQLException {
		boolean flag = false;
		if(StringUtils.isEmpty(loginName)){
			return flag;
		}
		String[] strs = loginName.split(",");
		for(String str: strs){
			if(StringUtils.isEmpty(str)){
				continue;
			}
			
			User user = UserUtils.getByLoginName(str);
			
			flag = this.send(user, message);		
		}
		return flag;
	}

}
