<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件告知书管理</title>
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
				$("#inputForm").attr("action","${ctx}/case/caseNotify/save");
				$("#inputForm").submit();		    	
		    });			
			
		    $('#btnNotify').click(function() {
				$("#inputForm").attr("action","${ctx}/case/caseNotify/exportPDF");
				$("#inputForm").submit();		    	
		    });	
		    
		    $('#btnNotifyStub').click(function() {
				$("#inputForm").attr("action","${ctx}/case/caseNotify/exportCopyPDF");
				$("#inputForm").submit();		    	
		    });	

			$("#btnRecall").click(function(){
				
				top.$.jBox.confirm("确认要收回告知书编号吗？本操作无法撤销。","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#inputForm").attr("action","${ctx}/case/caseNotify/recallNumber");
						$("#inputForm").submit();
					}
				},{buttonsFocus:1});				

				top.$('.jbox-body .jbox-icon').css('top','55px');
			});		    
		    			    
		});
	</script>
</head>
<body>
	<h3>案件告知书</h3>

    <les:caseSummary caseAttr="${caseAct.tcase}"></les:caseSummary>	
    
    <les:caseTab tab="notify" caseActAttr="${caseAct}"></les:caseTab> 
    
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
    
	<form:form id="inputForm" modelAttribute="caseNotify" action="${ctx}/case/caseNotify/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="caseId"/>
		<form:hidden path="paramUri" value="${caseAct.paramUri}"/>
		<sys:message content="${message}"/>		
		
		<div class="control-group">
			<label class="control-label">告知书编号：</label>
			<div class="controls">
			    <form:select path="notifyType">
			       <form:options items="${fns:getDictList('case_notify_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			    </form:select>
				[<form:input path="year" htmlEscape="false" maxlength="8" class="input-mini required"/>
				<span class="help-inline"><font color="red">*</font> </span>]年第(
			    <form:hidden path="seq"/>
			    <c:if test="${empty caseNotify.seq}">
			    <input value="自动生成" readonly="readonly" class="input-mini">)号
			    </c:if>
			    <c:if test="${not empty caseNotify.seq}">${caseNotify.seq})号 
			    <c:if test="${caseNotify.seq ne '已收回'}">
			    <shiro:hasPermission name="case:tcase:seqRecall">
			    <input id="btnRecall" class="btn btn-primary" type="button" value="收回编号"/>
			    </shiro:hasPermission>
			    </c:if>
			    </c:if>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">责任人／单位：</label>
			<div class="controls">
				<form:input path="partyName" htmlEscape="false" maxlength="100" class="input-xlarge required" readonly="readonly"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">告知书内容：</label>
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
					value="<fmt:formatDate value="${caseNotify.launchDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="form-actions">
		
			<shiro:hasPermission name="case:tcase:edit">
		<c:if test="${caseAct.tcase.caseProcess.editable}">	
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
		</c:if>
			<input id="btnNotify" class="btn btn-primary" type="button" value="行政处罚事先告知书"/>&nbsp;
			<input id="btnNotifyStub" class="btn btn-primary" type="button" value="行政处罚事先告知书（存根）"/>&nbsp;
			</shiro:hasPermission>
		</div>
	</form:form>
	<les:toApprove caseActAttr="${caseAct}" caseStageAttr="40"></les:toApprove>
</body>
</html>