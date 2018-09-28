package org.wxjs.les.modules.message;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wxjs.les.modules.base.jdbc.SmsDAOHelper;
import org.wxjs.les.modules.sys.entity.User;

public class DbMessageSender implements MessageSender {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean send(User user, String message) throws SQLException {
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(" insert into smsMessage (sendUserName,receiveUserName,sendTime,saveTime,"); 
		buffer.append(" content,receiveMobilePhone,isSend)");
		buffer.append(" VALUES ('"+user.getName()+"', '"+user.getName()+"',SYSDATETIME(),SYSDATETIME(),"); 
		buffer.append(" '"+message+"', '"+user.getMobile()+"', 'false')");
		
		logger.debug(buffer.toString());
		
		int effects = SmsDAOHelper.executeSQL(buffer.toString());
		
		logger.info("Post "+effects+" items to SMS database");
		
		return true;
		
	}

}
