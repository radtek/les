<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件结束管理</title>
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
		<li class="active"><a href="${ctx}/tcase/caseFinish/">案件结束列表</a></li>
		<shiro:hasPermission name="tcase:caseFinish:edit"><li><a href="${ctx}/tcase/caseFinish/form">案件结束添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="caseFinish" action="${ctx}/tcase/caseFinish/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>合计页数：</label>
				<form:input path="totalPage" htmlEscape="false" maxlength="11" class="input-medium"/>
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
				<shiro:hasPermission name="tcase:caseFinish:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="caseFinish">
			<tr>
				<td><a href="${ctx}/tcase/caseFinish/form?id=${caseFinish.id}">
					<fmt:formatDate value="${caseFinish.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${caseFinish.remarks}
				</td>
				<shiro:hasPermission name="tcase:caseFinish:edit"><td>
    				<a href="${ctx}/tcase/caseFinish/form?id=${caseFinish.id}">修改</a>
					<a href="${ctx}/tcase/caseFinish/delete?id=${caseFinish.id}" onclick="return confirmx('确认要删除该案件结束吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>