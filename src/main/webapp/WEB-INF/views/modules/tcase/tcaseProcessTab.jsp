<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

		});
	</script>
</head>
<body>
	<h3>案件管理</h3>
    <div style="text-align:right;margin-right:30px;">
        <input class="btn btn-primary" type="button" value="返回 " onclick="window.location.href='${ctx}/case/tcase/'"/>
    </div>
    <les:caseSummary caseAttr="${caseAct.tcase}"></les:caseSummary>	
    
    <les:caseTab tab="process" caseActAttr="${caseAct}"></les:caseTab>  
    
    <c:if test="${not empty caseAct.task}">
    <h5>&nbsp;流程处理</h5>
    <c:choose>
       <c:when test="${caseAct.tcase.caseProcess.caseStageStatus eq '0'}">
         <les:processStartTag handleAction="${ctx}/case/tcase/startWorkFlow"></les:processStartTag>
       </c:when>
       <c:when test="${caseAct.tcase.caseProcess.caseStageStatus eq '1' and not empty caseAct.task}">
         <les:processHandleTag handleAction="${ctx}/case/tcase/handletask" taskId="${caseAct.task.id}" availableHandlers="${caseAct.tcase.caseProcess.availableHandlers}"></les:processHandleTag>
       </c:when>       
       <c:otherwise>
       </c:otherwise>
    </c:choose>    
    </c:if> 
    
    <h5>&nbsp;处罚流程</h5>
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:70%">
		<thead>
			<tr>
			    <th width="5%">序号</th>
			    <th width="55%">流程阶段</th>
				<th width="20%">状态</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
            <c:forEach items="${processlist}" var="process" varStatus="status">		
			<tr>
			    <td>${status.index + 1}</td>
				<td>${fns:getDictLabel(process.caseStage, 'case_stage', '')}</td>			
				<td>${fns:getDictLabel(process.caseStageStatus, 'case_stage_status', '')}</td>	
				<td>
				<c:if test="${process.caseStageStatus eq '0'}">
				  <a href="">启动</a>
				</c:if>
				<c:if test="${not empty process.procInstId}">
				  <a target="_blank" href="${pageContext.request.contextPath}/act/diagram-viewer?processDefinitionId=${process.procDefId}&processInstanceId=${process.procInstId}">跟踪</a>&nbsp;
				</c:if>
				<c:if test="${process.caseStageStatus eq '2'}">
				  <a href="">修改办理时间</a>
				</c:if>
				</td>
			</tr>
			</c:forEach>																	
		</tbody>
	</table>
	
	<h5>&nbsp;独立流程</h5>
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:70%">
		<thead>
			<tr>
			    <th width="5%">序号</th>
			    <th width="55%">流程阶段</th>
				<th width="20%">状态</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
            <c:forEach items="${independentProcesslist}" var="process" varStatus="status">		
			<tr>
			    <td>${status.index + 1}</td>
				<td>${fns:getDictLabel(process.caseStage, 'case_stage', '')}</td>			
				<td>${fns:getDictLabel(process.caseStageStatus, 'case_stage_status', '')}</td>	
				<td>
				<c:if test="${process.caseStageStatus eq '0'}">
				  <a href="">启动</a>
				</c:if>
				<c:if test="${not empty process.procInstId}">
				  <a target="_blank" href="${pageContext.request.contextPath}/act/diagram-viewer?processDefinitionId=${process.procDefId}&processInstanceId=${process.procInstId}">跟踪</a>&nbsp;
				</c:if>
				<c:if test="${process.caseStageStatus eq '2'}">
				  <a href="">修改办理时间</a>
				</c:if>				
				</td>
			</tr>
			</c:forEach>																	
		</tbody>
	</table>	
</body>
</html>