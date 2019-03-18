<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件汇总</title>
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
    
	<form:form id="searchForm" modelAttribute="tcaseDistrict" action="${ctx}/tcasedistrict/tcaseDistrict/query" method="post" class="breadcrumb form-search">
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
			<!--  
			<c:if test="${!fn:startsWith(userOffice, '320')}">
			<li><label class="control-label">经办单位：</label>
			    <sys:treeselect id="handleOrg" name="handleOrg" value="${tcaseDistrict.handleOrg}" 
			    labelName="name" labelValue="${tcaseDistrict.handleOrgName}" 
				title="经办单位" url="/sys/office/treeDataDistrict"  checked="true" cssClass="input-large" allowClear="true"
				/>
			</li>	
			</c:if>	
			-->		
			<li><label class="control-label">状态：</label>
				<form:select path="status" class="input-large ">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('case_district_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>		
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
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
				<th>经办单位</th>
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
					${tcaseDistrict.handleOrgName}
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
				<td><a href="${ctx}/tcasedistrict/tcaseDistrict/form?id=${tcaseDistrict.id}&readonly=1">查看</a></td>

			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>