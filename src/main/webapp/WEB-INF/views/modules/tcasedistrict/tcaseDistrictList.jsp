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
    <h3>案件列表</h3>
    
	<form:form id="searchForm" modelAttribute="tcaseDistrict" action="${ctx}/tcasedistrict/tcaseDistrict/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>登记时间：</label>
				<input name="dateFrom" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="<fmt:formatDate value="${tcaseDistrict.dateFrom}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>到
				<input name="dateTo" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="<fmt:formatDate value="${tcaseDistrict.dateTo}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>					
			</li>	
			<li><label class="control-label">状态：</label>
				<form:select path="status" class="input-large ">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('case_district_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>		
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			<input class="btn btn-primary" type="button" value="新案件 " onclick="window.location.href='${ctx}/tcasedistrict/tcaseDistrict/form'"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>案件编号</th>
				<th>立案日期</th>
				<th>立案经办人</th>
				<th>处罚决定日期</th>
				<th>结案日期</th>
				<th>当事人</th>
				<th>案件所涉项目名称</th>
				<th>案由</th>
				<th>决定书编号</th>
				<th>立案审批表</th>
				<th>决定书</th>
				<th>状态</th>
				<shiro:hasPermission name="tcasedistrict:tcaseDistrict:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tcaseDistrict">
			<tr>
				<td><a href="${ctx}/tcasedistrict/tcaseDistrict/form?id=${tcaseDistrict.id}">
				    ${tcaseDistrict.caseSeq}
				</a></td>
				<td>
					<fmt:formatDate value="${tcaseDistrict.initialDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${tcaseDistrict.initialHandler}
				</td>
				<td>
					<fmt:formatDate value="${tcaseDistrict.decisionDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${tcaseDistrict.settleDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${tcaseDistrict.partyDisplay}
				</td>
				<td>
					${tcaseDistrict.projectName}
				</td>
				<td>
					${tcaseDistrict.caseCause}
				</td>
				<td>
					${tcaseDistrict.decisionSeq}
				</td>
				<td>
					<a href="${tcaseDistrict.filepathInitial}" target="_blank">${tcaseDistrict.filenameInitial}</a>
				</td>
				<td>
					<a href="${tcaseDistrict.filepathDecision}" target="_blank">${tcaseDistrict.filenameDecision}</a>
				</td>
				<td>
					${fns:getDictLabel(tcaseDistrict.status, 'case_district_status', '')}
				</td>				
				<c:if test="${tcaseDistrict.status eq '0'}">
				<shiro:hasPermission name="tcasedistrict:tcaseDistrict:edit"><td>
    				<a href="${ctx}/tcasedistrict/tcaseDistrict/form?id=${tcaseDistrict.id}">修改</a>
					<a href="${ctx}/tcasedistrict/tcaseDistrict/delete?id=${tcaseDistrict.id}" onclick="return confirmx('确认要删除该案件吗？', this.href)">删除</a>
				</td></shiro:hasPermission>				
				</c:if>
				<c:if test="${tcaseDistrict.status eq '1'}">
				<td><a href="${ctx}/tcasedistrict/tcaseDistrict/form?id=${tcaseDistrict.id}">查看</a></td>
				</c:if>

			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>