<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文档类型管理</title>
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
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/base/caseDocType/">文档类型列表</a></li>
		<li class="active"><a href="${ctx}/base/caseDocType/form?id=${caseDocType.id}">文档类型<shiro:hasPermission name="base:caseDocType:edit">${not empty caseDocType.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="base:caseDocType:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="caseDocType" action="${ctx}/base/caseDocType/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">阶段：</label>
			<div class="controls">
			    <form:select path="caseStage" class="input-xlarge required">
			       <form:options items="${fns:getDictList('case_stage')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			       <form:option value="不限" label="不限"/>
			    </form:select>				
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文档类型：</label>
			<div class="controls">
				<form:input path="documentType" htmlEscape="false" maxlength="64" class="input-xlarge required"/>				
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否必须：</label>
			<div class="controls">
			    <form:select path="mandatory" class="input-xlarge required">
			       <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			    </form:select>					
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上传方式：</label>
			<div class="controls">
			    <form:select path="uploadMethod" class="input-xlarge required">
			       <form:options items="${fns:getDictList('upload_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			    </form:select>				
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="base:caseDocType:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>