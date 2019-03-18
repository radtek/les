<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>发文字号管理</title>
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
		<li><a href="${ctx}/base/documentHead/">发文字号列表</a></li>
		<li class="active"><a href="${ctx}/base/documentHead/form?id=${documentHead.id}">发文字号<shiro:hasPermission name="base:documentHead:edit">${not empty documentHead.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="base:documentHead:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="documentHead" action="${ctx}/base/documentHead/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">区域代码：</label>
			<div class="controls">
				<form:select path="areaId" class="input-xlarge ">
					<form:options items="${fns:getDictList('wx_area')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>				
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经办单位：</label>
			<div class="controls">
				<form:select path="handleOrg" class="input-xlarge ">
				    <form:options items="${fns:getDictList('org_brief')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>				
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">阶段：</label>
			<div class="controls">
				<form:select path="stage" class="input-xlarge ">
				    <form:option value="decision" label="决定书"/>
					<form:option value="notify" label="告知书"/>
				</form:select>				
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:input path="type" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font>填写1,2,3；比如当决定书有两种发文字号，用1和2区分 </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发文字号：</label>
			<div class="controls">
				<form:input path="docHead" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> 填写10,20,30</span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="64" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="base:documentHead:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>