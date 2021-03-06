<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

		});
	</script>
</head>
<body>
    <c:if test="${caseAct.caseTransfer eq '1'}">
    <h3>案源移交</h3>
    </c:if>
    <c:if test="${empty caseAct.caseTransfer or caseAct.caseTransfer eq '0'}">
    <h3>案件审理</h3>
    </c:if>

    <les:caseSummary caseAttr="${caseAct.tcase}"></les:caseSummary>	
    
    <les:caseTab tab="process" caseActAttr="${caseAct}"></les:caseTab>  

    <c:choose>
       <c:when test="${caseAct.tcase.caseProcess.caseStage eq '110'}">
          <c:set var="handleAction" value="${ctx}/tcase/caseSerious/handletask"></c:set>
       </c:when>       
       <c:otherwise>
          <c:set var="handleAction" value="${ctx}/case/tcase/handletask"></c:set>
       </c:otherwise>
    </c:choose>

    <c:choose>
       <c:when test="${caseAct.tcase.caseProcess.caseStageStatus eq '1' and not empty caseAct.task}">
         <h5>&nbsp;流程处理</h5>
         <les:processHandleTag handleAction="${handleAction}"
         availableHandlers="${caseAct.tcase.caseProcess.availableHandlers}" 
         caseStageName="${fns:getDictLabel(caseAct.tcase.caseProcess.caseStage, 'case_stage', '')}"
         actTaskAttr="${actTask}" >
         </les:processHandleTag>
       </c:when>       
       <c:otherwise>
       </c:otherwise>
    </c:choose>    
    
    <c:if test="${not empty processlist}">
    <h5>&nbsp;处罚流程</h5>
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:70%">
		<thead>
			<tr>
			    <th width="5%">序号</th>
			    <th width="55%">事件名称</th>
				<th width="20%">状态</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
            <c:forEach items="${processlist}" var="process" varStatus="status">		
			<tr>
			
			    <td>${status.index + 1}</td>
				<td>${fns:getDictLabel(process.caseStage, 'case_stage', '')}</td>			
				<td>${fns:getDictLabel(process.caseStageStatus, 'case_stage_status', '')}</td>	
				<td>
				<c:if test="${process.caseStageStatus eq '0'}">
				<!-- test, delete later 
				<a href="${ctx}/case/tcase/toStart?businesskey=${caseAct.tcase.id}:${process.id}">启动</a>
				-->
				
				<!--  
				<c:if test="${allowStart eq true or process.caseStage eq '10'}">
				  <a href="${ctx}/case/tcase/toStart?businesskey=${caseAct.tcase.id}:${process.id}">启动</a>
				</c:if>
				-->
				</c:if>
				<c:if test="${process.caseStageStatus eq '2' or process.caseStageStatus eq '9'}">
				  <a href="${ctx}/case/tcase/toView?businesskey=${caseAct.tcase.id}:${process.id}">查看</a>
				</c:if>				
				<c:if test="${not empty process.procInsId}">
				  <!-- 		 
				  <a target="_blank" href="${pageContext.request.contextPath}/act/diagram-viewer?processDefinitionId=${process.procDefId}&processInstanceId=${process.procInsId}">跟踪</a>&nbsp;
				  -->
				  <a target="_blank" href="${ctx}/common/activiti/toProcTrack?procDefId=${process.procDefId}&procInsId=${process.procInsId}&executionId=${process.executionId}">跟踪</a>&nbsp;

				</c:if>
				<c:if test="${process.caseStageStatus eq '2'}">
				<shiro:hasPermission name="case:tcase:handleTimeEdit">
				  <a target="_blank" href="${ctx}/tcase/caseProcess/toUpdateSignatureTime?id=${process.id}">修改办理时间</a>
				</shiro:hasPermission>
				</c:if>
					<shiro:hasPermission name="case:tcase:caseDelete4Admin">
					  <a href="${ctx}/case/tcase/deleteCaseStage?caseId=${tcase.id}&caseStage=${process.caseStage}" onclick="return confirmx('此操作不可恢复！确认要删除该流程吗？', this.href)">管理员删除</a>
					</shiro:hasPermission>				
				</td>
			</tr>

			    <c:set var="allowStart" value="${process.caseStageStatus eq '2'}" />

			</c:forEach>																	
		</tbody>
	</table>
	
	</c:if>
	
	<c:if test="${not empty independentProcesslist}">
	<h5>&nbsp;独立流程</h5>
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:70%">
		<thead>
			<tr>
			    <th width="5%">序号</th>
			    <th width="55%">事件名称</th>
				<th width="20%">状态</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
            <c:forEach items="${independentProcesslist}" var="process" varStatus="status">		
			<tr>
			    <td>${status.index + 1}</td>
				<td>${fns:getDictLabel(process.caseStage, 'case_stage', '')}</td>			
				<td>${fns:getDictLabel(process.caseStageStatus, 'case_stage_status', '')}</td>	
				<td>
				<c:if test="${process.caseStageStatus eq '0'}">
				  <a href="${ctx}/case/tcase/toStart?businesskey=${caseAct.tcase.id}:${process.id}">启动</a>
				</c:if>
				<c:if test="${not empty process.procInsId}">
 <!--
				  <a target="_blank" href="${pageContext.request.contextPath}/act/diagram-viewer?processDefinitionId=${process.procDefId}&processInstanceId=${process.procInsId}">跟踪</a>&nbsp;
	  -->			  
				  <a target="_blank" href="${ctx}/common/activiti/toProcTrack?procDefId=${process.procDefId}&procInsId=${process.procInsId}&executionId=${process.executionId}">跟踪</a>&nbsp;
				
				</c:if>
				<c:if test="${process.caseStageStatus eq '1' or (process.caseStageStatus eq '2' and fns:getUserIsAdmin() eq '1')}">
                  <shiro:hasPermission name="tcase:caseProcess:callBackProcess">
				  <a href="${ctx}//tcase/caseProcess/callBackProcess?id=${process.id}&procInsId=${process.procInsId}" onclick="return confirmx('此操作不可恢复！确认要撤回该流程吗？', this.href)">撤回流程</a>&nbsp;
				  </shiro:hasPermission>
				</c:if>		
					<shiro:hasPermission name="case:tcase:caseDelete4Admin">
					  <a href="${ctx}/case/tcase/deleteCaseStage?caseId=${tcase.id}&caseStage=${process.caseStage}" onclick="return confirmx('此操作不可恢复！确认要删除该流程吗？', this.href)">管理员删除</a>
					</shiro:hasPermission>						
				</td>
			</tr>
			</c:forEach>																	
		</tbody>
	</table>
	</c:if>	
	
	<c:if test="${not empty transferProcesslist}">
	<h5>&nbsp;案源移交流程</h5>
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:70%">
		<thead>
			<tr>
			    <th width="5%">序号</th>
			    <th width="55%">事件名称</th>
				<th width="20%">状态</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
            <c:forEach items="${transferProcesslist}" var="process" varStatus="status">		
			<tr>
			    <td>${status.index + 1}</td>
				<td>${fns:getDictLabel(process.caseStage, 'case_stage', '')}</td>
				<td>${fns:getDictLabel(process.caseStageStatus, 'case_stage_status', '')}</td>
				<td>
				<c:if test="${process.caseStageStatus eq '0'}">
				  <a href="${ctx}/case/tcase/toStartTransfer?businesskey=${caseAct.tcase.id}:${process.id}">启动</a>
				</c:if>
				<c:if test="${not empty process.procInsId}">
 <!--
				  <a target="_blank" href="${pageContext.request.contextPath}/act/diagram-viewer?processDefinitionId=${process.procDefId}&processInstanceId=${process.procInsId}">跟踪</a>&nbsp;
	  -->			  
				  <a target="_blank" href="${ctx}/common/activiti/toProcTrack?procDefId=${process.procDefId}&procInsId=${process.procInsId}&executionId=${process.executionId}">跟踪</a>&nbsp; 
				</c:if>	
					<shiro:hasPermission name="case:tcase:caseDelete4Admin">
					  <a href="${ctx}/case/tcase/deleteCaseStage?caseId=${tcase.id}&caseStage=${process.caseStage}" onclick="return confirmx('此操作不可恢复！确认要删除该流程吗？', this.href)">管理员删除</a>
					</shiro:hasPermission>						
				</td>
			</tr>
			</c:forEach>																	
		</tbody>
	</table>
	</c:if>
</body>
</html>