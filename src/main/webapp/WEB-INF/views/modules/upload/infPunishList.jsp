<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>inf_punish管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/upload/infPunish/">inf_punish列表</a></li>
		<shiro:hasPermission name="upload:infPunish:edit"><li><a href="${ctx}/upload/infPunish/form">inf_punish添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="infPunish" action="${ctx}/upload/infPunish/" method="post" class="breadcrumb form-search">

		<ul class="ul-form">
			<li><label>internal_no：</label>
				<form:input path="internalNo" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>update_date</th>
				<shiro:hasPermission name="upload:infPunish:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="infPunish">
			<tr>
				<td><a href="${ctx}/upload/infPunish/form?id=${infPunish.id}">
					<fmt:formatDate value="${infPunish.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<shiro:hasPermission name="upload:infPunish:edit"><td>
    				<a href="${ctx}/upload/infPunish/form?id=${infPunish.id}">修改</a>
					<a href="${ctx}/upload/infPunish/delete?id=${infPunish.id}" onclick="return confirmx('确认要删除该inf_punish吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>