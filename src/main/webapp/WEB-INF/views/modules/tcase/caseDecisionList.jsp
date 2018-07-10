<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件决定书管理</title>
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
		<li class="active"><a href="${ctx}/case/caseDecision/">案件决定书列表</a></li>
		<shiro:hasPermission name="case:caseDecision:edit"><li><a href="${ctx}/case/caseDecision/form">案件决定书添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="caseDecision" action="${ctx}/case/caseDecision/" method="post" class="breadcrumb form-search">
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
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="case:caseDecision:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="caseDecision">
			<tr>
				<td><a href="${ctx}/case/caseDecision/form?id=${caseDecision.id}">
					<fmt:formatDate value="${caseDecision.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${caseDecision.remarks}
				</td>
				<shiro:hasPermission name="case:caseDecision:edit"><td>
    				<a href="${ctx}/case/caseDecision/form?id=${caseDecision.id}">修改</a>
					<a href="${ctx}/case/caseDecision/delete?id=${caseDecision.id}" onclick="return confirmx('确认要删除该案件决定书吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>