package org.wxjs.les.common.message;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wxjs.les.modules.base.jdbc.SmsDAOHelper;

public class DbMessageSender implements MessageSender {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean send(String mobiles, String message) throws SQLException {
		List<String> sqls = new ArrayList<String>();
		String[] items = mobiles.split(",");
		int index = 1;
		for(String item: items){
			if(!item.equals("")){
				StringBuffer buffer = new StringBuffer();
				String id = "VEHICLE-"+System.currentTimeMillis()+"-"+index;
				
				buffer.append(" insert into sms_outbox (sismsid, extcode, destaddr, messagecontent,"); 
				buffer.append(" reqdeliveryreport,msgfmt,sendmethod,requesttime,applicationid)");
				buffer.append(" VALUES ('"+id+"', '70', '"+item+"',"); 
				buffer.append(" '"+message+"', 1, 15, 0, now(), 'oa')");
				
				logger.debug(buffer.toString());
				
				sqls.add(buffer.toString());
				index++;
			}
			
		}
		
		int effects = SmsDAOHelper.executeSQL(sqls);
		
		logger.info("Post "+effects+" items to SMS database");
		
		return true;
		
	}

}
