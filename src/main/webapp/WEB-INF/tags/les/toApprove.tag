<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<%@ attribute name="caseActAttr" type="org.wxjs.les.modules.task.entity.CaseAct" required="true"%>
<%@ attribute name="caseStageAttr" type="java.lang.String" required="true"%>

<c:if test="${caseActAttr.tcase.caseProcess.id eq caseActAttr.processId and caseActAttr.tcase.caseProcess.caseStageStatus eq '1' and caseActAttr.operateType eq 'handle'}">
<div style="text-align:center;">
<input id="btnToApprove" class="btn btn-primary" type="button" value=" 签   名  >>>> " onclick="window.location.href='${ctx}/case/tcase/processTab?${caseActAttr.paramUri}'"/>点击按钮进入签名页。
</div>
</c:if>


