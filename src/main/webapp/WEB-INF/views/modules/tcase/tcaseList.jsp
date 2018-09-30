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
    <h3>案件列表<c:if test="${tcase.unfinishedFlag eq '1'}">（未办结）</c:if></h3>
	
	<form:form id="searchForm" modelAttribute="tcase" action="${ctx}/case/tcase/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:hidden path="unfinishedFlag"/>
		<ul class="ul-form">
			<li><label>案由：</label>
				<form:input path="caseCause" htmlEscape="false" maxlength="100" class="input-xxlarge"/>
			</li>		
			<li><label>单位名称：</label>
				<form:input path="orgName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>个人姓名：</label>
				<form:input path="psnName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>			
			<li><label>受理时间：</label>
				<input name="acceptDateFrom" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tcase.acceptDateFrom}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<input name="acceptDateTo" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tcase.acceptDateTo}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>					
			</li>					
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			<c:if test="${tcase.unfinishedFlag ne '1'}">
			<input class="btn btn-primary" type="button" value="启动新案件 " onclick="window.location.href='${ctx}/case/tcase/toStartAcceptance'"/>
			</c:if>
			</li>
			<li class="clearfix"></li>
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
				<th>案件来源</th>
				<th>当前阶段</th>
				<shiro:hasPermission name="case:tcase:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tcase" varStatus="status">
			<tr>
			    <td>${tcase.caseSeq}</td>
				<td>${tcase.orgName}</td>
				<td>
					${tcase.projectName}
				</td>
				<td>
					${tcase.caseCause}
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
    				<a href="${ctx}/case/tcase/infoTab?businesskey=${tcase.id}">进入</a>
    				<c:if test="${empty tcase.currentCaseProcesses}">
    				  <a href="${ctx}/case/tcase/delete?id=${tcase.id}" onclick="return confirmx('确认要删除该案件吗？', this.href)">删除</a>
    				</c:if>
					
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>