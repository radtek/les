<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件结案管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		    $('#btnSubmit').click(function() {
				$("#inputForm").attr("action","${ctx}/tcase/caseSettle/save");
				$("#inputForm").submit();		    	
		    });			
			
		    $('#btnSettle').click(function() {
				$("#inputForm").attr("action","${ctx}/tcase/caseSettle/exportPDF");
				$("#inputForm").submit();		    	
		    });	
		    
		    
		});
	</script>
</head>
<body>
	<h3>结案书</h3>

    <les:caseSummary caseAttr="${caseAct.tcase}"></les:caseSummary>	
    
    <les:caseTab tab="settle" caseActAttr="${caseAct}"></les:caseTab> 
    
	<les:caseProcessTag caseSummaryBehaviour="hide"></les:caseProcessTag>    
    
    <form:form class="form-horizontal">
	   	<div class="control-group">
			<label class="control-label">案件事实经过及证据<BR>(供参照)：</label>
			<div class="controls">
			<textarea style="width:800px;height:300px;" readonly="readonly">${caseHandle.fact}
			</textarea>
			</div>
		</div>
	</form:form>     
    
	<form:form id="inputForm" modelAttribute="caseSettle" action="${ctx}/tcase/caseSettle/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="caseId"/>
		<form:hidden path="paramUri" value="${caseAct.paramUri}"/>
		<sys:message content="${message}"/>	
		<div class="control-group">
			<label class="control-label">案件处理情况：</label>
			<div class="controls">
				<form:textarea path="handleSummary" htmlEscape="false"  style="width:800px;height:600px;" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">案件执行情况：</label>
			<div class="controls">
				<form:textarea path="executeSummary" htmlEscape="false"  style="width:800px;height:600px;" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">案件复议情况：</label>
			<div class="controls">
				<form:textarea path="reviewSummary" htmlEscape="false"  style="width:800px;height:600px;" class="required"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="case:tcase:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
			<input id="btnSettle" class="btn btn-primary" type="button" value="案件结案审批表"/>
			</shiro:hasPermission>
		</div>
	</form:form>
</body>
</html>