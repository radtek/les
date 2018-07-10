<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件资料管理</title>
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
		<li class="active"><a href="${ctx}/case/caseAttach/">案件资料列表</a></li>
		<shiro:hasPermission name="case:caseAttach:edit"><li><a href="${ctx}/case/caseAttach/form">案件资料添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="caseAttach" action="${ctx}/case/caseAttach/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
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
				<shiro:hasPermission name="case:caseAttach:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="caseAttach">
			<tr>
				<td><a href="${ctx}/case/caseAttach/form?id=${caseAttach.id}">
					<fmt:formatDate value="${caseAttach.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${caseAttach.remarks}
				</td>
				<shiro:hasPermission name="case:caseAttach:edit"><td>
    				<a href="${ctx}/case/caseAttach/form?id=${caseAttach.id}">修改</a>
					<a href="${ctx}/case/caseAttach/delete?id=${caseAttach.id}" onclick="return confirmx('确认要删除该案件资料吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>