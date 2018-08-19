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
	
	<form:form id="searchForm" modelAttribute="tcase" action="${ctx}/case/tcase/query" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">案卷年度：</label>
			<div class="controls controls-tight">
			<!--  
			    <form:input path="tcase.caseSource" htmlEscape="false" maxlength="100" class="input-xlarge"/>
			-->
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label">当事人类型：</label>
			<div class="controls controls-tight">
				<form:radiobuttons path="partyType" items="${fns:getDictList('party_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
			</div>
		        </div>
		    </div>
		</div>
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">当事人名称：</label>
			<div class="controls controls-tight">
			    <form:input path="partyName" htmlEscape="false" maxlength="100" class="input-xlarge"/>
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label">项目名称：</label>
			<div class="controls controls-tight">
				<form:input path="projectName" htmlEscape="false" maxlength="100" class="input-xlarge"/>
			</div>
		        </div>
		    </div>
		</div>
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">案由：</label>
			<div class="controls controls-tight">
			    <form:input path="caseCause" htmlEscape="false" maxlength="100" class="input-xlarge"/>
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label">处罚决定书编号：</label>
			<div class="controls controls-tight">
				<form:input path="caseDecision.year" htmlEscape="false" maxlength="100" class="input-xlarge"/>年<form:input path="caseDecision.seq" htmlEscape="false" maxlength="100" class="input-xlarge"/>号
			</div>
		        </div>
		    </div>
		</div>
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">立案日期：</label>
			<div class="controls controls-tight">
				<input name="initialDateFrom" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tcase.initialDateFrom}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<input name="initialDateTo" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tcase.initialDateTo}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>			
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label">结案日期：</label>
			<div class="controls controls-tight">
				<input name="settleDateFrom" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tcase.settleDateFrom}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<input name="settleDateTo" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tcase.settleDateTo}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		        </div>
		    </div>
		</div>						
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">案件状态：</label>
			<div class="controls controls-tight">
                <form:radiobuttons path="status" items="${fns:getDictList('case_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>			
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label"></label>
			<div class="controls controls-tight">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			</div>
		        </div>
		    </div>
		</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>编号</th>
				<th>案卷编号</th>
				<th>当事人</th>
				<th>项目名称</th>
				<th>案由</th>
				<th>处罚决定书编号</th>
				<th>立案日期</th>
				<th>结案日期</th>
				<th>经办人</th>
				<th>案件状态</th>
				<th>上报状态</th>
				<th>上报附件缺失</th>
				<th>前置机附件缺失</th>
				<shiro:hasPermission name="case:tcase:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tcase" varStatus="status">
			<tr>
			    <td>${status。index+1}</td>
				<td></td>
				<td>${tcase.projectName}</td>
				<td>${tcase.caseCause}</td>
				<td>${tcase.caseSource}</td>								
				<td></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>