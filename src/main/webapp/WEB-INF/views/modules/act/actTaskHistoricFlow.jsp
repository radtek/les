<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<table class="table table-striped table-bordered table-condensed" style="width:90%">
	<tr><th width="15%">事项名称</th><th width="15%">执行环节</th><th>执行人</th><th>提交意见</th><th width="15%">签名时间</th></tr>
	<c:forEach items="${histoicFlowList}" var="act">
		<tr>
            <td>${act.procDef.name}</td>		
			<td>${act.histIns.activityName}</td>
			<td>${act.assigneeName}</td>
			<td style="word-wrap:break-word;word-break:break-all;">${act.comment}</td>
			<td><fmt:formatDate value="${act.endDate}" type="both"/></td>
		</tr>
	</c:forEach>
</table>