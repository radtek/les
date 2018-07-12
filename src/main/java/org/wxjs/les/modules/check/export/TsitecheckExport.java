package org.wxjs.les.modules.check.export;


import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wxjs.les.common.utils.PdfUtil;
import org.wxjs.les.modules.check.entity.Tsitecheck;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class TsitecheckExport {
	
	private static Logger log = LoggerFactory.getLogger(TsitecheckExport.class);
	private final static int tableWidth = 90;
	private final static float borderWidth = 0.5f;
	final static float[] widths = new float[]{0.25f, 0.12f, 0.16f ,0.24f ,0.23f};
	final static float[] widthsGap = new float[]{0.25f, 0.12f, 0.16f ,0.47f};
	
	private Tsitecheck check;
	
	public TsitecheckExport(Tsitecheck check){
		this.check = check;
	}
	
	public void generate(OutputStream os) throws DocumentException {
		Document document = null;
		PdfWriter writer = null;
		PdfPTable table = null;
        Phrase phrase = null;
        Paragraph paragraph = null;
    	phrase = new Phrase("");
        PdfPCell cell_pending = new PdfPCell(phrase);
        cell_pending.setBorderWidth(0);
		
        document=new Document(PageSize.A4);
        writer = PdfWriter.getInstance(document, os);
        
        document.open();
       
        
	}
}
