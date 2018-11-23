<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
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
			
		    $("input[type=radio][name='tcase.partyType']").change(function() {
		        if (this.value == '1') {
		        	$("#info4Org").show();
		        	$("#info4Individual").hide();
		        }
		        else if (this.value == '0') {
		        	$("#info4Org").hide();
		        	$("#info4Individual").show();
		        }
		    });	
		    
		    $('#btnSubmit').click(function() {
				$("#inputForm").attr("action","${ctx}/tcase/caseTransfer/save");
				$("#inputForm").submit();		    	
		    });
		    
		    $('#btnStart').click(function() {
				$("#inputForm").attr("action","${ctx}/tcase/caseTransfer/saveAndStart");
				$("#inputForm").submit();		    	
		    });
		    
		    $('#btnExport').click(function() {
				$("#inputForm").attr("action","${ctx}/tcase/caseTransfer/exportPDF");
				$("#inputForm").submit();		    	
		    });
		    
		    
		});
	</script>
</head>
<body>
	<h3>案件移交</h3>
	
    <c:if test="${not empty caseAct.tcase.id}">
    <les:caseSummary caseAttr="${caseAct.tcase}"></les:caseSummary>
    </c:if>
    
    <les:caseTab tab="infoTransfer" caseActAttr="${caseAct}"></les:caseTab>
    	
	<form:form id="inputForm" modelAttribute="caseAct" action="${ctx}/tcase/caseTransfer/save" method="post" class="form-horizontal">
		<form:hidden path="tcase.id"/>
		
		<form:hidden path="operateType"/>

		<sys:message content="${message}"/>	
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">事项序号：</label>
			<div class="controls controls-tight">
			    <form:hidden path="tcase.caseSeq"/>
			    <c:if test="${empty caseAct.tcase.caseSeq}">自动生成</c:if>
			    <c:if test="${not empty caseAct.tcase.caseSeq}">${caseAct.tcase.caseSeq}</c:if>
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label">事项类型：</label>
			<div class="controls controls-tight">
				案源移交
			</div>
		        </div>
		    </div>
		</div>

		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span4">		
			<label class="control-label">当事人类型：：</label>
			<div class="controls controls-tight">
				<form:radiobuttons path="tcase.partyType" items="${fns:getDictList('party_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span8">		
			<div class="controls controls-tight">
			    <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#projectModal">单位信息查询</button>
			</div>
		        </div>		        
		    </div>
		</div>	
        <!-- org begin -->
        
        <c:if test="${caseAct.tcase.partyType ne '1'}"><div id="info4Org" class="partyBox" style="display:none"></c:if>
        <c:if test="${caseAct.tcase.partyType eq '1'}"><div id="info4Org" class="partyBox"></c:if>
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span7">		
			<label class="control-label">名称：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.orgName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>			
				<div class="span5">		
			<label class="control-label">统一社会信用代码：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.orgCode" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>			

		    </div>
		</div>	
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span7">		
			<label class="control-label">法定代表人：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.orgAgent" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>			
			</div>
		        </div>
				<div class="span5">		
			<label class="control-label">负责人：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.orgResponsiblePerson" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>			
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span7">		
			<label class="control-label">住址：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.orgAddress" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span5">		
			<label class="control-label">联系电话：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.orgPhone" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>
		</div><!-- org end -->
		<!-- individual begin -->
		
        <c:if test="${caseAct.tcase.partyType ne '0'}"><div id="info4Individual" class="partyBox" style="display:none"></c:if>
        <c:if test="${caseAct.tcase.partyType eq '0'}"><div id="info4Individual" class="partyBox"></c:if>		
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span5">		
			<label class="control-label">姓名：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.psnName" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>			
				<div class="span7">			
			<label class="control-label">性别：</label>
			<div class="controls controls-tight">
				<form:radiobuttons path="tcase.psnSex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>			
		        </div>

		    </div>
		</div>	
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span5">		
			<label class="control-label">身份证：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.psnCode" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span7">		
			<label class="control-label">出生年月：</label>
			<div class="controls controls-tight">
				<input name="tcase.psnBirthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${caseAct.tcase.psnBirthday}" pattern="yyyy-MM"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span5">		
			<label class="control-label">工作单位：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.psnOrganization" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span7">		
			<label class="control-label">职务：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.psnPost" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>		
							
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
			    <div class="span5">
			<label class="control-label">联系电话：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.psnPhone" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>			    
			    </div>
				<div class="span7">		
			<label class="control-label">住址：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.psnAddress" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>

		    </div>
		</div>
		
		</div><!-- individual end -->
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">案发时间：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.caseHappenDate" htmlEscape="false" maxlength="20" class="input-large required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label">案发地点：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.caseHappenAddress" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">案件所涉项目名称：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.projectName" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		        
		        <div class="span6">		
			<label class="control-label">移送单位：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.transferUnit" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		        
		    </div>
		</div>
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span12">		
			<label class="control-label">案由：</label>
			<div class="controls controls-tight">
				<form:textarea path="tcase.caseCause" htmlEscape="false"  style="width:800px;height:80px;" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span12">		
			<label class="control-label">处罚类型：</label>
			<div class="controls controls-tight">
				<form:select path="tcase.punishType" class="input-medium">
				  <form:options items="${fns:getDictList('punish_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span12">		
			<label class="control-label">案情摘要：</label>
			<div class="controls controls-tight">
			    <form:hidden path="tcase.caseProcess.id"/>
			    <form:hidden path="tcase.caseProcess.caseId"/>
			    <form:hidden path="tcase.caseProcess.caseStage"/>
			    <form:hidden path="tcase.caseProcess.caseStageStatus"/>
				<form:textarea path="tcase.caseProcess.caseSummary" htmlEscape="false"  style="width:800px;height:80px;" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>

		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span12">		
			<label class="control-label">办案人：</label>
			<div class="controls controls-tight">
			   <sys:treeselect id="caseHandlerList" name="tcase.caseProcess.caseHandlerList" value="${caseAct.tcase.caseProcess.caseHandler}" labelName="" labelValue="${caseAct.tcase.caseProcess.caseHandlerName}" 
				title="办案人" url="/sys/user/treeDataWithLoginName?officeId=" checked="true" cssClass="input-xxlarge" allowClear="true" notAllowSelectParent="true"/>
						       
		       <span class="help-inline"><font color="red">*</font> </span>			              			
				
			</div>
		        </div>
		    </div>
		</div>
		
		<div class="form-actions">
		
			<shiro:hasPermission name="case:tcase:edit">
				<c:if test="${caseAct.tcase.caseProcess.editable}">
					<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
				</c:if>
				<c:if test="${caseAct.operateType eq 'start' || caseAct.tcase.caseProcess.caseStageStatus eq '0'}">
			  	 <input id="btnStart" class="btn btn-primary" type="button" value="启动事件"/>&nbsp;
				</c:if>
			</shiro:hasPermission>
			<input id="btnExport" class="btn btn-primary" type="button" value="案件移送单"/>&nbsp;
		</div>
	</form:form>
	<les:projectSearchModal></les:projectSearchModal>
	<les:toApprove caseActAttr="${caseAct}" caseStageAttr="210"></les:toApprove>
</body>
</html>