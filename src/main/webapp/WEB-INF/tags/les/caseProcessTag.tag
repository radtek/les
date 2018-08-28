<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="caseSummaryBehaviour" type="java.lang.String" required="false" description="hide, readonly"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#processForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});			
		    
		    $('#btnSaveProcess').click(function() {
				$("#processForm").attr("action","${ctx}/case/tcase/saveProcess");
				$("#processForm").submit();		    	
		    });
		    
		    $('#btnStart').click(function() {
				$("#processForm").attr("action","${ctx}/case/tcase/saveAndStart");
				$("#processForm").submit();		    	
		    });		    
		});
	</script>

	<form:form id="processForm" modelAttribute="caseAct" action="${ctx}/case/tcase/saveProcess" method="post" class="form-horizontal">
		<form:hidden path="tcase.id"/>
		
		<form:hidden path="operateType"/>

		<form:hidden path="tcase.caseProcess.id"/>
		<form:hidden path="tcase.caseProcess.caseId"/>
		<form:hidden path="tcase.caseProcess.caseStage"/>
		<form:hidden path="tcase.caseProcess.caseStageStatus"/>
		
		<sys:message content="${message}"/>	
        <c:choose>
 			<c:when test="${caseSummaryBehaviour eq 'hide'}">
 			</c:when>
 			<c:when test="${caseSummaryBehaviour eq 'readonly'}">
		 		<div class="control-group container-fluid nopadding">
					<div class="row-fluid">
						<div class="span12">		
					<label class="control-label">案情摘要：</label>
					<div class="controls controls-tight">
						<textarea style="width:800px;height:300px;" readonly="readonly">${caseAct.tcase.caseProcess.caseSummary}</textarea>
					</div>
				        </div>
				    </div>
				</div>			
 			</c:when> 			
 			<c:otherwise>
				<div class="control-group container-fluid nopadding">
					<div class="row-fluid">
						<div class="span12">		
					<label class="control-label">案情摘要：</label>
					<div class="controls controls-tight">
		
						<form:textarea path="tcase.caseProcess.caseSummary" htmlEscape="false"  style="width:800px;height:300px;" maxlength="1000" class="required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				        </div>
				    </div>
				</div> 			
 			</c:otherwise>
 		</c:choose>		

		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span12">		
			<label class="control-label">办案人：</label>
			<div class="controls controls-tight">
			    
		       <form:checkboxes path="tcase.caseProcess.caseHandlerList" items="${caseAct.tcase.caseProcess.availableHandlers}" itemLabel="name" itemValue="loginName" htmlEscape="false" class="required"/>
		       <span class="help-inline"><font color="red">*</font> </span>	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		              			
				<c:if test="${caseAct.tcase.caseProcess.editable}">
				    <shiro:hasPermission name="case:tcase:edit"><input id="btnSaveProcess" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
					<c:if test="${caseAct.tcase.caseProcess.startable}">			   
					   <input id="btnStart" class="btn btn-primary" type="button" value="启动事件"/>&nbsp;
					   
					</c:if>
					
					</shiro:hasPermission>
					
				</c:if>				
			</div>
		        </div>
		    </div>
		</div>
		
		<!--  
		<div class="form-actions" style="margin-top:0px;">
		<c:if test="${caseAct.tcase.caseProcess.editable}">
			<shiro:hasPermission name="case:tcase:edit"><input id="btnSaveProcess" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
			<c:if test="${caseAct.tcase.caseProcess.startable}">
			   <input id="btnStart" class="btn btn-primary" type="button" value="启动事件"/>&nbsp;
			</c:if>
			</shiro:hasPermission>
		</c:if>
		</div>
		-->
	</form:form>