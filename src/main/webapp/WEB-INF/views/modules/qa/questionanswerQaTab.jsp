<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>询问笔录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		
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
	<les:questionanswer tab="qa" qaId="${questionanswer.id}" isNew="${questionanswer.isNewRecord }"></les:questionanswer>
	<form:form id="inputForm" modelAttribute="questionanswer" action="${ctx}/qa/questionanswer/saveQa" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label control-tight">案由：</label>
			<div class="controls controls-tight">
				${questionanswer.caseCause }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label control-tight">询问时间：</label>
			<div class="controls controls-tight">
				<fmt:formatDate value="${questionanswer.fromDate}" pattern="yyyy-MM-dd HH:mm"/>到<fmt:formatDate value="${questionanswer.toDate}" pattern="yyyy-MM-dd HH:mm"/>	
			</div>
		</div>
		<div class="control-group">
			<label class="control-label control-tight">询问地点：</label>
			<div class="controls controls-tight">
				${questionanswer.location}
			</div>
		</div>

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
			<shiro:hasPermission name="qa:questionanswer:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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