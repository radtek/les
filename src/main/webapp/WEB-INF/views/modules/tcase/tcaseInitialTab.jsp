<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件管理</title>
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
			
		    $("input[type=radio][name='tcase.partyType']").change(function() {
		        if (this.value == '单位') {
		        	$("#info4Org").show();
		        	$("#info4Individual").hide();
		        }
		        else if (this.value == '个人') {
		        	$("#info4Org").hide();
		        	$("#info4Individual").show();
		        }
		    });	
		    
		    $('#btnSubmit').click(function() {
				$("#inputForm").attr("action","${ctx}/case/tcase/save");
				$("#inputForm").submit();		    	
		    });
		    
		    $('#btnStart').click(function() {
				$("#inputForm").attr("action","${ctx}/case/tcase/saveAndStart");
				$("#inputForm").submit();		    	
		    });
		    
		    $('#btnApproveTable').click(function() {
				$("#inputForm").attr("action","${ctx}/case/tcase/exportPDF");
				$("#inputForm").submit();		    	
		    });
		});
	</script>
</head>
<body>
	<h3>案件管理</h3>
	
    <c:if test="${not empty caseAct.tcase.id}">
    <les:caseSummary caseAttr="${caseAct.tcase}"></les:caseSummary>
    </c:if>
    
    <les:caseTab tab="initial" caseActAttr="${caseAct}"></les:caseTab>
    	
	<form:form id="inputForm" modelAttribute="caseAct" action="${ctx}/case/tcase/save" method="post" class="form-horizontal">
		<form:hidden path="tcase.id"/>
		
		<form:hidden path="operateType"/>

		<sys:message content="${message}"/>	
        
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span12">		
			<label class="control-label">案情摘要：</label>
			<div class="controls controls-tight">
			    <form:hidden path="tcase.caseProcess.id"/>
			    <form:hidden path="tcase.caseProcess.caseId"/>
			    <form:hidden path="tcase.caseProcess.caseStage"/>
			    <form:hidden path="tcase.caseProcess.caseStageStatus"/>
				<form:textarea path="tcase.caseProcess.caseSummary" htmlEscape="false"  style="width:800px;height:80px;" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>

		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span12">		
			<label class="control-label">办案人：</label>
			<div class="controls controls-tight">
			    
		       <form:checkboxes path="tcase.caseProcess.caseHandlerList" items="${caseAct.tcase.caseProcess.availableHandlers}" itemLabel="name" itemValue="loginName" htmlEscape="false" class="required"/>
		       <span class="help-inline"><font color="red">*</font> </span>			              			
				
			</div>
		        </div>
		    </div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="case:tcase:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
			<c:if test="${caseAct.operateType eq 'start'}">
			   <input id="btnStart" class="btn btn-primary" type="button" value="启动事件"/>&nbsp;
			</c:if>
			<input id="btnApproveTable" class="btn btn-primary" type="button" value="案件立案审批表"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>