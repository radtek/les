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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.Encodes;
import org.wxjs.les.common.utils.IdGen;
import org.wxjs.les.common.utils.PdfUtil;
import org.wxjs.les.common.utils.SpringContextHolder;
import org.wxjs.les.modules.base.dao.SignatureDao;
import org.wxjs.les.modules.base.entity.Signature;
import org.wxjs.les.modules.sys.dao.UserDao;
import org.wxjs.les.modules.sys.utils.DictUtils;
import org.wxjs.les.modules.tcase.entity.Tcase;

import com.google.common.collect.Lists;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;


public abstract class ExportBase<T> {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected int tableWidth = 100;
	
	protected float borderWidth = 0.5f;
	
	protected final static Font fontTitle = PdfUtil.getTitle22Font(true);
	
	protected final static Font fontContent = PdfUtil.getFont12(Font.NORMAL);
	
	protected final static Font fontContentSmall = PdfUtil.getFont10(Font.NORMAL);
	
	protected final static Font fontContentTiny = PdfUtil.getFont8(Font.NORMAL);
	
	protected final static Font fontNumber = PdfUtil.getFont10(Font.NORMAL);
	
	protected SignatureDao signatureDao = SpringContextHolder.getBean(SignatureDao.class);
	
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
    
    protected PdfPTable getPartyInfo(Tcase tcase){
    	PdfPTable table = null;
    	if("单位".equals(tcase.getPartyType())){
    		try {
				table = this.getPartyInfo4Org(tcase);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}else{
    		try {
				table = this.getPartyInfo4Individual(tcase);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return table;
    }
    
    protected PdfPTable getPartyInfo4Org(Tcase tcase) throws DocumentException{
    	PdfPTable table = null;
    	
        table = new PdfPTable(6);
        table.setWidths(new float[]{0.1f, 0.1f, 0.15f, 0.3f, 0.15f, 0.2f});
        table.setWidthPercentage(tableWidth);
        
        final float minheight = 25;

        PdfPCell cell;
    	
    	cell = PdfUtil.getContentCell("当\n事\n人\n情\n况", Element.ALIGN_LEFT, borderWidth, fontContent, 3, 1, 0);

    	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell("法人或其他组织", Element.ALIGN_LEFT, borderWidth, fontContent, 3, 1, 0);
    	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell("名称", Element.ALIGN_LEFT, borderWidth, fontContent, minheight);
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell(tcase.getOrgName(), Element.ALIGN_LEFT, borderWidth, fontContent);
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell("法定代表人", Element.ALIGN_LEFT, borderWidth, fontContent, minheight);
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell(tcase.getOrgAgent(), Element.ALIGN_LEFT, borderWidth, fontContent);
    	table.addCell(cell);  
    	
    	cell = PdfUtil.getContentCell("统一社会\n信用代码", Element.ALIGN_LEFT, borderWidth, fontContent, minheight);
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell(tcase.getOrgCode(), Element.ALIGN_LEFT, borderWidth, fontContent);
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell("负责人", Element.ALIGN_LEFT, borderWidth, fontContent, minheight);
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell(tcase.getOrgResponsiblePerson()+" ("+tcase.getOrgResponsiblePersonPost()+")", Element.ALIGN_LEFT, borderWidth, fontContent);
    	table.addCell(cell); 
    	
    	cell = PdfUtil.getContentCell("住址", Element.ALIGN_LEFT, borderWidth, fontContent, minheight);
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell(tcase.getOrgAddress(), Element.ALIGN_LEFT, borderWidth, fontContent);
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell("联系电话", Element.ALIGN_LEFT, borderWidth, fontContent, minheight);
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell(tcase.getOrgPhone(), Element.ALIGN_LEFT, borderWidth, fontContent);
    	table.addCell(cell); 
    	
    	return table;
    }
    
    protected PdfPTable getPartyInfo4Individual(Tcase tcase) throws DocumentException{
    	PdfPTable table = null;
    	
        table = new PdfPTable(6);
        table.setWidths(new float[]{0.1f, 0.1f, 0.15f, 0.3f, 0.15f, 0.2f});
        table.setWidthPercentage(tableWidth);
        
        final float minheight = 25;

        PdfPCell cell;
    	
    	cell = PdfUtil.getContentCell("当\n事\n人\n情\n况", Element.ALIGN_LEFT, borderWidth, fontContent, 3, 1, 0);

    	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell("公民", Element.ALIGN_LEFT, borderWidth, fontContent, 3, 1, 0);
    	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell("姓名", Element.ALIGN_LEFT, borderWidth, fontContent, minheight);
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell(tcase.getPsnName(), Element.ALIGN_LEFT, borderWidth, fontContent);
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell("性别", Element.ALIGN_LEFT, borderWidth, fontContent, minheight);
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell(DictUtils.getDictLabel(tcase.getPsnSex(),"sex",""), Element.ALIGN_LEFT, borderWidth, fontContent);
    	table.addCell(cell);  
    	
    	cell = PdfUtil.getContentCell("身份证", Element.ALIGN_LEFT, borderWidth, fontContent, minheight);
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell(tcase.getPsnCode(), Element.ALIGN_LEFT, borderWidth, fontContent);
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell("出生年月", Element.ALIGN_LEFT, borderWidth, fontContent, minheight);
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell(DateUtil.formatDate(tcase.getPsnBirthday(), "yyyy-MM"), Element.ALIGN_LEFT, borderWidth, fontContent);
    	table.addCell(cell); 
    	
    	cell = PdfUtil.getContentCell("住址", Element.ALIGN_LEFT, borderWidth, fontContent, minheight);
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell(tcase.getPsnAddress(), Element.ALIGN_LEFT, borderWidth, fontContent);
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell("联系电话", Element.ALIGN_LEFT, borderWidth, fontContent, minheight);
    	table.addCell(cell);
    	
    	cell = PdfUtil.getContentCell(tcase.getPsnPhone(), Element.ALIGN_LEFT, borderWidth, fontContent);
    	table.addCell(cell); 
    	
    	return table;
    }
    
    public PdfPTable getSignatureTable(String procInstId) throws DocumentException{
    	
		return this.getSignatureTable(procInstId, "");
    }
    
    public PdfPTable getSignatureTable(String procInstId, String taskName) throws DocumentException{
		//get signatures
		Signature signatureParam = new Signature(false);
		signatureParam.setProcInstId(procInstId);
		if(!StringUtils.isEmpty(taskName)){
			signatureParam.setTaskName(taskName);
		}
		List<Signature> signatures = signatureDao.findList(signatureParam);
    	
    	PdfPTable table = new PdfPTable(4);
    	table.setWidths(new float[]{0.1f, 0.4f, 0.1f, 0.4f});
    	table.setWidthPercentage(tableWidth);
    	
    	PdfPCell cell;
    	
    	//filter 办案人意见，做特殊处理  	
    	List<Signature> sigsOthers = Lists.newArrayList();
    	String taskNameBar = "办案人意见";
    	String opinionBar = "";
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.YEAR, -100);
    	Date dateBar = cal.getTime();
    	List<String> sigStrBar = Lists.newArrayList();
    	
    	for(Signature sig : signatures){
    		if(taskNameBar.equals(sig.getTaskName())){
    			sigStrBar.add(sig.getSignature());
    			if(sig.getApproveOpinion().length() > opinionBar.length()){
    				opinionBar = sig.getApproveOpinion();
    			}
    			if(sig.getUpdateDate().compareTo(dateBar)>0){
    				dateBar = sig.getUpdateDate();
    			}
    		}else{
    			sigsOthers.add(sig);
    		}
    	}
    	
    	//办案人签名输出
    	cell = PdfUtil.getContentCell(PdfUtil.transferVertical(taskNameBar), Element.ALIGN_LEFT, borderWidth, fontContent, 1, 1, 0);
    	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
    	table.addCell(cell);
    	
    	//sub table
    	PdfPTable tableSub = new PdfPTable(2);
    	tableSub.setWidths(new float[]{0.2f, 0.8f});
    	
    	//opinion
        Font font = fontContentSmall;
        
        if(opinionBar.length()>50){
        	font = fontContentTiny;
        }
    	cell = PdfUtil.getContentCell(opinionBar, Element.ALIGN_LEFT, 0, font, 1, 2, 0);
    	cell.setMinimumHeight(50);
    	tableSub.addCell(cell);
    	
    	//signature
		Phrase phrase = new Phrase("签名", fontContent);
		cell = new PdfPCell(phrase);
    	cell.setBorderWidth(0);
    	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中    	
    	tableSub.addCell(cell); 
    	
    	PdfPTable tableSubSig = new PdfPTable(sigStrBar.size());
    	for(String sigStr : sigStrBar){
        	cell = new PdfPCell();
        	cell.setBorderWidth(0);
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中 
        	cell.addElement(PdfUtil.getSignatureImage(sigStr));   
        	tableSubSig.addCell(cell);
    	}
    	cell = new PdfPCell(tableSubSig);
    	cell.setBorderWidth(0);
    	tableSub.addCell(cell); 
    	//date
		cell = new PdfPCell();
    	cell.setBorderWidth(0);
    	tableSub.addCell(cell);
		phrase = new Phrase(DateUtil.formatDate(dateBar, "yyyy年MM月dd日"), fontContentSmall);
		cell = new PdfPCell(phrase);
    	cell.setBorderWidth(0);
    	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
    	
    	tableSub.addCell(cell);
    	
    	table.addCell(tableSub);    	
    	
    	
    	for(Signature sig : sigsOthers){
        	cell = PdfUtil.getContentCell(PdfUtil.transferVertical(sig.getTaskName()), Element.ALIGN_LEFT, borderWidth, fontContent, 1, 1, 0);
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
        	table.addCell(cell);
        	
        	//sub table
        	tableSub = new PdfPTable(3);
        	tableSub.setWidths(new float[]{0.15f, 0.45f, 0.4f});
        	//opinion
        	cell = PdfUtil.getContentCell(sig.getApproveOpinion(), Element.ALIGN_LEFT, 0, fontContentSmall, 1, 3, 0);
        	cell.setMinimumHeight(30);
        	tableSub.addCell(cell);
        	//signature
    		phrase = new Phrase("签名", fontContent);
    		cell = new PdfPCell(phrase);
        	cell.setBorderWidth(0);
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中    	
        	tableSub.addCell(cell);  
        	
        	cell = new PdfPCell();
        	cell.setBorderWidth(0);
        	cell.addElement(PdfUtil.getSignatureImage(sig.getSignature()));
        	tableSub.addCell(cell); 
        	//date
    		phrase = new Phrase(DateUtil.formatDate(sig.getUpdateDate(), "yyyy年MM月dd日"), fontContentSmall);
    		cell = new PdfPCell(phrase);
        	cell.setBorderWidth(0);
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
        	
        	tableSub.addCell(cell);
        	
        	table.addCell(tableSub);
    	}
    	
    	//单数，补空
    	if((signatures.size() % 2) ==1){
        	cell = PdfUtil.getContentCell("", Element.ALIGN_LEFT, borderWidth, fontContent, 1, 1, 0);
        	table.addCell(cell);   
        	table.addCell(cell);
    	}
    	
    	return table;   	
    }


}
