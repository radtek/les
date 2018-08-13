<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件资料管理</title>
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
	<h3>资料附件</h3>
	<les:caseSummary caseAttr="${caseAct.tcase}"></les:caseSummary>
	
	<form:form id="inputForm" modelAttribute="caseAttach" action="${ctx}/case/caseAttach/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="caseId" value="${caseAct.tcase.id}"/>
		<form:hidden path="paramUri" value="${caseAct.paramUri}"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">资料类型：</label>
			<div class="controls">
				<form:input path="attachType" htmlEscape="false" maxlength="64" class="input-xlarge required"/>	
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">流程阶段：</label>
			<div class="controls">
				<form:select path="flowNode" class="input-xlarge required">
			       <form:options items="${fns:getDictList('case_stage')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			    </form:select>			
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">保存路径：</label>
			<div class="controls">
				<form:hidden id="filepath" path="filepath" htmlEscape="false" maxlength="256" class="input-xlarge"/>
				<sys:ckfinder input="filepath" type="files" uploadPath="/${caseAct.tcase.id}" selectMultiple="false"/>				
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="case:tcase:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>