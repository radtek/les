<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件审理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
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
		});
	</script>
</head>
<body>
    <h3>案件审理</h3>

    <les:caseSummary caseAttr="${caseAct.tcase}"></les:caseSummary>	
    
    <les:caseTab tab="handle" caseActAttr="${caseAct}"></les:caseTab> 
    
    <sys:message content="${message}"/>	
	<form:form id="inputUploadInfoForm" modelAttribute="caseHandle" action="${ctx}/tcase/caseHandle/saveUploadInfo" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="caseId"/>
		<form:hidden path="paramUri" value="${caseAct.paramUri}"/>
		
		<h5>案件上报资料编辑 </h5>	
		<div class="control-group">
			<label class="control-label">行政处罚编码：</label>
			<div class="controls">
				<form:textarea path="punishCode" htmlEscape="false"  style="width:800px;height:100px;" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法律依据：</label>
			<div class="controls">
				<form:textarea path="legalBasis" htmlEscape="false"  style="width:800px;height:100px;" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">依据内容：</label>
			<div class="controls">
				<form:textarea path="legalBasisContent" htmlEscape="false"  style="width:800px;height:100px;" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">自由裁量权：</label>
			<div class="controls">
				<form:textarea path="discretion" htmlEscape="false"  style="width:800px;height:100px;" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际罚款金额（元）：</label>
			<div class="controls">
				<form:input path="punishMoney" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="case:tcase:edit"><input id="btnSaveUploadInfo" class="btn btn-primary" type="submit" value="保 存上报资料"/>&nbsp;
			</shiro:hasPermission>
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
		<div class="control-group">
			<label class="control-label">调查人：</label>
			<div class="controls">
				<form:input path="investigator" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="case:tcase:edit"><input id="btnSaveReport" class="btn btn-primary" type="submit" value="保存调查报告"/>&nbsp;
			<input id="btnInvestReport" class="btn btn-primary" type="button" value="案件调查报告"/>&nbsp;
			<input id="btnApproveTable" class="btn btn-primary" type="button" value="案件处理审批表"/>&nbsp;
			</shiro:hasPermission>
		</div>
	</form:form>
</body>
</html>