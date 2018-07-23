<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<%@ attribute name="caseAttr" type="org.wxjs.les.modules.tcase.entity.Tcase" required="true"%>
	
<form:form class="form-horizontal">
	<fieldset>
		<table class="table-form" style="width:90%">
			<tr>
				<td class="tit" width="20%">事项序号：</td><td width="30%">${caseAttr.caseSeq}</td>
				<td class="tit" width="20%">事项类型：</td><td width="30%">${fns:getDictLabel(caseAttr.caseProcess.caseStage, 'case_stage', '')}</td>
			</tr>	
			<tr>
				<td class="tit">当事人类型：</td><td>${fns:getDictLabel(caseAttr.partyType, 'party_type', '')}</td>
				<td class="tit">名称：</td><td>${caseAttr.party}</td>
			</tr>
			<tr>
				<td class="tit">案件所涉项目名称：</td><td colspan="3">${caseAttr.projectName}</td>
			</tr>
			<tr>
				<td class="tit">案由：</td><td colspan="3">${caseAttr.caseCause}</td>
			</tr>											
		</table>
	</fieldset>		
</form:form>	
<BR>