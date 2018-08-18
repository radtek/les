<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>常用批语管理</title>
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
		<li><a href="${ctx}/base/opinionTemplate/">常用批语列表</a></li>
		<li class="active"><a href="${ctx}/base/opinionTemplate/form?id=${opinionTemplate.id}">常用批语<shiro:hasPermission name="base:opinionTemplate:edit">${not empty opinionTemplate.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="base:opinionTemplate:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="opinionTemplate" action="${ctx}/base/opinionTemplate/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">登录名：</label>
			<div class="controls">
			<c:if test="${fns:getUserIsAdmin() eq '1'}">
				<form:input path="owner" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>			
			</c:if>
			<c:if test="${fns:getUserIsAdmin() ne '1'}">
				<form:hidden path="owner"/>	
				${opinionTemplate.owner }		
			</c:if>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">常用批语：</label>
			<div class="controls">
				<form:textarea path="opinion" htmlEscape="false" rows="4" maxlength="64" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="10" class="input-large required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="form-actions">
			<shiro:hasPermission name="base:opinionTemplate:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>