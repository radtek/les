package org.wxjs.les.modules.test;

import java.util.Calendar;
import java.util.Date;

import org.wxjs.les.common.utils.DateUtils;

public class TestDate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date dt = Calendar.getInstance().getTime();
		String str = DateUtils.formatDate(dt, "yyyy-MM-dd HH:mm");
		System.out.println(str);
	}

}
