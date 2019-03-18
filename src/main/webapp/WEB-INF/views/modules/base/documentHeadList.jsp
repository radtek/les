<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>发文字号管理</title>
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
		<li class="active"><a href="${ctx}/base/documentHead/">发文字号列表</a></li>
		<shiro:hasPermission name="base:documentHead:edit"><li><a href="${ctx}/base/documentHead/form">发文字号添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="documentHead" action="${ctx}/base/documentHead/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		
		<ul class="ul-form">
	        <li><label>区域：</label>	
					<form:select path="areaId" class="input-xlarge ">
					    <form:option value="" label="全部"/>
						<form:options items="${fns:getDictList('wx_area')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>			    
			</li>		
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>区域</th>
				<th>经办单位</th>
				<th>阶段</th>
				<th>类型</th>
				<th>发文字号</th>
				<th>排序</th>
				<shiro:hasPermission name="base:documentHead:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="documentHead">
			<tr>
				<td><a href="${ctx}/base/documentHead/form?id=${documentHead.id}">
					${fns:getDictLabel(documentHead.areaId, 'wx_area', '')}
				</a></td>
				<td>
					${fns:getDictLabel(documentHead.handleOrg, 'org_brief', '')}
				</td>
				<td>
					${documentHead.stage}
				</td>
				<td>
					${documentHead.type}
				</td>
				<td>
					${documentHead.docHead}
				</td>
				<td>
					${documentHead.sort}
				</td>
				<shiro:hasPermission name="base:documentHead:edit"><td>
    				<a href="${ctx}/base/documentHead/form?id=${documentHead.id}">修改</a>
					<a href="${ctx}/base/documentHead/delete?id=${documentHead.id}" onclick="return confirmx('确认要删除该发文字号吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>