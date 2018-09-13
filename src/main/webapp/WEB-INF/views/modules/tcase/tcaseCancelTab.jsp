<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件撤销管理</title>
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
				$("#inputForm").attr("action","${ctx}/tcase/caseCancel/save");
				$("#inputForm").submit();		    	
		    });			
			
		    $('#btnExport').click(function() {
				$("#inputForm").attr("action","${ctx}/tcase/caseCancel/exportPDF");
				$("#inputForm").submit();		    	
		    });
		});
	</script>
</head>
<body>
	<h3>案件撤销</h3>

    <les:caseSummary caseAttr="${caseAct.tcase}"></les:caseSummary>	
    
    <les:caseTab tab="cancel" caseActAttr="${caseAct}"></les:caseTab> 
    
    <les:caseProcessTag4Cancel></les:caseProcessTag4Cancel>
    
	<form:form id="inputForm" modelAttribute="caseCancel" action="${ctx}/tcase/caseCancel/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="caseId"/>
		<form:hidden path="paramUri" value="${caseAct.paramUri}"/>
		<sys:message content="${message}"/>		
		<h4>案件撤销</h4>
		<div class="control-group">
			<label class="control-label">撤销理由：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" style="width:800px;height:300px;" class="required"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="case:tcase:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
			<input id="btnExport" class="btn btn-primary" type="button" value="调查报告"/>&nbsp;
			</shiro:hasPermission>
		</div>
	</form:form>
	<les:toApprove caseActAttr="${caseAct}" caseStageAttr="120"></les:toApprove>
</body>
</html>