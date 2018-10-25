<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<%@ attribute name="caseActAttr" type="org.wxjs.les.modules.task.entity.CaseAct" required="true"%>
<%@ attribute name="caseStageAttr" type="java.lang.String" required="true"%>

<c:if test="${caseActAttr.tcase.caseProcess.id eq caseActAttr.processId and caseActAttr.tcase.caseProcess.caseStageStatus eq '1' and caseActAttr.operateType eq 'handle'}">

<act:histoicFlow procInsId="${caseActAttr.procInsId}"/>

<!--  
<div style="text-align:center;margin-bottom:30px">
<input id="btnToApprove" class="btn btn-primary" type="button" value=" 签   名  >>>> " onclick="window.location.href='${ctx}/case/tcase/processTab?${caseActAttr.paramUri}'"/>&nbsp;&nbsp;&nbsp;点击按钮进入签名页。
</div>
-->

    <c:choose>
       <c:when test="${handlingProcess.caseStage eq '110'}">
          <c:set var="handleAction" value="${ctx}/tcase/caseSerious/handletask"></c:set>
       </c:when>       
       <c:otherwise>
          <c:set var="handleAction" value="${ctx}/case/tcase/handletask"></c:set>
       </c:otherwise>
    </c:choose>

    <c:choose>
       <c:when test="${handlingProcess.caseStageStatus eq '1' and not empty caseAct.task}">
         <h5>&nbsp;流程处理</h5>
         <les:processHandleTag handleAction="${handleAction}"
         availableHandlers="${handlingProcess.availableHandlers}" 
         caseStageName="${fns:getDictLabel(handlingProcess.caseStage, 'case_stage', '')}"
         actTaskAttr="${actTask}" >
         </les:processHandleTag>
       </c:when>       
       <c:otherwise>
       </c:otherwise>
    </c:choose>

</c:if>


