package org.wxjs.les.modules.tcase.entity;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.xfire.util.Base64;
import org.wxjs.les.common.utils.XmlHelper;

@XmlRootElement(name = "row")
public class PunishInfo4Xml{
	
	@XmlElement(name="ajNo") 
	private String ajNo;
	
	@XmlElement(name="prjNum") 
	private String prjNum;
	
	@XmlElement(name="cflx") 
	private String cflx;

	@XmlElement(name="wfwgxm") 
	private String wfwgxm;
	
	@XmlElement(name="stlx") 
	private String stlx;
	
	@XmlElement(name="wfwgdwry") 
	private String wfwgdwry;
	
	@XmlElement(name="zzjgdmSfzh") 
	private String zzjgdmSfzh;
	
	@XmlElement(name="wfxw") 
	private String wfxw;
	
	@XmlElement(name="lasj") 
	private String lasj;
	
	@XmlElement(name="jasj") 
	private String jasj;
	
	@XmlElement(name="updateFlag") 
	private String updateFlag;
	
	@XmlElement(name="source") 
	private String source;
	
	public PunishInfo4Xml(){
		
	}
	
	private String encode(String src){
		String rst = "";
		if(!StringUtils.isBlank(src)){
			try {
				rst = Base64.encode(src.getBytes("GBK"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		
		return rst;
	}
	
	public String toEncodedXml(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("<dataTable>");
		buffer.append("<row>");
		buffer.append("<ajNo>").append(this.encode(this.ajNo)).append("</ajNo>");
		buffer.append("<prjNum>").append(this.encode(this.prjNum)).append("</prjNum>");
		buffer.append("<cflx>").append(this.encode(this.cflx)).append("</cflx>");
		buffer.append("<wfwgxm>").append(this.encode(this.wfwgxm)).append("</wfwgxm>");
		buffer.append("<stlx>").append(this.encode(this.stlx)).append("</stlx>");
		buffer.append("<wfwgdwry>").append(this.encode(this.wfwgdwry)).append("</wfwgdwry>");
		buffer.append("<zzjgdmSfzh>").append(this.encode(this.zzjgdmSfzh)).append("</zzjgdmSfzh>");
		buffer.append("<wfxw>").append(this.encode(this.wfxw)).append("</wfxw>");
		buffer.append("<lasj>").append(this.encode(this.lasj)).append("</lasj>");
		buffer.append("<jasj>").append(this.encode(this.jasj)).append("</jasj>");
		buffer.append("<source>").append(this.encode(this.source)).append("</source>");
		buffer.append("<updateFlag>").append(this.encode(this.updateFlag)).append("</updateFlag>");
		buffer.append("</row>");		
		buffer.append("</dataTable>");
		return buffer.toString();
	}

	public void setAjNo(String ajNo) {
		this.ajNo = ajNo;
	}

	public void setPrjNum(String prjNum) {
		this.prjNum = prjNum;
	}

	public void setCflx(String cflx) {
		this.cflx = cflx;
	}

	public void setWfwgxm(String wfwgxm) {
		this.wfwgxm = wfwgxm;
	}

	public void setStlx(String stlx) {
		this.stlx = stlx;
	}

	public void setWfwgdwry(String wfwgdwry) {
		this.wfwgdwry = wfwgdwry;
	}

	public void setZzjgdmSfzh(String zzjgdmSfzh) {
		this.zzjgdmSfzh = zzjgdmSfzh;
	}

	public void setWfxw(String wfxw) {
		this.wfxw = wfxw;
	}

	public void setLasj(String lasj) {
		this.lasj = lasj;
	}

	public void setJasj(String jasj) {
		this.jasj = jasj;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
