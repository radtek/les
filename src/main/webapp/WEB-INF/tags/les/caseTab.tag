<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

	<style type="text/css">
		.tabText {font-size: 18px;font-weight:bold;}
	</style>

<%@ attribute name="tab" type="java.lang.String" required="true"%>
<%@ attribute name="caseActAttr" type="org.wxjs.les.modules.task.entity.CaseAct" required="true"%>


	<ul class="nav nav-tabs tabText">
		<li <c:if test="${empty tab || tab eq 'info'}">class="active"</c:if> ><a href="${ctx}/case/tcase/infoTab?${caseActAttr.paramUri}">基本信息</a></li>
		<c:if test="${not empty caseActAttr.tcase.id}">
		<li <c:if test="${tab eq 'attach'}">class="active"</c:if>><a href="${ctx}/case/tcase/attachTab?${caseActAttr.paramUri}">资料附件</a></li>
		<li <c:if test="${tab eq 'process'}">class="active"</c:if>><a href="${ctx}/case/tcase/processTab?${caseActAttr.paramUri}">流程管理</a></li>		
		</c:if>

	</ul>