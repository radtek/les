/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.export;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.Encodes;
import org.wxjs.les.common.utils.IdGen;
import org.wxjs.les.common.utils.PdfUtil;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;


public abstract class ExportBase<T> {
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	protected int tableWidth = 100;
	
	protected float borderWidth = 0.5f;
	
	protected final static Font fontTitle = PdfUtil.getTitle22Font(true);
	
	protected final static Font fontContent = PdfUtil.getFont12(Font.NORMAL);
	
	public abstract void generate(OutputStream os) throws DocumentException;

	
	/**
	 * 输出到客户端
	 * @param fileName 输出文件名
	 * @throws DocumentException 
	 */
	public <T> T write(HttpServletResponse response, String fileName) throws IOException, DocumentException{
		response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename="+Encodes.urlEncode(fileName));
		this.generate(response.getOutputStream());
		return (T)this;
	}
	
	/**
	 * 输出到文件
	 * @param fileName 输出文件名
	 * @throws DocumentException 
	 */
	public <T> T  writeFile(String name) throws FileNotFoundException, IOException, DocumentException{
		FileOutputStream os = new FileOutputStream(name);
		this.generate(os);
		return (T)this;
	}
	
    protected String base64StringToImage(String base64String) {
    	String filename = Global.getConfig("userfiles.basedir") + "/" + IdGen.uuid()+".png";
        try {
        	
            byte[] bytes1 = Base64.decodeBase64(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 = ImageIO.read(bais);
            File f1 = new File(filename);
            ImageIO.write(bi1, "png", f1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }


}
