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
				$("#inputForm").attr("action","${ctx}/case/tcase/save");
				$("#inputForm").submit();		    	
		    });

		});
	</script>
</head>
<body>
	<h3>案件管理</h3>
	
    <c:if test="${not empty caseAct.tcase.id}">
    <les:caseSummary caseAttr="${caseAct.tcase}"></les:caseSummary>
    </c:if>
    
    <les:caseTab tab="info" caseActAttr="${caseAct}"></les:caseTab>
    	
	<form:form id="inputForm" modelAttribute="caseAct" action="${ctx}/case/tcase/save" method="post" class="form-horizontal">
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
				${fns:getDictLabel(caseAct.tcase.caseProcess.caseStage, 'case_stage', '')}
			</div>
		        </div>
		    </div>
		</div>	
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">受理人：</label>
			<div class="controls controls-tight">
                <sys:treeselectAllowInput id="tcaseaccepter" name="tcase.accepter" value="${caseAct.tcase.accepter}" labelName="tcase.accepter" labelValue="${caseAct.tcase.accepter}" 
				title="受理人" url="/sys/user/treeDataWithLoginName?officeId=" checked="true" cssClass="input-xlarge" allowClear="true" notAllowSelectParent="true"/>				
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label">受理时间：</label>
			<div class="controls controls-tight">
				<input name="tcase.acceptDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${caseAct.tcase.acceptDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>				
			</div>
		        </div>
		    </div>
		</div>				

		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span12">		
			<label class="control-label">案件来源：</label>
			<div class="controls controls-tight">
                <sys:treeselectAllowInput id="tcasecaseSource" name="tcase.caseSource" value="${caseAct.tcase.caseSource}" labelName="tcase.caseSource" labelValue="${caseAct.tcase.caseSource}" 
				title="案件来源" url="/sys/dict/treeData?type=case_source" cssClass="input-xlarge" allowClear="true" notAllowSelectParent="true"/>				
				
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>

		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span4">		
			<label class="control-label">当事人类型：</label>
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
				<div class="span6">		
			<label class="control-label">名称：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.orgName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>			
				<div class="span6">		
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
				<div class="span4">		
			<label class="control-label">法定代表人：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.orgAgent" htmlEscape="false" maxlength="32" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>			
			</div>
		        </div>
				<div class="span4">		
			<label class="control-label">负责人：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.orgResponsiblePerson" htmlEscape="false" maxlength="100" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span4">		
			<label class="control-label">负责人职务：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.orgResponsiblePersonPost" htmlEscape="false" maxlength="32" class="input-medium required"/>
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
				<div class="span12">		
			<label class="control-label">案件所涉项目代码：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.projectCode" htmlEscape="false" maxlength="100" class="input-large"/>
			</div>
		        </div>
		    </div>
		</div>		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span12">		
			<label class="control-label">案件所涉项目名称：</label>
			<div class="controls controls-tight">
				<form:input path="tcase.projectName" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
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

				<!--  
                <sys:treeselectWithTextArea id="tcase.caseCause" name="tcase.caseCause" value="" labelName="tcase.caseCause" labelValue="${caseAct.tcase.caseCause}" 
				title="案由" url="/base/punishLib/treeData" cssStyle="width:800px;height:80px;" cssStyleButton="width:40px;height:80px;" allowClear="true" allowInput="true" notAllowSelectParent="true"/>				
				-->
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span12">		
			<label class="control-label">信用扣分类别：</label>
			<div class="controls controls-tight">
				<form:select id="tcase_projectType" path="tcase.projectType" class="input-medium required">
				  <form:option value="" label="----"/>
				  <form:options items="${fns:getDictList('project_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
				<form:select id="tcase_punishType" path="tcase.punishType" class="input-medium required">
				  <form:option value="" label="----"/>
				  <form:options items="${fns:getDictList('punish_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>		
		
		<div class="form-actions">
			<shiro:hasPermission name="case:tcase:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<les:projectSearchModal></les:projectSearchModal>
</body>
</html>