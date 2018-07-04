<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>询问笔录管理</title>
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
		<li class="active"><a href="${ctx}/qa/questionanswer/">询问笔录列表</a></li>
		<shiro:hasPermission name="qa:questionanswer:edit"><li><a href="${ctx}/qa/questionanswer/form">询问笔录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="questionanswer" action="${ctx}/qa/questionanswer/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>编号：</label>
				<form:input path="id" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>案由：</label>
				<form:input path="caseCause" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>结束时间：</label>
				<input name="toDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${questionanswer.toDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>案由</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>地点</th>
				<th>被询问人</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="qa:questionanswer:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="questionanswer">
			<tr>
				<td><a href="${ctx}/qa/questionanswer/form?id=${questionanswer.id}">
					${questionanswer.id}
				</a></td>
				<td>
					${questionanswer.caseCause}
				</td>
				<td>
					<fmt:formatDate value="${questionanswer.fromDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${questionanswer.toDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${questionanswer.location}
				</td>
				<td>
					${questionanswer.answerer}
				</td>
				<td>
					<fmt:formatDate value="${questionanswer.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${questionanswer.remarks}
				</td>
				<shiro:hasPermission name="qa:questionanswer:edit"><td>
    				<a href="${ctx}/qa/questionanswer/form?id=${questionanswer.id}">修改</a>
					<a href="${ctx}/qa/questionanswer/delete?id=${questionanswer.id}" onclick="return confirmx('确认要删除该询问笔录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>