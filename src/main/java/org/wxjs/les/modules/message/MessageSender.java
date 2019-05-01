package org.wxjs.les.modules.message;

import java.sql.SQLException;
import java.util.List;

import org.wxjs.les.modules.sys.entity.User;

public interface MessageSender {
	
	public boolean send(User user, String message) throws SQLException;
	
	public void send(List<User> users, String message) throws SQLException;
	
	public boolean send(String loginName, String message) throws SQLException;

}
