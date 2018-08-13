<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文档类型管理</title>
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
		<li class="active"><a href="${ctx}/base/caseDocType/">文档类型列表</a></li>
		<shiro:hasPermission name="base:caseDocType:edit"><li><a href="${ctx}/base/caseDocType/form">文档类型添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="caseDocType" action="${ctx}/base/caseDocType/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>阶段：</label>
				<form:input path="caseStage" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>阶段</th>
				<th>文档类型</th>
				<th>是否必须</th>
				<th>上传方式</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="base:caseDocType:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="caseDocType">
			<tr>
				<td><a href="${ctx}/base/caseDocType/form?id=${caseDocType.id}">
					${caseDocType.id}
				</a></td>
				<td>
					${fns:getDictLabel(caseDocType.caseStage, 'case_stage', '')}
				</td>				
				<td>
					${caseDocType.documentType}
				</td>
				<td>
				    ${fns:getDictLabel(caseDocType.mandatory, 'yes_no', '否')}			
				</td>
				<td>
					${fns:getDictLabel(caseDocType.uploadMethod, 'upload_method', '')}
				</td>
				<td>
					<fmt:formatDate value="${caseDocType.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="base:caseDocType:edit"><td>
    				<a href="${ctx}/base/caseDocType/form?id=${caseDocType.id}">修改</a>
					<a href="${ctx}/base/caseDocType/delete?id=${caseDocType.id}" onclick="return confirmx('确认要删除该文档类型吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>