<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<%@ include file="/WEB-INF/views/include/head.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>流转详情</title>
</head>
<body>
<h4>流转详情</h4>


	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
			<h5>流程图</h5>
			<c:choose>
			  <c:when test="${not empty executionId}">
				<iframe id="flowchart" name="flowchart" src="${ctx}/common/activiti/processTracking?processDefId=${procDefId}&executionId=${executionId}" 
				style="overflow: visible; height: 400px;" scrolling="yes" frameborder="no" width="100%" height="400px">
				</iframe>			  
			  </c:when>
			  <c:otherwise>
				<iframe id="flowchart" name="flowchart" src="${ctx}/act/diagram-viewer?processDefinitionId=${procDefId}&processInstanceId=${procInsId}" 
				style="overflow: visible; height: 400px;" scrolling="yes" frameborder="no" width="100%" height="400px">
				</iframe>			  
			  </c:otherwise>
			</c:choose>
		
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
<h5>流转信息</h5>
<table class="table table-striped table-bordered table-condensed" style="width:80%;font-size:13px;margin-left:20px">
   <thead>
	<tr><th width="15%">执行环节</th><th>执行人</th><th>提交意见</th><th width="15%">签名时间</th></tr>
   </thead>
   <tbody>
	<c:forEach items="${histoicFlowList}" var="act">
		<tr>
			<td>${act.histIns.activityName}</td>
			<td>${act.assigneeName}</td>
			<td style="word-wrap:break-word;word-break:break-all;">${act.comment}</td>
			<td><fmt:formatDate value="${act.endDate}" type="both"/></td>
		</tr>
	</c:forEach>
   </tbody>
</table>
			</div>

		</div>		
	</div>


</body>
</html>