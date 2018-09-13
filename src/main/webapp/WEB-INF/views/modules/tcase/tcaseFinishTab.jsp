<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件结束管理</title>
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
				$("#inputForm").attr("action","${ctx}/tcase/caseFinish/save");
				$("#inputForm").submit();		    	
		    });			
			
		    $('#btnFinish').click(function() {
				$("#inputForm").attr("action","${ctx}/tcase/caseFinish/exportPDF");
				$("#inputForm").submit();		    	
		    });			
		});
	</script>
</head>
<body>
	<h3>案件结束</h3>

    <les:caseSummary caseAttr="${caseAct.tcase}"></les:caseSummary>	
    
    <les:caseTab tab="finish" caseActAttr="${caseAct}"></les:caseTab> 
    
    <les:caseProcessTag caseSummaryBehaviour="hide"></les:caseProcessTag>
    
	<form:form id="inputForm" modelAttribute="caseFinish" action="${ctx}/tcase/caseFinish/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="caseId"/>
		<form:hidden path="paramUri" value="${caseAct.paramUri}"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">页数信息：</label>
			<div class="controls">
				本保管单位内共<form:input path="totalPage" htmlEscape="false" maxlength="6" class="input-mini required"/>页,
				其中:文字材料<form:input path="wordPage" htmlEscape="false" maxlength="6" class="input-mini " onkeyup="value=value.replace(/[^1234567890-]+/g,'')"/>页,
				图样(图)<form:input path="diagramPage" htmlEscape="false" maxlength="6" class="input-mini " onkeyup="value=value.replace(/[^1234567890-]+/g,'')"/>页,
				照片<form:input path="photoPage" htmlEscape="false" maxlength="6" class="input-mini " onkeyup="value=value.replace(/[^1234567890-]+/g,'')"/>页,
				其他 <form:input path="otherPage" htmlEscape="false" maxlength="6" class="input-mini " onkeyup="value=value.replace(/[^1234567890-]+/g,'')"/>页。
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">说明：</label>
			<div class="controls">
				<form:textarea path="handleSummary" htmlEscape="false"  style="width:800px;height:300px;" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备考表时间：</label>
			<div class="controls">
				<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${caseFinish.finishDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="form-actions">
		<c:if test="${caseAct.tcase.caseProcess.editable}">
			<shiro:hasPermission name="case:tcase:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
			
			</shiro:hasPermission>
		</c:if>
		    <input id="btnFinish" class="btn btn-primary" type="button" value="备考表"/>
		</div>
	</form:form>
	<les:toApprove caseActAttr="${caseAct}" caseStageAttr="70"></les:toApprove>
</body>
</html>