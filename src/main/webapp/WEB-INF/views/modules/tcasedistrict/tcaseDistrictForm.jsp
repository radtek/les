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
			
		    $("input[type=radio][name='partyType']").change(function() {

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
		    	
				$("#inputForm").attr("action","${ctx}/tcasedistrict/tcaseDistrict/save");
				$("#inputForm").submit();		    	
		    });	
		    
		    $('#btnCommit').click(function() {
		    	
		    	var msg = checkFiles();
		    	if(msg != ""){
		    		alert(msg);
		    		return;
		    	}
		    	
				top.$.jBox.confirm("提交后将不能修改，请确认数据正确，确定要提交吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#inputForm").attr("action","${ctx}/tcasedistrict/tcaseDistrict/commit");
						$("#inputForm").submit();
					}
				},{buttonsFocus:1});				

				top.$('.jbox-body .jbox-icon').css('top','55px');		    	
		    });	
		    
		    function checkFiles(){
		    	var msg = "";
		    	var filepathInitial = $("#filepathInitial").val();
		    	
		    	var filepathDecision = $("#filepathDecision").val();
		    	
		    	if(filepathInitial==""){
		    		msg = msg + "请上传立项审批表！";
		    	}
		    	if(filepathDecision==""){
		    		msg = msg + "请上传决定书！";
		    	}
		    	return msg;
		    }
		});
	</script>
