<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>签名库管理</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript">
	    
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	
	</script>	
	
</head>
<body>
	<h4>签名库</h4>
	<form:form id="searchForm" modelAttribute="signatureLib" action="${ctx}/base/signatureLib/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
	        <li><label>区域：</label>	
					<form:select path="user.office.areaId" class="input-xlarge ">
					    <form:option value="" label="全部"/>
						<form:options items="${fns:getDictList('wx_area')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>			    
			</li>		
			<li><label>登录名：</label>
				<form:input path="user.loginName" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="user.name" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:myMessage content="${message}"/>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:70%">
		<thead>
			<tr>
				<th>登录名</th>
				<th>姓名</th>
				<th>区域</th>
				<th>签名</th>
				<shiro:hasPermission name="base:signatureLib:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="signatureLib">
			<tr>
				<td><a href="${ctx}/base/signatureLib/form?id=${signatureLib.user.loginName}">
					${signatureLib.user.loginName}
				</a></td>
				
				<td>
					${signatureLib.user.name}
				</td>
				<td>${fns:getDictLabel(signatureLib.user.office.areaId, 'wx_area', '')}</td>
				<td>
				<c:if test="${not empty signatureLib.data}">
				  <img style="height:80px; width:160px; margin:0px" src="${signatureLib.data}"/>
				</c:if>
				<c:if test="${empty signatureLib.data}">
				  <font color="red">暂缺</font>
				</c:if>
				</td>
				<shiro:hasPermission name="base:signatureLib:edit"><td>
    				<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#sigModal" onclick="loadModal('${signatureLib.user.loginName}')">上传签名</button>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		<les:signatureImagePicker></les:signatureImagePicker>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>

