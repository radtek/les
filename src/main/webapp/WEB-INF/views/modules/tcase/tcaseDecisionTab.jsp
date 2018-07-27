<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件决定书</title>
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
	<h3>案件决定书</h3>

    <les:caseSummary caseAttr="${caseAct.tcase}"></les:caseSummary>	
    
    <les:caseTab tab="decision" caseActAttr="${caseAct}"></les:caseTab>  
    
	<form:form id="inputForm" modelAttribute="caseDecision" action="${ctx}/tcase/caseDecision/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="caseId"/>
		<form:hidden path="paramUri" value="${caseAct.paramUri}"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">主送：</label>
			<div class="controls">
				<form:input path="partyName" htmlEscape="false" maxlength="100" class="input-xlarge required" readonly="readonly"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">备案单位：</label>
			<div class="controls">
				<form:input path="recordOrg" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">决定书编号：</label>
			<div class="controls">
			    <form:select path="decisionType">
			       <form:options items="${fns:getDictList('case_decision_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			    </form:select>
				[<form:input path="year" htmlEscape="false" maxlength="8" class="input-mini required"/>
				<span class="help-inline"><font color="red">*</font> </span>]年第(
			    <form:hidden path="seq"/>
			    <c:if test="${empty caseDecision.seq}">
			    <input value="自动生成" readonly="readonly" class="input-mini">
			    </c:if>
			    <c:if test="${not empty caseDecision.seq}">${caseDecision.seq}</c:if>)号
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">拟稿日期：</label>
			<div class="controls">
				<input name="compileDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${caseAct.tcase.acceptDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>					
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">印数：</label>
			<div class="controls">
				<form:input path="printCount" htmlEscape="false" maxlength="64" class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">送达地点：</label>
			<div class="controls">
				<form:input path="destinationAddress" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">无锡市建设局行政处罚决定书内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false"  style="width:800px;height:600px;" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发证部门：</label>
			<div class="controls">
				<form:input path="launchDept" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发证时间：</label>
			<div class="controls">
				<input name="launchDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${caseDecision.launchDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="case:tcase:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnLaunch" class="btn btn-primary" type="button" value="发文稿"/>&nbsp;
			<input id="btnDecision" class="btn btn-primary" type="button" value="处罚决定书"/>&nbsp;
			<input id="btnReach" class="btn btn-primary" type="button" value="送达回证"/>&nbsp;
			</shiro:hasPermission>
		</div>
	</form:form>
</body>
</html>