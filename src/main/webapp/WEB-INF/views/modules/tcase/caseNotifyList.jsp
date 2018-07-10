<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件告知书管理</title>
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
		<li class="active"><a href="${ctx}/case/caseNotify/">案件告知书列表</a></li>
		<shiro:hasPermission name="case:caseNotify:edit"><li><a href="${ctx}/case/caseNotify/form">案件告知书添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="caseNotify" action="${ctx}/case/caseNotify/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="case:caseNotify:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="caseNotify">
			<tr>
				<td><a href="${ctx}/case/caseNotify/form?id=${caseNotify.id}">
					<fmt:formatDate value="${caseNotify.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${caseNotify.remarks}
				</td>
				<shiro:hasPermission name="case:caseNotify:edit"><td>
    				<a href="${ctx}/case/caseNotify/form?id=${caseNotify.id}">修改</a>
					<a href="${ctx}/case/caseNotify/delete?id=${caseNotify.id}" onclick="return confirmx('确认要删除该案件告知书吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>