</head>
<body>
	<h3>案件管理</h3>
	<form:form id="inputForm" modelAttribute="tcaseDistrict" action="${ctx}/tcasedistrict/tcaseDistrict/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="isNewRecord"/>
		<form:hidden path="handleOrg"/>
		<form:hidden path="status"/>
		
		<sys:message content="${message}"/>		

		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span4">		
			<label class="control-label">案件编号：</label>
			<div class="controls controls-tight">
			    <form:input path="caseSeq" htmlEscape="false" maxlength="32" class="input-medium required"/>
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
	        
				<div class="span4">		
			<label class="control-label">案件来源：</label>
			<div class="controls controls-tight">
                <sys:treeselectAllowInput id="tcasecaseSource" name="caseSource" value="${tcaseDistrict.caseSource}" labelName="caseSource" labelValue="${tcaseDistrict.caseSource}" 
				title="案件来源" url="/sys/dict/treeData?type=case_source" cssClass="input-medium" allowClear="true" notAllowSelectParent="true"/>				
				
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span4">		
			<label class="control-label">状态：</label>
			<div class="controls controls-tight">
			    ${fns:getDictLabel(tcaseDistrict.status, 'case_district_status', '')}
			</div>
		        </div>			        
		    </div>
		</div>
				
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">立案经办人：</label>
			<div class="controls controls-tight">
                <form:input path="initialHandler" htmlEscape="false" maxlength="32" class="input-large required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label">立案时间：</label>
			<div class="controls controls-tight">
				<input name="initialDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${tcaseDistrict.initialDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>			
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
				<form:radiobuttons path="partyType" items="${fns:getDictList('party_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
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
        
        <c:if test="${tcaseDistrict.partyType ne '1'}"><div id="info4Org" class="partyBox" style="display:none"></c:if>
        <c:if test="${tcaseDistrict.partyType eq '1'}"><div id="info4Org" class="partyBox"></c:if>
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">名称：</label>
			<div class="controls controls-tight">
				<form:input id="tcase.orgName" path="orgName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>			
				<div class="span6">		
			<label class="control-label">统一社会信用代码：</label>
			<div class="controls controls-tight">
				<form:input id="tcase.orgCode" path="orgCode" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
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
				<form:input id="tcase.orgAgent" path="orgAgent" htmlEscape="false" maxlength="32" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>			
			</div>
		        </div>
				<div class="span4">		
			<label class="control-label">负责人：</label>
			<div class="controls controls-tight">
				<form:input id="tcase.orgResponsiblePerson" path="orgResponsiblePerson" htmlEscape="false" maxlength="100" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span4">		
			<label class="control-label">负责人职务：</label>
			<div class="controls controls-tight">
				<form:input id="tcase.orgResponsiblePersonPost" path="orgResponsiblePersonPost" htmlEscape="false" maxlength="32" class="input-medium required"/>
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
				<form:input id="tcase.orgAddress" path="orgAddress" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span5">		
			<label class="control-label">联系电话：</label>
			<div class="controls controls-tight">
				<form:input id="tcase.orgPhone" path="orgPhone" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>
		</div><!-- org end -->
		<!-- individual begin -->
		
        <c:if test="${tcaseDistrict.partyType ne '0'}"><div id="info4Individual" class="partyBox" style="display:none"></c:if>
        <c:if test="${tcaseDistrict.partyType eq '0'}"><div id="info4Individual" class="partyBox"></c:if>		
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span5">		
			<label class="control-label">姓名：</label>
			<div class="controls controls-tight">
				<form:input path="psnName" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>			
				<div class="span7">			
			<label class="control-label">性别：</label>
			<div class="controls controls-tight">
				<form:radiobuttons path="psnSex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
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
				<form:input path="psnCode" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span7">		
			<label class="control-label">出生年月：</label>
			<div class="controls controls-tight">
				<input name="psnBirthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${tcaseDistrict.psnBirthday}" pattern="yyyy-MM"/>"
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
				<form:input path="psnOrganization" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span7">		
			<label class="control-label">职务：</label>
			<div class="controls controls-tight">
				<form:input path="psnPost" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
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
				<form:input path="psnPhone" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>			    
			    </div>
				<div class="span7">		
			<label class="control-label">住址：</label>
			<div class="controls controls-tight">
				<form:input path="psnAddress" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
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
				<form:input path="caseHappenDate" htmlEscape="false" maxlength="20" class="input-large required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label">案发地点：</label>
			<div class="controls controls-tight">
				<form:input path="caseHappenAddress" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">案件所涉项目代码：</label>
			<div class="controls controls-tight">
				<form:input path="projectCode" htmlEscape="false" maxlength="100" class="input-large"/>
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label">案件所涉项目名称：</label>
			<div class="controls controls-tight">
				<form:input path="projectName" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
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

				<form:textarea path="caseCause" htmlEscape="false"  style="width:800px;height:80px;" class="required"/>

				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">信用扣分类别：</label>
			<div class="controls controls-tight">
				<form:select id="tcase_projectType" path="projectType" class="input-medium required">
				  <form:option value="" label="----"/>
				  <form:options items="${fns:getDictList('project_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		        
				<div class="span6">		
			<label class="control-label">处罚类型：</label>
			<div class="controls controls-tight">
				<form:select id="tcase_punishType" path="punishType" class="input-medium required">
				  <form:option value="" label="----"/>
				  <form:options items="${fns:getDictList('punish_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>		        
		    </div>
		</div>	
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">处罚决定日期：</label>
			<div class="controls controls-tight">
				<input name="decisionDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${tcaseDistrict.decisionDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label">结案日期：</label>
			<div class="controls controls-tight">
				<input name="settleDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${tcaseDistrict.settleDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>						
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">决定书编号：</label>
			<div class="controls controls-tight">
				<form:input id="decisionSeq" path="decisionSeq" htmlEscape="false" maxlength="256" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label">罚款金额（元）：</label>
			<div class="controls controls-tight">
				<form:input path="punishMoney" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*（无罚款填写0）</font> </span>
			</div>
		        </div>
		    </div>
		</div>
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">立案审批表：</label>
			<div class="controls controls-tight">
				<form:hidden id="filepathInitial" path="filepathInitial" htmlEscape="false" maxlength="256" class="input-xlarge required"/>
				<sys:ckfinder input="filepathInitial" type="files" uploadPath="/${tcaseDistrict.id}" selectMultiple="false"/>					
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label">决定书：</label>
			<div class="controls controls-tight">
				<form:hidden id="filepathDecision" path="filepathDecision" htmlEscape="false" maxlength="256" class="input-xlarge required"/>
				<sys:ckfinder input="filepathDecision" type="files" uploadPath="/${tcaseDistrict.id}" selectMultiple="false"/>					
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>						

		<div class="form-actions">
			<shiro:hasPermission name="tcasedistrict:tcaseDistrict:edit">
			<c:if test="${tcaseDistrict.status eq '0' && tcaseDistrict.readonly ne '1'}">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保  存"/>&nbsp;&nbsp;
			<input id="btnCommit" class="btn btn-primary" type="button" value="提  交"/>&nbsp;&nbsp;			
			</c:if>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	
	<les:projectSearchModal></les:projectSearchModal>
</body>
</html>