<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件流程管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/tcase/caseProcess/">案件流程列表</a></li>
		<shiro:hasPermission name="tcase:caseProcess:edit"><li><a href="${ctx}/tcase/caseProcess/form">案件流程添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="caseProcess" action="${ctx}/tcase/caseProcess/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>案件编号：</label>
				<form:input path="caseId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>案件编号</th>
				<th>事项类型</th>
				<th>事项类型状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="tcase:caseProcess:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="caseProcess">
			<tr>
				<td><a href="${ctx}/tcase/caseProcess/form?id=${caseProcess.id}">
					${caseProcess.caseId}
				</a></td>
				<td>
					${caseProcess.caseStage}
				</td>
				<td>
					${caseProcess.caseStageStatus}
				</td>
				<td>
					<fmt:formatDate value="${caseProcess.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${caseProcess.remarks}
				</td>
				<shiro:hasPermission name="tcase:caseProcess:edit"><td>
    				<a href="${ctx}/tcase/caseProcess/form?id=${caseProcess.id}">修改</a>
					<a href="${ctx}/tcase/caseProcess/delete?id=${caseProcess.id}" onclick="return confirmx('确认要删除该案件流程吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>