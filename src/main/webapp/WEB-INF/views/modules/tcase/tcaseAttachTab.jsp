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
    <c:if test="${caseAct.caseTransfer eq '1'}">
    <h3>案源移交</h3>
    </c:if>
    <c:if test="${empty caseAct.caseTransfer or caseAct.caseTransfer eq '0'}">
    <h3>案件审理</h3>
    </c:if>
	
    <les:caseSummary caseAttr="${caseAct.tcase}"></les:caseSummary>

    <les:caseTab tab="attach" caseActAttr="${caseAct}"></les:caseTab>  

	<sys:message content="${message}"/>
	
	<div style="margin:10px 60px 10px 0;text-align:right">
	    <shiro:hasPermission name="case:tcase:edit">
	    <input class="btn btn-primary" type="button" value="添加资料" onclick="window.location.href='${ctx}/case/caseAttach/form?${caseAct.paramUri}'"/>
	    </shiro:hasPermission>	   
	</div>	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>序号</th>
			    <th>流程点</th>
				<th>资料类型</th>
				<th>文件名称</th>
				<th>添加人</th>
				<th>添加时间</th>
				<shiro:hasPermission name="case:tcase:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${attachlist}" var="caseAttach" varStatus="status">
			<tr>
			    <td>${status.index + 1}</td>
				<td>
					${caseAttach.flowNode}
				</td>			
				<td>
				    ${fns:getDictLabel(caseAttach.attachType, 'case_stage_filetype', '')}
				</td>
				<td>
					<a href="${caseAttach.filepath}" target="_blank">${caseAttach.filename}</a>
				</td>
				<td>
					${caseAttach.createBy.name}
				</td>
				<td>
					<fmt:formatDate value="${caseAttach.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="case:tcase:edit"><td>
					<a href="${ctx}/tcase/caseAttach/delete?id=${caseAttach.id}" onclick="return confirmx('确认要删除该案件资料吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>

</body>
</html>