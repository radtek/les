package org.wxjs.les.common.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wxjs.les.common.utils.SpringContextHolder;
import org.wxjs.les.modules.sys.dao.UserDao;
import org.wxjs.les.modules.sys.entity.User;

public class MessageUtils {
	
	protected static Logger logger = LoggerFactory.getLogger(MessageUtils.class);
	
	private static MessageSender instance = new DbMessageSender();

	protected static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
	
	public static String getLongByLoginName(String loginName){
		User userParam = new User();
		User user = userDao.getByLoginName(userParam);
		return user.getMobile();		
	}
	
	public static void sendByLoginName(String loginName, String message){
		String phone = getLongByLoginName(loginName);
		
		sendByLong(phone, message);
	}
	
	public static void sendByLong(String mobiles, String message){
		try {			
			instance.send(mobiles, message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
