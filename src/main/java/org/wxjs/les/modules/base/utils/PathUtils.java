package org.wxjs.les.modules.base.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.wxjs.les.common.config.Global;

public class PathUtils {
	
	public static final String getRealPath(String fileUri){
		String rst = "";
		String path = "";
		try {
			path = URLDecoder.decode(fileUri, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int index = path.indexOf("/les/") + 4;
		if(index > 3){		
			rst = Global.getUserfilesBaseDir() + path.substring(index);
		}
		
		return rst;
		
	}

}