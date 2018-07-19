<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>	


<%@ attribute name="handleAction" type="java.lang.String" required="true" description="controller"%>

    <script type="text/javascript">
	    
	    function startProcess(){
			$("#approveForm").submit();
	    	
	    } 

	</script>

<div class="borderedBox" style="width:80%; text-align:center;">
	<form:form id="approveForm" modelAttribute="tcase" action="${handleAction}" method="post" class="form-horizontal">
	    
	    <form:hidden path="id" value="${tcase.id}"/>
	    <form:hidden path="caseProcess.id" value="${tcase.caseProcess.id}"/>
	    <form:hidden path="caseProcess.caseStage" value="${tcase.caseProcess.caseStage}"/>
	    
		<div class="control-group">
			<label class="control-label">下一环节处理人：</label>
			<div class="controls">
                <c:choose>
                   <c:when test="${tcase.caseProcess.multiple}">
                     <form:checkboxes items="${tcase.caseProcess.availableHandlers}" path="caseProcess.caseHandler" itemLabel="name" itemValue="loginName"/>
                   </c:when>
                   <c:otherwise>
                     <form:radiobuttons items="${tcase.caseProcess.availableHandlers}" path="caseProcess.caseHandler" itemLabel="name" itemValue="loginName"/>
                   </c:otherwise>
                </c:choose>
			<input id="btnStart" class="btn btn-primary" type="button" value="启动事件" onclick="startProcess()"/>&nbsp;&nbsp;		
			</div>
		</div>		
	</form:form>	
</div>


