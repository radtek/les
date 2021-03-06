<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>已办任务</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
        	location = '${ctx}/task/historic/?pageNo='+n+'&pageSize='+s;
        }
	</script>
</head>
<body>
	<h3>我的已办</h3>
	<form:form id="searchForm" modelAttribute="caseAct" action="${ctx}/task/historic/" method="get" class="breadcrumb form-search">
		<div>
			<label>流程类型：&nbsp;</label>
			<form:select path="procDefKey" class="input-medium">
				<form:option value="" label="全部流程"/>
				<form:options items="${fns:getDictList('process_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<label>完成时间：</label>
			<input id="beginDate"  name="beginDate"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
				value="<fmt:formatDate value="${caseAct.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
				　--　
			<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
				value="<fmt:formatDate value="${caseAct.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
		
	</form:form>
	<sys:message content="${message}"/>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>当事人</th>
				<th>项目名称</th>
				<th>事项名称</th>
				<th>当前环节</th><%--
				<th>任务内容</th> --%>
				<th>流程名称</th>
				<th>完成时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="act">
				<c:set var="task" value="${act.histTask}" />
				<c:set var="tcase" value="${act.tcase}" />
				<c:set var="vars" value="${act.vars}" />
				<c:set var="procDef" value="${act.procDef}" /><%--
				<c:set var="procExecUrl" value="${act.procExecUrl}" /> --%>
				<c:set var="status" value="${act.status}" />
				<tr>
				    <td>
                        ${tcase.caseSeq}				    
				    </td>
				    <td>${tcase.partyDisplay}</td>
				    <td>${tcase.projectNameShort}</td>
				    <td>${tcase.caseCauseShort}</td>
					<td>
						${task.name}<%--
						<a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">${task.name}</a>
						<a target="_blank" href="${ctx}/act/task/trace/info/${task.processInstanceId}">${task.name}</a> --%>
					</td><%--
					<td>${task.description}</td> --%>
					<td>${procDef.name}</td>
					<td><fmt:formatDate value="${task.endTime}" type="both"/></td>
					<td>
						<a href="${ctx}/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${status}&businesskey=${act.businesskey}&caseTransfer=${tcase.caseTransfer}">详情</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<!--  
	<h4>现场踏勘已办</h4>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>当事人</th>
				<th>项目名称</th>
				<th>事项名称</th>
				<th>流程名称</th>
				<th>完成时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page4Sitecheck.list}" var="act">
				<c:set var="task" value="${act.histTask}" />
				<c:set var="tcase" value="${act.tcase}" />
				<c:set var="vars" value="${act.vars}" />
				<c:set var="procDef" value="${act.procDef}" /><%--
				<c:set var="procExecUrl" value="${act.procExecUrl}" /> --%>
				<c:set var="status" value="${act.status}" />
				<tr>
				    <td>
                        ${tcase.caseSeq}				    
				    </td>
				    <td>${tcase.partyDisplay}</td>
				    <td>${tcase.projectNameShort}</td>
				    <td>${tcase.caseCauseShort}</td>
					<td>${procDef.name}</td>
					<td><fmt:formatDate value="${task.endTime}" type="both"/></td>
					<td>
						<a href="${ctx}/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${status}&businesskey=${act.businesskey}&caseTransfer=${tcase.caseTransfer}">详情</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page4Sitecheck}</div>	
	-->
</body>
</html>
