<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>签名库管理</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript">
	
	</script>	
	
</head>
<body>

    <h4>我的签名</h4>

	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:70%">
		<thead>
			<tr>
				<th>登录名</th>
				<th>姓名</th>
				<th>签名</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="signatureLib">
			<tr>
				<td><a href="${ctx}/base/signatureLib/form?id=${signatureLib.user.loginName}">
					${signatureLib.user.loginName}
				</a></td>
				<td>
					${signatureLib.user.name}
				</td>
				<td>
				<c:if test="${not empty signatureLib.data}">
				  <img style="height:100px; width:200px; margin:0px" src="${signatureLib.data}"/>
				</c:if>
				<c:if test="${empty signatureLib.data}">
				  <font color="red">暂缺</font>
				</c:if>
				</td>
				<td>
    				<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#sigModal" onclick="loadModal('${signatureLib.user.loginName}')">签名</button>
				</td>
			</tr>
		</c:forEach>
		<les:signatureModal4Lib></les:signatureModal4Lib>
		</tbody>
	</table>
</body>
</html>

