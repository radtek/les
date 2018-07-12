<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>询问笔录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#infoInputForm").validate({
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

			$("#infoInputForm").attr("action","${ctx}/qa/questionanswer/exportPDF");
			$("#infoInputForm").submit();
	    	
	    }

		function addQA(){
			$("#qaContent").val($("#qaContent").val() + "\n\r问：\n\r答：");
		}
	</script>
	
	<style type="text/css">
		.nopadding {
		    padding-left: 0px;
		    padding-right: 0px;
		}
			
	</style>
</head>
<body>
    
    <h3>询问笔录</h3>
    <div style="text-align:center">
    <input id="btnExportPdf" class="btn btn-primary" type="button" value="导出PDF " onclick="exportPdf()"/>
    </div>

	<h4>基本信息</h4>
	<sys:message content="${message}"/>
	<form:form id="infoInputForm" modelAttribute="questionanswer" action="${ctx}/qa/questionanswer/saveInfo" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				
		<div class="control-group">
			<label class="control-label control-tight">案由：</label>
			<div class="controls controls-tight">
				<form:textarea path="caseCause" htmlEscape="false"  style="width:800px;height:80px;" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label control-tight">询问时间：</label>
			<div class="controls controls-tight">
				<input name="fromDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${questionanswer.fromDate}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>到
				<input name="toDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${questionanswer.toDate}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>	
				<span class="help-inline"><font color="red">*</font> </span>				
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label control-tight">询问地点：</label>
			<div class="controls controls-tight">
				<form:input path="location" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>			
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span7">		
			<label class="control-label control-tight">调查询问人：</label>
			<div class="controls controls-tight">
				<form:checkboxes path="quizzerList" items="${fns:getUserByOffice('')}" itemLabel="name" itemValue="name" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span5">		
			<label class="control-label control-tight">记录人：</label>
			<div class="controls controls-tight">
				<form:input path="recorder" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		        </div>
		    </div>
		</div>					
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span4">		
			<label class="control-label control-tight">被询问人：</label>
			<div class="controls controls-tight">
				<form:input path="answerer" htmlEscape="false" maxlength="32" class="input-middle "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span3">		
			<label class="control-label control-tight">性别：</label>
			<div class="controls controls-tight">
				<form:radiobuttons path="answererSex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>		        
		        <div class="span5">		
			<label class="control-label control-tight">身份证：</label>
			<div class="controls controls-tight">
				<form:input path="answererCode" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>	
		        </div>
		    </div>
		</div>		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label control-tight">工作单位：</label>
			<div class="controls controls-tight">
				<form:input path="answererOrganization" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span3">		
			<label class="control-label control-tight">职务：</label>
			<div class="controls controls-tight">
				<form:input path="answererPost" htmlEscape="false" maxlength="100" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>		        
		        <div class="span3">		
			<label class="control-label control-tight">出生年月：</label>
			<div class="controls controls-tight">
				<input name="answererBirthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${questionanswer.answererBirthday}" pattern="yyyy-MM"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label control-tight">住址：</label>
			<div class="controls controls-tight">
				<form:input path="answererAddress" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span3">		
			<label class="control-label control-tight">联系电话：</label>
			<div class="controls controls-tight">
				<form:input path="answererPhone" htmlEscape="false" maxlength="32" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>		        
		        <div class="span3">		
			<label class="control-label control-tight">邮政编码：</label>
			<div class="controls controls-tight">
				<form:input path="zipCode" htmlEscape="false" maxlength="32" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>		
		<div class="form-actions">
			<shiro:hasPermission name="qa:questionanswer:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保存基本信息"/>&nbsp;</shiro:hasPermission>
		</div>
	</form:form>
	<h4>问答记录</h4>
	<form:form id="qaInputForm" modelAttribute="questionanswer" action="${ctx}/qa/questionanswer/saveQa" method="post" class="form-horizontal">
		<form:hidden path="id"/>

			<div class="control-group">
				<label class="control-label">询问笔录项目：</label>
				<div class="controls controls-tight">
				</div>
				
			</div>		

			<div class="control-group">
				<label class="control-label control-tight"></label>
				<div class="controls controls-tight">
				 <form:textarea path="qaContent" style="width:800px;height:600px;"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label control-tight"></label>
				<div class="controls" style="text-align:center;">
				 <input id="btnAddQA" class="btn" type="button" value="添加问答" onclick="addQA()" />
				</div>
			</div>	
		<div class="form-actions">
			<shiro:hasPermission name="qa:questionanswer:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保存询问笔录"/>&nbsp;</shiro:hasPermission>
		</div>			
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">			
				<div class="span6">		
			<label class="control-label">被询问人签名：</label>
			<div class="controls controls-tight">
				<les:signatureLoader sig="${questionanswer.asig}"></les:signatureLoader>
				<les:signatureModal></les:signatureModal>
			</div>
		        </div> 
				<div class="span6">		
			<label class="control-label">调查询问人签名：</label>
			<div class="controls controls-tight">
			    <les:signatureLoader sig="${questionanswer.qsig}"></les:signatureLoader>
			</div>
		        </div>
		    </div>
		</div>		
					

	</form:form>	
</body>
</html>