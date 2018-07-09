/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.qa.export;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.Encodes;
import org.wxjs.les.common.utils.FileUtils;
import org.wxjs.les.common.utils.IdGen;
import org.wxjs.les.common.utils.PdfUtil;
import org.wxjs.les.modules.qa.entity.Questionanswer;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Image;

public class QuestionanswerExport {
	
	private static Logger log = LoggerFactory.getLogger(QuestionanswerExport.class);
	
	private final static int tableWidth = 90;
	
	private final static float borderWidth = 0.5f;
	
	final static float[] widths = new float[]{0.25f, 0.12f, 0.16f ,0.24f ,0.23f};
	
	final static float[] widthsGap = new float[]{0.25f, 0.12f, 0.16f ,0.47f};
	
	private Questionanswer qa;
	
	public QuestionanswerExport( Questionanswer qa){
		this.qa = qa;
	}
	
	private void generate(OutputStream os) throws DocumentException{
		
		Document document = null;
		PdfWriter writer = null;
		
		PdfPTable table = null;
        Phrase phrase = null;
        
        Paragraph pragraph = null;
        
    	phrase = new Phrase("");
        PdfPCell cell_pending = new PdfPCell(phrase);
        cell_pending.setBorderWidth(0);
        
		try{
			document = new Document(PageSize.A4);
			
			writer = PdfWriter.getInstance(document, os);
			
            document.open();
            
            //add title
            
            pragraph = new Paragraph("询问笔录", PdfUtil.getTitle1Font(true));
            pragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(pragraph);
            
            document.add(PdfUtil.generateTable4Padding());
            
            String[] items;
            
            //案由
            items = new String[]{"案由：", this.qa.getCaseCause()};
            table = PdfUtil.generateTableRow(items, PdfUtil.getTextFont(true), new float[]{0.1f, 0.9f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 0);
            document.add(table);
            
            //时间
            items = new String[]{"时间：", DateUtils.formatDate(this.qa.getFromDate(),"yyyy-MM-dd HH:mm:ss")+"至"+DateUtils.formatDate(this.qa.getToDate(),"yyyy-MM-dd HH:mm:ss")};
            table = PdfUtil.generateTableRow(items, PdfUtil.getTextFont(true), new float[]{0.1f, 0.9f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 0);           
            document.add(table);
	        
            //地点
            items = new String[]{"地点：", this.qa.getLocation()};
            table = PdfUtil.generateTableRow(items, PdfUtil.getTextFont(true), new float[]{0.1f, 0.9f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 0);           
            document.add(table);	
            
            //调查询问人等
            items = new String[]{"调查询问人：", this.qa.getQuizzer(), "记录人：", this.qa.getRecorder()};
            table = PdfUtil.generateTableRow(items, PdfUtil.getTextFont(true), new float[]{0.25f, 0.35f, 0.2f, 0.2f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 0);           
            document.add(table);
            
            //被询问人等
            items = new String[]{"被调查询问人：", this.qa.getAnswerer(), "性别：", this.qa.getAnswererSex(), "身份证号码：", this.qa.getAnswererCode()};
            table = PdfUtil.generateTableRow(items, PdfUtil.getTextFont(true), new float[]{0.2f, 0.2f, 0.1f, 0.1f, 0.2f, 0.2f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 0);           
            document.add(table);
            
            //工作单位等
            items = new String[]{"工作单位：", this.qa.getAnswererOrganization(), "职务：", this.qa.getAnswererPost(), "出生年月：", DateUtils.formatDate(this.qa.getAnswererBirthday(), "yyyy-MM")};
            table = PdfUtil.generateTableRow(items, PdfUtil.getTextFont(true), new float[]{0.15f, 0.3f, 0.1f, 0.15f, 0.15f, 0.15f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 0);           
            document.add(table);
            
            //地址等
            items = new String[]{"地址：", this.qa.getAnswererAddress(), "邮政编码：", this.qa.getZipCode(), "电话：", this.qa.getAnswererPhone()};
            table = PdfUtil.generateTableRow(items, PdfUtil.getTextFont(true), new float[]{0.15f, 0.3f, 0.1f, 0.15f, 0.15f, 0.15f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 0);           
            document.add(table);
            
            //问答
            items = new String[]{this.qa.getQaContent()};
            table = PdfUtil.generateTableRow(items, PdfUtil.getTextFont(true), new float[]{1.0f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 0);           
            document.add(table);
            
            //签名
            
            Image qsig = null;
            try {
            	String filename = this.base64StringToImage(this.qa.getQsig().getSignature());
				qsig = Image.getInstance(filename);
				FileUtils.deleteFile(filename);
			} catch (MalformedURLException e) {
				log.error("qsig error", e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("qsig error", e);
			}
            
            Image asig = null;
            try {
            	String filename = this.base64StringToImage(this.qa.getAsig().getSignature());
				asig = Image.getInstance(filename);
				FileUtils.deleteFile(filename);
			} catch (MalformedURLException e) {
				log.error("asig error", e);
			} catch (IOException e) {
				log.error("asig error", e);
			}
            
            table = new PdfPTable(4);
            table.setWidths(new float[]{0.2f, 0.3f, 0.2f, 0.3f});
            table.setWidthPercentage(tableWidth);
            
            PdfPCell cell;
    	
            phrase = new Phrase("被询问人（签名）", PdfUtil.getTextFont(true));
            cell = new PdfPCell(phrase);
            cell.setBorderWidth(borderWidth);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);

            table.addCell(cell);
            
            table.addCell(qsig);
            
            phrase = new Phrase("调查询问人（签名）", PdfUtil.getTextFont(true));
            cell = new PdfPCell(phrase);
            cell.setBorderWidth(borderWidth);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);

            table.addCell(cell);      
            
            table.addCell(asig);
            
			document.add(table);
			
		}finally{
			if(document!=null){
				try{
					document.close();
				}catch(Exception ex){
				}
			}
		}
	}
	
	private PdfPCell getContentCell(String content, int align){
		Phrase phrase = new Phrase(content, PdfUtil.getTextFont(false));
		PdfPCell cell = new PdfPCell(phrase);
    	cell.setBorderWidth(0);
    	cell.setHorizontalAlignment(align);
    	return cell;
	}

	
	/**
	 * 输出到客户端
	 * @param fileName 输出文件名
	 * @throws DocumentException 
	 */
	public QuestionanswerExport write(HttpServletResponse response, String fileName) throws IOException, DocumentException{
		response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename="+Encodes.urlEncode(fileName));
		this.generate(response.getOutputStream());
		return this;
	}
	
	/**
	 * 输出到文件
	 * @param fileName 输出文件名
	 * @throws DocumentException 
	 */
	public QuestionanswerExport writeFile(String name) throws FileNotFoundException, IOException, DocumentException{
		FileOutputStream os = new FileOutputStream(name);
		this.generate(os);
		return this;
	}
	
    public String base64StringToImage(String base64String) {
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
