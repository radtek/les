<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>	

<%@ attribute name="punishLibAttr" type="org.wxjs.les.modules.base.entity.PunishLib" required="true" description=""%>

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
	    <h5>编号：${punishLibAttr.caseSeq}</h5> <a href=""></a>
		<table class="table-form" style="width:90%">
			<tr>
				<td class="tit" width="20%">行为名称</td><td width="80%" colspan="3">${punishLibAttr.behavior}</td>
			</tr>	
			<tr>
				<td class="tit">法律依据</td><td colspan="3">${punishLibAttr.lawBasis}</td>
			</tr>
			<tr>
				<td class="tit">处罚种类</td><td colspan="3">${punishLibAttr.punishType}</td>
			</tr>
			<tr>
				<td class="tit" colspan="4">自由裁量基准</td>
			</tr>			
			<c:set var="listSize" value="${punishLibAttr.punishLibRangeList.size}" />
			<c:forEach items="${punishLibAttr.punishLibRangeList}" var="entity" varStatus="status">
			   <c:if test="${status.index eq 0}">
			    <td class="tit" width="20%" rowspan="${listSize}">情形描述</td><td width="30%">${entity.situation}</td>
			    <td class="tit" width="20%" rowspan="${listSize}">裁量幅度</td><td width="30%">${entity.punishRange}</td>
			   </c:if>
			   <c:if test="${status.index gt 0}">
			    <td>${entity.situation}</td>
			    <td>${entity.punishRange}</td>
			   </c:if>			
			</c:forEach>											
		</table>
	</fieldset>		
</form:form>


