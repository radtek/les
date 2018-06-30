<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>	

<%@ attribute name="charge" type="org.wxjs.les.modules.charge.entity.Charge" required="true"%>
		
<form:form id="chargeForm" class="form-horizontal">
    <input id="id" name="id" type="hidden" value="${charge.id}"/>
	<fieldset>
		<div style="text-align:left"><strong>申报信息</strong></div>
		<table class="table-form">

			<tr>
				<td class="tit">申报代码：</td><td>${charge.id}</td>
				<td class="tit">申报日期：</td><td><fmt:formatDate value="${charge.reportDate}" pattern="yyyy-MM-dd"/></td>
			</tr>		
			<tr>
				<td class="tit">项目编号：</td><td>${charge.project.prjNum}</td>
				<td class="tit">项目名称：</td><td>${charge.project.prjName}</td>
			</tr>
			<c:if test="${not empty charge.historyCharges }">
			<tr>
				<td class="tit">历史征收：</td>
				<td colspan="3">
				<c:forEach items="${charge.historyCharges}" var="historyCharge">
				<a href="${ctx}/report/report/searchInfo?id=${historyCharge.id}" target="_blank">${historyCharge.id}&nbsp;(${historyCharge.statusLabel})</a>
				<c:set var="printCommaFlag" value="true" scope="request"/>
				</c:forEach>
				</td>
			</tr>	
			</c:if>		
			<tr>
				<td class="tit">建设单位代码：</td><td>${charge.project.buildCorpCode}</td>
				<td class="tit">建设单位名称：</td><td>${charge.project.buildCorpName}</td>
			</tr>
			<tr>
				<td class="tit">状态：</td><td>${charge.statusLabel}</td>
				<td class="tit">结算金额（元）：</td>
				<td style="text-align:right">
				    <fmt:formatNumber value="${charge.calMoney}" pattern="#,##0.00"/>
				</td>				
			</tr>	
			<c:if test="${charge.status gt '30' }">
			<tr>
				<td class="tit">缴费金额（元）：</td>
				<td style="text-align:right"><fmt:formatNumber value="${charge.payMoney}" pattern="#,##0.00"/></td>
				<td class="tit">待清算金额（元）：</td>
				<td style="text-align:right">${charge.moneyGapDisplay}
				</td>				
			</tr>	
			</c:if> 
			<c:if test="${charge.project.hintShowFlag eq '1' }">
			<tr>
				<td class="tit">提示信息：</td>
				<td colspan="3" >
				  <font color="red">${charge.project.hintMessage}</font>
				</td>
			</tr>	
			</c:if>							      
			
		</table>
	</fieldset>		
</form:form>
