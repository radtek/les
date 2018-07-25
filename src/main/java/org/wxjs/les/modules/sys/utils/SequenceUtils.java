package org.wxjs.les.modules.sys.utils;

import java.util.Calendar;

import org.wxjs.les.common.utils.SpringContextHolder;
import org.wxjs.les.common.utils.Util;
import org.wxjs.les.modules.base.dao.SequenceDao;
import org.wxjs.les.modules.base.entity.Sequence;



public class SequenceUtils {
	
	private static SequenceDao sequenceDao = SpringContextHolder.getBean(SequenceDao.class);
	
	public synchronized static long fetchSeq(String name){
		long seq = 1;
		
		Sequence entity = sequenceDao.getByName(name);
		
		if(entity == null){
			entity = new Sequence();
			entity.setName(name);
			entity.setNextid("2");
			sequenceDao.insert(entity);
		}else{
			seq = Util.getLong(entity.getNextid());
			entity.setNextid((seq+1)+"");
			sequenceDao.update(entity);
		}
		
		return seq;
	}
	
	/**
	 * add year in unique key
	 * @param name
	 * @return
	 */
	public static long fetchYearSeq(String name){
		String key = name + Calendar.getInstance().get(Calendar.YEAR);
		return fetchSeq(key);
	}
	
	public static String fetchCaseSeqStr(){
		String year = Calendar.getInstance().get(Calendar.YEAR) + "";
		String seq = fetchYearSeq("case_seq") +"";
		return "T-"+year+extendStr(seq, 5);
	}
	
	public static String fetchCaseTransferSeqStr(){
		String year = Calendar.getInstance().get(Calendar.YEAR) + "";
		String seq = fetchYearSeq("case_transfer_seq") +"";
		
		return "TR-"+year+extendStr(seq, 5);
	}
	
	public static String extendStr(String str, int length){
		StringBuffer buffer = new StringBuffer();
		buffer.append(str);
		while(buffer.length()<length){
			buffer.insert(0, "0");
		}
		return buffer.toString();
	}

}
