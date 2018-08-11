<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>重大行政处罚管理</title>
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
			
		    $('#btnSubmit').click(function() {
				$("#inputForm").attr("action","${ctx}/tcase/caseSerious/save");
				$("#inputForm").submit();		    	
		    });			
			
		    $('#btnExport').click(function() {
				$("#inputForm").attr("action","${ctx}/tcase/caseSerious/exportPDF");
				$("#inputForm").submit();		    	
		    });
		});
	</script>
</head>
<body>
	<h3>重大行政处罚</h3>

    <les:caseSummary caseAttr="${caseAct.tcase}"></les:caseSummary>	
    
    <les:caseTab tab="serious" caseActAttr="${caseAct}"></les:caseTab> 
    
    <c:choose>
       <c:when test="${caseAct.tcase.caseProcess.caseStageStatus eq '1' and not empty caseAct.task}">
         <h5>&nbsp;流程处理</h5>
         <les:processHandleTag handleAction="${ctx}/tcase/caseSerious/handletask"
         availableHandlers="${caseAct.tcase.caseProcess.availableHandlers}" 
         actTaskAttr="${actTask}" >
         </les:processHandleTag>
       </c:when>       
       <c:otherwise>
       </c:otherwise>
    </c:choose>    

    <les:caseProcessTag4Serious hideCaseSummary="1"></les:caseProcessTag4Serious>
    
	<form:form id="inputForm" modelAttribute="caseSerious" action="${ctx}/tcase/caseSerious/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="caseId"/>
		<form:hidden path="paramUri" value="${caseAct.paramUri}"/>
		<sys:message content="${message}"/>		
		<h4>重大行政处罚</h4>
		<div class="control-group">
			<label class="control-label">时间：</label>
			<div class="controls">
				<input name="meetingDateFrom" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${caseSerious.meetingDateFrom}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>到
				<input name="meetingDateTo" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${caseSerious.meetingDateTo}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地点：</label>
			<div class="controls">
				<form:input path="meetingAddress" htmlEscape="false" maxlength="64" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主持人：</label>
			<div class="controls">
			    <sys:treeselect id="master" name="master.loginName" value="${caseSerious.master.loginName}" labelName="master.name" labelValue="${caseSerious.master.name}" 
				title="主持人" url="/sys/user/treeDataWithLoginName?officeId=" checked="true" cssClass="input-xxlarge" allowClear="true" notAllowSelectParent="true"/>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参会人员：</label>
			<div class="controls">
			    <sys:treeselect id="voter" name="voter.loginName" value="${caseSerious.voter.loginName}" labelName="voter.name" labelValue="${caseSerious.voter.name}" 
				title="参会人员" url="/sys/user/treeDataWithLoginName?officeId=" checked="true" cssClass="input-xxlarge" allowClear="true" notAllowSelectParent="true"/>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参会人员补充：</label>
			<div class="controls">
				<form:input path="voterAdd" htmlEscape="false" maxlength="128" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">列席人员：</label>
			<div class="controls">
			    <sys:treeselect id="attendee" name="attendee.loginName" value="${caseSerious.attendee.loginName}" labelName="attendee.name" labelValue="${caseSerious.attendee.name}" 
				title="列席人员" url="/sys/user/treeDataWithLoginName?officeId=" checked="true" cssClass="input-xxlarge" allowClear="true" notAllowSelectParent="true"/>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">列席人员补充：</label>
			<div class="controls">
				<form:input path="attendeeAdd" htmlEscape="false" maxlength="128" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">记录人员：</label>
			<div class="controls">
			    <sys:treeselect id="recorder" name="recorder.loginName" value="${caseSerious.recorder.loginName}" labelName="recorder.name" labelValue="${caseSerious.recorder.name}" 
				title="记录人员" url="/sys/user/treeDataWithLoginName?officeId=" checked="true" cssClass="input-xxlarge" allowClear="true" notAllowSelectParent="true"/>					
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">执法机构汇报案情：</label>
			<div class="controls">
				<form:textarea path="caseSummary" htmlEscape="false"  style="width:800px;height:300px;" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">执法机构处罚建议：</label>
			<div class="controls">
				<form:textarea path="punishProposal" htmlEscape="false"  style="width:800px;height:300px;" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审查小组审查意见：</label>
			<div class="controls">
				<form:textarea path="checkOpinion" htmlEscape="false"  style="width:800px;height:300px;" class="required"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="case:tcase:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
			<input id="btnExport" class="btn btn-primary" type="button" value="无锡市建设局重大行政处罚审查表"/>&nbsp;
			</shiro:hasPermission>
		</div>
	</form:form>
</body>
</html>