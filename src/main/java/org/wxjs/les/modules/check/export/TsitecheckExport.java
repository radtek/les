package org.wxjs.les.modules.check.export;

import com.google.common.collect.Lists;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.Encodes;
import org.wxjs.les.common.utils.FileUtils;
import org.wxjs.les.common.utils.IdGen;
import org.wxjs.les.common.utils.PdfUtil;
import org.wxjs.les.modules.base.utils.PathUtils;
import org.wxjs.les.modules.check.entity.Tsitecheck;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.util.DateUtil;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

public class TsitecheckExport {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    // 表格宽、高
    private final static int tableWidth = 90;
    private final static float borderWidth = 0.5f;

    // 默认实体类对象
    private Tsitecheck tsitecheck;
    private static BaseFont bfChinese = null;
    private static Map<String, Font> fontMap = new HashMap<String, Font>();

    /**
     * 静态资源初始化
     */
    static {
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            // 设置字体样式
            fontMap.put("textFont", new Font(bfChinese, 12, Font.NORMAL)); // 正常
            fontMap.put("subjectFont", new Font(bfChinese, 22, Font.BOLD)); // 加粗
            fontMap.put("firstTitleFont", new Font(bfChinese, 18, Font.BOLD)); // 一级标题
            fontMap.put("secondTitleFont", new Font(bfChinese, 15, Font.BOLD)); // 二级标题
            fontMap.put("underlineFont", new Font(bfChinese, 11, Font.UNDERLINE)); // 下划线斜体
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建表格给标题
     * msg     表格内容
     * colSpan 合并列
     * rolSpan 合并行
     * borderWidth 内容宽度
     * borderHeight 内容高度
     */
    private PdfPCell createPDFCellToTitle(String msg, Font font, int rowSpan, int colSpan, float borderWidth, float borderHeight) {
        // 创建单元格对象，将内容及字体传入
        PdfPCell cell = new PdfPCell(new Paragraph(msg, font));
        // 设置单元格内容样式
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);   // 设置水平样式
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);     //设置垂直样式
        // 内容宽度、高度 
        cell.setBorderWidth(borderWidth);
        cell.setMinimumHeight(borderHeight);
        // 合并行
        if (rowSpan > 0) {
            cell.setRowspan(rowSpan);
        }
        // 合并列
        if (colSpan > 0) {
            cell.setColspan(colSpan);
        }
        return cell;
    }

    /**
     * 创建表格给内容
     */
    private PdfPCell createPDFCellToContent(String msg, Font font, int rowSpan, int colSpan, float borderWidth, float borderHeight) {
        // 创建单元格对象，将内容及字体传入
        PdfPCell cell = new PdfPCell(new Paragraph(msg, font));
        // 内容宽度、高度
        cell.setBorderWidth(borderWidth);
        cell.setMinimumHeight(borderHeight);
        // 合并行
        if (rowSpan > 0) {
            cell.setRowspan(rowSpan);
        }
        // 合并列
        if (colSpan > 0) {
            cell.setColspan(colSpan);
        }
        return cell;
    }

    
    /**
     * 根据字符串解析图片
     * @param imagePaths 图片字符串(多张)
     * @param cutWidth 剪切后的图片宽度
     * @param cutHeight 剪切后的图片高度
     * @return
     */
    private PdfPCell createPDFCellToImage(String imagePaths, int cutWidth, 
    		int cutHeight, int rowSpan, int colSpan, float borderWidth, float borderHeight) {
    	
    	String[] images = imagePaths.split("\\|");
    	List<String> imageList= Lists.newArrayList();
    	for(String str : images){
    		if(!StringUtils.isEmpty(str)){
    			imageList.add(str);
    		}
    	}
    	
    	PdfPCell cell = null;
    	
        try {
        	/*
            PdfPCell cell = new PdfPCell();
            cell.setBorderWidth(borderWidth);
            cell.setMinimumHeight(borderHeight);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            */
            if(!StringUtils.isEmpty(images)) {

                
                /*
                Paragraph paragraph = new Paragraph();
                for(String imageStr : imageList) {
                	//数组的第一个元素是空，后面两个是路径，因为当不为空的时候，执行下面的内容
                	if(!"".equals(imageStr)) {
                    	String imagePath = PathUtils.getRealPath(imageStr);
                        Image image = Image.getInstance(imagePath);
                        image.scaleToFit(cutWidth, cutHeight);
                        image.setAlignment(Element.ALIGN_CENTER);
                        paragraph.add(new Chunk(image, 0, 0, true));     //使文字与图片处于同一行
                	}
                }
                */
                
            	PdfPTable tableSubSig = new PdfPTable(imageList.size());
            	for(String imageStr : imageList){
            		PdfPCell cellTemp = new PdfPCell();
            		cellTemp.setBorderWidth(0);
            		cellTemp.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
            		cellTemp.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中 
            		String imagePath = PathUtils.getRealPath(imageStr);
                    Image image = Image.getInstance(imagePath);
                    image.scaleToFit(cutWidth, cutHeight);
                    image.setAlignment(Element.ALIGN_CENTER);
            		cellTemp.addElement(image);   
                	tableSubSig.addCell(cellTemp);
            	}
            	
            	cell = new PdfPCell(tableSubSig);
            	
                // 合并行
                if (rowSpan > 0) {
                    cell.setRowspan(rowSpan);
                }
                // 合并列
                if (colSpan > 0) {
                    cell.setColspan(colSpan);
                }
                
            	cell.setBorderWidth(borderWidth);
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中    	
                
                //cell.addElement(paragraph);
            }
            return cell;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("图片转换异常 {}", e.getMessage());
        }
        return null;
    }

    public TsitecheckExport(Tsitecheck tsitecheck) {
        this.tsitecheck = tsitecheck;
    }

    /**
     * 生成PDF对象数据
     *
     * @param os OutputStream对象
     */
    public void generate(OutputStream os) {
        // 正常的表格内容高度(非PDF高度)
        final int normalBorderHeight = 32;
        // 表格内内容的高度(表中的大表格，非PDF高度)
        final int contentTableHeight = 80;
        // 超大号表内内容的高度
        final int contentBigTableHeight = 130;
        // 缺省的图片裁剪高宽
        final int defaultImageWidth = 150;
        final int defaultImageHeight = 400;

        // 生成上下文对象
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, os);
            document.open();
            // 创建table对象(五列)
            float[] widths = new float[]{8, 12, 43, 12, 25};
            PdfPTable table = new PdfPTable(widths.length);
            table.setWidths(widths);
            table.setWidthPercentage(tableWidth);
         

            // 添加标题
            Paragraph paragraph = new Paragraph("现场踏勘情况", fontMap.get("subjectFont"));
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(paragraph);
            document.add(PdfUtil.generateTable4Padding());

            /* ============ 表格中的第一部分数据 ============ */
            table.addCell(createPDFCellToTitle("当事人情况", fontMap.get("textFont"), 4, 0, borderWidth, normalBorderHeight));

            table.addCell(createPDFCellToTitle("建设单位", fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));
            table.addCell(createPDFCellToContent(tsitecheck.getDevelopOrg(), fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));

            table.addCell(createPDFCellToTitle("联系人姓名、电话", fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));
            table.addCell(createPDFCellToContent(tsitecheck.getDevelopContact() + "  " + tsitecheck.getDevelopPhone(), fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));

			table.addCell(createPDFCellToTitle("施工单位", fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));
            table.addCell(createPDFCellToContent(tsitecheck.getConstructionOrg(), fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));

			table.addCell(createPDFCellToTitle("联系人姓名、电话", fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));
			table.addCell(createPDFCellToContent(tsitecheck.getConstructionContact() + "  " + tsitecheck.getConstructionPhone(), fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));

			table.addCell(createPDFCellToTitle("工程名称", fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));
			table.addCell(createPDFCellToContent(tsitecheck.getProjectName(), fontMap.get("textFont"), 0, 3, borderWidth, normalBorderHeight));

			table.addCell(createPDFCellToTitle("工程地址", fontMap.get("textFont"), 0, 0, borderWidth, normalBorderHeight));
			table.addCell(createPDFCellToContent(tsitecheck.getProjectAddress(), fontMap.get("textFont"), 0, 3, borderWidth, normalBorderHeight));

            /* ============ 表格中的第二部分数据 ============ */
            table.addCell(createPDFCellToTitle("现场工程检查情况", fontMap.get("textFont"), 0, 0, borderWidth, contentTableHeight));
            table.addCell(createPDFCellToContent(tsitecheck.getSiteSituation(), fontMap.get("textFont"), 0, 4, borderWidth, contentTableHeight));

            /* ============ 表格中的第三部分数据 ============ */
            table.addCell(createPDFCellToTitle("现场踏勘示意图", fontMap.get("textFont"), 0, 0, borderWidth, contentBigTableHeight));
            table.addCell(createPDFCellToImage(tsitecheck.getSitePicture(), defaultImageWidth, defaultImageHeight, 0, 4, borderWidth, contentBigTableHeight));

            /* ============ 表格中的第四部分数据 ============ */
            table.addCell(createPDFCellToTitle("现场踏勘情况", fontMap.get("textFont"), 0, 0, borderWidth, contentTableHeight));
            table.addCell(createPDFCellToContent(tsitecheck.getSiteCheckResult(), fontMap.get("textFont"), 0, 4, borderWidth, contentTableHeight));
            document.add(table);

            /* ============ 表格中的第五部分数据 ============ */
            Image checkerSig=null;
            if(this.tsitecheck.getCheckerSig()!=null && !StringUtils.isEmpty(this.tsitecheck.getCheckerSig().getSignature())){
                try {
              	   String filename=PdfUtil.base64StringToImage(this.tsitecheck.getCheckerSig().getSignature());
              	   checkerSig=Image.getInstance(filename);
              	   FileUtils.deleteFile(filename);
                 } catch (MalformedURLException e) {
             	   logger.error("checkerSig error",e);
                } catch (IOException e) {
              	   logger.error("checkerSig error",e);
                }
            	
            }
         
           Image partySig=null;
            
           if(this.tsitecheck.getPartySig()!=null && !StringUtils.isEmpty(this.tsitecheck.getPartySig().getSignature())){
	           try {
	     	   String filename=PdfUtil.base64StringToImage(this.tsitecheck.getPartySig().getSignature());
	         	   partySig=Image.getInstance(filename);
	         	   FileUtils.deleteFile(filename);
	            } catch (MalformedURLException e) {
	         	   logger.error("partySig error",e);
	            } catch (IOException e) {
	         	   logger.error("partySig error",e);
	            }       	   
           }

	           widths = new float[]{8, 42,8,42};
	           table = new PdfPTable(widths.length);
	           table.setWidths(widths);
	           table.setWidthPercentage(tableWidth);
	          
	          Date date=tsitecheck.getCheckDate();
	    	  PdfPTable	tableSub = new PdfPTable(3);
	      
	    	  Phrase  phrase=new Phrase("\n\n\n\n    勘\n    查\n    人",fontMap.get("textFont"));
	    	  table.addCell(phrase);
	    	  tableSub=getSignatureTable(checkerSig,date);
	    	  table.addCell(tableSub); 
      
	    	  phrase=new Phrase("\n\n\n\n    当 \n    事\n    人",fontMap.get("textFont"));
	    	  table.addCell(phrase);
	    	  tableSub=getSignatureTable(partySig,date);
	    	  table.addCell(tableSub);
	            
	       document.add(table);
        } catch (Exception e) {
            logger.error("生成PDF数据异常", e);
        } finally {
            document.close();
        }
    }

	
    public PdfPTable getSignatureTable(Image sig,Date date) throws DocumentException{
    	PdfPTable tableSub = new PdfPTable(3);
        tableSub.setWidths(new float[]{0.25f, 0.35f, 0.4f});
   	  
        Phrase phrase = new Phrase("签名：", PdfUtil.getFont12(Font.NORMAL));
        PdfPCell cell = new PdfPCell(phrase);
		cell.setMinimumHeight(120);
    	cell.setBorderWidth(0);
    	cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平
    	cell.setVerticalAlignment(Element.ALIGN_TOP); //垂直 	
    	tableSub.addCell(cell);  
    	
    	cell = new PdfPCell();
    	cell.setBorderWidth(0);
    	cell.addElement(sig);
    	cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平
    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直    	
    	tableSub.addCell(cell); 
    	
    	phrase = new Phrase("");
    	if(sig != null){
    		phrase = new Phrase(DateUtil.formatDate(date, "yyyy年MM月dd日"), PdfUtil.getFont10(Font.NORMAL));
    	}
    	
		cell = new PdfPCell(phrase);
    	cell.setBorderWidth(0);
    	cell.setHorizontalAlignment(Element.ALIGN_RIGHT); //水平
    	cell.setVerticalAlignment(Element.ALIGN_BOTTOM); //垂直
    	tableSub.addCell(cell);
    	return tableSub;
    
    
    }
    
    

	/**
     * 输出到文件
     *
     * @throws DocumentException
     * @throws IOException
     */
    public TsitecheckExport writeFile(String name) throws DocumentException, IOException {
        FileOutputStream os = new FileOutputStream(name);
        this.generate(os);
        return this;
    }

    /**
     * 输出到客户端
     * @param response
     * @param fileName 输出文件名
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public TsitecheckExport write(HttpServletResponse response, String fileName) throws DocumentException, IOException {
        response.reset();
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + Encodes.urlEncode(fileName));
        this.generate(response.getOutputStream());
        return this;
    }
    
}