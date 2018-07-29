package org.wxjs.les.common.utils;

import java.awt.Color;
import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class PdfUtil {
	
    private static BaseFont bfChinese;
    
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
    
    public static PdfPTable generateTable(String[] headers, Font headerFont, List<String[]> items, Font rowFont, float[] widths, int tableWidth, boolean headerCenter, float bordWidth) throws DocumentException{
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
            	cell.setBorderWidth(bordWidth);
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
                	cell.setBorderWidth(bordWidth);
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
    
    public static PdfPTable generateTableRow(String[] strs, Font rowFont, float[] widths, int tableWidth, int textAlign, float bordWidth, float minimumHeight) throws DocumentException{
    	int columns = widths.length;
    	//int rows = items.size()+1;
    	
        PdfPTable table = new PdfPTable(columns);
        table.setWidths(widths);
        table.setWidthPercentage(tableWidth);
        
        Phrase phrase;
        PdfPCell cell;
	
        if(strs!=null){
        	//write items

    		for(String str:strs){
            	phrase = new Phrase(str, rowFont);
            	cell = new PdfPCell(phrase);
            	cell.setBorderWidth(bordWidth);
            	cell.setHorizontalAlignment(textAlign);
            	
            	if(minimumHeight>0){
            		cell.setMinimumHeight(minimumHeight);
            	}

            	table.addCell(cell);
    		}
       	
        }
    	
    	return table;
    } 
    
    public static PdfPTable generateTableRow(String[] strs, Font[] rowFonts, float[] widths, int tableWidth, int textAlign, float bordWidth, float minimumHeight) throws DocumentException{
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
            	cell.setBorderWidth(bordWidth);
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
		return getContentCell(content, align, borderWidth, font, 1, 1);
	}
	
	public static PdfPCell getContentCell(String content, int align, float borderWidth, Font font, int rowSpan, int colSpan){
		Phrase phrase = new Phrase(content, font);
		PdfPCell cell = new PdfPCell(phrase);
    	cell.setBorderWidth(borderWidth);
    	cell.setHorizontalAlignment(align);
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

}

