package org.wxjs.les.modules.base.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.wxjs.les.common.config.Global;

public class PathUtils {
	
	public static final String getRealPath(String fileUri){
		String rst = "";
		String path = decodeUri(fileUri);
		
		int index = path.indexOf("/les/") + 4;
		if(index > 3){		
			rst = Global.getUserfilesBaseDir() + path.substring(index);
		}
		
		return rst;
		
	}
	
	public static final String decodeUri(String uri){
		String rst = "";
		try {
			rst = URLDecoder.decode(uri, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return rst;
	}
	
	public static final String makeFilePath(String userId, String caseId, String filename){
		StringBuffer buffer = new StringBuffer();
		buffer.append("/les/userfiles/");
		buffer.append(userId);
		buffer.append("/files/");
		buffer.append(caseId);
		buffer.append("/");
		String filenameEn = "";
		try {
			filenameEn = URLEncoder.encode(filename, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		buffer.append(filenameEn);
		
		return buffer.toString();
	}

}