<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>现场踏勘信息管理</title>
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
		});
	</script>
</head>
<body>
	
	<h3>现场踏勘情况</h3>
    <div style="text-align:center">
    <input id="btnExportPdf" class="btn btn-primary" type="button" value="导出PDF " onclick="exportPdf()"/>
    </div>
	
	<form:form id="inputForm" modelAttribute="tsitecheck" action="${ctx}/check/tsitecheck/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
			<div class="control-group container-fluid nopadding">
				<div class="row-fluid">
					<div class="span6">		
				<label class="control-label control-tight">建设单位：</label>
				<div class="controls controls-tight">
					<form:input path="developOrg" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			        </div>
					<div class="span3">		
				<label class="control-label control-tight">联系人姓名：</label>
				<div class="controls controls-tight">
					<form:input path="developContact" htmlEscape="false" maxlength="32" class="input-medium required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			        </div>
			        
			        <div class="span3">		
				<label class="control-label control-tight">联系人电话：</label>
				<div class="controls controls-tight">
					<form:input path="developPhone" htmlEscape="false" maxlength="32" class="input-medium required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			        </div>
		    	</div>
		    </div>
		    
		    <div class="control-group container-fluid nopadding">
			    <div class="row-fluid">
					<div class="span6">		
				<label class="control-label control-tight">&nbsp;施工单位：</label>
				<div class="controls controls-tight">
					<form:input path="constructionOrg" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			        </div>
			        
					<div class="span3">		
				<label class="control-label control-tight">联系人姓名：</label>
				<div class="controls controls-tight">
					<form:input path="constructionContact" htmlEscape="false" maxlength="32" class="input-medium required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			        </div>
			        
			        <div class="span3">		
				<label class="control-label control-tight">联系人电话：</label>
				<div class="controls controls-tight">
					<form:input path="constructionPhone" htmlEscape="false" maxlength="32" class="input-medium required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
		      	  </div>
		    </div>
		   </div>
		   
		   <div class="control-group container-fluid nopadding"> 
				<div class="row-fluid">
					<div class="span6">		
				<label class="control-label control-tight">工程名称：</label>
				<div class="controls controls-tight">
					<form:input path="projectName" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			        </div>
			        
					<div class="span6">		
				<label class="control-label control-tight">工程地址：</label>
				<div class="controls controls-tight">
					<form:input path="projectAddress" htmlEscape="false" maxlength="32" class="input-xxlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			        </div>
			    </div>
		    </div>
		
		
		<div class="control-group">
			<label class="control-label">现场检查情况：</label>
			<div class="controls">
				<form:textarea path="siteSituation" htmlEscape="false" rows="2" maxlength="128" style="width:800px;height:80px;"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现场踏勘示意图:</label>
			<div class="controls">
				<form:hidden id="nameImage" path="sitePicture" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/test/test" selectMultiple="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现场踏勘情况：</label>
			<div class="controls">
				<form:textarea path="siteCheckResult" htmlEscape="false" rows="2" maxlength="128" style="width:800px;height:80px;"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	<div class="control-group">
			<label class="control-label">勘查人：</label>
			<div class="controls">
				<form:input path="checker" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">勘查时间：</label>
			<div class="controls">
				<input name="checkDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tsitecheck.checkDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>				

		<div class="form-actions">
			<shiro:hasPermission name="check:tsitecheck:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		

	</form:form>
</body>
</html>