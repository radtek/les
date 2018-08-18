<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>常用批语管理</title>
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
		<li class="active"><a href="${ctx}/base/opinionTemplate/">常用批语列表</a></li>
		<shiro:hasPermission name="base:opinionTemplate:edit"><li><a href="${ctx}/base/opinionTemplate/form">常用批语添加</a></li></shiro:hasPermission>
	</ul>
	<c:if test="${fns:getUserIsAdmin() eq '1'}">
	<form:form id="searchForm" modelAttribute="opinionTemplate" action="${ctx}/base/opinionTemplate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>	
		<ul class="ul-form">
		    <li><label>登录名：</label><form:input path="owner" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	</c:if>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>拥有者</th>
				<th>常用批语</th>
				<th>排序</th>
				<th>更新时间</th>
				<shiro:hasPermission name="base:opinionTemplate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="opinionTemplate">
			<tr>
				<td><a href="${ctx}/base/opinionTemplate/form?id=${opinionTemplate.id}">
					${opinionTemplate.id}
				</a></td>
				<td>
					${opinionTemplate.owner}
				</td>				
				<td>
					${opinionTemplate.opinion}
				</td>
				<td>
					${opinionTemplate.sort}
				</td>				
				<td>
					<fmt:formatDate value="${opinionTemplate.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="base:opinionTemplate:edit"><td>
    				<a href="${ctx}/base/opinionTemplate/form?id=${opinionTemplate.id}">修改</a>
					<a href="${ctx}/base/opinionTemplate/delete?id=${opinionTemplate.id}" onclick="return confirmx('确认要删除该常用批语吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>