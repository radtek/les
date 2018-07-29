<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>处罚基准库管理</title>
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
		<li><a href="${ctx}/base/punishLib/">处罚基准库列表</a></li>
		<li><a href="${ctx}/base/punishLib/form?id=${punishLib.id}">处罚基准库<shiro:hasPermission name="base:punishLib:edit">${not empty punishLib.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="base:punishLib:edit">查看</shiro:lacksPermission></a></li>
		<li class="active"><a href="${ctx}/base/punishLib/importTab">导入数据</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="punishLib" action="${ctx}/base/punishLib/importLib" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">行政处罚自由裁量基准文件：</label>
			<div class="controls">
				<form:hidden id="filepath" path="filepath" htmlEscape="false" maxlength="256" class="input-xlarge"/>
				<sys:ckfinder input="filepath" type="words" uploadPath="/punishLib" selectMultiple="false"/>				
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="base:punishLib:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="导 入"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>