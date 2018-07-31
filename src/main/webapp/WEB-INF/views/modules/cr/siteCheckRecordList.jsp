<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>现场检查笔录管理</title>
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
		<li class="active"><a href="${ctx}/cr/siteCheckRecord/">现场检查笔录列表</a></li>
		<shiro:hasPermission name="cr:siteCheckRecord:edit"><li><a href="${ctx}/cr/siteCheckRecord/form">现场检查笔录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="siteCheckRecord" action="${ctx}/cr/siteCheckRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span4">	
				<li><label>当事人类型：</label>
					<form:input path="partyType" htmlEscape="false" maxlength="200" class="input-medium"/>
				</li>
				</div>
			
			<div class="span4">	
				<li><label>名称</label>
					<form:input path="orgName" htmlEscape="false" maxlength="200" class="input-medium"/>
				</li>
			</div>
			
			<div class="span2">	
				<li><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<li class="clearfix"></li>
			</div>
			
			
		</div>
		</div>	
		</ul>
		
		
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>当事人类型</th>
				<th>名称</th>
				<shiro:hasPermission name="cr:siteCheckRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="siteCheckRecord">
			<tr>
				
				<td>
					${siteCheckRecord.partyType}
				</td>
				<td>
					${siteCheckRecord.orgName}
				</td>
				
				<shiro:hasPermission name="cr:siteCheckRecord:edit"><td>
    				<a href="${ctx}/cr/siteCheckRecord/form?id=${siteCheckRecord.id}">修改</a>
					<a href="${ctx}/cr/siteCheckRecord/delete?id=${siteCheckRecord.id}" onclick="return confirmx('确认要删除该现场检查笔录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>