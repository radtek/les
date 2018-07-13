<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();

		});
	</script>
</head>
<body>
	<h3>案件管理</h3>
    <div style="text-align:right;margin-right:30px;">
        <input class="btn btn-primary" type="button" value="返回 " onclick="window.location.href='${ctx}/case/tcase/'"/>
    </div>		
    <les:caseSummary></les:caseSummary>
    <les:caseTab tab="attach" id="${tcase.id}"></les:caseTab>    

	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>流程点</th>
				<th>资料类型</th>
				<th>文件名称</th>
				<th>添加人</th>
				<th>更新时间</th>
				<shiro:hasPermission name="tcase:caseAttach:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${attachlist}" var="caseAttach">
			<tr>
				<td>
					${caseAttach.flowNode}
				</td>			
				<td>
				    ${caseAttach.attachType}
				</td>
				<td>
					${caseAttach.filename}
				</td>
				<td>
					${caseAttach.createBy.name}
				</td>
				<td>
					<fmt:formatDate value="${caseAttach.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="tcase:caseAttach:edit"><td>
    				<a href="${ctx}/tcase/caseAttach/form?id=${caseAttach.id}">修改</a>
					<a href="${ctx}/tcase/caseAttach/delete?id=${caseAttach.id}" onclick="return confirmx('确认要删除该案件资料吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>

</body>
</html>