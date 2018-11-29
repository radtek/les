<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件管理</title>
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
    <h3>我的案件列表</h3>
	<form:form id="searchForm" modelAttribute="tcase" action="${ctx}/case/tcase/mylist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>编号</th>
				<th>当事人</th>
				<th>项目名称</th>	
				<th>事项名称</th>
				<th>立案经办人</th>
				<th>案件来源</th>
				<th>当前阶段</th>
				<shiro:hasPermission name="case:tcase:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tcase" varStatus="status">
			<tr>
			    <td>${tcase.caseSeq}</td>
				<td>${tcase.partyDisplay}</td>
				<td>
					${tcase.projectName}
				</td>
				<td>
					${tcase.caseCause}
				</td>				
				<td>
					${tcase.initialHandlerName}
				</td>
				<td>
					${tcase.caseSource}
				</td>								
				<td>
				    <c:choose>
				        <c:when test="${tcase.status eq '0'}">未开始</c:when>
					    <c:when test="${tcase.status eq '2'}">已办结</c:when>
					    <c:when test="${tcase.status eq '9'}">已撤销</c:when>	
					    <c:when test="${fns:startsWith(tcase.status, '1')}">
		
							<c:forEach items="${tcase.currentCaseProcesses}" var="process" varStatus="pstatus">
							<c:if test="${pstatus.index gt 0}"><BR></c:if>
							${fns:getDictLabel(process.caseStage, 'case_stage', '无')},
							状态：${fns:getDictLabel(process.caseStageStatus, 'case_stage_status', '无')}		
							</c:forEach>
					    </c:when>				    
					    <c:otherwise>												    
					    </c:otherwise>
				    </c:choose>
				</td>				
				<shiro:hasPermission name="case:tcase:edit"><td>
    				<a href="${ctx}/case/tcase/infoTab?businesskey=${tcase.id}" target="_blank">进入</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>