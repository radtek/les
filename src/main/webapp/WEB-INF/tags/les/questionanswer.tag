<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

	<style type="text/css">
		.tabText {font-size: 18px;font-weight:bold;}
	</style>

<%@ attribute name="tab" type="java.lang.String" required="true"%>
<%@ attribute name="qaId" type="java.lang.String" required="true"%>
<%@ attribute name="isNew" type="java.lang.String" required="true"%>


	<ul class="nav nav-tabs tabText">
		<li <c:if test="${empty tab || tab eq 'info'}">class="active"</c:if> ><a href="${ctx}/qa/questionanswer/infoTab?id=${qaId}">基本信息</a></li>
		
		<c:if test="${not isNew}">
		<li <c:if test="${tab eq 'qa'}">class="active"</c:if>><a href="${ctx}/qa/questionanswer/qaTab?id=${qaId}">问答记录</a></li>
		</c:if>
	</ul>