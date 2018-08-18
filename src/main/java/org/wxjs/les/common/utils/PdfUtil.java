package org.wxjs.les.common.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.wxjs.les.common.config.Global;

public class PdfUtil {
	
    private static BaseFont bfChinese;
    
    public static final float RealWidthChinese = 2f;
    public static final float RealWidthNumber = 0.92f;
    public static final float RealWidthNarrow = 0.5f;
    public static final float RealWidthLowerCase = 0.869f;
    public static final float RealWidthUpperCase = 1.28f;
    
    public static final String ChineseChar = "[^\\x00-\\xff]";  //双字节字符
    public static final String NarrowChar = "^[JjlIi]";  //narrow
    public static final String NumberChar = "^[0-9]";  //number
    public static final String LowerCaseChar = "^[a-z]";  //lowerCase
    public static final String UpperCaseChar = "^[A-Z]";  //upperCase
    
    public static final float SignatureImageWidth = 60f;
    
    static{
    	try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * 二号
     * @param bold
     * @return
     */
    public static Font getTitle22Font(boolean bold){
    	int fontType = Font.NORMAL;
    	if(bold){
    		fontType = Font.BOLD;
    	}
    	return new Font(bfChinese, 22, fontType);
    }    
    
    public static Font getTitle1Font(boolean bold){
    	int fontType = Font.NORMAL;
    	if(bold){
    		fontType = Font.BOLD;
    	}
    	return new Font(bfChinese, 19, fontType);
    }
    
    public static Font getTitle2Font(boolean bold){
    	int fontType = Font.NORMAL;
    	if(bold){
    		fontType = Font.BOLD;
    	}
    	return new Font(bfChinese, 16, fontType);
    }
    
    /**
     * 小四
     * @param bold
     * @return
     */
    public static Font getFont12(int fontType){
    	return new Font(bfChinese, 12, fontType);
    }
    
    public static Font getFont10(int fontType){
    	return new Font(bfChinese, 10, fontType);
    }
    
    public static Font getFont8(int fontType){
    	return new Font(bfChinese, 8, fontType);
    }
    
    public static Font getTitle3Font(boolean bold){
    	int fontType = Font.NORMAL;
    	if(bold){
    		fontType = Font.BOLD;
    	}
    	return new Font(bfChinese, 14, fontType);
    }
    
    public static Font getTextFont(boolean bold){
    	int fontType = Font.NORMAL;
    	if(bold){
    		fontType = Font.BOLD;
    	}
    	return new Font(bfChinese, 10, fontType);
    }
    
    public static PdfPTable generateTable(String[] headers, Font headerFont, List<String[]> items, Font rowFont, float[] widths, int tableWidth) throws DocumentException{
    	return generateTable(headers, headerFont, items, rowFont, widths, tableWidth, false, 1);
    }
    
    public static PdfPTable generateTable(String[] headers, Font headerFont, List<String[]> items, Font rowFont, float[] widths, int tableWidth, boolean headerCenter, float borderWidth) throws DocumentException{
    	int columns = widths.length;
    	//int rows = items.size()+1;
    	
        PdfPTable table = new PdfPTable(columns);
        table.setWidths(widths);
        table.setWidthPercentage(tableWidth);
        
        Phrase phrase;
        PdfPCell cell;
        
        if(headers!=null){
            //write header
            for(String header: headers){
            	phrase = new Phrase(header, headerFont);
            	cell = new PdfPCell(phrase);
            	cell.setBorderWidth(borderWidth);
            	if(headerCenter){
            		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	}else{
            		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	}
            	
            	//cell.setBackgroundColor(Color.lightGray);
            	table.addCell(cell);
            }           	
        }
	
        if(items!=null){
        	//write items
        	for(String[] strs: items){
        		for(String str:strs){
                	phrase = new Phrase(str, rowFont);
                	cell = new PdfPCell(phrase);
                	cell.setBorderWidth(borderWidth);
                	if(isMoneyArea(str)){
                		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                	}else{
                		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                	}
                	table.addCell(cell);
        		}
        	}        	
        }
    	
    	return table;
    }
    
    public static PdfPTable generateTableRowCenter(String[] strs, Font rowFont, float[] widths, int tableWidth, int textAlign, float borderWidth, float minimumHeight) throws DocumentException{
    	
    	int columns = widths.length;
    	//int rows = items.size()+1;
    	
        PdfPTable table = new PdfPTable(columns);
        table.setWidths(widths);
        table.setWidthPercentage(tableWidth);
        
        Phrase phrase;
        PdfPCell cell;
	
        if(strs!=null){
        	//write items

        	int index = 0;
    		for(String str:strs){
            	phrase = new Phrase(str, rowFont);
            	cell = new PdfPCell(phrase);
            	cell.setBorderWidth(borderWidth);
            	cell.setHorizontalAlignment(textAlign);
            	
            	if(index%2==0){
                	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中       		
            	}else{
                	cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
                	                   		
            	}
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中 
            	
            	
            	if(minimumHeight>0){
            		cell.setMinimumHeight(minimumHeight);
            	}

            	table.addCell(cell);
            	index ++;
    		}
       	
        }
    	
    	return table;
    } 
    
    public static PdfPTable generateTableRow(String[] strs, Font rowFont, float[] widths, int tableWidth, int textAlign, float borderWidth, float minimumHeight) throws DocumentException{
    	int columns = widths.length;
    	//int rows = items.size()+1;
    	
        PdfPTable table = new PdfPTable(columns);
        table.setWidths(widths);
        table.setWidthPercentage(tableWidth);
        
        Phrase phrase;
        PdfPCell cell;
	
        if(strs!=null){
        	//write items

        	int index = 0;
    		for(String str:strs){
            	phrase = new Phrase(str, rowFont);
            	cell = new PdfPCell(phrase);
            	cell.setBorderWidth(borderWidth);
            	cell.setHorizontalAlignment(textAlign);
            	
            	if(index%2==0){
                	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中       		
            	}else{
                	cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
                	                   		
            	}
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
            	
            	if(minimumHeight>0){
            		cell.setMinimumHeight(minimumHeight);
            	}

            	table.addCell(cell);
            	index ++;
    		}
       	
        }
    	
    	return table;
    } 
    
    public static PdfPTable generateTableRow4LongText(String[] strs, Font rowFont, float[] widths, int tableWidth, int textAlign, float borderWidth, float minimumHeight) throws DocumentException{
    	int columns = widths.length;
    	//int rows = items.size()+1;
    	
        PdfPTable table = new PdfPTable(columns);
        table.setWidths(widths);
        table.setWidthPercentage(tableWidth);
        
        Phrase phrase;
        PdfPCell cell;
	
        if(strs!=null){
        	//write items

        	int index = 0;
    		for(String str:strs){
            	phrase = new Phrase(str, rowFont);
            	cell = new PdfPCell(phrase);
            	cell.setBorderWidth(borderWidth);
            	cell.setHorizontalAlignment(textAlign);
            	
            	if(index%2==0){
                	cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中       	
                	cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
            	}else{
                	cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
                	                   		
            	}
            	if(minimumHeight>0){
            		cell.setMinimumHeight(minimumHeight);
            	}

            	table.addCell(cell);
            	index ++;
    		}
       	
        }
    	
    	return table;
    }
    
    public static PdfPTable generateTableRow(String[] strs, Font[] rowFonts, float[] widths, int tableWidth, int textAlign, float borderWidth, float minimumHeight) throws DocumentException{
    	int columns = widths.length;
    	//int rows = items.size()+1;
    	
        PdfPTable table = new PdfPTable(columns);
        table.setWidths(widths);
        table.setWidthPercentage(tableWidth);
        
        Phrase phrase;
        PdfPCell cell;
	
        if(strs!=null){
        	//write items

        	int index = 0;
    		for(String str:strs){
            	phrase = new Phrase(str, rowFonts[index]);
            	cell = new PdfPCell(phrase);
            	cell.setBorderWidth(borderWidth);
            	cell.setHorizontalAlignment(textAlign);
            	
            	if(minimumHeight>0){
            		cell.setMinimumHeight(minimumHeight);
            	}

            	table.addCell(cell);
            	index ++;
    		}
       	
        }
    	
    	return table;
    } 
    
	public static PdfPCell getContentCell(String content, int align, float borderWidth, Font font){
		return getContentCell(content, align, borderWidth, font, 1, 1, 0);
	}
	
	public static PdfPCell getContentCell(String content, int align, float borderWidth, Font font, float minimumHeight){ 
		return getContentCell(content, align, borderWidth, font, 1, 1, minimumHeight);
	}
	
	public static PdfPCell getContentCell(String content, int align, float borderWidth, Font font, int rowSpan, int colSpan){
		return getContentCell(content, align, borderWidth, font, 1, 1, 0);
	}
	
	public static PdfPCell getContentCell(String content, int align, float borderWidth, Font font, int rowSpan, int colSpan, float minimumHeight){
		Phrase phrase = new Phrase(content, font);
		PdfPCell cell = new PdfPCell(phrase);
    	cell.setBorderWidth(borderWidth);
    	cell.setHorizontalAlignment(align);
    	
    	if(minimumHeight>0){
    		cell.setMinimumHeight(minimumHeight);
    	}
    	
    	if(rowSpan>1){
    		cell.setRowspan(rowSpan);
    	}
    	if(colSpan>1){
    		cell.setColspan(colSpan);
    	}
    	return cell;
	}
    
    private static boolean isMoneyArea(String str){
    	boolean flag = false;
    	if(StringUtils.isBlank(str)){
    		return flag;
    	}
    	int dotIndex = str.indexOf(".");
    	if(dotIndex>0){
    		String partBehindDot = str.substring(dotIndex+1);
    		if(partBehindDot.length() == 2){
    			flag = true;
    		}
    	}
    	return flag;
    }
    
    public static PdfPTable generateTable4Padding() throws DocumentException{
        PdfPTable table = new PdfPTable(1);
        
        Phrase phrase;
        PdfPCell cell;
        
    	phrase = new Phrase(" ");
    	cell = new PdfPCell(phrase);
    	cell.setBorderWidth(0);
    	table.addCell(cell); 	

    	return table;
    }
    
    public static String toPlain(String src){
		if(!StringUtils.isBlank(src)){
			return src.replaceAll("<br>", "\n").replace("<BR>", "\n");
		}else{
			return src;
		}
    }
    
    public static Image getSignatureImage(String signature){
        Image sig = null;
        try {
        	String filename = base64StringToImage(signature);
        	sig = Image.getInstance(filename);
        	//sig.setBorderWidth(0);
        	//sig.setBorderWidth(1);
        	sig.scaleAbsoluteWidth(SignatureImageWidth);
        	sig.setAlignment(Image.ALIGN_CENTER);
			FileUtils.deleteFile(filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadElementException e) {
			e.printStackTrace();
		}
        return sig;
    }
    
    private static String base64StringToImage(String base64String) {
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
    
    /**
     * 
     * @param str
     * @param lineLen  单行长度
     * @return
     */
	public static String transferVertical(String str, int lineLen){
		StringBuffer buffer = new StringBuffer();
		int startIndex = 0;
		int endIndex = 0;
		for(int i=0;i<str.length();i=i+lineLen){
			startIndex = i;
			endIndex = i+lineLen;
			if(endIndex>str.length()){
				endIndex = str.length();
			}
			buffer.append("\n").append(str.substring(startIndex, endIndex));
		}
		
		return buffer.substring(1);
	}
	
	public static String transferVertical(String str){
		return transferVertical(str, 1);
	}
	
	public static void main(String[] args){
		String str = "我是中国人，你是日本人";
		
		System.out.println(transferVertical(str));
		System.out.println(transferVertical(str, 2));
		System.out.println(transferVertical(str, 3));
		System.out.println(transferVertical(str, 4));
		
	}

}

