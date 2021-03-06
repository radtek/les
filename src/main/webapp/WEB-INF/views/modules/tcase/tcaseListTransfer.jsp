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
    <h3>案源列表</h3>
	
	<form:form id="searchForm" modelAttribute="tcase" action="${ctx}/tcase/caseTransfer/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
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
			<li><label>创建时间：</label>
				<input name="createDateFrom" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tcase.createDateFrom}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<input name="createDateTo" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tcase.createDateTo}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>					
			</li>	
			<li><label class="control-label">案件状态：</label>
				<form:select path="status" class="input-xlarge ">
					<form:option value="all" label="全部"/>
					<form:options items="${fns:getDictList('case_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>							
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			<input class="btn btn-primary" type="button" value="录入新案源 " onclick="window.location.href='${ctx}/tcase/caseTransfer/toStart'"/>
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
				<th>移交单位</th>
				<th>项目名称</th>
				<th>事项名称</th>
				<th>状态</th>
				<shiro:hasPermission name="case:tcase:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tcase" varStatus="status">
			<tr>
			    <td>${tcase.caseSeq}</td>
				<td>${tcase.orgName}</td>
				<td>${tcase.transferUnit}</td>
				<td>
					${tcase.projectName}
				</td>
				<td>
					${tcase.caseCause}
				</td>
				<td>
					${fns:getDictLabel(tcase.status, 'case_status', '无')}
				</td>				
				<shiro:hasPermission name="case:tcase:edit"><td>
    				<a href="${ctx}/tcase/caseTransfer/infoTab?businesskey=${tcase.id}">进入</a>
    				<c:if test="${empty tcase.currentCaseProcesses}">
    				  <a href="${ctx}/tcase/caseTransfer/delete?id=${tcase.id}" onclick="return confirmx('确认要删除该案源吗？', this.href)">删除</a>
    				</c:if>
					
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>