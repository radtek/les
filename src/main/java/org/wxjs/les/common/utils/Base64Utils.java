package org.wxjs.les.common.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
 
import org.apache.commons.codec.binary.Base64;


public class Base64Utils {
	public static String ImageToBase64(String imgFile) {
 
 
		InputStream in = null;
		byte[] data = null;
		try {
			in = new FileInputStream(imgFile);
			
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
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
 
	public static void main(String[] args) throws Exception {

		String url = "F:/test1.jpg";
		
		System.out.println(url);
		
		String str = Base64Utils.ImageToBase64(url);
		
		System.out.println(str);
		
		Base64Utils.Base64ToImage(str,"F:/test2.jpg");
	}
	
}

