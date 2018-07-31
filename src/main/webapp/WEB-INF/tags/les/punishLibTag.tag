<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>	

<%@ attribute name="punishLibAttr" type="org.wxjs.les.modules.tcase.entity.CaseHandlePunishLib" required="true" description=""%>

<%@ attribute name="paramUri" type="java.lang.String" required="true" description=""%>

    <script type="text/javascript">
    
    $(document).ready(function() {
    	
	    $("input[type=radio][name='punishLib${punishLibAttr.punishLib.id}']").click(function() {
	    	saveRange("${punishLibAttr.id}", this.value)
	    	
	    });
	    
	    function saveRange(itemId, rangeId){
	    	
	    	var json = {};
	    	json["id"] = itemId;
	    	json["punishLibRange.id"] = rangeId;
	    	
	    	$.post("${ctx}/tcase/caseHandlePunishLib/updateRange",
	    		json,
	    		function(a){

	    	}); 	    	
	    }
	    
    
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
			    <td class="tit" width="20%" rowspan="${listSize}">情形描述</td>
			   </c:if>
			    <td width="30%">
			    <input type="radio" id="range${entity.id}" name="punishLib${punishLibAttr.punishLib.id}" value="${entity.id}" <c:if test="${entity.id eq punishLibAttr.punishLibRange.id}">checked</c:if> style="width:20px; height:20px;"/>${entity.situation}
			    </td>
			    <c:if test="${status.index eq 0}">
			    <td class="tit" width="20%" rowspan="${listSize}">裁量幅度</td>
			    </c:if>
			    <td width="30%">${entity.punishRange}</td>	
			</tr>		
			</c:forEach>											
		</table>
	</fieldset>		
</form:form>


