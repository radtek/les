/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.export;

import java.io.OutputStream;

import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.PdfUtil;
import org.wxjs.les.modules.base.export.ExportBase;
import org.wxjs.les.modules.tcase.entity.CaseDecision;
import org.wxjs.les.modules.tcase.entity.CaseHandle;
import org.wxjs.les.modules.tcase.entity.Tcase;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
/**
 * 决定书送达回证
 * @author GLQ
 *
 */
public class CaseDecisionReachExport extends ExportBase<CaseDecisionReachExport> {

	private Tcase tcase;
	private CaseDecision caseDecision;
	private CaseHandle  caseHandle;
	
	public CaseDecisionReachExport(Tcase tcase, CaseDecision caseDecision,CaseHandle  caseHandle){
		this.tcase = tcase;
		this.caseDecision = caseDecision;
		this.caseHandle = caseHandle;
	}
	
	public void generate(OutputStream os) throws DocumentException{
		
		Document document = null;
		PdfWriter writer = null;
		
		PdfPTable table = null;
        Phrase phrase = null;
        
        Paragraph pragraph = null;
        
    	phrase = new Phrase("");
        PdfPCell cell_pending = new PdfPCell(phrase);
        cell_pending.setBorderWidth(0);
        
		try{
			document = new Document(PageSize.A4, 40, 40, 40, 40);
			
			writer = PdfWriter.getInstance(document, os);
            
            //页脚设置结束
			
			document.open();
            
            //add title
            
            pragraph = new Paragraph("送       达         回         证", fontTitle);
            pragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(pragraph);
            
            document.add(PdfUtil.generateTable4Padding());
            
            String[] items;
           
            //事由
            items = new String[]{"事由", this.caseHandle.getFact()};
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.2f,0.8f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 40, true);
            document.add(table);  
            //受送达人
            items = new String[]{"受送达人", this.caseDecision.getPartyName()};
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.2f,0.8f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 40, true);
            document.add(table);  
            //送达地点
            items = new String[]{"送达地点", this.caseDecision.getDestinationAddress()};
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.2f,0.8f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 40, true);
            document.add(table);  
            //送达文书 名称及文号
            String type=this.caseDecision.getDecisionType();
            String name="("+this.caseDecision.getYear()+")"+"第"+this.caseDecision.getSeq()+"号";
            if(type.equals("1")) {
            	name="锡建监罚字"+name;
            }else {
            	name="锡建监不罚字"+name;
            }
            items = new String[]{"送达文书\n名称及文号", Global.getConfig("defaultLaunchDept")+"行政处罚决定书\n"+name};
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.2f,0.8f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 40, true);
            document.add(table);
            //收件人 签名或印章
            items = new String[]{"收件人\n签名或印章", ""};
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.2f,0.8f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 40, true);
            document.add(table);
            //收到时间
            items = new String[]{"收件人\n签名或印章", "\n                                                     "
            		+ "                                            年          月          日          时           分"};
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.2f,0.8f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 40, true);
            document.add(table);
            //代收人证明代收理由
            items = new String[]{"代收人证明\n代收理由", ""};
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.2f,0.8f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 40, true);
            document.add(table);
            //备注
            items = new String[]{"备\n注", ""};
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.2f,0.8f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 120, true);
            document.add(table);
            //送达人
            items = new String[]{"送达人", ""};
            table = PdfUtil.generateTableRow(items, fontContent,  new float[]{0.2f,0.8f}, tableWidth, Element.ALIGN_LEFT, borderWidth, 40, true);
            document.add(table);
            
            String str="注: 1.送达文书交受给送达人本人,如本人不在可以交给其成年家属或所在单位负责人代收。\n" + 
            			"        2.如系代收，收件人应在收件人栏内签名或印章，并注明与受送达人的关系。\n" + 
            			"      3.受送达人或代收人拒绝接受或不签名、印章时，送达人可邀请其邻居或其他证人到场，说明情况，把文书留在其住处，在送达回证上记明拒绝的事由和送达日期，由送达人签名，即认为已经送达。";
            		   
            pragraph = new Paragraph(str, fontContent);
            pragraph.setAlignment(Paragraph.ALIGN_JUSTIFIED);
            document.add(pragraph); 
         
            
            
		}finally{
			if(document!=null){
				try{
					document.close();
				}catch(Exception ex){
				}
			}
		}
	}


}
