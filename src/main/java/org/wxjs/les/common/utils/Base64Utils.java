package org.wxjs.les.common.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
 
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;


public class Base64Utils {
	public static String ImageToBase64(String imgFile) {
 
 
		InputStream in = null;
		byte[] data = null;
		try {
			in = new FileInputStream(imgFile);
			
			data = new byte[in.available()];
			in.read(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			
			if(in!=null){
				try {
					in.close();
					//data = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
 
		return Base64.encodeBase64String(data);
	}

	public static boolean Base64ToImage(String imgStr,String imgFilePath) {
		boolean flag = false;
		OutputStream out = null;
		try {
			byte[] b = Base64.decodeBase64(imgStr);
 
			out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
 
			flag = true;
		} catch (Exception e) {
			return false;
		} finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
	
	
	public static String decode(String src){
		String rst = "";
		if(!StringUtils.isBlank(src)){
			byte[] bytes =  org.codehaus.xfire.util.Base64.decode(src);
			
			rst = new String(bytes);			
		}
		
		return rst;
	}
	
	public static String encode(String src){
		String rst = "";
		if(!StringUtils.isBlank(src)){
			rst = org.codehaus.xfire.util.Base64.encode(src.getBytes());		
		}
		
		return rst;
	}
 
	public static void main(String[] args) throws Exception {

		String url = "F:/test1.jpg";
		
		System.out.println(url);
		
		String str = Base64Utils.ImageToBase64(url);
		
		System.out.println(str);
		
		Base64Utils.Base64ToImage(str,"F:/test2.jpg");
	}
	
}

