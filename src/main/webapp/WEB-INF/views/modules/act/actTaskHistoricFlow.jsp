<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<table class="table table-striped table-bordered table-condensed">
	<tr><th>执行环节</th><th>执行人</th><th>提交意见</th><th>签名时间</th></tr>
	<c:forEach items="${histoicFlowList}" var="act">
		<tr>
			<td>${act.histIns.activityName}</td>
			<td>${act.assigneeName}</td>
			<td style="word-wrap:break-word;word-break:break-all;">${act.comment}</td>
			<td><fmt:formatDate value="${act.endDate}" type="both"/></td>
		</tr>
	</c:forEach>
</table>