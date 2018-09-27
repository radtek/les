package org.wxjs.les.common.message;;

public interface MessageSender {
	
	public boolean send(String mobiles, String message) throws Exception;

}
