<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>	

<%@ attribute name="punishLibAttr" type="org.wxjs.les.modules.tcase.entity.CaseHandlePunishLib" required="true" description=""%>

<%@ attribute name="paramUri" type="java.lang.String" required="true" description=""%>

    <script type="text/javascript">
    
    $(document).ready(function() {
    	
    	$("#btnReturn").click(function(){
    		if(!check()){
    			return;
    		}
    		top.$.jBox.confirm("确认要退回吗？","系统提示",function(v,h,f){
    			if(v=="ok"){
    				$("#approve").val("return");
    				$("#approveForm").submit();
    			}
    		},{buttonsFocus:1});
    		top.$('.jbox-body .jbox-icon').css('top','55px');
    	});
    	


	</script>

<form:form class="form-horizontal">
	<fieldset>
	    <font bold="true">编号：${punishLibAttr.punishLib.seq}</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="${ctx}/tcase/caseHandlePunishLib/delete?id=${punishLibAttr.id}&${paramUri}"><font color="red">删除</font></a>
		<table class="table-form" style="width:90%">
			<tr>
				<td class="tit" width="20%">行为名称</td><td width="80%" colspan="3">${punishLibAttr.punishLib.behavior}</td>
			</tr>	
			<tr>
				<td class="tit">法律依据</td><td colspan="3">${punishLibAttr.punishLib.lawBasis}</td>
			</tr>
			<tr>
				<td class="tit">处罚种类</td><td colspan="3">${punishLibAttr.punishLib.punishType}</td>
			</tr>
			<tr>
				<td class="tit" colspan="4">自由裁量基准</td>
			</tr>			
			<c:set var="listSize" value="${fn:length(punishLibAttr.punishLib.punishLibRangeList)}" />
			<c:forEach items="${punishLibAttr.punishLib.punishLibRangeList}" var="entity" varStatus="status">
			<tr>
			   <c:if test="${status.index eq 0}">
			    <td class="tit" width="20%" rowspan="${listSize}">情形描述</td><td width="30%">${entity.situation}</td>
			    <td class="tit" width="20%" rowspan="${listSize}">裁量幅度</td><td width="30%">${entity.punishRange}</td>
			   </c:if>
			   <c:if test="${status.index gt 0}">
			    <td>${entity.situation}</td>
			    <td>${entity.punishRange}</td>
			   </c:if>	
			</tr>		
			</c:forEach>											
		</table>
	</fieldset>		
</form:form>


