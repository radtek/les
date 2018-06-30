package org.wxjs.les.modules.message;

import org.wxjs.les.modules.base.utils.KeyValue;


public interface MessageSender {
	
	public KeyValue send(String mobiles, String message) throws Exception;

}
