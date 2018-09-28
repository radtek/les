package org.wxjs.les.modules.message;

import org.wxjs.les.modules.sys.entity.User;

public interface MessageSender {
	
	public boolean send(User user, String message) throws Exception;

}
