<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件审理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
	    	$("#btnSavePublishLib").click(function(){
	    		var libId = $("#punishLibId").val();
	    		if(libId == ""){
	    			alert("请先指定项目！");
	    		}else{
	    			$("#punishLibForm").submit();
	    		}
				
	    	});
	    	
		    $('#btnSaveReport').click(function() {
				$("#inputReportForm").attr("action","${ctx}/tcase/caseHandle/saveReport");
				$("#inputReportForm").submit();		    	
		    });			
			
		    $('#btnReport').click(function() {
				$("#inputReportForm").attr("action","${ctx}/tcase/caseHandle/exportReportPDF");
				$("#inputReportForm").submit();		    	
		    });	
		    
		    $('#btnApprove').click(function() {
				$("#inputReportForm").attr("action","${ctx}/tcase/caseHandle/exportApprovePDF");
				$("#inputReportForm").submit();		    	
		    });	
		    
		    $('#btnSaveUploadInfo').click(function() {
		    	var ids = "";
		    	$("input[name^='punishLibRANGE']:checked").each(function(){
		    	    ids = ids + this.value + ",";
		    	});
		    	$("#lib_range_checked_ids").val(ids);
				$("#inputUploadInfoForm").attr("action","${ctx}/tcase/caseHandle/saveUploadInfo");
				$("#inputUploadInfoForm").submit();		    	
		    });		    
	    
		});
	</script>
</head>
<body>
    <h3>案件审理</h3>

    <les:caseSummary caseAttr="${caseAct.tcase}"></les:caseSummary>	
    
    <les:caseTab tab="handle" caseActAttr="${caseAct}"></les:caseTab> 
    
    <sys:message content="${message}"/>	
    <!--  
    <form:form class="form-horizontal">
	   	<div class="control-group">
			<label class="control-label">案情摘要<BR>(前一环节)：</label>
			<div class="controls">
			<textarea style="width:800px;height:300px;" readonly="readonly">${previousCaseSummary}
			</textarea>
			</div>
		</div>
	</form:form> 
	-->
	<les:caseProcessTag caseSummaryBehaviour="readonly"></les:caseProcessTag>  
	
	<c:set var="editable" value="${caseAct.tcase.caseProcess.editable}"></c:set>
	    
	<form:form id="punishLibForm" modelAttribute="caseHandlePunishLib" action="${ctx}/tcase/caseHandlePunishLib/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="caseId"/>
		<form:hidden path="paramUri" value="${caseAct.paramUri}"/>
		
		<h5>案件上报资料编辑 </h5>	
		
        
		<div class="control-group">
			<label class="control-label">自由裁量权：</label>
			<div class="controls">
			<sys:treeselectExtend id="punishLib" name="punishLib.id" value="" labelName="punishLib.behavior" labelValue="" 
				title="自由裁量权" url="/base/punishLib/treeData" cssClass="input-xxlarge" allowClear="true" notAllowSelectParent="true" dialogWidth="800" dialogHeight="500"/>
			<c:if test="${editable }">
			<shiro:hasPermission name="case:tcase:edit"><input id="btnSavePublishLib" class="btn btn-primary" type="button" value="添 加"/>&nbsp;
			</shiro:hasPermission>
			</c:if>
			</div>
		</div>			
			
		<BR>
		<c:forEach items="${libList}" var="libEntity" varStatus="status">
		   
		   <les:punishLibTag punishLibAttr="${libEntity}" paramUri="${caseAct.paramUri}" editable="${editable }"></les:punishLibTag>
		
		</c:forEach>	
	</form:form>
	<form:form id="inputUploadInfoForm" modelAttribute="caseHandle" action="${ctx}/tcase/caseHandle/saveUploadInfo" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="caseId"/>
		<form:hidden path="paramUri" value="${caseAct.paramUri}"/>
		<input type="hidden" id="lib_range_checked_ids" name="rangeIds" value="${caseHandle.rangeIds}">

		<div class="form-actions">
		<c:if test="${editable }">
			<shiro:hasPermission name="case:tcase:edit"><input id="btnSaveUploadInfo" class="btn btn-primary" type="button" value="保 存上报资料"/>&nbsp;
			</shiro:hasPermission>
		</c:if>
		</div>		
	</form:form>	
	
	<form:form id="inputReportForm" modelAttribute="caseHandle" action="${ctx}/tcase/caseHandle/saveReport" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="caseId"/>
		<form:hidden path="paramUri" value="${caseAct.paramUri}"/>		
		<h5>案件调查报告  </h5>	
		<div class="control-group">
			<label class="control-label">案件调查报告内容：</label>
			<div class="controls">
				<form:textarea path="investReport" htmlEscape="false"  style="width:800px;height:300px;" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">案件事实经过及证据：</label>
			<div class="controls">
				<form:textarea path="fact" htmlEscape="false"  style="width:800px;height:300px;" class="required"/>
			</div>
		</div>
		<!--  
		<div class="control-group">
			<label class="control-label">调查人：</label>
			<div class="controls">
			    <sys:treeselect id="investigator" name="investigator.loginName" value="${caseHandle.investigator.loginName}" labelName="investigator.name" labelValue="${caseHandle.investigator.name}" 
				title="记录人员" url="/sys/user/treeDataWithLoginName?officeId=" checked="true" cssClass="input-xxlarge" allowClear="true" notAllowSelectParent="true"/>				
			</div>
		</div>
		-->
		<div class="form-actions">
			<shiro:hasPermission name="case:tcase:edit">
			<c:if test="${caseAct.tcase.caseProcess.editable}">
			<input id="btnSaveReport" class="btn btn-primary" type="button" value="保存调查报告"/>&nbsp;
			</c:if>
			<input id="btnReport" class="btn btn-primary" type="button" value="案件调查报告"/>&nbsp;
			<input id="btnApprove" class="btn btn-primary" type="button" value="案件处理审批表"/>&nbsp;
			</shiro:hasPermission>
		</div>
	</form:form>
	<les:toApprove caseActAttr="${caseAct}" caseStageAttr="30"></les:toApprove>
</body>
</html>