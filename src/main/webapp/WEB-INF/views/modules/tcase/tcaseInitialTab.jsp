<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#processForm").validate({
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
		    
		});
	</script>
</head>
<body>
	<h3>案件管理</h3>
	
    <c:if test="${not empty caseAct.tcase.id}">
    <les:caseSummary caseAttr="${caseAct.tcase}"></les:caseSummary>
    </c:if>
    
    <les:caseTab tab="initial" caseActAttr="${caseAct}"></les:caseTab>
    
    <!--  
    <form:form class="form-horizontal">

		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span12">		
			<label class="control-label">案情摘要<BR>(前一环节)：</label>
			<div class="controls controls-tight">
			<textarea style="width:800px;height:300px;" readonly="readonly">${previousCaseSummary}
			</textarea>
			</div>
		        </div>
		    </div>
		</div>		
	</form:form>    
	-->
    	
    <les:caseProcessTag4Initial></les:caseProcessTag4Initial>
</body>
</html>