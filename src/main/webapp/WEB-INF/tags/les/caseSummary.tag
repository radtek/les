<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<%@ attribute name="caseAttr" type="org.wxjs.les.modules.tcase.entity.Tcase" required="true"%>
	
<form:form class="form-horizontal">
	<fieldset>
		<table class="table-form" style="width:90%">
			<tr>
				<td class="tit" width="20%">事项序号：</td><td width="30%">${caseAttr.caseSeq}
				    <c:choose>
				        <c:when test="${caseAttr.status eq '0'}">（未开始）</c:when>
				        <c:when test="${fns:startsWith(caseAttr.status, '1')}">（办理中）</c:when>
					    <c:when test="${caseAttr.status eq '2'}">（已办结）</c:when>
					    <c:when test="${caseAttr.status eq '9'}">（已撤销）</c:when>					    
					    <c:otherwise>${caseAttr.status}</c:otherwise>
				    </c:choose>				
				</td>
				<td class="tit" width="20%">事项类型：</td><td width="30%">${fns:getDictLabel(caseAttr.caseProcess.caseStage, 'case_stage', '')}：${fns:getDictLabel(caseAttr.caseProcess.caseStageStatus, 'case_stage_status', '未启动')}</td>
			</tr>
			<tr>
				<td class="tit">当事人类型：</td><td>${fns:getDictLabel(caseAttr.partyType, 'party_type', '')}</td>
				<td class="tit">名称：</td><td>${caseAttr.partyDisplay}</td>
			</tr>
			<tr>
				<td class="tit">案件所涉项目名称：</td><td colspan="3">${caseAttr.projectName}&nbsp;&nbsp;<a href="${caseAttr.projectPositionLink}" target="_blank">项目位置</a></td>
			</tr>
			<tr>
				<td class="tit">案由：</td><td colspan="3">${caseAttr.caseCause}</td>
			</tr>
			<tr>
				<td class="tit">经办单位：</td><td colspan="3">${caseAttr.handleOrgName}</td>
			</tr>														
		</table>
	</fieldset>		
</form:form>	
<BR>