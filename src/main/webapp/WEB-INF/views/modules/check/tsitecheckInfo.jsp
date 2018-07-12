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
		
		function exportPdf(){
			$("#inputForm").attr("action","${ctx}/check/tsitecheck/exportPDF");
			$("#inputForm").submit();
	    	
	    }
		
		$(function(){  
		    alert("tsitecheck.projectName");  
		});  
		
	</script>
</head>
<body>
 <h3>基本信息</h3>
    <div style="text-align:center">
    <input id="btnExportPdf" class="btn btn-primary" type="button" value="导出PDF " onclick="exportPdf()"/>
    </div>
	
	<form:form id="inputForm" modelAttribute="tsitecheck" action="${ctx}/check/tsitecheck/saveInfo" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span7">		
			<label class="control-label control-tight">建设单位：</label>
			<div class="controls controls-tight">
				<form:input path="developOrg" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span5">		
			<label class="control-label control-tight">联系人姓名电话：</label>
			<div class="controls controls-tight">
				<form:input path="developContact" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		    
		    <div class="row-fluid">
				<div class="span7">		
			<label class="control-label control-tight">&nbsp;施工单位：</label>
			<div class="controls controls-tight">
				<form:input path="constructionOrg" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span5">		
			<label class="control-label control-tight">联系人姓名电话：</label>
			<div class="controls controls-tight">
				<form:input path="constructionContact" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		    
			<div class="row-fluid">
				<div class="span7">		
			<label class="control-label control-tight">工程名称：</label>
			<div class="controls controls-tight">
				<form:input path="projectName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span5">		
			<label class="control-label control-tight">工程地址：</label>
			<div class="controls controls-tight">
				<form:input path="projectAddress" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
	</div>	
		
		<div class="control-group">
			<label class="control-label">现场检查工程情况：</label>
			<div class="controls">
				<form:textarea path="siteSituation" htmlEscape="false" rows="2" maxlength="128" class="input-xxlarge "/>
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
				<form:textarea path="siteCheckResult" htmlEscape="false" rows="2" maxlength="128" class="input-xxlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="64" class="input-xxlarge "/>
			</div>
		</div>
	
	
	<div class="control-group container-fluid nopadding">
			<div class="row-fluid">			
				<div class="span6">		
			<label class="control-label">勘察人签名：</label>
			<div class="controls controls-tight">
				<les:signatureLoader sig="${tsitecheck.checkerSig}"></les:signatureLoader>
				<les:signatureModal></les:signatureModal>
			</div>
		        </div> 
				<div class="span6">		
			<label class="control-label">当事人签名：</label>
			<div class="controls controls-tight">
			    <les:signatureLoader sig="${tsitecheck.partySig}"></les:signatureLoader>
				<les:signatureModal></les:signatureModal>
			</div>
		        </div>
		    </div>
		</div>		
	<!--  
	<div class="control-group container-fluid nopadding">
		<div class="control-group">
			<div class="span6">
			<label class="control-label">勘察人签名：</label>
			<div class="controls">
				<form:input path="checkerSig" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
			</div>
		</div>
		
		<div class="control-group">
			<div class="span6">
			<label class="control-label">当事人签名：</label>
			<div class="controls">
				<form:input path="partySig" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
			</div>
		</div>
		</div>
		-->	

	</form:form>
	
	 
	 	
		</tbody>
	</table>
	 
</body>
</html>