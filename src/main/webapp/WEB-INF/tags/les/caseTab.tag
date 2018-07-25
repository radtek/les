<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

	<style type="text/css">
		.tabText {font-size: 18px;font-weight:bold;}
	</style>

<%@ attribute name="tab" type="java.lang.String" required="true"%>
<%@ attribute name="caseActAttr" type="org.wxjs.les.modules.task.entity.CaseAct" required="true"%>


	<ul class="nav nav-tabs tabText">
	    <c:if test="${caseActAttr.caseTransfer ne '1' }">
		<li <c:if test="${empty tab || tab eq 'info'}">class="active"</c:if> ><a href="${ctx}/case/tcase/infoTab?${caseActAttr.paramUri}">基本信息</a></li>
		</c:if>
		
		<c:if test="${caseActAttr.caseTransfer eq '1' }">
		<li <c:if test="${empty tab || tab eq 'infoTransfer'}">class="active"</c:if> ><a href="${ctx}/case/tcase/infoTabTransfer?${caseActAttr.paramUri}">基本信息</a></li>
		</c:if>
		
		<c:if test="${caseActAttr.caseTransfer ne '1' }">
		<li <c:if test="${tab eq 'handle'}">class="active"</c:if>><a href="${ctx}/case/tcase/handleTab?${caseActAttr.paramUri}">案件审理</a></li>
		<li <c:if test="${tab eq 'notify'}">class="active"</c:if>><a href="${ctx}/case/tcase/notifyTab?${caseActAttr.paramUri}">告知书</a></li>
		<li <c:if test="${tab eq 'decision'}">class="active"</c:if>><a href="${ctx}/case/tcase/decisionTab?${caseActAttr.paramUri}">决定书</a></li>
		<li <c:if test="${tab eq 'settle'}">class="active"</c:if>><a href="${ctx}/case/tcase/settleTab?${caseActAttr.paramUri}">结案书</a></li>
		<li <c:if test="${tab eq 'finish'}">class="active"</c:if>><a href="${ctx}/case/tcase/finishTab?${caseActAttr.paramUri}">案件结束</a></li>		
		</c:if>
		
		<c:if test="${not empty caseActAttr.tcase.id and caseActAttr.operateType ne 'start'}">

		<li <c:if test="${tab eq 'attach'}">class="active"</c:if>><a href="${ctx}/case/tcase/attachTab?${caseActAttr.paramUri}">资料附件</a></li>
		<li <c:if test="${tab eq 'process'}">class="active"</c:if>><a href="${ctx}/case/tcase/processTab?${caseActAttr.paramUri}">流程管理</a></li>		
		</c:if>

	</ul>